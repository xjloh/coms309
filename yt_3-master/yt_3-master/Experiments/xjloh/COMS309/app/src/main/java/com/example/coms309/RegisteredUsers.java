package com.example.coms309;

/**
 * For storing user information for registered users retrieved from LoginData
 */
public class RegisteredUsers {
    private String username;
    private String displayName;

    public RegisteredUsers(String user, String display){
        username=user;
        displayName=display;
    }

    public String getUsername(){
        return username;
    }

    public String getDisplayName(){
        return displayName;
    }
}
