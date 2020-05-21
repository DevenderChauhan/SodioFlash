package com.thinker.basethinker.bean;

/**
 * Created by huan on 2016/1/15.
 */
public class AuthCredentials {
    private String mUsername;
    private String mPassword;

    public AuthCredentials(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}