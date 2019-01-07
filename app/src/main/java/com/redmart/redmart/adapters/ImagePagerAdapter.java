package com.redmart.redmart.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.redmart.redmart.R;
import com.redmart.redmart.models.ImageDetails;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageDetails> mListProductImages;
    private int mSliderWidth;
    private int mSliderHeight;
    private LayoutInflater mInflater;
    private String mImageBaseURL;

    private ImagePagerAdapter(Context context) {
        this.mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageBaseURL = context.getResources().getString(R.string.IMAGE_BASE_URL);
    }

    public static ImagePagerAdapter create(Context context) {
        return new ImagePagerAdapter(context);
    }

    public ImagePagerAdapter withData(List<ImageDetails> listProductImages, int sliderWidth, int sliderHeight) {
        this.mListProductImages = listProductImages;
        this.mSliderWidth = sliderWidth;
        this.mSliderHeight = sliderHeight;
        return this;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = mInflater.inflate(R.layout.row_product_image_pager, container, false);
        final ImageView imageView = view.findViewById(R.id.ivProductImage);
        final ProgressBar pbProductImage = view.findViewById(R.id.pbProductImage);
        pbProductImage.setVisibility(View.VISIBLE);
        Picasso.with(mContext).
                load(mImageBaseURL + mListProductImages.get(position).getName())
                .resize(mSliderWidth, mSliderHeight)
                .centerInside()
                .placeholder(R.drawable.no_image)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        pbProductImage.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        pbProductImage.setVisibility(View.GONE);
                    }
                });
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mListProductImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}