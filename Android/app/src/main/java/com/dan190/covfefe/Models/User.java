package com.dan190.covfefe.Models;

import android.support.annotation.Nullable;

/**
 * Created by Dan on 16/09/2017.
 */

public class User {
    private String displayName;
    private String id;
    private String photoUrl;
    private FacebookAccount facebookAccount;

    public User(String displayName, @Nullable String email, @Nullable String googleId, @Nullable String photoUrl, @Nullable FacebookAccount facebookAccount) {
        this.displayName = displayName;
        this.id = googleId;
        this.photoUrl = photoUrl;
        this.facebookAccount = facebookAccount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getId() {
        return id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public FacebookAccount getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(FacebookAccount facebookAccount) {
        this.facebookAccount = facebookAccount;
    }
}
