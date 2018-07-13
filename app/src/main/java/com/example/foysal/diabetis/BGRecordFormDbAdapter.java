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


public class BGRecordFormDbAdapter {
    myDbHelper myhelper;
    public BGRecordFormDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    public long insertData(String date, String amount,String time)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DATE, date);
        contentValues.put(myDbHelper.AMOUNT, amount);
        contentValues.put(myDbHelper.TIME,time);

        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }
    public String getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.DATE,myDbHelper.AMOUNT,myDbHelper.TIME};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String date =cursor.getString(cursor.getColumnIndex(myDbHelper.DATE));
            String  amount =cursor.getString(cursor.getColumnIndex(myDbHelper.AMOUNT));
            String  time =cursor.getString(cursor.getColumnIndex(myDbHelper.TIME));
            buffer.append("Date :"+date + "   Amount:" + amount + "   Time: " + time +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String udate)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={udate};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.DATE+" = ?",whereArgs);
        return  count;
    }

    public int updatedate(String olddate , String newdate)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.DATE,newdate);
        String[] whereArgs= {olddate};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.DATE+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Diera";    // Database Name
        private static final String TABLE_NAME = "BGRecord";   // Table Name
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
