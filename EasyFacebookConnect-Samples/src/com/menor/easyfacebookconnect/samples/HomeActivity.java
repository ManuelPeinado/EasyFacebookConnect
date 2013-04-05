package com.menor.easyfacebookconnect.samples;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.menor.easyfacebookconnect.EasySessionListener;
import com.menor.easyfacebookconnect.FacebookApp;

public class HomeActivity extends Activity {
	/**
	 * TODO CAMBIOS
	 * 
	 * - Hay que probar que estando así la lib, cambiando el manifest a la librería
	 * siga funcionando
	 * 
	 * - Hay que crear algun tipo de singleton, ya que de esta forma los datos de 
	 * usuario pueden ser guardados, de otra forma no
	 * 
	 * - Hay que añadir funcionalidad extendible de EasyFacebookActivity, que te permita
	 * heredar los metodos de ciclo de vida que tenemos que poner nosotros obligatoriamente
	 * 
	 * - Explicar la forma de hacerlo del HelloFacebookSampleActivity, a partir de un boton de Login
	 * tambien se podria solventar nuestro problema!
	 * 
	 * Ejemplos a mirar:
	 * HelloFacebookSample
	 * Scrumptious
	 * 
	 * Si no funciona el modo UiHelper, utilizar:
	 * SessionLoginSample como ejemplo (habré hecho un zip para seguir con mi librería basada en ésta)
	 * 
	 */
	FacebookApp facebook;
	Button facebookView;
	TextView userView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		facebook = new  FacebookApp(this);
		facebook.onCreate(savedInstanceState);
		
		facebookView = (Button) findViewById(R.id.facebook_connect);
		userView = (TextView) findViewById(R.id.facebook_info);
		if (facebook.isConnected()) {
			facebookView.setText("Logout");
			userView.setText("Name: " + facebook.getUserFirstName() + "\n" 
					+ "Surname: " + facebook.getUserSurname() + "\n" 
					+ "Birth: " + facebook.getUserBirth() + "\n" 
					+ "Id: " + facebook.getUserId() + "\n" 
					+ "Username: " + facebook.getUserName() + "\n"
					+ "City: " + facebook.getUserCity() + "\n"
					+ "Email: " + facebook.getUserEmail() + "\n"
					+ "Gender: " + facebook.getUserGender() + "\n"
					+ "accessToken: " + facebook.getAccessToken() + "\n"
					+ "appId: " + facebook.getApplicationId() + "\n");
		} else {
			facebookView.setText("Login");
			userView.setText("");
		}
		facebookView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (facebook.isConnected()) {
					facebook.disconnect();
					facebookView.setText("Login");
					userView.setText("");
				} else {
					facebook.connect(new EasySessionListener() {
						
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
							userView.setText("Name: " + facebook.getUserFirstName() + "\n" 
									+ "Surname: " + facebook.getUserSurname() + "\n" 
									+ "Birth: " + facebook.getUserBirth() + "\n" 
									+ "Id: " + facebook.getUserId() + "\n" 
									+ "Username: " + facebook.getUserName() + "\n"
									+ "City: " + facebook.getUserCity() + "\n"
									+ "Email: " + facebook.getUserEmail() + "\n"
									+ "Gender: " + facebook.getUserGender() + "\n"
									+ "accessToken: " + facebook.getAccessToken() + "\n"
									+ "appId: " + facebook.getApplicationId() + "\n");
							facebookView.setText("Logout");
						}
						
						@Override
						public void onFinish() {
							super.onFinish();
							progressDialog.dismiss();
						}
						
						public void onDependencyError(String error) {
							Toast.makeText(HomeActivity.this, error, Toast.LENGTH_LONG).show();
						}
						
						@Override
						public void onError(FacebookRequestError error) {
							super.onError(error);
							Toast.makeText(HomeActivity.this, error.getErrorMessage(), Toast.LENGTH_LONG).show();
						}
						
						public void onClosedLoginFailed(com.facebook.Session session, com.facebook.SessionState state, Exception exception) {
							Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
						}
						
						public void onCreated(com.facebook.Session session, com.facebook.SessionState state, Exception exception) {
							Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
						}
						
						public void onClosed(com.facebook.Session session, com.facebook.SessionState state, Exception exception) {
							Toast.makeText(HomeActivity.this, state.toString(), Toast.LENGTH_LONG).show();
						}
						
						
					});
				}
			}
		});
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		facebook.onStart();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		facebook.onStop();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebook.onActivityResult(requestCode, resultCode, data);
    }
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		facebook.onSaveInstanceState(outState);
	}


}
