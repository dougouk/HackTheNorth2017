package com.dan190.covfefe.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.dan190.covfefe.MainActivity;
import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dan on 16/09/2017.
 */

public class AllowLocationActivity extends AppCompatActivity {
    private static final String TAG = AllowLocationActivity.class.getSimpleName();
    private static final int ACCESS_FINE_LOCATION = 150;

    @BindView(R.id.allowImage)
    ImageView allowImage;

    @BindView(R.id.allowButton)
    Button allowButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_location);
        ButterKnife.bind(this);

        if(ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            // Permission already granted
            startMainActivity();
        }

    }

    @OnClick(R.id.allowButton)
    public void askLocationPermission(){
        if(ActivityCompat.checkSelfPermission(MyApplication.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            }
        }else{
            // permission is already granted
            startMainActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case ACCESS_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startMainActivity();
                }
        }
    }

    private void startMainActivity(){
        startActivity(new Intent(AllowLocationActivity.this, MainActivity.class));
        finish();
    }
}
