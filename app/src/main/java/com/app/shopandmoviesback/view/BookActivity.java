package com.app.shopandmoviesback.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.app.shopandmoviesback.R;
import com.app.shopandmoviesback.appconstant.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookActivity extends AppCompatActivity {

    @BindView(R.id.appWebView)
    WebView appWebView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        setTitle(null);
        appWebView.getSettings().setJavaScriptEnabled(true);
        appWebView.getSettings().setLoadWithOverviewMode(true);
        appWebView.getSettings().setUseWideViewPort(true);
        appWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {

            }
        });
        appWebView.loadUrl(AppConstant.BOOK_SITE);
    }

}
