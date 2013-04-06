package com.menor.easyfacebookconnect.preference;


import android.content.Context;
import android.content.SharedPreferences;

public class FacebookPreference {

    protected final SharedPreferences pref;
    protected final SharedPreferences.Editor editor;

    private static final String FACEBOOK_SHARED = "facebook_preference";

    public FacebookPreference(Context context) {
        pref = context.getSharedPreferences(FACEBOOK_SHARED, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

}
