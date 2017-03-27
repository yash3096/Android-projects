package neebal.in.clean;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import  android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import core.Donor;
import db.CustomerdbAdapter;

public class DonorListActivity extends AppCompatActivity {
    ListView dataListView;
    ArrayList<Donor> list;
    CustomerdbAdapter dbAdapter;
    CustomAdapter adapter;
    String arena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);
        dataListView= (ListView) findViewById(R.id.lv_data);
        list=new ArrayList<Donor>();
        adapter=new CustomAdapter();
        dataListView.setAdapter(adapter);
        registerForContextMenu(dataListView);
        dbAdapter=new CustomerdbAdapter(this);
        arena=getIntent().getStringExtra("Arena");
        new ExecTask().execute();
    }
    class ExecTask extends AsyncTask<Void,Void,ArrayList<Donor>>
    {

        @Override
        protected ArrayList<Donor> doInBackground(Void... params) {
            ContentResolver resolver=getContentResolver();

            Cursor cursor=resolver.query(Uri.parse("content://neebal.in.clean.CleanDbConnectionHelper/Waste"), null, null, new String[]{arena}, null);
            int nameColumnIndex=cursor.getColumnIndex(CustomerdbAdapter.COL_NAME);
            int addressColumnIndex=cursor.getColumnIndex(CustomerdbAdapter.COL_ADDRESS);
            int typeColumnIndex=cursor.getColumnIndex(CustomerdbAdapter.COL_WASTETYPE);
            int numberColumnIndex=cursor.getColumnIndex(CustomerdbAdapter.COL_NUMBER);
            while(cursor.moveToNext())
            {
                    Donor donor = new Donor(cursor.getString(nameColumnIndex), cursor.getString(numberColumnIndex), cursor.getString(typeColumnIndex), cursor.getString(addressColumnIndex));
                    // System.out.println(donor);
                    list.add(donor);
            }
            return list;

            //  return dbAdapter.getDataForCollector(arena);
        }

        @Override
        protected void onPostExecute(ArrayList<Donor> arrayLists) {
            list=arrayLists;
            System.out.println(list.get(0));
            adapter.notifyDataSetChanged();
        }
    }
    class CustomerHolder {
    TextView name,number,address,type;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position=info.position;
        Donor donor=list.get(position);
        Intent intent=new Intent();
        switch(item.getItemId()) {
            case R.id.mi_call:
                String telString = "tel:" + donor.getNumber();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse(telString));
                break;
            case R.id.mi_sms:
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:" + donor.getNumber()));
                intent.putExtra("sms_body","Thank u for cleaning society "+donor.getName());
                break;
        }
        startActivity(intent);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.wastelist_menu,menu);
    }

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomerHolder holder = null;
            if (convertView == null) {
                holder = new CustomerHolder();
                convertView = getLayoutInflater().inflate(R.layout.waste, null);
                holder.name = (TextView) convertView.findViewById(R.id.tv_name);
                holder.number = (TextView) convertView.findViewById(R.id.tv_Number);
                holder.address = (TextView) convertView.findViewById(R.id.tv_address);
                holder.type = (TextView) convertView.findViewById(R.id.tv_wastetype);
                convertView.setTag(holder);
            } else
                holder = (CustomerHolder) convertView.getTag();
            holder.name.setText(list.get(position).getName());
            holder.number.setText(list.get(position).getNumber());
            holder.address.setText(list.get(position).getAddress());
            holder.type.setText(list.get(position).getWtype());
        return convertView;
        }
    }
}
