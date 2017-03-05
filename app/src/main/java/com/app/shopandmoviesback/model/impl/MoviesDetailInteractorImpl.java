package com.app.shopandmoviesback.model.impl;

import android.content.Context;
import android.util.Log;

import com.app.shopandmoviesback.model.IMoviesDetailsInteractor;
import com.app.shopandmoviesback.model.response.MovieDetails;
import com.app.shopandmoviesback.restfull.ApiInterface;
import com.app.shopandmoviesback.utils.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class MoviesDetailInteractorImpl implements IMoviesDetailsInteractor {
    private Context context;
    private ApiInterface apiService;

    public MoviesDetailInteractorImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public void getMoviesDetails(int movie_id, String api_key, String language, final Callback callback) {
        Call<MovieDetails> call = apiService.getMoviesDetailsRequest(movie_id, api_key,
                language);
        call.enqueue(new retrofit2.Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.body() != null) {
                    MovieDetails movieDetails = response.body();
                    callback.onSuccess(movieDetails);
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });
    }
}



