package com.app.shopandmoviesback.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shopandmoviesback.R;
import com.app.shopandmoviesback.appconstant.AppConstant;
import com.app.shopandmoviesback.model.impl.MoviesDetailInteractorImpl;
import com.app.shopandmoviesback.model.response.MovieDetails;
import com.app.shopandmoviesback.presenter.MoviesDetailsPresenter;
import com.app.shopandmoviesback.presenter.impl.MoviesDetailPresenterImpl;
import com.app.shopandmoviesback.restfull.ApiClient;
import com.app.shopandmoviesback.restfull.ApiInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesDetailsActivity extends AppCompatActivity implements MoviesDetailsPresenter.View, View.OnClickListener {
    private Context mContext;
    private Activity mActivity;
    private Toolbar mToolbar;
    private MoviesDetailsPresenter moviesDetailsPresenter;
    public ApiInterface apiService;

    @BindView(R.id.iv_movie_image)
    ImageView mMovieImage;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.tv_movie_overview_value)
    TextView mOverviewValue;
    @BindView(R.id.tv_movie_tagline_value)
    TextView mTagLineValue;
    @BindView(R.id.tv_movie_status_value)
    TextView mStatusValue;
    @BindView(R.id.tv_movie_production_companies_value)
    TextView mProductionCompaniesValue;
    @BindView(R.id.tv_movie_budget_value)
    TextView mBudgetValue;
    @BindView(R.id.tl_movie_details)
    TableLayout mMovieDetails;
    @BindView(R.id.btn_book)
    Button mBook;
    @BindView(R.id.tv_movie_genres_value)
    TextView mGenres;
    @BindView(R.id.tv_movie_time_value)
    TextView mTime;
    @BindView(R.id.tv_movie_language_value)
    TextView mLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        int position = getIntent().getIntExtra("position", 0);
        ButterKnife.bind(this);
        if (apiService == null) {
            apiService = ApiClient.getClient(getApplicationContext(), MoviesDetailsActivity.this).create(ApiInterface.class);
        }
        moviesDetailsPresenter = new MoviesDetailPresenterImpl(
                this,
                new MoviesDetailInteractorImpl(this, apiService)
        );
        mContext = getApplicationContext();
        mActivity = MoviesDetailsActivity.this;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        moviesDetailsPresenter.getMoviesDetails(position, AppConstant.APP_WEBSERICE_KEY, AppConstant.APP_LANGUAGE);
        mBook.setOnClickListener(this);
    }


    @Override
    public void onGetMoviesDetailsSuccessful(final MovieDetails movieDetails) {

        mCollapsingToolbarLayout.setTitle(movieDetails.getOriginalTitle());
        if (movieDetails.getOverview() != null) {
            mOverviewValue.setText(movieDetails.getOverview());
        }
        if (movieDetails.getBudget() != null) {
            mBudgetValue.setText("$" + movieDetails.getBudget());
        }
        if (movieDetails.getReleaseDate() != null) {
            mStatusValue.setText(movieDetails.getStatus());
        }

        if (movieDetails.getProductionCompanies().size() > 0) {
            String productComp = "";
            for (int i = 0; i < movieDetails.getProductionCompanies().size(); i++) {
                if (productComp.length() > 2) {
                    productComp = productComp + "\n" + movieDetails.getProductionCompanies().get(i).getName();
                } else {
                    productComp = movieDetails.getProductionCompanies().get(i).getName();
                }
            }
            mProductionCompaniesValue.setText(productComp);
        }

        if (movieDetails.getGenres().size() > 0) {
            String genres = "";
            for (int i = 0; i < movieDetails.getGenres().size(); i++) {
                if (genres.length() > 2) {
                    genres = genres + "\n" + movieDetails.getGenres().get(i).getName();
                } else {
                    genres = movieDetails.getGenres().get(i).getName();
                }
            }
            mGenres.setText(genres);
        }


        if (movieDetails.getOriginalLanguage() != null) {
            if (movieDetails.getOriginalLanguage().equalsIgnoreCase("en")) {
                mLanguage.setText("English");
            } else {
                mLanguage.setText(movieDetails.getOriginalLanguage());
            }
        }

        if (movieDetails.getRuntime() != null) {
            int hours = movieDetails.getRuntime() / 60; //since both are ints, you get an int
            int minutes = movieDetails.getRuntime() % 60;
            mTime.setText(hours + ":" + minutes);
        }


        if (movieDetails.getTagline() != null) {
            mTagLineValue.setText(movieDetails.getTagline());
        }

        Glide.with(MoviesDetailsActivity.this)
                .load(AppConstant.BASE_IMAGE_URL + movieDetails.getPosterPath())
                .asBitmap().fitCenter()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        mMovieImage.setImageBitmap(resource);
                    }
                });

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(int code, String message) {
        Toast.makeText(MoviesDetailsActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_book:
                Intent intent = new Intent(MoviesDetailsActivity.this, BookActivity.class);
                startActivity(intent);
                break;
        }
    }
}
