package com.dan190.covfefe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Util.Logger;
import com.dan190.covfefe.Util.MainSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;

/**
 * Created by Dan on 16/09/2017.
 */

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = EditProfileActivity.class.getSimpleName();

    @BindView(R.id.currentDisplayName)
    TextView userId;

    @BindView(R.id.newDisplayName)
    EditText newNameEditText;

    @BindView(R.id.changeDisplayName)
    Button changeNameButton;

    @BindView(R.id.mainLayout)
    ConstraintLayout mainLayout;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String userIdString = MainSharedPreferences.retrieveUser(MyApplication.getInstance()).getId();
        String username = MainSharedPreferences.retrieveUser(MyApplication.getInstance()).getDisplayName();
        userId.setText(userIdString);
        newNameEditText.setHint(username);

        newNameEditText.addTextChangedListener(textCounter);
        changeNameButton.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.changeDisplayName)
    public void changeName(){
        mainLayout.setAlpha(0.2f);
        progressBar.setVisibility(View.VISIBLE);
        String newName = newNameEditText.getText().toString();
        if(newName.equals("")){
            Logger.makeToast(getString(R.string.enter_new_display_name));
            return;
        }

        boolean success = MainSharedPreferences.changeDisplayname(MyApplication.getInstance(), newName);
        if(success){
            Logger.makeToast(String.format("Display name changed to %s", newName));
            finish();
        }else{
            Logger.makeToast(getString(R.string.display_name_failed_to_change));
        }

        progressBar.setVisibility(GONE);
        mainLayout.setAlpha(1f);
    }

    private final TextWatcher textCounter = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            Log.d(TAG, String.format("Count: %d", count));
            if(count == 0){
                changeNameButton.setEnabled(false);
            }else if (count > 0){
                changeNameButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
