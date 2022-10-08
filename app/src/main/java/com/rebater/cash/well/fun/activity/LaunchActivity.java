package com.rebater.cash.well.fun.activity;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;
import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.rebater.cash.well.fun.util.DialogTools;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.OpenTool;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;

import java.util.List;

import butterknife.BindView;

public class LaunchActivity extends OverActivity implements IModel.FirstView {

    @BindView(R.id.progress)
    ProgressBar progress; //圆形加载进度条
    LauncherPresent launcherPresent;
    private FirebaseAnalytics mFirebaseAnalytics;
    String email;

    Piskos sdk3o;


    @Override //初始化Present
    protected OverPresent initModel() {
        return new LauncherPresent(this);
    }

    @Override
    protected void initContentView() { //初始化视图
        isFullScreen();
        setContentView(R.layout.activity_launch);
    }

    private void isFullScreen() {
        try {
            if (!isTaskRoot()) {
                finish();
                LogInfo.e("finish");
                return;
            }
            if (Build.VERSION.SDK_INT >= 25) { //SDK版本
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
    public void getToken(){ //FireBase
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            LogInfo.e("failed" + task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        LogInfo.e(token);
                    }
                });
    }

    @Override
    protected void initView() {
        try {
            int status = OpenTool.getNetworkType(this);
            LogInfo.e("s--"+status);
            SelfPrefrence.INSTANCE.setString(SelfValue.NETSTATUS, "" + status);
            launcherPresent = (LauncherPresent) getModel();  //创建launcherPresent实例
            if (ProjectTools.vpnActive(this)) {
                SelfPrefrence.INSTANCE.setString(SelfValue.ISVPSNXCS, "1");
            } else {
                SelfPrefrence.INSTANCE.setString(SelfValue.ISVPSNXCS, "0");
            }
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this); //埋点工具类
            getToken();
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISFIRST, true)) {
                //判断是否第一次登录
                LoginActivity.gotoLogin(this);
                finish();
            } else {
                LogInfo.e("getCation");
                launcherPresent.getCation(Constans.urlLocation);
                LogInfo.e("getCation Over");
            }

            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISNOTIFY, false)) {
//                Intent intent = new Intent(this, WalkService.class);
//                intent.putExtra("type", 0);
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                    //适配8.0机制
//                    startForegroundService(intent);
//                } else {
//                    startService(intent);
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPeizhi(UserInfo userInfo) {
        progress.setVisibility(View.GONE);
        if (userInfo != null && userInfo.config != null) {
            ProjectTools.setSp(userInfo, mFirebaseAnalytics, LaunchActivity.this);
            MainActivity.startActivity(LaunchActivity.this); //跳转主界面
            finish();
        } else {
            gotoError(1);
        }
    }

    @Override
    public void onPatner(List<Oiswd> tabls) {

    }

    @Override
    public void onMan(List<Okdko> list) {

    }

    @Override
    public void onLocationError() {
        WorksUtils.sendInfo(LaunchActivity.this, SelfPrefrence.INSTANCE.getString(SelfValue.USER_CODE, ""), launcherPresent, null, 0, null);
    }

    @Override
    public void onLocation(Piskos info) {
        sdk3o=info;
        //        handler.sendEmptyMessageDelayed(0x11,2000);
        ProjectTools.saveInfo(sdk3o, LaunchActivity.this, launcherPresent, 1, email);
    }

    @Override
    public void onError() {
        progress.setVisibility(View.GONE);  //进度条彻底消失
        progress.setVisibility(View.GONE);
        gotoError(1);
    }
    public void gotoError(int type){
        DialogTools.showPushDialog(LaunchActivity.this, type);
        String types = ProjectTools.getABtype();
        ProjectTools.upnewEventView(types, Constans.user_login_err, mFirebaseAnalytics, 2);
        WorksUtils.insertData(LaunchActivity.this, 0, 69, 15, "0", "0", Constans.user_login_err, 0, "0");

    }
}
