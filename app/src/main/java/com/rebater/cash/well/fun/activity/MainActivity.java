package com.rebater.cash.well.fun.activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.fragment.ExploreFragment;
import com.rebater.cash.well.fun.fragment.MyAppFragment;
import com.rebater.cash.well.fun.fragment.VersionFragment;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.rebater.cash.well.fun.present.OfferPresent;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tapjoy.TJActionRequest;
import com.tapjoy.TJError;
import com.tapjoy.TJPlacement;
import com.tapjoy.TJPlacementListener;
import com.tapjoy.Tapjoy;

import java.util.List;

import butterknife.BindView;

public class MainActivity extends OverActivity implements BottomNavigationBar.OnTabSelectedListener, IModel.FirstView {
    @BindView(R.id.fragment_content)
    FrameLayout mLlContent;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;
    @BindView(R.id.left_top)
    ConstraintLayout left_top;
    @BindView(R.id.right_top)
    LinearLayout right_top;
    @BindView(R.id.points)
    TextView point;
    @BindView(R.id.top_layout)
    ConstraintLayout top_layout;
    ExploreFragment exploreFragment;
    MyAppFragment myAppFragment;
    VersionFragment versionFragment;
    private int lastSelectedPosition;
    LauncherPresent launcherPresent;
    private TJPlacement offerwallPlacement;
    List<Oiswd> tabls;

    List<Okdko> okdkoList;

    List<Woask> historyList;
    public List<Okdko> getContactInfoList() {
        return okdkoList;
    }


    public int getLastSelectedPosition() {
        return lastSelectedPosition;
    }

    public ConstraintLayout getTop_layout() {
        return top_layout;
    }

    public TJPlacement getOfferwallPlacement() {
        return offerwallPlacement;
    }

    public List<Oiswd> getBrotherInfos() {
        return tabls;
    }

    public BottomNavigationBar getmBottomNavigationBar(){
        return  mBottomNavigationBar;
    }


    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        LogInfo.e("MainActivity is coming--");
        initNavigationBar();
        setDefaultFragment();
        initGooglepush();
    }


    @Override
    protected OverPresent initModel() {
        return new LauncherPresent(this);
    }


    public static void startActivity(Context context) {
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    private void setDefaultFragment() {
        exploreFragment =getInfoFragment();
        addFragment(R.id.fragment_content, exploreFragment);
        launcherPresent= (LauncherPresent) getModel();
        launcherPresent.getPatner();
        launcherPresent.getMan();
    }
    public TextView getmoneyTextView() {
        return point;
    }
    private MyAppFragment getSearchFragment() {
        MyAppFragment infoFragment1 = MyAppFragment.newInstance("","");
        return infoFragment1;
    }
    private ExploreFragment getInfoFragment() { //创建首页fragment实例
        ExploreFragment exploreFragment =new ExploreFragment();
        return exploreFragment;
    }
    private void initGooglepush() {
        if(! SelfPrefrence.INSTANCE.getString(SelfValue.PER_TIME, "0").equals(ProjectTools.getCurrDateFormat())) {
            FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
            WorksUtils.insertData(this, 0, 33, 1, "0", "0", "0", 0, "0");
            SelfPrefrence.INSTANCE.setString(SelfValue.PER_TIME, ProjectTools.getCurrDateFormat());
        }
    }
    protected void onResume() {
        super.onResume();
    }
    protected void onPause() {
        super.onPause();
    }

    private void initNavigationBar() {
        LogInfo.e("initBaring");
        lastSelectedPosition=0;
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(R.color.bottom_actation);
        mBottomNavigationBar //初始化底部导航栏，设置每个按钮样式属性
                .addItem(new BottomNavigationItem(R.drawable.play_selected_icon, R.string.info_fragment).setActiveColorResource(R.color.button_yellow).setInactiveIconResource(R.drawable.play_unselected_icon).setInActiveColorResource(R.color.bottom_text))
                .addItem(new BottomNavigationItem(R.drawable.explore_selected_icon, R.string.play_fragment).setActiveColorResource(R.color.button_yellow).setInactiveIconResource(R.drawable.explore_unselected_icon).setInActiveColorResource(R.color.bottom_text))
                .addItem(new BottomNavigationItem(R.drawable.setting_selected_icon, R.string.set_fragment).setActiveColorResource(R.color.button_yellow).setInactiveIconResource(R.drawable.setting_unselected_icon).setInActiveColorResource(R.color.bottom_text))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(this); //设置点击监听器
        LogInfo.e("initBar Over");

        right_top.setOnClickListener(new View.OnClickListener() {//主页面右上角 Invite
            @Override
            public void onClick(View v) {
                if (ViewTools.isFastDoubleClick(R.id.right_top)) {
                    return;
                }
                WorksUtils.insertData(MainActivity.this, 0, 6, 1, "0", "0", "0", 0, "0");
                InviteActivity.startActivity(MainActivity.this, okdkoList);
            }
        });
        left_top.setOnClickListener(new View.OnClickListener() { //左上角积分
            @Override
            public void onClick(View v) {
                try {
                    if (ViewTools.isFastDoubleClick(R.id.left_top)) {
                        return;
                    }
                    int position = mBottomNavigationBar.getCurrentSelectedPosition();
                    if (position != 2) {
                        mBottomNavigationBar.selectTab(2);
                        if (versionFragment == null) {
                            versionFragment = VersionFragment.newInstance(null, null);
                            addFragment(R.id.fragment_content, versionFragment);
                        } else {
                            showFragment(versionFragment);
                        }
                        WorksUtils.insertData(MainActivity.this, 0, 25, 1, "0", "0", "0", 0, "0");  //?
                    }
                } catch (Exception e) {
                    LogInfo.e(e.toString());
                }
            }
        });
    }

    IModel.TapJoyListen tapJoyListen = id -> callShowOffers(id);
    private void callShowOffers(String id) {
        if(offerwallPlacement==null) {
            Tapjoy.setActivity(MainActivity.this);
            offerwallPlacement = Tapjoy.getPlacement(id, new TJPlacementListener() {
                @Override
                public void onRequestSuccess(TJPlacement placement) {

                }

                @Override
                public void onRequestFailure(TJPlacement placement, TJError error) {

                }

                @Override
                public void onContentReady(TJPlacement placement) {

                }

                @Override
                public void onContentShow(TJPlacement placement) {
                }

                @Override
                public void onContentDismiss(TJPlacement placement) {
                }

                @Override
                public void onPurchaseRequest(TJPlacement placement, TJActionRequest request, String productId) {
                }

                @Override
                public void onRewardRequest(TJPlacement placement, TJActionRequest request, String itemId, int quantity) {
                }

                @Override
                public void onClick(TJPlacement placement) {
                }
            });
        }
        offerwallPlacement.requestContent();
    }
    @Override
    public void onError() {

    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        hideFragment();
        switch (position) {
            case 0:   //home
                if (exploreFragment == null) {
                    exploreFragment = new ExploreFragment();
                    addFragment(R.id.fragment_content, exploreFragment);
                } else {
                    showFragment(exploreFragment);
                }
                break;
            case 1:  // wall
                if (myAppFragment == null) {
                    myAppFragment = MyAppFragment.newInstance("","");
                    addFragment(R.id.fragment_content, myAppFragment);
                } else {
                    showFragment(myAppFragment);
                }
                break;
            case 2:
                if (versionFragment == null) {
                    versionFragment = VersionFragment.newInstance(null,null);
                    addFragment(R.id.fragment_content, versionFragment);
                } else {
                    showFragment(versionFragment);
                }
                break;
            case 3:
                // offer
                break;
        }
    }
    private void hideFragment() {
        if (exploreFragment != null) {
            hideFragment(exploreFragment);
        }
        if (myAppFragment != null) {
            hideFragment(myAppFragment);
        }
        if (versionFragment != null) {
            hideFragment(versionFragment);
        }

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPeizhi(UserInfo userInfo) {

    }

    @Override
    public void onPatner(List<Oiswd> brother) {
        tabls =brother;
        OfferPresent.initPartner(tabls,MainActivity.this, tapJoyListen);
    }

    @Override
    public void onMan(List<Okdko> list) {
        okdkoList =list;
    }

    @Override
    public void onLocationError() {

    }

    @Override
    public void onLocation(Piskos piskos) {

    }
}