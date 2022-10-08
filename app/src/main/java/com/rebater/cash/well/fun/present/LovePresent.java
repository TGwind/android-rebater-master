package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.ReOffer;
import com.rebater.cash.well.fun.bean.ReceiverInfo;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LovePresent implements IModel.WorkReq {
    IModel.WorkView workView;

    public LovePresent(IModel.WorkView view){
        workView = view;
    }

    @Override
    public void getThings(String body) { //首页GP任务奖励上报
        WorkRequest.getAllface().doWork(body).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(rewardObservable);
    }
    @Override
    public void getDayThing(String body) { //任务领取接口
        WorkRequest.getAllface().getListTab(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(workObservable);
    }
    Observer<ReceiverInfo> workObservable=new Observer<ReceiverInfo>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ReceiverInfo receiverInfo) {
            if (receiverInfo!=null){
                workView.onFinish(receiverInfo);
            }else{
                workView.onError();
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
    public OverView getBase() {
        return null;
    }
    Observer<ReOffer> rewardObservable =new Observer<ReOffer>(){
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(ReOffer str) {
            if(str!=null){
                workView.onCash(str);
            }else{
                workView.onError();
            }
        }

        @Override
        public void onError(Throwable e) {
            workView.onError();
        }
        @Override
        public void onComplete() {
        }
    };

}
