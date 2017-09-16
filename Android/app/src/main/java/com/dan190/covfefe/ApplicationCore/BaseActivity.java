package com.dan190.covfefe.ApplicationCore;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Dan on 16/09/2017.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    // Signed in
                    Log.i(TAG, "User signed in");
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    String id = user.getUid();
                    String photoUrl = "";
                    User newUser = new User(name, email, id, photoUrl, null);
                    MainSharedPreferences.emailLogin(MyApplication.getInstance(), newUser);

                }else{
                    // Signed out

                }
            }
        };
        MyApplication.getFirebaseAuth().addAuthStateListener(authStateListener);
    }

    public FirebaseAuth getAuth() {
        return auth;
    }
}
