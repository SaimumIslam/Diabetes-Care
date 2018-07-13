package com.example.foysal.diabetis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PhysicalNotificationActivity extends AppCompatActivity {

    private PhysicalDbAdapter helper;
    private String name,det;
    String data;
    private int  mHour;

    private TextView PhysicalView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_notification);
        helper=new PhysicalDbAdapter(this);

       PhysicalView=(TextView)findViewById(R.id.physicalView);

        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);

        if(mHour>=7 && mHour<9)
        {
            name="early morning";
        }
        else if(mHour>=9 && mHour<=11)
        {
            name="morning";
        }
        else if(mHour>=16 && mHour<=17)
        {
            name="afternoon";
        }
        else if(mHour>=18 && mHour<=19)
        {
            name="evening";

        }
        else if(mHour>=20 && mHour<=21)
        {
            name="night";

        }
        else if(mHour>=22 && mHour<=23) {
            name = "latenight";
        }
        data="Name    Duration     Time\n";
        det=helper.getData(name);
        data += det;
        PhysicalView.setText(data);
        //
    }
}