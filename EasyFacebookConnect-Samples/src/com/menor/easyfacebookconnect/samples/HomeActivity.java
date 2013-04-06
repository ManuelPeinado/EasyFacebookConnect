package com.menor.easyfacebookconnect.samples;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.SessionState;
import com.menor.easyfacebookconnect.EasySessionListener;
import com.menor.easyfacebookconnect.ui.EasyFacebookActivity;

public class HomeActivity extends EasyFacebookActivity {
    /**
     *
     * - Explicar la forma de hacerlo del HelloFacebookSampleActivity, a partir de un boton de Login
     * tambien se podria solventar nuestro problema!
     *
     * Ejemplos a mirar:
     * HelloFacebookSample
     * Scrumptious
     * <p/>
     * Si no funciona el modo UiHelper, utilizar:
     * SessionLoginSample como ejemplo (habré hecho un zip para seguir con mi librería basada en ésta)
     */
    Button facebookView;
    TextView userView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        facebookView = (Button) findViewById(R.id.facebook_connect);
        userView = (TextView) findViewById(R.id.facebook_info);
        if (isConnected()) {
            updateLoginUi();
        } else {
            udateLogoutUi();
        }
        facebookView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

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
                        public void onComplete() {
                            super.onComplete();
                            updateLoginUi();
                        }

                        @Override
                        public void onFinish() {
                            super.onFinish();
                            progressDialog.dismiss();
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
