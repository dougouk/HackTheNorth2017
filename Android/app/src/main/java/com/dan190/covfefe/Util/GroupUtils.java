package com.dan190.covfefe.Util;

import android.app.Activity;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.Group;
import com.dan190.covfefe.Models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * Created by david on 2017-09-16.
 */

public final class GroupUtils extends Activity {

    static FirebaseDatabase database = MyApplication.getInstance().getGlobalDB();

    // NO GETTERS HERE, IT IS SUPPOSED TO BE LISTENER BASED

    // add user to db (called once per user)
    public static String init_user(User new_user){

        DatabaseReference usersRef = database.getReference("users");

        DatabaseReference newUserRef = usersRef.push();
        newUserRef.setValue(new_user);

        String user_id = newUserRef.getKey();

        new_user.setFirebaseDbId(user_id);

        return user_id;
    }

    public void modify_user_fcm_id(){

    }

    //add group to db (called once per group)
    public static String start_group(Group new_group, String user_id){
        DatabaseReference groupsRef = database.getReference("groups");

        DatabaseReference newGroupRef = groupsRef.push();

        // generate random group code
        String group_code = random();
        new_group.setGroupCode(group_code);
        newGroupRef.setValue(new_group);

        String group_id = newGroupRef.getKey();

        return group_id;
    }

    public static void join_group(String group_code, String user_id){

        DatabaseReference groupRef = get_group_using(group_code);
        DatabaseReference userRef = database.getReference("users").child(user_id);

        // add to group table
        DatabaseReference newGroupUser = groupRef.push();
        newGroupUser.setValue(true);

        // add to user table
        DatabaseReference newUserGroup = userRef.push();
        newUserGroup.setValue(true);
    }

    //query group_id from group_code
    public static DatabaseReference get_group_using(String group_code){

        DatabaseReference groupRef = database.getReference("groups");
        DatabaseReference wanted_group = groupRef.equalTo("groupCode", group_code).getRef();

        return wanted_group;
    }

    // helper funcs

    public static String random() {
        int MAX_LENGTH = 8;

        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
