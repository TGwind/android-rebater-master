package com.rebater.cash.well.fun.present;

import android.app.Activity;

import com.adgatemedia.sdk.classes.AdGateMedia;
import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.OfferwallListener;
import com.tapjoy.TJConnectListener;
import com.tapjoy.TJPlacement;
import com.tapjoy.Tapjoy;
import com.tapjoy.TapjoyConnectFlag;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class OfferPresent {
    public static void initPartner(List<Oiswd> list, Activity activity, IModel.TapJoyListen tapJoyListen){
        String userID = SelfPrefrence.INSTANCE.getString(SelfValue.USER_ID, "");
//        OmAds.setUserId(userID);
        for( Oiswd oiswd :list){
            int partnerId= oiswd.app_sub;
            if(partnerId==11201){//ir
                IronSource.setOfferwallListener(new OfferwallListener() {

                    @Override
                    public void onOfferwallAvailable(boolean isAvailable) {
                        LogInfo.e("-"+isAvailable);
                    }

                    @Override
                    public void onOfferwallOpened() {
                    }

                    @Override
                    public void onOfferwallShowFailed(IronSourceError error) {
                        LogInfo.e(error.getErrorMessage());
                    }

                    @Override
                    public boolean onOfferwallAdCredited(int credits, int totalCredits, boolean totalCreditsFlag) {
                        return true;
                    }

                    @Override
                    public void onGetOfferwallCreditsFailed(IronSourceError error) {
                        LogInfo.e(error.getErrorMessage());
                    }
                    @Override
                    public void onOfferwallClosed() {
                    }
                });
                IronSource.setUserId(userID);
                IronSource.init(activity, oiswd.app_id, IronSource.AD_UNIT.OFFERWALL);
                IntegrationHelper.validateIntegration(activity);
            }else if(partnerId==11202) {//tap
                Hashtable<String, Object> connectFlags = new Hashtable<String, Object>();
                connectFlags.put(TapjoyConnectFlag.ENABLE_LOGGING, "false");
                connectFlags.put(TapjoyConnectFlag.USER_ID, userID);
                Tapjoy.connect(activity, oiswd.app_key, connectFlags, new TJConnectListener() {
                    @Override
                    public void onConnectSuccess() {
                        tapJoyListen.onPrepear(oiswd.app_id);
//                            LogTool.e("onConnectSuccess");}
                    }

                    @Override
                    public void onConnectFailure() {
//                            LogTool.e("onConnectFailure");
                    }
                });
            }else if(partnerId==11203) {//adgate
                HashMap<String, String> subids = new HashMap<>();
                subids.put("s2", SelfPrefrence.INSTANCE.getString(SelfValue.GAID, ProjectTools.getAndroidId(activity)));
                AdGateMedia adGateMedia = AdGateMedia.getInstance();
                adGateMedia.loadOfferWall(activity, oiswd.app_key, userID, subids,
                        () -> {
//                                LogUtils.e("OnOfferWallLoadSuccess");
                        },
                        reason -> {
//                                LogUtils.e("OnOfferWallLoadFailed" + reason);
                        });
            }
        }

    }
    public static void showWall(int id, OverActivity activity, TJPlacement offerwallPlacement){
        try {
//            String user_id= Sprefrence.INSTANCE.getString(PrefrenceKey.USER_ID,"0");
            if (id == 11202) {
                if(offerwallPlacement.isContentReady()) {
//            LogTool.e("showTopJoy");
                    offerwallPlacement.showContent();
                }
                else {
                    activity.showToast(activity.getString(R.string.no_ad));
                }
            } else if (id == 11201) {
                if (IronSource.isOfferwallAvailable()) {
                    IronSource.showOfferwall();//"Earn_Credits"
                } else {
                    activity.showToast(activity.getString(R.string.no_ad));
                }
            } else if (id == 11203) {
                AdGateMedia.getInstance().showOfferWall(activity,
                        () -> {
                        });
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
}
