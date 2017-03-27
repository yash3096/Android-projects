package neebal.in.clean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AboutActivity extends AppCompatActivity {
    ArrayList<String> infoList;
    ListView list;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        list= (ListView) findViewById(R.id.lv_about);
        infoList=new ArrayList<String>();
        infoList.add("hello");
        infoList.add("hello");
        infoList.add("bye");
        infoList.add("hello");
        infoList.add("bye");
        infoList.add("hello");
        infoList.add("bye");
        infoList.add("hello");
        infoList.add("bye");
        infoList.add("bye");
        infoList.add("hello");
        infoList.add("bye");
        infoList.add("hello");
        infoList.add("bye");
        infoList.add("hello");
        infoList.add("bye");
        adapter=new CustomAdapter();
        list.setAdapter(adapter);
    }
    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return infoList.size();
        }

        @Override
        public Object getItem(int position) {
            return infoList.get(position);
        }
        class ViewHolder
        {
            TextView details;
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder=null;
            if(convertView==null)
            {
                holder=new ViewHolder();
                convertView=getLayoutInflater().inflate(R.layout.info,null);
                holder.details= (TextView) convertView.findViewById(R.id.tv_info);
                convertView.setTag(holder);
            }
            else
                holder= (ViewHolder) convertView.getTag();
            holder.details.setText(infoList.get(position));
            return convertView;
        }
    }
}
