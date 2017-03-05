package com.app.shopandmoviesback.presenter;


import com.app.shopandmoviesback.model.response.MoviesList;

public interface MoviesListPresenter {

    interface View extends BaseView {
        void onGetMoviesListSuccessful(MoviesList moviesList);
    }

    void getMoviesList(String api_key, String language, String sort_by,
                       boolean include_adult, boolean include_video,
                       int page, int primary_release_year);
}
