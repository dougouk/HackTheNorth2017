package com.dan190.covfefe.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;

import com.dan190.covfefe.Models.FacebookAccount;
import com.dan190.covfefe.Models.User;
import com.facebook.AccessToken;

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
    private static final String DISPLAY_NAME = "display_name";
    private static final String PHOTO_URL = "photo_url";
    private static final String GOOGLE_ID = "google_id";

    // 1 = email
    // 2 = facebook
    // 3 = gmail
    public static final int EMAIL_ACCOUNT = 1;
    public static final int FACEBOOK_ACCOUNT = 2;
    public static final int GMAIL_ACCOUNT = 3;

    private static final String ACCOUNT_TYPE = "account_type";

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
            editor.putInt(ACCOUNT_TYPE, FACEBOOK_ACCOUNT);
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
            editor.putString(GOOGLE_ID, user.getId());
            editor.putInt(ACCOUNT_TYPE, EMAIL_ACCOUNT);
        }catch(Exception e){
            e.printStackTrace();
        }
        return editor.commit();
    }

    public static int retrieveAccountType(final Context context){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);

        int type = sharedPreferences.getInt(ACCOUNT_TYPE, 0);
        return type;
    }

    public static User retrieveUser(final Context context){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);

        String displayName = sharedPreferences.getString(DISPLAY_NAME, "");
        String email = sharedPreferences.getString(EMAIL, "");
        String id = sharedPreferences.getString(GOOGLE_ID, "");
        String photoUrl= sharedPreferences.getString(PHOTO_URL, "");
        return new User(displayName, email, id, photoUrl, null);
    }

    public static FacebookAccount retrieveFacebookAccount(final Context context){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);

        String name = sharedPreferences.getString(FB_NAME, "");
        String id = sharedPreferences.getString(FB_ID, "");
        return new FacebookAccount(id, name);
    }

    public static boolean changeDisplayname(final Context context, final String newName){
        final SharedPreferences sharedPreferences = context.getSharedPreferences(COVFEFE_USER, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(DISPLAY_NAME, newName);
        return editor.commit();
    }

}
