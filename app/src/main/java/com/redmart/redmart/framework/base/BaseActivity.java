package com.redmart.redmart.framework.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.redmart.redmart.common.AlertManager;

public abstract class BaseActivity extends AppCompatActivity implements MVPView{

    protected BaseActivity mThisActivity;

    /**
     * Called when activity is created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThisActivity = this;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getPresenter() != null && getView() != null) {
            getPresenter().attachView(getView());
        }
    }

    /**
     * Called when activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        if (getPresenter() != null && getView() != null) {
            getPresenter().detachView();
        }
        super.onDestroy();
    }

    @Override
    public void onError(int errorTitleResId, int errorMsgResId) {
        AlertManager.showError(mThisActivity, getString(errorTitleResId), getString(errorMsgResId), null);
    }

    protected abstract BasePresenter getPresenter();

    protected abstract MVPView getView();
}