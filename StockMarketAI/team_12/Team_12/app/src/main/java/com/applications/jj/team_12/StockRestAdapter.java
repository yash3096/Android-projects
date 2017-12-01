package com.applications.jj.team_12;


import android.util.Log;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Jainil on 06-01-2017.
 */

public class StockRestAdapter {
    protected final String TAG = getClass().getName();
    private static final String STOCK_URL = "https://www.quandl.com";
    /*
	2 API Keys to load data faster, without getting error of limit over
	 */
	private static final String API_KEY_1 = "<API_KEY_1>";
    private static final String API_KEY_2 = "<API_KEY_2>";
    private Retrofit retrofit;
    private StockApi stockApi;
    private static boolean flag = true;

    public StockRestAdapter(){
        retrofit = new Retrofit.Builder()
                .baseUrl(STOCK_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        stockApi = retrofit.create(StockApi.class);
        Log.d(TAG,"Adapter --Created");
    }



    public void getStockFromApi(String indices,String ticker, Callback<StockData> stockDataCallback)
    {
        Log.d(TAG,"StockApi : " + ticker);
        if(flag) {
            flag=false;
            stockApi.getStockDataFromApi(indices,ticker, API_KEY_1).enqueue(stockDataCallback);

        }
        else{
            flag=true;
            stockApi.getStockDataFromApi(indices,ticker, API_KEY_2).enqueue(stockDataCallback);

        }
    }

    public StockData getStockFromApiSync (String indices,String ticker)
    {
        Call<StockData> call = stockApi.getStockDataFromApi(indices,ticker,API_KEY_1);
        StockData stockData = null;
        try{
            stockData = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stockData;
    }

    public void getStockFromGivenDateFromApi(String indices,String ticker,String start_date, Callback<StockData> stockDataCallback)
    {
        Log.d(TAG,"StockApi : " + ticker);
        if(flag) {
            flag=false;
            stockApi.getStockDataFromGivenDateFromApi(indices,ticker,start_date ,API_KEY_1).enqueue(stockDataCallback);
        }
        else{
            flag=true;
            stockApi.getStockDataFromGivenDateFromApi(indices,ticker,start_date, API_KEY_2).enqueue(stockDataCallback);

        }
    }
}


