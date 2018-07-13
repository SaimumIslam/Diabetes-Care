package com.example.foysal.diabetis;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Foysal on 11/15/2017.
 */

public final class PhysicalNotificationServiceStarterReceiver extends BroadcastReceiver {

    private PhysicalDbAdapter helper;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {

        helper=new PhysicalDbAdapter(context);

        if(!helper.getData("early morning").equals(""))
        {
            PhysicalNotificationEventReceiver.setupAlarm(context,10,7,0,0);
        }
        if(!helper.getData("morning").equals(""))
        {
            PhysicalNotificationEventReceiver.setupAlarm(context,11,9,0,0);
        }
        if(!helper.getData("afternoon").equals(""))
        {
            PhysicalNotificationEventReceiver.setupAlarm(context,12,16,0,0);
        }
        if(!helper.getData("evening").equals(""))
        {
            PhysicalNotificationEventReceiver.setupAlarm(context,13,18,0,0);
        }
        if(!helper.getData("night").equals(""))
        {
            PhysicalNotificationEventReceiver.setupAlarm(context,14,19,30,0);
        }
        if(!helper.getData("late night").equals(""))
        {
            PhysicalNotificationEventReceiver.setupAlarm(context,15,23,0,0);
        }
    }
}