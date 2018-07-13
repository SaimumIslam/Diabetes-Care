package com.example.foysal.diabetis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Food extends AppCompatActivity {

    private LinearLayout sec1,sec2;
    private Button next5,next6;
    private TextView breakfast,morningSnacks,lunch,eveningSnacks,dinner,bedtime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        sec1=(LinearLayout)findViewById(R.id.sec1);
        sec2=(LinearLayout)findViewById(R.id.sec2);

        next5=(Button)findViewById(R.id.nxt5);
        next6=(Button)findViewById(R.id.nxt6);

        breakfast=(TextView) findViewById(R.id.breakfast);
        morningSnacks=(TextView)findViewById(R.id.mornigSnacks);
        lunch=(TextView)findViewById(R.id.lunch);
        eveningSnacks=(TextView)findViewById(R.id.evening);
        dinner=(TextView)findViewById(R.id.dinner);
        bedtime=(TextView)findViewById(R.id.bedtime);


        next5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sec2.setVisibility(View.VISIBLE);
                bedtime.setVisibility(View.GONE);
                view.setBackgroundResource(R.color.colorPrimary);
                next6.setBackgroundResource(android.R.color.darker_gray);

            }
        });
        next6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sec2.setVisibility(View.VISIBLE);
                bedtime.setVisibility(View.VISIBLE);
                view.setBackgroundResource(R.color.colorPrimary);
                next5.setBackgroundResource(android.R.color.darker_gray);
            }
        });

        breakfast.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {


                breakfast.setTextColor(R.color.text);
                Intent foodIntent=new Intent(Food.this,FoodSelect.class);
                foodIntent.putExtra("FoodTime","Breakfast");
                startActivity(foodIntent);

            }
        });

        morningSnacks.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                morningSnacks.setTextColor(R.color.text);
                Intent foodIntent=new Intent(Food.this,FoodSelect.class);
                foodIntent.putExtra("FoodTime","Morning Snacks");
                startActivity(foodIntent);
            }
        });

        eveningSnacks.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                eveningSnacks.setTextColor(R.color.text);
                Intent foodIntent=new Intent(Food.this,FoodSelect.class);
                foodIntent.putExtra("FoodTime","Evening Snacks");
                startActivity(foodIntent);

            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                lunch.setTextColor(R.color.text);
                Intent foodIntent=new Intent(Food.this,FoodSelect.class);
                foodIntent.putExtra("FoodTime","Lunch");
                startActivity(foodIntent);
            }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                dinner.setTextColor(R.color.text);
                Intent foodIntent=new Intent(Food.this,FoodSelect.class);
                foodIntent.putExtra("FoodTime","Dinner");
                startActivity(foodIntent);

            }
        });

        bedtime.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                bedtime.setTextColor(R.color.text);
                Intent foodIntent=new Intent(Food.this,FoodSelect.class);
                foodIntent.putExtra("FoodTime","Bed Time");
                startActivity(foodIntent);
            }
        });
    }

    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
