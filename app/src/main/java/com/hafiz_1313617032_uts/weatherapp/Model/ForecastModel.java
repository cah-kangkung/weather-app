package com.hafiz_1313617032_uts.weatherapp.Model;

import com.google.gson.annotations.SerializedName;
import com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel.ForecastDayModel;

import java.util.ArrayList;

public class ForecastModel {
    @SerializedName("forecastday")
    private ArrayList<ForecastDayModel> forecastDayList;

    public ArrayList<ForecastDayModel> getForecastDayList() {
        return forecastDayList;
    }

    public void setForecastDayList(ArrayList<ForecastDayModel> forecastDayList) {
        this.forecastDayList = forecastDayList;
    }
}
