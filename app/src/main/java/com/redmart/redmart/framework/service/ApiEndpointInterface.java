package com.redmart.redmart.framework.service;

import com.redmart.redmart.models.ProductDetailsResponse;
import com.redmart.redmart.models.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpointInterface {

    @GET("./search")
    Call<ProductListResponse> getProductList(
            @Query("page") int pageNo,
            @Query("pageSize") int pageSize
    );

    @GET("./products/{product_id}")
    Call<ProductDetailsResponse> getProductDetails(
            @Path("product_id") int productId
    );
}