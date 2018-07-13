package com.example.foysal.diabetis;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private Button btnDatePicker,Reg;
    private EditText txtDate;
    private EditText Age_text;
    AlertDialog.Builder builder;

    private TextInputLayout Name,Fname,Mname,Sname,Address,Citygenship,Weight,Height,Email;

    private String name="",fName,mName,sName,address,citygen,date,age="",weight="",height="",email="";
    private int mYear, mMonth, mDay;
    private int agee = 0;

    private RegisterDbAdapter helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        helper=new RegisterDbAdapter(this);

        builder = new AlertDialog.Builder(RegisterActivity.this);

        txtDate=(EditText)findViewById(R.id.in_date);
        Age_text=(EditText)findViewById(R.id.in_age);
        btnDatePicker=(Button)findViewById(R.id.btn_date);
        Reg=(Button)findViewById(R.id.RegCreate);

        Name=(TextInputLayout)findViewById(R.id.Name);
        Fname=(TextInputLayout)findViewById(R.id.FatherName);
        Mname=(TextInputLayout)findViewById(R.id.MotherName);
        Sname=(TextInputLayout)findViewById(R.id.SpouseName);
        Address=(TextInputLayout)findViewById(R.id.Address);
        Citygenship=(TextInputLayout)findViewById(R.id.Cityzenship);
        Weight=(TextInputLayout)findViewById(R.id.Weight);
        Height=(TextInputLayout)findViewById(R.id.Height);
        Email=(TextInputLayout)findViewById(R.id.Email);


        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR)-1;
                mMonth = c.get(Calendar.MONTH);
                mDay=c.get(Calendar.DAY_OF_YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                if(year/1000==2)
                                {
                                    agee=(mYear-year)+1;
                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+" / "+agee);
                                }
                                else
                                {
                                    agee=(mYear-2000)+(2000-year);
                                    txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year+" / "+agee);
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=Name.getEditText().getText().toString();
                fName=Fname.getEditText().getText().toString();
                mName=Mname.getEditText().getText().toString();
                sName=Sname.getEditText().getText().toString();
                address=Address.getEditText().getText().toString();
                citygen=Citygenship.getEditText().getText().toString();
                age= String.valueOf(agee);
                weight=Weight.getEditText().getText().toString();
                height=Height.getEditText().getText().toString();
                email=Email.getEditText().getText().toString();
                date=txtDate.getText().toString();

                Register(name,age,weight,height,email);
            }
        });

    }
    public void Register(String N,String age,String weight,String height,String email)
    {
        if(N.isEmpty() || age.isEmpty()||weight.isEmpty()||height.isEmpty()||email.isEmpty())
        {
            builder.setTitle("Please  Fill All Things");
            displayAlert("Fill  Necessary Somes");
        }
        else
        {
            long id = helper.insertData(N,age,weight,height,email);
            if(id<=0)
            {
                RegisterActivity.this.finish();
                Toast.makeText(getApplicationContext(),"Register Successful",Toast.LENGTH_SHORT).show();
                Name.getEditText().setText("");
                Age_text.setText("");
                Weight.getEditText().setText("");
                Height.getEditText().setText("");
                Email.getEditText().setText("");

            }
            else
            {
                RegisterActivity.this.finish();
                Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void onBackPressed() {
        finishAffinity();
    }
}
