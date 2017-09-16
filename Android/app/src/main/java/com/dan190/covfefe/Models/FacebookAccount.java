package com.dan190.covfefe.Models;

/**
 * Created by Dan on 16/09/2017.
 */

public class FacebookAccount {
    private String userId;
    private String username;

    public FacebookAccount(String userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
