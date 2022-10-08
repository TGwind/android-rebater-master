package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.PlayFirst;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ExplorePresent implements IModel.ExploreRequest {
    IModel.ExploreView exploreView;

    public ExplorePresent(IModel.ExploreView view) {
        exploreView = view;
    }

    @Override
    public void getCredit() { //获取邀请码
        WorkRequest.getAllface().askMyCoins().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pointObservable);

    }

    @Override
    public void getTable() {    //获取首页数据
        WorkRequest.getAllface().askPlayInfo().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(firstObservable);
    }

    @Override
    public void getBaseList() { //获取用户领取的任务列表
        WorkRequest.getAllface().askHistory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(myListObservable);
    }

    @Override
    public void getCash(String body) { //任务奖励上报
        WorkRequest.getAllface().askvideowork(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(rewardObservable);

    }

    @Override
    public OverView getBase() {
        return exploreView;
    }

    Observer<PlayFirst> firstObservable = new Observer<PlayFirst>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(PlayFirst qisoos) {
            exploreView.homeInfo(qisoos);
        }

        @Override
        public void onError(Throwable e) {
            exploreView.homeInfo(null);
        }

        @Override
        public void onComplete() {

        }
    };

    Observer<UserView> pointObservable = new Observer<UserView>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserView userView) {
            if (userView != null) {
                exploreView.onCoins(userView);
            } else {
                exploreView.onError();
            }
        }

        @Override
        public void onError(Throwable e) {
            exploreView.onError();
        }

        @Override
        public void onComplete() {

        }
    };

    Observer<List<Woask>> myListObservable = new Observer<List<Woask>>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List<Woask> myList) {//observable发出的每一个item都会调用onNext方法来处理
            if (myList != null && myList.size() > 0) {
                exploreView.onWorkList(myList);
            } else {
                exploreView.onlistError();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    Observer<UserView> rewardObservable = new Observer<UserView>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserView str) {
            exploreView.onHrefReward(str);
        }

        @Override
        public void onError(Throwable e) {
            exploreView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
}
