package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FootCareActivity extends AppCompatActivity {

    private MaterialSpinner Time;
    private ArrayAdapter<String> adapter;
    private List<String> TimeList=new ArrayList<>();

    private CheckBox Yes;
    private CheckBox AdYes;

    private Button Save;

    String NTime;
    String y="",Ay="";
    private FootCareDbAdapter helper;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_care);

        builder = new AlertDialog.Builder(FootCareActivity.this);
        builder.setTitle("Confirm all data");

        helper=new FootCareDbAdapter(this);

        Yes=(CheckBox)findViewById(R.id.yes);
        AdYes=(CheckBox)findViewById(R.id.AdditionalYes);

        Save=(Button)findViewById(R.id.Save);

        Time=(MaterialSpinner)findViewById(R.id.Time);

        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,TimeList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setTime();
        Time.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    NTime=Time.getItemAtPosition(i).toString().toLowerCase();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Time.setError("Please select your recomanned time");
            }
        });
        

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Yes.isChecked())
                {
                    y="yes";
                }
                if(AdYes.isChecked()&& Yes.isChecked())
                {
                    Ay="yes";
                }
                if(y.isEmpty()||NTime.isEmpty())
                {
                    Toast.makeText(FootCareActivity.this,"Please set time and checkbox",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    displayAlert("Time : "+NTime);
                }
                //db
            }
        });


    }

    public void FootCare(String YWant,String Aditional,String Rtime)
    {
        long id = helper.insertData(YWant,Aditional,Rtime);
        if(id<=0)
        {
            Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();

        } else
        {
            Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
        }
    }
    public void setTime()
    {
        TimeList.add("others");
        TimeList.add("Early Morning");
        TimeList.add("Morning");
        TimeList.add("Noon");
        TimeList.add("Afternoon");
        TimeList.add("Evening");
        TimeList.add("Night");
        TimeList.add("Late Night");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        super.onOptionsItemSelected(item);
        return true;
    }
    public void displayAlert(final String code) {
        builder.setMessage(code);
        builder.setCancelable(true);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                setSave();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        AlertDialog alertDialo = builder.create();
        alertDialo.show();

    }
    private void setSave() {
        if(NTime.equals("early morning"))
        {
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),10,7,0,0);
        }
        else if(NTime.equals("morning"))
        {
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),11,9,0,0);
        }
        else if(NTime.equals("afternoon"))
        {
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),12,16,0,0);
        }
        else if(NTime.equals("evening"))
        {
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),13,18,0,0);
        }
        else if(NTime.equals("night"))
        {
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),14,19,30,0);
        }
        else if(NTime.equals("late night"))
        {
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),15,23,0,0);
        }
        else
        {
            FootCare(y,Ay,NTime);
            FootCareNotificationEventReceiver.setupAlarm(getApplicationContext(),40,7,30,0);

        }
    }

}
