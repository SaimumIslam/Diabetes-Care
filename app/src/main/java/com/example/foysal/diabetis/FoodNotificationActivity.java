package com.example.foysal.diabetis;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class FoodNotificationActivity extends AppCompatActivity {

    private FoodDbAdapter helper;
    private String name="Bed Time";
    String data,store="";
    private int  mHour;

    ArrayList<String> list=new ArrayList<String>();
    private TextView foodview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add("Breakfast");
        list.add("Morning Snacks");
        list.add("Evening Snacks");
        list.add("Lunch");
        list.add("Dinner");
        list.add("Bed Time");

        setContentView(R.layout.activity_food_notification);

        helper=new FoodDbAdapter(this);
        foodview=(TextView)findViewById(R.id.FoodView);

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);

        if(mHour>=8 && mHour<10)
        {
            name="Breakfast";
        }
        else if(mHour>=10 && mHour<=12) {
            name = "Morning Snacks";
        }
        else if(mHour>=16 && mHour<=18)
        {
            name="Evening Snacks";

        }
        else if(mHour>=13 && mHour<=15)
        {
            name="Lunch";
        }
        else if(mHour>=19 && mHour<=21)
        {
            name="Dinner";
        }
        else if(mHour>=22 && mHour<=7)
        {
            name="Bed Time";
        }
        data="Name  Amount\n";
        for (int i= list.indexOf(name);i>=0;i--)
        {
            store= helper.getData(list.get(i));
            if (!store.equals(""))
            {
                break;
            }
        }
        foodview.setText(data+store);
        //
    }
}
