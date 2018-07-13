package com.example.foysal.diabetis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

public class HIFollowUpNotificationActivity extends AppCompatActivity {

    private HIFollowUpDbAdapter helper;

    private int  mDay;

    private TextView next,now;
    private int  d=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_follow_up_notification);

        helper=new HIFollowUpDbAdapter(this);
        now=(TextView)findViewById(R.id.now);
        next=(TextView)findViewById(R.id.next);

        String s=helper.getDate();
        if(s!="")
        {
         try {
             d= Integer.parseInt(s);
         }
         catch (Exception e)
         {
             e.printStackTrace();
         }
        }

        final Calendar c = Calendar.getInstance();
        mDay = c.get(Calendar.DAY_OF_YEAR);;
        if(mDay==d)
        {
            now.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        }
        else
        {
            now.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);

        }

    }
}