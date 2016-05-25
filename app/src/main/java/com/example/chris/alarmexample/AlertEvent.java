package com.example.chris.alarmexample;

import android.app.Notification;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by Chris on 5/19/2016.
 */
public class AlertEvent implements Parcelable{
    private Notification alertNotification;
    private Calendar alertCalendar;


    public AlertEvent (Notification n, Calendar c){
        this.alertNotification = n;
        this.alertCalendar = c;
    }

    public AlertEvent (Parcel p){
//        this.alertNotification = n;
        this.alertCalendar = Calendar.getInstance();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeParcelable(alertNotification);
    }

    public static final Parcelable.Creator<AlertEvent> CREATOR = new Parcelable.Creator<AlertEvent>()
    {
        public AlertEvent createFromParcel(Parcel in1)
        {
//            Log.d (TAG, "createFromParcel()");
            return new AlertEvent(in1);
        }

        public AlertEvent[] newArray (int size)
        {
//            Log.d (TAG, "createFromParcel() newArray ");
            return new AlertEvent[size];
        }
    };

    public Notification getAlertNotification() {
        return alertNotification;
    }

    public Calendar getAlertCalendar() {
        return alertCalendar;
    }
}
