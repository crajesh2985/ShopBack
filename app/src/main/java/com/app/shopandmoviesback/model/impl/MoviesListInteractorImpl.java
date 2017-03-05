package com.app.shopandmoviesback.model.impl;

import android.content.Context;

import com.app.shopandmoviesback.model.IMoviesListInteractor;
import com.app.shopandmoviesback.model.response.MoviesList;
import com.app.shopandmoviesback.restfull.ApiInterface;
import com.app.shopandmoviesback.utils.NoConnectivityException;

import retrofit2.Call;
import retrofit2.Response;

public class MoviesListInteractorImpl implements IMoviesListInteractor {
    private Context context;
    private ApiInterface apiService;

    public MoviesListInteractorImpl(Context context, ApiInterface apiService) {
        this.context = context;
        this.apiService = apiService;
    }

    @Override
    public void getMoviesList(String api_key, String language, String sort_by,
                              boolean include_adult, boolean include_video,
                              int page, int primary_release_year, final Callback callback) {
        Call<MoviesList> call = apiService.getMoviesListRequest(api_key,
                language, sort_by, include_adult,
                include_video, page, primary_release_year);
        call.enqueue(new retrofit2.Callback<MoviesList>() {
            @Override
            public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                if (response.body() != null) {
                    MoviesList moviesList = response.body();
                    callback.onSuccess(moviesList);
                } else {
                    callback.onError(500, "Server Error");
                }
            }

            @Override
            public void onFailure(Call<MoviesList> call, Throwable t) {
                if (t instanceof NoConnectivityException) {
                    callback.onError(599, t.getMessage());
                } else {
                    callback.onError(598, "TimeOut Error");
                }
            }
        });

    }
}



