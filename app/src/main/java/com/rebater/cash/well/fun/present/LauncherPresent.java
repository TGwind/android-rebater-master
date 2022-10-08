package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LauncherPresent implements IModel.FirstReq {
    IModel.FirstView firstView;

    public LauncherPresent(IModel.FirstView view) {
        firstView = view;
    }

    @Override
    public void start(String content) { //用户登录注册
        WorkRequest.getAllface().askLogin(content).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginBeanObservable);
    }

    @Override
    public void getPatner() { //合作伙伴接口
        WorkRequest.getAllface().askPartener().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(brotherObservable);
    }

    @Override
    public void getMan() { //获取联系人
        WorkRequest.getAllface().getCode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userObservable);
    }

    Observer<Piskos> locationObservable = new Observer<Piskos>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Piskos piskos) {
            if (piskos != null) {
                firstView.onLocation(piskos);
            } else {
                firstView.onLocationError();
            }
        }

        @Override
        public void onError(Throwable e) {
            firstView.onLocationError();
        }

        @Override
        public void onComplete() {

        }
    };

    Observer<UserInfo> loginBeanObservable = new Observer<UserInfo>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserInfo userInfo) {
            firstView.onPeizhi(userInfo);
        }

        @Override
        public void onError(Throwable e) {
            firstView.onError();
        }

        @Override
        public void onComplete() {

        }
    };


    Observer<List<Oiswd>> brotherObservable = new Observer<List<Oiswd>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Oiswd> oiswds) {
            if (oiswds != null) {
                firstView.onPatner(oiswds);
            } else {
                firstView.onError();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observer<List<Okdko>> userObservable = new Observer<List<Okdko>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Okdko> okdkos) {
            if (okdkos != null) {
                firstView.onMan(okdkos);
            } else {
                firstView.onError();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    public void getCation(String url) {
        WorkRequest.getAllface().askLocat(url).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(locationObservable);
    }

    @Override
    public OverView getBase() {
        return firstView;
    }
}
