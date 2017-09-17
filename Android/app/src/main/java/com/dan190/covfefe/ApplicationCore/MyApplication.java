package com.dan190.covfefe.ApplicationCore;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dan on 16/09/2017.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    private static MyApplication instance;
    private static FirebaseAuth auth;
    private static FirebaseAuth.AuthStateListener authStateListener;

    private static FirebaseDatabase mGlobalDB;



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
                    // Signed in
                    Log.i(TAG, "User signed in");
                    String name = user.getDisplayName();
                    String id = user.getUid();
                    String photoUrl = "";
                    User newUser = new User(name, id, null);
                    MainSharedPreferences.emailLogin(MyApplication.getInstance(), newUser);

                }else{
                    // Signed out

                    signOutAndFinish();

                }
            }
        };
        MyApplication.getFirebaseAuth().addAuthStateListener(authStateListener);

        mGlobalDB = FirebaseDatabase.getInstance();
    }


    public static MyApplication getInstance(){
        return instance;
    }

    public static FirebaseAuth getFirebaseAuth(){
        return auth;
    }

    public static void setGlobalDB(FirebaseDatabase database) {
        mGlobalDB = database;
    }

    public static FirebaseDatabase getGlobalDB() {
        return mGlobalDB;
    }


    private void signOutAndFinish(){
        auth.removeAuthStateListener(authStateListener);
        auth.signOut();
        LoginManager.getInstance().logOut();
    }
}
