package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductOriginDetails implements Parcelable {

    @SerializedName("country_of_origin")
    private String countryOrigin;

    public ProductOriginDetails() {}

    private ProductOriginDetails(Parcel in) {
        countryOrigin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(countryOrigin);
    }

    static final Creator<ProductOriginDetails> CREATOR = new Creator<ProductOriginDetails>() {
        @Override
        public ProductOriginDetails createFromParcel(Parcel in) {
            return new ProductOriginDetails(in);
        }

        @Override
        public ProductOriginDetails[] newArray(int size) {
            return new ProductOriginDetails[size];
        }
    };

    public String getCountryOrigin() {
        return countryOrigin;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
