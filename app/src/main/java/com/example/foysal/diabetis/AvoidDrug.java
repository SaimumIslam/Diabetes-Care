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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class AvoidDrug extends AppCompatActivity {

    private MaterialSpinner Time;
    private ArrayAdapter<String> adapter;
    private List<String> TimeList=new ArrayList<>();

    private EditText Name;
    private String time;
    private Button save;

    private AvoidDrugDbAdapter helper;

    private String name;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avoid_drug);

        builder = new AlertDialog.Builder(AvoidDrug.this);
        builder.setTitle("Confirm all data");

        helper=new AvoidDrugDbAdapter(this);

        save=(Button)findViewById(R.id.save);
        Name=(EditText)findViewById(R.id.drug);


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
                    time=Time.getItemAtPosition(i).toString().toLowerCase();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Time.setError("Please select your recomanned food");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=Name.getText().toString();
                if(name.isEmpty()||time.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please Fill Name and Time",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    displayAlert("Name : "+name+" \nTime: "+time);

                }
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        finish();
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        super.onOptionsItemSelected(item);
        return true;
    }
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
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
    public void addAvoid(String Name,String Amount,String Time)
    {
        long id = helper.insertData(Name,Amount,Time);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
            }
    }
    public  void setSave()
    {
        addAvoid(name,"noting",time);
        if(time.equals("early morning"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),50,7,0,0);
        }
        else if(time.equals("morning"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),51,10,0,0);
        }
        else if(time.equals("noon"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),51,13,0,0);
        }
        else if(time.equals("afternoon"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),52,16,0,0);
        }
        else if(time.equals("evening"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),53,18,0,0);
        }
        else if(time.equals("night"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),54,20,0,0);
        }
        else if(time.equals("late night"))
        {
            AvoidDrugNotificationEventReceiver.setupAlarm(getApplicationContext(),55,22,0,0);
        }
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
}