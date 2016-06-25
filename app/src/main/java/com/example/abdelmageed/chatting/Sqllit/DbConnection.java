package com.example.abdelmageed.chatting.Sqllit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DbConnection extends SQLiteOpenHelper {
    public final static String dbName="Student.db";
    public static final int version=1;
    public DbConnection(Context context){
        super(context, dbName, null, version);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS admin(id INTEGER primary key,name TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("Drop table IF EXISTS admin");
        onCreate(db);
    }
    public void insertRowAdmin(String name){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name", name);
        db.insert("admin", null, values);
    }
    public ArrayList getAllRecord(){
        ArrayList arrayList=new ArrayList();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from admin",null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            arrayList.add(cursor.getString(cursor.getColumnIndex("name")));
            cursor.moveToNext();
        }
        return arrayList;
    }

}
