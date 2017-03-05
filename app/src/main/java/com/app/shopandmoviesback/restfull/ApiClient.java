package com.app.shopandmoviesback.restfull;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.app.shopandmoviesback.appconstant.AppConstant;
import com.app.shopandmoviesback.utils.ConnectivityInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    private static Retrofit retrofit = null;
    private static Context mContext;


    public static Retrofit getClient(Context context, Activity activity) {
        if (retrofit == null) {
            mContext = context;

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(25, TimeUnit.SECONDS)
                    .readTimeout(25, TimeUnit.SECONDS)
                    .writeTimeout(25, TimeUnit.SECONDS)
                    .addInterceptor(new ConnectivityInterceptor(mContext, activity))
                    .addInterceptor(
                            new HttpLoggingInterceptor(
                                    new HttpLoggingInterceptor.Logger() {
                                        @Override
                                        public void log(String message) {
                                            Log.d("TAG_WEBSERVICE", "response ::::: " + message);
                                        }
                                    }
                            ).setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConstant.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
