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
import com.dan190.covfefe.R;
import com.dan190.covfefe.Util.GroupUtils;
import com.dan190.covfefe.Util.Logger;
import com.dan190.covfefe.Util.MainSharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dan on 17/09/2017.
 */

public class JoinGroupActivity extends AppCompatActivity {
    private static final String TAG = JoinGroupActivity.class.getSimpleName();

    @BindView(R.id.joinGroupName)
    EditText joinGroupName;

    @BindView(R.id.joinGroupButton)
    Button joinGroupButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        joinGroupName.addTextChangedListener(textCounter);
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

    @OnClick(R.id.joinGroupButton)
    public void joinGroup(){
        Log.d(TAG, "create group");
        String groupCode = joinGroupName.getText().toString();
        if(groupCode.equals("")){
            Logger.makeToast(getString(R.string.fill_out_group_name));
        }

        GroupUtils.updateGroupsAndUsers(groupCode, MainSharedPreferences.retrieveFirebaseId(MyApplication.getInstance()));
        Logger.makeToast(getString(R.string.group_joined));
        finish();
    }

    private final TextWatcher textCounter = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            Log.d(TAG, String.format("Count: %d", count));
            if(count == 0){
                joinGroupButton.setEnabled(false);
            }else if (count > 0){
                joinGroupButton.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
