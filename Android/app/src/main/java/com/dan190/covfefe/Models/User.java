package com.dan190.covfefe.Models;

import android.support.annotation.Nullable;

/**
 * Created by Dan on 16/09/2017.
 */

public class User {
    private String displayName;
    private String signOnId;
    private String photoUrl;
    private String firebaseDbId;
    private FacebookAccount facebookAccount;

    public User(String displayName, String signOnId, @Nullable FacebookAccount facebookAccount) {
        this.displayName = displayName;
        this.signOnId = signOnId;
        this.facebookAccount = facebookAccount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSignOnId() {
        return signOnId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public FacebookAccount getFacebookAccount() {
        return facebookAccount;
    }

    public void setFacebookAccount(FacebookAccount facebookAccount) {
        this.facebookAccount = facebookAccount;
    }

    public void setFirebaseDbId(String firebaseDbId) {
        this.firebaseDbId = firebaseDbId;
    }

    public String getFirebaseDbId() {
        return firebaseDbId;
    }
}
