package com.dan190.covfefe;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Dan on 16/09/2017.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private static FirebaseAuth auth;
    private static FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        auth = FirebaseAuth.getInstance();

    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static FirebaseAuth getFirebaseAuth(){
        return auth;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static void setUser(FirebaseUser user) {
        MyApplication.user = user;
    }
}
