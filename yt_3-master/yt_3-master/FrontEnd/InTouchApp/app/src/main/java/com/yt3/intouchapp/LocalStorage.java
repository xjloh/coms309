package com.yt3.intouchapp;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {

    private static SharedPreferences userPreferences;

    /**
     * Get session key; return null if it's not created yet
     */
    public static String getSessionKey(Context context)
    {
        // Get shared preferences
        userPreferences = context.getSharedPreferences("AccountConfig", Context.MODE_PRIVATE);

        // Return session key
        if (userPreferences.contains("sessionKey"))
            return userPreferences.getString("sessionKey", null);
        else
            return "";
    }


    public static void setSessionKey(Context context, String newSessionKey)
    {
        // Get shared preferences
        userPreferences = context.getSharedPreferences("AccountConfig", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userPreferences.edit();

        // Store session key to shared preferences
        editor.putString("sessionKey", newSessionKey);
        editor.commit();
    }
}
