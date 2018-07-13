package com.example.foysal.diabetis;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class BGRecordForm extends AppCompatActivity {

    private Button btnDatePicker;
    private int mYear, mMonth, mDate,mDay;

    private EditText Date;
    private EditText Value;
    private EditText Time;

    private Button Save,Add;

    private MaterialSpinner PExeTime;
    private ArrayAdapter<String> adapter2;
    private List<String> PxeTimeList=new ArrayList<>();

    private String rtime="",date="",value="";

    LinearLayout ETime;

    AlertDialog.Builder builder;
    private BGRecordFormDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgrecord_form);

        builder = new AlertDialog.Builder(BGRecordForm.this);
        builder.setTitle("Confirm all data");

        helper=new BGRecordFormDbAdapter(this);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDate = c.get(Calendar.DAY_OF_MONTH);
                mDay=c.get(Calendar.DAY_OF_YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(BGRecordForm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

        Date=(EditText)findViewById(R.id.Date);
        Value=(EditText)findViewById(R.id.value);
        Time=(EditText)findViewById(R.id.time);

        Save=(Button)findViewById(R.id.save);
        Add=(Button)findViewById(R.id.add);

        ETime=(LinearLayout)findViewById(R.id.Etime);

        PExeTime=(MaterialSpinner)findViewById(R.id.recordedtime);

        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,PxeTimeList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setExTime();
        PExeTime.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

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
                        rtime=PExeTime.getItemAtPosition(i).toString().toLowerCase();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                PExeTime.setError("Please select your recomanned food");
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rtime=Time.getText().toString();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date=Date.getText().toString();
                value=Value.getText().toString();

                if(date.equals("")||value.equals("")||rtime.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Filled up all",Toast.LENGTH_LONG).show();

                }
                else
                {
                    displayAlert("Date : "+date+"\nValue :  "+value+"\nTime: "+rtime);
                }
            }
        });
    }

    public void BGRecordForm(String DName,String Amount,String Rtime)
    {
        if(DName.isEmpty() || Amount.isEmpty()||Rtime.isEmpty())
        {
            //Toast.makeText(getApplicationContext(),"Enter Both Name and Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(DName,Amount,Rtime);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();
                Date.setText("");
                Time.setText("");
                Value.setText("");

            } else
            {
                Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void setExTime()
    {
        PxeTimeList.add("others");
        PxeTimeList.add("Morning Before meal");
        PxeTimeList.add("Morning After meal");
        PxeTimeList.add("Late Morning");
        PxeTimeList.add("Noon before lunch");
        PxeTimeList.add("Noon after lunch");
        PxeTimeList.add("Afternoon");
        PxeTimeList.add("Evening");
        PxeTimeList.add("Night Before meal");
        PxeTimeList.add("Night after meal");
        PxeTimeList.add("Late Night");
    }
    public void displayAlert(final String code) {
        builder.setMessage(code);
        builder.setCancelable(true);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                BGRecordForm(date,value,rtime);
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