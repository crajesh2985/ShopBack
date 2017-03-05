package com.app.shopandmoviesback.presenter.impl;


import com.app.shopandmoviesback.model.IMoviesDetailsInteractor;
import com.app.shopandmoviesback.model.response.MovieDetails;
import com.app.shopandmoviesback.presenter.MoviesDetailsPresenter;

public class MoviesDetailPresenterImpl implements MoviesDetailsPresenter {
    private IMoviesDetailsInteractor moviesDetailsInteractor;

    private View mView;

    public MoviesDetailPresenterImpl(View view, IMoviesDetailsInteractor moviesDetailsInteractor) {
        this.mView = view;
        this.moviesDetailsInteractor = moviesDetailsInteractor;
    }


    @Override
    public void getMoviesDetails(int movieId, String api_key, String language) {
        mView.showProgress();
        moviesDetailsInteractor.getMoviesDetails(movieId, api_key, language, new IMoviesDetailsInteractor.Callback() {
            @Override
            public void onSuccess(MovieDetails response) {
                mView.onGetMoviesDetailsSuccessful(response);
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
