package com.rebater.cash.well.fun.present;//package com.amusing.cash.well.fun.present;
//
//import com.amusing.cash.well.fun.retorfit.WorkRequest;
//import com.amusing.cash.well.fun.essential.IModel;
//import com.amusing.cash.well.fun.essential.OverView;
//import com.amusing.cash.well.fun.util.LogInfo;
//import com.amusing.cash.well.fun.util.ssp.SelfPrefrence;
//import com.amusing.cash.well.fun.util.ssp.SelfValue;
//import com.amusing.cash.well.fun.bean.Woask;
//
//import java.util.List;
//
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//
//public class ReceivePresent implements IModel.ReceiverAsk{
//    IModel.ReceiverView receiverView;
//
//    public ReceivePresent (IModel.ReceiverView view){
//        receiverView =view;
//    }
//    @Override
//    public void getAllTasks() {
//        WorkRequest.getAllface().askHistory().subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(myListObservable);
//
//    }
//    Observer<List<Woask>> myListObservable=new Observer<List<Woask>>() {
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(List<Woask> myList) {
//            if(myList!=null&&myList.size()>0){
//                receiverView.onWorkList(myList);
//            }else{
//                receiverView.onWorkListError();
//
//            }
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            LogInfo.e(e.toString());
//            receiverView.onWorkListError();        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    };
//
//    @Override
//    public OverView getBase() {
//        return receiverView;
//    }
//
//    @Override
//    public void sendlogs(String log) {
//        if(SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log,false)) {
//            WorkRequest.getAllface().askLoges(log).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(logObservable);
//        }
//    }
//    Observer<String> logObservable=new Observer<String>() {
//        @Override
//        public void onSubscribe(Disposable d) {
//
//        }
//
//        @Override
//        public void onNext(String point) {
//
//        }
//
//        @Override
//        public void onError(Throwable e) {
////            receiverView.onError();
//        }
//
//        @Override
//        public void onComplete() {
//
//        }
//    };
//}
