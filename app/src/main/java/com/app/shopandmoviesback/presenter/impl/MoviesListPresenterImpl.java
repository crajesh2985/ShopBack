package com.app.shopandmoviesback.presenter.impl;


import com.app.shopandmoviesback.model.IMoviesListInteractor;
import com.app.shopandmoviesback.model.response.MoviesList;
import com.app.shopandmoviesback.presenter.MoviesListPresenter;

public class MoviesListPresenterImpl implements MoviesListPresenter {
    private IMoviesListInteractor moviesListInteractor;

    private MoviesListPresenter.View mView;

    public MoviesListPresenterImpl(View view, IMoviesListInteractor moviesListInteractor) {
        this.mView = view;
        this.moviesListInteractor = moviesListInteractor;
    }


    @Override
    public void getMoviesList(String api_key, String language, String sort_by,
                              boolean include_adult, boolean include_video,
                              int page, int primary_release_year) {

        mView.showProgress();
        moviesListInteractor.getMoviesList(api_key, language, sort_by,
                include_adult, include_video,
                page, primary_release_year, new IMoviesListInteractor.Callback() {
                    @Override
                    public void onSuccess(MoviesList response) {
                        mView.onGetMoviesListSuccessful(response);
                        mView.hideProgress();
                    }

                    @Override
                    public void onError(int code, String error) {
                        mView.showError(code, error);
                        mView.hideProgress();
                    }
                });
    }
}
