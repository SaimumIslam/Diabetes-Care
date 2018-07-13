package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
public class Medicine extends AppCompatActivity {

    //medical frequency spinner
    private MaterialSpinner MedFreq;
    private ArrayAdapter<String> adapter1;
    private List<String> MFreList=new ArrayList<>();

    //medical time spinner;
    private MaterialSpinner MedTime;
    private ArrayAdapter<String> adapter2;
    private List<String> MTimeList=new ArrayList<>();

    private ListView listView;
    private List<String> Medicinelist=new ArrayList<>();
    private ArrayAdapter list;

    private EditText Et_MdName;
    private EditText Et_MdDose;
    private EditText Et_MdFrequency;
    private EditText Et_Duration;
    private EditText Et_MdTime;


    private CheckBox VeryMorning;
    private CheckBox Morning;
    private CheckBox Noon;
    private CheckBox AfterNoon;
    private CheckBox Evening;
    private CheckBox Night;
    private CheckBox LateNight;
    private CheckBox MidNight;

    private String MedicineName="",MedicineDose="",MedicineFrequency="",MedicineDuration="",MedicineTime="";

    private Button Save,MFSave,MTSave;
    private LinearLayout MFre,MTime;

    private MedicineDbAdapter helper;
    private int check=0;

    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        builder = new AlertDialog.Builder(Medicine.this);
        builder.setTitle("Confirm all data");

        helper=new MedicineDbAdapter(this);

        VeryMorning=(CheckBox)findViewById(R.id.VeryMorning);
        Morning=(CheckBox)findViewById(R.id.Morning);
        Noon=(CheckBox)findViewById(R.id.Noon);
        AfterNoon=(CheckBox)findViewById(R.id.Afternoon);
        Evening=(CheckBox)findViewById(R.id.Evening);
        Night=(CheckBox)findViewById(R.id.Night);
        LateNight=(CheckBox)findViewById(R.id.LateNight);
        MidNight=(CheckBox)findViewById(R.id.MidNight);


        MTime=(LinearLayout)findViewById(R.id.Mtime);
        MFre=(LinearLayout)findViewById(R.id.MFre);

        listView=(ListView)findViewById(R.id.Listview);


        Et_MdName=(EditText)findViewById(R.id.MedicineName);
        Et_MdDose=(EditText)findViewById(R.id.MedicineDose);
        Et_MdFrequency=(EditText)findViewById(R.id.MedicineFrquency);
        Et_Duration=(EditText)findViewById(R.id.DoseDay);
        Et_MdTime=(EditText)findViewById(R.id.MedicineTime);

        Save=(Button)findViewById(R.id.SaveMedicine);
        MFSave=(Button)findViewById(R.id.MFSave);
        MTSave=(Button)findViewById(R.id.MTSave);

        MedFreq= (MaterialSpinner)findViewById(R.id.MedicineFs);
        MedTime=(MaterialSpinner)findViewById(R.id.MedicineTimes);

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
        final ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,MFreList);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setMedFreq();
        MedFreq.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,MTimeList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setMedTime();
        MedTime.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        //med freq spinner
        MedFreq.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    if(i==0)
                    {
                        //MFre.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        MFre.setVisibility(View.GONE);
                        MedicineFrequency=MedFreq.getItemAtPosition(i).toString().toLowerCase();
                    }
                    check=i;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                MedFreq.setError("Please select your recomanned food");
            }
        });

        //med times
        MedTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    if(i==0)
                    {
                        //MTime.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        MTime.setVisibility(View.GONE);
                        MedicineTime=MedTime.getItemAtPosition(i).toString().toLowerCase();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                MedTime.setError("Please select your recomanned food");
            }
        });


        //for checkbox
        VeryMorning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    check--;
                    //checked
                } else {
                    check++;
                    //not checked
                }

            }
        });
        Morning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });
        Noon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });
        AfterNoon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });
        Evening.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });
        Night.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });
        LateNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });
        MidNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    //checked
                    check--;
                } else {
                    //not checked
                    check++;
                }

            }
        });

        //save medicine
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MedicineDose=Et_MdDose.getText().toString();
                MedicineName=Et_MdName.getText().toString();
                MedicineDuration=Et_Duration.getText().toString();
                 if(MedicineName.isEmpty() || MedicineDose.isEmpty()||MedicineFrequency.isEmpty()||MedicineDuration.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Plesase Enter Name,Dose,Frequency,Duration",Toast.LENGTH_SHORT).show();
                }
                else if(check!=0)
                {
                    Toast.makeText(Medicine.this,"your frequency and  time is not same"+check,Toast.LENGTH_SHORT).show();
                }
                else
                {
                    displayAlert("Name: "+MedicineName+"\nDose: "+MedicineDose+"\nDuration: "+MedicineDuration+"\nFrequency: "
                            +MedicineFrequency);
                    //addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
                }

            }
        });
        //medicine time
        MTSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                MedicineTime=Et_MdTime.getText().toString();
                MTimeList.add(MedicineTime);
                adapter2.notifyDataSetChanged();
            }
        });
        //medincine frequency add
        MFSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MedicineFrequency=Et_MdFrequency.getText().toString();
                check= Integer.parseInt(MedicineFrequency);
                MFreList.add(MedicineFrequency+"times");
                adapter1.notifyDataSetChanged();
            }
        });
    }

    private void setSave() {
        if(VeryMorning.isChecked())
        {
            MedicineTime="Early Morning";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),20,7,0,0);
        }
        if(Morning.isChecked())
        {
            MedicineTime="Morning";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),21,10,0,0);
        }
        if(Noon.isChecked())
        {
            MedicineTime="Noon";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),22,13,0,0);
        }
        if(AfterNoon.isChecked())
        {
            MedicineTime="AfterNoon";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),23,16,0,0);
        }
        if(Evening.isChecked())
        {
            MedicineTime="Evening";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),24,19,0,0);
        }
        if(Night.isChecked())
        {
            MedicineTime="Night";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),25,21,0,0);
        }
        if(LateNight.isChecked())
        {
            MedicineTime="LateNight";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),26,23,0,0);
        }
        if(MidNight.isChecked())
        {
            MedicineTime="MidNight";
            addMedicine(MedicineName,MedicineDose,MedicineFrequency,MedicineTime,MedicineDuration);
            MedicineNotificationEventReceiver.setupAlarm(getApplicationContext(),27,1,30,0);
        }
        Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();

    }

    public void addMedicine(String Name,String Dose,String Freq,String Time,String Duration)
    {
        if(Name.isEmpty() || Dose.isEmpty()||Freq.isEmpty()||Time.isEmpty()||Duration.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Plesase Enter Name,Dose,Frequency,Duration",Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(Name,Dose,Freq,Time,Duration);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();
                Et_MdDose.setText("");
                Et_MdName.setText("");
                Et_Duration.setText("");
            } else
            {
                Et_MdDose.setText("");
                Et_MdName.setText("");
                Et_Duration.setText("");
            }

            Medicinelist.add(Name+" "+Dose+" "+Freq+" "+Time+" "+Duration);
            list.notifyDataSetChanged();
        }
    }
    public void setMedFreq()
    {
        MFreList.add("others");
        MFreList.add("1 time");
        MFreList.add("2 times");
        MFreList.add("3 times");
        MFreList.add("4 times");
        MFreList.add("5 times");
        MFreList.add("6 times");
        MFreList.add("7 times");
    }
    //this is not used
    public void setMedTime()
    {
        /*
        MTimeList.add("others");
        MTimeList.add("Morning");
        MTimeList.add("Late Morning");
        MTimeList.add("Noon");
        MTimeList.add("Afternoon");
        MTimeList.add("Evening");
        MTimeList.add("Night");
        MTimeList.add("Late Night");
        */
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
