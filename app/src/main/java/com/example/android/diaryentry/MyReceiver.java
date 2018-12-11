package com.example.android.diaryentry;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intent1 = new Intent(context, MyNewIntentService.class);
        Log.e(MyReceiver.class.getSimpleName(),"Entered MyReceiver");

        context.startService(intent1);
    }
}