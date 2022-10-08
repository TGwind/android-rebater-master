package com.rebater.cash.well.fun.essential;

import android.content.Context;

public interface OverView {
    void showReqProgress(boolean flag, String message);

    void hideReqProgress();

    void showToast(int resId);

    void showToast(String msg);

    Context  getContext();

    void onError();

}
