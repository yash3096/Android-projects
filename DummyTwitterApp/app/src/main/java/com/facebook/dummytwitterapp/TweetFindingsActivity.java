package com.facebook.dummytwitterapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class TweetFindingsActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    private EditText tweet;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_findings);
        tweet= (EditText) findViewById(R.id.et_details);
    }
    public void onList(View view)
    {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if(resultCode==RESULT_OK)
        {if(requestCode==REQUEST_CODE)
        {
           Uri uri=intent.getData();
            Toast.makeText(this,uri.toString(),Toast.LENGTH_LONG).show();
            ContentResolver contentresolver=getContentResolver();
            Cursor cursor=contentresolver.query(uri,null,null,null,null);
            if(cursor!=null)
            {
                int ageColumnIndex=cursor.getColumnIndex("age");
                int heightColumnIndex=cursor.getColumnIndex("height");
                int addressColumnIndex=cursor.getColumnIndex("adress");
            // since we have only one item we dont need while loop for moveToNext
                if(cursor.moveToNext())
                {
                    int age=cursor.getInt(ageColumnIndex);
                    float height=cursor.getFloat(heightColumnIndex);
                    String address=cursor.getString(addressColumnIndex);
                    String text=String.format("Height: %f\nAge: %d\nAddress: %s",height,age,address);
                    tweet.setText(text);
                }
            }
        }
        }
    }
}
