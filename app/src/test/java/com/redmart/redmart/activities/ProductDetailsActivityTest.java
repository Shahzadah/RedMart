package com.redmart.redmart.activities;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.redmart.redmart.R;
import com.redmart.redmart.models.ImageDetails;
import com.redmart.redmart.models.PriceDetails;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.models.ProductMeasure;
import com.redmart.redmart.models.ProductOriginDetails;
import com.redmart.redmart.widgets.ViewPagerCustomDuration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowApplication;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;

@RunWith(RobolectricTestRunner.class)
public class ProductDetailsActivityTest {

    private ActivityController mActivityController;
    private ProductDetailsActivity mActivity;

    private Toolbar toolbar;
    private FrameLayout flImagePager;
    private ImageView ivNoImage;
    private ViewPagerCustomDuration vpImagePager;
    private CircleIndicator cpiCircleIndicator;
    private TextView tvTitle;
    private TextView tvWeight;
    private TextView tvPrice;
    private TextView tvCountryOrigin;
    private TextView tvProductDesc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(), ProductDetailsActivity.class);
        intent.putExtra("ProductSelected", getProduct());
        mActivityController = Robolectric.buildActivity(ProductDetailsActivity.class, intent).create();
        mActivity = (ProductDetailsActivity) mActivityController.get();
        toolbar = mActivity.findViewById(R.id.toolbar);
        flImagePager = mActivity.findViewById(R.id.flImagePager);
        ivNoImage = mActivity.findViewById(R.id.ivNoImage);
        vpImagePager = mActivity.findViewById(R.id.vpImagePager);
        cpiCircleIndicator = mActivity.findViewById(R.id.cpiCircleIndicator);
        tvTitle = mActivity.findViewById(R.id.textview_title);
        tvWeight = mActivity.findViewById(R.id.textview_weight);
        tvPrice = mActivity.findViewById(R.id.textview_price);
        tvCountryOrigin = mActivity.findViewById(R.id.textview_country_origin);
        tvProductDesc = mActivity.findViewById(R.id.textview_product_description);
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
        assertNotNull(flImagePager);
        assertNotNull(ivNoImage);
        assertNotNull(vpImagePager);
        assertNotNull(cpiCircleIndicator);
        assertNotNull(tvTitle);
        assertNotNull(tvWeight);
        assertNotNull(tvPrice);
        assertNotNull(tvCountryOrigin);
        assertNotNull(tvProductDesc);
    }

    @Test
    public void setValuesTest() {
        assertEquals(tvTitle.getText(), "Product Title");
        assertEquals(tvProductDesc.getText(), "Product Description");
        assertEquals(tvWeight.getText(), "150 g");
        assertTrue(tvWeight.getVisibility() == View.VISIBLE);
        assertEquals(tvPrice.getText(), "$3.5");
        assertEquals(tvCountryOrigin.getText(), "Australia");
        assertTrue(ivNoImage.getVisibility() == View.VISIBLE);
    }

    private Product getProduct() {
        Product product = mock(Product.class);
        when(product.getTitle()).thenReturn("Product Title");
        when(product.getDescription()).thenReturn("Product Description");

        ProductMeasure productMeasure = mock(ProductMeasure.class);
        when(productMeasure.getWeightVolume()).thenReturn("150 g");
        when(product.getMeasure()).thenReturn(productMeasure);

        PriceDetails priceDetails = mock(PriceDetails.class);
        when(priceDetails.getPrice()).thenReturn(3.5);
        when(product.getPriceDetails()).thenReturn(priceDetails);

        ProductOriginDetails originDetails = mock(ProductOriginDetails.class);
        when(originDetails.getCountryOrigin()).thenReturn("Australia");
        when(product.getOriginDetails()).thenReturn(originDetails);

        List<ImageDetails> listProductImage = new ArrayList<>();
        when(product.getListImages()).thenReturn(listProductImage);
        return product;
    }
}