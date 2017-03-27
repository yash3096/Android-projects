package in.neebal.firstandroidapp.db;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import in.neebal.firstandroidapp.core.Findings;
//to help android system to find provider change manifest by putting provider tag
//also define a no arg default constructor for content provider's use as manifest /android system
// would not know what overloaded constructor we have defined coz it makes use of default constructor to create object
public class KnightDbAdapter extends ContentProvider {

    public KnightDbAdapter(){

    }

    //convention of .sqlite should be followed for name of Database file
    private static final String DB_NAME="KnightDB.sqlite";
    //version is 1 for 1st creation
    //version is changed for new updated app which is then copied to old users' app by onUpgrade method
    // while downloading update from playstore
    //now the ere can 2 two users one on version 2 and other on version1 so onUpgrade gets updated
    private static final int DB_VERSION=2;
    //table name
    private static final String TABLE_FINDINGS="findings";
    //id
    public static final String COL_ID="id";
    //newly added column complexion in update on playstore
    public static final String COL_COMPLEXION="complexion";
    public static final String COL_HEIGHT="height";
    public static final String COL_AGE="age";
    public static final String COL_ADDRESS="adress";
    private SQLiteDatabase db;
    //SQL command to create a table in java code is a string
    //SQLite comes with its own set of datatype
    //for a row a unique column whose value cannot repaeat in any other row has primary key like ID
    //in integer field in SQL u can store decimal values
    //adding new compplexion column in database for new user downloading the app
    //keep initial value null coz previous users dint have such column so dint insert any values for that column
    private static final String CREATE_TABLE_FINDINGS=
            String.format("create table %s (%s integer not null primary key autoincrement," +
                    "%s integer not null,%s integer null,%s text not null, %s text null)"
                    ,TABLE_FINDINGS,COL_ID,COL_HEIGHT,COL_AGE,COL_ADDRESS,COL_COMPLEXION);
    //creating alter table i.e changes to be done on the sql file using THIS SYNTAX
    //this is for old users who are updating their app
    //onCreate method not being called as already installed onUpgrade gets called instead and new code replaces old code
    private static final String ALTER_TABLE_FINDINGS_1=String.format("alter table %s add column %s text null"
            ,TABLE_FINDINGS,COL_COMPLEXION);
    //Urimatcher is a class used to match uri constructor takes CODE which is -1 default
    //this code is changed when different uris are added in static initializer block
    private static final UriMatcher matcher=new UriMatcher(UriMatcher.NO_MATCH);
    private static final int FINDING = 1;

    private static final int FINDING_WITH_ID = 2;


    static{
    //add uri to the matcher with different codes which can be checked while query called with different uri
        matcher.addURI("in.neebal.firstandroidapp.KnightsDBContentProvider","finding",FINDING);
        matcher.addURI("in.neebal.firstandroidapp.KnightsDBContentProvider","finding/#",FINDING_WITH_ID);
    }

    //outer class constructor
    public KnightDbAdapter(Context context)
    {
        KnightsDbConnectionHelper Helper=new KnightsDbConnectionHelper(context);

        //creating inner class object
        //this method called on helper calls onCreate intenally returning Sqlitedatabase object
        //it creates and open database for first time onCreate will be called else onOpen called
        //this constructor is called from activity willing to connect to database
        db=Helper.getWritableDatabase();
    }
    public boolean insertFindings(float height,int age,String address,String complexion)
    {
        //COntentValue is like map containing key and value used in 3rd parameter in insert method on db object
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_AGE,age);
        contentValues.put(COL_ADDRESS,address);
        contentValues.put(COL_HEIGHT,height);
        contentValues.put(COL_COMPLEXION,complexion);
        //returns long i.e -1 if error occurs or else row id of newly created row
        long id=db.insert(TABLE_FINDINGS,null,contentValues);
        if(id==-1)
            return false;
        return true;
    }
    public ArrayList<Findings> getFindings()
    {
        //we need to return the Arraylist of particular objects to be retrieved i.eFindings in this case
        ArrayList<Findings> list=new ArrayList<Findings>();
        //overloaded to get data from table takin table name,columns to query for else all null
        //returns an object of class Cursor(RAM) having only query asked for particular
        //cursor has internal pointer goes internally from one record to other pointer points to -1 record
        //move pointer to first record implicitly by moveToNext returning
        //we know the name of column not their column indexes so
        // dont guess the column index value get them seperately using getColumnIndex method
        Cursor cursor=db.query(TABLE_FINDINGS, new String[]{COL_HEIGHT, COL_AGE, COL_ADDRESS,COL_ID}, null, null, null, null, null);
        int heightColumnIndex=cursor.getColumnIndex(COL_HEIGHT);
        int ageColumnIndex=cursor.getColumnIndex(COL_AGE);
        int addressColumnIndex=cursor.getColumnIndex(COL_ADDRESS);
        int id=cursor.getColumnIndex(COL_ID);
        while(cursor.moveToNext())
        {
            long ID=cursor.getInt(id);
            float h=cursor.getFloat(heightColumnIndex);
            int a=cursor.getInt(ageColumnIndex);
            String addr=cursor.getString(addressColumnIndex);
            Findings findings=new Findings(ID,h,a,addr);
            list.add(findings);
        }
        return list;
    }
    //user defined method to delete a record in database according to id coz it is uniqe for every data
    //delete method
    public void deleteFindings(long findingId)
    {
        //whereclause is a statements(String) whose syntax is given below indicating column name from where u want to delete data
        //like id=? interpreted as delete from finding(db) where id is ?
        String whereClause=String.format("%s = ?",COL_ID);
        //this is whereclause arg which replaces id by the string array values defined in whereclause args
        String[] whereClauseArgs=new String[]{findingId+""};
        //delete method on database object takes table whereclause and whereclause arg
        //this method relates whereclause args to ? in whereclause and deletes findings from the db(findings)
        db.delete(TABLE_FINDINGS,whereClause,whereClauseArgs);
    }
    //these methods are overridden from ContentProviders but used by ContentResolver
    //this is diffenrent from SQLite dbadapter ka onCreate
    //when ur application ka process runs this contentprovider will be initialised by this method
    //thus it just creates and opens a database
    //if app killed and started again this method will be called again
    @Override
    public boolean onCreate() {
        //getContext returns the context of the application where provider is running in
        //this method retuurns null if used in constructor so cannot be used inside knightdb constructor as earlier
        // and was passed by new findings explicitly
        System.out.println("Oncreate of content provider");
        KnightsDbConnectionHelper helper=new KnightsDbConnectionHelper(getContext());
        db=helper.getWritableDatabase();
        return true;
    }

    //different uri need different data from different databases so made use of UriMatcher code
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //content resolver for this query will not be same application it can to be other application
       //match on matcher object returns the code
       // which was added instatic initializer block at the time of adding uri i UriMatcher
        switch(matcher.match(uri))
        {
            case FINDING:
                break;
            //this is the case wehn we need particular data from database when uri sent by content resolver
            // has id of data to be retirved
            case FINDING_WITH_ID:
            {
                //lastpathsegment returns the last part of uri after last / which can be id or any other column data type
                // each / ---- / is a segment in Uri first segment is scheme second authority third finding fourth id
                long id= Long.parseLong(uri.getLastPathSegment());
                //create whereclause statement for what column type given in Uri
                selection=String.format("%s=?",COL_ID);
                selectionArgs=new String[]{id+""};
                break;
            }
            default:return null;
        }
        return db.query(TABLE_FINDINGS,projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //this insert of sqlite returns the id of newly inserted row else -1 if error occurred
       long id= db.insert(TABLE_FINDINGS,null,values);
        //return uri recieved as defined in newFindings +"/"+id returned by insert of Sqlite
        //with appends appends the string after a / in provided uri
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

    class KnightsDbConnectionHelper extends SQLiteOpenHelper{

        //create constructor after overriding abstract methods coz there is no default constructor in SQLiteOpenHelper
        // so if super not called it shows error
        //passs null in factory coz object of no use
        //remove contructor paramenters version and name after defining constants coz these constants passed to super constructor
        //override  onCreate and onUpdgrade
        public KnightsDbConnectionHelper(Context context) {
            super(context,DB_NAME,null,DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_FINDINGS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //for old users updation
            //once DB_VERSION increases upadted version comes and onUpgrade called
            //once updated DB_VERSION changes to new incremented version and no onUpgrade called
            //if user has DB_version 1 i.e old version so it has to download AlterTableFIndings1 and 2 both
            // so put if conditions but no ELSE
            db.execSQL(ALTER_TABLE_FINDINGS_1);
        }
    }
}
