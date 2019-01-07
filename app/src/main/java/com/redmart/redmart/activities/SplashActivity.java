package com.redmart.redmart.activities;

import com.redmart.redmart.framework.base.BaseActivity;
import com.redmart.redmart.framework.base.BasePresenter;
import com.redmart.redmart.framework.base.MVPView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    private static final int SPLASH_DISPLAY_TIME = 1000;
    private Timer mTimer;

    /**
     * Start timer when splash activity becomes visible to user.
     */
    @Override
    protected void onResume() {
        super.onResume();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ProductListActivity.launch(mThisActivity);
                finish();
            }
        }, SPLASH_DISPLAY_TIME);
    }

    /**
     * Cancel timer when splash activity becomes in-visible to user.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected MVPView getView() {
        return this;
    }
}