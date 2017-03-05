package com.app.shopandmoviesback.model;


import com.app.shopandmoviesback.model.response.MoviesList;

public interface IMoviesListInteractor {
    void getMoviesList(String api_key, String language, String sort_by,
                       boolean include_adult, boolean include_video,
                       int page, int primary_release_year, IMoviesListInteractor.Callback callback);

    interface Callback {
        void onSuccess(MoviesList response);
        void onError(int code, String error);
    }
}
