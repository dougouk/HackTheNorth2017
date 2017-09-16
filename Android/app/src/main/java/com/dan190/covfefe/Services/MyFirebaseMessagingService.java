package com.dan190.covfefe.Services;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Dan on 16/09/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, String.format("From %s", remoteMessage.getFrom()));
        Log.d(TAG, String.format("Data size: %d", remoteMessage.getData().size()));
        if(remoteMessage.getData().size() > 0) {

        }
    }
}
