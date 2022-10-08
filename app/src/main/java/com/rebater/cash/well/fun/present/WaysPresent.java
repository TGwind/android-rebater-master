package com.rebater.cash.well.fun.present;

import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WaysPresent implements IModel.ProduceReq {

    IModel.ProduceView produceView;
    public WaysPresent(IModel.ProduceView view){
        produceView =view;
    }

    @Override
    public OverView getBase() {
        return produceView;
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
                        if(simpleInfos !=null&& simpleInfos.size()>0){
                            produceView.onModel(simpleInfos);
                        }else{
                            produceView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        produceView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getHelp() {
        WorkRequest.getAllface().getCode().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Okdko>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Okdko> okdkos) {
                        if(okdkos !=null&& okdkos.size()>0){
                            produceView.onService(okdkos);
                        }else{
                            produceView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        produceView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void sendStep(int id, int step) {
        WorkRequest.getAllface().askStep(id,step).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserView>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserView okdkos) {
                        if(okdkos !=null){
                            produceView.onCash(okdkos);
                        }else{
                            produceView.onError();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        produceView.onError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

