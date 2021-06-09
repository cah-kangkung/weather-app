package com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForecastDayModel implements Parcelable {
    @SerializedName("date")
    private String date;
    @SerializedName("day")
    private Day day;
    @SerializedName("astro")
    private Astro astro;
    @SerializedName("hour")
    private ArrayList<Hour> hour;

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public ArrayList<Hour> getHour() {
        return hour;
    }

    public void setHour(ArrayList<Hour> hour) {
        this.hour = hour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeParcelable(this.day, flags);
        dest.writeParcelable(this.astro, flags);
        dest.writeTypedList(this.hour);
    }

    public void readFromParcel(Parcel source) {
        this.date = source.readString();
        this.day = source.readParcelable(Day.class.getClassLoader());
        this.astro = source.readParcelable(Astro.class.getClassLoader());
        this.hour = source.createTypedArrayList(Hour.CREATOR);
    }

    public ForecastDayModel() {
    }

    protected ForecastDayModel(Parcel in) {
        this.date = in.readString();
        this.day = in.readParcelable(Day.class.getClassLoader());
        this.astro = in.readParcelable(Astro.class.getClassLoader());
        this.hour = in.createTypedArrayList(Hour.CREATOR);
    }

    public static final Parcelable.Creator<ForecastDayModel> CREATOR = new Parcelable.Creator<ForecastDayModel>() {
        @Override
        public ForecastDayModel createFromParcel(Parcel source) {
            return new ForecastDayModel(source);
        }

        @Override
        public ForecastDayModel[] newArray(int size) {
            return new ForecastDayModel[size];
        }
    };
}
