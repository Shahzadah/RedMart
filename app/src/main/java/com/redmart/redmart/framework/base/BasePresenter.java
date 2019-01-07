package com.redmart.redmart.framework.base;

import android.content.Context;

import com.redmart.redmart.interfaces.IViewActionListener;

public abstract class BasePresenter<T extends MVPView> implements Presenter<T> {

    protected T view;

    @Override
    public void attachView(T mvpView) {
        view = mvpView;
    }

    @Override
    public void detachView() {
        view = null;
    }

    protected abstract IViewActionListener getViewActionListener();
}