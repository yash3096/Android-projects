package db;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import core.Customer;
import core.Donor;

/**
 * Created by Owner on 24-01-2016.
 */
public class CustomerdbAdapter extends ContentProvider{
    SQLiteDatabase db;
    private static  final String DB_NAME="Clean.sqlite",
                             DB_TABLE_NAME1="Donordetails",
                             DB_TABLE_NAME2="Wastedetails",
                             DB_TABLE_NAME3="Collectordetails";
    private static final int DB_VERSION=1;
   public  static final String COL_ID="id";
    public static final String COL_USERNAME="username";
    public static final String COL_PASSWORD="password";
    public static final String COL_NUMBER="number";
    public static final String COL_NAME="name";
    public static final String COL_ADDRESS="address";
    public static final String COL_WASTETYPE="wastetype";
    public static final String COL_ARENA="arena";
    public static final String COL_QUANTITY="quantity";
    public static final String COL_POINTS="points";
    private static final String CREATE_TABLE_DONOR_DETAILS=String.format("create table %s( %s integer not null primary key autoincrement, " +
            "%s text not null, " +
            "%s text not null, " +
            "%s text not null, " +
            "%s text not null, " +
            "%s text not null)",DB_TABLE_NAME1,COL_ID,COL_USERNAME,COL_PASSWORD,COL_NAME,COL_NUMBER,COL_ADDRESS);
     static final String CREATE_TABLE_WASTE_DETAILS=String.format("create table %s( %s text not null," +
            " %s text not null," +
            " %s text not null," +
            " %s text not null," +
            " %s integer null," +
            " %s text not null)",DB_TABLE_NAME2,COL_NAME,COL_NUMBER,COL_ARENA,COL_WASTETYPE,COL_QUANTITY,COL_ADDRESS);
    public CustomerdbAdapter() {

    }

    @Override
    public boolean onCreate() {
        CustomerConnectionHelper helper=new CustomerConnectionHelper(getContext());
        db=helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        selection=String.format("%s=?",COL_ARENA);
        return db.query(DB_TABLE_NAME2,projection,selection,selectionArgs,null,null,sortOrder);
    }
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id=db.insert(DB_TABLE_NAME2,null,values);
        return Uri.withAppendedPath(uri,id+"");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public CustomerdbAdapter(Context context)
    {
        CustomerConnectionHelper helper=new CustomerConnectionHelper(context);
        db=helper.getWritableDatabase();
    }
    public class CustomerConnectionHelper extends SQLiteOpenHelper
    {

        public CustomerConnectionHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE_DONOR_DETAILS);
            db.execSQL(CREATE_TABLE_WASTE_DETAILS);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }        public boolean insertDonor(String username,String password,String name,String address,String number)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_PASSWORD,password);
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_ADDRESS,address);
        contentValues.put(COL_NUMBER,number);
        long id=db.insert(DB_TABLE_NAME1,null,contentValues);
        if(id==-1)
            return false;
        return true;
    }
        public boolean insertWaste(String type,String arena,String address,double quantity,String number,String name)
        {
            ContentValues contentValues=new ContentValues();
            contentValues.put(COL_WASTETYPE,type);
            contentValues.put(COL_ARENA,arena);
            contentValues.put(COL_ADDRESS,address);
            contentValues.put(COL_QUANTITY,quantity);
            contentValues.put(COL_NUMBER,number);
            contentValues.put(COL_NAME,name);
            long id=db.insert(DB_TABLE_NAME2, null, contentValues);
            if(id==-1)
                return false;
            return true;
        }
        public Customer OnLoginAttempt(String username,String password)
        {
            ArrayList<Customer> list;
            Cursor cursor=db.query(DB_TABLE_NAME1,new String[]{COL_USERNAME,COL_NAME,COL_PASSWORD,COL_NUMBER},null,null,null,null,null);
            int un=cursor.getColumnIndex(COL_USERNAME);
            int n=cursor.getColumnIndex(COL_NAME);
            int num=cursor.getColumnIndex(COL_NUMBER);
            int pass=cursor.getColumnIndex(COL_PASSWORD);
            while (cursor.moveToNext())
            {
                String user=cursor.getString(un);
                String p=cursor.getString(pass);
                if(p.equals(password)&& user.equals(username))
                {
                    System.out.println(cursor.getString(n));
                    return new Customer(cursor.getString(n),null,user,p,cursor.getString(num));
                }
            }
            return null;
        }
    public ArrayList<Donor> getDataForCollector(String Arena)
    {
        ArrayList<Donor> list=new ArrayList<Donor>();
        Donor donor;
        Cursor cursor=db.query(DB_TABLE_NAME2,new String[]{COL_NAME,COL_ADDRESS,COL_ARENA,COL_WASTETYPE,COL_NUMBER},null,null,null,null,null);
        int nameColumnIndex=cursor.getColumnIndex(COL_NAME);
        int addressColumnIndex=cursor.getColumnIndex(COL_ADDRESS);
        int arenaColumnIndex=cursor.getColumnIndex(COL_ARENA);
        int typeColumnIndex=cursor.getColumnIndex(COL_WASTETYPE);
        int numberColumnIndex=cursor.getColumnIndex(COL_NUMBER);
        while(cursor.moveToNext())
        {
            String arena=cursor.getString(arenaColumnIndex);
            if(arena.equals(Arena)) {
                donor = new Donor(cursor.getString(nameColumnIndex), cursor.getString(numberColumnIndex), cursor.getString(typeColumnIndex), cursor.getString(addressColumnIndex));
                // System.out.println(donor);
                list.add(donor);
            }}
        return list;
    }
}
