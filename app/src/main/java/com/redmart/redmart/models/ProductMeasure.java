package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductMeasure implements Parcelable{

    @SerializedName("wt_or_vol")
    private String weightVolume;

    public ProductMeasure() {}

    private ProductMeasure(Parcel in) {
        weightVolume = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(weightVolume);
    }

    static final Creator<ProductMeasure> CREATOR = new Creator<ProductMeasure>() {
        @Override
        public ProductMeasure createFromParcel(Parcel in) {
            return new ProductMeasure(in);
        }

        @Override
        public ProductMeasure[] newArray(int size) {
            return new ProductMeasure[size];
        }
    };

    public String getWeightVolume() {
        return weightVolume;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
