package com.applications.jj.team_12;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class DetailActivity extends AppCompatActivity {

    private static final String COL_ID="id";
    private static final String COL_TICKER_NAME="ticker";
    private static final String COL_FAVOURITE="favourite";
    private static final String COL_INDICES="indices";
    private static final String DATASET_CODE = "dataset_code";
    private static final String DATABASE_CODE = "database_code";
    private TextView companyDetailTv;
    private TextView priceDetailTv;
    private TextView prevDetailTv;
    TextView tv_date,startDateTV,lastDateTV;
    TextView tvLowHigh;
    Button btnDate;



    DatePickerDialog dialog;
    String dataset_code,database_code;
    Calendar calender;
    GraphView graph;
    StockRestAdapter adapter;
    DataBaseHelper db;

    TextView companyTV;

    Callback<StockData> stockDataCallback = new Callback<StockData>() {
        @Override
        public void onResponse(Response<StockData> response, Retrofit retrofit) {
            StockData data = response.body();
            if(data!=null) {
               // Toast.makeText(DetailActivity.this, "Callback", Toast.LENGTH_SHORT).show();
               /* LineGraphS    eries<DataPoint> line_series = new LineGraphSeries<DataPoint>(new DataPoint[]{});
                int size = data.getDataset().getData().size();
                for (int i=0 ;i<size;i++)
                {

                    line_series.appendData(new DataPoint(size-i,Double.parseDouble(data.getDataset().getData().get(i).get(4))),true,size);
                }
                line_graph.addSeries(line_series);


*/              graph.removeAllSeries();
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {

                });
                int size=data.getDataset().getData().size();
                //Toast.makeText(DetailActivity.this, ""+size, Toast.LENGTH_SHORT).show();

                for(int i=0,j=size-1;i<size;i++,j--)
                {
                    series.appendData(new DataPoint(i,Double.parseDouble(data.getDataset().getData().get(j).get(4))),true,size);
                }
                graph.addSeries(series);

                companyDetailTv.setText(data.getDataset().getName());

                startDateTV.setText(data.getDataset().getStartDate());
                lastDateTV.setText(data.getDataset().getEndDate());
                tvLowHigh.setText("Today's Low : "+data.getDataset().getData().get(0).get(3)+"   High : "+data.getDataset().getData().get(0).get(2));
                tv_date.setText("Last updated on : "+data.getDataset().getRefreshedAt().substring(0,10));
                priceDetailTv.setText(data.getDataset().getData().get(0).get(4));
                prevDetailTv.setText("Prev. Close : "+data.getDataset().getData().get(1).get(4));
                Double change = Double.parseDouble(data.getDataset().getData().get(0).get(4))-Double.parseDouble(data.getDataset().getData().get(1).get(4));
                NumberFormat formatter = new DecimalFormat("#0.00");
                companyTV.setText(formatter.format(change));
                if(change<0)
                    companyTV.setTextColor(Color.RED);
                else
                    companyTV.setTextColor(Color.GREEN);


            }
            else
            {
               // Toast.makeText(DetailActivity.this, ""+response.code(), Toast.LENGTH_SHORT).show();

            }
        }

        @Override
        public void onFailure(Throwable t) {
           // Toast.makeText(DetailActivity.this, "Failure", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        database_code=intent.getStringExtra(DATABASE_CODE);
        dataset_code = intent.getStringExtra(DATASET_CODE);
        //Toast.makeText(this, database_code+dataset_code, Toast.LENGTH_SHORT).show();
        adapter = new StockRestAdapter();
        db = new DataBaseHelper(this);
        companyDetailTv = (TextView) findViewById(R.id.tv_company_detail);
        prevDetailTv = (TextView) findViewById(R.id.tv_previous_detail);
        priceDetailTv = (TextView) findViewById(R.id.tv_price_detail);
        tvLowHigh= (TextView) findViewById(R.id.tv_low_high);
        companyTV = (TextView) findViewById(R.id.tv_change_detail);
        //line_graph = (GraphView) findViewById(R.id.gv_graph);
        adapter.getStockFromApi(database_code,dataset_code,stockDataCallback);
        tv_date = (TextView) findViewById(R.id.tv_date);

        startDateTV = (TextView) findViewById(R.id.tv_startDate);
        lastDateTV = (TextView) findViewById(R.id.tv_endDate);
        graph = (GraphView) findViewById(R.id.gv_graph);
        graph.setBackgroundColor(Color.WHITE);
        btnDate= (Button) findViewById(R.id.btn_date);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calender=Calendar.getInstance();
                dialog= new DatePickerDialog(DetailActivity.this, listener, calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

            }
        });
    }
    DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            adapter.getStockFromGivenDateFromApi(database_code,dataset_code,""+year+"-"+monthOfYear+"-"+dayOfMonth,stockDataCallback);
        }
    };

}

