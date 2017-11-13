package com.dan190.covfefe.Util;

import android.app.Activity;
import android.util.Log;

import com.dan190.covfefe.ApplicationCore.MyApplication;
import com.dan190.covfefe.Models.Group;
import com.dan190.covfefe.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Random;

/**
 * Created by david on 2017-09-16.
 */

public final class GroupUtils extends Activity {
    private static final String TAG = GroupUtils.class.getSimpleName();

    private static final String USER_DISPLAY_NAME = "displayName";
    private static final String USER_PHOTO_URL = "photoUrl";
    private static final String USER_SIGN_ON_ID = "signOnId";



    // NO GETTERS HERE, IT IS SUPPOSED TO BE LISTENER BASED

    // add user to db (called once per user)
    public static String initUser(User new_user){
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
    public static String initializeGroup(Group newGroup, String userId){
        DatabaseReference groupsRef = MyApplication.getGlobalDB().getReference("groups");

        DatabaseReference newGroupRef = groupsRef.push();

        // generate random group code
        String groupCode = random();
        newGroup.setGroupCode(groupCode);
        newGroupRef.setValue(newGroup);

        String group_id = newGroupRef.getKey();

        updateGroupsAndUsers(groupCode, userId);

        return group_id;
    }

    // Add group to user
    // Add user to group
    public static void updateGroupsAndUsers(final String group_code, final String user_id){

        // Add the member to the group
        DatabaseReference groupRef = MyApplication.getGlobalDB().getReference("groups");
        groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, String.format("%d children", dataSnapshot.getChildrenCount()));
                for (DataSnapshot keys : dataSnapshot.getChildren()) {
                    if (keys.child("groupCode").getValue().toString().equals(group_code)) {
                        DatabaseReference group = keys.getRef();
                        DatabaseReference members = group.child("members");
                        if (members == null) {
                            group.child("members").child(user_id).setValue(true);
                        } else {
                            members.getRef().child(user_id).setValue(true);
                        }
                    } else {
                        Log.e(TAG, String.format("no maching group found for %s", group_code));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, String.format("Error in adding member to group: %s", databaseError.toString()));
            }
        });

        DatabaseReference usersRef = MyApplication.getGlobalDB().getReference("users");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, String.format("%d children", dataSnapshot.getChildrenCount()));
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().equals(user_id)){
                        DatabaseReference user = snapshot.getRef();
                        DatabaseReference groups = user.child("groups");
                        if (groups == null) {
                            user.child("groups").child(group_code).setValue(true);
                        } else {
                            groups.child(group_code).setValue(true);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, String.format("Error in adding member to group: %s", databaseError.toString()));
            }
        });
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
