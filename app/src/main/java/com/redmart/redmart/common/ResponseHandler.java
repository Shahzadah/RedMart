package com.redmart.redmart.common;

public interface ResponseHandler<M> {
    void onRequestFailure(String errorMessage);

    void onRequestSuccess(M model);
}