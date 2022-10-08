package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Qisks;
import com.rebater.cash.well.fun.bean.Records;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.bean.WithdrawalPanel;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WithdrawPresent implements IModel.LiftReq {
    IModel.LiftView liftView;
    public WithdrawPresent(IModel.LiftView view){
        liftView =view;
    }
    @Override
    public OverView getBase() {
        return liftView;
    }

    @Override
    public void beHold(String body) {
        WorkRequest.getAllface().askSidmog(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tixianObservable);
    }
    Observer<UserView> tixianObservable=new Observer<UserView>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(UserView str) {
            liftView.onCash(str);
        }

        @Override
        public void onError(Throwable e) {
            liftView.onError();
        }

        @Override
        public void onComplete() {

        }

    };
    @Override
    public void getHold(int id) {
        WorkRequest.getAllface().askWiso(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(takelistObservable);
    }

    @Override
    public void getRecord(int page) {
        WorkRequest.getAllface().askUserCor(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Records>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Records recordsList) {
                        if (recordsList != null) {
                            liftView.onRecord(recordsList);
                        } else {
                            liftView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        liftView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getModel() {
        WorkRequest.getAllface().askWaysTab().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<SimpleInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<SimpleInfo> simpleInfos) {
                        if (simpleInfos != null && simpleInfos.size() > 0) {
                            liftView.onModel(simpleInfos);
                        } else {
                            liftView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        liftView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getDeal() {
        WorkRequest.getAllface().askDeals().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Qisks>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Qisks qisks) {
                        if (qisks != null) {
                            liftView.onDeal(qisks);
                        } else {
                            liftView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        liftView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    Observer<WithdrawalPanel> takelistObservable=new Observer<WithdrawalPanel>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(WithdrawalPanel withdrawalPanel) {
            if(withdrawalPanel !=null){
                liftView.onHold(withdrawalPanel);
            }else{
                liftView.onError();
            }
        }

        @Override
        public void onError(Throwable e) {
            liftView.onError();
        }

        @Override
        public void onComplete() {

        }

    };
}
