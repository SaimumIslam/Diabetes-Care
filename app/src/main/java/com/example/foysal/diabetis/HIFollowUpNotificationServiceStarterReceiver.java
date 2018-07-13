package com.example.foysal.diabetis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Foysal on 11/16/2017.
 */


public final class HIFollowUpNotificationServiceStarterReceiver extends BroadcastReceiver {

    private HIFollowUpDbAdapter helper;
    @Override
    public void onReceive(Context context, Intent intent) {

        helper=new HIFollowUpDbAdapter(context);
        String da = helper.getDate();

        if(!da.equals("55"))
        {
            HIFollowUpNotificationEventReceiver.setupAlarm(context,34, Integer.parseInt(da), 8,0);
            HIFollowUpNotificationEventReceiver.setupAlarm(context,35, Integer.parseInt(da)-1, 8,0);
        }
    }
}

