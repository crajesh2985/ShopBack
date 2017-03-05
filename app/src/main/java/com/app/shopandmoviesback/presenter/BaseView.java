package com.app.shopandmoviesback.presenter;


public interface BaseView {

    void showProgress();
    void hideProgress();
    void showError(int code, String message);
}
