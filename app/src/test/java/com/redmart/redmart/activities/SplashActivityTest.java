package com.redmart.redmart.activities;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowApplication;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class SplashActivityTest {

    private ActivityController mActivityController;
    private SplashActivity mActivity;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        mActivityController = Robolectric.buildActivity(SplashActivity.class).create();
        mActivity = (SplashActivity) mActivityController.get();
    }

    @After
    public void tear() {
        mActivityController.pause();
        mActivityController.stop();
        mActivityController.destroy();
    }

    @Test
    public void launchProductListActivity() throws InterruptedException {
        mActivityController.resume();
        Timer timer = new Timer();
        final CountDownLatch latch = new CountDownLatch(1);
        timer.schedule(new TimerTask() {
            public void run() {
                latch.countDown();
            }
        }, 1000);
        latch.await();
        Intent intent = Shadows.shadowOf(mActivity).peekNextStartedActivity();
        assertEquals(ProductListActivity.class.getCanonicalName(), intent.getComponent().getClassName());
        assertTrue(mActivity.isFinishing());
    }
}