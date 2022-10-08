package com.rebater.cash.well.fun.util;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStatsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rebater.cash.well.fun.bean.AdInfo;
import com.rebater.cash.well.fun.bean.AdvertiBean;
import com.rebater.cash.well.fun.bean.Kixiba;
import com.rebater.cash.well.fun.bean.LogEvent;
import com.rebater.cash.well.fun.bean.Odfi3a;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.Qiest;
import com.rebater.cash.well.fun.bean.Qisks;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.green.AdvertiBeanDao;
import com.rebater.cash.well.fun.green.GreenDaoUtils;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kochava.base.Tracker;

import org.greenrobot.greendao.query.QueryBuilder;

import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ProjectTools {
    public static String getDECCode(String code) {
    try {
        String md5 = Madtools.getMD5String(code);
        String base64code = new StringBuffer(Base64.encodeToString(code.getBytes(), Base64.NO_WRAP)).reverse().toString();
        return md5 + base64code;
    } catch (Exception e) {
        return null;
    }
}
    public static List<LogEvent>getLog(List<LogEvent> logEventList){
        List<LogEvent>list=new ArrayList<>();
        try {
            if (logEventList.size() < 30) {
                return logEventList;
            } else {
                for (int i = 0; i < logEventList.size(); i++) {
                    list.add(logEventList.get(i));
                }
            }
        } catch (Exception E) {
            LogInfo.e(E.toString());
        }
        return list;
    }

    public static AdvertiBean getAdinfo(List<AdvertiBean> userList, String down_pkg) {
        try {
            for (AdvertiBean adInfo : userList) {
                if (!TextUtils.isEmpty(adInfo.getPackageName()) && !TextUtils.isEmpty(down_pkg) && adInfo.getPackageName().equalsIgnoreCase(down_pkg)) {
                    return adInfo;
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return null;
    }

    public static SimpleInfo getPayWay(List<SimpleInfo> owksaList) {
        SimpleInfo owksa = null;
        try {
            for (SimpleInfo bean : owksaList) {
                if (!bean.name.equalsIgnoreCase("Paypal")) {
                    owksa = bean;
                    break;
                }
            }
            if (owksa == null) {
                owksa = owksaList.get(0);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return owksa;
    }

    public static void upOpenError(String event, FirebaseAnalytics mFirebaseAnalytics, String tableID, String offerId) {
        try {
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log, false)) {
                Tracker.sendEvent(new Tracker.Event(event)
                        .addCustom("item_name", offerId)
                        .addCustom("item_quantity", tableID)
                );
                Bundle bundleEvent = new Bundle();
                bundleEvent.putString("ITEM_ID", offerId);
                bundleEvent.putString("ITEM_LIST_ID", tableID);
                mFirebaseAnalytics.logEvent(event, bundleEvent);
                LogInfo.e("finish--" + event);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    public static int[] findRangeLinear(LinearLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;
    }

    public static void uplOGWith3(Context context, String event, int kind) {
        try {
            String types = "A";
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").equals("2")) {
                types = "B";
            } else {
                types = "A";
            }
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
            ProjectTools.upnewEventView(types, event, mFirebaseAnalytics, kind);
        }catch (Exception  e){
            LogInfo.e(e.toString());
        }
    }

    public static void upnewEventView(String type, String event, FirebaseAnalytics mFirebaseAnalytics,int kind){
        try {
            if(kind==1) {
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log, false)) {
//                    Tracker.sendEvent(event, "" + System.currentTimeMillis());
                    Tracker.sendEvent(new Tracker.Event(event)
                            .addCustom("item_name", type)
                    );
                    Bundle bundleEvent = new Bundle();
                    bundleEvent.putString("ITEM_ID", type);
                    mFirebaseAnalytics.logEvent(event, bundleEvent);
                    LogInfo.e("finish--" + event);
                }
            }else{
                Tracker.sendEvent(new Tracker.Event(event)
                        .addCustom("item_name", type)
                );
                Bundle bundleEvent = new Bundle();
                bundleEvent.putString("ITEM_ID", type);
                mFirebaseAnalytics.logEvent(event, bundleEvent);
                LogInfo.e("finish--" + event);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
        public static Animation getAnimotion(int type){
            if(type==1){
                Animation enterAnimation = new AlphaAnimation(0f, 1f);
                enterAnimation.setDuration(600);
                enterAnimation.setFillAfter(true);
                return  enterAnimation;
            }else{
                Animation exitAnimation = new AlphaAnimation(1f, 0f);
                exitAnimation.setDuration(600);
                exitAnimation.setFillAfter(true);
                return  exitAnimation;
            }
        }
        public static void saveInfo(Piskos piskos, Context context, LauncherPresent launcherPresent, int type, String email) {
            try {
                String county = piskos.country;
                if (!TextUtils.isEmpty(county)) {
                    SelfPrefrence.INSTANCE.setString(SelfValue.PER_COUNTRY, county);
                }
                String countycode = piskos.countryCode;
                if (!TextUtils.isEmpty(countycode)) {
                    SelfPrefrence.INSTANCE.setString(SelfValue.COUNTRY_ID, countycode);
                }
                if(type==1) {
                    WorksUtils.sendInfo(context, SelfPrefrence.INSTANCE.getString(SelfValue.USER_CODE, ""), launcherPresent, null, 0, null);
                }
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
        }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrDateFormat() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    public static void setSp(UserInfo userInfo, FirebaseAnalytics firebaseAnalytics, Context context) {
        try {
            String uuid = userInfo.user.uuid;
            String id = "" + userInfo.user.level_id;
            int islogin = userInfo.user.is_logined;
            LogInfo.e("--" + uuid + id);
            SelfPrefrence.INSTANCE.setString(SelfValue.USER_level, "" + id);
            SelfPrefrence.INSTANCE.setString(SelfValue.USER_ID, uuid);
            SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISFIRST, false);
            SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_SHOW_GUIDE, userInfo.config.is_ask == 1);
            SelfPrefrence.INSTANCE.setString(SelfValue.USER_INVITEICON, userInfo.user.my_recommended_code);
                SelfPrefrence.INSTANCE.setString(SelfValue.recommended_code, userInfo.user.recommended_code);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_SHOW_DOWN, userInfo.config.is_apidown == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_Log, userInfo.config.is_uolog == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_HREF, userInfo.config.is_h5guide == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_TOAST, userInfo.config.is_native == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_BANNER, userInfo.config.is_banner == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_GAME, userInfo.config.is_game == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_WALL, userInfo.config.is_wall == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.IS_PUSH, userInfo.config.is_push == 1);
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISRATE, userInfo.config.is_rate == 1);
                SelfPrefrence.INSTANCE.setString(SelfValue.url, userInfo.config.push_url);
                SelfPrefrence.INSTANCE.setInt(SelfValue.type, userInfo.config.push_type);
                SelfPrefrence.INSTANCE.setInt(SelfValue.CONFIGID, userInfo.config.id);
                SelfPrefrence.INSTANCE.setString(SelfValue.pkg, userInfo.config.push_package);
                SelfPrefrence.INSTANCE.setString(SelfValue.content, userInfo.config.push_content);
                SelfPrefrence.INSTANCE.setInt(SelfValue.Push_count, userInfo.config.push_count);
                SelfPrefrence.INSTANCE.setString(SelfValue.ABTYPE, userInfo.user.user_type_page);
                SelfPrefrence.INSTANCE.setString(SelfValue.SUB1, userInfo.config.sub1);
            SelfPrefrence.INSTANCE.setString(SelfValue.SUB2, userInfo.config.sub2);
            SelfPrefrence.INSTANCE.setString(SelfValue.SUB3, userInfo.config.sub3);
            SelfPrefrence.INSTANCE.setString(SelfValue.SUB4, userInfo.config.sub4);
            SelfPrefrence.INSTANCE.setString(SelfValue.SUB5, userInfo.config.sub5);
            SelfPrefrence.INSTANCE.setInt(SelfValue.LOADTIME, userInfo.config.contact_reward);
            String credits = "" + userInfo.user.points;
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            String mol = userInfo.user.user_type_page;
            String type = "A";
            if (mol.equals("2")) {
                type = "B";
            } else {
                type = "A";
            }
            if (islogin == 0) {
                ProjectTools.upnewEventView(type, Constans.user_register, firebaseAnalytics, 2);
                WorksUtils.insertData(context, 0, 67, 15, "0", "0", Constans.user_register, 0, "0");

            } else {
//                ProjectTools.upAFF(  Constans.user_login_ok,1,firebaseAnalytics);
                ProjectTools.upnewEventView(type, Constans.user_login_ok, firebaseAnalytics, 2);
                WorksUtils.insertData(context, 0, 68, 15, "0", "0", Constans.user_login_ok, 0, "0");

            }
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
        }
        public static Qisks.Deal getDeal(List<Qisks.Deal> deal){
            Qisks.Deal info=null;
            if(deal.size()==1){
                info= deal.get(0);
            }else{
                for(Qisks.Deal dealinfo:deal){
                    if(!dealinfo.currency.contains("USD")){
                        info = dealinfo;
                    }
                }
                if(info==null){
                    info= deal.get(0);
                }
            }
            return info;
        }
        public static boolean startIntentWithPackage(Context context, String url, String packName) {
            try {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                intent.setPackage(packName);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                context.startActivity(intent);
            } catch (Exception e) {
                LogInfo.e(e.toString());
                return false;
            }
            return true;
        }

        public static void HandleContact(int c5type, String url, String pkg, Context context, String title) {
            if (!TextUtils.isEmpty(url)) {
                if (c5type == 2) {
                    try {
                        //获取剪贴板管理器
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", title);
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
//                        showToast(this.getString(R.string.t5y6hg));
                    } catch (Exception e) {
                        LogInfo.e(e.toString());
                    }
                } else if (c5type == 1) {
                    if (!TextUtils.isEmpty(pkg) && ProjectTools.isAvilible(context, pkg)) {
                        ProjectTools.startIntentWithPackage(context, url, pkg);
                    } else {
                        ProjectTools.startIntent(context, url);
//                        showToast(getString(R.string.t6uy7iu));
//                    BaseUtils.openTaskInPlayMarket(context, pkg);
                    }
                } else if (c5type == 3) {
                    ProjectTools.paste(context, url);

                } else if (c5type == 4) {
                    ProjectTools.startIntent(context, url);
                }
            }
        }

        public static Okdko getInfo(Context context, List<Okdko> list) {
            Okdko okdko = null;
            if (list != null && list.size() > 0) {
                for (Okdko info : list) {
                    String pkg = info.pkg;
                    if (!TextUtils.isEmpty(pkg) && isAvilible(context, pkg)) {
                        okdko = info;
                        break;
                    }
                }
                if (okdko == null) {
                    for (Okdko info : list) {
                        int type = info.is_open_type;
                        if (type == 3) {
                            okdko = info;
                            break;
                        }
                    }
                    if (okdko == null) {
                        okdko = list.get(0);
                    }
                }
            }
            return okdko;
        }

        public static Odfi3a getDtailsByHistory(Woask woask) {
            try {
                Parcel parcel = Parcel.obtain();
                Odfi3a odfi3a = new Odfi3a(parcel);
                odfi3a.award = woask.offerInfo.award;
                odfi3a.desc = woask.offerInfo.desc;
                odfi3a.id = woask.t_id;
                odfi3a.reward_type = woask.offerInfo.reward_type;
                odfi3a.down_type = woask.offerInfo.down_type;
                odfi3a.step = woask.step;
                odfi3a.tpl_id=""+woask.tpl_id;
                int step = woask.step;
                odfi3a.uptime=woask.uptime;
                odfi3a.image_max=woask.offerInfo.image_max;
                LogInfo.e("--" + woask.t_id);
                odfi3a.down_pkg = woask.offerInfo.down_pkg;
                odfi3a.tracking_link = woask.offerInfo.tracking_link;
                odfi3a.image_url = woask.offerInfo.image_url;
                odfi3a.title = woask.offerInfo.title;
                if (woask.offerInfo != null) {
                    if (!TextUtils.isEmpty(woask.offerInfo.step1_title)) {
                        LogInfo.e(woask.offerInfo.step1_title);
                        odfi3a.step1_desc = woask.offerInfo.step1_desc;
                        odfi3a.step1_award = woask.offerInfo.step1_award;
                        odfi3a.step1_title = woask.offerInfo.step1_title;
                        odfi3a.step1_time = woask.offerInfo.step1_time;
                        odfi3a.step1_type = woask.offerInfo.step1_type;
                        if (step == 99) {
                            odfi3a.step1_status = 0;
                        } else {
                            odfi3a.step1_status = 1;
                        }
                    }
                    if (!TextUtils.isEmpty(woask.offerInfo.step2_title)) {
                        odfi3a.step2_desc = woask.offerInfo.step2_desc;
                        odfi3a.step2_award = woask.offerInfo.step2_award;
                        odfi3a.step2_title = woask.offerInfo.step2_title;
                        odfi3a.step2_time = woask.offerInfo.step2_time;
                        odfi3a.step2_type = woask.offerInfo.step2_type;
                        odfi3a.step2_reward_type=woask.offerInfo.step2_reward_type;
                        odfi3a.step2_day=woask.offerInfo.step2_day;
                        if (step == 99) {
                            odfi3a.step2_status = 0;
                        } else {
                            if (step == 1) {
                                odfi3a.step2_status = 0;
                            } else if (step == 2) {
                                odfi3a.step2_status = 1;
                            } else if (step == 3) {
                                odfi3a.step2_status = 1;
                            } else if (step == 4) {
                                odfi3a.step2_status = 1;
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(woask.offerInfo.step3_title)) {
                        odfi3a.step3_desc = woask.offerInfo.step3_desc;
                        odfi3a.step3_award = woask.offerInfo.step3_award;
                        odfi3a.step3_title = woask.offerInfo.step3_title;
                        odfi3a.step3_time = woask.offerInfo.step3_time;
                        odfi3a.step3_type = woask.offerInfo.step3_type;
                        odfi3a.step3_reward_type=woask.offerInfo.step3_reward_type;
                        odfi3a.step3_day=woask.offerInfo.step3_day;
                        if (step == 99) {
                            odfi3a.step3_status = 0;
                        } else {
                            if (step == 1) {
                                odfi3a.step3_status = 0;
                            } else if (step == 2) {
                                odfi3a.step3_status = 0;
                            } else if (step == 3) {
                                odfi3a.step3_status = 1;
                            } else if (step == 4) {
                                odfi3a.step3_status = 1;
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(woask.offerInfo.step4_title)) {
                        odfi3a.step4_desc = woask.offerInfo.step4_desc;
                        odfi3a.step4_award = woask.offerInfo.step4_award;
                        odfi3a.step4_title = woask.offerInfo.step4_title;
                        odfi3a.step4_time = woask.offerInfo.step4_time;
                        odfi3a.step4_type = woask.offerInfo.step4_type;
                        odfi3a.step4_reward_type=woask.offerInfo.step4_reward_type;
                        odfi3a.step4_day=woask.offerInfo.step4_day;
                        if (step == 99) {
                            odfi3a.step4_status = 0;
                        } else {
                            if (step == 1) {
                                odfi3a.step4_status = 0;
                            } else if (step == 2) {
                                odfi3a.step4_status = 0;
                            } else if (step == 3) {
                                odfi3a.step4_status = 0;
                            } else if (step == 4) {
                                odfi3a.step4_status = 1;
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(woask.offerInfo.step5_title)) {
                        odfi3a.step5_desc = woask.offerInfo.step5_desc;
                        odfi3a.step5_award = woask.offerInfo.step5_award;
                        odfi3a.step5_title = woask.offerInfo.step5_title;
                        odfi3a.step5_time = woask.offerInfo.step5_time;
                        odfi3a.step5_type = woask.offerInfo.step5_type;
                        odfi3a.step5_reward_type=woask.offerInfo.step5_reward_type;
                        odfi3a.step5_day=woask.offerInfo.step5_day;
                        if (step == 99) {
                            odfi3a.step5_status = 0;
                        } else {
                            if (step == 1) {
                                odfi3a.step5_status = 0;
                            } else if (step == 2) {
                                odfi3a.step5_status = 0;
                            } else if (step == 3) {
                                odfi3a.step5_status = 0;
                            } else if (step == 4) {
                                odfi3a.step5_status = 0;
                            }
                        }
                    }
                    parcel.recycle();
                } else {
                    LogInfo.e("go-");
                }
                return odfi3a;
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
            return null;
        }

        public static Odfi3a getDtails(AdInfo adInfo) {
            try {
                Parcel parcel = Parcel.obtain();
                Odfi3a odfi3a = new Odfi3a(parcel);
                odfi3a.award = adInfo.award;
                odfi3a.reward_type = adInfo.reward_type;
                odfi3a.down_type = adInfo.down_type;
                odfi3a.desc = adInfo.desc;
                odfi3a.id = adInfo.id;
                odfi3a.step = 99;
                LogInfo.e("--" + adInfo.id);
                odfi3a.down_pkg = adInfo.down_pkg;
                odfi3a.tracking_link = adInfo.tracking_link;
                odfi3a.image_url = adInfo.image_url;
                odfi3a.title = adInfo.title;
                odfi3a.image_max= adInfo.image_max;
                if (adInfo.gp != null) {
                    if (!TextUtils.isEmpty(adInfo.gp.step1_title)) {
                        LogInfo.e(adInfo.gp.step1_title);
                        odfi3a.step1_desc = adInfo.gp.step1_desc;
                        odfi3a.step1_award = adInfo.gp.step1_award;
                        odfi3a.step1_title = adInfo.gp.step1_title;
                        odfi3a.step1_time = adInfo.gp.step1_time;
                        odfi3a.step1_type = adInfo.gp.step1_type;
                        odfi3a.step1_status = adInfo.gp.step1_status;
                    }
                    if (!TextUtils.isEmpty(adInfo.gp.step2_title)) {
                        odfi3a.step2_desc = adInfo.gp.step2_desc;
                        odfi3a.step2_award = adInfo.gp.step2_award;
                        odfi3a.step2_title = adInfo.gp.step2_title;
                        odfi3a.step2_time = adInfo.gp.step2_time;
                        odfi3a.step2_type = adInfo.gp.step2_type;
                        odfi3a.step2_status = adInfo.gp.step2_status;
                        odfi3a.step2_reward_type= adInfo.gp.step2_reward_type;
                        odfi3a.step2_day= adInfo.gp.step2_day;
                    }
                    if (!TextUtils.isEmpty(adInfo.gp.step3_title)) {
                        odfi3a.step3_desc = adInfo.gp.step3_desc;
                        odfi3a.step3_award = adInfo.gp.step3_award;
                        odfi3a.step3_title = adInfo.gp.step3_title;
                        odfi3a.step3_time = adInfo.gp.step3_time;
                        odfi3a.step3_type = adInfo.gp.step3_type;
                        odfi3a.step3_status = adInfo.gp.step3_status;
                        odfi3a.step3_reward_type= adInfo.gp.step3_reward_type;
                        odfi3a.step3_day= adInfo.gp.step3_day;

                    }
                    if (!TextUtils.isEmpty(adInfo.gp.step4_title)) {
                        odfi3a.step4_desc = adInfo.gp.step4_desc;
                        odfi3a.step4_award = adInfo.gp.step4_award;
                        odfi3a.step4_title = adInfo.gp.step4_title;
                        odfi3a.step4_time = adInfo.gp.step4_time;
                        odfi3a.step4_type = adInfo.gp.step4_type;
                        odfi3a.step4_status = adInfo.gp.step4_status;
                        odfi3a.step4_reward_type= adInfo.gp.step4_reward_type;
                        odfi3a.step4_day= adInfo.gp.step4_day;

                    }
                    if (!TextUtils.isEmpty(adInfo.gp.step5_title)) {
                        odfi3a.step5_desc = adInfo.gp.step5_desc;
                        odfi3a.step5_award = adInfo.gp.step5_award;
                        odfi3a.step5_title = adInfo.gp.step5_title;
                        odfi3a.step5_time = adInfo.gp.step5_time;
                        odfi3a.step5_type = adInfo.gp.step5_type;
                        odfi3a.step5_status = adInfo.gp.step5_status;
                        odfi3a.step5_reward_type= adInfo.gp.step5_reward_type;
                        odfi3a.step5_day= adInfo.gp.step5_day;

                    }
                    parcel.recycle();
                } else {
                    LogInfo.e("go-");
                }
                return odfi3a;
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
            return null;
        }

        public static List<Qiest> getStep(Context context, Odfi3a firstInfo, long serviceTime) {
            List<Qiest> list = new ArrayList<>();
            if (firstInfo != null) {
                String pack=firstInfo.down_pkg;
                if (!TextUtils.isEmpty(pack)) {
                    if (!TextUtils.isEmpty(firstInfo.step1_desc)) {
                        Qiest qiest1 = new Qiest();
                        qiest1.title = firstInfo.step1_title;
                        qiest1.desc = firstInfo.step1_desc;
                        qiest1.award = firstInfo.step1_award;
                        qiest1.type = firstInfo.step1_type;
                        qiest1.time = firstInfo.step1_time;
                        qiest1.status = firstInfo.step1_status;
                        if (firstInfo.step1_status == 0) {
                            if (isAvilible(context, pack)) {
                                qiest1.lastTimes = serviceTime;
                                qiest1.doingtime= (int) ProjectTools.getRunTime(ProjectTools.getEventList(context, firstInfo.down_pkg, serviceTime * 1000));
                            }else{
                                qiest1.lastTimes = serviceTime;
                                qiest1.doingtime=0;
                            }
                        }
                        list.add(qiest1);
                    }
                    if (!TextUtils.isEmpty(firstInfo.step2_desc)) {
                        Qiest qiest2 = new Qiest();
                        qiest2.title = firstInfo.step2_title;
                        qiest2.desc = firstInfo.step2_desc;
                        qiest2.award = firstInfo.step2_award;
                        qiest2.type = firstInfo.step2_type;
                        qiest2.time = firstInfo.step2_time;
                        qiest2.status = firstInfo.step2_status;
                        qiest2.reward_type = firstInfo.step2_reward_type;
                        LogInfo.e("--"+firstInfo.step2_reward_type);
                        qiest2.step_day = firstInfo.step2_day;
                        if (firstInfo.step2_status == 0) {
                            if (isAvilible(context, pack)) {
                                qiest2.lastTimes = serviceTime;
                                qiest2.doingtime= (int) ProjectTools.getRunTime(ProjectTools.getEventList(context, firstInfo.down_pkg, serviceTime * 1000));
                            }
                        }
                        list.add(qiest2);
                    }
                    if (!TextUtils.isEmpty(firstInfo.step3_desc)) {
                        Qiest qiest3 = new Qiest();
                        qiest3.title = firstInfo.step3_title;
                        qiest3.desc = firstInfo.step3_desc;
                        qiest3.award = firstInfo.step3_award;
                        qiest3.type = firstInfo.step3_type;
                        qiest3.time = firstInfo.step3_time;
                        qiest3.status = firstInfo.step3_status;
                        qiest3.reward_type = firstInfo.step3_reward_type;
                        qiest3.step_day = firstInfo.step3_day;
                        if (firstInfo.step3_status == 0) {
                            if (isAvilible(context, pack)) {
                                qiest3.lastTimes = serviceTime;
                                qiest3.doingtime= (int) ProjectTools.getRunTime(ProjectTools.getEventList(context, firstInfo.down_pkg, serviceTime * 1000));
                            }
                        }
                        list.add(qiest3);
                    }
                    if (!TextUtils.isEmpty(firstInfo.step4_desc)) {
                        Qiest qiest4 = new Qiest();
                        qiest4.title = firstInfo.step4_title;
                        qiest4.desc = firstInfo.step4_desc;
                        qiest4.award = firstInfo.step4_award;
                        qiest4.type = firstInfo.step4_type;
                        qiest4.time = firstInfo.step4_time;
                        qiest4.status = firstInfo.step4_status;
                        qiest4.reward_type = firstInfo.step4_reward_type;
                        qiest4.step_day = firstInfo.step4_day;
                        if (firstInfo.step4_status == 0) {
                            if (isAvilible(context, pack)) {
                                qiest4.lastTimes = serviceTime;
                                qiest4.doingtime= (int) ProjectTools.getRunTime(ProjectTools.getEventList(context, firstInfo.down_pkg, serviceTime * 1000));
                            }
                        }
                        list.add(qiest4);
                    }
                    if (!TextUtils.isEmpty(firstInfo.step5_desc)) {
                        Qiest qiest5 = new Qiest();
                        qiest5.title = firstInfo.step5_title;
                        qiest5.desc = firstInfo.step5_desc;
                        qiest5.award = firstInfo.step5_award;
                        qiest5.type = firstInfo.step5_type;
                        qiest5.time = firstInfo.step5_time;
                        qiest5.status = firstInfo.step5_status;
                        qiest5.reward_type = firstInfo.step5_reward_type;
                        qiest5.step_day = firstInfo.step5_day;
                        if (firstInfo.step5_status == 0) {
                            if (isAvilible(context, pack)) {
                                qiest5.lastTimes = serviceTime;
                                qiest5.doingtime= (int) ProjectTools.getRunTime(ProjectTools.getEventList(context, firstInfo.down_pkg, serviceTime * 1000));
                            }
                        }
                        list.add(qiest5);
                    }
                }
            }
            return list;
        }


        public static int paste(Context context, String email) {
            try {
//            Intent data=new Intent(Intent.ACTION_SENDTO);
//            data.setData(Uri.parse(context.getString(R.string.mine_customer_email)));
//            context.startActivity(data);

                Uri uri = Uri.parse("mailto:" + email);
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
                // 设置对方邮件地址
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
//            // 设置标题内容
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT, title);
//            // 设置邮件文本内容
//            emailIntent.putExtra(Intent.EXTRA_TEXT, content);
                context.startActivity(Intent.createChooser(emailIntent, "Choose a Email"));

//            Log.e("--",".toString()");
                return 1;
            } catch (Exception e) {
//            Log.e("--",e.toString());
                return 0;
            }
        }

        public static void openTaskInPlayMarket(Context context, String name,String url) {
            try {
//            Intent i3 = new Intent(Intent.ACTION_VIEW, Uri
//                    .parse(Constans.playMarketWayToApp + name));
//            i3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i3);
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                intent.setPackage("com.android.vending");
                context.startActivity(intent);
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
        }

        public static String getCode(String code) {
            try {
//            LogUtils.e(code);
                if (!TextUtils.isEmpty(code) && !code.equals("null")) {
                    code = code.substring(32);
                    String codejj = new StringBuffer(code).reverse().toString();
                    String json = new String(Base64.decode(codejj, Base64.NO_WRAP));
                    if (!TextUtils.isEmpty(json)) {
                        return json;
                    }
                }
            } catch (Exception e) {
                return null;
            }
            return null;
        }
        public static boolean vpnActive(Context context){
            boolean vpnInUse = false;
            try {
                //this method doesn't work below API 21
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                    return false;


                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    Network activeNetwork = connectivityManager.getActiveNetwork();
                    NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(activeNetwork);

                    return caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN);
                }

                Network[] networks = connectivityManager.getAllNetworks();

                for (int i = 0; i < networks.length; i++) {

                    NetworkCapabilities caps = connectivityManager.getNetworkCapabilities(networks[i]);
                    if (caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        vpnInUse = true;
                        break;
                    }
                }
            }catch (Exception e){
                LogInfo.e(e.toString());
            }

            return vpnInUse;
        }
        public static boolean isVpnUsed() {
            try {
                Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
                if (niList != null) {
                    for (NetworkInterface intf : Collections.list(niList)) {
                        if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                            continue;
                        }
//                    LogUtils.e("isVpnUsed() NetworkInterface Name: " + intf.getName());
                        if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                            return true; // The VPN is up
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return false;
        }
        public static void upHomeView(int type, String event, FirebaseAnalytics mFirebaseAnalytics,String tableID,String offerId,String step){
            if(SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log,false)) {
                Bundle bundleEvent = new Bundle();
                try {
                    String aBtype = getABtype();
//                    Tracker.sendEvent(event, "" + System.currentTimeMillis());
                    if (type == 0) {
                        Tracker.sendEvent(new Tracker.Event(event)
                                .addCustom("item_quantity", tableID)
                        );
                        bundleEvent.putString("item_quantity", tableID);

                    } else if (type == 1) {
                        Tracker.sendEvent(new Tracker.Event(event)
                                .addCustom("item_quantity", tableID)
                                .addCustom("item_name", offerId)
                        );
                        bundleEvent.putString("ITEM_LIST_ID", tableID);
                        bundleEvent.putString("ITEM_ID", offerId);

                    } else if (type == 2) {
                        Tracker.sendEvent(new Tracker.Event(event)
                                .addCustom("item_quantity", tableID)
                                .addCustom("item_name", offerId)
                                .addCustom("items_in_basket", step)
                        );
                        bundleEvent.putString("ITEM_LIST_ID", tableID);
                        bundleEvent.putString("ITEM_ID", offerId);
                        bundleEvent.putString("ITEM_CATEGORY", step);
                    } else if (type == 3) {
                        Tracker.sendEvent(new Tracker.Event(event)
                                .addCustom("item_quantity", offerId)
                                .addCustom("item_name", aBtype)
                                .addCustom("items_in_basket", tableID)
                        );
                        bundleEvent.putString("ITEM_ID", aBtype);
                        bundleEvent.putString("ITEM_LIST_ID", offerId);
                        bundleEvent.putString("ITEM_CATEGORY", tableID);
                    }
                    mFirebaseAnalytics.logEvent(event, bundleEvent);
                    LogInfo.e("finish--" + event);
                } catch (Exception e) {
                    LogInfo.e(e.toString());
                }
            }
        }

    public static String getABtype() {
        String types = "A";
        if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").equals("2")) {
            types = "B";
        } else {
            types = "A";
        }
        return types;
    }

    public static void upAFF(String event, int type, FirebaseAnalytics mFirebaseAnalytics) {
        if (type == 1) {
            upInso(event, mFirebaseAnalytics);
        } else {
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log, false)) {
                upInso(event, mFirebaseAnalytics);
            }
        }
    }

    public static void upInso(String event, FirebaseAnalytics mFirebaseAnalytics) {
            try {
                String type = getABtype();
                Tracker.sendEvent(new Tracker.Event(event)
                        .addCustom("item_name", type)
                );
                Bundle bundleEvent = new Bundle();
                bundleEvent.putString("ITEM_ID", type);
                mFirebaseAnalytics.logEvent(event, bundleEvent);
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
        }

        public static void getAAID(Context context) {
            try {
                String android_id = getAndroidId(context);
                if (TextUtils.isEmpty(SelfPrefrence.INSTANCE.getString(SelfValue.GAID, ""))) {
                    SelfPrefrence.INSTANCE.setString(SelfValue.ANDEIS, android_id);
                    SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL, android_id + Constans.packageName);
                    if (!isAvilible(context, "com.google.android.gms")) {
                        SelfPrefrence.INSTANCE.setString(SelfValue.GAID,android_id + Constans.packageName);
                        SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,android_id+ Constans.packageName);
                        return;
                    }
                    LogInfo.e("MISS--");
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                                String myId = adInfo != null ? adInfo.getId() : null;
                                LogInfo.e("id--" + myId);
                                if (!TextUtils.isEmpty(myId)) {
                                    if (myId.contains("00000000")) {
                                        SelfPrefrence.INSTANCE.setString(SelfValue.GAID,android_id + Constans.packageName);
                                        SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,android_id + Constans.packageName);
                                    } else {
                                        SelfPrefrence.INSTANCE.setString(SelfValue.GAID, myId);
                                        SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL, myId + Constans.packageName);
                                    }
                                } else {
                                    SelfPrefrence.INSTANCE.setString(SelfValue.GAID,android_id + Constans.packageName);
                                    SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,android_id+ Constans.packageName);
                                }
                            } catch (Exception var3) {
                                SelfPrefrence.INSTANCE.setString(SelfValue.GAID, android_id + Constans.packageName);
                                SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,android_id + Constans.packageName);
                                LogInfo.e(var3.toString());
                            }
                        }
                    });
//            Executors.newSingleThreadExecutor().execute((() -> {
//                try {
//                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
//                    String myId = adInfo != null ? adInfo.getId() : null;
//                    LogInfo.e("id--" + myId);
//                    if (!TextUtils.isEmpty(myId)) {
//                        SelfPrefrence.INSTANCE.setString(SelfValue.GAID, myId);
//                        SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,myId+Constans.packageName);
//
//                    } else {
//                        SelfPrefrence.INSTANCE.setString(SelfValue.GAID, ProjectTools.getAndroidId(context) + Constans.packageName);
//                        SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,ProjectTools.getAndroidId(context)+Constans.packageName);
//                    }
//                } catch (Exception var3) {
//                    SelfPrefrence.INSTANCE.setString(SelfValue.GAID, ProjectTools.getAndroidId(context) + Constans.packageName);
//                    SelfPrefrence.INSTANCE.setString(SelfValue.USER_EMAIL,ProjectTools.getAndroidId(context)+Constans.packageName);
//                    LogInfo.e(var3.toString());
//                }
//
//            }));
                }
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
        }

        //获取androidID
        @SuppressLint("HardwareIds")
        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        public static String getAndroidId(Context context) {
            String id = UUID.randomUUID().toString();
            try {
                id = Settings.Secure.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
                if (TextUtils.isEmpty(id)) {
                    id = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
                }
            } catch (Exception var4) {
                LogInfo.e(var4.toString());
            }
            return id;
        }

        public static boolean getJSONType(String str) {
            boolean result = false;
            if (!TextUtils.isEmpty(str)) {
                str = str.trim();
                if (str.startsWith("{") && str.endsWith("}")) {
                    result = true;
                } else if (str.startsWith("[") && str.endsWith("]")) {
                    result = true;
                }
            }
            return result;
        }

        public static boolean startIntent(Context context, String url) {
            try {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(url);
                intent.setData(uri);
                context.startActivity(intent);
            } catch (Exception e) {
                LogInfo.e(e.toString());
                return false;
            }
            return true;
        }
        public static  boolean hasPermissionToReadNetworkStats(Context context) {
//        int checkOpNoThrow = ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats",  android.os.Process.myUid(), context.getPackageName());
//        boolean z = true;
//        if (checkOpNoThrow != 3 ? checkOpNoThrow != 0 : context.checkCallingOrSelfPermission("android.permission.PACKAGE_USAGE_STATS") != PackageManager.PERMISSION_DENIED) {
//            z = false;
//        }
//        return z;
            boolean granted = false;
            AppOpsManager appOps = (AppOpsManager)context
                    .getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                    android.os.Process.myUid(), context.getPackageName());
            LogInfo.e("requestPermission: mode" + mode);
            if (mode == AppOpsManager.MODE_DEFAULT) {
                granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
            } else {
                granted = (mode == AppOpsManager.MODE_ALLOWED);
            }
            return  granted;
        }
        public static String getLanguageEnv() {
            String lang=null;
            try {
                Locale l = Locale.getDefault();

                String language = l.getLanguage();

                String country = l.getCountry().toLowerCase();
                return country + "_" + language;
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
            return lang;
        }
        public static long getRunTime(ArrayList<UsageEvents.Event> arrayList) {
            long j = 0;
            try {
                int type=arrayList.get(0).getEventType();
                if(type==1) {
                    for (int i4 = 1; i4 < arrayList.size(); i4 += 2) {
                        if (arrayList.get(i4).getEventType() == 2) {
                            int i5 = i4 - 1;
                            if (arrayList.get(i5).getEventType() == 1) {
                                j += arrayList.get(i4).getTimeStamp() - arrayList.get(i5).getTimeStamp();
                            }
                        }
                    }
                }else if(type==2){
                    for (int i4 = 2; i4 < arrayList.size(); i4 += 2) {
                        if (arrayList.get(i4).getEventType() == 2) {
                            int i5 = i4 - 1;
                            if (arrayList.get(i5).getEventType() == 1) {
                                j += arrayList.get(i4).getTimeStamp() - arrayList.get(i5).getTimeStamp();
                            }
                        }
                    }
                }
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
            LogInfo.e("--"+j);
            return j/1000;
        }
        public static  ArrayList<UsageEvents.Event> getEventList(Context context,String str, long j) {
            ArrayList<UsageEvents.Event> arrayList = new ArrayList<>();
            try {
                UsageEvents queryEvents = ((UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE)).queryEvents(j, System.currentTimeMillis());
                if (queryEvents != null) {
                    while (queryEvents.hasNextEvent()) {
                        UsageEvents.Event event = new UsageEvents.Event();
                        queryEvents.getNextEvent(event);
                        if (str.equals(event.getPackageName()) && (event.getEventType() == 1 || event.getEventType() == 2)) {
                            arrayList.add(event);
                        }
                    }
                }
            } catch (Error | Exception e) {
                LogInfo.e(e.toString());        }
//        Log.e("--","EventList size: " + arrayList.size());
            return arrayList;
        }
        public static void gotoSetting(Context context){
            try {
                context.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS", Uri.parse("package:" + context.getPackageName())));
            } catch (Error | Exception e) {
                try {
                    context.startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
                } catch (Error | Exception unused) {

                }
            }
        }
        public static boolean startIntentWithPKG(Context context, String pkg) {
            try {
//            Intent intent = new Intent();
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setAction(Intent.ACTION_VIEW);
//            Uri uri = Uri.parse(url);
//            intent.setData(uri);
//            context.startActivity(intent);

                PackageManager packageManager = context.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(pkg);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e) {
                LogInfo.e(e.toString());
                return false;
            }
            return true;
        }

        public static boolean isAvilible(Context context, String packageName) {
//        if (TextUtils.isEmpty(packageName)) {
//            return false;
//        }
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                PackageManager packageManager = context.getPackageManager();
//                PackageInfo info = packageManager.getPackageInfo(packageName, 0);
//                if (info != null) {
//                    LogInfo.e(info.packageName);
//                    return true;
//                }
//            } else {
//                PackageManager packageManager = context.getPackageManager();
//                // 获取所有已安装程序的包信息
//                List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
//                for (int i = 0; i < pinfo.size(); i++) {
//                    if (pinfo.get(i).packageName.equalsIgnoreCase(packageName))
//                        return true;
//                }
//            }
//        } catch (Exception e) {
//            LogInfo.e(e.toString());
//        }
//
//        return false;
            PackageInfo packageInfo;
            if (context == null) {
                return false;
            }
            PackageManager packageManager = context.getPackageManager();
            try {
                packageInfo = packageManager.getPackageInfo(packageName, 0);
            } catch (Exception unused) {
                packageInfo = null;
            }
            if (packageInfo == null || packageManager.getApplicationEnabledSetting(packageName) == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
                return false;
            }
            return true;
        }

        public static List<Kixiba> getHrefList(List<Kixiba> offerInfoList, List<Woask> mHistorylist) {
            List<Kixiba> kixibaList = new ArrayList<>();
            if (mHistorylist != null && mHistorylist.size() > 0) {
                for (int j = 0; j < offerInfoList.size(); j++) {
                    Kixiba kixiba = offerInfoList.get(j);
                    boolean isContain = false;
                    for (int i = 0; i < mHistorylist.size(); i++) {
                        Woask woask = mHistorylist.get(i);
                        int id = woask.t_id;
                        if (kixiba.id == id) {
                            isContain = true;
                        }
                    }
                    if (!isContain) {
                        kixibaList.add(kixiba);
                    }
                }
            } else {
                kixibaList = offerInfoList;
            }
            return kixibaList;
        }

        public static boolean hasUsageSettingPage(Context context) {
            PackageManager packageManager = context.getPackageManager();
            Intent intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
            ArrayList arrayList = new ArrayList();
            try {
                arrayList.addAll(packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList.size() > 0;
        }

    public static List<AdInfo> getUninstallList(List<AdInfo> list, Context context, int tableId) {
        List<AdInfo> qisooList = new ArrayList<>();
        try {
            AdvertiBeanDao advertiBeanDao = GreenDaoUtils.getSingleTon(context).getDaoSession().getAdvertiBeanDao();
            QueryBuilder qb = advertiBeanDao.queryBuilder();
            if (advertiBeanDao != null && qb != null) {
                for (int j = 0; j < list.size(); j++) {
                    AdInfo qisoo = list.get(j);
                    if (qisoo.ad_type == 2) {
                        String packages = qisoo.down_pkg;
//                        int id = qisoo.id;
                        if (!isAvilible(context, packages)) {
                            doAdd(advertiBeanDao, qisooList, qisoo);
                        } else {
                            insertInfo(context, qisoo, tableId, advertiBeanDao);
//                            findPresent.uplog(WorksUtils.getpKString(id, 32, 1, j+"_"+tableId,packages));
                        }
                    } else {
                        qisooList.add(qisoo);
                    }
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return qisooList;
    }

    public static boolean isMore() {
        try {
            float last = Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));
            int lastPoints = (int) last;
            float max = (Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.MAX_COUNT, "100000")));
            int maxs = (int) max;
            if (lastPoints >= maxs) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return false;
    }

    public static void doAdd(AdvertiBeanDao advertiBeanDao, List<AdInfo> qisooList, AdInfo qisoo) {
        try {
            QueryBuilder qb = advertiBeanDao.queryBuilder();
            qb.where(AdvertiBeanDao.Properties.PackageName.eq(qisoo.down_pkg), AdvertiBeanDao.Properties.AdId.eq(qisoo.id)).build();
            List<AdInfo> userList = qb.list();
            if (userList != null && userList.size() > 0) {
                LogInfo.e("has--in");
            } else {
                LogInfo.e(qisoo.down_pkg + qisoo.id);
                qisooList.add(qisoo);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    public static void insertInfo(Context context, AdInfo qisoo, int tableId, AdvertiBeanDao advertiBeanDao) {
        try {
            QueryBuilder qb = advertiBeanDao.queryBuilder();
            qb.where(AdvertiBeanDao.Properties.PackageName.eq(qisoo.down_pkg), AdvertiBeanDao.Properties.AdId.eq(qisoo.id)).build();
            List<AdvertiBean> userList = qb.list();
            if (userList != null && userList.size() > 0) {
                LogInfo.e("size--" + userList.size());
                AdvertiBean advertiBean = userList.get(0);//
                boolean isup = advertiBean.getIsUp();
                if (!isup) {//有了但没传
                    LogInfo.e("no--");
                    LogInfo.e("--" + qisoo.title);
                    WorksUtils.insertData(context, qisoo.id, 32, 1, "0", "" + tableId, "0", 0, qisoo.down_pkg);
                    advertiBean.setIsUp(true);
                    advertiBeanDao.update(advertiBean);
//                                        showToast("up installed");
                } else {
                    LogInfo.e("-" + qisoo.title);
                    LogInfo.e("yes-");
                }
            } else {
                AdvertiBean advertiBean = new AdvertiBean();
                advertiBean.setAdId(qisoo.id);
                advertiBean.setCreateTime(System.currentTimeMillis());
                advertiBean.setChangeTime(System.currentTimeMillis());
                advertiBean.setPackageName(qisoo.down_pkg);
                advertiBean.setIsUp(true);
                long id = advertiBeanDao.insert(advertiBean);
                LogInfo.e("-" + qisoo.title + id);
                WorksUtils.insertData(context, qisoo.id, 32, 1, "0", "" + tableId, "0", 0, qisoo.down_pkg);
//                                    showToast("up installed");
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
//        public static List<AdInfo> getUninstallList(List<AdInfo> list, Context context, int tableId) {
//            List<AdInfo> adInfoList = new ArrayList<>();
//            try {
//                for (int j = 0; j < list.size(); j++) {
//                    AdInfo adInfo = list.get(j);
//                    if(adInfo.ad_type==2) {
//                        String packages = adInfo.down_pkg;
//                        int id= adInfo.id;
//                        if (!isAvilible(context, packages)) {
//                            adInfoList.add(adInfo);
//                        }else{
//                            insertInfo(context, adInfo, tableId);
//                        }
//                    } else {
//                        adInfoList.add(adInfo);
//                    }
//                }
//            } catch (Exception e) {
//                LogInfo.e(e.toString());
//            }
//            return adInfoList;
//        }
//
//    public static void insertInfo(Context context, AdInfo qisoo, int tableId) {
//        AdvertiBeanDao advertiBeanDao = GreenDaoUtils.getSingleTon(context).getmDaoSession().getAdvertiBeanDao();
//        if (advertiBeanDao != null) {
//            QueryBuilder qb = advertiBeanDao.queryBuilder();
//            qb.and(AdvertiBeanDao.Properties.PackageName.eq(qisoo.down_pkg), AdvertiBeanDao.Properties.AdId.eq(qisoo.id));
//            List<AdvertiBean> userList = qb.list();
//            if (userList == null || userList.size() == 0) {
//                AdvertiBean advertiBean = new AdvertiBean();
//                advertiBean.setAdId(qisoo.id);
//                advertiBean.setCreateTime(System.currentTimeMillis());
//                advertiBean.setChangeTime(System.currentTimeMillis());
//                advertiBean.setPackageName(qisoo.down_pkg);
//                advertiBean.setIsUp(true);
//                advertiBeanDao.insert(advertiBean);
//                LogInfo.e("insert");
//                WorksUtils.insertData(context, qisoo.id, 32, 1, "0", "" + tableId, "0", 0, qisoo.down_pkg);
////                                    showToast("up installed");
//            } else {
//                AdvertiBean advertiBean = userList.get(0);//
//                boolean isup = advertiBean.getIsUp();
//                if (!isup) {//有了但没传
//                    LogInfo.e("no--");
//                    WorksUtils.insertData(context, qisoo.id, 32, 1, "0", "" + tableId, "0", 0, qisoo.down_pkg);
//                    advertiBean.setIsUp(true);
//                    advertiBeanDao.update(advertiBean);
////                                        showToast("up installed");
//                } else {
//                    LogInfo.e("yes-");
//                }
//            }
//        }
//    }

//    public static List<AdInfo> getUninstall(List<AdInfo> list, List<Woask> mHistorylist) {
//        List<AdInfo> adInfoList = new ArrayList<>();
//        try {
//            if (mHistorylist != null && mHistorylist.size() > 0) {
//                for (int j = 0; j < list.size(); j++) {
//                    AdInfo adInfo = list.get(j);
//                    int ids = adInfo.id;
//                    boolean isContain = false;
//                    for (int i = 0; i < mHistorylist.size(); i++) {
//                        Woask woask = mHistorylist.get(i);
//                            int id = woask.t_id;
//                            if (ids == id&&woask.status!=1) {
//                                isContain = true;
//                            }
//                        }
//                        if (!isContain) {
//                            adInfoList.add(adInfo);
//                        }
//                    }
//                } else {
//                    adInfoList = list;
//                }
//            } catch (Exception e) {
//                LogInfo.e(e.toString());
//            }
//            return adInfoList;
//        }

//        public static Kixiba getHref(Woask woask) {
//            try {
//                Parcel parcel = Parcel.obtain();
//                Kixiba kixiba = new Kixiba(parcel);
//                kixiba.id = woask.t_id;
//                kixiba.award = woask.offerInfo.award;
//                kixiba.offer_pkg = woask.offerInfo.offer_pkg;
//                kixiba.title = woask.offerInfo.title;
//                kixiba.requirements = woask.offerInfo.requirements;
//                kixiba.desc = woask.offerInfo.desc;
//                kixiba.click_url = woask.offerInfo.click_url;
//                kixiba.icon = woask.offerInfo.icon;
//                parcel.recycle();
//                return kixiba;
//            } catch (Exception e) {
//                LogInfo.e(e.toString());
//            }
//            return null;
//        }

    public static List<Woask> getShowInfo(List<Woask> list) {
        List<Woask> user = new ArrayList<>();
        for (Woask woask : list) {
            int status = woask.status;
            if (status != 3) {
                user.add(woask);
            }
        }
        return user;
        }
}
