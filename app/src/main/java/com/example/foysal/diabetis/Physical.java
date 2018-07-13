package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class Physical extends AppCompatActivity {

    //medical frequency spinner
    private MaterialSpinner ExNames;
    private ArrayAdapter<String> adapter1;
    private List<String> ExList=new ArrayList<>();

    private MaterialSpinner LiteEx;
    private ArrayAdapter<String> adapter3;
    private List<String> LiteExList=new ArrayList<>();

    //medical time spinner;
    private MaterialSpinner PExeTime;
    private ArrayAdapter<String> adapter2;
    private List<String> PxeTimeList=new ArrayList<>();

    private ListView listView;
    private List<String> Medicinelist=new ArrayList<>();
    private ArrayAdapter list;

    private EditText Et_ExName;
    private EditText Et_Duration;
    private EditText Et_ExTime;

    private String ExName,ExDuration,ExTime;

    private Button Save,ENSave,ETSave;
    private LinearLayout FEx,ETime;

    private PhysicalDbAdapter helper;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical);

        builder = new AlertDialog.Builder(Physical.this);
        builder.setTitle("Confirm all data");

        helper=new PhysicalDbAdapter(this);

        ETime=(LinearLayout)findViewById(R.id.Etime);
        FEx=(LinearLayout)findViewById(R.id.FEx);

        listView=(ListView)findViewById(R.id.Listview);

        Et_ExName=(EditText)findViewById(R.id.PExName);
        Et_Duration=(EditText)findViewById(R.id.Duration);
        Et_ExTime=(EditText)findViewById(R.id.ExTime);

        Save=(Button)findViewById(R.id.SaveExcercise);
        ENSave=(Button)findViewById(R.id.PESave);
        ETSave=(Button)findViewById(R.id.ETSave);

        ExNames= (MaterialSpinner)findViewById(R.id.ExcrciseNames);
        PExeTime=(MaterialSpinner)findViewById(R.id.ExTimes);
        LiteEx=(MaterialSpinner)findViewById(R.id.lightExcercise);

        //list view
        list=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Medicinelist)
        {
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.BLACK);
                return view;
            }
        };
        listView.setAdapter(list);

        //spinner
        final ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,ExList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setExNames();
        ExNames.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,PxeTimeList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setExTime();
        PExeTime.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();


        final ArrayAdapter adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,LiteExList);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setLiteEx();
        LiteEx.setAdapter(adapter3);
        adapter3.notifyDataSetChanged();

        //med freq spinner
        ExNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    if(i==0)
                    {
                        FEx.setVisibility(View.VISIBLE);
                    }
                    else if(i==1)
                    {
                        LiteEx.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        FEx.setVisibility(View.GONE);
                        LiteEx.setVisibility(View.GONE);
                        ExName=ExNames.getItemAtPosition(i).toString().toLowerCase();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ExNames.setError("Please select your recomanned food");
            }
        });

        //med times
        PExeTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    if(i==0)
                    {
                        //ETime.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        ETime.setVisibility(View.GONE);
                        ExTime=PExeTime.getItemAtPosition(i).toString().toLowerCase();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PExeTime.setError("Please select your recomanned food");
            }
        });
        LiteEx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    ExName+=" "+ExNames.getItemAtPosition(i).toString().toLowerCase();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PExeTime.setError("Please select your recomanned food");
            }
        });
        //save physical
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ExDuration=Et_Duration.getText().toString();
                if(ExName.isEmpty() || ExDuration.isEmpty()||ExTime.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Name Duration and Time",Toast.LENGTH_SHORT).show();
                }
                else {
                    displayAlert("Name: "+ExName+"\nDuration: "+ExDuration+"\nTime: "
                            +ExTime);
                }


            }
        });
        //medicine time
        ETSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                ExTime=Et_ExTime.getText().toString();
                PxeTimeList.add(ExTime);
                adapter2.notifyDataSetChanged();
            }
        });
        //physical Excercise name add
        ENSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ExName=Et_ExName.getText().toString();
                ExList.add(ExName+"times");
                adapter1.notifyDataSetChanged();
            }
        });
    }

    private void setSave() {
        if(ExTime.equals("early morning"))
        {
            PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),10,7,0,0);
        }
        else if(ExTime.equals("morning"))
        {
            PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),11,9,0,0);
        }
        else if(ExTime.equals("afternoon"))
        {
            PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),12,16,0,0);
        }
        else if(ExTime.equals("evening"))
        {
            PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),13,18,0,0);
        }
        else if(ExTime.equals("night"))
        {
            PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),14,19,30,0);
        }
        else if(ExTime.equals("late night"))
        {
            PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),15,23,0,0);
        }
        addExcercise(ExName,ExDuration,ExTime);
    }


    public void addExcercise(String Name,String Duraton,String Time)
    {
        if(Name.isEmpty() || Duraton.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Both Name and Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(Name,Duraton,Time);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();
                Et_Duration.setText("");

            } else
            {
               // PhysicalNotificationEventReceiver.setupAlarm(getApplicationContext(),16,7,0,0);
                Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
                Medicinelist.add(ExName+" "+ExTime+" "+ExDuration);
                list.notifyDataSetChanged();
                Et_Duration.setText("");
            }
        }
    }

    public void setExNames()
    {
        ExList.add("others");
        ExList.add("Light activities include");
        ExList.add("Walking");
        ExList.add("Walking briskly");
        ExList.add("Riding bi-cycle");
        ExList.add("Swimming");
        ExList.add("Dancing");
        ExList.add("Playing Football/ Basketball/ Tennis/ other sports");
        ExList.add("Aerobic exercise (Yoga)");
    }
    public void setExTime()
    {
        PxeTimeList.add("others");
        PxeTimeList.add("Early Morning");
        PxeTimeList.add("Morning");
        PxeTimeList.add("Afternoon");
        PxeTimeList.add("Evening");
        PxeTimeList.add("Night");
        PxeTimeList.add("Late Night");
    }


    public void setLiteEx()
    {
        LiteExList.add("leg lifts or extensions");
        LiteExList.add("overhead arm stretches");
        LiteExList.add("desk chair swivels");
        LiteExList.add("torso twists");
        LiteExList.add("side lunges");
        LiteExList.add("walking in place");
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
