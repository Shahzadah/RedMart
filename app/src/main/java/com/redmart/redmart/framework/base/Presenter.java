package com.redmart.redmart.framework.base;

public interface Presenter<V extends MVPView> {
    void attachView(V mvpView);

    void detachView();
}