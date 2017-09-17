package com.dan190.covfefe.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.dan190.covfefe.Models.User;
import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.Logger;
import com.dan190.covfefe.Util.MainSharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dan on 16/09/2017.
 */

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();

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

        // Set up back arrow in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.signup)
    public void signUp(){
        String email = username.getText().toString();
        String pass = password.getText().toString();
        String passRepeat = passwordRepeat.getText().toString();

        if (email.equals("") || pass.equals("") || passRepeat.equals("")) {
            Logger.makeToast(getString(R.string.fill_out_login_info));
            return;
        }

        if(!pass.equals(passRepeat)){
            Logger.makeToast(getString(R.string.password_no_match));
            return;
        }

        MyApplication.getFirebaseAuth().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // Save user
                            FirebaseUser user = MyApplication.getFirebaseAuth().getCurrentUser();
                            String name = user.getDisplayName();
                            String email = user.getEmail();
                            String id = user.getUid();
                            String photoUrl = user.getPhotoUrl().toString();
                            User newUser = new User(name, email, id, photoUrl, null);
                            MainSharedPreferences.emailLogin(MyApplication.getInstance(), newUser);

                            startActivity(new Intent(SignUpActivity.this, AllowLocationActivity.class));

                        }else{

                            Logger.makeToast(getString(R.string.signup_failed));
                        }
                    }
                });
    }
}
