package com.redmart.redmart.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product implements Parcelable {

    @SerializedName("id")
    private int productId;

    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String description;

    @SerializedName("details")
    private ProductOriginDetails originDetails;

    @SerializedName("measure")
    private ProductMeasure measure;

    @SerializedName("pricing")
    private PriceDetails priceDetails;

    @SerializedName("img")
    private ImageDetails imageDetails;

    @SerializedName("images")
    private List<ImageDetails> listImages;

    public Product() {}

    private Product(Parcel in) {
        productId = in.readInt();
        title = in.readString();
        description = in.readString();
        originDetails = in.readParcelable(ProductOriginDetails.class.getClassLoader());
        measure = in.readParcelable(ProductMeasure.class.getClassLoader());
        priceDetails = in.readParcelable(PriceDetails.class.getClassLoader());
        imageDetails = in.readParcelable(ImageDetails.class.getClassLoader());
        listImages = in.createTypedArrayList(ImageDetails.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeParcelable(originDetails, flags);
        dest.writeParcelable(measure, flags);
        dest.writeParcelable(priceDetails, flags);
        dest.writeParcelable(imageDetails, flags);
        dest.writeTypedList(listImages);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public PriceDetails getPriceDetails() {
        return priceDetails;
    }

    public ImageDetails getImageDetails() {
        return imageDetails;
    }

    public List<ImageDetails> getListImages() {
        return listImages;
    }

    public ProductMeasure getMeasure() {
        return measure;
    }

    public ProductOriginDetails getOriginDetails() {
        return originDetails;
    }
}