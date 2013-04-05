package com.menor.easyfacebookconnect;

import com.facebook.FacebookRequestError;
import com.facebook.Session;
import com.facebook.SessionState;

public class EasySessionListener implements FacebookAppListener {

	@Override
	public void onComplete() { }
	
	@Override
	public void onError(FacebookRequestError error) { }

	@Override
	public void onStart() { }

	@Override
	public void onOpened(Session session, SessionState state, Exception exception) { }

	@Override
	public void onClosed(Session session, SessionState state, Exception exception) { }

	@Override
	public void onCreated(Session session, SessionState state, Exception exception) { }

	@Override
	public void onClosedLoginFailed(Session session, SessionState state, Exception exception) { }

	@Override
	public void onDependencyError(String error) { }

	@Override
	public void onFinish() { }

}
