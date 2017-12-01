package com.applications.jj.team_12;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jainil on 07-01-2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "stock.db";
    private static final int DATABASE_VERSION = 1;

    private static final String INDICES_TABLE="INDICE_TABLE";
    private static final String NIFTY_50_TABLE = "NIFTY_50_TABLE";
    private static final String SENSEX_TABLE = "SENSEX_TABLE";

    private static final String COL_ID="id";
    private static final String COL_TICKER_NAME="ticker";
    private static final String COL_FAVOURITE="favourite";
    private static final String COL_INDICES="indices";

    String[] nifty50Array;
    String[] sensexArray;


    private static final String CREATE_INDICES_TABLE=String.format("" +
            "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT,%s INTEGER,%s TEXT)", INDICES_TABLE, COL_ID, COL_TICKER_NAME,COL_FAVOURITE,COL_INDICES);

    private static final String CREATE_NIFTY_50_TABLE=String.format("" +
            "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT,%s INTEGER,%s TEXT)", NIFTY_50_TABLE, COL_ID, COL_TICKER_NAME,COL_FAVOURITE,COL_INDICES);

    private static final String CREATE_SENSEX_TABLE=String.format("" +
            "create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s TEXT,%s INTEGER,%s TEXT)", SENSEX_TABLE, COL_ID, COL_TICKER_NAME,COL_FAVOURITE,COL_INDICES);

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_NIFTY_50_TABLE);
        db.execSQL(CREATE_SENSEX_TABLE);
        db.execSQL(CREATE_INDICES_TABLE);

        nifty50Array = new String[]{"ACC","ADANIPORTS","AMBUJACEM","ASIANPAINT","AUROPHARMA","AXISBANK","BAJAJ-AUTO","BANKBARODA","BHEL",
                "BPCL","BHARTIARTL","INFRATEL","BOSCHLTD","CIPLA","COALINDIA","DRREDDY","EICHERMOT","GAIL","GRASIM","HCLTECH","HDFCBANK",
                "HEROMOTOCO","HINDALCO","HINDUNILVR","HDFC","ITC","ICICIBANK","IDEA","INDUSINDBK","INFY","KOTAKBANK","LT","LUPIN","M&M",
                "MARUTI","NTPC","ONGC","POWERGRID","RELIANCE","SBIN","SUNPHARMA","TCS","TATAMTRDVR","TATAMOTORS","TATAPOWER","TATASTEEL",
                "TECHM","ULTRACEMCO","WIPRO","YESBANK","ZEEL"};
        sensexArray = new String[]{"500312","500820","500124","500180","500087","532174","500696","500112","532155","500470","500257",
                "500182","500510","524715","532454","532500","500325","532720","532555", "532215","533278","532921","500570","500103",
                "500875","507685","532540","500209","532977"};

        for(int i=0;i<nifty50Array.length;i++)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_FAVOURITE,0);
            contentValues.put(COL_TICKER_NAME,nifty50Array[i]);
            contentValues.put(COL_INDICES,"NSE");
            db.insert(NIFTY_50_TABLE,null,contentValues);
        }

        for(int i=0;i<sensexArray.length;i++)
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_FAVOURITE,0);
            contentValues.put(COL_TICKER_NAME,"BOM"+sensexArray[i]);
            contentValues.put(COL_INDICES,"BSE");
            db.insert(SENSEX_TABLE,null,contentValues);
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_FAVOURITE,0);
        contentValues.put(COL_TICKER_NAME,"NIFTY_50");
        contentValues.put(COL_INDICES,"NSE");
        db.insert(INDICES_TABLE,null,contentValues);

        contentValues.clear();
        contentValues.put(COL_FAVOURITE,0);
        contentValues.put(COL_TICKER_NAME,"SENSEX");
        contentValues.put(COL_INDICES,"BSE");
        db.insert(INDICES_TABLE,null,contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addTickerNifty50(String ticker)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TICKER_NAME,ticker);
        contentValues.put(COL_FAVOURITE,0);
        long result = db.insert(NIFTY_50_TABLE,null,contentValues);
        return result!=-1;
    }

    public Cursor getNifty50Cursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + NIFTY_50_TABLE, null);
        return cursor;
    }

    public Cursor getSensexCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + SENSEX_TABLE, null);
        return cursor;
    }

    public Cursor getIndicesCursor(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + INDICES_TABLE, null);
        return cursor;
    }
}
