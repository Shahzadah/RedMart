package com.redmart.redmart.presenters;

import android.widget.ImageView;

import com.redmart.redmart.R;
import com.redmart.redmart.common.NetworkUtil;
import com.redmart.redmart.common.ResponseHandler;
import com.redmart.redmart.framework.base.BasePresenter;
import com.redmart.redmart.framework.base.MVPView;
import com.redmart.redmart.framework.service.CloudDataConnector;
import com.redmart.redmart.framework.service.ServiceClient;
import com.redmart.redmart.interfaces.IProductListViewActionListener;
import com.redmart.redmart.interfaces.IProductListViewListener;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.models.ProductDetailsResponse;
import com.redmart.redmart.models.ProductListResponse;

import java.util.ArrayList;
import java.util.List;

public class ProductListPresenter extends BasePresenter implements IProductListViewActionListener {

    private IProductListViewListener mListener;
    private String mAPIBaseURL;

    private static final int PAGE_SIZE = 20;
    private int mCurrentPageNo = 0;
    private int mTotalProduct;
    private boolean mOnRefresh;
    private boolean mIsOnLoadMore;
    private List<Product> mListProductDetails;

    public ProductListPresenter() {
        mListProductDetails = new ArrayList<>();
    }

    @Override
    public IProductListViewActionListener getViewActionListener() {
        return this;
    }

    @Override
    public void attachView(MVPView listener) {
        super.attachView(listener);
        this.mListener = (IProductListViewListener) listener;
    }

    @Override
    public void setAPIBaseUrl(String url) {
        this.mAPIBaseURL = url;
    }

    @Override
    public List<Product> getListProductDetails() {
        return this.mListProductDetails;
    }

    @Override
    public void onActivityLoaded() {
        if (validateNetworkAvailability()) {
            mCurrentPageNo = 1;
            mIsOnLoadMore = true;
            mListener.notifyAdapter();  //To show 'Load more' indicator
            callProductListAPI(mAPIBaseURL);
        }
    }

    @Override
    public boolean isLoadMore() {
        return mIsOnLoadMore;
    }

    @Override
    public void onRefresh() {
        if (validateNetworkAvailability()) {
            mCurrentPageNo = 1;
            mOnRefresh = true;
            callProductListAPI(mAPIBaseURL);
        } else {
            mListener.notifyAdapter();  //To hide 'Swipe refresh' indicator
        }
    }

    @Override
    public void onLoadMore() {
        if (validateNetworkAvailability() && !mIsOnLoadMore && mListProductDetails.size() < mTotalProduct) {
            mIsOnLoadMore = true;
            mCurrentPageNo++;
            mListener.notifyAdapter(); //To show 'Load more' indicator
            callProductListAPI(mAPIBaseURL);
        }
    }

    @Override
    public void onListItemClick(int position, ImageView imageView) {
        if (validateNetworkAvailability()) {
            mListener.showProgressDialog();
            callProductDetailsAPI(mAPIBaseURL, mListProductDetails.get(position).getProductId(), imageView);
        }
    }

    /**
     * Call ProductListAPI to get list of product.
     *
     * @param apiUrl :   API URL
     */
    private void callProductListAPI(String apiUrl) {
        CloudDataConnector.create(ServiceClient.getRestService(apiUrl))
                .getProductList(mCurrentPageNo, PAGE_SIZE, new ResponseHandler<ProductListResponse>() {
                    @Override
                    public void onRequestFailure(String errorMessage) {
                        mIsOnLoadMore = false;
                        if (mListener != null) {
                            mListener.onError(R.string.title_server_unavailable, R.string.msg_server_unavailable);
                            mListener.notifyAdapter();  //To hide 'Load more' indicator
                        }
                    }

                    @Override
                    public void onRequestSuccess(ProductListResponse model) {
                        mIsOnLoadMore = false;
                        if (model != null && model.getStatus() != null && model.getStatus().getCode() == 0) {
                            mTotalProduct = model.getTotalProduct();
                            if (mOnRefresh) {
                                mOnRefresh = false;
                                mListProductDetails.clear();
                            }
                            mListProductDetails.addAll(model.getListProduct());
                            if (mListener != null) {
                                mListener.notifyAdapter();
                            }
                        }
                    }
                });
    }

    /**
     * Call ProductDetailsAPI to get product details
     *
     * @param apiUrl    :   API URL
     * @param productId :   Id of selected product
     * @param imageView :   ImageView of selected product which need to be sent to activity for animation
     */
    private void callProductDetailsAPI(String apiUrl, int productId, final ImageView imageView) {
        CloudDataConnector.create(ServiceClient.getRestService(apiUrl))
                .getProductDetails(productId, new ResponseHandler<ProductDetailsResponse>() {
                    @Override
                    public void onRequestFailure(String errorMessage) {
                        if (mListener != null) {
                            mListener.hideProgressDialog();
                            mListener.onError(R.string.title_server_unavailable, R.string.msg_server_unavailable);
                        }
                    }

                    @Override
                    public void onRequestSuccess(ProductDetailsResponse model) {
                        if (mListener != null) {
                            mListener.hideProgressDialog();
                            if (model != null && model.getStatus() != null && model.getStatus().getCode() == 2000) {
                                mListener.onProductSelected(model.getProduct(), imageView);
                            }
                        }
                    }
                });
    }


    /**
     * Send network unavailable error to activity and dismissIndicator progress indicator.
     */
    private boolean validateNetworkAvailability() {
        if (NetworkUtil.isNetworkConnected()) {
            return true;
        }
        if (mListener != null) {
            mListener.onError(R.string.title_network_unavailable, R.string.msg_network_unavailable);
        }
        return false;
    }
}