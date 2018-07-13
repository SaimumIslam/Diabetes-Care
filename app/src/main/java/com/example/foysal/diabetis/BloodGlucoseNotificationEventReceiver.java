package com.example.foysal.diabetis;

/**
 * Created by Foysal on 11/17/2017.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;


public class BloodGlucoseNotificationEventReceiver extends WakefulBroadcastReceiver {

    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    // private static final int NOTIFICATIONS_INTERVAL_IN_HOURS = 1;


    public static void setupAlarm(Context context, int id, int dat, int hh, int min ) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context,id);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date(),dat,hh,min),AlarmManager.INTERVAL_DAY*30,
                alarmIntent);
    }

    private static long getTriggerAt(Date now,int dat,int hh,int min) {
        Calendar calendar = Calendar.getInstance();
// if it's after or equal 9 am schedule for next dayCurrentHour

        calendar.set(Calendar.DAY_OF_MONTH,dat);
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) >dat) {
            calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
        }
        calendar.set(Calendar.HOUR_OF_DAY,hh);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //calendar.add(Calendar.DAY_OF_YEAR, 1);

        //Calendar calendar = Calendar.getInstance();
        //calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context,int id) {
        Intent intent = new Intent(context, BloodGlucoseNotificationEventReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        return PendingIntent.getBroadcast(context, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Intent intent = new Intent(context, BloodGlucoseNotificationEventReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notificationr service");
            serviceIntent = BloodGlucoseNotificationIntentService.createIntentStartNotificationService(context);
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive delete notificationr action, starting notificationr service to handle delete");
            serviceIntent = BloodGlucoseNotificationIntentService.createIntentDeleteNotification(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }
}
