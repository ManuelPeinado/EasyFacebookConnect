package com.menor.easyfacebookconnect.model;

import android.content.Context;
import com.facebook.model.GraphUser;
import com.menor.easyfacebookconnect.preference.UserPreference;

public class FacebookUser {

    UserPreference userPreference;

    public FacebookUser(Context context) {
        userPreference = new UserPreference(context);
    }

    public void storeUser(GraphUser user) {
        userPreference.storeUser(user.getFirstName(), user.getLastName(), user.getBirthday(), user.getId(), user.getUsername(), (String) user.getLocation().getProperty("name"));
    }

    public String getFirstName() {
        return userPreference.getFirstName();
    }

    public String getLastName() {
        return userPreference.getLastName();
    }

    public String getBirthday() {
        return userPreference.getBirthday();
    }

    public String getId() {
        return userPreference.getId();
    }

    public String getUserName() {
        return userPreference.getUserName();
    }

    public String getCity() {
        return userPreference.getCity();
    }


}
