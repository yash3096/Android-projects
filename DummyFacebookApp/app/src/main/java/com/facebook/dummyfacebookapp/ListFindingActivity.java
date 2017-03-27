package com.facebook.dummyfacebookapp;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class ListFindingActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    FindingsAdpater adpater;
    private ListView listView;
    private ArrayList<Findings>  findingsArrayList;

    class FindingsHolder
    {
        TextView age,height,address;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_findings);
        listView= (ListView) findViewById(R.id.lv_findings);
        //every item of list view listens to on tap and opens menu inflator process
        //registerForContextMenu(listView);
        findingsArrayList=new ArrayList<Findings>();
        adpater=new FindingsAdpater();
        listView.setAdapter(adpater);
        //long running operation to geT large findings from database
        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        new GetFindingsTask().execute();
    }
    //inflating menu in our listview for findings list
    //getFindings should be done inside the background to avoid blank screen when app starts
    //we are associating datatbase findings with our array list in UI thread only
    // but if there are 1000 contacts to be retrieved we need to do it in background to prevent delayed app start
    //so we have used asyntask to do run in background
    //intitially size of arraylist is 0 but when all db retireved associated in on post by calling method
    // notifyDataSetChange on adapter
    class GetFindingsTask extends AsyncTask<Void,Void,ArrayList<Findings>>
    {
        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }


        @Override
        protected ArrayList<Findings> doInBackground(Void... params) {
            ContentResolver contentResolver=getContentResolver();
            ArrayList<Findings> list=new ArrayList<Findings>();
            //this uri is just to reach to the content provider using content resolver
            String uriString="content://in.neebal.firstandroidapp.KnightsDBContentProvider/finding";
            Cursor cursor=contentResolver.query(Uri.parse(uriString),null,null,null,null);
            int heightColumnIndex=cursor.getColumnIndex("height");
            int ageColumnIndex=cursor.getColumnIndex("age");
            int addressColumnIndex=cursor.getColumnIndex("adress");
            int id=cursor.getColumnIndex("id");
            while(cursor.moveToNext())
            {
                float h=cursor.getFloat(heightColumnIndex);
                int a=cursor.getInt(ageColumnIndex);
                String addr=cursor.getString(addressColumnIndex);
                Findings findings=new Findings(id,h,a,addr);
                list.add(findings);
            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList<Findings> findingses) {
            findingsArrayList=findingses;
            adpater.notifyDataSetChanged();
            progressDialog.hide();
        }
    }
    class FindingsAdpater extends BaseAdapter
    {

        @Override
        public int getCount() {
            return findingsArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return findingsArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FindingsHolder holder=null;
            if(convertView== null)
            {
                LayoutInflater li = getLayoutInflater();
                convertView = li.inflate(R.layout.listview_findings, null);
                holder=new FindingsHolder();
                holder.address= (TextView) convertView.findViewById(R.id.tv_address);
                holder.height= (TextView) convertView.findViewById(R.id.tv_height);
                holder.age= (TextView) convertView.findViewById(R.id.tv_age);
                convertView.setTag(holder);
            }
            else
            {
                holder= (FindingsHolder) convertView.getTag();
            }
            Findings findings=findingsArrayList.get(position);
            holder.age.setText(findings.getAge()+"");
            holder.height.setText(findings.getHeight() + "");
            holder.address.setText(findings.getAddress()+"");
            return convertView;
        }
    }
}
