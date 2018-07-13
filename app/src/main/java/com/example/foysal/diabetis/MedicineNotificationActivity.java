package com.example.foysal.diabetis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MedicineNotificationActivity extends AppCompatActivity {

    private MedicineDbAdapter helper;
    private String name;
    String data;
    private int  mHour;

    private TextView medicineView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_notification);
        helper=new MedicineDbAdapter(this);

    medicineView=(TextView)findViewById(R.id.medicineView);

    final Calendar c = Calendar.getInstance();
    mHour = c.get(Calendar.HOUR_OF_DAY);


        if(mHour>=7 && mHour<9)
    {
        name="Early Morning";
    }
        else if(mHour>=9 && mHour<=11)
    {
        name="Morning";
    }
        else if(mHour>=13 && mHour<=15)
    {
        name="Noon";
    }
        else if(mHour>=16 && mHour<=17)
    {
        name="Afternoon";
    }
        else if(mHour>=18 && mHour<=19)
    {
        name="Evening";
    }
        else if(mHour>=20 && mHour<=21)
    {
        name="Night";
    }
        else if(mHour>=22 && mHour<=23) {
        name = "LateNight";
    }
        else if(mHour>=1 && mHour<=3) {
        name = "MidNight";
    }
    data="Name     Dose Frequency Time Duration(Day)\n";
    data += helper.getData(name);
        medicineView.setText(data);
    //
}
}