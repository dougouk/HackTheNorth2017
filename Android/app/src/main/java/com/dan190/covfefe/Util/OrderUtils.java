package com.dan190.covfefe.Util;

import com.google.firebase.database.FirebaseDatabase;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by david on 2017-09-16.
 */

public class OrderUtils {

    MyAppApplication mApp = ((MyAppApplication)getApplicationContext());
    FirebaseDatabase database = mApp.getGlobalDB();

}
