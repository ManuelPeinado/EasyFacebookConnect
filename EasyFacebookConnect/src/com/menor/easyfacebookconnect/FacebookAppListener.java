package com.menor.easyfacebookconnect;

import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.SessionState;

/**
 * This listener is called with the results of a facebook user data with specific session attempt
 */
public interface FacebookAppListener {

    /**
     * Called when a dialog completes.
     */
    public void onComplete();
    
    public void onFinish();

    /**
     * Called when a user data attempt is failed
     */
    public void onError(FacebookRequestError error);
    
    public void onStart();

    public void onOpened(Session session, SessionState state, Exception exception);
    
    public void onClosed(Session session, SessionState state, Exception exception);
    
    public void onCreated(Session session, SessionState state, Exception exception);
    
    public void onClosedLoginFailed(Session session, SessionState state, Exception exception);
    
    public void onDependencyError(String error);

}