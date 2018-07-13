package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FoodSelect extends AppCompatActivity {

    private MaterialSpinner selectFood;
    private ArrayAdapter<String> adapter1;
    private List<String> foodlist=new ArrayList<>();

    private MaterialSpinner selectAmount;
    private ArrayAdapter<String> adapter2;
    private List<String> Amountlist=new ArrayList<>();

    private EditText Et_food,Et_amount;
    private  Button add,next;
    private TextView Title;

    private  String foodName="",setfood="",foodAmount="",selectedAmount="";
    private boolean check=false;
    String name;

    private FoodDbAdapter helper;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodselect);

        builder = new AlertDialog.Builder(FoodSelect.this);
        builder.setTitle("Confirm all data");

        helper=new FoodDbAdapter(this);

        name=this.getIntent().getStringExtra("FoodTime");

        setAmount();
        Title=(TextView)findViewById(R.id.PageTitle);
        add=(Button)findViewById(R.id.add);
        next=(Button)findViewById(R.id.saveFood);

        Et_food=(EditText)findViewById(R.id.addFood);
        Et_amount=(EditText)findViewById(R.id.foodAmount);

        selectFood= (MaterialSpinner) findViewById(R.id.selectedFood);
        final ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,foodlist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectFood.setAdapter(adapter1);;
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    String data = helper.getData(name);
                    //Toast.makeText(getApplicationContext(),data,Toast.LENGTH_SHORT).show();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                startActivity(new Intent(FoodSelect.this,FoodNotificationActivity.class));
            }
        });



        selectAmount= (MaterialSpinner) findViewById(R.id.selectedAmount);
        final ArrayAdapter adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,Amountlist);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectAmount.setAdapter(adapter2);;

        selectAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    Et_amount.setVisibility(View.VISIBLE);
                    selectedAmount=selectAmount.getItemAtPosition(i).toString().toLowerCase();
                }
                else
                {
                    Et_amount.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectFood.setError("Please select your recomanned food");
            }
        });

        if(name.equals("Breakfast"))
        {
            Title.setText("Breakfast");
            setBreakfast();
            adapter1.notifyDataSetChanged();
        }
        else if(name.equals("Morning Snacks"))
        {
            Title.setText("Morning Snacks");
            setMorningSnacs();
            adapter1.notifyDataSetChanged();
        }
        else if(name.equals("Evening Snacks"))
        {
            Title.setText("Evening Snack");
            setEveningSnacks();
            adapter1.notifyDataSetChanged();
        }
        else if(name.equals("Lunch"))
        {
            Title.setText("Lunch");
            setLunch();
            adapter1.notifyDataSetChanged();
        }
        else if(name.equals("Dinner"))
        {
            Title.setText("Dinner");
            setDinner();
            adapter1.notifyDataSetChanged();
        }
        else if(name.equals("Bed Time"))
        {
            Title.setText("Bed Time");
            setlatenight();
            adapter1.notifyDataSetChanged();
        }
        selectFood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=-1)//hint
                {
                    if(i==0)
                    {
                        Et_food.setVisibility(View.VISIBLE);
                        check=true;
                    }
                    else
                        {
                            Et_food.setVisibility(View.GONE);
                        foodName=selectFood.getItemAtPosition(i).toString().toLowerCase();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectFood.setError("Please select your recomanned food");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                setfood=Et_food.getText().toString();
                foodAmount=Et_amount.getText().toString();
                foodlist.add(setfood);
                adapter1.notifyDataSetChanged();
                if(check)
                {
                    foodName=setfood;
                }
                if(foodName.isEmpty() || foodAmount.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Select Food and Amount",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    displayAlert("Name : "+foodName+"\nAmount : ");
                }

                //inserting into db
                //Toast.makeText(getApplicationContext(),foodName+" "+foodAmount,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSave()
    {
        if(name.equals("Breakfast"))
        {
            FoodNotificationEventReceiver.setupAlarm(getApplicationContext(),0,8,0,0);
        }
        else if(name.equals("Morning Snacks"))
        {
            FoodNotificationEventReceiver.setupAlarm(getApplicationContext(),1,11,0,0);
        }
        else if(name.equals("Evening Snacks"))
        {
            FoodNotificationEventReceiver.setupAlarm(getApplicationContext(),2,17,0,0);
        }
        else if(name.equals("Lunch"))
        {
            FoodNotificationEventReceiver.setupAlarm(getApplicationContext(),3,13,0,0);
        }
        else if(name.equals("Dinner"))
        {
            FoodNotificationEventReceiver.setupAlarm(getApplicationContext(),4,19,30,0);
        }
        else if(name.equals("Bed Time"))
        {
            FoodNotificationEventReceiver.setupAlarm(getApplicationContext(),5,23,0,0);
        }
        addFood(foodName,foodAmount+"  "+selectedAmount,name);
    }

    public void addFood(String Name,String Amount,String Time)
    {
        if(Name.isEmpty() || Amount.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Select Food and Amount",Toast.LENGTH_SHORT).show();
        }
        else
        {
            long id = helper.insertData(Name,Amount,Time);
            if(id<=0)
            {
                Toast.makeText(getApplicationContext(),"Insertion Unsuccessful",Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getApplicationContext(),"Insertion Successful",Toast.LENGTH_SHORT).show();
                Et_food.setText("");
                Et_amount.setText("");
            }
        }
    }

    public void setBreakfast()
    {
        foodlist.add("Others");
        foodlist.add("Bread");
        foodlist.add("Ruti (Whole grain)");
        foodlist.add("Chapati");
        foodlist.add("Margarine");
        foodlist.add("Egg");
        foodlist.add("Dal");
        foodlist.add("Vegetable");
        foodlist.add("Juice");
    }
    public void setMorningSnacs()
    {
        foodlist.add("Others");
        foodlist.add("Biscuit");
        foodlist.add("Puffed rice");
        foodlist.add("Khoi");
        foodlist.add("Tea/ Coffee");
        foodlist.add("Low fat milk");
    }
    public void setLunch()
    {
        foodlist.add("Others");
        foodlist.add("Steamed Rice");
        foodlist.add("Fish");
        foodlist.add("Meat (Chicken/Beef/Mutton/Pork/others)");
        foodlist.add("Dal");
        foodlist.add("Vegetable");
        foodlist.add("Sandwich");
        foodlist.add("Bread");
        foodlist.add("Mayonnaise");
        foodlist.add("Salad (Lettuce, Tomato, Cucumber etc) ");
        foodlist.add("Sugar Cookies");
        foodlist.add("Low fat milk");
    }
    public void setEveningSnacks()
    {
        foodlist.add("Others");
        foodlist.add("Biscuit");
        foodlist.add("Khoi");
        foodlist.add("Tea/ Coffee");
        foodlist.add("Puffed rice");

    }
    public void  setDinner()
    {
        foodlist.add("Others");
        foodlist.add("Steamed Rice");
        foodlist.add("Fish");
        foodlist.add("Meat (Chicken/Beef/Mutton/Pork/others)");
        foodlist.add("Dal");
        foodlist.add("Vegetable");
        foodlist.add("Gravy Apple Pie");
        foodlist.add("Bread");
        foodlist.add("Mashed Potato");
        foodlist.add("Chapati");
        foodlist.add("Ruti (Whole grain)");
        foodlist.add("Coleslaw");
        foodlist.add("Lemonade");
    }
    public void setlatenight()
    {
        foodlist.add("Others");
        foodlist.add("Low fat milk");
    }

    public void setAmount()
    {
        Amountlist.add("quantity in numbers");
        Amountlist.add("small bowl");
        Amountlist.add("medium bowl");
        Amountlist.add("big bowl");
        Amountlist.add("spoon");
        Amountlist.add("glass");
        Amountlist.add("cup");
        Amountlist.add("plate");
        Amountlist.add("others");
    }

    public Context getContext()
    {
        return FoodSelect.this;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.skip,menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent newIntent = new Intent(FoodSelect.this,MainActivity.class);
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