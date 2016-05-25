package com.example.chris.alarmexample;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private int uniqueID;
    private Button button;
    private TimePicker alarmTimePicker;
    private TextView alarmTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uniqueID = 0;

        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Create pendingIntent to set alerts at a later time
                setNotificationScheduler();
            }

        });

        alarmTimePicker = (TimePicker) findViewById(R.id.alarmTimePicker);
        alarmTextView = (TextView) findViewById(R.id.alarmText);

    }


    private void setNotificationScheduler(){

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 11);
//        calendar.set(Calendar.MINUTE,47);
        calendar.set(Calendar.SECOND, 0);
        long futureInMillis = calendar.getTimeInMillis();
//        long futureInMillis = SystemClock.elapsedRealtime() + 10000;



//        ArrayList<Notification> nList = new ArrayList<>();
//        nList.add(getNotification("Time for "+uniqueID));
//        nList.add(getNotification("Time for "+(uniqueID+1)));
//        Log.i("In setNS", ""+nList.size());


        ArrayList<String> sList = new ArrayList<>();
        ArrayList<Integer> sListIDs = new ArrayList<>();
        sList.add("Time for "+uniqueID);
        sListIDs.add(uniqueID);
        uniqueID++;
        sList.add("Time for "+(uniqueID));
        sListIDs.add(uniqueID);
        uniqueID++;
        Log.i("In setNS", ""+sList.size());


//        Intent schedulerIntent = new Intent(this, NSchedulerIntent.class);
////        schedulerIntent.putExtra(NSchedulerIntent.ALERT_LIST, aList);
//        schedulerIntent.putExtra(NSchedulerIntent.NOTIFICATION_LIST, sList);
//        schedulerIntent.putExtra(NSchedulerIntent.NUMBER_NOTIFICATIONS, sList.size());
//        startService(schedulerIntent);

        Intent schedulerIntent = new Intent(this, SchedulerReceiver.class);
//        schedulerIntent.putExtra(NSchedulerIntent.ALERT_LIST, aList);
        schedulerIntent.putExtra(SchedulerReceiver.NOTIFICATION_LIST, sList);
        schedulerIntent.putExtra(SchedulerReceiver.NUMBER_NOTIFICATIONS, sList.size());
        schedulerIntent.putExtra(SchedulerReceiver.NOTIFICATION_IDS, sListIDs);

//        PendingIntent pendingIntent = PendingIntent.getService(this, uniqueID, schedulerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueID, schedulerIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //Can use setRepeating
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
            Log.i("In setSchedule", "SDK >=19");
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
        }

        if(calendar.MINUTE < 10) {
            Toast.makeText(this, "Intent sent. Set for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" +"0" + calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Intent sent. Set for: " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE), Toast.LENGTH_SHORT).show();
        }

        uniqueID++;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Calendar calendar = Calendar.getInstance();
        switch (item.getItemId()) {
            case R.id.action_5:
                calendar.set(Calendar.HOUR_OF_DAY, 15);
                calendar.set(Calendar.MINUTE,27);
                calendar.set(Calendar.SECOND, 0);
                scheduleNotification(getNotification("Time for "+uniqueID), calendar);
                return true;
            case R.id.action_10:
                scheduleNotification(getNotification("10 second delay"), 5000);
                return true;
            case R.id.cancel_alert:
                cancelAlert(uniqueID-1);
//                cancelAlerts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void scheduleNotification(Notification notification, Calendar calendar) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);

        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, uniqueID);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        long futureInMillis = calendar.getTimeInMillis();
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        //can use setRepeating
        if (Build.VERSION.SDK_INT >= 19) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, futureInMillis, pendingIntent);
            Log.i("In getNotification", "SDK >=19");
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

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, uniqueID);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, uniqueID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

        uniqueID++;
        Toast.makeText(this, "Alert set" , Toast.LENGTH_SHORT).show();
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
//        long timeStamp = System.currentTimeMillis() + 1000*60;
//        builder.setWhen(timeStamp);
        //PRIORITY_MAX places notification at top of notification list and defaults to expanded
        builder.setPriority(NotificationCompat.PRIORITY_MAX);
        //DEFUALT_VIBRATE causes vibration and pup-up notification (provided prioirty == HIGH or greater)
        builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        builder.setCategory(NotificationCompat.CATEGORY_ALARM);

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
            Log.i("In getNotification", "SDK >=16");
            return builder.build();
        }

    }

    private void cancelAlert(int notificationID)
    {
        Intent intent = new Intent(this, NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),notificationID , intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        Toast.makeText(this, "Canceled alert for: X" , Toast.LENGTH_SHORT).show();
    }

    private void cancelAlerts()
    {
        NotificationPublisher publisher = new NotificationPublisher();
//        publisher.cancelAlert(this);
    }


}
