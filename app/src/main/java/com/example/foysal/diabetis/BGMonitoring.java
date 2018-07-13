package com.example.foysal.diabetis;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class BGMonitoring extends AppCompatActivity {

    private MaterialSpinner MnTime;
    private ArrayAdapter<String> adapter2;
    private List<String> MTimeList=new ArrayList<>();


    private EditText Et_MnDate;
    private EditText Et_MnDuration;
    private EditText ET_MnTime;

    private String AdDate="",AdDuration="",MonieringTime="";

    private Button Save,Download,MTSave;
    private LinearLayout MTime;

    //for pdf
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    private Button btnDatePicker;
    private int mYear, mMonth, mDate,mDay;
    private int date;
    private int time=0;

    AlertDialog.Builder builder;

    //for db
    private BGRecordFormDbAdapter helper;
    private BGMonitoringDbAdapter help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bgmonitoring);
        builder = new AlertDialog.Builder(BGMonitoring.this);
        builder.setTitle("Confirm all data");

        helper=new BGRecordFormDbAdapter(this);
        help=new BGMonitoringDbAdapter(this);

        MTime=(LinearLayout)findViewById(R.id.Mtime);


        Et_MnDate=(EditText)findViewById(R.id.Date);
        Et_MnDuration=(EditText)findViewById(R.id.AdDuration);
        ET_MnTime=(EditText)findViewById(R.id.MonieringTime);

        Save=(Button)findViewById(R.id.SaveMedicine);
        Download=(Button)findViewById(R.id.download);
        MTSave=(Button)findViewById(R.id.MTSave);

        MnTime=(MaterialSpinner)findViewById(R.id.MonieringTimes);

        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,MTimeList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setMnTime();
        MnTime.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();


        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDate = c.get(Calendar.DAY_OF_MONTH);
                mDay=c.get(Calendar.DAY_OF_YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(BGMonitoring.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                date=dayOfMonth;
                                Et_MnDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });
        //med times
        MnTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        MonieringTime=MnTime.getItemAtPosition(i).toString().toLowerCase();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                MnTime.setError("Please select your recomanned food");
            }
        });
        //save medicine
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AdDuration=Et_MnDuration.getText().toString();
                AdDate=Et_MnDate.getText().toString();

                if(AdDuration.isEmpty() || AdDate.isEmpty()||MonieringTime.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter Date,Time and Duration",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    displayAlert("Date : "+AdDate+"\nDuration  "+AdDuration+"\nTime: "+MonieringTime);
                }
                //Toast.makeText(getApplicationContext(),AdDate+" "+AdDuration+" "+MonieringTime,Toast.LENGTH_LONG).show();
            }
        });
        //medicine time
        MTSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                MonieringTime=ET_MnTime.getText().toString();
                MTimeList.add(MonieringTime);
                adapter2.notifyDataSetChanged();
            }
        });
        //download
        Download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "Your  Blood monitoring history\n\n";
                fn_permission();
                if(!helper.getData().equals(""))
                {
                    data+= helper.getData();
                }
                else
                {
                    data+="please set your history first";
                }
                createandDisplayPdf(data);

            }
        });
    }

    private void setSave() {
        if(MonieringTime.equals("morning"))
        {
            time=7;
        }
        else if(MonieringTime.equals("late morning"))
        {
            time=10;
        }
        else if(MonieringTime.equals("noon"))
        {
            time=12;
        }
        else if(MonieringTime.equals("afternoon"))
        {
            time=15;
        }
        else if(MonieringTime.equals("evening"))
        {
            time=17;
        }
        else if(MonieringTime.equals("night"))
        {
            time=18;
        }
        BloodGlucoseNotificationEventReceiver.setupAlarm(getApplicationContext(),30,date-1,8,0);
        BloodGlucoseNotificationEventReceiver.setupAlarm(getApplicationContext(),31,date,time,0);
        BGMonitoring(String.valueOf(date),AdDuration, String.valueOf(time));

    }

    public void BGMonitoring(String DName,String Amount,String Rtime)
    {
        if(DName.isEmpty() || Amount.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Enter Both Date and Time",Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = help.insertData(DName,Amount,Rtime);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();
                Et_MnDuration.setText("");
                Et_MnDate.setText("");

            } else
            {
                Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setMnTime()
    {
        MTimeList.add("others");
        MTimeList.add("Morning");
        MTimeList.add("Late Morning");
        MTimeList.add("Noon");
        MTimeList.add("Afternoon");
        MTimeList.add("Evening");
        MTimeList.add("Night");
    }

    public void createandDisplayPdf(String text) {

        Document doc = new Document();

        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, "newFile.pdf");
            FileOutputStream fOut = new FileOutputStream(file);

            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

            Paragraph p1 = new Paragraph(text);
            Font paraFont= new Font();
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.setFont(paraFont);
            //add paragraph to document
            doc.add(p1);

        } catch (DocumentException de) {
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Log.e("PDFCreator", "ioException:" + e);
        } finally {
            doc.close();
        }

        viewPdf("newFile.pdf", "Dir");
    }

    // Method for opening a pdf file
    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(BGMonitoring.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(BGMonitoring.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(BGMonitoring.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(BGMonitoring.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(BGMonitoring.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent newIntent = new Intent(BGMonitoring.this,MainActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);
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

}
