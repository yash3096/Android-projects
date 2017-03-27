package in.neebal.firstandroidapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import in.neebal.firstandroidapp.core.Findings;
import in.neebal.firstandroidapp.db.KnightDbAdapter;

public class ListFindingActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ProgressDialog progressDialog;
    KnightDbAdapter dbAdapter;
    FindingsAdpater adpater;
    private ListView listView;
    private ArrayList<Findings>  findingsArrayList;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Findings findings=findingsArrayList.get(position);
        String findingUri="content://in.neebal.firstandroidapp.KnightsDBContentProvider/finding/"+findings.getId();
        Uri uri=Uri.parse(findingUri);
        Intent intent=new Intent();
        intent.setData(uri);
        //sending result to call for result used in calling method use method setResult
        //Result code is predefined constant to return whether activity didnthe task or not
        setResult(RESULT_OK,intent);
        //explicitly destroys current ACTIVITY pops out this activity from Task stack
        finish();

    }

    class FindingsHolder
    {
        TextView age,height,address;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_finding);
        listView= (ListView) findViewById(R.id.lv_findings);
        //every item of list view listens to on tap and opens menu inflator process
        //to listen to click on item click on listview
        listView.setOnItemClickListener(this);
        registerForContextMenu(listView);
        findingsArrayList=new ArrayList<Findings>();
        adpater=new FindingsAdpater();
        listView.setAdapter(adpater);
        dbAdapter=new KnightDbAdapter(this);
        //long running operation to geT large findings from database
        progressDialog=new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Please wait...");
        new GetFindingsTask().execute();
    }
    //inflating menu in our listview for findings list
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_findings,menu);
    }
    //this method is called for long tap of any listview item
    //no onclick given for menu xml coz onclick will be called for all items
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //will give the info about the adapter which is in background
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //will give the position on the adapter through which we are getting in the context menu
        int p=info.position;
        //give findings object from position given from clicked item
        // we can also find object of findings in arraylist using position
        Findings findings=findingsArrayList.get(p);
        //remove id from database
        dbAdapter.deleteFindings(findings.getId());
        // remove the finding from the data set and inform the adapter about it using notifydatasetchange method
        findingsArrayList.remove(p);
        adpater.notifyDataSetChanged();
        return true;
    }

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
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return dbAdapter.getFindings();
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
    public void onAdd(MenuItem menuItem)
    {
        Intent intent=new Intent(this,NewFindingsActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_list_findings,menu);
        return true;
    }
}
