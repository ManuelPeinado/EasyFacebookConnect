package com.menor.easyfacebookconnect.preference;


import android.content.Context;

public class ResponsePreference extends FacebookPreference {

    private static final String RESPONSE_EMAIL = "facebook_response_email";
    private static final String RESPONSE_GENDER = "facebook_response_gender";
    private static final String RESPONSE_ACCESS_TOKEN = "facebook_response_access_token";
    private static final String RESPONSE_APP_ID = "facebook_response_app_id";

    public ResponsePreference(Context context) {
        super(context);
    }

    public void storeResponse(String email, String gender, String appId, String accessToken) {
        editor.putString(RESPONSE_EMAIL, email);
        editor.putString(RESPONSE_GENDER, gender);
        editor.putString(RESPONSE_ACCESS_TOKEN, accessToken);
        editor.putString(RESPONSE_APP_ID, appId);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(RESPONSE_EMAIL, null);
    }

    public String getGender() {
        return pref.getString(RESPONSE_GENDER, null);
    }

    public String getAppId() {
        return pref.getString(RESPONSE_APP_ID, null);
    }

    public String getAccessToken() {
        return pref.getString(RESPONSE_ACCESS_TOKEN, null);
    }

}
