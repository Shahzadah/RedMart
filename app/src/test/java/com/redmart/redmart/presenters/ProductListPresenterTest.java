package com.redmart.redmart.presenters;

import android.widget.ImageView;

import com.redmart.redmart.CommonMethodForTest;
import com.redmart.redmart.R;
import com.redmart.redmart.common.NetworkUtil;
import com.redmart.redmart.common.ResponseHandler;
import com.redmart.redmart.framework.service.CloudDataConnector;
import com.redmart.redmart.framework.service.ServiceClient;
import com.redmart.redmart.interfaces.IProductListViewListener;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.models.ProductDetailsResponse;
import com.redmart.redmart.models.ProductListResponse;
import com.redmart.redmart.models.Status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NetworkUtil.class, CloudDataConnector.class, ServiceClient.class})
public class ProductListPresenterTest {

    private final static String API_BASE_URL = "https://api.redmart.com/v1.6.0/catalog/";
    private static final int PAGE_SIZE = 20;
    private ProductListPresenter mPresenter;

    @Rule
    public Timeout globalTimeout = Timeout.millis(CommonMethodForTest.TEST_TIMEOUT);

    @Mock
    private IProductListViewListener mListener;

    @Mock
    private CloudDataConnector mCloudDataConnector;

    @Captor
    private ArgumentCaptor<ResponseHandler<ProductListResponse>> mProductListCaptor;

    @Captor
    private ArgumentCaptor<ResponseHandler<ProductDetailsResponse>> mProductDetailsCaptor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockStatic(NetworkUtil.class);
        mockStatic(CloudDataConnector.class);
        mockStatic(ServiceClient.class);
        when(NetworkUtil.isNetworkConnected()).thenReturn(true);
        when(CloudDataConnector.create(ServiceClient.getRestService(API_BASE_URL))).thenReturn(mCloudDataConnector);
        mPresenter = new ProductListPresenter();
        mPresenter.attachView(mListener);
        mPresenter.setAPIBaseUrl(API_BASE_URL);
    }

    @Test
    public void onActivityLoadedWhenNoNetwork() {
        when(NetworkUtil.isNetworkConnected()).thenReturn(false);
        mPresenter.onActivityLoaded();
        verify(mListener).onError(R.string.title_network_unavailable, R.string.msg_network_unavailable);
    }

    @Test
    public void onActivityLoadedWhenNetworkAvailable() {
        mPresenter.onActivityLoaded();

        //Verify notify adapter to show 'Load more' indicator
        verify(mListener).notifyAdapter();

        //Verify API call
        verify(mCloudDataConnector, times(1)).getProductList(eq(1), eq(PAGE_SIZE), mProductListCaptor.capture());
        ResponseHandler<ProductListResponse> responseHandler = mProductListCaptor.getValue();

        //Verify API error
        responseHandler.onRequestFailure("Server error");
        verify(mListener).onError(R.string.title_server_unavailable, R.string.msg_server_unavailable);
    }

    @Test
    public void onProductListAPISuccess() {
        mPresenter.onActivityLoaded();
        verify(mListener, times(1)).notifyAdapter();

        //Verify API call
        verify(mCloudDataConnector, times(1)).getProductList(eq(1), eq(PAGE_SIZE), mProductListCaptor.capture());
        ResponseHandler<ProductListResponse> responseHandler = mProductListCaptor.getValue();

        //Verify API success
        ProductListResponse response = mock(ProductListResponse.class);
        Status productStatus = mock(Status.class);
        when(productStatus.getCode()).thenReturn(0);
        when(response.getStatus()).thenReturn(productStatus);
        when(response.getTotalProduct()).thenReturn(2000);
        List<Product> productList = new ArrayList<>();
        Product product = mock(Product.class);
        productList.add(product);
        when(response.getListProduct()).thenReturn(productList);

        int oldProductListSize = mPresenter.getListProductDetails().size();
        responseHandler.onRequestSuccess(response);
        assertEquals(oldProductListSize + productList.size(), mPresenter.getListProductDetails().size());
        assertEquals(mPresenter.getListProductDetails().get(oldProductListSize), product);

        //Verify notify adapter
        verify(mListener, times(2)).notifyAdapter();
    }

    @Test
    public void onRefreshWhenNoNetwork() {
        when(NetworkUtil.isNetworkConnected()).thenReturn(false);

        mPresenter.onRefresh();
        verify(mListener).onError(R.string.title_network_unavailable, R.string.msg_network_unavailable);

        //Verify notify adapter to hide 'Swipe refresh' indicator
        verify(mListener).notifyAdapter();
    }

    @Test
    public void onRefreshWhenNetworkAvailable() {
        mPresenter.onRefresh();

        //Verify API call
        verify(mCloudDataConnector, times(1)).getProductList(eq(1), eq(PAGE_SIZE), mProductListCaptor.capture());
        ResponseHandler<ProductListResponse> responseHandler = mProductListCaptor.getValue();

        //Verify API success
        ProductListResponse response = mock(ProductListResponse.class);
        Status productStatus = mock(Status.class);
        when(productStatus.getCode()).thenReturn(0);
        when(response.getStatus()).thenReturn(productStatus);
        when(response.getTotalProduct()).thenReturn(2000);
        List<Product> productList = new ArrayList<>();
        Product product = mock(Product.class);
        productList.add(product);
        when(response.getListProduct()).thenReturn(productList);

        //Assume 'few product' available
        mPresenter.getListProductDetails().add(product);
        mPresenter.getListProductDetails().add(product);
        mPresenter.getListProductDetails().add(product);

        responseHandler.onRequestSuccess(response);

        //Only 'new product list' should be available
        assertEquals(productList.size(), mPresenter.getListProductDetails().size());

        //Verify notify adapter
        verify(mListener).notifyAdapter();
    }

    @Test
    public void onLoadMoreScrolled() throws NoSuchFieldException, IllegalAccessException {
        int totalProduct = 2000;
        CommonMethodForTest.setFieldValue(mPresenter, "mTotalProduct", totalProduct);
        CommonMethodForTest.setFieldValue(mPresenter, "mIsOnLoadMore", false);

        //Check it should call API and should set 'mIsOnLoadMore' to true
        mPresenter.onLoadMore();
        assertTrue(mPresenter.isLoadMore());

        //Verify API call
        verify(mCloudDataConnector, times(1)).getProductList(eq(1), eq(PAGE_SIZE), mProductListCaptor.capture());
    }

    @Test
    public void onListItemClickedAPIFailure() {
        Product product = mock(Product.class);
        when(product.getProductId()).thenReturn(1234);
        mPresenter.getListProductDetails().add(product);

        mPresenter.onListItemClick(0, mock(ImageView.class));

        //Verify 'ProgressDialog' show
        verify(mListener).showProgressDialog();

        //Verify API call
        verify(mCloudDataConnector, times(1)).getProductDetails(eq(1234), mProductDetailsCaptor.capture());

        //Verify API error
        ResponseHandler<ProductDetailsResponse> responseHandler = mProductDetailsCaptor.getValue();
        responseHandler.onRequestFailure("Server error");
        verify(mListener).onError(R.string.title_server_unavailable, R.string.msg_server_unavailable);
    }

    @Test
    public void onListItemClickedAPISuccess() {
        ImageView imageView = mock(ImageView.class);
        Product product = mock(Product.class);
        when(product.getProductId()).thenReturn(1234);
        mPresenter.getListProductDetails().add(product);

        mPresenter.onListItemClick(0, imageView);

        //Verify 'ProgressDialog' show
        verify(mListener).showProgressDialog();

        //Verify API call
        verify(mCloudDataConnector, times(1)).getProductDetails(eq(1234), mProductDetailsCaptor.capture());

        ResponseHandler<ProductDetailsResponse> responseHandler = mProductDetailsCaptor.getValue();
        ProductDetailsResponse response = mock(ProductDetailsResponse.class);
        Status productStatus = mock(Status.class);
        when(productStatus.getCode()).thenReturn(2000);
        when(response.getStatus()).thenReturn(productStatus);
        responseHandler.onRequestSuccess(response);

        //Verify 'ProgressDialog' hide
        verify(mListener).hideProgressDialog();

        //Verify 'ProductSelected' call with API Response and imageview(for animation)
        verify(mListener).onProductSelected(response.getProduct(), imageView);
    }
}