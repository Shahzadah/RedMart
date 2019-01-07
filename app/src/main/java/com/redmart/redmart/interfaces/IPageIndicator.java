package com.redmart.redmart.interfaces;

import android.support.v4.view.ViewPager;

/**
 * A IPageIndicator is responsible to show an visual indicator on the total views
 * number and the current visible view.
 */
public interface IPageIndicator extends ViewPager.OnPageChangeListener {
    
    void setViewPager(ViewPager view);

    void setViewPager(ViewPager view, int initialPosition);

    void setCurrentItem(int item);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener listener);

    void notifyDataSetChanged();
}
