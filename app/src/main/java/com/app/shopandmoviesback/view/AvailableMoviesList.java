package com.app.shopandmoviesback.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.app.shopandmoviesback.R;
import com.app.shopandmoviesback.adapter.MovieListAdapter;
import com.app.shopandmoviesback.appconstant.AppConstant;
import com.app.shopandmoviesback.listener.OnLoadMoreListener;
import com.app.shopandmoviesback.model.impl.MoviesListInteractorImpl;
import com.app.shopandmoviesback.model.response.MoviesList;
import com.app.shopandmoviesback.model.response.MoviesListResult;
import com.app.shopandmoviesback.presenter.MoviesListPresenter;
import com.app.shopandmoviesback.presenter.impl.MoviesListPresenterImpl;
import com.app.shopandmoviesback.restfull.ApiClient;
import com.app.shopandmoviesback.restfull.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AvailableMoviesList extends AppCompatActivity implements MoviesListPresenter.View, OnLoadMoreListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private MoviesListPresenter moviesListPresenter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private MovieListAdapter movieListAdapter;
    private int pageNo = 1;
    public ApiInterface apiService;

    List<MoviesListResult> moviesListResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_movies_list);
        ButterKnife.bind(this);
        if (apiService == null) {
            apiService = ApiClient.getClient(getApplicationContext(), AvailableMoviesList.this).create(ApiInterface.class);
        }
        moviesListPresenter = new MoviesListPresenterImpl(
                this,
                new MoviesListInteractorImpl(this, apiService)
        );
        recyclerViewLayoutManager = new GridLayoutManager(AvailableMoviesList.this, 2);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        moviesListPresenter.getMoviesList(AppConstant.APP_WEBSERICE_KEY,
                AppConstant.APP_LANGUAGE, "popularity.desc", false, false, pageNo, 2017);

        movieListAdapter = new MovieListAdapter(AvailableMoviesList.this, moviesListResults,
                recyclerView, AvailableMoviesList.this);
        recyclerView.setAdapter(movieListAdapter);
    }


    @Override
    public void onGetMoviesListSuccessful(MoviesList moviesList) {
        moviesListResults.addAll(moviesList.getResults());
        movieListAdapter.updateRecord(moviesListResults);
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(int code, String message) {
        Toast.makeText(AvailableMoviesList.this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore() {
        Handler customHandler = new Handler();
        customHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo++;
                moviesListPresenter.getMoviesList(AppConstant.APP_WEBSERICE_KEY,
                        AppConstant.LANGUAGE,
                        "popularity.desc", false, false, pageNo, 2017);
            }
        }, 2000);
    }
}
