package com.example.foysal.diabetis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Foysal on 11/16/2017.
 */


public final class MedicineNotificationServiceStarterReceiver extends BroadcastReceiver {
    private MedicineDbAdapter helper;
    @Override
    public void onReceive(Context context, Intent intent) {

        helper=new MedicineDbAdapter(context);

        if(!helper.getData("Early Morning").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,20,7,0,0);
        }
        if(!helper.getData("Morning").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,21,10,0,0);
        }
        if(!helper.getData("Noon").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,22,13,0,0);
        }
        if(!helper.getData("AfterNoon").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,23,16,0,0);
        }
        if(!helper.getData("Evening").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,24,19,0,0);
        }
        if(!helper.getData("Night").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,25,21,0,0);
        }
        if(!helper.getData("LateNight").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,26,23,0,0);
        }
        if(!helper.getData("MidNight").equals(""))
        {
            MedicineNotificationEventReceiver.setupAlarm(context,27,1,30,0);
        }
    }
}