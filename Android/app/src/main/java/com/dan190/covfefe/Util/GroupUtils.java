package com.dan190.covfefe.Util;

import android.app.Activity;
import android.util.Log;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.Group;
import com.dan190.covfefe.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.Random;

/**
 * Created by david on 2017-09-16.
 */

public final class GroupUtils extends Activity {
    private static final String TAG = GroupUtils.class.getSimpleName();

    private static final String USER_DISPLAY_NAME = "displayName";
    private static final String USER_PHOTO_URL = "photoUrl";
    private static final String USER_SIGN_ON_ID = "signOnId";



    // add user to db (called once per user)
    public static String init_user(User new_user){
        Log.d(TAG, "init user");

        DatabaseReference usersRef = MyApplication.getGlobalDB().getReference("users");

        DatabaseReference newUserRef = usersRef.push();
        newUserRef.setValue(new_user);

        String user_id = newUserRef.getKey();

        new_user.setFirebaseDbId(user_id);

        return user_id;
    }

    public void modify_user_fcm_id(){

    }

    //add group to db (called once per group)
    /*
        Create a group with the given name
     */
    public static String start_group(Group new_group, String user_id){
        DatabaseReference groupsRef = MyApplication.getGlobalDB().getReference("groups");

        DatabaseReference newGroupRef = groupsRef.push();

        // generate random group code
        String group_code = random();
        new_group.setGroupCode(group_code);
        newGroupRef.setValue(new_group);

        String group_id = newGroupRef.getKey();

        join_group(group_code, user_id);

        return group_id;
    }

    // Add group to user
    // Add user to group
    public static void join_group(String group_code, String user_id){

        DatabaseReference groupRef = get_group_using(group_code).child("users");
        DatabaseReference userRef = MyApplication.getGlobalDB().getReference("users").child(user_id).child("groups");

        // add to group table
        DatabaseReference newGroupUser = groupRef.push();
        newGroupUser.setValue(true);

        // add to user table
        DatabaseReference newUserGroup = userRef.push();
        newUserGroup.setValue(true);
    }

    //query group_id from group_code
    // Given the group code, looks it up and returns a reference to the group
    public static DatabaseReference get_group_using(String group_code){

        DatabaseReference groupRef = MyApplication.getGlobalDB().getReference("groups");

        DatabaseReference wanted_group = groupRef.equalTo(group_code, "groupCode").getRef();

        return wanted_group;
    }

    // helper funcs

    public static String random() {
        int MAX_LENGTH = 8;
        int MIN_LENGTH = 8;
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH) + MIN_LENGTH;
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(26) + 97);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    public static List<User> getGroup(String groupKey){
        DatabaseReference ref = MyApplication.getGlobalDB().getReference("users");
        final List<User> users = new ArrayList<User>();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, String.format("%d children", dataSnapshot.getChildrenCount()));
                for(DataSnapshot list: dataSnapshot.getChildren()){
                    Map<String, Object> map = ((Map<String, Object>) list.getValue());
                    String displayName = (String) map.get(USER_DISPLAY_NAME);
                    String photoUrl = (String) map.get(USER_PHOTO_URL);
                    String signOnId = (String) map.get(USER_SIGN_ON_ID);
//                    User user = new User(displayName, signOnId, photoUrl, null);
//                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled");
            }
        });

        return users;


    }
}
