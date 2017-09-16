package com.dan190.covfefe;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Dan on 16/09/2017.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication instance;
    private static FirebaseAuth auth;
    private static FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        auth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // Signed out
                    signOutAndFinish();
                }else{
                    // Signed in
                    Log.i(TAG, "User signed in at MainActivity");
                }
            }
        };
        MyApplication.getFirebaseAuth().addAuthStateListener(authStateListener);
    }

    public static MyApplication getInstance(){
        return instance;
    }

    public static FirebaseAuth getFirebaseAuth(){
        return auth;
    }

    private void signOutAndFinish(){
        auth.signOut();
        LoginManager.getInstance().logOut();
    }
}
