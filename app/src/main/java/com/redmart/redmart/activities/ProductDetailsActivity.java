package com.redmart.redmart.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.redmart.redmart.R;
import com.redmart.redmart.adapters.ImagePagerAdapter;
import com.redmart.redmart.framework.base.BaseActivity;
import com.redmart.redmart.framework.base.BasePresenter;
import com.redmart.redmart.framework.base.MVPView;
import com.redmart.redmart.models.ImageDetails;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.widgets.ViewPagerCustomDuration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class ProductDetailsActivity extends BaseActivity {

    private static final String PRODUCT_SELECTED = "ProductSelected";
    private Product mProduct;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.flImagePager)
    FrameLayout flImagePager;

    @BindView(R.id.ivNoImage)
    ImageView ivNoImage;

    @BindView(R.id.vpImagePager)
    ViewPagerCustomDuration vpImagePager;

    @BindView(R.id.cpiCircleIndicator)
    CircleIndicator cpiCircleIndicator;

    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.textview_weight)
    TextView tvWeight;

    @BindView(R.id.textview_price)
    TextView tvPrice;

    @BindView(R.id.textview_country_origin)
    TextView tvCountryOrigin;

    @BindView(R.id.textview_product_description)
    TextView tvProductDesc;

    /**
     * To start this activity.
     *
     * @param activity        :   calling activity
     * @param transitionView  :   transition view
     * @param productSelected :   selected product details
     */
    public static void launch(BaseActivity activity, View transitionView, Product productSelected) {
        Intent intent = new Intent(activity, ProductDetailsActivity.class);
        intent.putExtra(PRODUCT_SELECTED, productSelected);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && transitionView != null) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(activity, transitionView, activity.getString(R.string.transition_product_details));
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent() != null && getIntent().hasExtra(PRODUCT_SELECTED)) {
            mProduct = getIntent().getParcelableExtra(PRODUCT_SELECTED);
            setValues();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();
        switch (menuItemId) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void setValues() {
        setImageViewPager();
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(mProduct.getTitle());
        }
        tvTitle.setText(mProduct.getTitle());
        if(mProduct.getMeasure() != null) {
            tvWeight.setVisibility(View.VISIBLE);
            tvWeight.setText(mProduct.getMeasure().getWeightVolume());
        } else {
            tvWeight.setVisibility(View.GONE);
        }
        tvPrice.setText("$" + mProduct.getPriceDetails().getPrice());
        if(mProduct.getOriginDetails() != null) {
            tvCountryOrigin.setText(mProduct.getOriginDetails().getCountryOrigin());
        }
        tvProductDesc.setText(mProduct.getDescription());
    }

    private void setImageViewPager() {
        double screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        double imageWidth = 640;
        double imageHeight = 480;
        double height = screenWidth * (imageHeight / imageWidth);

        //Set adapter
        List<ImageDetails> listProductImage = mProduct.getListImages();
        if (listProductImage == null || listProductImage.size() == 0) {
            ivNoImage.setVisibility(View.VISIBLE);
        } else {
            ImagePagerAdapter mSliderAdapter = ImagePagerAdapter.create(mThisActivity).withData(listProductImage, (int) screenWidth, (int) height);
            vpImagePager.setAdapter(mSliderAdapter);
            vpImagePager.setScrollDurationFactor(2);
            if (listProductImage.size() > 1) {
                cpiCircleIndicator.setViewPager(vpImagePager);
            }
        }
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected MVPView getView() {
        return null;
    }
}
