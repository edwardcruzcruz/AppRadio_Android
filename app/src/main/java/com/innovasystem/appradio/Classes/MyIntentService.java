package com.innovasystem.appradio.Classes;


import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class MyIntentService extends IntentService {

    public static final int NOTIFICATION_ID = 1;

    private NotificationManager notificationManager;
        NotificationCompat.Builder builder;

        public MyIntentService() {
        super("MyIntentService");
        }

        @Override

        protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        // Do the work that requires your app to keep the CPU running.
        // ...
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        //MyWakefulReceiver.completeWakefulIntent(intent);
        }
}