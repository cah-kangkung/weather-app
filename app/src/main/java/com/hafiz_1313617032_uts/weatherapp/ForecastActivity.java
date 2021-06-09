package com.hafiz_1313617032_uts.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hafiz_1313617032_uts.weatherapp.Adapter.ForecastAdapter;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.ForecastDayModel;
import com.hafiz_1313617032_uts.weatherapp.Model.ForecastModel;
import com.hafiz_1313617032_uts.weatherapp.Model.HistoryModel;
import com.hafiz_1313617032_uts.weatherapp.Model.WeatherModel;
import com.hafiz_1313617032_uts.weatherapp.Retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity implements ForecastAdapter.OnForecastListener, SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "ForecastActivity";
    private RecyclerView recyclerView;
    private ForecastAdapter forecastAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static ForecastActivity mainActivity;

    TextView tv_location, tv_country;
    LinearLayout tv_progress_bar, tv_error;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<ForecastDayModel> listForecastDay = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        recyclerView = findViewById(R.id.forecast_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mainActivity = this;

        tv_location = findViewById(R.id.location);
        tv_country = findViewById(R.id.country);

        tv_location.setText(getIntent().getStringExtra("EXTRA_LOCATION"));
        tv_country.setText(getIntent().getStringExtra("EXTRA_COUNTRY"));

        tv_progress_bar = findViewById(R.id.progress_bar);
        tv_error = findViewById(R.id.error_layout);

        swipeRefreshLayout = findViewById(R.id.forecast_swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.secondary_color);

        getDataFromApi();
    }

    private void getDataFromApi() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        // Log.d(TAG, "getHistoryFromApi: " + formatter.format(cal.getTime()));

        ApiClient.endpoint().getHistory("history.json?key=d4892adf2c014c37b2e113200212905&q=Jakarta&dt=" + formatter.format(cal.getTime()))
                .enqueue(new Callback<HistoryModel>() {
                    @Override
                    public void onResponse(Call<HistoryModel> call, Response<HistoryModel> response) {
                        listForecastDay = response.body().getForecast().getForecastDayList();
                        Log.d(TAG, "onResponse: Masuk History");
                        getForecastFromApi();
                    }

                    @Override
                    public void onFailure(Call<HistoryModel> call, Throwable t) {
                        recyclerView.setVisibility(View.GONE);
                        tv_progress_bar.setVisibility(View.GONE);
                        tv_error.setVisibility(View.VISIBLE);

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "onFailure: Fail History");
                    }
                });
    }

    private void getForecastFromApi() {
        ApiClient.endpoint().getData()
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                        tv_progress_bar.setVisibility(View.GONE);
                        tv_error.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        // get forecast array and append it to the already history filled listForecastDay
                        ArrayList<ForecastDayModel> responseList = response.body().getForecast().getForecastDayList();
                        listForecastDay.addAll(responseList);

                        forecastAdapter = new ForecastAdapter(listForecastDay, mainActivity);
                        recyclerView.setAdapter(forecastAdapter);

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "onResponse: Masuk Forecast");
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        recyclerView.setVisibility(View.GONE);
                        tv_progress_bar.setVisibility(View.GONE);
                        tv_error.setVisibility(View.VISIBLE);

                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "onFailure: Fail Forecast");
                    }
                });
    }

    @Override
    public void onForecastClick(int position) {
        // Log.d(TAG, "onForecastClick: clicked");

        Intent intent = new Intent(this, DetailForecast.class);
        intent.putExtra("ForecastDay", listForecastDay.get(position));
        startActivity(intent);
    }

    public void click(View v) {
        switch(v.getId()) {
            case R.id.to_main: // R.id.textView1
                Log.d(TAG, "click: Top back button");
                finish();
        }
    }

    @Override
    public void onRefresh() {
        recyclerView.setVisibility(View.GONE);
        tv_error.setVisibility(View.GONE);
        tv_progress_bar.setVisibility(View.VISIBLE);
        Log.d(TAG, "onRefresh: Refresh Success");
        getDataFromApi();
    }
}