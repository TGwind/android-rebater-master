package com.rebater.cash.well.fun.essential;


import com.rebater.cash.well.fun.bean.Currency;
import com.rebater.cash.well.fun.bean.Kisdi;
import com.rebater.cash.well.fun.bean.Kosa;
import com.rebater.cash.well.fun.bean.LogBean;
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

import retrofit2.http.Body;
import retrofit2.http.GET;
import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IRetrofit {
    @GET("userconf")
    Observable<VideoConfig> askVideo(@Query("type") int type);

    @POST
    Observable<String> gft5h5(@Body String body);

    @POST("askback")
    Observable<String> askback(@Body String body);

    @POST
    Observable<String> gfrr4(@Body String body);

    @POST("taskdo")
    Observable<ReOffer> doWork(@Body String body); //首页GP任务奖励上报

    @POST("taskdo")
    Observable<UserView> askvideowork(@Body String body); //任务奖励上报接口

    @POST
    Observable<String> j54rffef(@Body String body);

    @POST("task")
    Observable<ReceiverInfo> getListTab(@Body String body); //任务领取接口

    @GET("step")
    Observable<UserView> askStep(@Query("id") int id, @Query("step") int step);

    @GET("userconnect")
    Observable<List<Okdko>> getCode();

    @POST
    Observable<String> jer4tee(@Body String body);

    @GET("partners")
    Observable<List<Oiswd>> askPartener();

    @GET("tasklist")
    Observable<List<Woask>> askHistory();   //用户领取的任务列表接口

    @POST
    Observable<String> j76y4f(@Body String body);

    @GET("inconf")
    Observable<Kisdi> askCheckIn();

    @GET("upReson")
    Observable<Kosa> askReason(@Query("code") String code);

    @POST
    Observable<String> mfdf4rdwd(@Body String body);

    @POST("userlog")
    Observable<LogBean> askLoges(@Body String body);

    @GET("paylist")
    Observable<List<SimpleInfo>> askWaysTab();

    @POST("affiliation")
    Observable<UserView> askAff(@Body String body);

    @GET("deal")
    Observable<Qisks> askDeals();
//    @GET("h5offer")
//    Observable<List<Poisa>> getHrefInfo();

    @GET("integraldetails")
    Observable<Currency> askCoinsInfo(@Query("offset") int offset);

    @GET("usercash")
    Observable<Records> askUserCor(@Query("offset") int offset);

    @GET("gethome")
    Observable<PlayFirst> askPlayInfo();

    @GET("cashlist") //获取提现面板列表
    Observable<WithdrawalPanel> askWiso(@Query("id") int id);

    @POST("cash") //执行提现
    Observable<UserView> askSidmog(@Body String body);

    @GET("in")
    Observable<UserView> checkIn(@Query("isvideo") int id);

    @GET("cite")
    Observable<UserView> askCites(@Query("type") int type);

    @GET("usercode") //邀请码接口
    Observable<UserView> askMyCoins();

    @POST("app")
    Observable<UserInfo> askLogin(@Body String body);  //用户登录注册接口

    @GET
    Observable<Piskos> askLocat(@Url String url);


}
