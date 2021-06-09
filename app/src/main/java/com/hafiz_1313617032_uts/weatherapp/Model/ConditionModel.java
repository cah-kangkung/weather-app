package com.hafiz_1313617032_uts.weatherapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ConditionModel implements Parcelable {

    private String text;

    private String icon;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.icon);
    }

    public void readFromParcel(Parcel source) {
        this.text = source.readString();
        this.icon = source.readString();
    }

    public ConditionModel() {
    }

    protected ConditionModel(Parcel in) {
        this.text = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<ConditionModel> CREATOR = new Parcelable.Creator<ConditionModel>() {
        @Override
        public ConditionModel createFromParcel(Parcel source) {
            return new ConditionModel(source);
        }

        @Override
        public ConditionModel[] newArray(int size) {
            return new ConditionModel[size];
        }
    };
}
