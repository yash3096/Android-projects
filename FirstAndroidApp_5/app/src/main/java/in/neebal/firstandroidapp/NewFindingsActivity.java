package in.neebal.firstandroidapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import in.neebal.firstandroidapp.core.Findings;
import in.neebal.firstandroidapp.db.KnightDbAdapter;

public class NewFindingsActivity extends AppCompatActivity {

    //private KnightDbAdapter dbadapter;
    private EditText age,height,address;
    private ArrayList<String> complexions;
    private Spinner complexionSpinner;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_findings);
        complexions=new ArrayList<String>();
        complexions.add("Fair");
        complexions.add("Light");
        complexions.add("Dark");
        complexionSpinner= (Spinner) findViewById(R.id.spinner_complexion);
        adapter=new ArrayAdapter<String>(this, android.support.v7.appcompat.R.layout.support_simple_spinner_dropdown_item,complexions);
        complexionSpinner.setAdapter(adapter);

        age= (EditText) findViewById(R.id.et_age);
        address=(EditText)findViewById(R.id.et_address);
        height= (EditText) findViewById(R.id.et_height);
       //calling dbadapter to connect to db
       //dbadapter=new KnightDbAdapter(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_new_findings,menu);
        return true;
    }
    public void onSave(MenuItem menuItem)
    {
        float h= Float.parseFloat(height.getText().toString());
        int a= Integer.parseInt(age.getText().toString());
        String complexion=(String) complexionSpinner.getSelectedItem();
        ContentValues values=new ContentValues();
        String addr=address.getText().toString();
        //make the column names as public
        //if some other app uses constants of table column names
        //we had to define a class containing all constants and then pass the file as jar to that app
        values.put(KnightDbAdapter.COL_COMPLEXION, complexion);
        values.put(KnightDbAdapter.COL_ADDRESS,addr);
        values.put(KnightDbAdapter.COL_AGE,a);
        values.put(KnightDbAdapter.COL_HEIGHT,h);

        //insert method in ContentResolver is used for inserting data inside the database insert of adapter's insert method
        //give content:// as schema in uri required as first parameter in insert method on contentresolver
        //followed by authority name defined in manifest in provider tag and after /
        //give any name for 1 table coz till now the uri is sufficient to reach
        // to database but if more than 1 table than specify that name associated to table after /
        ContentResolver contentResolver=getContentResolver();
        String uriString="content://in.neebal.firstandroidapp.KnightsDBContentProvider/find";
        Uri uriReturnedByInsert=contentResolver.insert(Uri.parse(uriString), values);
        System.out.println(uriReturnedByInsert);

        Findings findings=new Findings(-1,h,a,addr);
        new PostFindingsAsynTask().execute(findings);
    /*   boolean insert= dbadapter.insertFindings(h,a,addr, (String) complexionSpinner.getSelectedItem());
        if(insert)
        {
            //display toast success
           Toast toast= Toast.makeText(this, "SUCCESSFUL ENTRY", Toast.LENGTH_LONG);
            toast.show();
            age.setText("");
            height.setText("");
            address.setText("");
        }
        else
        {
            //display toast failure
            Toast toast= Toast.makeText(this, "FAILURE", Toast.LENGTH_LONG);
            toast.show();
        }
    */}
    //sending data is a long running operation coz v need to make connection so making use of AsynTask
    class PostFindingsAsynTask extends AsyncTask<Findings,Void,Integer>
    {

        @Override
        protected Integer doInBackground(Findings... params)
        {
            Findings findings=params[0];
            //standard server side code
            //create URL from string
            //findings is given by server side docs so follow i
            try {
                URL url=new URL("http://192.168.1.17/findings");
                //super class is URLConnection open connection returns object of superClass
               HttpURLConnection connection= (HttpURLConnection) url.openConnection();

               //post method told by server docs to be used to add new finding to server
               connection.setRequestMethod("POST");
                //telling URLConnection class that we are sending some output as data and also recieving some input as data
                connection.setDoOutput(true);
                connection.setDoInput(true);
                //making JSON data type to be sent to server side
                String data=String.format("{\"age\":%d,\"height\":%.1f,\"address\":\"%s\"}",
                        findings.getAge(),findings.getHeight(),findings.getAddress());
                connection.setRequestProperty("Content-Type","application/json");
                connection.setRequestProperty("Accept","application/json");
               //for sending json data to server use printwriter object print data and flush it
                PrintWriter printwriter=new PrintWriter(connection.getOutputStream());
                printwriter.println(data);
                printwriter.flush();
               //response code is sent by server side to client which should be
               // equal to predefined constant in HttpUrlconnection class
                int statuscode=connection.getResponseCode();
                //do not forget to disconnect your http connection with server
                connection.disconnect();
                return statuscode;

            } catch (IOException e) {
                e.printStackTrace();
            return -1;}
        }
        //also give permission in manifest
        //show the toast o UI thread so return integer i.e status code returned by server and override onPostExecute showing Toast
              @Override
        protected void onPostExecute(Integer integer) {
            if(integer==HttpURLConnection.HTTP_CREATED)
            {
                Toast.makeText(NewFindingsActivity.this, "Success", Toast.LENGTH_LONG).show();
            }else
            {
                //this is shortcut to get context while in inner class
                Toast.makeText(NewFindingsActivity.this,"Error"+integer,Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onGetList(MenuItem menuItem)
    {
        Intent intent=new Intent(this,ListFindingActivity.class);
        startActivity(intent);
        //if one activity intends to call other activity that intends to call same activity again
        //use finish method to avoid recursion in stack as in this case
        finish();
    }
}
