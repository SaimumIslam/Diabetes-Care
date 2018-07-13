package com.example.foysal.diabetis;

/**
 * Created by Foysal on 11/13/2017.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class RegisterDbAdapter {
    RegisterDbAdapter.myDbHelper myhelper;

    public RegisterDbAdapter(Context context)
    {
        myhelper = new RegisterDbAdapter.myDbHelper(context);
    }
    public long insertData(String name,String age,String weight,String hight,String email)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(RegisterDbAdapter.myDbHelper.NAME, name);
        contentValues.put(RegisterDbAdapter.myDbHelper.DOSE, age);
        contentValues.put(RegisterDbAdapter.myDbHelper.FREQ, weight);
        contentValues.put(RegisterDbAdapter.myDbHelper.TIME,hight);
        contentValues.put(RegisterDbAdapter.myDbHelper.DUR, email);

        long id = dbb.insert(RegisterDbAdapter.myDbHelper.TABLE_NAME,null ,contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {RegisterDbAdapter.myDbHelper.UID, RegisterDbAdapter.myDbHelper.NAME, RegisterDbAdapter.myDbHelper.DOSE, RegisterDbAdapter.myDbHelper.FREQ, RegisterDbAdapter.myDbHelper.TIME, RegisterDbAdapter.myDbHelper.DUR};
        //Cursor cursor =db.query(RegisterDbAdapter.myDbHelper.TABLE_NAME,columns, RegisterDbAdapter.myDbHelper.TIME+"=?",new String[] {t},null,null,null);
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(RegisterDbAdapter.myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(RegisterDbAdapter.myDbHelper.NAME));
            String  dose =cursor.getString(cursor.getColumnIndex(RegisterDbAdapter.myDbHelper.DOSE));
            String  freq =cursor.getString(cursor.getColumnIndex(RegisterDbAdapter.myDbHelper.FREQ));
            String  time =cursor.getString(cursor.getColumnIndex(RegisterDbAdapter.myDbHelper.TIME));
            String  dur =cursor.getString(cursor.getColumnIndex(RegisterDbAdapter.myDbHelper.DUR));
            buffer.append(cid+ "   " + name + "   " + dose+ "   " + freq + "   " + time +"   " + dur +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(RegisterDbAdapter.myDbHelper.TABLE_NAME , RegisterDbAdapter.myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RegisterDbAdapter.myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};

        int count =db.update(RegisterDbAdapter.myDbHelper.TABLE_NAME,contentValues, RegisterDbAdapter.myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "dfsd";    // Database Name
        private static final String TABLE_NAME = "Register";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String DOSE= "Dose";    // Column III
        private static final String FREQ= "Frequency";    // Column IV
        private static final String TIME= "Time";    // Column V
        private static final String DUR= "Duration";    // Column VI
        //private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME +" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ," + DOSE+" VARCHAR(255) ," +FREQ+" VARCHAR(255) ,"+TIME+" VARCHAR(255) ," +DUR+" VARCHAR(225));";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+NAME+" VARCHAR(255) ,"+DOSE+" VARCHAR(255) ,"+FREQ+" VARCHAR(255) ,"+TIME+
                " VARCHAR(255) ,"+DUR+" VARCHAR(225));";

        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();

            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Toast.makeText(context,"OnUpgrade",Toast.LENGTH_SHORT);
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Toast.makeText(context,""+e,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
