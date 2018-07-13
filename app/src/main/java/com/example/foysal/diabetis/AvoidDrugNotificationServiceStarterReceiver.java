package com.example.foysal.diabetis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Foysal on 11/17/2017.
 */

public class AvoidDrugNotificationServiceStarterReceiver extends BroadcastReceiver {
    private AvoidDrugDbAdapter helper;
    @Override
    public void onReceive(Context context, Intent intent) {

        helper=new AvoidDrugDbAdapter(context);
        if(!helper.getData("early morning").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,50,7,0,0);
        }
        if(!helper.getData("morning").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,51,10,0,0);
        }
        if(!helper.getData("noon").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,52,13,0,0);
        }
        if(!helper.getData("afternoon").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,53,16,0,0);
        }
        if(!helper.getData("evening").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,54,18,0,0);
        }
        if(!helper.getData("night").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,55,20,0,0);
        }
        if(!helper.getData("late night").equals(""))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(context,56,22,0,0);
        }

    }
}
