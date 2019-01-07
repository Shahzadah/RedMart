package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductListResponse implements Parcelable {

    @SerializedName("status")
    private Status status;

    @SerializedName("total")
    private int totalProduct;

    @SerializedName("products")
    private List<Product> listProduct;

    public ProductListResponse() {}

    private ProductListResponse(Parcel in) {
        status = in.readParcelable(Status.class.getClassLoader());
        totalProduct = in.readInt();
        listProduct = in.createTypedArrayList(Product.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(status, flags);
        dest.writeInt(totalProduct);
        dest.writeTypedList(listProduct);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductListResponse> CREATOR = new Creator<ProductListResponse>() {
        @Override
        public ProductListResponse createFromParcel(Parcel in) {
            return new ProductListResponse(in);
        }

        @Override
        public ProductListResponse[] newArray(int size) {
            return new ProductListResponse[size];
        }
    };

    public Status getStatus() {
        return status;
    }

    public int getTotalProduct() {
        return totalProduct;
    }

    public List<Product> getListProduct() {
        return listProduct;
    }
}