package com.example.chris.alarmexample;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chris on 5/19/2016.
 */
public class NSchedulerIntent extends IntentService {

    public static String NOTIFICATION_LIST = "notification-list";
    public static String ALERT_LIST = "alert-list";
    public static String NUMBER_NOTIFICATIONS = "number-notifications";
    public static String NOTIFICATION_IDS = "notification-ids";

    public static String SCHEDULER = "scheduler";
    private int uniqueID;
    private int numNotifications;
    private ArrayList<AlertEvent> aList;
    private ArrayList<Notification> nList;
    private ArrayList<String> sList;
    private ArrayList<Integer> sListIDs;

    public NSchedulerIntent() {
        super(NotificationScheduler.class.getName());
    }

    @Override
    public void onCreate() {
        super.onCreate(); // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }

    @Override
    public void onHandleIntent(Intent intent) {
        // Just to test
        Toast.makeText(this, "Intent sent.", Toast.LENGTH_SHORT).show();
        Log.i("In onHandleIntent", "");
        AlertEvent tempAlert;
        Notification tempNotification;
//        uniqueID = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE,50);
        calendar.set(Calendar.SECOND, 0);
        long futureInMillis = calendar.getTimeInMillis();
        int delay;


        sList = intent.getStringArrayListExtra(NOTIFICATION_LIST);
        sListIDs = intent.getIntegerArrayListExtra(NOTIFICATION_IDS);
        numNotifications = intent.getIntExtra(NUMBER_NOTIFICATIONS, 0);
        for( int i = 0; i < numNotifications; i++) {
            tempNotification = getNotification(sList.get(i), sListIDs.get(i));
            delay = ((i)+1)*5500;
            Log.i("In onHandleIntent", ""+delay + "s");
            Log.i("In onHandleIntent", "ID:"+sListIDs.get(i));
            scheduleNotification(tempNotification, delay, sListIDs.get(i));
        }


//        nList = intent.getParcelableArrayListExtra(NOTIFICATION_LIST);
//        numNotifications = intent.getIntExtra(NUMBER_NOTIFICATIONS, 0);
//        for( int i = 1; i < numNotifications; i++) {
//            tempNotification = nList.get(i);
//            int delay = ((i*3))*1500;
//            Log.i("In onHandleIntent", ""+delay + "s)");
//            scheduleNotification(tempNotification, delay);
//        }

    }

    private void scheduleNotification(Notification notification, Calendar calendar) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, uniqueID);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("In schedNot Intent", "ID:"+uniqueID);

        long futureInMillis = calendar.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //can use setRepeating
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        }

        if(calendar.MINUTE < 10) {
            Toast.makeText(this, "Set alert for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +"0" + calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Set alert for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        }

        uniqueID++;
    }

    private void scheduleNotification(Notification notification, int delay, Integer id) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, id);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("In schedNot Intent", "ID:"+id);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        uniqueID++;
        Toast.makeText(this, "Alert set" , Toast.LENGTH_SHORT).show();
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, uniqueID);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.i("In schedNot Intent", "ID:"+uniqueID);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        uniqueID++;
        Toast.makeText(this, "Alert set" , Toast.LENGTH_SHORT).show();
    }

    private Notification getNotification(String content, Integer id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Pre-Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        long timeStamp = System.currentTimeMillis() + 1000*60;
//        builder.setWhen(timeStamp);
        //PRIORITY_MAX places notification at top of notification list and defaults to expanded
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //DEFUALT_VIBRATE causes vibration and pup-up notification (provided prioirty == HIGH or greater)
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setCategory(NotificationCompat.CATEGORY_ALARM);
        Log.i("In getNot Intent", "ID:"+id);

        //Adds an action button which will appear under the notification
        Intent actionIntent = new Intent(this, NotificationPublisher.class);
        actionIntent.setAction("ACTION");
        actionIntent.putExtra("ID", id);
        PendingIntent pIntentAction = PendingIntent.getBroadcast(this, id, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 16) {
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Action", pIntentAction).build();
            builder.addAction(action);
        }


        if (Build.VERSION.SDK_INT < 16) {

            return builder.getNotification();
        } else {
            return builder.build();
        }

    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Pre-Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        long timeStamp = System.currentTimeMillis() + 1000*60;
//        builder.setWhen(timeStamp);
        //PRIORITY_MAX places notification at top of notification list and defaults to expanded
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //DEFUALT_VIBRATE causes vibration and pup-up notification (provided prioirty == HIGH or greater)
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setCategory(NotificationCompat.CATEGORY_ALARM);
        Log.i("In getNot Intent", "ID:"+uniqueID);

        //Adds an action button which will appear under the notification
        Intent actionIntent = new Intent(this, NotificationPublisher.class);
        actionIntent.setAction("ACTION");
        actionIntent.putExtra("ID", uniqueID);
        PendingIntent pIntentAction = PendingIntent.getBroadcast(this, uniqueID, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >= 16) {
            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Action", pIntentAction).build();
            builder.addAction(action);
        }


        if (Build.VERSION.SDK_INT < 16) {

            return builder.getNotification();
        } else {
            return builder.build();
        }

    }
}