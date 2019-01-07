package com.redmart.redmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.redmart.redmart.R;
import com.redmart.redmart.adapters.ProductListAdapter;
import com.redmart.redmart.common.DialogManager;
import com.redmart.redmart.framework.base.BaseActivity;
import com.redmart.redmart.framework.base.BasePresenter;
import com.redmart.redmart.framework.base.MVPView;
import com.redmart.redmart.interfaces.IProductListViewListener;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.presenters.ProductListPresenter;
import com.redmart.redmart.widgets.RecyclerViewOnScrollListener;
import com.redmart.redmart.widgets.SimpleDividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends BaseActivity implements IProductListViewListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private ProductListPresenter mPresenter;
    private ProductListAdapter mAdapter;

    /**
     * To start this activity.
     *
     * @param activity :   calling activity
     */
    public static void launch(BaseActivity activity) {
        Intent intent = new Intent(activity, ProductListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.market_place);
        setSupportActionBar(toolbar);
        mPresenter = new ProductListPresenter();
        mPresenter.attachView(this);
        mPresenter.setAPIBaseUrl(getString(R.string.API_ENDPOINT_PRODUCT_LIST));

        mAdapter = ProductListAdapter.create(mThisActivity)
                .withData(mPresenter.getListProductDetails())
                .withListener(mPresenter.getViewActionListener());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mThisActivity));
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(layoutManager) {

            @Override
            public void onLoadMore() {
                mPresenter.getViewActionListener().onLoadMore();
            }
        });
        mPresenter.getViewActionListener().onActivityLoaded();
    }

    @Override
    protected BasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected MVPView getView() {
        return this;
    }

    @Override
    public void onProductSelected(Product product, ImageView imageView) {
        ProductDetailsActivity.launch(mThisActivity, imageView, product);
    }

    @Override
    public void notifyAdapter() {
        swipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressDialog() {
        DialogManager.show(mThisActivity);
    }

    @Override
    public void hideProgressDialog() {
        DialogManager.dismiss();
    }

    @Override
    public void onRefresh() {
        mPresenter.getViewActionListener().onRefresh();
    }
}