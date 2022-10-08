package com.rebater.cash.well.fun.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.BusSendEvent;
import com.rebater.cash.well.fun.bean.CheckBean;
import com.rebater.cash.well.fun.bean.Kisdi;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.CheckAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.SignPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.MaxReward;
import com.applovin.mediation.MaxRewardedAdListener;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
// check-in
public class CheckInActivity extends OverActivity implements IModel.CheckView {

    @BindView(R.id.recyclerview_sign)
    RecyclerView recyclerview_sign;
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.notice)
    TextView notice;
    @BindView(R.id.sign_video)
    Button sign_video;
    @BindView(R.id.sign_normal)
    Button sign_normal;
    SignPresent signPresent;
    Kisdi signInfo;
    CheckAdapter checkAdapter;
    boolean  isSign;
    private MaxRewardedAd rewardedAd;
    private int           retryAttempt;
    Dialog alertDialog;
    @Override
    protected OverPresent initModel() {
        return new SignPresent(this);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_check_in);

    }
    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    private static Intent intentof(Context context) {
        return new Intent(context, CheckInActivity.class);
    }
    @Override
    protected void initView() {
        initData();
    }

    private void initData() {
        title.setText(R.string.check);
        backgo.setOnClickListener(v -> {
            try {
                if (ViewTools.isFastDoubleClick(R.id.backgo)) {
                    return;
                }
                if (signInfo != null && signInfo.status == 1) {
                    BusSendEvent appEvent = new BusSendEvent(0x14, 1);
                    EventBus.getDefault().postSticky(appEvent);
                }
                finish();
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
        });
        if(signPresent==null){
            signPresent= (SignPresent) getModel();
        }
        showReqProgress(true,getString(R.string.load));
        signPresent.getCheckData();

        createRewardedAd();
        sign_normal.setOnClickListener(v -> {
            try {
                if (ViewTools.isFastDoubleClick(R.id.sign_normal)) {
                    return;
                }
                if (signInfo != null) {
                    if (signInfo.status == 0) {
                        signPresent.checkPoints(0);
                    } else {
                        showToast(getString(R.string.sign_aldec));
                    }
                }
                ProjectTools.upAFF(Constans.daily_checkin_click, 0, FirebaseAnalytics.getInstance(CheckInActivity.this));
                WorksUtils.insertData(CheckInActivity.this, 0, 12, 14, "0", "0", "0", 0, "0");
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
        });
        sign_video.setOnClickListener(v -> {
            try {
                if (ViewTools.isFastDoubleClick(R.id.sign_video)) {
                    return;
                }
                if (signInfo != null) {
                    if (signInfo.status == 0) {
                        watchVideo();
                    } else {
                        showToast(getString(R.string.sign_aldec));
                    }
                }
                ProjectTools.upAFF(Constans.daily_video_checkin_click, 0, FirebaseAnalytics.getInstance(CheckInActivity.this));

            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
        });
    }
    private void watchVideo() {
        try {
//            signPresent.sendlogs(WorksUtils.getString(0,13,14,"0"));
            if (rewardedAd.isReady()) {
                rewardedAd.showAd(Constans.check_vieo);
            } else {
                showToast(R.string.no_video);
                signPresent.checkPoints(0);
            }
            WorksUtils.insertData(CheckInActivity.this, 0, 13, 14, "0", "0", "0", 0, "0");

        }catch (Exception e){
            LogInfo.e(e.toString());
        }

    }
    private void createRewardedAd()
    {
        rewardedAd = MaxRewardedAd.getInstance( getString(R.string._video), this );
        rewardedAd.setListener( maxRewardedAdListener );
        rewardedAd.loadAd();
    }

    @Override
    public void onOkSign(UserView user) {
        try {
            showToast(CheckInActivity.this.getString(R.string.check) + CheckInActivity.this.getString(R.string.success));
            sign_normal.setText(CheckInActivity.this.getString(R.string.signed));
            sign_normal.setTextColor(CheckInActivity.this.getResources().getColor(R.color.sign_color));
            sign_normal.setBackground(CheckInActivity.this.getDrawable(R.drawable.back_home));
            sign_video.setBackground(CheckInActivity.this.getDrawable(R.drawable.back_home));
            sign_video.setTextColor(CheckInActivity.this.getResources().getColor(R.color.sign_color));
            onReward(user);
            signInfo.status = 1;
            int number=signInfo.round_the_clock;
            checkAdapter.getData().get(number).status=1;
            checkAdapter.notifyDataSetChanged();

        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
    public void onReward(UserView user) {
        try{
            String credits = "" + user.points;
            if(credits.contains(".")){
                credits=credits.split("\\.")[0];
            }
            float last= Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));
            int lastPoints=(int)last;
            int currentPoints=Integer.valueOf(credits);
//            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                showAddPoints(currentPoints, lastPoints);
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits  );

        }catch (Exception|Error e){
            LogInfo.e(e.toString());
        }
    }
    private void showAddPoints(int currentPoints,int beforePoints){
        if(alertDialog==null){
            alertDialog=new Dialog(CheckInActivity.this);
            alertDialog.setCancelable(false);
        }else{
            if(alertDialog.isShowing()){
                alertDialog.dismiss();
            }
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            View view = LayoutInflater.from(CheckInActivity.this).inflate(R.layout.layout_points, null);
            alertDialog.setContentView(view);
            window.setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.DialogBottom);
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            WindowManager.LayoutParams params = window.getAttributes();
            Point point = new Point();
            Display display = CheckInActivity.this.getWindowManager().getDefaultDisplay();
            // 将window的宽高信息保存在point中
            display.getSize(point);
            params.width = point.x;
            LogInfo.e("--"+point.x);
            params.height=WindowManager.LayoutParams.WRAP_CONTENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;//|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            params.dimAmount = 0.5f;
            window.setAttributes(params);
            RollingTextView my_point = view.findViewById(R.id.my_point);
            my_point.addCharOrder(CharOrder.Number);
            my_point.setAnimationDuration(2000L);
            my_point.setText(""+currentPoints);
            TextView addPoints= view.findViewById(R.id.addPoints);
            TextView desc= view.findViewById(R.id.desc);
            LinearLayout ok=view.findViewById(R.id.sure);
            int nowPoints=currentPoints-beforePoints;
            addPoints.setText("+"+nowPoints);
            float max=(Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.MAX_COUNT, "100000")));
            int maxs= (int) max;
            if(currentPoints>=maxs){
                desc.setText(getString(R.string.finished));
            }else{
                int gap=maxs-currentPoints;
                desc.setText(getString(R.string.unenough).replace("xxx",""+gap));
            }
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            window.setBackgroundDrawableResource(android.R.color.transparent);

        }
        alertDialog.show();
    }
    MaxRewardedAdListener maxRewardedAdListener=new MaxRewardedAdListener() {
        @Override
        public void onRewardedVideoStarted(MaxAd ad) {
            LogInfo.e("onRewardedVideoStarted");
        }

        @Override
        public void onRewardedVideoCompleted(MaxAd ad) {
            LogInfo.e("onRewardedVideoCompleted");

        }

        @Override
        public void onUserRewarded(MaxAd ad, MaxReward reward) {
            LogInfo.e("onUserRewarded");
            try {
                String name = ad.getPlacement();
                LogInfo.e(name);
                if (!TextUtils.isEmpty(name)) {
                    if ( name.equals(Constans.check_vieo)) {
                        LogInfo.e("1");
                        isSign=true;
//                        signPresent.checkPoints(1);
                    }
                }
            }catch (Exception e){
                LogInfo.e(e.toString());
            }
        }

        @Override
        public void onAdLoaded(MaxAd ad) {
            LogInfo.e("onAdLoaded");
            retryAttempt = 0;
        }

        @Override
        public void onAdDisplayed(MaxAd ad) {
            LogInfo.e("onAdDisplayed");

        }

        @Override
        public void onAdHidden(MaxAd ad) {
            LogInfo.e("onAdHidden");

            try {
                String name = ad.getPlacement();
                LogInfo.e(name);
                if (!TextUtils.isEmpty(name)) {
                    if ( name.equals(Constans.check_vieo)&&isSign) {
                        LogInfo.e("1");
                        isSign=false;
                        signPresent.checkPoints(1);
                    }
                }
                rewardedAd.loadAd();
            }catch (Exception e){
                LogInfo.e(e.toString());
            }

        }

        @Override
        public void onAdClicked(MaxAd ad) {
            LogInfo.e("onAdClicked");

        }

        @Override
        public void onAdLoadFailed(String adUnitId, MaxError error) {
            LogInfo.e("onAdLoadFailed");
            retryAttempt++;
            long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

            new Handler().postDelayed(() -> rewardedAd.loadAd(), delayMillis );
        }

        @Override
        public void onAdDisplayFailed(MaxAd ad, MaxError error) {
            rewardedAd.loadAd();
        }
    };
    private void setAdapter(List<CheckBean> list){
        if(checkAdapter ==null){
            checkAdapter = new CheckAdapter(CheckInActivity.this, R.layout.item_check, list);
            checkAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(CheckInActivity.this, 3);
            recyclerview_sign.addItemDecoration(new ItemGap(14, 12));
            recyclerview_sign.setLayoutManager(gridLayoutManager);
            recyclerview_sign.setAdapter(checkAdapter);

//            signAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//                SIgnBean sIgnBean=list.get(position);
//                if(sIgnBean!=null&&sIgnBean.status==0&&signInfo.status==0) {
//                    signPresent.checkPoints(0);
//                }
//            });
            checkAdapter.setOnItemClickListener((adapter, view, position) -> {
                CheckBean checkBean =list.get(position);
                if(checkBean !=null&& checkBean.status==0&&signInfo.status==0) {
                    signPresent.checkPoints(0);
                }
            });
        }else{
            checkAdapter.getData();
            checkAdapter.getData().clear();
            checkAdapter.addData(list);
        }

    }

    @Override
    public void onCheckInfo(Kisdi kisdi) {
        try {
            hideReqProgress();
//            LogInfo.e("onCheckInfo");
            signInfo = kisdi;
            if (signInfo != null) {
                String userLevelConf=signInfo.userLevelConf;
                int todayStatus=signInfo.status;
                if(todayStatus==1){
                    sign_normal.setText(CheckInActivity.this.getString(R.string.signed));
                    sign_normal.setTextColor(CheckInActivity.this.getResources().getColor(R.color.sign_color));
                    sign_normal.setBackground(CheckInActivity.this.getDrawable(R.drawable.back_home));
                    sign_video.setBackground(CheckInActivity.this.getDrawable(R.drawable.back_home));
                }
                if(!TextUtils.isEmpty(userLevelConf)&&userLevelConf.contains(",")){
                    String[] userinfo=userLevelConf.split(",");
                    List<CheckBean> list=new ArrayList<>();
                    int nowstatus=signInfo.status;
                    int day=signInfo.round_the_clock;
                    for(int i=0;i<userinfo.length;i++){
                        int itemStatus=i+1;
                        CheckBean checkBean =new CheckBean();
                        checkBean.points=userinfo[i];
                        int value=signInfo.in_status.get(""+itemStatus);
                        if(value==1){
                            checkBean.status=1;
                        }else{
                            if(value==0){
                                if(nowstatus==0){
                                    if(day==0&&i==0) {
                                        checkBean.status = 0;
                                    }else{
                                        if(day==itemStatus){
                                            checkBean.status = 0;
                                        }else {
                                            checkBean.status = 2;
                                        }
                                    }
                                }else{
                                    checkBean.status=2;
                                }
                            }
                        }
                        list.add(checkBean);
                    }
                    if(list.size()>0){
                        setAdapter(list);
                    }else{
                    }
                }
            } else {
                sign_normal.setText(CheckInActivity.this.getString(R.string.signed));
                sign_normal.setTextColor(CheckInActivity.this.getResources().getColor(R.color.sign_color));
                sign_normal.setBackground(CheckInActivity.this.getDrawable(R.drawable.back_home));
                sign_video.setBackground(CheckInActivity.this.getDrawable(R.drawable.back_home));
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onError() {
        hideReqProgress();
    }
}