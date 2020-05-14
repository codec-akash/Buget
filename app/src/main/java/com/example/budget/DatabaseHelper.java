package com.example.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "Budget.db";
    public static int total = 0;

    //Table name = bd_table
    //CREATE TABLE USING SQLITE QUERY:-
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS \"bd_table\" (\n" +
            "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\t\"name\"\tTEXT,\n" +
            "\t\"rupees\"\tINTEGER\n" +
            ")";

    public DatabaseHelper(@Nullable Context context) {
        super(context, Database_Name, null, 1);
        getWritableDatabase().execSQL(CREATE_TABLE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

 /* public Cursor getAllDAta(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM bd_table";
        Cursor cursor = db.rawQuery(query , null);
        return cursor;
    }


  */

    public int getSum() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(rupees) AS PriceTotal FROM bd_table ";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            total = cursor.getInt(cursor.getColumnIndex("PriceTotal"));
        }
        return total;
    }

    public boolean insertData(String name, int money) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("rupees", money);

        long result = sqLiteDatabase.insert("bd_table", "", contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public ArrayList<objects> getAllData() {

        ArrayList<objects> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM bd_table ";
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int money = cursor.getInt(2);
            objects object = new objects(id, name, money);

            arrayList.add(object);
        }
        return arrayList;
    }
}
