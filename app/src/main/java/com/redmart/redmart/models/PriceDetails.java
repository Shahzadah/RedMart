package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PriceDetails implements Parcelable {

    @SerializedName("price")
    private double price;

    public PriceDetails() {}

    private PriceDetails(Parcel in) {
        price = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PriceDetails> CREATOR = new Creator<PriceDetails>() {
        @Override
        public PriceDetails createFromParcel(Parcel in) {
            return new PriceDetails(in);
        }

        @Override
        public PriceDetails[] newArray(int size) {
            return new PriceDetails[size];
        }
    };

    public double getPrice() {
        return price;
    }
}