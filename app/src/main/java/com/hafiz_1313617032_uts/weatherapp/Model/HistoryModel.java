package com.hafiz_1313617032_uts.weatherapp.Model;

// the same as weather model, since the response body is also the same
public class HistoryModel {

    private LocationModel location;
    private CurrentModel current;
    private ForecastModel forecast;

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public CurrentModel getCurrent() {
        return current;
    }

    public void setCurrent(CurrentModel current) {
        this.current = current;
    }

    public ForecastModel getForecast() {
        return forecast;
    }

    public void setForecast(ForecastModel forecast) {
        this.forecast = forecast;
    }

}
