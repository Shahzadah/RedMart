package com.redmart.redmart.interfaces;

import android.widget.ImageView;

import com.redmart.redmart.framework.base.MVPView;
import com.redmart.redmart.models.Product;

public interface IProductListViewListener extends MVPView {

    void onProductSelected(Product product, ImageView imageView);

    void notifyAdapter();

    void showProgressDialog();

    void hideProgressDialog();
}