package com.dan190.covfefe.Util;

import android.app.Activity;

import com.dan190.covfefe.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by david on 2017-09-16.
 */

public class GroupUtils extends Activity {

    MyAppApplication mApp = ((MyAppApplication)getApplicationContext());
    FirebaseDatabase database = mApp.getGlobalDB();

    public String init_user(User new_user){

        DatabaseReference usersRef = database.getReference("users");

        DatabaseReference newUserRef = usersRef.push();
        newUserRef.setValue(new_user);

        String user_id = newUserRef.getKey();

        return user_id;
    }

    public void modify_user_fcm_id(){

    }

    public void start_group(String group_name, int user_id){
//        DatabaseReference groupRef = database.getReference("groups");

//        DatabaseReference newGroupRef = groupRef.push();
//        newGroupRef.setValue(new )
    }

    public void join_group(int group_id, int user_id){

    }
}
