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


public class BGMonitoringDbAdapter {
    BGMonitoringDbAdapter.myDbHelper myhelper;
    public BGMonitoringDbAdapter(Context context)
    {
        myhelper = new BGMonitoringDbAdapter.myDbHelper(context);
    }
    public long insertData(String date, String amount,String time)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BGMonitoringDbAdapter.myDbHelper.DATE, date);
        contentValues.put(BGMonitoringDbAdapter.myDbHelper.AMOUNT, amount);
        contentValues.put(BGMonitoringDbAdapter.myDbHelper.TIME,time);

        long id = dbb.insert(BGMonitoringDbAdapter.myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {BGMonitoringDbAdapter.myDbHelper.UID, BGMonitoringDbAdapter.myDbHelper.DATE, BGMonitoringDbAdapter.myDbHelper.AMOUNT, BGMonitoringDbAdapter.myDbHelper.TIME};
        Cursor cursor =db.query(BGMonitoringDbAdapter.myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(BGMonitoringDbAdapter.myDbHelper.UID));
            String date =cursor.getString(cursor.getColumnIndex(BGMonitoringDbAdapter.myDbHelper.DATE));
            String  amount =cursor.getString(cursor.getColumnIndex(BGMonitoringDbAdapter.myDbHelper.AMOUNT));
            String  time =cursor.getString(cursor.getColumnIndex(BGMonitoringDbAdapter.myDbHelper.TIME));
            buffer.append("Date :"+date + "   Amount:" + amount + "   Time: " + time +" \n");
        }
        return buffer.toString();
    }

    public String getDate()
    {
        String date="35";
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {BGMonitoringDbAdapter.myDbHelper.UID, BGMonitoringDbAdapter.myDbHelper.DATE, BGMonitoringDbAdapter.myDbHelper.AMOUNT, BGMonitoringDbAdapter.myDbHelper.TIME};
        Cursor cursor =db.query(BGMonitoringDbAdapter.myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            date =cursor.getString(cursor.getColumnIndex(BGMonitoringDbAdapter.myDbHelper.DATE));
            //buffer.append(date);
        }
        return date;
    }

    public String getTime()
    {
        String date="34";
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {BGMonitoringDbAdapter.myDbHelper.UID, BGMonitoringDbAdapter.myDbHelper.DATE, BGMonitoringDbAdapter.myDbHelper.AMOUNT, BGMonitoringDbAdapter.myDbHelper.TIME};
        Cursor cursor =db.query(BGMonitoringDbAdapter.myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            date =cursor.getString(cursor.getColumnIndex(BGMonitoringDbAdapter.myDbHelper.TIME));
            //buffer.append(date);
        }
        return date;
    }
    public  int delete(String udate)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={udate};

        int count =db.delete(BGMonitoringDbAdapter.myDbHelper.TABLE_NAME , BGMonitoringDbAdapter.myDbHelper.DATE+" = ?",whereArgs);
        return  count;
    }

    public int updatedate(String olddate , String newdate)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BGMonitoringDbAdapter.myDbHelper.DATE,newdate);
        String[] whereArgs= {olddate};
        int count =db.update(BGMonitoringDbAdapter.myDbHelper.TABLE_NAME,contentValues, BGMonitoringDbAdapter.myDbHelper.DATE+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Dia";    // Database Name
        private static final String TABLE_NAME = "RecordForm";   // Table Name
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
    //here amout=duration
}