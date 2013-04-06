package com.menor.easyfacebookconnect.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.facebook.*;
import com.facebook.model.GraphUser;
import com.menor.easyfacebookconnect.EasySessionListener;
import com.menor.easyfacebookconnect.model.FacebookResponse;
import com.menor.easyfacebookconnect.model.FacebookUser;

public class EasyFacebookActivity extends Activity {

    private EasySessionListener userListener;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    private FacebookUser user;
    private FacebookResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setData(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Session.getActiveSession().addCallback(statusCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Session.getActiveSession().removeCallback(statusCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Session session = Session.getActiveSession();
        Session.saveSession(session, outState);
    }

    /**
     * **********************************
     * ******** Public Methods **********
     * **********************************
     */
    public void connect(EasySessionListener listener)  {
        userListener = listener;
        Session session = Session.getActiveSession();
        if (!session.isOpened() && !session.isClosed()) {
            session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
        } else {
            Session.openActiveSession(this, true, statusCallback);
        }
        session = Session.getActiveSession();
        if (session.isOpened()) {
            makeMeRequest(session);
        }
    }

    protected void disconnect()  {
        Session session = Session.getActiveSession();
        if (!session.isClosed()) {
            session.closeAndClearTokenInformation();
        }
    }

    protected boolean isConnected()  {
        Session session = Session.getActiveSession();
        return session.isOpened();
    }

    protected String getUserFirstName() {
        return (isConnected()) ? user.getFirstName() : null;
    }

    protected String getUserLastName() {
        return (isConnected()) ? user.getLastName() : null;
    }

    protected String getUserBirth() {
        return (isConnected()) ? user.getBirthday() : null;
    }

    protected String getUserId() {
        return (isConnected()) ? user.getId() : null;
    }

    protected String getUserName() {
        return (isConnected()) ? user.getUserName() : null;
    }

    protected String getUserCity() {
        return (isConnected()) ? user.getCity() : null;
    }

    protected String getUserEmail() {
        return (isConnected()) ? response.getEmail() : null;
    }

    protected String getUserGender() {
        return (isConnected()) ? response.getGender() : null;
    }

    protected String getAccessToken() {
        return (isConnected()) ?response.getAccessToken() : null;
    }

    protected String getApplicationId() {
        return (isConnected()) ? response.getAppId() : null;
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


    /**
     * **********************************
     * ******** Private Methods *********
     * **********************************
     */
    private void makeMeRequest(final Session session) {
        userListener.onStart();
        Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {

            @Override
            public void onCompleted(GraphUser facebookUser, Response facebookResponse) {
                if (facebookUser != null) {
                      user.storeUser(facebookUser);
                }
                if (facebookResponse != null) {
                    response.storeResponse(facebookResponse);
                }
                if (session == Session.getActiveSession()) {
                    userListener.onComplete();
                }
                if (facebookResponse.getError() != null) {
                    userListener.onError(facebookResponse.getError());
                }
                userListener.onFinish();
            }

        });
        request.executeAsync();
    }

    private void setData(Bundle savedInstanceState) {
        user = new FacebookUser(this);
        response = new FacebookResponse(this);

        Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }
    }


}
