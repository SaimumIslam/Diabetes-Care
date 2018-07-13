package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class HIFollowUpVisit extends AppCompatActivity {

    private EditText Interval,Date;
    private Button save,next;

    private Button btnDatePicker;
    private int mYear, mMonth, mDate,mDay;

    private HIFollowUpDbAdapter helper;
    int date;
    private String interval;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hifollow_up_visit);

        builder = new AlertDialog.Builder(HIFollowUpVisit.this);
        builder.setTitle("Confirm all data");

        helper=new HIFollowUpDbAdapter(this);

        next=(Button)findViewById(R.id.Next);
        save=(Button)findViewById(R.id.save);

        Interval=(EditText)findViewById(R.id.Interval);
        Date=(EditText)findViewById(R.id.Date);


        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDate = c.get(Calendar.DAY_OF_MONTH);
                mDay=c.get(Calendar.DAY_OF_YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(HIFollowUpVisit.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date=dayOfMonth;
                                Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interval=Interval.getText().toString();
                if(String.valueOf(date).isEmpty()||interval.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Please set Date and Interval",Toast.LENGTH_SHORT).show();
                }
                else {

                    displayAlert("Date: "+date+"\nInterval: "+interval);
                }
                //Toast.makeText(getApplicationContext(),"Successfull",Toast.LENGTH_LONG).show();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void addDate(String Name,String Amount)
    {
        if(Name.isEmpty() || Amount.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Both Name and Password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(Name,Amount);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();

            } else
            {
                Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
            }
        }
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
                addDate(String.valueOf(date),interval);
                HIFollowUpNotificationEventReceiver.setupAlarm(getApplicationContext(),34,date,8,0);
                HIFollowUpNotificationEventReceiver.setupAlarm(getApplicationContext(),35,date-1,8,0);
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
