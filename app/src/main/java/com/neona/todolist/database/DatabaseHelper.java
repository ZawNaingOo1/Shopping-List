package com.neona.todolist.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ShoppingList";
    public static final String TABLE_NAME = "shopping_list_table";
    public static final String Col_1 = "ID";
    public static final String Col_2 = "NAME";
    public static final String Col_3 = "PRICE";
    public static final String Col_4 = "BOUGHT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, PRICE INTEGER, BOUGHT BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CURD
    // GETTING DATA
    public Cursor getAllData() {

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;

    }

    // INSERT DATA
    public boolean insertData(String name, int price, boolean bought) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_2, name);
        contentValues.put(Col_3, price);
        contentValues.put(Col_4, bought);
        long result = database.insert(TABLE_NAME, null, contentValues);

        return result == -1;
    }

    public void  updateIsBought(String name, int someValue){
        SQLiteDatabase database = this.getWritableDatabase();
        //database.execSQL( "UPDATE " +TABLE_NAME +" SET BOUGHT = " +someValue +" WHERE " +Col_2 +" = " +String.valueOf(name));
        ContentValues contentValues = new ContentValues();
        contentValues.put(Col_4, someValue);
        String whereClause = "NAME=?";
        String[] whereArgs = new String[] { name };
        database.update(TABLE_NAME,contentValues,whereClause,whereArgs);
    }

    // delete one row
    public void deleteRow(String name){
        SQLiteDatabase database = this.getWritableDatabase();
        String whereClause = "NAME=?";
        String[] whereArgs = new String[] { name };
        database.delete(TABLE_NAME, whereClause, whereArgs);
    }

    public void clearTable() {
        SQLiteDatabase database = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM " +TABLE_NAME;
        database.execSQL(clearDBQuery);
    }
}
