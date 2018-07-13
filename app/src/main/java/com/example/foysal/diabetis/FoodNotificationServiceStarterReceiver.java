package com.example.foysal.diabetis;

/**
 * Created by Foysal on 11/15/2017.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
public final class FoodNotificationServiceStarterReceiver extends BroadcastReceiver {

    private FoodDbAdapter helper;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        helper=new FoodDbAdapter(context);

        if(!helper.getData("Breakfast").equals(""))
        {
            FoodNotificationEventReceiver.setupAlarm(context,0,8,0,0);
        }
        if(!helper.getData("Morning Snacks").equals(""))
        {
            FoodNotificationEventReceiver.setupAlarm(context,1,11,0,0);
        }
        if(!helper.getData("Lunch").equals(""))
        {
            FoodNotificationEventReceiver.setupAlarm(context,2,13,0,0);
        }
        if(!helper.getData("Evening Snacks").equals(""))
        {
            FoodNotificationEventReceiver.setupAlarm(context,3,17,0,0);
        }
        if(!helper.getData("Dinner").equals(""))
        {
            FoodNotificationEventReceiver.setupAlarm(context,4,19,30,0);
        }
        if(!helper.getData("Bed Time").equals(""))
        {
            FoodNotificationEventReceiver.setupAlarm(context,5,23,0,0);
        }
    }
}