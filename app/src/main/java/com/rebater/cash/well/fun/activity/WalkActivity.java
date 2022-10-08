package com.rebater.cash.well.fun.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
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

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.present.WaysPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.bumptech.glide.Glide;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;

import java.util.List;

import butterknife.BindView;

public class WalkActivity extends OverActivity implements IModel.ProduceView {
    @BindView(R.id.steps)
    TextView steps;
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.step_rate)
    TextView step_rate;
    @BindView(R.id.point)
    TextView point;
    @BindView(R.id.rule)
    TextView rule;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.running)
    ImageView running;
    public static int PERMISSION_RECORD = 1;
    public static int PERMISSION_WINDOW = 2;
    int  sub2;
    int proId;
    WaysPresent waysPresent;
    boolean isClick;
    Dialog alertDialog;

    private WalkService walkService;
    private boolean isBind,isSet;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                steps.setText(msg.arg1 + "");
                int mPoint=msg.arg1/sub2;
                point.setText("+"+mPoint);
            }else if(msg.what==0x13){
                isClick=true;
                start.setBackground(getDrawable(R.drawable.dra_button));
                start.setClickable(true);
            }
        }
    };

    @Override
    protected OverPresent initModel() {
        return new WaysPresent(this);
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_run);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        try {
            isSet=false;
            SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISNOTIFY,true);
            waysPresent = (WaysPresent) getModel();
            title.setText(R.string.walk);
            backgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ViewTools.isFastDoubleClick(R.id.backgo)) {
                        return;
                    }
                    finish();
                }
            });
            getoperation();
            proId = SelfPrefrence.INSTANCE.getInt(SelfValue.CONFIGID, 0);
            sub2 = Integer.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.SUB2, "10"));
            String sub3 = SelfPrefrence.INSTANCE.getString(SelfValue.SUB3, "10000");
            String sub4 = SelfPrefrence.INSTANCE.getString(SelfValue.SUB4, "1200");

            if (!SelfPrefrence.INSTANCE.getString(SelfValue.STEPDAY, "1").equals(ProjectTools.getCurrDateFormat())) {
                SelfPrefrence.INSTANCE.setInt(SelfValue.RUNSTEP, 0);
                SelfPrefrence.INSTANCE.setString(SelfValue.STEPDAY, ProjectTools.getCurrDateFormat());
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISFINISHSTEP, true);
            }
            int stepdaily = SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0);
            steps.setText("" + stepdaily);
            step_rate.setText(getString(R.string.goal) + " /" + sub3);
            String  rules=getString(R.string.rule).replace("10xx",""+sub2).replace("1,200xx",sub4).replace("10,000xx",sub3);
            rule.setText(rules);
            int mPoint = stepdaily / sub2;
            point.setText("+" + mPoint);
            if (stepdaily < 10) {
                isClick = false;
                start.setBackground(getDrawable(R.drawable.back_home));
                start.setClickable(false);
            } else {
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISFINISHSTEP, true)) {
                    isClick = true;
                    start.setBackground(getDrawable(R.drawable.dra_money));
                    start.setClickable(true);
                } else {
                    isClick = false;
                    start.setBackground(getDrawable(R.drawable.back_home));
                    start.setClickable(false);
                }
            }

            start.setOnClickListener(view -> {
                if (!isClick) {
                    return;
                }
                if (!SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISFINISHSTEP, true)) {
                    return;
                }
                if (SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0) > 10) {
                    showPermission(3, WalkActivity.this);
                }
            });
            Glide.with(this).load(R.drawable.running_icon).into(running);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getoperation() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            //  用户未彻底拒绝授予权限
            showPermission(1, this);
        } else {
//            if (!Settings.canDrawOverlays(this)) {
//                showPermission(2, this);
//
//            }
            startMyservice();
        }
//        }else{
//
//        }

    }

    public void showPermission(int type, AppCompatActivity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ask, null);
        Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        Button yes_button = view.findViewById(R.id.yes_button);
        Button no_button = view.findViewById(R.id.no_button);
        TextView content = view.findViewById(R.id.content);
        if (type == 1) {
            content.setText(getString(R.string.per1));
        } else if (type == 2) {
            content.setText(getString(R.string.per2));
        } else {
            content.setText(getString(R.string.per3));
            yes_button.setText(getString(R.string.yes_s));
            no_button.setText(getString(R.string.no_n));
        }
        yes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == 1) {
                    ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, PERMISSION_RECORD);
                } else if(type==2){
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
                    startActivityForResult(intent, PERMISSION_WINDOW);
                }else{
                    showReqProgress(true,getString(R.string.refresh));
                    waysPresent.sendStep(proId,SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0));
                }
                dialog.dismiss();
            }
        });
        no_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PERMISSION_WINDOW) {
            LogInfo.e("win.get");
        }
    }

    private static Intent intentof(Context context) {
        return new Intent(context, WalkActivity.class);
    }

    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }
    private void startMyservice() {
        Intent intent = new Intent(WalkActivity.this, WalkService.class);
        intent.putExtra("type",1);
        isBind = bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //适配8.0机制
            startForegroundService(intent);
        } else {
            startService(intent);
        }

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            WalkService.LcBinder lcBinder = (WalkService.LcBinder) service;
            walkService = lcBinder.getService();
            walkService.registerCallback(stepCount -> {
                if (stepCount >= 10) {
                    if (!isSet) {
                        handler.sendEmptyMessage(0x13);
                        isSet = true;
                    }
                }
                int stepdaily = SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0);
                if (stepCount - stepdaily > 5) {
                    SelfPrefrence.INSTANCE.setInt(SelfValue.RUNSTEP, stepCount);
                }
                //当前接收到stepCount数据，就是最新的步数
                Message message = Message.obtain();
                message.what = 1;
                message.arg1 = stepCount;
                handler.sendMessage(message);
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    // 申请成功
                    startMyservice();
                } else {
                    // 申请失败
                }
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {  //app被关闭之前，service先解除绑定
        super.onDestroy();
        if (isBind) {
            this.unbindService(serviceConnection);
        }
    }

    @Override
    public void onModel(List<SimpleInfo> simpleInfoList) {

    }

    @Override
    public void onService(List<Okdko> list) {

    }

    private void showAddPoints(int currentPoints,int beforePoints){
        if(alertDialog==null){
            alertDialog=new Dialog(WalkActivity.this);
            alertDialog.setCancelable(false);
        }else{
            if(alertDialog.isShowing()){
                alertDialog.dismiss();
            }
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            View view = LayoutInflater.from(WalkActivity.this).inflate(R.layout.layout_points, null);
            alertDialog.setContentView(view);
            window.setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.DialogBottom);
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            WindowManager.LayoutParams params = window.getAttributes();
            Point point = new Point();
            Display display = WalkActivity.this.getWindowManager().getDefaultDisplay();
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

    @Override
    public void onError() {
        hideReqProgress();
        super.onError();
    }

    @Override
    public void onCash(UserView userView) {
        try {
            hideReqProgress();
            String credits = "" + userView.points;
            if(credits.contains(".")){
                credits=credits.split("\\.")[0];
            }
            float last= Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));
            int lastPoints=(int)last;
            int currentPoints=Integer.valueOf(credits);
//            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            showAddPoints(currentPoints,lastPoints);
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            isClick = false;
            SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISFINISHSTEP, false);
            start.setBackground(getDrawable(R.drawable.back_home));
            start.setClickable(false);
//            showToast(R.string.success_reward);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }


    }
}