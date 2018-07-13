package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

     private  Button foodbtn,medicinebtn,excercisebtn,helthInform;
     private Button avoid,glucosebtn;
     private Button FootCare;

     private RegisterDbAdapter helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        helper=new RegisterDbAdapter(this);
        if(helper.getData()=="")
        {
            Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        }
        setContentView(R.layout.activity_main);


        foodbtn=(Button)findViewById(R.id.Foodbtn);
        medicinebtn=(Button)findViewById(R.id.Medicinebtn);
        excercisebtn=(Button)findViewById(R.id.Physicalbtn);
        helthInform=(Button)findViewById(R.id.HelthInformbtn);
        avoid=(Button)findViewById(R.id.BGRECbtn);
        glucosebtn=(Button)findViewById(R.id.Glucosebtn);
        FootCare=(Button)findViewById(R.id.FootCare);


        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Food.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });

        medicinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Medicine.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });
        excercisebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Physical.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


            }
        });
        helthInform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,HIFollowUpVisit.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        glucosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,BloodGlucoseActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
        avoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AvoidDrug.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
        FootCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,FootCareActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent=new Intent(MainActivity.this,UserGuideActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        super.onOptionsItemSelected(item);
        return true;
    }

    public void onBackPressed() {
        final AlertDialog.Builder exitbuilder = new AlertDialog.Builder(MainActivity.this);
        exitbuilder.setTitle("Attention");
        exitbuilder.setMessage("Want To Exit?!!");
        exitbuilder.setCancelable(true);
        exitbuilder.setIcon(R.drawable.ic_exit_to_app_black_24dp);

        exitbuilder.setPositiveButton("YES, Exit",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        exitbuilder.setNegativeButton("NO, i don't", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog mydialog=exitbuilder.create();
        mydialog.show();
    }

}
