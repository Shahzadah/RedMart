package com.redmart.redmart.widgets;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

	private final LinearLayoutManager mLinearLayoutManager;

	public RecyclerViewOnScrollListener(LinearLayoutManager linearLayoutManager) {
		this.mLinearLayoutManager = linearLayoutManager;
	}

	@Override
	public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
		super.onScrolled(recyclerView, dx, dy);
		if(dy < 0){
			return;
		}
		int visibleThreshold = 5;
		int visibleItemCount = recyclerView.getChildCount();
		int totalItemCount = mLinearLayoutManager.getItemCount();
		int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

		if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			// End has been reached
			onLoadMore();
		}
	}
	public abstract void onLoadMore();
} 