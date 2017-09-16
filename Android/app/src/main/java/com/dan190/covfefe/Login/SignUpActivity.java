package com.dan190.covfefe.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @BindView(R.id.username)
    EditText username;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.password_repeat)
    EditText passwordRepeat;

    @BindView(R.id.signup)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
        ButterKnife.bind(this);

        auth = MyApplication.getFirebaseAuth();
    }

    @OnClick(R.id.signup)
    public void signUp(){
        Log.d(TAG, "signUp");
        String email = username.getText().toString();
        String pass = password.getText().toString();
        String passRepeat = passwordRepeat.getText().toString();

        if(!pass.equals(passRepeat)){
            Logger.makeToast(getString(R.string.password_no_match));
            return;
        }

        auth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, String.format("create new user with email on complete: %s", task.isSuccessful()));

                        if(!task.isSuccessful()){
                            Logger.makeToast(getString(R.string.signup_failed));
                        }else{
                            Log.d(TAG, "created user");
                            MyApplication.setUser(auth.getCurrentUser());
                            startActivity(new Intent(SignUpActivity.this, AllowLocationActivity.class));
                        }
                    }
                });
    }
}
