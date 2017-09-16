package com.dan190.covfefe.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.dan190.covfefe.MyApplication;
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.Logger;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dan on 16/09/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private CallbackManager callbackManager;

    @BindView(R.id.facebook_login)
    LoginButton facebookLogin;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.signup)
    Button singup;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.loginComponents)
    ConstraintLayout mainLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressBar.setVisibility(View.INVISIBLE);

        facebookLogin.setReadPermissions("email");
        // Other app specific specialization

        callbackManager = CallbackManager.Factory.create();

        // Callback registration
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Logger.makeToast(getString(R.string.facebook_login_failed));
            }
        });

    }

    @OnClick(R.id.signup)
    public void signUp(){
        startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        finish();
    }

    @OnClick(R.id.login)
    public void login(){
        String email = username.getText().toString();
        String pass = password.getText().toString();

        MyApplication.getFirebaseAuth().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            proceed();
                        }else{
                            Logger.makeToast(getString(R.string.login_failed));
                            resetViews();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mainLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken accessToken){
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        MyApplication.getFirebaseAuth().signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            proceed();
                        }else{
                            Logger.makeToast(getString(R.string.facebook_login_failed));
                            resetViews();
                        }
                    }
                });
    }

    private void proceed(){
        startActivity(new Intent(LoginActivity.this, AllowLocationActivity.class));
        finish();
    }

    private void resetViews(){
        progressBar.setVisibility(View.GONE);
        mainLayout.setVisibility(View.VISIBLE);
    }
}
