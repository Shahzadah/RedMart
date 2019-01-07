package com.redmart.redmart.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.redmart.redmart.CommonMethodForTest;
import com.redmart.redmart.R;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.presenters.ProductListPresenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowDialog;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(RobolectricTestRunner.class)
public class ProductListActivityTest {

    private ActivityController mActivityController;
    private ProductListActivity mActivity;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    @Mock
    ProductListPresenter mPresenter;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        mActivityController = Robolectric.buildActivity(ProductListActivity.class).create();
        mActivity = (ProductListActivity) mActivityController.get();
        toolbar = mActivity.findViewById(R.id.toolbar);
        swipeRefreshLayout = mActivity.findViewById(R.id.swipe_refresh_layout);
        recyclerView = mActivity.findViewById(R.id.recycler_view);

        CommonMethodForTest.setFieldValue(mActivity, "mPresenter", mPresenter);
    }

    @After
    public void tear() {
        mActivityController.pause();
        mActivityController.stop();
        mActivityController.destroy();
    }

    @Test
    public void checkNotNull() {
        assertNotNull(mActivity);
        assertNotNull(toolbar);
        assertNotNull(swipeRefreshLayout);
        assertNotNull(recyclerView);
    }

    @Test
    public void test_ProductDetailsLaunch() {
        ImageView imageView = mock(ImageView.class);
        Product product = spy(new Product());
        mActivity.onProductSelected(product, imageView);

        Intent intent = Shadows.shadowOf(mActivity).peekNextStartedActivity();
        intent.putExtra("ProductSelected", product);
        assertEquals(ProductDetailsActivity.class.getCanonicalName(), intent.getComponent().getClassName());
        assertEquals(Shadows.shadowOf(mActivity).getNextStartedActivity(), intent);
    }

    @Test
    public void onErrorTest() throws Exception {
        mActivity.onError(R.string.title_network_unavailable, R.string.msg_network_unavailable);
        Dialog dialog = ShadowDialog.getLatestDialog();
        assertNotNull(dialog);
        assertNotNull(dialog.findViewById(R.id.errorHeaderTextView));
        assertNotNull(dialog.findViewById(R.id.errorMessageTextView));
        assertNotNull(dialog.findViewById(R.id.okayBtn));
        String dialogTitle = ((TextView) dialog.findViewById(R.id.errorHeaderTextView)).getText().toString().trim();
        String dialogMessage = ((TextView) dialog.findViewById(R.id.errorMessageTextView)).getText().toString().trim();
        Assert.assertEquals(mActivity.getString(R.string.title_network_unavailable), dialogTitle);
        Assert.assertEquals(mActivity.getString(R.string.msg_network_unavailable), dialogMessage);
        dialog.findViewById(R.id.okayBtn).performClick();
    }
}