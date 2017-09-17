package com.dan190.covfefe.Group;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.Group;
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.GroupUtils;
import com.dan190.covfefe.Util.Logger;
import com.dan190.covfefe.Util.MainSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dan on 16/09/2017.
 */

public class CreateGroupActivity extends AppCompatActivity {
    private static final String TAG = CreateGroupActivity.class.getSimpleName();

    @BindView(R.id.createGroupName)
    EditText createGroupName;

    @BindView(R.id.createGroupButton)
    Button createGroupButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_group);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        createGroupName.addTextChangedListener(textCounter);
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

    @OnClick(R.id.createGroupButton)
    public void createGroup(){
        Log.d(TAG, "create group");
        String groupTitle = createGroupName.getText().toString();
        if(groupTitle.equals("")){
            Logger.makeToast(getString(R.string.fill_out_group_name));
        }

        Group group = new Group(groupTitle);
        String firebaseId = MainSharedPreferences.retrieveFirebaseId(MyApplication.getInstance());

        GroupUtils.start_group(group, firebaseId);
    }

    private final TextWatcher textCounter = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            Log.d(TAG, String.format("Count: %d", count));
            if(count == 0){
                createGroupButton.setEnabled(false);
            }else if (count > 0){
                createGroupButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
