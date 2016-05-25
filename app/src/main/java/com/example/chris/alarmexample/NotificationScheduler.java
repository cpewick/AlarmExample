package com.example.chris.alarmexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Chris on 5/19/2016.
 */
public class NotificationScheduler extends BroadcastReceiver {

    public static String SCHEDULER = "scheduler";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Just to test
        Toast.makeText(context, "Intent sent.", Toast.LENGTH_SHORT).show();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

//        Notification notification = intent.getParcelableExtra(NOTIFICATION);
//        int id = intent.getIntExtra(NOTIFICATION_ID, 0);
//        notificationManager.notify(id, notification);

//        scheduleNotification(getNotification("10 second delay"), 10000);

    }

//    private void scheduleNotification(Notification notification, Calendar calendar) {
//        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
//
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, uniqueID);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//
//        long futureInMillis = calendar.getTimeInMillis();
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        //can use setRepeating
//        if (Build.VERSION.SDK_INT >= 19) {
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
//            Log.i("In getNotification", "SDK >=19");
//        } else {
//            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
//        }
//
//        if(calendar.MINUTE < 10) {
//            Toast.makeText(this, "Set alert for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +"0" + calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Set alert for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
//        }
//
//        uniqueID++;
//    }
}
