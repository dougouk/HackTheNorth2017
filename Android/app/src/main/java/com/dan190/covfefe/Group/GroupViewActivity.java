package com.dan190.covfefe.Group;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.dan190.covfefe.Adapters.GroupViewAdapter;
import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Dan on 17/09/2017.
 */

public class GroupViewActivity extends AppCompatActivity {
    private static final String TAG = GroupViewActivity.class.getSimpleName();

    private ImageView user1Prof;
    private ImageView user2Prof;
    private ImageView user3Prof;
    private EditText user1Edit;
    private EditText user2Edit;
    private EditText user3Edit;

    @BindView(R.id.user1)
    CardView user1;

    @BindView(R.id.user2)
    CardView user2;

    @BindView(R.id.user3)
    CardView user3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_view);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user1Prof = (ImageView) user1.findViewById(R.id.profilePicture);
        user2Prof = (ImageView) user2.findViewById(R.id.profilePicture);
        user3Prof = (ImageView) user3.findViewById(R.id.profilePicture);

        user1Edit = (EditText) user1.findViewById(R.id.moneyEditText);
        user2Edit = (EditText) user2.findViewById(R.id.moneyEditText);
        user3Edit = (EditText) user3.findViewById(R.id.moneyEditText);
/*
        user1Edit.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

             }

             @Override
             public void afterTextChanged(Editable editable) {
                user1Edit.setText(String.format("$ %s", user1Edit.getText().toString()));
             }
         });

        user2Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user2Edit.setText(String.format("$ %s", user1Edit.getText().toString()));
            }
        });

        user3Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                user3Edit.setText(String.format("$ %s", user1Edit.getText().toString()));
            }
        });
        */
        Bitmap bmp = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),
                R.drawable.userphoto);

        Bitmap rabbit = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),
                R.drawable.snowball);
        Bitmap pusheen = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),
                R.drawable.pusheen);
        Bitmap john = BitmapFactory.decodeResource(MyApplication.getInstance().getResources(),
                R.drawable.android);

        user1Prof.setImageBitmap(rabbit);
        user2Prof.setImageBitmap(pusheen);
        user3Prof.setImageBitmap(john);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
