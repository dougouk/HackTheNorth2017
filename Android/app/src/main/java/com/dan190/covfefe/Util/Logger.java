package com.dan190.covfefe.Util;

import android.util.Log;
import android.widget.Toast;

import com.dan190.covfefe.ApplicationCore.MyApplication;

/**
 * Created by Dan on 16/09/2017.
 */

public class Logger {
    private static final String TAG = "Logger";

    public static void makeToast(String message){
        Toast.makeText(MyApplication.getInstance(), message, Toast.LENGTH_SHORT).show();
    }

    public static void log(String message){
        Log.d(TAG, message);
    }
}
