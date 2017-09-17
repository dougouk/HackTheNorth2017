package com.dan190.covfefe.Util;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by david on 2017-09-16.
 */


public class MyAppApplication extends Application {

    private FirebaseDatabase mGlobalDB;

    public FirebaseDatabase getGlobalDB() {
        return mGlobalDB;
    }

    public void setGlobalDB(FirebaseDatabase database) {
        mGlobalDB = database;
    }
}
