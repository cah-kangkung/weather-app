package com.hafiz_1313617032_uts.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.hafiz_1313617032_uts.weatherapp.Adapter.ForecastAdapter;
import com.hafiz_1313617032_uts.weatherapp.Adapter.HourAdapter;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.ForecastDayModel;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.Hour;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailForecast extends AppCompatActivity {

    private static final String TAG = "DetailForecast";
    private RecyclerView recyclerView;
    private HourAdapter hourAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static DetailForecast detailForecast;

    TextView tv_detail_date, tv_detail_avgtemp, tv_detail_maxtemp, tv_detail_mintemp, tv_detail_maxwind, tv_detail_totalprecip, tv_detail_conditiontext;
    ImageView tv_detail_conditionicon;
    ArrayList<Hour> listHour = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_forecast);

        ForecastDayModel forecastDay = getIntent().getParcelableExtra("ForecastDay");

        tv_detail_conditionicon = findViewById(R.id.detail_condition_icon);
        tv_detail_date = findViewById(R.id.detail_date);
        tv_detail_avgtemp = findViewById(R.id.detail_avgtemp);
        tv_detail_maxtemp = findViewById(R.id.detail_maxtemp);
        tv_detail_mintemp = findViewById(R.id.detail_mintemp);
        tv_detail_maxwind = findViewById(R.id.detail_maxwind);
        tv_detail_totalprecip = findViewById(R.id.detail_totalprecip);
        tv_detail_conditiontext = findViewById(R.id.detail_condition_text);

        Picasso.get().load("https:" + forecastDay.getDay().getCondition().getIcon()).into(tv_detail_conditionicon);
        tv_detail_conditiontext.setText(forecastDay.getDay().getCondition().getText());
        tv_detail_date.setText(forecastDay.getDate());
        tv_detail_maxtemp.setText(Double.toString(forecastDay.getDay().getMaxtemp_c()));
        tv_detail_mintemp.setText(Double.toString(forecastDay.getDay().getMintemp_c()));
        tv_detail_avgtemp.setText(Double.toString(forecastDay.getDay().getAvgtemp_c()));
        tv_detail_maxwind.setText(Double.toString(forecastDay.getDay().getMaxwind_kph()));
        tv_detail_totalprecip.setText(Double.toString(forecastDay.getDay().getTotalprecip_mm()));

        // Embed RecyclerView
        recyclerView = findViewById(R.id.hour_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listHour = forecastDay.getHour();
        hourAdapter = new HourAdapter(listHour);
        recyclerView.setAdapter(hourAdapter);

        // Log.d(TAG, "onCreate: Move to Detail");
    }
}