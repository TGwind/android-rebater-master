package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Kisdi;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignPresent implements IModel.CheckReq {
    IModel.CheckView checkView;

    public SignPresent(IModel.CheckView view){
        checkView =view;
    }

    @Override
    public void checkPoints(int id) {
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
                checkView.onOkSign(userView);
            }else{
                checkView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            checkView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
    @Override
    public void getCheckData() {
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
                checkView.onCheckInfo(kisdi);
            }else{
                checkView.onError();

            }
        }

        @Override
        public void onError(Throwable e) {
            checkView.onError();
        }

        @Override
        public void onComplete() {

        }
    };
    @Override
    public OverView getBase() {
        return checkView;
    }
}
