package com.example.sqliteoperations;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;

import static com.example.sqliteoperations.Message.message;
//myDbAdapter class
public class myDbAdapter {
    //myDbHelper is thee static class we have created down this class
    myDbHelper myhelper;
    //Constructor it get the context of application
    public myDbAdapter(Context context)
    {
        //it call myDbHelper construct and pass the context
        //we are getting from mainActivity

        myhelper = new myDbHelper(context);
    }
   //function to insert data that user will pass through
    //it will recieve the data user passed in mainActivity
    public long insertData(String name, String pass)
    {
        //SQLiteDatabase is the class and we creating the obj
        //When you want to write data into your data
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        //ContentValues is a map like class that matches a value to a String key.
        // It contains multiple overloaded put methods that enforce type safety.
        // Create a new map of values, where column names are the keys
        //To perform insert operation using parameterized query we have to call insert function
        // available in SQLiteDatabase class. insert() function has three parameters
        // like public long insert(String tableName,
        // String  nullColumnHack,ContentValues values) where tableName is name of table in which data to be inserted.
        //NullColumnHack may be passed null, it require table column value in case
        // we don’t put column name in ContentValues object so
        // a null value must be inserted for that particular column,
        // values are those values that needs to be inserted-
        // ContentValues is a key-pair based object which accepts all primitive type
        // values so whenever data is being put in ContentValues object
        // it should be put again table column name as key and data as value.
        // insert function will return a long value
        // i.e number of inserted row if successfully inserted, – 1
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME, name);
        contentValues.put(myDbHelper.MyPASSWORD, pass);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public String getData()
    {

        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD};
        //A Cursor implementation that exposes results from a query on a SQLiteDatabase.
        // SQLiteCursor is not internally synchronized
        // so code using a SQLiteCursor from multiple threads should perform its own synchronization when using the SQLiteCursor.
        //the action of causing a set of data or files to remain identical in more than one location.
        Cursor cursor =db.query(myDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        //A string buffer is like a String , but can be modified.
        // At any point in time it contains some particular sequence of characters,
        // but the length and content of the sequence can be changed through certain method calls.
        StringBuffer buffer= new StringBuffer();
        while (cursor.moveToNext())
        {
            //it get the colum id
            int cid =cursor.getInt(cursor.getColumnIndex(myDbHelper.UID));
            String name =cursor.getString(cursor.getColumnIndex(myDbHelper.NAME));
            String  password =cursor.getString(cursor.getColumnIndex(myDbHelper.MyPASSWORD));
            //this is to append something in buffer
            buffer.append(cid+ "   " + name + "   " + password +" \n");
        }
        return buffer.toString();
    }

    public  int delete(String uname)
            //it again write the data
            //whereArgs={uname} is for updation query
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={uname};
//delete function get table name, where Clause and whereArgs
        int count =db.delete(myDbHelper.TABLE_NAME ,myDbHelper.NAME+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String oldName , String newName)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.NAME,newName);
        String[] whereArgs= {oldName};
        int count =db.update(myDbHelper.TABLE_NAME,contentValues, myDbHelper.NAME+" = ?",whereArgs );
        return count;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";    // Database Name
        private static final String TABLE_NAME = "myTable";   // Table Name
        private static final int DATABASE_Version = 1;    // Database Version
        private static final String UID="_id";     // Column I (Primary Key)
        private static final String NAME = "Name";    //Column II
        private static final String MyPASSWORD= "Password";    // Column III
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+
                " ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255) ,"+ MyPASSWORD+" VARCHAR(225));";
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
                message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                message(context,""+e);
            }
        }
    }
}