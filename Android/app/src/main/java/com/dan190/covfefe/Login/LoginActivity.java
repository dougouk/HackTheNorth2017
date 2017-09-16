package com.dan190.covfefe.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.dan190.covfefe.MainActivity;
import com.dan190.covfefe.MyApplication;
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.Logger;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dan on 16/09/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private FirebaseAuth auth;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.signup)
    Button singup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        auth = MyApplication.getFirebaseAuth();

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

        auth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Logger.makeToast(getString(R.string.login_failed));
                        }else{
                            MyApplication.setUser(auth.getCurrentUser());
                            startActivity(new Intent(LoginActivity.this, AllowLocationActivity.class));
                        }
                    }
                });
    }
}
