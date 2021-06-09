package com.hafiz_1313617032_uts.weatherapp.Retrofit;

import com.hafiz_1313617032_uts.weatherapp.Model.HistoryModel;
import com.hafiz_1313617032_uts.weatherapp.Model.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiEndpoint {
    // since forecast also fetch realtime weather, i'm only using one endpoint
    @GET("forecast.json?key=d4892adf2c014c37b2e113200212905&q=Jakarta&days=3&aqi=yes&alerts=no")
    Call<WeatherModel> getData();

    // get history, url param is dynamic
    @GET
    Call<HistoryModel> getHistory(@Url String url);
}
