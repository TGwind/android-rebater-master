package com.rebater.cash.well.fun.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.run.StepCount;
import com.rebater.cash.well.fun.run.StepValuePassListener;
import com.rebater.cash.well.fun.run.UpdateUiCallBack;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;

public class WalkService extends Service implements SensorEventListener {
    private static final String CHANNAL_ID = "notify";
    private static final int NOTIFICATION_CUSTOM_HEADS_UP = 1;

    private LcBinder lcBinder = new LcBinder();

    private int nowBuSu = 0;

    private SensorManager sensorManager;

    private StepCount mStepCount;

    private UpdateUiCallBack mCallback;

    private static int stepSensorType = -1;

    private boolean hasRecord = false;

    private int hasStepCount = 0;
    private int notifyStep;
    NotificationCompat.Builder builder;
    private int previousStepCount = 0;
    RemoteViews headsUpView;
    Notification notification;
    Context context;

    public WalkService() {
    }
    int type;
    TextView textView;
    NotificationManager mNotificationManager;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x12) {
                LogInfo.e(""+msg.arg1+"type"+type);
//                builder.getContentView().setTextViewText(R.id.textView3,""+msg.arg1);
                if(type==1) {
                    headsUpView.setTextViewText(R.id.step, "" + msg.arg1);
                }else{
                    headsUpView.setTextViewText(R.id.step,""+ SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0));
                }
                String  points= SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0");
                headsUpView.setTextViewText(R.id.points,points);
                mNotificationManager.notify(NOTIFICATION_CUSTOM_HEADS_UP,notification);
//                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        nowBuSu=SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0);
        LogInfo.e("start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
                LogInfo.e("run");
            }
        }).start();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Android 8.0开始要设置通知渠道
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNAL_ID,
                    "notify", NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false);
            channel.enableVibration(false);
            channel.setVibrationPattern(new long[]{0});
            channel.setSound(null, null);
            mNotificationManager.createNotificationChannel(channel);
        }
        sendCustomHeadsUpViewNotification(this);

    }
    public void sendCustomHeadsUpViewNotification(Context context) {
        //创建点击通知时发送的广播
        Intent intent = new Intent(context, SkipActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("notyfy", 2);
        PendingIntent pi = PendingIntent.getActivity(context,0,intent,0);
        String  points= SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0");
        headsUpView = new RemoteViews(context.getPackageName(),R.layout.toast);
        headsUpView.setTextViewText(R.id.points,points);
        headsUpView.setTextViewText(R.id.step,""+SelfPrefrence.INSTANCE.getInt(SelfValue.RUNSTEP, 0));
        //创建通知
        builder = new NotificationCompat.Builder(context,CHANNAL_ID)
                //设置通知左侧的小图标
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置通知标题
                .setContentTitle(getString(R.string.app_name))
                //设置通知内容
                .setContentText(getString(R.string.sign_dec))
                //设置点击通知后自动删除通知
                .setAutoCancel(false)
                //设置通知不可删除
                .setOngoing(true)
                //设置显示通知时间
                .setShowWhen(true)
                //设置点击通知时的响应事件
                .setContentIntent(pi)
                //设置全屏响应事件;
                .setFullScreenIntent(pi,true)
                .setContent(headsUpView);
        //设置自定义顶部提醒视图
//                .setCustomHeadsUpContentView(headsUpView);
        notification=builder.build();
        //发送通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            startForeground(NOTIFICATION_CUSTOM_HEADS_UP,notification );
        }else {
            mNotificationManager.notify(NOTIFICATION_CUSTOM_HEADS_UP, notification);
        }
    }
    private void startStepDetector() {
        if (sensorManager != null) {
            sensorManager = null;
        }
        //获取传感器管理类
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        int versionCodes = Build.VERSION.SDK_INT;//取得SDK版本
        if (versionCodes >= 19) {
            //SDK版本大于等于19开启计步传感器
            addCountStepListener();
        } else {        //小于就使用加速度传感器
            addBasePedometerListener();
        }
    }

    private void addCountStepListener() {
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if (countSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_COUNTER;
            sensorManager.registerListener(WalkService.this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
            LogInfo.e("Sensor.TYPE_STEP_COUNTER");
        } else if (detectorSensor != null) {
            stepSensorType = Sensor.TYPE_STEP_DETECTOR;
            sensorManager.registerListener(WalkService.this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            addBasePedometerListener();
        }
    }

    private void addBasePedometerListener() {
        LogInfo.e("BindService");
        mStepCount = new StepCount();
        mStepCount.setSteps(nowBuSu);
        //获取传感器类型 获得加速度传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //此方法用来注册，只有注册过才会生效，参数：SensorEventListener的实例，Sensor的实例，更新速率
        boolean isAvailable = sensorManager.registerListener(mStepCount.getStepDetector(), sensor, SensorManager.SENSOR_DELAY_UI);
        mStepCount.initListener(new StepValuePassListener() {
            @Override
            public void stepChanged(int steps) {
                nowBuSu = steps;//通过接口回调获得当前步数
                updateNotification();    //更新步数通知
            }
        });
    }

    private void updateNotification() {
        if(type==1) {
            if (mCallback != null) {
                LogInfo.e("refresh-" + nowBuSu);
                mCallback.updateUi(nowBuSu);
                if (nowBuSu - notifyStep >5) {
                    notifyStep = nowBuSu;
                    Message message = handler.obtainMessage();
                    message.arg1 = nowBuSu;
                    message.what = 0x12;
                    handler.sendMessage(message);
                }
            }
        }else{
            if (nowBuSu - notifyStep > 5) {
                notifyStep = nowBuSu;
                Message message = handler.obtainMessage();
                message.arg1 = nowBuSu;
                message.what = 0x12;
                handler.sendMessage(message);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {

        return lcBinder;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        //这种类型的传感器返回步骤的数量由用户自上次重新启动时激活。返回的值是作为浮动(小数部分设置为0),
        // 只在系统重启复位为0。事件的时间戳将该事件的第一步的时候。这个传感器是在硬件中实现,预计低功率。
        if (stepSensorType == Sensor.TYPE_STEP_COUNTER) {
            //获取当前传感器返回的临时步数
            int tempStep = (int) event.values[0];
            //首次如果没有获取手机系统中已有的步数则获取一次系统中APP还未开始记步的步数
            if (!hasRecord) {
                hasRecord = true;
                hasStepCount = tempStep;
            } else {
                //获取APP打开到现在的总步数=本次系统回调的总步数-APP打开之前已有的步数
                int thisStepCount = tempStep - hasStepCount;
                //本次有效步数=（APP打开后所记录的总步数-上一次APP打开后所记录的总步数）
                int thisStep = thisStepCount - previousStepCount;
                //总步数=现有的步数+本次有效步数
                nowBuSu += (thisStep);
                //记录最后一次APP打开到现在的总步数
                previousStepCount = thisStepCount;
            }
        }
        //这种类型的传感器触发一个事件每次采取的步骤是用户。只允许返回值是1.0,为每个步骤生成一个事件。
        // 像任何其他事件,时间戳表明当事件发生(这一步),这对应于脚撞到地面时,生成一个高加速度的变化。
        else if (stepSensorType == Sensor.TYPE_STEP_DETECTOR) {
            if (event.values[0] == 1.0) {
                nowBuSu++;
            }
        }
        updateNotification();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public class LcBinder extends Binder {
        public WalkService getService() {
            return WalkService.this;
        }
    }

    public void registerCallback(UpdateUiCallBack paramICallback) {
        this.mCallback = paramICallback;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (intent != null) {
                type = intent.getIntExtra("type", 0);
            }
            if (notification != null && mNotificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForeground(NOTIFICATION_CUSTOM_HEADS_UP, notification);
                } else {
                    mNotificationManager.notify(NOTIFICATION_CUSTOM_HEADS_UP, notification);
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        //返回START_STICKY ：在运行onStartCommand后service进程被kill后，那将保留在开始状态，但是不保留那些传入的intent。
        // 不久后service就会再次尝试重新创建，因为保留在开始状态，在创建     service后将保证调用onstartCommand。
        // 如果没有传递任何开始命令给service，那将获取到null的intent。
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //取消前台进程
        stopForeground(true);

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
