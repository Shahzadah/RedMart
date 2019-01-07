package com.redmart.redmart.interfaces;

import android.view.View;

public interface IRecyclerViewListClickListener {

    /**
     * Called when the view is clicked.
     *
     * @param v view that is clicked
     * @param position of the clicked item
     */
    void onClick(View v, int position);
}
