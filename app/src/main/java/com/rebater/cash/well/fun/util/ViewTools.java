package com.rebater.cash.well.fun.util;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Application;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.retorfit.WorkRequest;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.kochava.base.AttributionUpdateListener;
import com.kochava.base.Tracker;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ViewTools {  //？
    private static long lastClickTime = 0;
    private static long DIFF = 2000;
    private static int lastButtonId = -1;

    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
//            Log.v("isFastDoubleClick", "短时间内按钮多次触发");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
    Map<String, Object> conversionData = null;

    public static void upInfos(String body){
        WorkRequest.getAllface().askAff(body).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserView>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserView userView) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static String getJson(Map<String, Object> conversionDataMap){
        String info="";
        try{
            JSONObject jsonObject=new JSONObject();
            for (String attrName : conversionDataMap.keySet()) {
                jsonObject.put(attrName,conversionDataMap.get(attrName));
            }
            info=jsonObject.toString();
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
        return  info;
    }
    public static  void initAPPflyer(Application application) {
        try {
            Tracker.configure(new Tracker.Configuration(application)
                    .setAppGuid(application.getString(R.string.infre))
                    .setAttributionUpdateListener(new AttributionUpdateListener() {
                        @Override
                        public void onAttributionUpdated(@NonNull String attribution) {
                            // got the attribution results, now we need to parse it
                            try {
                                JSONObject attributionObject = new JSONObject(attribution);
                                if ("false".equals(attributionObject.optString("attribution", ""))) {
                                    // Install is not attributed.
                                } else {
                                    // Install is attributed. Retrieve the values we care about.
                                    String attributedNetworkId = attributionObject.optString("network");
                                    LogInfo.e(attributedNetworkId);
                                    if(!TextUtils.isEmpty(attributedNetworkId)) {
                                        SelfPrefrence.INSTANCE.setString(SelfValue.CHANNEL, attributedNetworkId);
                                    }
                                    // ...
                                }
                                if(!SelfPrefrence.INSTANCE.getString(SelfValue.KOVAO, "0").equals(ProjectTools.getCurrDateFormat())) {
                                    String body= ProjectTools.getDECCode(attribution);
                                    upInfos(body);
                                    SelfPrefrence.INSTANCE.setString(SelfValue.KOVAO, ProjectTools.getCurrDateFormat());
                                }
                            } catch (JSONException exception) {
                                LogInfo.e(exception.toString());
                            }
                        }
                    })
            );
//            AppsFlyerLib appsflyer = AppsFlyerLib.getInstance();
////        appsflyer.setDebugLog(true);
////        AppsFlyerLib.getInstance().setAppInviteOneLink("H5hv");
//
//            appsflyer.subscribeForDeepLink(deepLinkResult -> {
//                DeepLinkResult.Status dlStatus = deepLinkResult.getStatus();
//                if (dlStatus == DeepLinkResult.Status.FOUND) {
////                Log.d(LOG_TAG, "Deep link found");
//                } else if (dlStatus == DeepLinkResult.Status.NOT_FOUND) {
////                Log.d(LOG_TAG, "Deep link not found");
//                    return;
//                } else {
//                    // dlStatus == DeepLinkResult.Status.ERROR
////                DeepLinkResult.Error dlError = deepLinkResult.getError();
////                Log.d(LOG_TAG, "There was an error getting Deep Link data: " + dlError.toString());
//                    return;
//                }
//                DeepLink deepLinkObj = deepLinkResult.getDeepLink();
//                try {
////                Log.d(LOG_TAG, "The DeepLink data is: " + deepLinkObj.toString());
//                } catch (Exception e) {
////                Log.d(LOG_TAG, "DeepLink data came back null");
//                    return;
//                }
//                // An example for using is_deferred
//                if (deepLinkObj.isDeferred()) {
////                Log.d(LOG_TAG, "This is a deferred deep link");
//                } else {
////                Log.d(LOG_TAG, "This is a direct deep link");
//                }
//                // An example for getting deep_link_value
// /*           String fruitName = "";
//            try {
//                fruitName = deepLinkObj.getDeepLinkValue();
//                if (fruitName == null || fruitName.equals("")){
//                    Log.d(LOG_TAG, "deep_link_value returned null");
//                    fruitName = deepLinkObj.getStringValue("fruit_name");
//                    if (fruitName == null || fruitName.equals("")) {
//                        Log.d(LOG_TAG, "could not find fruit name");
//                        return;
//                    }
//                    Log.d(LOG_TAG, "fruit_name is " + fruitName + ". This is an old link");
//                }
//                Log.d(LOG_TAG, "The DeepLink will route to: " + fruitName);
//            } catch (Exception e) {
//                Log.d(LOG_TAG, "There's been an error: " + e.toString());
//                return;
//            }*/
////                goToFruit(fruitName, deepLinkObj);
//            });
//
//            AppsFlyerConversionListener conversionListener = new AppsFlyerConversionListener() {
//                @Override
//                public void onConversionDataSuccess(Map<String, Object> conversionDataMap) {
//                    for (String attrName : conversionDataMap.keySet()) {
//                        if(attrName.equalsIgnoreCase("media_source")){
//                            String channel= (String) conversionDataMap.get(attrName);
//                            LogInfo.e(channel);
//                            if(!TextUtils.isEmpty(channel)) {
//                                SelfPrefrence.INSTANCE.setString(SelfValue.CHANNEL, channel);
//                            }
//                        }
//
////                         LogInfo.e( "Conversion attribute: " + attrName + " = " + conversionDataMap.get(attrName));
//
//                    }
//                    if(!SelfPrefrence.INSTANCE.getString(SelfValue.AFFLY, "0").equals(ProjectTools.getCurrDateFormat())) {
//                        String body=ProjectTools.getDECCode(getJson(conversionDataMap));
//                        upInfos(body);
//                        SelfPrefrence.INSTANCE.setString(SelfValue.AFFLY, ProjectTools.getCurrDateFormat());
//                    }
//                    String status = Objects.requireNonNull(conversionDataMap.get("af_status")).toString();
//                    if (status.equals("Non-organic")) {
//                        if (Objects.requireNonNull(conversionDataMap.get("is_first_launch")).toString().equals("true")) {
////                        Log.d(LOG_TAG,"Conversion: First Launch");
//                            //Deferred deep link in case of a legacy link
//                        } else {
////                        Log.d(LOG_TAG,"Conversion: Not First Launch");
//                        }
//                    } else {
////                    Log.d(LOG_TAG, "Conversion: This is an organic install.");
//                    }
////                    conversionData = conversionDataMap;
//                }
//
//                @Override
//                public void onConversionDataFail(String errorMessage) {
////                Log.d(LOG_TAG, "error getting conversion data: " + errorMessage);
//                }
//
//                @Override
//                public void onAppOpenAttribution(Map<String, String> attributionData) {
////                Log.d(LOG_TAG, "onAppOpenAttribution: This is fake call.");
//                }
//
//                @Override
//                public void onAttributionFailure(String errorMessage) {
////                Log.d(LOG_TAG, "error onAttributionFailure : " + errorMessage);
//                }
//            };
//            appsflyer.init(id, conversionListener, application);
//            appsflyer.start(application, id, new AppsFlyerRequestListener() {
//                @Override
//                public void onSuccess() {
////                Log.d(LOG_TAG, "Launch sent successfully, got 200 response code from server");
//                }
//
//                @Override
//                public void onError(int i, @NonNull String s) {
////                Log.d(LOG_TAG, "Launch failed to be sent:\n" +
////                        "Error code: " + i + "\n"
////                        + "Error description: " + s);
//                }
//            });
        }catch (Exception e){
//            LogUtils.e(e.toString());
        }
    }
    public static ObjectAnimator tada(View view) {
        return tada(view, 1f);
    }

    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

//        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
//                Keyframe.ofFloat(0f, 0f),
//                Keyframe.ofFloat(.1f, -3f * shakeFactor),
//                Keyframe.ofFloat(.2f, -3f * shakeFactor),
//                Keyframe.ofFloat(.3f, 3f * shakeFactor),
//                Keyframe.ofFloat(.4f, -3f * shakeFactor),
//                Keyframe.ofFloat(.5f, 3f * shakeFactor),
//                Keyframe.ofFloat(.6f, -3f * shakeFactor),
//                Keyframe.ofFloat(.7f, 3f * shakeFactor),
//                Keyframe.ofFloat(.8f, -3f * shakeFactor),
//                Keyframe.ofFloat(.9f, 3f * shakeFactor),
//                Keyframe.ofFloat(1f, 0)
//        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY).
                setDuration(1000);
    }
}
