package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageDetails implements Parcelable{

    @SerializedName("name")
    private String name;

    public ImageDetails() {}

    private ImageDetails(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    static final Creator<ImageDetails> CREATOR = new Creator<ImageDetails>() {
        @Override
        public ImageDetails createFromParcel(Parcel in) {
            return new ImageDetails(in);
        }

        @Override
        public ImageDetails[] newArray(int size) {
            return new ImageDetails[size];
        }
    };

    public String getName() {
        return name;
    }
}
