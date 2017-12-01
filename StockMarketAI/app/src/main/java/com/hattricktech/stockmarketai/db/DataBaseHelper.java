package com.hattricktech.stockmarketai.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jainil on 07-01-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stock.db";
    private static final int DATABASE_VERSION = 1;

    private static final String FAVOURITE_TABLE ="FAVOURITE_TABLE";


    private static final String COL_ID="id";
    private static final String COL_TICKER_NAME="ticker";
    private static final String COL_INDICES="indices";



    private static final String CREATE_FAVOURITE_TABLE =String.format("" +
            "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT,%s TEXT)", FAVOURITE_TABLE, COL_ID, COL_TICKER_NAME,COL_INDICES);

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_FAVOURITE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addToFavouriteTable(String indices,String tickerName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_INDICES,indices);
        contentValues.put(COL_TICKER_NAME,tickerName);
        db.insert(FAVOURITE_TABLE,null,contentValues);
    }

    public Cursor getFavouriteCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + FAVOURITE_TABLE, null);
        return cursor;
    }



}
