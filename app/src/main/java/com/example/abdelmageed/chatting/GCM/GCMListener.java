package com.example.abdelmageed.chatting.GCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.example.abdelmageed.chatting.R;
import com.example.abdelmageed.chatting.activities.ChatPage;
import com.google.android.gms.gcm.GcmListenerService;


public class GCMListener extends GcmListenerService {
    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String message = data.getString("message");
        String from_user = data.getString("fromUserId");
        String to = data.getString("fromUserName");
        sendNotification(message, from_user, to);
        updateMyActivity(this, message);
    }

    static void updateMyActivity(Context context, String message) {

        Intent intent = new Intent("refresh");

        //put whatever data you want to send, if any
        intent.putExtra("message", message);

        //send broadcast
        context.sendBroadcast(intent);
    }

    private void sendNotification(String friend_name, String friend_id, String message) {

        Intent intent = new Intent(this, ChatPage.class);
        intent.putExtra("friendId", friend_id);
        intent.putExtra("friendName", message);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.icon)
                .setContentTitle("Message")
                .setContentText(friend_name)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}