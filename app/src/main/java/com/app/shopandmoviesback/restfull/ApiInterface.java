package com.app.shopandmoviesback.restfull;


import com.app.shopandmoviesback.appconstant.AppConstant;
import com.app.shopandmoviesback.model.response.MovieDetails;
import com.app.shopandmoviesback.model.response.MoviesList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    @GET(AppConstant.GET_MOVIE_LIST)
    Call<MoviesList> getMoviesListRequest
            (@Query(AppConstant.API_KEY) String api_key,
             @Query(AppConstant.LANGUAGE) String language,
             @Query(AppConstant.SORT_BY) String sort_by,
             @Query(AppConstant.INCLUDE_ADULT) boolean include_adult,
             @Query(AppConstant.INCLUDE_VIDEO) boolean include_video,
             @Query(AppConstant.PAGE) int page,
             @Query(AppConstant.PRIMARY_RELEASE_YEAR) int primary_release_year);

    @GET(AppConstant.GET_MOVIE_DETAILS)
    Call<MovieDetails> getMoviesDetailsRequest
            (@Path(AppConstant.MOVIE_ID) int movies_id,
             @Query(AppConstant.API_KEY) String api_key,
             @Query(AppConstant.LANGUAGE) String language);


}
