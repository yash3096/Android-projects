package com.applications.jj.team_12;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CompanyListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String COL_ID="id";
    private static final String COL_TICKER_NAME="ticker";
    private static final String COL_FAVOURITE="favourite";
    private static final String COL_INDICES="indices";
    private static final String DATASET_CODE = "dataset_code";
    private static final String DATABASE_CODE = "database_code";
    TextView indicesNameTV,marqueeTV;
    StockRestAdapter adapter;
    DataBaseHelper db;
    ArrayList<StockData> stockDataArrayList;
    ListView stockDataListView;
    CompanyCustomAdapter companyCustomAdapter;
    ProgressDialog progressDialog;
    static int count;
    String indicesString;

    Callback<StockData> stockDataCallback = new Callback<StockData>() {
        @Override
        public void onResponse(Response<StockData> response, Retrofit retrofit) {
            StockData data = response.body();
            count--;
            if(count<0)
            {
                marqueeTV.setSelected(true);
                marqueeTV.setFocusable(true);
            }
            if(data!=null)
            {
                stockDataArrayList.add(data);
                companyCustomAdapter.notifyDataSetChanged();

                StringBuffer buffer = new StringBuffer("  |  ");
                if(data.getDataset().getDatabaseCode().compareTo("NSE")==0)
                {
                    buffer.append(data.getDataset().getDatasetCode());
                }
                else{
                    String name[] = data.getDataset().getName().split(" ");
                    buffer.append(name[0]);
                    buffer.append(" ");
                    buffer.append(name[1]);
                }
                buffer.append("  ");
                buffer.append(data.getDataset().getData().get(0).get(4));
                buffer.append("  ");
                marqueeTV.append(buffer.toString());
                NumberFormat formatter = new DecimalFormat("#0.00");

                Double change = Double.parseDouble(data.getDataset().getData().get(0).get(4)) - Double.parseDouble(data.getDataset().getData().get(1).get(4));
                Spannable changeSpannable = new SpannableString(""+formatter.format(change));
                if(change<0)
                {
                    changeSpannable.setSpan(new ForegroundColorSpan(Color.RED),0,changeSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                else
                {
                    changeSpannable.setSpan(new ForegroundColorSpan(Color.GREEN),0,changeSpannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                marqueeTV.append(changeSpannable);


            }
            else
            {
               // Toast.makeText(CompanyListActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Throwable t) {
          //  Toast.makeText(CompanyListActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        Intent intent = getIntent();
        indicesString = intent.getStringExtra(DATASET_CODE);
        indicesNameTV = (TextView) findViewById(R.id.tv_company_indices);
        indicesNameTV.setText(indicesString);
        adapter = new StockRestAdapter();
        db = new DataBaseHelper(this);
        marqueeTV = (TextView) findViewById(R.id.marquee);
        stockDataArrayList = new ArrayList<StockData>();
        stockDataListView = (ListView) findViewById(R.id.lv_company);
        getData(indicesString);

        companyCustomAdapter = new CompanyCustomAdapter();
        stockDataListView.setAdapter(companyCustomAdapter);
        count = 20;
        /*progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching data.......");

        progressDialog.show();*/
    }
    protected void getData(String indicesString){
        Cursor cursor = null;
        int col_tickerIndex=-1,col_indicesIndex=-1;
        switch (indicesString){
            case "NIFTY_50":cursor = db.getNifty50Cursor();
                 col_tickerIndex = cursor.getColumnIndex(COL_TICKER_NAME);
                 col_indicesIndex = cursor.getColumnIndex(COL_INDICES);
                break;
            case "SENSEX":cursor = db.getSensexCursor();
                 col_tickerIndex = cursor.getColumnIndex(COL_TICKER_NAME);
                 col_indicesIndex = cursor.getColumnIndex(COL_INDICES);
                break;
        }


        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE,-7);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date.getTime());
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                adapter.getStockFromGivenDateFromApi(cursor.getString(col_indicesIndex), cursor.getString(col_tickerIndex), dateString, stockDataCallback);
            }
            cursor.close();
        }
        stockDataListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(DATABASE_CODE,stockDataArrayList.get(position).getDataset().getDatabaseCode());
        intent.putExtra(DATASET_CODE,stockDataArrayList.get(position).getDataset().getDatasetCode());
        startActivity(intent);
    }

    class CompanyCustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return stockDataArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return stockDataArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if(view==null)
            {
                LayoutInflater inflater = getLayoutInflater();
                view=inflater.inflate(R.layout.list_item_stock,null);
            }
            StockData data = stockDataArrayList.get(position);
            String companyName[] = data.getDataset().getName().split("-");
            TextView companyNameTV = (TextView) view.findViewById(R.id.tv_li_company);
            TextView priceTV = (TextView) view.findViewById(R.id.tv_li_price);
            TextView changeTV = (TextView) view.findViewById(R.id.tv_li_change);
            companyNameTV.setText(companyName[companyName.length-1]);
            priceTV.setText(data.getDataset().getData().get(0).get(4));
            Double change = Double.parseDouble(data.getDataset().getData().get(0).get(4))-Double.parseDouble(data.getDataset().getData().get(1).get(4));
            NumberFormat formatter = new DecimalFormat("#0.00");
            changeTV.setText(formatter.format(change));
            if(change<0)
                changeTV.setTextColor(Color.RED);
            else
                changeTV.setTextColor(Color.GREEN);
            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public void onRefresh(MenuItem menuItem)
    {
        Toast.makeText(this, "Refreshing..", Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        finish();
        startActivity(intent);

    }
}
