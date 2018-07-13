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


public class MedicineDbAdapter {
    myDbHelper myhelper;

    public MedicineDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }
    public long insertData(String name,String dose,String freq,String time,String duration)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.DOSE, dose);
        contentValues.put(myDbHelper.FREQ, freq);
        contentValues.put(myDbHelper.TIME,time);
        contentValues.put(myDbHelper.DUR, duration);

        long id = dbb.insert(myDbHelper.TABLE_NAME,null ,contentValues);
        return id;
    }
    public String getData(String t)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.DOSE,myDbHelper.FREQ,myDbHelper.TIME,myDbHelper.DUR};
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,myDbHelper.TIME+"=?",new String[] {t},null,null,null);
        //Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  dose =cursor.getString(cursor.getColumnIndex(myDbHelper.DOSE));
            String  freq =cursor.getString(cursor.getColumnIndex(myDbHelper.FREQ));
            String  time =cursor.getString(cursor.getColumnIndex(myDbHelper.TIME));
            String  dur =cursor.getString(cursor.getColumnIndex(myDbHelper.DUR));
            buffer.append(name + "   " + dose+ "   " + freq + "   " + time +"   " + dur +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};

        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};

        int count =db.update(myDbHelper.TABLE_NAME,contentValues,myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "Diaes";    // Database Name
        private static final String TABLE_NAME = "medicine";   // Table Name
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
