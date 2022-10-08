package com.rebater.cash.well.fun.activity;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
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
import android.view.animation.Animation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.AdvertiBean;
import com.rebater.cash.well.fun.bean.Odfi3a;
import com.rebater.cash.well.fun.bean.Qiest;
import com.rebater.cash.well.fun.bean.ReOffer;
import com.rebater.cash.well.fun.bean.ReceiverInfo;
import com.rebater.cash.well.fun.bean.Wibumer;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.green.AdvertiBeanDao;
import com.rebater.cash.well.fun.green.GreenDaoUtils;
import com.rebater.cash.well.fun.match.ProgressAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.LovePresent;
import com.rebater.cash.well.fun.util.Glidetap;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.OpenTool;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WebSkip;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Builder;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.RelativeGuide;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;
import org.greenrobot.greendao.query.QueryBuilder;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class TaskDetailActivity extends OverActivity implements IModel.WorkView {
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.ask)
    ImageView ask;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.big_pic)
    ImageView big_pic;
    @BindView(R.id.icon)
    ImageView img;
    @BindView(R.id.title_ad)
    TextView apptitle;
    @BindView(R.id.desc_ad)
    TextView requirements;
    @BindView(R.id.points)
    TextView right_point;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.step_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.per_top)
    LinearLayout per_top;
    Odfi3a firstInfo;
    List<Qiest> list;
    ProgressAdapter progressAdapter;
    LovePresent lovePresent;
    int type, tableId;
    int reward_type;
    boolean isClicked, iscando, isFirst, isRate, isStart;
    Timer timer;
    Dialog alertDialog;
    long lastTime, firstCountTime;
    public static int PERMISSION_RECORD = 1;
    private FirebaseAnalytics mFirebaseAnalytics;
    private WebView webView;
    ReceiverInfo receiverInfo;

    public static void startActivity(Context context, Odfi3a info, int type, int tableid) {
        context.startActivity(intentof(context, info, type, tableid));
    }

    private static Intent intentof(Context context, Odfi3a info, int type, int tableid) {
        Intent intent = new Intent(context, TaskDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("first", info);
        bundle.putInt("type", type);
        bundle.putInt("table", tableid);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    public void onError() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        LogInfo.e("onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideReqProgress();
        LogInfo.e("onDestroy");
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                LogInfo.e("coming--" + msg.what);
                if (msg.what == 0x13) {
                    hideReqProgress();
                } else if (msg.what == 0x07) {
                    hideReqProgress();
                    if (!isStart && !isRate && firstInfo.step1_status != 1 && ProjectTools.isAvilible(TaskDetailActivity.this, firstInfo.down_pkg)) {
                        showPermission(TaskDetailActivity.this, 1);
                    }
                } else if (msg.what == 0x09) {
                    if (recyclerView.getLayoutManager().findViewByPosition(0) != null) {
//                    if (recyclerView.geC != null) {
                        LogInfo.e("show");
                        Animation enterAnimation = ProjectTools.getAnimotion(1);
                        Animation exitAnimation = ProjectTools.getAnimotion(0);
                        Builder builder = NewbieGuide.with(TaskDetailActivity.this)
                                .setLabel("doing_view_guide")
                                .setShowCounts(1);
//                                .alwaysShow(true);

                        GuidePage guidePage1 = GuidePage.newInstance()
                                //getChildAt获取的是屏幕中可见的第一个，并不是数据中的position
                                .addHighLight(recyclerView.getLayoutManager().findViewByPosition(0), new RelativeGuide(R.layout.guide_progress,
                                        Gravity.TOP, 16))
                                .setBackgroundColor(getResources().getColor(R.color.translate))
                                .setEnterAnimation(enterAnimation);
//                                .setEnterAnimation(enterAnimation)   ;
                        GuidePage guidePage3 = GuidePage.newInstance()
                                //getChildAt获取的是屏幕中可见的第一个，并不是数据中的position
                                .addHighLight(submit, HighLight.Shape.RECTANGLE, 8,
                                        new RelativeGuide(R.layout.guide_first,
                                                Gravity.TOP, 0))
                                .setBackgroundColor(getResources().getColor(R.color.translate))
//                                .setEnterAnimation(enterAnimation)
                                .setExitAnimation(exitAnimation);
                        builder.addGuidePage(guidePage1).addGuidePage(guidePage3).show();

                    } else {
                        LogInfo.e("noshow");

                    }
                }
            } catch (Exception | Error e) {
                LogInfo.e(e.toString());
            }
        }
    };

    private void checkPermission() {
        try {
            boolean isIns = ProjectTools.hasPermissionToReadNetworkStats(TaskDetailActivity.this);
            if (isRate && !isIns) {
                per_top.setVisibility(View.VISIBLE);
            } else {
                per_top.setVisibility(View.GONE);
            }
            if (isRate && isIns) {
                boolean isGranted = SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISpermission, false);
                if (!isGranted) {
                    ProjectTools.upHomeView(3, Constans.permisiionIn, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "0");
//                    lovePresent.uplog(WorksUtils.getString(firstInfo.id, 61, 8, "0_" + tableId));
                    WorksUtils.insertData(this, firstInfo.id, 61, 8, "0", "" + tableId, Constans.permisiionIn, firstInfo.step, firstInfo.down_pkg);
                    SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISpermission, true);
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void workOne() {
        try {
            int times = firstInfo.step1_time * 60;
            long playedetime = 0;
            if (!isRate) {
                playedetime = firstCountTime;
                LogInfo.e("no mission" + playedetime);
            } else {
                playedetime = ProjectTools.getRunTime(ProjectTools.getEventList(this, firstInfo.down_pkg, lastTime * 1000));
            }
            if (progressAdapter != null && progressAdapter.getData().get(0) != null) {
                progressAdapter.getData().get(0).doingtime = (int) playedetime;
                progressAdapter.getData().get(0).lastTimes = lastTime;
                progressAdapter.notifyItemChanged(0);
            }
            LogInfo.e("time--" + playedetime + "--" + times);
            if (playedetime >= times) {
                if (!isRate) {
                    timer.cancel();
                    timer = null;
                }
                if (reward_type == 1) {
                    WorksUtils.getWorkReward(1, null, lovePresent, firstInfo.id, 1);
                    ProjectTools.upHomeView(3, Constans.task_installed, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
                }
                finishInstalled(0);
            } else {
                finishInstalled(1);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void finishInstalled(int type) {
        try {
            AdvertiBeanDao advertiBeanDao = GreenDaoUtils.getSingleTon(this).getDaoSession().getAdvertiBeanDao();
            if (advertiBeanDao != null) {
                QueryBuilder qb = advertiBeanDao.queryBuilder();
                qb.where(AdvertiBeanDao.Properties.PackageName.eq(firstInfo.down_pkg), AdvertiBeanDao.Properties.AdId.eq(firstInfo.id)).build();
                List<AdvertiBean> userList = qb.list();
                if (userList != null && userList.size() > 0) {
                    LogInfo.e("--size--" + userList.size());
                    AdvertiBean advertiBean = ProjectTools.getAdinfo(userList, firstInfo.down_pkg);//
                    if (advertiBean != null) {
                        boolean isup = advertiBean.getIsUp();
                        LogInfo.e("insert" + isup);
                        if (!isup) {//有了但没传
                            LogInfo.e("no--");
                            ProjectTools.upHomeView(3, FirebaseAnalytics.Event.ADD_TO_WISHLIST, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
                            ProjectTools.upHomeView(3, Constans.task_install, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
                            WorksUtils.insertData(this, firstInfo.id, 65, 8, "0", "" + tableId, Constans.task_install, firstInfo.step, firstInfo.down_pkg);
                            advertiBean.setIsUp(true);
                            advertiBeanDao.update(advertiBean);
//                                        showToast("up installed");
                        } else {
                            LogInfo.e("yes-" + advertiBean.getPackageName() + advertiBean.getAdId() + advertiBean.getIsUp() + advertiBean.getCreateTime());
                        }
                    }
                } else {
                    AdvertiBean advertiBean = new AdvertiBean();
                    advertiBean.setAdId(firstInfo.id);
                    advertiBean.setCreateTime(System.currentTimeMillis());
                    advertiBean.setChangeTime(System.currentTimeMillis());
                    advertiBean.setPackageName(firstInfo.down_pkg);
                    advertiBean.setIsUp(true);
                    advertiBeanDao.insert(advertiBean);
                    ProjectTools.upHomeView(3, Constans.task_install, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
                    ProjectTools.upHomeView(3, FirebaseAnalytics.Event.ADD_TO_WISHLIST, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
                    LogInfo.e("insert");
                    WorksUtils.insertData(this, firstInfo.id, 65, 8, "0", "" + tableId, Constans.task_install, firstInfo.step, firstInfo.down_pkg);
//                                    showToast("up installed");
                }
            } else {
                LogInfo.e("fail");
                WorksUtils.insertData(this, firstInfo.id, 58, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            }
            if (type == 1) {
                WorksUtils.insertData(this, firstInfo.id, 48, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void workOther() {
        try {
            int step = firstInfo.step;
            Qiest steolass = list.get(step);
            int reward_types = steolass.reward_type;
            LogInfo.e("reward-" + reward_types);
            int time = steolass.time * 60;
            int id = firstInfo.id;
            int day = steolass.step_day;
            long stepTime = 0;
            if (!isRate) {
                stepTime = firstCountTime;
            } else {
                stepTime = ProjectTools.getRunTime(ProjectTools.getEventList(this, firstInfo.down_pkg, lastTime * 1000));
                LogInfo.e("time--" + stepTime);
            }
            if (progressAdapter != null && progressAdapter.getData().get(step) != null) {
                LogInfo.e("time--" + stepTime + "--" + lastTime + "--" + step + "--nn-" + time + "---" + day);
                progressAdapter.getData().get(step).doingtime = (int) stepTime;
                progressAdapter.getData().get(step).lastTimes = lastTime;
                progressAdapter.setmSteps(step);
                progressAdapter.notifyDataSetChanged();
            }
            long gapTime = System.currentTimeMillis() / 1000 - lastTime;
            LogInfo.e("time--" + gapTime + "---" + day * 24 * 60 * 60);
            if (gapTime >= day * 24 * 60 * 60) {
                if (stepTime >= time) {
                    if (!isRate) {
                        timer.cancel();
                        timer = null;
                    }
                    if (reward_types == 1) {
                        LogInfo.e("time--" + stepTime + "--" + step);
                        WorksUtils.getWorkReward(1, null, lovePresent, firstInfo.id, step + 1);
                        WorksUtils.insertData(this, firstInfo.id, 35, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
                    }
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            checkPermission();
            if (firstInfo != null) {
                if (!isFirst) {
                    showReqProgress(true, getString(R.string.refresh));
                    handler.sendEmptyMessageDelayed(0X07, 2000);
                }
                LogInfo.e(firstInfo.down_pkg + firstInfo.step1_status);
                if (firstInfo != null && ProjectTools.isAvilible(this, firstInfo.down_pkg)) {
                    if (firstInfo.step1_status == 0) {
                        workOne();
                    } else {
                        workOther();
                    }
                }
            }
            isFirst = false;
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_task_detail);
    }

    public void showTips(AppCompatActivity context) {//1表示handler进来 2表示开始进来
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_detail, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        Button yes_button = view.findViewById(R.id.yes_button);
        yes_button.setOnClickListener(view1 -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();

    }

    private void initData() {
        try {
            isClicked = false;
            iscando = false;
            isFirst = true;
            firstCountTime = 0;
            isRate = SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISRATE, false);
            per_top.setOnClickListener(v -> {
                if (ViewTools.isFastDoubleClick(R.id.per_top)) {
                    return;
                }
                ProjectTools.gotoSetting(TaskDetailActivity.this);
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 56, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            });
            ask.setVisibility(View.VISIBLE);
            ask.setOnClickListener(v -> {
                if (ViewTools.isFastDoubleClick(R.id.ask)) {
                    return;
                }
                showTips(TaskDetailActivity.this);
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 57, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            });
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            Intent intent = getIntent();
            if (timer == null) {
                timer = new Timer();
            }
            if (lovePresent == null) {
                lovePresent = (LovePresent) getModel();
            }
            backgo.setOnClickListener(v -> {
                if (ViewTools.isFastDoubleClick(R.id.backgo)) {
                    return;
                }
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 62, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
                finish();
            });
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    firstInfo = bundle.getParcelable("first");
                    type = bundle.getInt("type");
                    tableId = bundle.getInt("table");
                    if (firstInfo != null) {
                        String url = firstInfo.image_url;
                        String maxUrl = firstInfo.image_max;
                        int id = firstInfo.id;
                        reward_type = firstInfo.reward_type;
                        int down_type = firstInfo.down_type;
                        if (type != 1) {
                            lastTime = Long.valueOf(firstInfo.uptime);
                        } else {
                            lastTime = 0;
                        }
                        LogInfo.e("--" + id + reward_type);
                        list = ProjectTools.getStep(this, firstInfo, lastTime);
                        initAdapter(list);
                        if (!TextUtils.isEmpty(url) && !url.equals("null")) {
                            RequestOptions options = new RequestOptions()
                                    .error(R.drawable.default__icon)
                                    .transform(new Glidetap(24, 1));
                            Glide.with(this).load(url)
                                    .apply(options).transition(withCrossFade())
                                    .into(img);
                        }
                        RequestOptions option = new RequestOptions()
                                .error(R.drawable.game_icon)
                                .priority(Priority.HIGH);
                        Glide.with(this).load(maxUrl)
                                .apply(option).transition(withCrossFade())
                                .transform(new Glidetap(32, 2))
                                .into(big_pic);
                        title.setText(R.string.details);
                        apptitle.setText(firstInfo.title);
                        right_point.setText("+" + firstInfo.award);
                        requirements.setText(firstInfo.desc);
                        submit.setOnClickListener(v -> {
                                    if (ViewTools.isFastDoubleClick(R.id.submit)) {
                                        return;
                                    }
                                    if (isRate && !ProjectTools.hasPermissionToReadNetworkStats(TaskDetailActivity.this)) {
                                        showPermission(TaskDetailActivity.this, 2);
                                    } else {
                                        if (isClicked) {
                                            showToast(getString(R.string.task_ok));
                                            return;
                                        }
                                        jugeWork(down_type);
                                    }
                                }
                        );
                        handler.sendEmptyMessageDelayed(0x09, 100);
                    } else {
                        pushAP();
                    }
                } else {
                    pushAP();
                }
            } else {
                pushAP();
            }
            ProjectTools.upHomeView(3, Constans.task_page_view, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
            LogInfo.e("page_view");
            WorksUtils.insertData(this, firstInfo.id, 34, 8, "0", "" + tableId, Constans.task_page_view, firstInfo.step, firstInfo.down_pkg);

        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    public void showPermission(AppCompatActivity context, int type) {//1表示handler进来 2表示开始进来
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_ask, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        Button yes_button = view.findViewById(R.id.yes_button);
        Button no_button = view.findViewById(R.id.no_button);
        TextView content = view.findViewById(R.id.content);
        ImageView imageView = view.findViewById(R.id.img);
        if (type == 1) {
            imageView.setBackground(getDrawable(R.drawable.lock_icon));
            content.setText(getString(R.string.start_work));
            yes_button.setText(getString(R.string.start));
        } else {
            content.setText(getString(R.string.usage));
        }
        yes_button.setOnClickListener(view1 -> {
            if (type == 1) {
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 54, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
                ProjectTools.startIntentWithPKG(TaskDetailActivity.this, firstInfo.down_pkg);
                isStart = true;
                if (timer == null) {
                    timer = new Timer();
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        firstCountTime = firstCountTime + 3;
                    }
                }, 0, 3000);
            } else {
                ProjectTools.gotoSetting(TaskDetailActivity.this);
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 53, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            }
            dialog.dismiss();
        });
        no_button.setOnClickListener(view12 -> {
            if (type == 1) {
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 55, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            } else {
                WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 52, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
            }
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog.show();

    }

    public void jugeWork(int down_type) {//三方0 自己1  浏览器0 GP1
        try {
            String pkg = firstInfo.down_pkg;
            String url1 = firstInfo.tracking_link;
            int id = firstInfo.id;
            LogInfo.e("--" + id + firstInfo.step + url1);
            int step = firstInfo.step;
            if (step == 99) {
                if (ProjectTools.isAvilible(this, firstInfo.down_pkg) && firstInfo.step1_status == 0) {
                    ProjectTools.startIntentWithPKG(TaskDetailActivity.this, pkg);
                    showToast(R.string.start_task);
                    WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 49, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
                    if (!isRate && reward_type == 1) {
                        Qiest steolass = list.get(0);
                        int play_time = steolass.time * 60;
                        if (!isStart) {
                            isStart = true;
                            if (timer == null) {
                                timer = new Timer();
                            }
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    firstCountTime = firstCountTime + 3;

                                }
                            }, 0, 3 * 1000);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(url1)) {
                        lastTime = System.currentTimeMillis() / 1000;
                        if (type == 1) {
                            WorksUtils.getWork(lovePresent, id, 1, tableId);
                        }
                        if (down_type == 0) {
                            OpenTool.a(TaskDetailActivity.this, url1, id, step, pkg, "" + tableId);
                        } else {
                            if (url1.toLowerCase().startsWith("market://")) {
                                OpenTool.gotoGoogle(TaskDetailActivity.this, url1, id, step, pkg, 1, "" + tableId);
                            } else if (url1.startsWith("https://play.google.com/store/apps/")) {
                                OpenTool.gotoGoogle(TaskDetailActivity.this, url1, id, step, pkg, 0, "" + tableId);
                            } else if (url1.startsWith("http://") || url1.startsWith("https://")) {
                                showReqProgress(true, getString(R.string.load));
                                Wibumer wibumer = new Wibumer();
                                wibumer.lovePresent = lovePresent;
                                wibumer.id = id;
                                wibumer.pkg = pkg;
                                wibumer.position = step + "_" + tableId;
                                WebSkip.getInstance(TaskDetailActivity.this).initWebView(url1, handler, wibumer);
                            }
                        }
                    } else {
                        pushAP();
                    }
                }

            } else {
                if (list != null && list.size() > 0) {
                    Qiest steolass = list.get(step);
                    int reward_types = steolass.reward_type;
                    int time = steolass.time * 60;
                    int day = steolass.step_day;
                    if (System.currentTimeMillis() / 1000 - lastTime >= day * 24 * 60 * 60) {
                        submit.setBackground(getDrawable(R.drawable.dra_button));
                        if (!TextUtils.isEmpty(pkg) && ProjectTools.isAvilible(TaskDetailActivity.this, pkg)) {
                            ProjectTools.startIntentWithPKG(TaskDetailActivity.this, pkg);
                            showToast(R.string.start_task);
                            WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 15, 8, "0", "" + tableId, "0", firstInfo.step, firstInfo.down_pkg);
                            if (!isRate && reward_types == 1) {
                                if (timer == null) {
                                    timer = new Timer();
                                }
                                timer.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        LogInfo.e("run+" + firstInfo);
                                        firstCountTime = firstCountTime + 3;
                                    }
                                }, 0, 3000);
                            }
                        } else {
                            ProjectTools.startIntent(TaskDetailActivity.this, url1);
                        }
                    } else {
                        showToast(R.string.isnotime);
                        submit.setBackground(getDrawable(R.drawable.back_home));
                        iscando = false;
                    }
                } else {
                    showToast(R.string.receive_fail);
                }
            }
            ProjectTools.upHomeView(3, Constans.task_start_btn_click, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + firstInfo.step);
            WorksUtils.insertData(TaskDetailActivity.this, firstInfo.id, 4, 8, "0", "" + tableId, Constans.task_start_btn_click, firstInfo.step, firstInfo.down_pkg);
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void pushAP() {
        showToast(R.string.receive_fail);
        finish();
    }

    private void initAdapter(List<Qiest> qiests) {
        if (qiests != null && qiests.size() > 0) {
            if (progressAdapter == null) {
                progressAdapter = new ProgressAdapter(this, R.layout.item_foot, qiests, firstInfo.step);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.addItemDecoration(new ItemGap(20, 4));
                recyclerView.setAdapter(progressAdapter);
            } else {
                progressAdapter.getData();
                progressAdapter.getData().clear();
                progressAdapter.addData(qiests);
            }
        }

    }

    @Override
    protected void initView() {
        initData();
    }

    @Override
    protected OverPresent initModel() {
        return new LovePresent(this);
    }


    @Override
    public void onFinish(ReceiverInfo info) {
        try {
            receiverInfo = info;
            if (receiverInfo.data.uptime > 0) {
                lastTime = receiverInfo.data.uptime;
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void showAddPoints(int currentPoints, int beforePoints) {
        if (alertDialog == null) {
            alertDialog = new Dialog(TaskDetailActivity.this);
            alertDialog.setCancelable(false);
        } else {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            View view = LayoutInflater.from(TaskDetailActivity.this).inflate(R.layout.layout_points, null);
            alertDialog.setContentView(view);
            window.setGravity(Gravity.TOP);
            window.setWindowAnimations(R.style.DialogBottom);
//            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //设置属性
            WindowManager.LayoutParams params = window.getAttributes();
            Point point = new Point();
            Display display = TaskDetailActivity.this.getWindowManager().getDefaultDisplay();
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

    public void onReward(ReOffer user) {
        try {
            String credits = "" + user.points;
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            float last = Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));
            int lastPoints = (int) last;
            int currentPoints = Integer.valueOf(credits);
//            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                showAddPoints(currentPoints, lastPoints);
            }
        } catch (Exception | Error e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onCash(ReOffer jisd3s) {
        try {
            onReward(jisd3s);
            int step = firstInfo.step;
            firstCountTime = 0;
            isStart = false;
            LogInfo.e("step" + step);
            lastTime = System.currentTimeMillis() / 1000;
            int size = progressAdapter.getItemCount();
            ProjectTools.upHomeView(2, Constans.task_step_finish, mFirebaseAnalytics, "" + tableId, "" + firstInfo.id, "" + step);

//            ProjectTools.upAFF(TaskDetailActivity.this, AFInAppEventType.LOCATION_CHANGED,0,mFirebaseAnalytics);
            if (size == step + 1) {
                isClicked = true;
                iscando = true;
                submit.setBackground(getDrawable(R.drawable.back_home));
                showToast(getString(R.string.task_ok));
//                ProjectTools.upAFF(TaskDetailActivity.this, AFInAppEventType.LEVEL_ACHIEVED,0,mFirebaseAnalytics);
                progressAdapter.getData().get(step).status = 1;
                progressAdapter.notifyDataSetChanged();
                return;
            }

            if (step == 99) {
                firstInfo.step1_status = 1;
                progressAdapter.getData().get(0).status = 1;
                showToast(getString(R.string.task_ok) + getString(R.string.go_step) + "1");
                firstInfo.step = 1;
                progressAdapter.setmSteps(1);
                if (size >= 2) {
                    progressAdapter.getData().get(1).lastTimes = lastTime;
                    progressAdapter.getData().get(1).doingtime = 0;
                }
                progressAdapter.notifyDataSetChanged();
            } else {
                LogInfo.e("step" + step);
                progressAdapter.getData().get(step).status = 1;
//                progressAdapter.notifyItemChanged(step);
                progressAdapter.setmSteps(step + 1);
                if (size > step) {
                    progressAdapter.getData().get(step + 1).lastTimes = lastTime;
                    progressAdapter.getData().get(step + 1).doingtime = 0;
                }
                progressAdapter.notifyDataSetChanged();
                iscando = true;
                firstInfo.step = step + 1;

                if (size != step + 1) {
                    showToast(getString(R.string.task_ok) + getString(R.string.go_step) + (step + 1));
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

}