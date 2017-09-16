package com.dan190.covfefe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dan190.covfefe.Login.AllowLocationActivity;
import com.dan190.covfefe.Login.LoginActivity;
import com.dan190.covfefe.Util.Logger;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Dan on 16/09/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 800; //ms
    private static final String TAG = SplashActivity.class.getSimpleName();

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = MyApplication.getFirebaseAuth();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    // User is signed in
                    Log.d(TAG, "signed in");
                    proceedLogOn();
                }else{
                    // User is not signed in
                    Log.d(TAG, "user not signed in");

                    if(AccessToken.getCurrentAccessToken() == null){
                        Log.d(TAG, "Facebook not signed in either");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                finish();
                            }
                        }, SPLASH_TIMEOUT);
                    }else {
                        Log.d(TAG, "Facebook already signed on");
                        AuthCredential authCredential = FacebookAuthProvider.getCredential(AccessToken
                                .getCurrentAccessToken()
                                .getToken());
                        auth.signInWithCredential(authCredential)
                                .addOnCompleteListener(SplashActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                            proceedLogOn();
                                        }else{
                                            Logger.makeToast(getString(R.string.facebook_login_failed));
                                        }
                                    }
                                });
                    }
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(authStateListener);
    }

    private void proceedLogOn(){
        startActivity(new Intent(SplashActivity.this, AllowLocationActivity.class));
        finish();
    }
}
