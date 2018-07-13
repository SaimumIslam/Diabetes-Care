package com.example.foysal.diabetis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Foysal on 11/17/2017.
 */



public class FootCareNotificationServiceStarterReceiver  extends BroadcastReceiver {
    private FootCareDbAdapter helper;
    public void onReceive(Context context, Intent intent) {
        FootCareNotificationEventReceiver.setupAlarm(context,40,7,30,0);
        helper=new FootCareDbAdapter(context);

        if(!helper.getData("early morning").equals(""))
        {
            FootCareNotificationEventReceiver.setupAlarm(context,10,7,0,0);
        }
        if(!helper.getData("morning").equals(""))
        {
            FootCareNotificationEventReceiver.setupAlarm(context,11,9,0,0);
        }
        if(!helper.getData("afternoon").equals(""))
        {
            FootCareNotificationEventReceiver.setupAlarm(context,12,16,0,0);
        }
        if(!helper.getData("evening").equals(""))
        {
            FootCareNotificationEventReceiver.setupAlarm(context,13,18,0,0);
        }
        if(!helper.getData("night").equals(""))
        {
            FootCareNotificationEventReceiver.setupAlarm(context,14,19,30,0);
        }
        if(!helper.getData("late night").equals(""))
        {
            FootCareNotificationEventReceiver.setupAlarm(context,15,23,0,0);
        }
    }
}
