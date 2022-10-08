package com.rebater.cash.well.fun.essential;
import com.rebater.cash.well.fun.bean.Currency;
import com.rebater.cash.well.fun.bean.Kisdi;
import com.rebater.cash.well.fun.bean.Kosa;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.PlayFirst;
import com.rebater.cash.well.fun.bean.Qisks;
import com.rebater.cash.well.fun.bean.ReOffer;
import com.rebater.cash.well.fun.bean.ReceiverInfo;
import com.rebater.cash.well.fun.bean.Records;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.bean.VideoConfig;
import com.rebater.cash.well.fun.bean.WithdrawalPanel;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.bean.UserInfo;

import java.util.List;

public interface IModel {
    interface MyAppView extends OverView {
        void onCoins(UserView user);
        void onVideoSured(UserView user);
        void onOkSign(UserView user);
        void onCheckInfo(Kisdi kisdi);
        void onInfoList(List<Woask> list);
        void onInfoListError();
        void onPageConf(VideoConfig videoConfig);
    }
    interface CheckView extends OverView {
        void onOkSign(UserView user);
        void onCheckInfo(Kisdi kisdi);
    }
    interface CheckReq extends OverPresent {
        //        void addCredits(int type);
        void checkPoints(int id);
        void getCheckData();
    }
//    interface  ReceiverView extends OverView {
//        void onWorkList(List<Woask> list);
//        void onWorkListError();
//    }
//    interface ReceiverAsk extends OverPresent {
//        void getAllTasks();
//    }
    interface MyAppRequest extends OverPresent {
        void getCredit();
        void addCredits(int type);
        void signPoints(int id);
        void getSignData();

    //        void getDeal();
        void getVideo(int type);
//        void getAllTasks();
    }
    interface ExploreView extends OverView {
        void onCoins(UserView user);
        void onWorkList(List<Woask> list);
        //        void onHref(List<Poisa> poisaList);
        void homeInfo(PlayFirst qisoos);
        void onlistError();
        void onHrefReward(UserView user);
    }
    interface ExploreRequest extends OverPresent {
        void getCredit();
        void getTable();
        void getBaseList();
        void getCash(String body);
    }
    interface TapJoyListen {
        void onPrepear(String id);
    }

    interface WorkView extends OverView {
        void onFinish(ReceiverInfo msg);
        void onCash(ReOffer jisd3s);
    }
    interface WorkReq extends OverPresent {
        void getThings(String body);
        void getDayThing(String body);

    }
    interface BackView extends OverView {
        void onFinish(String msg);
        void onDetails(Currency currency);
    }

    interface BackReq extends OverPresent {
        void sendBack(String body);

        void getDetails(int page);

    }

    interface LiftView extends OverView {
        void onHold(WithdrawalPanel withdrawalPanel);

        void onCash(UserView userView);

        void onRecord(Records recordsList);

        void onModel(List<SimpleInfo> simpleInfoList);

        void onDeal(Qisks qisks);
    }

    interface LiftReq extends OverPresent {
        void beHold(String body);

        void getHold(int id);

        void getRecord(int page);

        void getModel();

        void getDeal();
    }

    interface ProduceView extends OverView {
        void onModel(List<SimpleInfo> simpleInfoList);
        void onService(List<Okdko>list);
        void onCash(UserView userView);
    }
    interface ProduceReq extends OverPresent {
        void getModel();
        void getHelp();
        void sendStep(int id,int step);
    }
    interface FirstView extends OverView {
        void onPeizhi(UserInfo userInfo);
        void onPatner(List<Oiswd> tabls);
        void onMan(List<Okdko>list);
        void onLocationError();
        void onLocation(Piskos piskos);
//        void onCurrency(Qisks qisks);
    }

    interface FirstReq extends OverPresent { //?
        void start(String content);
        void getPatner();
        void getMan();
        void getCation(String url);
//        void getDeal();
//        void  upReson(String result);
    }

    interface HelpView extends OverView {
        void onPeizhi(UserInfo userInfo);
        void onLocationError();
        void onNumber(Kosa poloass);
        void onInfo(Piskos piskos);
    }

    interface HelpReq extends OverPresent {
        void start(String content);
//        void getCation(String url);
        void upReson(String code);
    }
}
