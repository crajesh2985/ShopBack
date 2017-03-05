package com.app.shopandmoviesback.model;


import com.app.shopandmoviesback.model.response.MovieDetails;

public interface IMoviesDetailsInteractor {
    void getMoviesDetails(int movie_id, String api_key, String language, IMoviesDetailsInteractor.Callback callback);

    interface Callback {
        void onSuccess(MovieDetails response);
        void onError(int code, String error);
    }
}
