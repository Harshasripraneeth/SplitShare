package com.pressure.splitshare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AmountDB {
    public final String KEY_ID = "_id";
    public final String KEY_NAME="_name";
    public  final  String KEY_PURPOSE = "_purpose";
    public final String KEY_AMOUNT  = "_amount";
    private String iname = "";
    public String amount;
    public int am=0;

    private  final String DATABASE_NAME = "amountDB";
    private final String DATABASE_TABLE = "amountTable";
    private int DATABASE_VERSION =1;

    private DBHelper ourhelper;
    public Context ourcontext;
    private SQLiteDatabase ourdatabase;
    public AmountDB(Context context, String name)
    {
        ourcontext = context;
        iname = name;
    }

    public class DBHelper extends SQLiteOpenHelper
    {
        public  DBHelper(Context context)
        {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL(" DROP TABLE IF EXISTS "+DATABASE_TABLE);
         onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
            CREATE TABLE amountTable ( _id INTEGER PRIMARY KEY AUTOINCREMENT,
                _purpose TEXT NOT NULL,_amount TEXT NOT NULL);

             */
      String sqlcode =" CREATE TABLE "+ DATABASE_TABLE +" ("+ KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
              + KEY_NAME+" TEXT NOT NULL, "
              + KEY_PURPOSE+" TEXT NOT NULL, "
              +KEY_AMOUNT+" TEXT NOT NULL); ";
       db.execSQL(sqlcode);

        }


    }
    public AmountDB open() throws SQLException
    {
        ourhelper = new DBHelper(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
        return  this;
    }
    public void close()
    {
        ourhelper.close();
    }
    public long createEntry(String purpose, String amount)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME,iname);
        contentValues.put(KEY_PURPOSE,purpose);
        contentValues.put(KEY_AMOUNT, amount);
        return ourdatabase.insert(DATABASE_TABLE,null,contentValues);
    }
    public String getdata()
    {
        String [] values = new String[] {KEY_ID,KEY_NAME,KEY_PURPOSE,KEY_AMOUNT};
        Cursor c = ourdatabase.query(DATABASE_TABLE,values,null,null,null,null,null);
        int iRowId =c.getColumnIndex(KEY_ID);
        int iRowName = c.getColumnIndex(KEY_NAME);
        int ipurpose = c.getColumnIndex(KEY_PURPOSE);
        int iamount =c.getColumnIndex(KEY_AMOUNT);
        String res= "";
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(iname.equals(c.getString(iRowName))){
            res= res+ c.getString(iRowId)+":"+c.getString(ipurpose)+" "+c.getString(iamount)
                +"\n";
            am =am+Integer.parseInt(c.getString(iamount));
        }}
        c.close();
        return res;
    }
    public int getamount()
    {String [] values = new String[] {KEY_ID,KEY_NAME,KEY_PURPOSE,KEY_AMOUNT};
        Cursor c = ourdatabase.query(DATABASE_TABLE,values,null,null,null,null,null);
        int iRowName = c.getColumnIndex(KEY_NAME);
        int iamount =c.getColumnIndex(KEY_AMOUNT);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            if(iname.equals(c.getString(iRowName))){

                am =am+Integer.parseInt(c.getString(iamount));
            }}
        c.close();
     return  am;
    }
    public void deleteentry(String name)
    {
        String [] values = new String[] {KEY_ID,KEY_NAME,KEY_PURPOSE,KEY_AMOUNT};
        String code = "DELETE FROM "+ DATABASE_TABLE+" WHERE "+KEY_NAME + " LIKE "+ name;
         ourdatabase.delete(DATABASE_TABLE,KEY_NAME+"=?",new String[]{iname});
    }

}
