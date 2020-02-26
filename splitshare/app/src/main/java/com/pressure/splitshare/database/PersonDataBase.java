package com.pressure.splitshare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pressure.splitshare.Mate;

import java.util.ArrayList;

public class PersonDataBase {
    public final String KEY_ID4 = "_id4";
    public final String KEY_NAME4="_name4";
    public  final  String KEY_TEL4 = "_TEL4";
    public final String KEY_AMOUNT4  = "_amount4";
    private  final String DATABASE_NAME4 = "extraDb";
    private final String DATABASE_TABLE4 = "DBTABLE";
    private int DATABASE_VERSION =1;

    private dbhelper ourhelper;
    public Context ourcontext;
    private SQLiteDatabase ourdatabase;
    public PersonDataBase(Context context)
    {
        ourcontext = context;
    }

    public class dbhelper extends SQLiteOpenHelper
    {
        public  dbhelper(Context context)
        {
            super(context, DATABASE_NAME4,null,DATABASE_VERSION);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(" DROP TABLE IF EXISTS "+DATABASE_TABLE4);
            onCreate(db);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
            CREATE TABLE amountTable ( _id INTEGER PRIMARY KEY AUTOINCREMENT,
                _purpose TEXT NOT NULL,_amount TEXT NOT NULL);

             */
            String sqlcode =" CREATE TABLE "+ DATABASE_TABLE4 +" ("+ KEY_ID4+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME4+" TEXT NOT NULL, "
                    + KEY_TEL4+" TEXT NOT NULL, "
                    +KEY_AMOUNT4+" TEXT NOT NULL); ";
            db.execSQL(sqlcode);

        }
    }
    public PersonDataBase open() throws SQLException
    {
        ourhelper = new dbhelper(ourcontext);
        ourdatabase = ourhelper.getWritableDatabase();
        return  this;
    }
    public void close()
    {
        ourhelper.close();
    }
    public void   createEntry(String name,String tel, String amount)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME4,name);
        contentValues.put(KEY_TEL4,tel);
        contentValues.put(KEY_AMOUNT4, amount);
         ourdatabase.insert(DATABASE_TABLE4,null,contentValues);
    }

    public ArrayList<Mate> getdata()
    {
        ArrayList<Mate> list2 = new ArrayList<Mate>();
        String [] values = new String[] {KEY_ID4,KEY_NAME4,KEY_TEL4,KEY_AMOUNT4};
        Cursor c = ourdatabase.query(DATABASE_TABLE4,values,null,null,null,null,null);
        int iRowId =c.getColumnIndex(KEY_ID4);
        int iRowName = c.getColumnIndex(KEY_NAME4);
        int ipurpose = c.getColumnIndex(KEY_TEL4);
        int iamount =c.getColumnIndex(KEY_AMOUNT4);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
                list2.add(new Mate(c.getString(iRowName),c.getString(ipurpose),Integer.parseInt(c.getString(iamount))));
            }
        c.close();
        return list2;
    }
    public long deleteentry(String name)
    {
        return ourdatabase.delete(DATABASE_TABLE4,KEY_NAME4 + "=?",new String[]{name});
    }
}
