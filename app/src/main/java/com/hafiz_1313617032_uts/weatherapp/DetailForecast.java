package com.hafiz_1313617032_uts.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hafiz_1313617032_uts.weatherapp.Adapter.ForecastAdapter;
import com.hafiz_1313617032_uts.weatherapp.Adapter.HourAdapter;
import com.hafiz_1313617032_uts.weatherapp.Adapter.HourDetailAdapter;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.ForecastDayModel;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.Hour;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DetailForecast extends AppCompatActivity {

    private static final String TAG = "DetailForecast";
    private RecyclerView recyclerView;
    private HourDetailAdapter hourDetailAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static DetailForecast detailForecast;

    TextView tv_detail_date, tv_detail_avgtemp, tv_detail_maxtemp, tv_detail_mintemp, tv_detail_maxwind, tv_detail_totalprecip, tv_detail_conditiontext, tv_detail_title_day, tv_detail_title_date;
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
        tv_detail_title_date = findViewById(R.id.detail_title_date);
        tv_detail_title_day = findViewById(R.id.detail_title_day);

        Picasso.get().load("https:" + forecastDay.getDay().getCondition().getIcon()).into(tv_detail_conditionicon);
        tv_detail_conditiontext.setText(forecastDay.getDay().getCondition().getText());

        String lastUpdate = forecastDay.getDate();
        Log.d(TAG, "lastUpdate: " + lastUpdate);
        SimpleDateFormat fromResponse = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat myFormat = new SimpleDateFormat("EEEE, dd MMM");
        String reformatDate = null;
        try {
            reformatDate = myFormat.format(fromResponse.parse(lastUpdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate:" + reformatDate);
        tv_detail_date.setText(reformatDate);
        String[] day_date = reformatDate.split(", ");
        tv_detail_title_day.setText(day_date[0] + ", ");
        tv_detail_title_date.setText(day_date[1]);


        tv_detail_maxtemp.setText(Double.toString(forecastDay.getDay().getMaxtemp_c()) + "\u2103");
        tv_detail_mintemp.setText(Double.toString(forecastDay.getDay().getMintemp_c()) + "\u2103");
        tv_detail_avgtemp.setText(Integer.toString((int)forecastDay.getDay().getAvgtemp_c()) + "\u00B0");
        tv_detail_maxwind.setText(Double.toString(forecastDay.getDay().getMaxwind_kph()) + "km/h");
        tv_detail_totalprecip.setText(Double.toString(forecastDay.getDay().getTotalprecip_mm()) + "mm");

        // Embed RecyclerView
        recyclerView = findViewById(R.id.hour_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        listHour = forecastDay.getHour();
        hourDetailAdapter = new HourDetailAdapter(listHour);
        recyclerView.setAdapter(hourDetailAdapter);
    }

    public void click(View v) {
        switch(v.getId()) {
            case R.id.back_forecast: // R.id.textView1
                Log.d(TAG, "click: Top back button");
                finish();
        }
    }
}