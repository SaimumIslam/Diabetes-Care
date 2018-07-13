package com.example.foysal.diabetis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Foysal on 11/17/2017.
 */

public class BloodGlucoseNotificationServiceStarterReceiver  extends BroadcastReceiver {

    private BGMonitoringDbAdapter helper;
    private BloodGlucoseDbAdapter hp;
    @Override
    public void onReceive(Context context, Intent intent) {

        helper=new BGMonitoringDbAdapter(context);
        hp=new BloodGlucoseDbAdapter(context);

        String date = helper.getDate();
        String time=helper.getTime();

        String da=hp.getDate();

        BloodGlucoseNotificationEventReceiver.setupAlarm(context,30, Integer.parseInt(date)-1,8,0);
        BloodGlucoseNotificationEventReceiver.setupAlarm(context,31, Integer.parseInt(date), Integer.parseInt(time),0);
        BloodGlucoseNotificationEventReceiver.setupAlarm(context,32, Integer.parseInt(da), 8,0);
        BloodGlucoseNotificationEventReceiver.setupAlarm(context,33, Integer.parseInt(da)-1, 8,0);

    }
}
