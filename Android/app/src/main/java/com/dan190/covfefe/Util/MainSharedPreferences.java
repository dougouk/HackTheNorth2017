package com.dan190.covfefe.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dan190.covfefe.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dan on 16/09/2017.
 */

public class MainSharedPreferences {
    private static final String COVFEFE_USER = "covfefe_user";
    private static final String FB_ID = "facebook_id";
    private static final String FB_NAME = "facebook_name";
    private static final String EMAIL = "email";
    private static final String DISPLAY_NAME = "email";
    private static final String PHOTO_URL = "email";
    private static final String ID = "email";

    public static boolean logOut(final Context context){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(FB_ID);
        editor.remove(FB_NAME);
        editor.remove(EMAIL);

        return true;
    }

    public static boolean facebookLogin(final Context context, JSONObject loginResponse){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        try{
            editor.putString(FB_ID, loginResponse.getString("id"));
            editor.putString(FB_NAME, loginResponse.getString("name"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return editor.commit();
    }

    public static boolean emailLogin(final Context context, User user){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        try{
            editor.putString(DISPLAY_NAME, user.getDisplayName());
            editor.putString(EMAIL, user.getEmail());
            editor.putString(PHOTO_URL, user.getPhotoUrl());
            editor.putString(ID, user.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        return editor.commit();
    }

    public static User retrieveUser(final Context context){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);

        String displayName = sharedPreferences.getString(DISPLAY_NAME, "");
        String email = sharedPreferences.getString(EMAIL, "");
        String id = sharedPreferences.getString(ID, "");
        String photoUrl= sharedPreferences.getString(PHOTO_URL, "");
        return new User(displayName, email, id, photoUrl, null);
    }

}
