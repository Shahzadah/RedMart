package com.redmart.redmart.framework.service;

import com.redmart.redmart.common.ResponseHandler;
import com.redmart.redmart.models.ProductDetailsResponse;
import com.redmart.redmart.models.ProductListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloudDataConnector {

    private final ApiEndpointInterface mServiceEndpoint;

    private CloudDataConnector(ApiEndpointInterface service) {
        this.mServiceEndpoint = service;
    }

    public static CloudDataConnector create(ApiEndpointInterface service) {
        return new CloudDataConnector(service);
    }

    public void getProductList(int pageNo, int pageSize, final ResponseHandler<ProductListResponse> responseHandler){
        mServiceEndpoint.getProductList(pageNo, pageSize).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if(response != null && response.isSuccessful()) {
                    responseHandler.onRequestSuccess(response.body());
                }else{
                    responseHandler.onRequestFailure(response != null ? response.message() : "");
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                responseHandler.onRequestFailure(t.getMessage());
            }
        });
    }

    public void getProductDetails(int productId, final ResponseHandler<ProductDetailsResponse> responseHandler){
        mServiceEndpoint.getProductDetails(productId).enqueue(new Callback<ProductDetailsResponse>() {
            @Override
            public void onResponse(Call<ProductDetailsResponse> call, Response<ProductDetailsResponse> response) {
                if(response != null && response.isSuccessful()) {
                    responseHandler.onRequestSuccess(response.body());
                }else{
                    responseHandler.onRequestFailure(response != null ? response.message() : "");
                }
            }

            @Override
            public void onFailure(Call<ProductDetailsResponse> call, Throwable t) {
                responseHandler.onRequestFailure(t.getMessage());
            }
        });
    }
}