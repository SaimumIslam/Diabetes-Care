package com.example.foysal.diabetis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Foysal on 11/17/2017.
 */

public class FootCareDbAdapter {
    FootCareDbAdapter.myDbHelper myhelper;
    public FootCareDbAdapter(Context context)
    {
        myhelper = new FootCareDbAdapter.myDbHelper(context);
    }
    public long insertData(String date, String amount,String time)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FootCareDbAdapter.myDbHelper.DATE, date);
        contentValues.put(FootCareDbAdapter.myDbHelper.AMOUNT, amount);
        contentValues.put(FootCareDbAdapter.myDbHelper.TIME,time);

        long id = dbb.insert(FootCareDbAdapter.myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData(String t)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {FootCareDbAdapter.myDbHelper.UID, FootCareDbAdapter.myDbHelper.DATE, FootCareDbAdapter.myDbHelper.AMOUNT, FootCareDbAdapter.myDbHelper.TIME};
        Cursor cursor =db.query(FootCareDbAdapter.myDbHelper.TABLE_NAME,columns, FootCareDbAdapter.myDbHelper.TIME+"=?",new String[] {t},null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(FootCareDbAdapter.myDbHelper.UID));
            String date =cursor.getString(cursor.getColumnIndex(FootCareDbAdapter.myDbHelper.DATE));
            String  amount =cursor.getString(cursor.getColumnIndex(FootCareDbAdapter.myDbHelper.AMOUNT));
            String  time =cursor.getString(cursor.getColumnIndex(FootCareDbAdapter.myDbHelper.TIME));
            buffer.append(cid+ "   " + date + "   " + amount + "   " + time +" \n");
        }
        return buffer.toString();
    }
    public String getDate()
    {
        String name="";
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {FootCareDbAdapter.myDbHelper.UID, FootCareDbAdapter.myDbHelper.AMOUNT};
        Cursor cursor =db.query(FootCareDbAdapter.myDbHelper.TABLE_NAME,columns,null,null,null,null, FootCareDbAdapter.myDbHelper.UID+"DESC");
        while (cursor.moveToNext())
        {
            name =cursor.getString(cursor.getColumnIndex(FootCareDbAdapter.myDbHelper.AMOUNT));
        }
        return name;
    }

    public String getTime()
    {
        String name = "";
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {FootCareDbAdapter.myDbHelper.UID, FootCareDbAdapter.myDbHelper.TIME};
        Cursor cursor =db.query(FootCareDbAdapter.myDbHelper.TABLE_NAME,columns,null,null,null,null, FootCareDbAdapter.myDbHelper.UID+"DESC");
        while (cursor.moveToNext())
        {
            name =cursor.getString(cursor.getColumnIndex(FootCareDbAdapter.myDbHelper.TIME));
        }
        return name;
    }


    public  int delete(String udate)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={udate};

        int count =db.delete(FootCareDbAdapter.myDbHelper.TABLE_NAME , FootCareDbAdapter.myDbHelper.DATE+" = ?",whereArgs);
        return  count;
    }

    public int updatedate(String olddate , String newdate)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FootCareDbAdapter.myDbHelper.DATE,newdate);
        String[] whereArgs= {olddate};
        int count =db.update(FootCareDbAdapter.myDbHelper.TABLE_NAME,contentValues, FootCareDbAdapter.myDbHelper.DATE+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "esdf";    // Database Name
        private static final String TABLE_NAME = "Footcare";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String DATE = "Date";    //Column II
        private static final String AMOUNT= "Amount";    // Column III
        private static final String TIME= "Time";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DATE+" VARCHAR(255) ,"+AMOUNT+" VARCHAR(255) ,"+TIME+" VARCHAR(225));";
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
