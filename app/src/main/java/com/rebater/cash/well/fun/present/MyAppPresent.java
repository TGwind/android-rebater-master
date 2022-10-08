package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Kisdi;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.VideoConfig;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyAppPresent implements IModel.MyAppRequest {

    IModel.MyAppView myAppView;

    public MyAppPresent(IModel.MyAppView view){
        myAppView =view;
    }

    @Override
    public OverView getBase() {
        return myAppView;
    }

    @Override
    public void getVideo(int type) {
        WorkRequest.getAllface().askVideo(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoConfig>() { //创建观察者Observer实例
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoConfig qisks) {
                        if(qisks !=null){
                            myAppView.onPageConf(qisks);
                        }else{
                            myAppView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        myAppView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    Observer<UserView> pointObservable=new Observer<UserView>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserView userView) {
            if(userView !=null){
                myAppView.onCoins(userView);
            }else{
                myAppView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            myAppView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
    @Override
    public void getCredit() {
        WorkRequest.getAllface().askMyCoins().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pointObservable);
    }
    Observer<UserView> videoObservable=new Observer<UserView>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserView userView) {
            if(userView !=null){
                myAppView.onVideoSured(userView);
            }else{
                myAppView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            myAppView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
    @Override
    public void addCredits(int type) {
        WorkRequest.getAllface().askCites(type).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(videoObservable);
    }

    @Override
    public void signPoints(int id) {
        WorkRequest.getAllface().checkIn(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(checkObservable);
    }
    Observer<UserView> checkObservable=new Observer<UserView>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserView userView) {
            if(userView !=null){
                myAppView.onOkSign(userView);
            }else{
                myAppView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            myAppView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
    @Override
    public void getSignData() {
        WorkRequest.getAllface().askCheckIn().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(checkInfoObservable);
    }


    Observer<Kisdi> checkInfoObservable=new Observer<Kisdi>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Kisdi kisdi) {
            if(kisdi !=null){
                myAppView.onCheckInfo(kisdi);
            }else{
                myAppView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            myAppView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
}
