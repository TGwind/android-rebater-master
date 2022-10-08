package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Currency;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class QuestionPresent implements IModel.BackReq {
    IModel.BackView backView;

    public QuestionPresent(IModel.BackView view){
        backView =view;
    }
    @Override
    public OverView getBase() {
        return backView;
    }

    @Override
    public void sendBack(String body) {
        WorkRequest.getAllface().askback(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(backObservable);
    }

    @Override
    public void getDetails(int page) {
        WorkRequest.getAllface().askCoinsInfo(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Currency>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Currency currency) {
                        if(currency !=null){
                            backView.onDetails(currency);
                        }else{
                            backView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        backView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    Observer<String> backObservable=new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String info) {
            if(info!=null){
                backView.onFinish(info);
            }else{
                backView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            backView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
}

