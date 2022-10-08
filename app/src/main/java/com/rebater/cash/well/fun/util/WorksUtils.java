package com.rebater.cash.well.fun.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;


import com.rebater.cash.well.fun.bean.LogBean;
import com.rebater.cash.well.fun.bean.LogEvent;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.green.GreenDaoUtils;
import com.rebater.cash.well.fun.green.LogEventDao;
import com.rebater.cash.well.fun.present.CodePresent;
import com.rebater.cash.well.fun.present.WithdrawPresent;
import com.rebater.cash.well.fun.present.ExplorePresent;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.rebater.cash.well.fun.present.LovePresent;
import com.rebater.cash.well.fun.present.QuestionPresent;
import com.rebater.cash.well.fun.retorfit.WorkRequest;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WorksUtils {
    //email
    public static byte[] args1={ 0x65, 0x6D, 0x61, 0x69, 0x6C };
    //gaid
    public static byte[] args2={ 0x67, 0x61, 0x69, 0x64 };
    //recommended_code
    public static byte[] args3={ 0x72, 0x65, 0x63, 0x6F, 0x6D, 0x6D, 0x65, 0x6E, 0x64, 0x65, 0x64, 0x5F, 0x63, 0x6F, 0x64, 0x65 };
    //android_id
    public static byte[] args4={ 0x61, 0x6E, 0x64, 0x72, 0x6F, 0x69, 0x64, 0x5F, 0x69, 0x64 };
    //country_simple
    public static byte[] args5={ 0x63, 0x6F, 0x75, 0x6E, 0x74, 0x72, 0x79, 0x5F, 0x73, 0x69, 0x6D, 0x70, 0x6C, 0x65 };
    //country_name
    public static byte[] args6={ 0x63, 0x6F, 0x75, 0x6E, 0x74, 0x72, 0x79, 0x5F, 0x6E, 0x61, 0x6D, 0x65 };
    //is_net
    public static byte[] args7={ 0x69, 0x73, 0x5F, 0x6E, 0x65, 0x74 };
    //regionName
    public static byte[] args8={ 0x72, 0x65, 0x67, 0x69, 0x6F, 0x6E, 0x4E, 0x61, 0x6D, 0x65 };
    //city
    public static byte[] args9={ 0x63, 0x69, 0x74, 0x79 };
    //lat
    public static byte[] args10={ 0x6C, 0x61, 0x74 };
    //lon
    public static byte[] args11={ 0x6C, 0x6F, 0x6E };
    //isp
    public static byte[] args12={ 0x69, 0x73, 0x70 };
    //timezone
    public static byte[] args13={ 0x74, 0x69, 0x6D, 0x65, 0x7A, 0x6F, 0x6E, 0x65 };
    //model
    public static byte[] args14={ 0x6D, 0x6F, 0x64, 0x65, 0x6C };
    //ua
    public static byte[] args15={ 0x75, 0x61 };
    //make
    public static byte[] args16={ 0x6D, 0x61, 0x6B, 0x65 };
    //offerId
    public static byte[] args17={ 0x6F, 0x66, 0x66, 0x65, 0x72, 0x49, 0x64 };
    //type
    public static byte[] args18={ 0x74, 0x79, 0x70, 0x65 };
    //content
    public static byte[] args19={ 0x63, 0x6F, 0x6E, 0x74, 0x65, 0x6E, 0x74 };
    //desc
    public static byte[] args20={ 0x64, 0x65, 0x73, 0x63 };

    //step
    public static byte[] args21={ 0x73, 0x74, 0x65, 0x70 };
    //channel
    public static byte[] args26={ 0x63, 0x68, 0x61, 0x6E, 0x6E, 0x65, 0x6C };
    //id
    public static byte[] args22={ 0x69, 0x64 };
    //page
    public static byte[] args23={ 0x70, 0x61, 0x67, 0x65 };
    //position
    public static byte[] args24={ 0x70, 0x6F, 0x73, 0x69, 0x74, 0x69, 0x6F, 0x6E };
    //currency
    public static byte[] args25={ 0x63, 0x75, 0x72, 0x72, 0x65, 0x6E, 0x63, 0x79 };
    //version
    public static byte[] args27={0x76, 0x65, 0x72, 0x73, 0x69, 0x6F, 0x6E };
    //package
    public static byte[] args28={ 0x70, 0x61, 0x63, 0x6B, 0x61, 0x67, 0x65 };
    //tpl_id
    public static byte[] args29={ 0x74, 0x70, 0x6C, 0x5F, 0x69, 0x64 };
    //language
    public static byte[] args30={ 0x6C, 0x61, 0x6E, 0x67, 0x75, 0x61, 0x67, 0x65 };
    //login
    public static byte[] args31={ 0x6C, 0x6F, 0x67, 0x69, 0x6E };

    public static void getWork(LovePresent lovePresent, int id , int type, int tbid){//领取任务
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(new String(args22),id);
            jsonObject.put(new String(args18),type);
            jsonObject.put(new String(args29),tbid);
            String str=jsonObject.toString();
            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            lovePresent.getDayThing(content);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
    public static void upLog(List<LogEvent> logEventList, LogEventDao logEventDao){
        if(SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log,false)) {
            String logs=getLogString(logEventList);
//            String content=ProjectTools.getDECCode(logs);
//            LogInfo.e(content);
            WorkRequest.getAllface().askLoges(logs).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LogBean>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }
                        @Override
                        public void onNext(LogBean logBean) {
                            if(logBean!=null){
                                int status=logBean.status;
                                if(status==0){
                                    logEventDao.deleteInTx(logEventList);
                                    List<LogEvent>list=logEventDao.loadAll();
                                    if(list!=null&&list.size()>0){
                                        LogInfo.e("size--"+list.size());
                                    }else{
                                        LogInfo.e("size--"+0);
                                    }
                                }
                            }else{
                                LogInfo.e("noerro");
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            LogInfo.e("noerro"+e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    //uuid,gaid,appPkg,appPkgVer,appPkgVerCode,country,templateId,offerId,offerStep,templatePosition,offerPkg,type,page,eventName,logCreateTime，isWifi,isVPN
    public static String getLogString(List<LogEvent> logEventList){//rizhi
        try{
            JSONArray jsonArray=new JSONArray();
            for(LogEvent logEvent:logEventList){
                String gaid=SelfPrefrence.INSTANCE.getString(SelfValue.GAID, "");
                LogInfo.e("--"+logEvent.getId());
                if(TextUtils.isEmpty(gaid)||gaid.contains("00000000")){
                    gaid=SelfPrefrence.INSTANCE.getString(SelfValue.ANDEIS, "")+Constans.packageName;
                }
                String net=SelfPrefrence.INSTANCE.getString(SelfValue.ISVPSNXCS,"0");
                String netTYPE=SelfPrefrence.INSTANCE.getString(SelfValue.NETSTATUS,"0");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid", SelfPrefrence.INSTANCE.getString(SelfValue.USER_ID,"0"));
                jsonObject.put("gaid",gaid);
                jsonObject.put("appPkg",Constans.pkg);
                jsonObject.put("appPkgVer",Constans.VERSION);
                jsonObject.put("channel",Constans.CHANNEL);
                jsonObject.put("appPkgVerCode",Constans.VERSIONNAME);
                jsonObject.put("channel",Constans.CHANNEL);
                jsonObject.put("country",SelfPrefrence.INSTANCE.getString(SelfValue.PER_COUNTRY, "0"));
                jsonObject.put("templateId",logEvent.getTemplateId());
                jsonObject.put("offerId",logEvent.getOfferId());
                jsonObject.put("offerStep",logEvent.getStep());
                jsonObject.put("templatePosition",logEvent.getPosition());
                jsonObject.put("offerPkg",logEvent.getPackageName());
                jsonObject.put("type",logEvent.getType());
                jsonObject.put("page",logEvent.getPage());
                jsonObject.put("eventName",logEvent.getEventName());
                jsonObject.put("logCreateTime",logEvent.getCreateTime());
                jsonObject.put("isWifi",netTYPE);
                jsonObject.put("isVPN",net);
                jsonArray.put(jsonObject);
            }
            String str=jsonArray.toString();
//            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            return  content;
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
        return  null;
    }

    public static void insertData(Context context, int id,int type,int page,String position,String tableId,String eventName,int step,String pkg){
        LogEventDao logEventDao= GreenDaoUtils.getSingleTon(context).getDaoSession().getLogEventDao();
        if(logEventDao!=null){
            LogEvent logEvent=new LogEvent();
            logEvent.setCreateTime(System.currentTimeMillis());
            logEvent.setOfferId(id);
            logEvent.setChangeTime(System.currentTimeMillis());
            logEvent.setEventName(eventName);
            logEvent.setPackageName(pkg);
            logEvent.setPage(page);
            logEvent.setTemplateId(tableId);
            logEvent.setPosition(position);
            logEvent.setType(type);
            logEvent.setIsUp(false);
            logEvent.setStep(step);
            logEventDao.insert(logEvent);
        }
    }
    public static String getString( int id,int type,int page,String position){//rizhi
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(new String(args22),id);
            jsonObject.put(new String(args18),type);
            jsonObject.put(new String(args23),page);
            jsonObject.put(new String(args24),position);
            jsonObject.put(new String(args27), Constans.VERSIONNAME);
            String str=jsonObject.toString();
            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            return  content;
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
        return  null;
    }
    public static void getcash(WithdrawPresent withdrawPresent, int id, String email, String type){//领取xianjin
        try{
            LogInfo.e(type);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(new String(args22),id);
            jsonObject.put(new String(args1),email);
            jsonObject.put(new String(args25),type);
            String str=jsonObject.toString();
            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            withdrawPresent.beHold(content);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }

    }

    public static void getWorkReward(int asktype, ExplorePresent explorePresent, LovePresent lovePresent, int id , int step){//领取任务奖励
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(new String(args22),id);
            jsonObject.put(new String(args21),step);
            jsonObject.put(new String(args18),1);
            String str=jsonObject.toString();
            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            if(asktype==1) {
                lovePresent.getThings(content);
            }else{
                explorePresent.getCash(content);
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }

    }
    public static void feedback(QuestionPresent questionPresent, String name, String sug, String email){
        try{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(new String(args1),email);
            jsonObject.put(new String(args19),sug);
            jsonObject.put(new String(args20),name);
            String str=jsonObject.toString();
            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            questionPresent.sendBack(content);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }

    }
    public static void sendInfo(Context context, String code, LauncherPresent launcherPresent, CodePresent codePresent, int type, Piskos piskos){
        try {
            JSONObject jsonObject=new JSONObject();
            String gaid= SelfPrefrence.INSTANCE.getString(SelfValue.GAID, "");
            String androidId= ProjectTools.getAndroidId(context);
            jsonObject.put(new String(args2),  gaid );
            jsonObject.put(new String(args1), SelfPrefrence.INSTANCE.getString(SelfValue.USER_EMAIL,ProjectTools.getAndroidId(context)+Constans.packageName));
            jsonObject.put(new String(args3),code);
            jsonObject.put(new String(args26), SelfPrefrence.INSTANCE.getString(SelfValue.CHANNEL,Constans.CHANNEL));
            jsonObject.put(new String(args4),androidId);
            jsonObject.put(new String(args5), SelfPrefrence.INSTANCE.getString(SelfValue.COUNTRY_ID,""));
            jsonObject.put(new String(args6), SelfPrefrence.INSTANCE.getString(SelfValue.PER_COUNTRY,""));
            jsonObject.put(new String(args7), SelfPrefrence.INSTANCE.getString(SelfValue.ISVPSNXCS,"0"));
            if(piskos !=null) {
                jsonObject.put(new String(args8), piskos.regionName);
                jsonObject.put(new String(args9), piskos.city);
                jsonObject.put(new String(args10), piskos.lat);
                jsonObject.put(new String(args11), piskos.lon);
                jsonObject.put(new String(args12), piskos.isp);
                jsonObject.put(new String(args13), piskos.timezone);
            }
            String lang=ProjectTools.getLanguageEnv();
            LogInfo.e("--"+lang);
            if(SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISFIRST, true)) {
                jsonObject.put(new String(args31), 2);
            }else{
                jsonObject.put(new String(args31), 1);
            }
            jsonObject.put(new String(args30), lang);
            jsonObject.put(new String(args14), Build.MODEL);
            jsonObject.put(new String(args15), WebSettings.getDefaultUserAgent(context));
            jsonObject.put(new String(args16), Build.MANUFACTURER);
            String str=jsonObject.toString();
            LogInfo.e(str);
            String content= ProjectTools.getDECCode(str);
            if(type==0) {
                launcherPresent.start(content);
            }else{
                codePresent.start(content);
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
}
