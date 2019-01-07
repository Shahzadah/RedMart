package com.redmart.redmart.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.redmart.redmart.R;
import com.redmart.redmart.interfaces.IProductListViewActionListener;
import com.redmart.redmart.interfaces.IRecyclerViewListClickListener;
import com.redmart.redmart.models.ImageDetails;
import com.redmart.redmart.models.Product;
import com.redmart.redmart.widgets.DotsProgressBar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {


    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOAD_MORE = 2;

    private Context mContext;
    private List<Product> mListProductDetails;
    private IProductListViewActionListener mListener;

    private ProductListAdapter(Context context) {
        this.mContext = context;
    }

    /**
     * Create adapter & returns it.
     *
     * @return : Return the adapter reference
     */
    public static ProductListAdapter create(Context context) {
        return new ProductListAdapter(context);
    }

    /**
     * Set Data of adapter.
     *
     * @param listProductDetails : List of product
     * @return : Return the adapter reference
     */
    public ProductListAdapter withData(List<Product> listProductDetails) {
        this.mListProductDetails = listProductDetails;
        return this;
    }

    /**
     * Set Listener in adapter for view action.
     *
     * @param listener : Callback listener to communicate with presenter
     * @return : Return the adapter reference
     */
    public ProductListAdapter withListener(IProductListViewActionListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_marketplace_item, parent, false);
            return new ViewHolder(v, viewType);
        } else if (viewType == TYPE_LOAD_MORE) {
            View v = new DotsProgressBar(mContext);
            int padding = (int) mContext.getResources().getDimension(R.dimen.padding_10dp);
            v.setPadding(padding, padding, padding, padding);
            return new ViewHolder(v, viewType);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (holder._Id == 1) {
            Product product = mListProductDetails.get(position);
            holder.tvTitle.setText(product.getTitle());
            holder.tvDescription.setText(product.getDescription());
            holder.tvPrice.setText("$" + product.getPriceDetails().getPrice());
            holder.ivProductImage.setImageDrawable(holder.mNoImageDrawable);
            holder.pbProductImage.setVisibility(View.VISIBLE);
            ImageDetails imageDetails = product.getImageDetails();
            if (imageDetails != null) {
                String imageUrl = holder.mImageURLPrefix + imageDetails.getName();
                Picasso picasso = Picasso.with(mContext);
                picasso.setIndicatorsEnabled(false);
                picasso.load(imageUrl).
                        placeholder(R.drawable.no_image).
                        into(holder.ivProductImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.pbProductImage.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                holder.pbProductImage.setVisibility(View.GONE);
                            }
                        });
            } else {
                holder.pbProductImage.setVisibility(View.GONE);
            }
            holder.setClickListener(new IRecyclerViewListClickListener() {

                @Override
                public void onClick(View v, int position) {
                    mListener.onListItemClick(position, holder.ivProductImage);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mListener.isLoadMore()) {
            return mListProductDetails.size() + 1;
        }
        return mListProductDetails.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mListener.isLoadMore() && mListProductDetails.size() == position) {
            return TYPE_LOAD_MORE;
        }
        return TYPE_ITEM;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int _Id;

        @BindView(R.id.ivProductImage)
        ImageView ivProductImage;

        @BindView(R.id.textview_title)
        TextView tvTitle;

        @BindView(R.id.textview_description)
        TextView tvDescription;

        @BindView(R.id.textview_price)
        TextView tvPrice;

        @BindView(R.id.pbProductImage)
        ProgressBar pbProductImage;

        @BindDrawable(R.drawable.no_image)
        Drawable mNoImageDrawable;

        @BindString(R.string.IMAGE_BASE_URL)
        String mImageURLPrefix;

        private IRecyclerViewListClickListener clickListener;

        ViewHolder(View itemView, int ViewType) {
            super(itemView);
            if (ViewType == TYPE_ITEM) {
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
                _Id = 1;
            } else
                _Id = 2;
        }

        /**
         * Setter for listener.
         */
        void setClickListener(IRecyclerViewListClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }
}