package com.example.chris.alarmexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

public class NotificationPublisher extends BroadcastReceiver {

    public static String NOTIFICATION_ID = "notification-id";
    public static String NOTIFICATION = "notification";

    public void onReceive(Context context, Intent intent) {

        //Behavior for the action buttons in the notification
        if(intent.getAction() != null ) {
            String action = intent.getAction();

            Log.i("Alarm Test", action + intent.getIntExtra("ID", 0));
            Toast.makeText(context, "Action " + intent.getIntExtra("ID", 0), Toast.LENGTH_SHORT).show();
            return;
        }

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
        Log.i("In onReceive" , ""+id);
        notificationManager.notify(id, notification);

    }
}
