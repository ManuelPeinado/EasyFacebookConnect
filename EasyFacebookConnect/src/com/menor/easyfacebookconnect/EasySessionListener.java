package com.menor.easyfacebookconnect;

import com.facebook.FacebookRequestError;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

public class EasySessionListener implements FacebookAppListener {

	@Override
	public void onStart() { }

    @Override
    public void onSuccess(Response response) { }

    @Override
    public void onFinish() { }

    @Override
    public void onError(FacebookRequestError error) { }

	@Override
	public void onOpened(Session session, SessionState state, Exception exception) { }

	@Override
	public void onClosed(Session session, SessionState state, Exception exception) { }

	@Override
	public void onCreated(Session session, SessionState state, Exception exception) { }

	@Override
	public void onClosedLoginFailed(Session session, SessionState state, Exception exception) { }

}
