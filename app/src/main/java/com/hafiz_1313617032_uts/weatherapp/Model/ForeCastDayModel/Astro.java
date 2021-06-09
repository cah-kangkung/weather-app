package com.hafiz_1313617032_uts.weatherapp.Model.ForeCastDayModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Astro implements Parcelable {

    @SerializedName("sunrise")
    private String sunrise;

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sunrise);
    }

    public void readFromParcel(Parcel source) {
        this.sunrise = source.readString();
    }

    public Astro() {
    }

    protected Astro(Parcel in) {
        this.sunrise = in.readString();
    }

    public static final Parcelable.Creator<Astro> CREATOR = new Parcelable.Creator<Astro>() {
        @Override
        public Astro createFromParcel(Parcel source) {
            return new Astro(source);
        }

        @Override
        public Astro[] newArray(int size) {
            return new Astro[size];
        }
    };
}
