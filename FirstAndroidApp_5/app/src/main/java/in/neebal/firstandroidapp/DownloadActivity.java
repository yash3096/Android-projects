package in.neebal.firstandroidapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DownloadActivity extends AppCompatActivity {
    int i=0;
// d is for downloading complete textview and u is for downloading %
    private TextView d,u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        d=(TextView)findViewById(R.id.tv_dc);
        u=(TextView)findViewById(R.id.tv_updation);
    }
    /*class DownloadAsyncTask extends AsyncTask<Void,Integer,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0;i<99;i++)
            {
                publishProgress(i+1);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            u.setText("Downloading "+(values[0])+"%");
            u.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            d.setVisibility(View.VISIBLE);
            u.setVisibility(View.INVISIBLE);


        }
    }
    */public void onDownload(View view)
    {
        Intent intent=new Intent(this,DownloadService.class);
        startService(intent);
    }
}
