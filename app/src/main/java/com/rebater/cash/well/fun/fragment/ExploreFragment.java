package com.rebater.cash.well.fun.fragment;


import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Point;
import android.graphics.Rect;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Builder;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.app.hubert.guide.model.RelativeGuide;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.activity.MainActivity;

import com.rebater.cash.well.fun.bean.AdInfo;
import com.rebater.cash.well.fun.bean.BusSendEvent;
import com.rebater.cash.well.fun.bean.Odfi3a;
import com.rebater.cash.well.fun.bean.PlayFirst;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.common.OverApplication;
import com.rebater.cash.well.fun.common.OverFragment;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.TreesAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.ExplorePresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.yy.mobile.rollingtextview.CharOrder;
import com.yy.mobile.rollingtextview.RollingTextView;


import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

public class ExploreFragment extends OverFragment implements IModel.ExploreView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @BindView(R.id.first_recyclerview)
    RecyclerView first_recyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout swipeRefreshLayout;
    ExplorePresent explorePresent;
    TreesAdapter treesAdapter;
    List<Woask> mHistorylist;
    List<AdInfo> mAdInfos;
    long currenttime, errorTime;
    boolean isShow, isFirst, isRequested, isTimerStart, isItemShow, isDetails;
    int tableId, taskTime;
    Dialog alertDialog;
    Timer timer;
    LinearLayoutManager linearLayoutManager;

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_explore;
    } //获取布局文件

    @Override
    public int initView(View view) {
        explorePresent = (ExplorePresent)getModel();  //返回一个FindPresent实例对象
        showInfo();
        return 0;
    }

    private void showInfo() {
        currenttime = System.currentTimeMillis();
        errorTime = 0;
        isShow = true;
        isFirst = true;
        taskTime = 0;
        isRequested = false;
        isTimerStart = false;
        isDetails = false;
        swipeRefreshLayout.autoRefresh();
        explorePresent.getBaseList(); //获取用户领取的任务列表
        explorePresent.getCredit();  //邀请码接口
        swipeRefreshLayout.setOnRefreshListener(refreshLayout -> {
            explorePresent.getTable();
        });
        if (!SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
            int alltime = Integer.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.SUB5, "30"));
            if (timer == null) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            taskTime += 5;
                            LogInfo.e("count->" + taskTime);
                            if (!SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                                explorePresent.getCredit();
                            } else {
                                if (!isRequested) {
                                    explorePresent.getTable();
                                } else {
                                    isTimerStart = false;
                                    if (timer != null) {
                                        timer.cancel();
                                        timer = null;
                                    }
                                }
                            }
                            if (taskTime > alltime) {
                                isTimerStart = false;
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                }
                            }

                        } catch (Exception e) {
//                            LogInfo.e(e.toString());
                        }
                    }
                }, 0l, 5000l);
                isTimerStart = true;
            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            if (OverApplication.getInstance().getActivityManager().last2Activity() != null) {
                String className = OverApplication.getInstance().getActivityManager().last2Activity().getClass().getName();
                LogInfo.e(className);
                if (className.contains("TaskDetailActivity")) {
                    isDetails = true;
                    explorePresent.getBaseList();
                }
                if (className.contains("TaskDetailActivity") || className.contains("MyTabActivity") || className.contains("InputActivity") || className.contains("WalkActivity") || className.contains("CheckInActivity")) {
                    explorePresent.getCredit();
                }

            }
        } catch (Exception e) {
//            LogInfo.e(e.toString());
        }

    }

    @Override
    protected void managerArguments() {

    }

    @Override
    protected OverPresent initModel() {
        return new ExplorePresent(this); //初始化present
    }

    @Override
    public void onCoins(UserView user) {
        refreshCount(user);
    }

    @Override
    public void onWorkList(List<Woask> list) {
        try {
            mHistorylist = list;
            BusSendEvent appEvent = new BusSendEvent(0x13, list);
            EventBus.getDefault().postSticky(appEvent);
            if (isDetails) {
                isDetails = false;
                showFirst(mAdInfos);
            }

        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }


    private void showFirst(List<AdInfo> list) {
        try {
            if (list != null && list.size() > 0) {
                mAdInfos = ProjectTools.getUninstallList(list, getBaseActivity(), tableId);
                if (mAdInfos != null && mAdInfos.size() > 0) {
                    if (treesAdapter == null) {
                        LogInfo.e("--" + mAdInfos.size());
                        first_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                    getVisibleViews(recyclerView, mAdInfos);
                                }
                            }
                        });
                        treesAdapter = new TreesAdapter(getBaseActivity(), R.layout.item_image, mAdInfos);
                        linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        first_recyclerview.setLayoutManager(linearLayoutManager);
                        first_recyclerview.addItemDecoration(new ItemGap(20, 4));
                        first_recyclerview.setAdapter(treesAdapter);
                        treesAdapter.setOnItemClickListener((adapter, view, position) -> {
                            AdInfo qisoo = mAdInfos.get(position);
                            if (qisoo != null) {
                                tenClick(qisoo, position);
                                WorksUtils.insertData(getBaseActivity(), qisoo.id, 1, 1, "" + position, "" + tableId, Constans.home_task_click, 0, qisoo.down_pkg);
//                                    explorePresent.uplog(WorksUtils.getString(qisoo.id, 1, 1, position + "_" + tableId));
                                FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseActivity());
                                ProjectTools.upHomeView(1, Constans.home_task_click, mFirebaseAnalytics, "" + tableId, "" + qisoo.id, "0");
                            }
                        });
                    } else {
                        treesAdapter.getData();
                        treesAdapter.getData().clear();
                        treesAdapter.addData(mAdInfos);
                    }
                    if (treesAdapter.getData() != null && treesAdapter.getData().size() > 0) {//&&SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")
                        handler.sendEmptyMessageDelayed(0x18, 2000);
                        first_recyclerview.setVisibility(View.VISIBLE);

                        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseActivity());
                        ProjectTools.upOpenError(Constans.home_page_view, mFirebaseAnalytics, "" + treesAdapter.getData().size(), ProjectTools.getABtype());
                        WorksUtils.insertData(getBaseActivity(), 0, 4, 1, "0", "0", Constans.home_page_view, 0, "0");

                    }
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    /*
     * 获取当前屏幕上可见的view
     * */
    private void getVisibleViews(RecyclerView reView, List<AdInfo> list) {
        if (reView == null || reView.getVisibility() != View.VISIBLE ||
                !reView.isShown() || !reView.getGlobalVisibleRect(new Rect())) {
            return;
        }
        //保险起见，为了不让统计影响正常业务，这里做下try-catch
        try {
            LogInfo.e("range：");
            int[] range = new int[2];
            RecyclerView.LayoutManager manager = reView.getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                range = ProjectTools.findRangeLinear((LinearLayoutManager) manager);
            }
            if (range == null || range.length < 2) {
                return;
            }
            LogInfo.e("showed-item：" + range[0] + "---" + range[1]);
//            String offerId="";
            for (int i = range[0]; i <= range[1]; i++) {
                if (i < list.size()) {
//                  offerId+=list.get(i).id+",";
                    WorksUtils.insertData(getBaseActivity(), list.get(i).id, 59, 1, "0", "" + tableId, "0", 0, "0");

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void showRewards() {
        try {
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.dialog_init, null);
            // 创建对话框对象
            Dialog dialog = new Dialog(getBaseActivity());
            dialog.setCancelable(false);
            // 设置标题
            dialog.setTitle("Reward");
            // 给对话框填充布局
            dialog.setContentView(view);
            // 获得当前activity所在的window对象
            Window window = dialog.getWindow();
            // 获得代表当前window属性的对象
            WindowManager.LayoutParams params = window.getAttributes();
            Point point = new Point();
            Display display = getBaseActivity().getWindowManager().getDefaultDisplay();
            // 将window的宽高信息保存在point中
            display.getSize(point);
            // 将设置后的大小赋值给window的宽高
            params.width = (int) (point.x * 0.9);
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            // 方式一：设置属性
            window.setAttributes(params);
            Button button = view.findViewById(R.id.receive);
            TextView textView = view.findViewById(R.id.points);
            String mPoints = SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0");
            textView.setText(mPoints);
            button.setOnClickListener(view1 -> {

                try {
                    SelfPrefrence.INSTANCE.setBoolean(SelfValue.FIRSTRWARD, false);
                    LogInfo.e(mPoints);
                    ((MainActivity) getBaseActivity()).getmoneyTextView().setText(mPoints);
                    ProjectTools.uplOGWith3(getBaseActivity(), Constans.home_instructions_reward, 1);
                } catch (Exception e) {
                    LogInfo.e(e.toString());
                }
                dialog.dismiss();
            });

            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                if (msg.what == 0x17) {
                    String credits = SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0");
                    if (credits.contains(".")) {
                        credits = credits.split("\\.")[0];
                    }
                    ((MainActivity) getBaseActivity()).getmoneyTextView().setText(credits);
                }
                if (msg.what == 0x18) {
                    if (!isItemShow) {
                        isItemShow = true;
                        LogInfo.e("onGlobalLayout");
                        if (mAdInfos != null && mAdInfos.size() > 0) {
                            getVisibleViews(first_recyclerview, mAdInfos);
                        }
                    }
                    if (first_recyclerview.getLayoutManager().findViewByPosition(0) != null) {
//                        LogInfo.e("show");
                        Animation enterAnimation = ProjectTools.getAnimotion(1);
                        Animation exitAnimation = ProjectTools.getAnimotion(0);
                        Builder builder = NewbieGuide.with(ExploreFragment.this)
                                .setLabel("explore_view_guide")
                                .setOnGuideChangedListener(new OnGuideChangedListener() {
                                    @Override
                                    public void onShowed(Controller controller) {
//                                        LogInfo.e("showed");

                                    }

                                    @Override
                                    public void onRemoved(Controller controller) {
//                                        LogInfo.e("onRemoved");
                                        if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.FIRSTRWARD, true) && isShow) {
                                            isShow = false;
//                                            ProjectTools.uplOGWith3(getBaseActivity(),FirebaseAnalytics.Event.SELECT_PROMOTION,1);
                                            ProjectTools.uplOGWith3(getBaseActivity(), Constans.home_instructions_03, 1);
                                            SelfPrefrence.INSTANCE.setBoolean(SelfValue.FIRSTRWARD, false);
                                            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                                                showRewards();
                                            } else {
                                                handler.sendEmptyMessage(0x17);
                                            }
                                        }
                                    }
                                })
                                .setOnPageChangedListener(page -> {
                                    LogInfo.e("page--" + page);
                                    String types = ProjectTools.getABtype();
                                    FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(getBaseActivity());
                                    if (page == 1) {
                                        ProjectTools.upnewEventView(types, Constans.home_instructions_01, mFirebaseAnalytics, 1);
                                    }
                                    if (page == 2) {
                                        ProjectTools.upnewEventView(types, Constans.home_instructions_02, mFirebaseAnalytics, 1);
                                    }
                                })
                                .setShowCounts(1)
                                .alwaysShow(false);
                        GuidePage guidePage1 = GuidePage.newInstance()
                                //getChildAt获取的是屏幕中可见的第一个，并不是数据中的position
                                .addHighLight(first_recyclerview.getLayoutManager().findViewByPosition(0), new RelativeGuide(R.layout.guide_self,
                                        Gravity.BOTTOM, 16))
                                .setBackgroundColor(getResources().getColor(R.color.translate))
                                .setEnterAnimation(enterAnimation);
                        GuidePage guidePage2 = GuidePage.newInstance()
                                //getChildAt获取的是屏幕中可见的第一个，并不是数据中的position
                                .setLayoutRes(R.layout.guide_explore)
                                .setBackgroundColor(getResources().getColor(R.color.translate));
                        GuidePage guidePage3 = GuidePage.newInstance()
                                //getChildAt获取的是屏幕中可见的第一个，并不是数据中的position
                                .addHighLight(((MainActivity) getBaseActivity()).getTop_layout(), HighLight.Shape.RECTANGLE, 8,
                                        new RelativeGuide(R.layout.guide_top,
                                                Gravity.BOTTOM, 12))
                                .setBackgroundColor(getResources().getColor(R.color.translate))
//                                .setEnterAnimation(enterAnimation)
                                .setExitAnimation(exitAnimation);
                        if (((MainActivity) getBaseActivity()).getLastSelectedPosition() == 0) {
                            builder.addGuidePage(guidePage1).addGuidePage(guidePage2).addGuidePage(guidePage3).show();
                        }
                    } else {
                        LogInfo.e("noshow");
                    }
                }
            } catch (Exception | Error e) {
                LogInfo.e(e.toString());
            }
        }
    };

    @Override
    public void homeInfo(PlayFirst list) {
        try {
            if (null != list) {
                if (isFirst) {
                    isFirst = false;
                    if (swipeRefreshLayout.isRefreshing()) {
//                        LogInfo.e("coming");
                        swipeRefreshLayout.finishRefresh(1000);
                        first_recyclerview.setVisibility(View.INVISIBLE);
                    }
                } else {
                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.finishRefresh();
                    }
                }
                tableId = list.tpl_id;
                showFirst(list.data);
            } else {
                if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.FIRSTRWARD, true)) {
                    if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                        showRewards();
                    }
                }
                WorksUtils.insertData(getBaseActivity(), 0, 50, 1, "0", "" + tableId, "0", 0, "0");
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.finishRefresh();
                }
            }
        } catch (Exception e) {
//            LogInfo.e(e.toString());
            tableId = 0;
            showFirst(list.data);
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.finishRefresh();
            }
        }
    }

    @Override
    public void onlistError() {

    }


    public void tenClick(AdInfo adInfo, int position) {
        try {
            int type = adInfo.ad_type;
            if (type == 2) {//GP
                Odfi3a odfi3a = ProjectTools.getDtails(adInfo);
                if (odfi3a != null) {
//                    TaskDetailActivity.startActivity(getBaseActivity(), odfi3a, 1, tableId);
                }
            } else {//H5链接
                showUrlDialog(adInfo, position);
            }
        } catch (Exception e) {
//            LogInfo.e(e.toString());
        }
    }

    private void showUrlDialog(AdInfo adInfo, int position) {
        try {
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_TOAST, false)) {
                View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.dialog_push, null);
                final Dialog dialog = new AlertDialog.Builder(getBaseActivity())
                        .setView(view)
                        .setCancelable(true)
                        .create();
                Button intall_button = view.findViewById(R.id.sure_button);
                intall_button.setText(R.string.start);
                ImageView icon = view.findViewById(R.id.icon_img);
                TextView textView = view.findViewById(R.id.dialog_type);
                textView.setText("");
                ImageView close = view.findViewById(R.id.close_img);
                TextView credits = view.findViewById(R.id.text_one);
                credits.setText("+ " + adInfo.award + " " + getBaseActivity().getString(R.string.points));
                TextView title = view.findViewById(R.id.text_two);
                title.setText(adInfo.title);
                TextView desc = view.findViewById(R.id.text_three);
                desc.setText(adInfo.desc);
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.default__icon)
                        .priority(Priority.HIGH);
                Glide.with(getBaseActivity()).load(adInfo.image_url)
                        .apply(options).transition(withCrossFade())
                        .into(icon);
                close.setOnClickListener(v -> dialog.dismiss());
                intall_button.setOnClickListener(v -> {
                    jugeType(adInfo);
                    WorksUtils.insertData(getBaseActivity(), adInfo.id, 3, 1, "" + position, "" + tableId, "0", 0, "0");
                    dialog.dismiss();
                });
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            } else {
                jugeType(adInfo);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void jugeType(AdInfo adInfo) {
        try {
            int adtype = adInfo.ad_type;
            if (adtype == 1) {
//                OfferPresent.showWall(adInfo.app_sub, getBaseActivity(), ((MainActivity) getBaseActivity()).getOfferwallPlacement());
            } else if (adtype == 3) {
                int h5type = adInfo.h5_type;
                String url = adInfo.h5_url;
                if (!TextUtils.isEmpty(url)) {
                    if (h5type == 0) {
                        ProjectTools.startIntent(getBaseActivity(), url);
                    } else if (h5type == 1) {
//                        MyWebActivity.startActivity(getBaseActivity(), url);
                    } else if (h5type == 2) {
                        String pkg = adInfo.h5_pkg;
                        if (!TextUtils.isEmpty(pkg)) {
                            ProjectTools.startIntentWithPackage(getBaseActivity(), url, pkg);
                        }
                    }
                    WorksUtils.getWorkReward(0, explorePresent, null, adInfo.id, 0);
                }
            } else if (adtype == 4) {
                int c5type = adInfo.contact_type;
                String url = adInfo.contact_url;
                String pkg = adInfo.contact_pkg;
                ProjectTools.HandleContact(c5type, url, pkg, getBaseActivity(), adInfo.title);
                WorksUtils.getWorkReward(0, explorePresent, null, adInfo.id, 0);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }


    @Override
    public void onHrefReward(UserView user) {
        try {
            String credits = "" + user.points;
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            float last = Float.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0"));
            int lastPoints = (int) last;
            int currentPoints = Integer.valueOf(credits);
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            ((MainActivity) getBaseActivity()).getmoneyTextView().setText(credits);
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                showAddPoints(currentPoints, lastPoints);
            }
        } catch (Exception | Error e) {
            LogInfo.e(e.toString());
        }
    }

    private void refreshCount(UserView user) { //更新积分
        try {
            String credits = "" + user.points; //用户积分
            String code = user.my_recommended_code; //用户邀请码
            String max = "" + user.set_points; //多少分换一美金
            int type = user.user_type_page; //AB面
            SelfPrefrence.INSTANCE.setString(SelfValue.ABTYPE, "" + type); //设置AB面
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            if (max.contains(".")) {
                max = max.split("\\.")[0];
            }
            if (TextUtils.isEmpty(code)) { //设置用户邀请码
                SelfPrefrence.INSTANCE.setString(SelfValue.USER_CODE, "");
            } else {
                SelfPrefrence.INSTANCE.setString(SelfValue.USER_CODE, code);
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits); //设置用户积分

            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.FIRSTRWARD, true) && SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                //当用户第一次打开app，且是B面用户
                ((MainActivity) getBaseActivity()).getmoneyTextView().setText("0");
            } else {
                ((MainActivity) getBaseActivity()).getmoneyTextView().setText(credits);
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.MAX_COUNT, max); //? MAX_COUNT ?
            if (type == 2 && isTimerStart && !isRequested) {
                LogInfo.e("req");
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onError() {
        hideReqProgress();
    }

}