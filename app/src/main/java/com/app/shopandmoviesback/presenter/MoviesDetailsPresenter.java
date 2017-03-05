package com.app.shopandmoviesback.presenter;


import com.app.shopandmoviesback.model.response.MovieDetails;

public interface MoviesDetailsPresenter {

    interface View extends BaseView {
        void onGetMoviesDetailsSuccessful(MovieDetails movieDetails);
    }

    void getMoviesDetails(int movieId, String api_key, String language);
}
