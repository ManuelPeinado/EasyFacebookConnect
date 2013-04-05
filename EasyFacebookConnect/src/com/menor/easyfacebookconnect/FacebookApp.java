package com.menor.easyfacebookconnect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

public class FacebookApp {

	private Activity activity;
	private EasySessionListener userListener;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	private GraphUser facebookUser;
	private Response facebookResponse;
	
	/**
	 * ********************************** 
	 * ********* Constructors ***********
	 * **********************************
	 */
	public FacebookApp(Activity activity) {
		this.activity = activity;
	}
	

	/**
     * **********************************
     * ******** Public Methods **********
     * **********************************
     */
    public void connect(EasySessionListener listener)  {
    	if (checkIntegrity()) {
    		userListener = listener;
    		Session session = Session.getActiveSession();
        	if (!session.isOpened() && !session.isClosed()) {
                session.openForRead(new Session.OpenRequest(activity).setCallback(statusCallback));
            } else {
                Session.openActiveSession(activity, true, statusCallback);
            }
        	session = Session.getActiveSession();
        	if (session.isOpened()) {
            	makeMeRequest(session);
            }
    	}
    } 
    
	public void disconnect()  {
		if (checkIntegrity()) {
			Session session = Session.getActiveSession();
			if (!session.isClosed()) {
				session.closeAndClearTokenInformation();
			}
		}
	}
    
    public boolean isConnected()  {
    	if (checkIntegrity()) {
    		Session session = Session.getActiveSession();
        	return session.isOpened();
    	}
    	return false;
    }
    
    public String getUserFirstName() {
    	if (facebookUser == null) return null;
    	return facebookUser.getFirstName();
    }
    
    public String getUserSurname() {
    	if (facebookUser == null) return null;
    	return facebookUser.getLastName();
    }
    
    public String getUserBirth() {
    	if (facebookUser == null) return null;
    	return facebookUser.getBirthday();
    }
    
    public String getUserId() {
    	if (facebookUser == null) return null;
    	return facebookUser.getId();
    }
    
    public String getUserName() {
    	if (facebookUser == null) return null;
    	return facebookUser.getUsername();
    }
    
    public String getUserCity() {
    	if (facebookUser == null) return null;
    	return (String) facebookUser.getLocation().getProperty("name");
    }
    
    public String getUserEmail() {
    	if (facebookResponse == null) return null;
    	return (String) facebookResponse.getGraphObject().getProperty("email");
    }
    
    public String getUserGender() {
    	if (facebookResponse == null) return null;
    	return (String) facebookResponse.getGraphObject().getProperty("gender");
    }
    
    public String getAccessToken() {
    	if (facebookResponse == null) return null;
    	return (String) facebookResponse.getRequest().getSession().getAccessToken();
    }
    
    public String getApplicationId() {
    	if (facebookResponse == null) return null;
    	return facebookResponse.getRequest().getSession().getApplicationId();
    }
    
    public String getHola() {
    	return null;
    }
    
    
    /**
     * To be called from an Activity or Fragment's onCreate method.
     *
     * @param savedInstanceState the previously saved state
     */
    public void onCreate(Bundle savedInstanceState) {
    	Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
    	Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(activity, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(activity);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(activity).setCallback(statusCallback));
            }
        }
    }

    /**
     * To be called from an Activity or Fragment's onStart method.
     */
    public void onStart() {
    	Session.getActiveSession().addCallback(statusCallback);
    }
    
    /**
     * To be called from an Activity or Fragment's onStop method.
     */
    public void onStop() {
    	Session.getActiveSession().removeCallback(statusCallback);
    }

    /**
     * To be called from an Activity or Fragment's onActivityResult method.
     *
     * @param requestCode the request code
     * @param resultCode the result code
     * @param data the result data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	Session.getActiveSession().onActivityResult(activity, requestCode, resultCode, data);
    }
    
    /**
     * To be called from an Activity or Fragment's onSaveInstanceState method.
     *
     * @param outState context state
     */
    public void onSaveInstanceState(Bundle outState) {
    	Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }


    /**
     * **********************************
     * ******** Private Methods *********
     * **********************************
     */
    private boolean checkIntegrity()  {
    	if (activity == null) {
    		userListener.onDependencyError("You must instantiate FacebookApp class before using its methods");
    		return false;
    	} else {
    		return true;
    	}
    }
    
    private void makeMeRequest(final Session session) {
    	userListener.onStart();
        Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
           
        	@Override
            public void onCompleted(GraphUser user, Response response) {
        		if (user != null) {
        			facebookUser = user;
        		}
        		if (response != null) {
        			facebookResponse = response;
        		}
                if (session == Session.getActiveSession()) {
                    userListener.onComplete();
                }
                if (response.getError() != null) {
                	userListener.onError(response.getError());
                }  
                userListener.onFinish();
            }
        	
        	
    
        });
        request.executeAsync();
    }
    

    /**
     * **********************************
     * ************ Classes *************
     * **********************************
     */
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            switch (state) {
			case CLOSED:
				if (userListener != null) {
					userListener.onClosed(session, state, exception);
				}	
				break;
			case CLOSED_LOGIN_FAILED:
				if (userListener != null) {
					userListener.onClosedLoginFailed(session, state, exception);
				}
				break;
			case OPENED:
				if (userListener != null) {
					userListener.onOpened(session, state, exception);
					makeMeRequest(session);
				}
				break;
			case CREATED:
				if (userListener != null) {
					userListener.onCreated(session, state, exception);
				}
				break;
			default:
				break;
			}
        }
    }

}
