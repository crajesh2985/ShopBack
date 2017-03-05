package com.app.shopandmoviesback.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.app.shopandmoviesback.R;

public class CommonUtils {

    private CommonUtils() {
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        boolean flag = netInfo != null && netInfo.isConnected();
        if(!flag) {
            Toast.makeText(context, R.string.str_check_network, Toast.LENGTH_SHORT).show();
        }
        return flag;

    }

}
