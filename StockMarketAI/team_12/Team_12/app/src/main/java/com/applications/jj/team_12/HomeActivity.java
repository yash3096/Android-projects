package com.applications.jj.team_12;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    private static final String COL_ID="id";
    private static final String COL_TICKER_NAME="ticker";
    private static final String COL_FAVOURITE="favourite";
    private static final String COL_INDICES="indices";
    private static final String DATASET_CODE = "dataset_code";
    StockRestAdapter adapter;
    DataBaseHelper db;
    ArrayList<StockData> stockDataArrayList;
    ListView stockDataListView;

    IndicesCustomAdapter indicesCustomAdapter;
    Callback<StockData> stockDataCallback = new Callback<StockData>() {
        @Override
        public void onResponse(Response<StockData> response, Retrofit retrofit) {
            StockData data = response.body();
            if(data!=null)
            {
                stockDataArrayList.add(data);
               indicesCustomAdapter.notifyDataSetChanged();
            }
            else
            {
              //  Toast.makeText(HomeActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Throwable t) {
           // Toast.makeText(HomeActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        NetworkInfo info= (NetworkInfo)((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(info==null){
            Toast.makeText(this, "Please Connect to Internet And Refresh", Toast.LENGTH_LONG).show();
        }
        adapter = new StockRestAdapter();
        db = new DataBaseHelper(this);
        stockDataArrayList = new ArrayList<StockData>();
        stockDataListView = (ListView) findViewById(R.id.lv_indices);
        getData();
        indicesCustomAdapter = new IndicesCustomAdapter();
        indicesCustomAdapter.notifyDataSetChanged();
        stockDataListView.setAdapter(indicesCustomAdapter);
        stockDataListView.setOnItemClickListener(this);

    }

    protected void getData(){
        Cursor indicesCursor = db.getIndicesCursor();
        int col_tickerIndex = indicesCursor.getColumnIndex(COL_TICKER_NAME);
        int col_indicesIndex = indicesCursor.getColumnIndex(COL_INDICES);
        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE,-7);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(date.getTime());
        while (indicesCursor.moveToNext())
        {
            adapter.getStockFromGivenDateFromApi(indicesCursor.getString(col_indicesIndex),indicesCursor.getString(col_tickerIndex),dateString,stockDataCallback);
        }
        indicesCursor.close();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,CompanyListActivity.class);
        intent.putExtra(DATASET_CODE,stockDataArrayList.get(position).getDataset().getDatasetCode());
        startActivity(intent);
    }

    class IndicesCustomAdapter extends BaseAdapter{
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
