package com.hafiz_1313617032_uts.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hafiz_1313617032_uts.weatherapp.Adapter.ForecastAdapter;
import com.hafiz_1313617032_uts.weatherapp.Adapter.HourAdapter;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.ForecastDayModel;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.Hour;
import com.hafiz_1313617032_uts.weatherapp.Model.HistoryModel;
import com.hafiz_1313617032_uts.weatherapp.Model.WeatherModel;
import com.hafiz_1313617032_uts.weatherapp.Retrofit.ApiClient;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener  {
    private static final String TAG = "MyActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HourAdapter hourAdapter;
    public static MainActivity mainActivity;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // var
    TextView tv_location, tv_country, tv_condition, tv_temp, tv_lastUpdated, tv_windKph, tv_pressureMb, tv_precipMM, tv_humidity, tv_cloud, tv_gustKph, tv_forecast_date, tv_forecast_avgtemp, tv_forecast_condition;
    ImageView tv_icon;

    // loader for better ui/ux
    CardView tv_card_view, tv_card_alert, tv_card_loading;
    LinearLayout tv_hour_data, tv_hour_loading;

    ArrayList<Hour> listHour = new ArrayList<>();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_icon = (ImageView)findViewById(R.id.current_icon);
        tv_location = findViewById(R.id.location);
        tv_country = findViewById(R.id.country);
        tv_condition = findViewById(R.id.condition);
        tv_temp = findViewById(R.id.temp);
        tv_lastUpdated = findViewById(R.id.last_updated);
        tv_windKph = findViewById(R.id.current_wind_text);
        tv_pressureMb = findViewById(R.id.current_pressure_text);
        tv_precipMM = findViewById(R.id.current_precip_text);
        tv_humidity = findViewById(R.id.current_humidity_text);
        tv_cloud = findViewById(R.id.current_cloud_text);
        tv_gustKph = findViewById(R.id.current_gust_text);

        tv_card_view = findViewById(R.id.card_view);
        tv_card_alert = findViewById(R.id.card_alert);
        tv_card_loading = findViewById(R.id.card_loading);
        tv_hour_data = findViewById(R.id.hour_data_layout);
        tv_hour_loading = findViewById(R.id.hour_loading_layout);

        recyclerView = findViewById(R.id.current_hour_list);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        mainActivity = this;

        // Swipe to Refresh
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(R.color.secondary_color);

        getCurrentFromApi();
        // Log.d(TAG, "onCreate: Test");
    }

    private void getCurrentFromApi () {
        ApiClient.endpoint().getData()
                .enqueue(new Callback<WeatherModel>() {
                    @Override
                    public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {

                        Picasso.get().load("https:" + response.body().getCurrent().getCondition().getIcon()).into(tv_icon);
                        tv_location.setText(response.body().getLocation().getName());
                        tv_country.setText(", " + response.body().getLocation().getCountry());
                        tv_condition.setText(response.body().getCurrent().getCondition().getText());
                        tv_temp.setText(Integer.toString((int)response.body().getCurrent().getTemp_c()) + "\u2103");

                        String lastUpdate = response.body().getCurrent().getLast_updated();
                        SimpleDateFormat fromResponse = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        SimpleDateFormat myFormat = new SimpleDateFormat("EEEE, dd MMM HH:mm");
                        String reformatDate = null;
                        try {
                            reformatDate = myFormat.format(fromResponse.parse(lastUpdate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tv_lastUpdated.setText(reformatDate);

                        tv_windKph.setText(Double.toString(response.body().getCurrent().getWind_kph()) + "km/h");
                        tv_pressureMb.setText((int)(response.body().getCurrent().getPressure_mb()) + "mbar");
                        tv_precipMM.setText(Double.toString(response.body().getCurrent().getPrecip_mm()) + "mm");
                        tv_humidity.setText(Integer.toString(response.body().getCurrent().getHumidity()) + "%");
                        tv_cloud.setText(Integer.toString(response.body().getCurrent().getCloud()) + "%");
                        tv_gustKph.setText(Double.toString(response.body().getCurrent().getGust_kph()) + "km/h");

                        // on response success, deactive refresh animation if present
                        mSwipeRefreshLayout.setRefreshing(false);
                        tv_card_loading.setVisibility(View.GONE);
                        tv_card_alert.setVisibility(View.GONE);
                        tv_hour_loading.setVisibility(View.GONE);
                        tv_hour_data.setVisibility(View.VISIBLE);
                        tv_card_view.setVisibility(View.VISIBLE);

                        // get forecast array and append it to the already history filled listForecastDay
                        /* ArrayList<ForecastDayModel> responseList = response.body().getForecast().getForecastDayList();
                        listForecastDay.addAll(responseList);

                        Log.d(TAG, "onResponse: Masuk Forecast");*/

                        listHour = response.body().getForecast().getForecastDayList().get(0).getHour();
                        hourAdapter = new HourAdapter(listHour);
                        recyclerView.setAdapter(hourAdapter);

                        /* tv_forecast_date0.setText(response.body().getForecast().getForecastDayList().get(0).getDate());
                        tv_forecast_date01.setText(response.body().getForecast().getForecastDayList().get(1).getDate());
                        tv_forecast_date02.setText(response.body().getForecast().getForecastDayList().get(2).getDate());
                        */

                        // Picasso.get().load("https:" + response.body().getCurrent().getCondition().getIcon()).into(tv_icon);
                    }

                    @Override
                    public void onFailure(Call<WeatherModel> call, Throwable t) {
                        tv_card_view.setVisibility(View.GONE);
                        tv_card_loading.setVisibility(View.GONE);
                        tv_hour_loading.setVisibility(View.GONE);
                        tv_hour_data.setVisibility(View.GONE);
                        tv_card_alert.setVisibility(View.VISIBLE);

                        // on response failure, also deactive refresh animation if present
                        mSwipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "onFailure: Fail Forecast");
                    }
                });
    }

    public void click(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.to_forecast: // R.id.textView1
                intent = new Intent(this, ForecastActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
        intent.putExtra("EXTRA_LOCATION", tv_location.getText());
        intent.putExtra("EXTRA_COUNTRY", tv_country.getText());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh: Refresh from MainActivity");
        getCurrentFromApi();
        tv_card_view.setVisibility(View.GONE);
        tv_card_alert.setVisibility(View.GONE);
        tv_hour_data.setVisibility(View.GONE);
        tv_hour_loading.setVisibility(View.VISIBLE);
        tv_card_loading.setVisibility(View.VISIBLE);
    }
}