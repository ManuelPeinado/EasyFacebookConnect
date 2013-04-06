package com.menor.easyfacebookconnect.preference;

import android.content.Context;

public class UserPreference extends FacebookPreference {

    private static final String USER_FIRSTNAME = "facebook_user_firstname";
    private static final String USER_LASTNAME = "facebook_user_lastname";
    private static final String USER_BIRTHDAY = "facebook_user_birthday";
    private static final String USER_ID = "facebook_user_id";
    private static final String USER_USERNAME = "facebook_user_username";
    private static final String USER_CITY = "facebook_user_city";

    public UserPreference(Context context) {
        super(context);
    }

    public void storeUser(String firstName, String lastName, String birth, String userId, String username, String city) {
        editor.putString(USER_FIRSTNAME, firstName);
        editor.putString(USER_LASTNAME, lastName);
        editor.putString(USER_BIRTHDAY, birth);
        editor.putString(USER_ID, userId);
        editor.putString(USER_USERNAME, username);
        editor.putString(USER_CITY, city);
        editor.commit();
    }

    public String getFirstName() {
        return pref.getString(USER_FIRSTNAME, null);
    }

    public String getLastName() {
        return pref.getString(USER_LASTNAME, null);
    }

    public String getBirthday() {
        return pref.getString(USER_BIRTHDAY, null);
    }

    public String getId() {
        return pref.getString(USER_BIRTHDAY, null);
    }

    public String getUserName() {
        return pref.getString(USER_USERNAME, null);
    }

    public String getCity() {
        return pref.getString(USER_CITY, null);
    }

}
