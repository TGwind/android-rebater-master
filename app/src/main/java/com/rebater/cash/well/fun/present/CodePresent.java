package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Kosa;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CodePresent implements IModel.HelpReq {
    IModel.HelpView helpView;

    public CodePresent(IModel.HelpView view) {
        helpView = view;
    }

    @Override
    public void start(String content) {
        WorkRequest.getAllface().askLogin(content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBeanObservable);
    }

    Observer<UserInfo> loginBeanObservable = new Observer<UserInfo>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserInfo userInfo) {
            if (userInfo != null) {
                helpView.onPeizhi(userInfo);
            } else {
                helpView.onLocationError();
            }
        }

        @Override
        public void onError(Throwable e) {
            helpView.onError();
        }

        @Override
        public void onComplete() {

        }

    };

    @Override
    public void upReson(String code) {
        WorkRequest.getAllface().askReason(code).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(codeObservable);
    }

    Observer<Kosa> codeObservable = new Observer<Kosa>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Kosa poloass) {
            if (poloass != null) {
                helpView.onNumber(poloass);
            } else {
                helpView.onError();
            }
        }

        @Override
        public void onError(Throwable e) {
            helpView.onLocationError();
        }

        @Override
        public void onComplete() {

        }

    };


    @Override
    public OverView getBase() {
        return helpView;
    }
}

