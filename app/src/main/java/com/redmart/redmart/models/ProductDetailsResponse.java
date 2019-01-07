package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductDetailsResponse implements Parcelable {

    @SerializedName("status")
    private Status status;

    @SerializedName("product")
    private Product product;

    public ProductDetailsResponse() {}

    private ProductDetailsResponse(Parcel in) {
        status = in.readParcelable(Status.class.getClassLoader());
        product = in.readParcelable(Product.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(status, flags);
        dest.writeParcelable(product, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductDetailsResponse> CREATOR = new Creator<ProductDetailsResponse>() {
        @Override
        public ProductDetailsResponse createFromParcel(Parcel in) {
            return new ProductDetailsResponse(in);
        }

        @Override
        public ProductDetailsResponse[] newArray(int size) {
            return new ProductDetailsResponse[size];
        }
    };

    public Status getStatus() {
        return status;
    }

    public Product getProduct() {
        return product;
    }
}