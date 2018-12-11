package com.example.android.diaryentry;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 0;
    NotificationCompat.Builder notification_builder;
    String username;

    public MyNewIntentService() {
        super("MyNewIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.e(MyNewIntentService.class.getSimpleName(), "Entered MyNewIntentService");
        NotificationManager notification_manager = (NotificationManager) this
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String chanel_id = "300";
            CharSequence name = "Channel Name";
            String description = "Chanel Description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(chanel_id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.BLUE);
            notification_manager.createNotificationChannel(mChannel);
            notification_builder = new NotificationCompat.Builder(this, chanel_id);
        } else {
            notification_builder = new NotificationCompat.Builder(this);
        }
        Intent notifyIntent = new Intent(this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notification_builder.setSmallIcon(R.mipmap.diary_icon)
                .setContentTitle("Diary Entry")
                .setContentText("Hey there!How did your day go?")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        Log.e("notif", "Building");
        notification_manager.notify(300, notification_builder.build());

    }


}