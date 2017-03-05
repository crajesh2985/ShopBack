package com.app.shopandmoviesback.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.app.shopandmoviesback.R;
import com.app.shopandmoviesback.appconstant.AppConstant;
import com.app.shopandmoviesback.listener.OnLoadMoreListener;
import com.app.shopandmoviesback.model.response.MoviesListResult;
import com.app.shopandmoviesback.view.AvailableMoviesList;
import com.app.shopandmoviesback.view.MoviesDetailsActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<MoviesListResult> moviesListResults;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;


    public MovieListAdapter(AvailableMoviesList context,
                            List<MoviesListResult> moviesListResults,
                            RecyclerView recyclerView, OnLoadMoreListener onLoadMoreListener) {
        this.context = context;
        this.moviesListResults = moviesListResults;
        mOnLoadMoreListener = onLoadMoreListener;

        final GridLayoutManager linearLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int sum = lastVisibleItem + visibleThreshold;
                if (!isLoading && totalItemCount <= sum) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mMovieTitle;
        public ImageView mMovieImage;
        public LinearLayout mMovieDetails;
        public RatingBar mMovieRate;
        public TextView mRatingCount, mPopularity;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            mMovieTitle = (TextView) view.findViewById(R.id.tv_movie_name);
            mMovieImage = (ImageView) view.findViewById(R.id.iv_movie_image);
            mMovieDetails = (LinearLayout) view.findViewById(R.id.ll_movie_details);
            mMovieRate = (RatingBar) view.findViewById(R.id.rb_movie_rating);
            mRatingCount = (TextView) view.findViewById(R.id.tv_movie_total_rating_count);
            mPopularity = (TextView) view.findViewById(R.id.tv_movie_popularity);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, MoviesDetailsActivity.class);
            intent.putExtra("position", moviesListResults.get(getPosition()).getId());
            context.startActivity(intent);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movies_view, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder userViewHolder = (MyViewHolder) holder;
            userViewHolder.mMovieTitle.setText(moviesListResults.get(position).getTitle());
            userViewHolder.mMovieRate.setRating(moviesListResults.get(position).getVote_average());
            userViewHolder.mPopularity.setText(context.getString(R.string.str_popularity) + moviesListResults.get(position).getPopularity());

            Glide.with(context)
                    .load(AppConstant.BASE_IMAGE_URL + moviesListResults.get(position).getBackdrop_path())
                    .asBitmap().fitCenter().error(R.drawable.no_image_available)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            userViewHolder.mMovieImage.setImageBitmap(resource);

                            Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                                public void onGenerated(Palette palette) {
                                    Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                                    if (vibrantSwatch != null) {
                                        userViewHolder.mMovieDetails.setBackgroundColor(vibrantSwatch.getRgb());
                                        userViewHolder.mMovieTitle.setTextColor(vibrantSwatch.getBodyTextColor());
                                        userViewHolder.mPopularity.setTextColor(vibrantSwatch.getBodyTextColor());
                                    }
                                }
                            });

                        }
                    });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return moviesListResults.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return moviesListResults.size() == position ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void updateRecord(List<MoviesListResult> moviesListResults) {
        isLoading = false;
        notifyDataSetChanged();
    }


}
