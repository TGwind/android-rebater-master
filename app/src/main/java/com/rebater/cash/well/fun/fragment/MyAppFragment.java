package com.rebater.cash.well.fun.fragment;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.activity.CheckInActivity;
import com.rebater.cash.well.fun.activity.MainActivity;
import com.rebater.cash.well.fun.activity.SkipActivity;
import com.rebater.cash.well.fun.activity.TaskDetailActivity;
import com.rebater.cash.well.fun.activity.WalkActivity;
import com.rebater.cash.well.fun.bean.BusSendEvent;
import com.rebater.cash.well.fun.bean.Kisdi;
import com.rebater.cash.well.fun.bean.Odfi3a;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Qisks;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.VideoConfig;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.common.OverFragment;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.AppleAdapter;
import com.rebater.cash.well.fun.match.OrangeAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.MyAppPresent;
import com.rebater.cash.well.fun.present.OfferPresent;
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
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.ads.MaxRewardedAd;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.RemoteMessage;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class MyAppFragment extends OverFragment implements View.OnClickListener, IModel.MyAppView {

    @BindView(R.id.all_sign)
    LinearLayout all_sign;
    @BindView(R.id.all_video)
    LinearLayout all_video;
    @BindView(R.id.video_in)
    LinearLayout video_go;
    @BindView(R.id.sign_in)
    LinearLayout sign_in;
    @BindView(R.id.all_run)
    LinearLayout all_run;
    @BindView(R.id.run_go)
    LinearLayout run_go;
    @BindView(R.id.check_in)
    TextView check_in;
    @BindView(R.id.right_point)
    TextView right_point;
    @BindView(R.id.units)
    TextView units;
    @BindView(R.id.top_extra)
    LinearLayout top_extra;
    @BindView(R.id.left_point)
    TextView lef_point;
    @BindView(R.id.exchange_in)
    LinearLayout exchange_in;
    @BindView(R.id.wall_name)
    TextView wall_name;
    @BindView(R.id.wall)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.progress_text)
    TextView progress_text;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.myTasks)
    RecyclerView myTasks;
    private String mParam1;
    private String mParam2;
    MyAppPresent myAppPresent;
    Kisdi mKisdi;
    long currenttime;
    private MaxRewardedAd rewardedAd;
    private int retryAttempt;
    private MaxInterstitialAd interstitialAd;
    private int retryAttemptIn;
    AppleAdapter appleAdapter;
    String coinType;
    OrangeAdapter wallTableAdapter;
    Timer timer;
    Qisks mQisks;
    Dialog alertDialog;
    boolean isSign, isWatchVideo;
    FirebaseAnalytics mFirebaseAnalytics;
    VideoConfig videoConfig;
    Bitmap bitmap;
    String push_title, push_content, push_channelId;
    @BindView(R.id.goon)
    TextView goon;

    public MyAppFragment() {
        // Required empty public constructor
    }

    public static MyAppFragment newInstance(String param1, String param2) {
        MyAppFragment fragment = new MyAppFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_game;
    }

    @Override
    public int initView(View view) {
        setListen();
        initAd();
        showInfo();
        return 0;
    }

    private void showAddPoints(int currentPoints, int beforePoints) {
        if (alertDialog == null) {
            alertDialog = new Dialog(getBaseActivity());
            alertDialog.setCancelable(false);
        } else {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.layout_points, null);
            alertDialog.setContentView(view);
            window.setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.DialogBottom);
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            WindowManager.LayoutParams params = window.getAttributes();
            Point point = new Point();
            Display display = getBaseActivity().getWindowManager().getDefaultDisplay();
            // 将window的宽高信息保存在point中
            display.getSize(point);
            params.width = point.x;
            LogInfo.e("--" + point.x);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;//|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//            params.dimAmount = 0.5f;
            window.setAttributes(params);
            RollingTextView my_point = view.findViewById(R.id.my_point);
            my_point.addCharOrder(CharOrder.Number);
            my_point.setAnimationDuration(2000L);
            my_point.setText("" + currentPoints);
            TextView addPoints = view.findViewById(R.id.addPoints);
            TextView desc = view.findViewById(R.id.desc);
            LinearLayout ok = view.findViewById(R.id.sure);
            int nowPoints = currentPoints - beforePoints;
            addPoints.setText("+" + nowPoints);
            float max = (Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.MAX_COUNT, "100000")));
            int maxs = (int) max;
            if (currentPoints >= maxs) {
                desc.setText(getString(R.string.finished));
            } else {
                int gap = maxs - currentPoints;
                desc.setText(getString(R.string.unenough).replace("xxx", "" + gap));
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

    private void showInfo() {
        if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_PUSH, false)) {
            int count = SelfPrefrence.INSTANCE.getInt(SelfValue.Push_count, 1);
            if (!SelfPrefrence.INSTANCE.getString(SelfValue.PUSH_DATE, "0").equals(ProjectTools.getCurrDateFormat())) {
                SelfPrefrence.INSTANCE.setString(SelfValue.PUSH_DATE, ProjectTools.getCurrDateFormat());
                SelfPrefrence.INSTANCE.setInt(SelfValue.current_count, 1);
                showPushDialog();
            } else {
                if (SelfPrefrence.INSTANCE.getInt(SelfValue.current_count, 0) < count) {
                    SelfPrefrence.INSTANCE.setInt(SelfValue.current_count, SelfPrefrence.INSTANCE.getInt(SelfValue.current_count, 0) + 1);
                    showPushDialog();
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceivedData(BusSendEvent busSendEvent) {
        try {
            if (busSendEvent != null && (int) busSendEvent.getMsg() == 0x13) {
                LogInfo.e("onReceivedInfo()");
                List<Woask> list = (List<Woask>) busSendEvent.getData();
                if (list != null && list.size() > 0) {
                    List<Woask> mList = ProjectTools.getShowInfo(list);
                    if (mList != null && mList.size() > 0) {
                        goon.setVisibility(View.VISIBLE);
                        if (appleAdapter == null) {
                            appleAdapter = new AppleAdapter(getBaseActivity(), R.layout.item_pass, mList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                            myTasks.setLayoutManager(linearLayoutManager);
                            myTasks.addItemDecoration(new ItemGap(20, 4));
                            myTasks.setAdapter(appleAdapter);
                            appleAdapter.setOnItemClickListener((adapter, view, position) -> {
                                Woask woask = mList.get(position);
                                if (woask != null) {
//                    DetailActivity.startActivity(getBaseActivity(),null,offerInfo1,1,0);
                                    makeHistory(woask);
                                    WorksUtils.insertData(getBaseActivity(), woask.t_id, 14, 2, "" + position, "" + woask.tpl_id, "0", 0, woask.offerInfo.down_pkg);
                                }
                            });
                        } else {
                            appleAdapter.getData();
                            appleAdapter.getData().clear();
                            appleAdapter.addData(mList);
                        }
                    }
                }
            } else if (busSendEvent != null && (int) busSendEvent.getMsg() == 0x14) {
                LogInfo.e("change");
                check_in.setText(getBaseActivity().getString(R.string.signed));
                check_in.setTextColor(getBaseActivity().getResources().getColor(R.color.sign_color));
                sign_in.setBackground(getBaseActivity().getDrawable(R.drawable.back_home));
                ((MainActivity) getBaseActivity()).getmoneyTextView().setText(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));

            } else if (busSendEvent != null && (int) busSendEvent.getMsg() == 0x16) {
//                Toast.makeText(getBaseActivity(),"-receive info-",Toast.LENGTH_LONG).show();
                RemoteMessage.Notification notification = (RemoteMessage.Notification) busSendEvent.getData();
                if (notification != null) {
                    sendCustomHeadsUpViewNotification(notification);
                }
                ProjectTools.upAFF(Constans.push_notification, 0, mFirebaseAnalytics);
            }
        } catch (Exception E) {
            LogInfo.e(E.toString());
        }
    }

    public void sendNotification(Context context) {
        try {
            //创建点击通知时发送的广播
            Intent intent = new Intent(context, SkipActivity.class);
            intent.putExtra("notyfy", 1);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            LogInfo.e(push_content + "--" + push_title + "--" + push_channelId);

            String points = SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0");
            RemoteViews headsUpView = new RemoteViews(context.getPackageName(), R.layout.notify_small_pic);
            headsUpView.setTextViewText(R.id.title, push_title);
            headsUpView.setTextViewText(R.id.content, push_content);
            headsUpView.setImageViewBitmap(R.id.push_pic, bitmap);
            //创建通知
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, push_channelId)
                    //设置通知左侧的小图标
                    .setSmallIcon(R.mipmap.ic_launcher)
                    //设置通知标题
                    .setContentTitle(getString(R.string.app_name))
                    //设置通知内容
                    .setContentText(getString(R.string.sign_dec))
                    //设置点击通知后自动删除通知
                    .setAutoCancel(true)
                    //设置通知不可删除
                    //设置显示通知时间
                    .setShowWhen(true)
                    //设置点击通知时的响应事件
                    .setContentIntent(pi)
                    //设置全屏响应事件;
                    .setFullScreenIntent(pi, true)
                    .setContent(headsUpView);
            //设置自定义顶部提醒视图
//                .setCustomHeadsUpContentView(headsUpView);
            Notification notifications = builder.build();
            NotificationManager notificationManager = (NotificationManager) getBaseActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    createNotificationChannel(push_channelId, "" + System.currentTimeMillis(), importance, notificationManager);
                }
                //发送通知
                notificationManager.notify(1, notifications);
            } else {
                LogInfo.e("null");
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    public void sendCustomHeadsUpViewNotification(RemoteMessage.Notification notification) {
        try {
            //创建点击通知时发送的广播
            push_content = notification.getBody();
            push_title = notification.getTitle();
            Uri imageUrl = notification.getImageUrl();//.toString()
            push_channelId = notification.getChannelId();
            LogInfo.e(push_content + "--" + push_title + "--" + imageUrl + "--" + push_channelId);
            if (imageUrl != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bitmap = Glide.with(getBaseActivity()).asBitmap().load(imageUrl).submit().get();
                            handler.sendEmptyMessage(0x26);
                        } catch (ExecutionException e) {
                            LogInfo.e(e.toString());
                        } catch (InterruptedException e) {
                            LogInfo.e(e.toString());
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    //消息通道，状态栏用
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance, NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onInfoList(List<Woask> list) {

    }

    private void makeHistory(Woask woask) {
        try {
            int type = woask.type;
            if (type == 1) {
                Odfi3a odfi3a = ProjectTools.getDtailsByHistory(woask);
                if (woask.status != 3) {
                    if (woask.step != 0) {
                        if (odfi3a != null) {
                            TaskDetailActivity.startActivity(getBaseActivity(), odfi3a, 0, woask.tpl_id);
                        }
                    }
                } else {
                    showToast(getBaseActivity().getString(R.string.task_ok));
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onInfoListError() {

    }

    @Override
    public void onPageConf(VideoConfig config) {
        try {
            hideReqProgress();
            videoConfig = config;
            String today = SelfPrefrence.INSTANCE.getString(SelfValue.VIDEODAY, "0");
            int count = SelfPrefrence.INSTANCE.getInt(SelfValue.VIDEOCOUNT, 0);
            int max = videoConfig.data.limit;
            if (!today.equals(ProjectTools.getCurrDateFormat())) {
                SelfPrefrence.INSTANCE.setInt(SelfValue.VIDEOCOUNT, 0);
                progress.setMax(max);
                progress.setProgress(0);
                progress_text.setText("0/" + max);
                SelfPrefrence.INSTANCE.setString(SelfValue.VIDEODAY, ProjectTools.getCurrDateFormat());
            } else {
                progress.setMax(max);
                progress.setProgress(count);
                progress_text.setText(count + "/" + max);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void maketype() {
        int type = SelfPrefrence.INSTANCE.getInt(SelfValue.type, 0);
        String url = SelfPrefrence.INSTANCE.getString(SelfValue.url, "");
        String pkg = SelfPrefrence.INSTANCE.getString(SelfValue.pkg, "");
        if (type == 0) {
            if (!TextUtils.isEmpty(url)) {
                ProjectTools.startIntent(getBaseActivity(), url);
            }
        } else if (type == 1) {
            if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(pkg) && ProjectTools.isAvilible(getBaseActivity(), pkg)) {
                ProjectTools.startIntentWithPackage(getBaseActivity(), url, pkg);
            } else {
                ProjectTools.startIntent(getBaseActivity(), url);
            }
        } else if (type == 2) {
            showAdd();
        }
    }

    private void showPushDialog() {
        try {
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.dialog_push, null);
            Dialog dialog = new android.app.AlertDialog.Builder(getBaseActivity())
                    .setView(view)
                    .setCancelable(true)
                    .create();
            Button intall_button = view.findViewById(R.id.sure_button);
            intall_button.setText(R.string.start);
//                intall_button.setBackground(getBaseActivity().getDrawable(R.drawable.shape_code));
            ImageView icon = view.findViewById(R.id.icon_img);
            TextView textView = view.findViewById(R.id.dialog_type);
            textView.setText("");
            ImageView close = view.findViewById(R.id.close_img);
            TextView credits = view.findViewById(R.id.text_one);
            credits.setVisibility(View.GONE);
//                credits.setText("+ " + firstInfo.award + " " + getBaseActivity().getString(R.string.point));
            TextView title = view.findViewById(R.id.text_two);
            title.setVisibility(View.GONE);
//                title.setText(firstInfo.title);
            String descqa = SelfPrefrence.INSTANCE.getString(SelfValue.content, "");
            TextView desc = view.findViewById(R.id.text_three);
            desc.setText(descqa);
            Glide.with(getBaseActivity()).load(R.drawable.default__icon)
                    .transition(withCrossFade())
                    .into(icon);
            close.setOnClickListener(v -> dialog.dismiss());
            intall_button.setOnClickListener(v -> {
                maketype();
                WorksUtils.insertData(getBaseActivity(), 0, 24, 2, "0", "" + 0, "0", 0, "0");
                dialog.dismiss();
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onStart() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        super.onStart();

    }

    @Override
    public void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    private void initAd() {
        if (myAppPresent == null) {
            myAppPresent = (MyAppPresent) getModel();
        }
        showReqProgress(true, getString(R.string.load));
        myAppPresent.getCredit();
        myAppPresent.getSignData();
        myAppPresent.getVideo(3);
        createRewardedAd();
        ProjectTools.upAFF(Constans.wishlist_page_view, 0, mFirebaseAnalytics);
    }

    private void createRewardedAd() {
        rewardedAd = MaxRewardedAd.getInstance(getString(R.string._video), getBaseActivity());
        rewardedAd.setListener(maxRewardedAdListener);
        rewardedAd.loadAd();
    }

    MaxRewardedAdListener maxRewardedAdListener = new MaxRewardedAdListener() {
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
                    if (name.equals(Constans.normal_video)) {
                        isWatchVideo = true;
//                        myAppPresent.addCredits(3);
                        LogInfo.e("3");
                    }
                }
            } catch (Exception e) {
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
                    if (name.equals(Constans.normal_video) && isWatchVideo) {
                        isWatchVideo = false;
                        myAppPresent.addCredits(3);
                        LogInfo.e("3");
                    } else {
                        ProjectTools.upAFF(Constans.video_no_reward, 0, mFirebaseAnalytics);
                        WorksUtils.insertData(getBaseActivity(), 0, 70, 2, "0", "0", Constans.video_no_reward, 0, "0");
                    }
                }
                rewardedAd.loadAd();
            } catch (Exception e) {
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
            long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));

            new Handler().postDelayed(() -> rewardedAd.loadAd(), delayMillis);
        }

        @Override
        public void onAdDisplayFailed(MaxAd ad, MaxError error) {
            rewardedAd.loadAd();
        }
    };
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                if (msg.what == 0x13) {
                    timer.cancel();
                    List<Oiswd> oiswdList = ((MainActivity) getBaseActivity()).getBrotherInfos();
                    if (oiswdList != null && oiswdList.size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        wall_name.setVisibility(View.VISIBLE);
                        if (wallTableAdapter == null) {
                            wallTableAdapter = new OrangeAdapter(getContext(), R.layout.item_wall, oiswdList);
                            wallTableAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseActivity(), 3);
//                            recyclerView.addItemDecoration(new MyDeration(8, 4));
                            recyclerView.setAdapter(wallTableAdapter);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            wallTableAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                                Oiswd oiswd = oiswdList.get(position);
                                if (oiswd != null) {
                                    itemClick(oiswd, position);
                                }
                            });
                            wallTableAdapter.setOnItemClickListener((adapter, view, position) -> {
                                Oiswd oiswd = oiswdList.get(position);
                                if (oiswd != null) {
                                    itemClick(oiswd, position);
                                }
                            });
                            LogInfo.e("view");
                        } else {
                            wallTableAdapter.getData();
                            wallTableAdapter.getData().clear();
                            wallTableAdapter.addData(oiswdList);
                        }
                    } else {
                        recyclerView.setVisibility(View.GONE);
                    }
                } else if (msg.what == 0x26) {
                    sendNotification(getBaseActivity());
                }
            } catch (Exception e) {
                LogInfo.e(e.toString());
            }
        }
    };

    private void setListen() {
        try {
            isSign = false;
            isWatchVideo = false;
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseActivity());
            all_sign.setOnClickListener(this);
            all_video.setOnClickListener(this);
            video_go.setOnClickListener(this);
            sign_in.setOnClickListener(this);
            run_go.setOnClickListener(this);
            all_run.setOnClickListener(this);
            all_run.setVisibility(View.VISIBLE);

        } catch (Exception E) {
            LogInfo.e(E.toString());
        }

    }

    private void itemClick(Oiswd oiswd, int position) {
        int partnerId = oiswd.app_sub;
        OfferPresent.showWall(partnerId, getBaseActivity(), ((MainActivity) getBaseActivity()).getOfferwallPlacement());
        WorksUtils.insertData(getBaseActivity(), partnerId, 36, 2, "" + position, "" + 0, "0", 0, "0");
    }

    @Override
    protected void managerArguments() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try {
            LogInfo.e("onResume");
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISFINISHSTEP, true) || !SelfPrefrence.INSTANCE.getString(SelfValue.STEPDAY, "1").equals(ProjectTools.getCurrDateFormat())) {
                run_go.setBackground(getBaseActivity().getDrawable(R.drawable.dra_button));
            } else {
                run_go.setBackground(getBaseActivity().getDrawable(R.drawable.back_home));
            }
            String type = SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1");
            LogInfo.e("--" + type);
            if (type.contains("2")) {
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_GAME, false)) {
                    all_run.setVisibility(View.VISIBLE);
                } else {
                    all_run.setVisibility(View.GONE);
                }
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_WALL, false)) {
                    if (wallTableAdapter != null && wallTableAdapter.getData() != null && wallTableAdapter.getData().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISFINISHSTEP, true) || !SelfPrefrence.INSTANCE.getString(SelfValue.STEPDAY, "1").equals(ProjectTools.getCurrDateFormat())) {
                run_go.setBackground(getBaseActivity().getDrawable(R.drawable.dra_button));
            } else {
                run_go.setBackground(getBaseActivity().getDrawable(R.drawable.back_home));
            }
            String type = SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1");
            if (type.contains("2")) {
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_GAME, false)) {
                    all_run.setVisibility(View.VISIBLE);
                } else {
                    all_run.setVisibility(View.GONE);
                }
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_WALL, false)) {
                    if (wallTableAdapter != null && wallTableAdapter.getData() != null && wallTableAdapter.getData().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    protected OverPresent initModel() {
        return new MyAppPresent(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (ViewTools.isFastDoubleClick(id)) {
            return;
        }
        switch (id) {
            case R.id.all_sign:
            case R.id.sign_in:
                CheckInActivity.startActivity(getBaseActivity());
//                showCheckDialog();
                break;

            case R.id.all_video:
            case R.id.video_in:
                watchVideo(1);
                break;
//            case R.id.all_wall:
//            case R.id.wall_go:
//                showAdd();
//                break;
            case R.id.all_run:
            case R.id.run_go:
                run();
                ProjectTools.upAFF(Constans.daily_walk_click, 0, mFirebaseAnalytics);
                break;
        }
    }

    private void run() {
        WalkActivity.startActivity(getBaseActivity());
    }

    private void showAdd() {
        try {
            List<Oiswd> oiswdList = ((MainActivity) getBaseActivity()).getBrotherInfos();
            if (oiswdList != null && oiswdList.size() > 0) {
                int id = new Random().nextInt(oiswdList.size());
                Oiswd oiswd = oiswdList.get(id);
                int partnerId = oiswd.app_sub;
                OfferPresent.showWall(partnerId, getBaseActivity(), ((MainActivity) getBaseActivity()).getOfferwallPlacement());
                WorksUtils.insertData(getBaseActivity(), partnerId, 11, 2, "0", "" + 0, "0", 0, "0");
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void watchVideo(int type) {//0 签到 1正常
        try {
            WorksUtils.insertData(getBaseActivity(), 0, 10, 2, "0", "" + 0, "0", 0, "0");
            if (type == 0) {
                if (rewardedAd.isReady()) {
                    rewardedAd.showAd(Constans.check_vieo);
                } else {
                    showToast(R.string.no_video);
                    myAppPresent.signPoints(0);
                }
            } else {
                if (rewardedAd.isReady()) {
                    rewardedAd.showAd(Constans.normal_video);
                    ProjectTools.upAFF(Constans.watch_video_ad, 0, mFirebaseAnalytics);

                } else {
                    showToast(R.string.no_video);
                    ProjectTools.upAFF(Constans.watch_video_ad_error, 0, mFirebaseAnalytics);
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }

    }

    @Override
    public void onVideoSured(UserView user) {
        try {
            onReward(user);
            int max = videoConfig.data.limit;
            progress.setMax(max);
            int count = SelfPrefrence.INSTANCE.getInt(SelfValue.VIDEOCOUNT, 0);

            progress.setProgress(count + 1);
            if (count + 1 >= max) {
                SelfPrefrence.INSTANCE.setInt(SelfValue.VIDEOCOUNT, max);
                progress_text.setText(max + "/" + max);
            } else {
                SelfPrefrence.INSTANCE.setInt(SelfValue.VIDEOCOUNT, count + 1);
                progress_text.setText((count + 1) + "/" + max);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onCoins(UserView user) {
        try {
            int userType = user.user_type_page;
            SelfPrefrence.INSTANCE.setString(SelfValue.ABTYPE, "" + userType);
            refreshCount(user);
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    public void onReward(UserView user) {
        try {

            String credits = "" + user.points;
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            float last = Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));
            int lastPoints = (int) last;
            int currentPoints = Integer.valueOf(credits);
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                showAddPoints(currentPoints, lastPoints);
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            ((MainActivity) getBaseActivity()).getmoneyTextView().setText(credits);

        } catch (Exception | Error e) {
            LogInfo.e(e.toString());
        }
    }

    private void refreshCount(UserView user) {
        try {
            String credits = "" + user.points;
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            String max = "" + user.set_points;
            SelfPrefrence.INSTANCE.setString(SelfValue.MAX_COUNT, max);
//            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
            if (!SelfPrefrence.INSTANCE.getBoolean(SelfValue.FIRSTRWARD, true)) {
                ((MainActivity) getBaseActivity()).getmoneyTextView().setText(credits);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onOkSign(UserView user) {
    }

    @Override
    public void onCheckInfo(Kisdi kisdi) {
        mKisdi = kisdi;
        if (mKisdi != null && mKisdi.status == 0) {

        } else {
            check_in.setText(getBaseActivity().getString(R.string.signed));
            check_in.setTextColor(getBaseActivity().getResources().getColor(R.color.sign_color));
            sign_in.setBackground(getBaseActivity().getDrawable(R.drawable.back_home));
        }
    }

    @Override
    public void onError() {
        hideReqProgress();

    }
}