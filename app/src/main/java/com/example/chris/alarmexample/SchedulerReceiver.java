package com.example.chris.alarmexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Chris on 5/20/2016.
 */
public class SchedulerReceiver extends BroadcastReceiver{
    public static String NOTIFICATION_LIST = "notification-list";
    public static String ALERT_LIST = "alert-list";
    public static String NUMBER_NOTIFICATIONS = "number-notifications";
    public static String NOTIFICATION_IDS = "notification-ids";

    private ArrayList<String> sList;
    private ArrayList<Integer> sListIDs;

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {
        sList = intent.getStringArrayListExtra(NOTIFICATION_LIST);
        sListIDs = intent.getIntegerArrayListExtra(NOTIFICATION_IDS);
        Log.i("In S-onRecieve", ""+sList.size());
//        numNotifications = intent.getIntExtra(NUMBER_NOTIFICATIONS, 0);

        Intent schedulerIntent = new Intent(context, NSchedulerIntent.class);
        schedulerIntent.putExtra(NSchedulerIntent.NOTIFICATION_LIST, sList);
        schedulerIntent.putExtra(NSchedulerIntent.NUMBER_NOTIFICATIONS, sList.size());
        schedulerIntent.putExtra(SchedulerReceiver.NOTIFICATION_IDS, sListIDs);
        context.startService(schedulerIntent);
    }
}
