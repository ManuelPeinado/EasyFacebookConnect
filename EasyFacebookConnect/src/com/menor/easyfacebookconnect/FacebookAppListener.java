package com.menor.easyfacebookconnect;

import com.facebook.FacebookRequestError;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

/**
 * This listener is called with the results of a facebook user data with specific session attempt
 */
public interface FacebookAppListener {

    /**
     * Callbacks
     */
    public void onStart();

    public void onSuccess(Response response);

    public void onError(FacebookRequestError error);
    
    public void onFinish();

    public void onOpened(Session session, SessionState state, Exception exception);
    
    public void onClosed(Session session, SessionState state, Exception exception);
    
    public void onCreated(Session session, SessionState state, Exception exception);
    
    public void onClosedLoginFailed(Session session, SessionState state, Exception exception);

}