package com.app.shopandmoviesback.utils;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ConnectivityInterceptor implements Interceptor {

    private Context mContext;
    public Activity activity;

    public ConnectivityInterceptor(Context context, Activity activity) {
        mContext = context;
        this.activity = activity;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!CommonUtils.isOnline(mContext)) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}
