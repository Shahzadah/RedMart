package com.redmart.redmart.interfaces;

import android.widget.ImageView;

import com.redmart.redmart.models.Product;

import java.util.List;

public interface IProductListViewActionListener extends IViewActionListener {

    void setAPIBaseUrl(String url);

    List<Product> getListProductDetails();

    void onActivityLoaded();

    void onRefresh();

    void onLoadMore();

    boolean isLoadMore();

    void onListItemClick(int position, ImageView imageView);
}