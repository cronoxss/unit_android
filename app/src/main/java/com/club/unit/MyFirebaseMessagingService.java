package com.club.unit;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Joon on 2017-02-19.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
//    SharedPreferences settings_pref = getSharedPreferences("noti_settings", MODE_PRIVATE);;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            String title = (remoteMessage.getData().get("title") != null) ? remoteMessage.getData().get("title") : "Default Notification Title";
//            showNotification(title, remoteMessage.getData().get("message"));
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
//        Log.d(TAG, "BOARD_ID: " + remoteMessage.getData().get("BOARD_ID")+ "// id is : "+settings_pref.getString("id",""));
//        if(remoteMessage.getData().containsKey("BOARD_ID") && settings_pref.getString("id","").equals(remoteMessage.getData().get("BOARD_ID"))){
//            sendNotification(remoteMessage);
//        }
        if(remoteMessage.getData().containsKey("TITLE") && remoteMessage.getData().containsKey("BODY") && remoteMessage.getData().containsKey("URL")){
            sendNotification(remoteMessage.getData().get("TITLE"),remoteMessage.getData().get("BODY"), remoteMessage.getData().get("URL"), remoteMessage.getData().get("WHERE"));
//            sendNotification(remoteMessage);
        }

    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.splash_unit)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    public void sendNotification(String msg_title, String msg_body, String addr_URL, String where) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent resultIntent = new Intent(Intent.ACTION_VIEW);
        resultIntent.setData(Uri.parse(addr_URL));

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, resultIntent,
                PendingIntent.FLAG_ONE_SHOT);
        switch (where){
            case "green":
                msg_title = "[그린] "+msg_title;
                break;
            case "donga":
                msg_title = "[동아] "+msg_title;
                break;
            case "city":
                msg_title = "[시티] "+msg_title;
                break;
            case "u_nion":
                msg_title = "[유니온] "+msg_title;
                break;
            case "jamsil":
                msg_title = "[잠실] "+msg_title;
                break;
            case "jungsan":
                msg_title = "[정산] "+msg_title;
                break;
            case "unit_notice":
                msg_title = "[공지] "+msg_title;
                break;
            case "account":
                msg_title = "[회계록] "+msg_title;
                break;
            case "meeting":
                msg_title = "[회의록] "+msg_title;
                break;
            case "gallary":
                msg_title = "[갤러리] "+msg_title;
                break;
        }

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.unit_logo)
                .setContentTitle(msg_title)
                .setContentText(msg_body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setTicker(msg_title)
                .setContentIntent(pendingIntent);



        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    private void sendNotification(RemoteMessage remoteMessage) {
//        remoteMessage.getData().get("TITLE"),remoteMessage.getData().get("BODY"), remoteMessage.getData().get("URL"), remoteMessage.getData().get("WHERE")
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.splash_unit)
                .setContentTitle("[댓글] "+remoteMessage.getData().get("TITLE"))
                .setContentText(remoteMessage.getData().get("BODY"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
