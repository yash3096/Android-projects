package com.applications.jj.team_12;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Jainil on 06-01-2017.
 */

public interface StockApi {

    @GET("/api/v3/datasets/{indices}/{ticker}.json")
    Call<StockData> getStockDataFromApi(
            @Path("indices") String indices,
            @Path("ticker") String ticker,
            @Query("api_key") String api_key
    );

    @GET("/api/v3/datasets/{indices}/{ticker}.json")
    Call<StockData> getStockDataFromGivenDateFromApi(
            @Path("indices") String indices,
            @Path("ticker") String ticker,
            @Query("start_date") String start_date,
            @Query("api_key") String api_key
    );
}
