package com.menor.easyfacebookconnect.samples;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookRequestError;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.menor.easyfacebookconnect.EasySessionListener;
import com.menor.easyfacebookconnect.ui.EasyFacebookActivity;

public class HomeActivity extends EasyFacebookActivity implements OnClickListener {

    TextView userView;
    Button facebookView;
    EditText statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setViews();
        if (isConnected()) {
            updateLoginUi();
        } else {
            udateLogoutUi();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.facebook_connect:
                connect();
                break;
            case R.id.facebook_image_upload:
                uploadPhoto();
                break;
            case R.id.facebook_status_upload:
                uploadStatus();
                break;

        }
    }

    private void setViews() {
        facebookView = (Button) findViewById(R.id.facebook_connect);
        facebookView.setOnClickListener(this);
        findViewById(R.id.facebook_status_upload).setOnClickListener(this);
        findViewById(R.id.facebook_image_upload).setOnClickListener(this);
        statusView = (EditText) findViewById(R.id.facebook_status_edit);
        userView = (TextView) findViewById(R.id.facebook_info);
    }

    private void connect() {
        if (isConnected()) {
            disconnect();
            udateLogoutUi();
        } else {
            connect(new EasySessionListener() {

                ProgressDialog progressDialog;

                @Override
                public void onStart() {
                    super.onStart();
                    progressDialog = new ProgressDialog(HomeActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Cargando...");
                    progressDialog.show();
                }

                @Override
                public void onSuccess(Response response) {
                    super.onSuccess(response);
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    progressDialog.dismiss();
                    updateLoginUi();
                }

                @Override
                public void onError(FacebookRequestError error) {
                    super.onError(error);
                    Toast.makeText(HomeActivity.this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
                }

                public void onClosedLoginFailed(Session session, SessionState state, Exception exception) {
                    Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
                }

                public void onCreated(Session session, SessionState state, Exception exception) {
                    Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
                }

                public void onClosed(Session session, SessionState state, Exception exception) {
                    Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
                }

            });
        }
    }

    private void uploadPhoto() {
        uploadPhoto(null, new EasySessionListener() {

            @Override
            public void onSuccess(Response response) {
                super.onSuccess(response);
                Toast.makeText(HomeActivity.this, "Photo uploaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookRequestError error) {
                super.onError(error);
                Toast.makeText(HomeActivity.this, "Photo not uploaded: " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                updateLoginUi();
            }
        });
    }

    private void uploadStatus() {
        String text = statusView.getText().toString();
        uploadStatus(text, new EasySessionListener() {

            @Override
            public void onSuccess(Response response) {
                super.onSuccess(response);
                Toast.makeText(HomeActivity.this, "Status uploaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookRequestError error) {
                super.onError(error);
                Toast.makeText(HomeActivity.this, "Status not uploaded: " + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                updateLoginUi();
            }
        });
    }

    private void updateLoginUi() {
        userView.setText("Name: " + getUserFirstName() + "\n"
            + "Surname: " + getUserLastName() + "\n"
            + "Birth: " + getUserBirth() + "\n"
            + "Id: " + getUserId() + "\n"
            + "Username: " + getUserName() + "\n"
            + "City: " + getUserCity() + "\n"
            + "Email: " + getUserEmail() + "\n"
            + "Gender: " + getUserGender() + "\n"
            + "accessToken: " + getAccessToken() + "\n"
            + "appId: " + getApplicationId() + "\n");
        facebookView.setText("Logout");
    }

    private void udateLogoutUi() {
        facebookView.setText("Login");
        userView.setText("");
    }

}
