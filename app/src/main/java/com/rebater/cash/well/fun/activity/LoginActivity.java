package com.rebater.cash.well.fun.activity;

import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.util.DialogTools;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LoginActivity extends OverActivity implements IModel.FirstView {
    LauncherPresent launcherPresent;
    Piskos user;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.privacy)
    TextView privacy;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.indicator)
    ImageView indicator;
    @BindView(R.id.start)
    Button start;
    boolean isCache, isAsk;
    @BindView(R.id.continues)
    TextView continues;

    public static void gotoLogin(LaunchActivity launchActivity) {
        launchActivity.startActivity(new Intent(launchActivity, LoginActivity.class));
    }

    @Override
    protected OverPresent initModel() {
        return new LauncherPresent(this);
    }

    public void useBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.img1_icon);
        list.add(R.drawable.img2_icon);
        list.add(R.drawable.img3_icon);
//        int[]imgs={R.drawable.img1,R.drawable.img2,R.drawable.img3};
        //--------------------------????????????-------------------------------
        banner.addBannerLifecycleObserver(this)//???????????????????????????
                .setIndicator(new CircleIndicator(this), false);

        //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        banner.setAdapter(new BannerImageAdapter<Integer>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                //????????????????????????
                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        });
        banner.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                LogInfo.e("position--"+position);

            }

            @Override
            public void onPageSelected(int position) {
//                LogInfo.e("position--" + position);
                if (position == 0) {
                    content.setText(getString(R.string.img1));
                    indicator.setBackground(getDrawable(R.drawable.pages1_icon));
                } else if (position == 1) {
                    content.setText(getString(R.string.img2));
                    indicator.setBackground(getDrawable(R.drawable.pages2_icon));

                } else if (position == 2) {
                    content.setText(getString(R.string.img3));
                    indicator.setBackground(getDrawable(R.drawable.pages3_icon));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                LogInfo.e("position--" + state);
            }
        });
        //???????????????????????????????????????????????????demo
    }

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        useBanner();
        isCache = false;
        isAsk = true;
        start.setOnClickListener(view -> {
            if (!ViewTools.isFastDoubleClick(R.id.start)) {
                if (checkbox.isChecked()) { //????????????????????????
                    if (!SelfPrefrence.INSTANCE.getString(SelfValue.USER_ID, "0").equals("0")) {
                        MainActivity.startActivity(LoginActivity.this); //???????????????
                        finish(); //???????????????
                    } else {
                        isCache = true;
                        showReqProgress(true, getString(R.string.login));
                        if (!isAsk) {
                            if (!SelfPrefrence.INSTANCE.getString(SelfValue.USER_ID, "0").equals("0")) {
                                MainActivity.startActivity(LoginActivity.this);
                                finish();
                            } else {
                                WorksUtils.sendInfo(LoginActivity.this, "", launcherPresent, null, 0, user);
                            }
                        }
                    }
                    String type = ProjectTools.getABtype();
                    ProjectTools.upnewEventView(type, Constans.user_login_agree_policy, FirebaseAnalytics.getInstance(LoginActivity.this), 2);
                    WorksUtils.insertData(LoginActivity.this, 0, 66, 15, "0", "0", Constans.user_login_agree_policy, 0, "0");

                } else {
                    showToast(R.string.goon);
                }
            }
        });
        checkbox.setChecked(true);
        String str = getString(R.string.agree);//Agree to the Privacy Policy
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        /*SpannableStringBuilder???setSpan()????????????????????????????????????????????????????????????????????????Span??????
        ????????????String????????????????????????????????????????????????String??????????????????????????????????????????????????????????????????????????????????????????????????????
        ??????????????????append()?????????????????????String*/

        ssb.append(str);
        int start = str.indexOf("U");//????????????????????????
        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) { //??????????????????
                ProjectTools.startIntent(LoginActivity.this, Constans.urlPrivacy);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.black));       //??????????????????
                // ???????????????
                ds.setUnderlineText(true);
            }
        }, 0, 18, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        ssb.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ProjectTools.startIntent(LoginActivity.this, Constans.urlTerms);
//                Intent intent = new Intent(LoginActivity.this, WebUserActivity.class);
//                intent.putExtra("url", Constans.urlPrivacy);
//                LoginActivity.this.startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.black));       //??????????????????
                // ???????????????
                ds.setUnderlineText(true);
            }
        }, 23, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        continues.setMovementMethod(LinkMovementMethod.getInstance());
        continues.setText(ssb);
        launcherPresent = (LauncherPresent) getModel();
        launcherPresent.getCation(Constans.urlLocation);
    }

    @Override
    public void onError() {
        isAsk = false;
        if (isCache) {
            hideReqProgress();
        }
    }

    @Override
    public void onPeizhi(UserInfo userInfo) {
        isAsk = false;
        if (isCache) {
            hideReqProgress();
        }
        if (userInfo != null) {
            ProjectTools.setSp(userInfo, FirebaseAnalytics.getInstance(LoginActivity.this), LoginActivity.this);
            if (isCache) {
                MainActivity.startActivity(LoginActivity.this);
                finish();
            }
        } else {
            DialogTools.showPushDialog(LoginActivity.this, 1);
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
        WorksUtils.sendInfo(LoginActivity.this, "", launcherPresent, null, 0, user);

    }

    @Override
    public void onLocation(Piskos piskos) {
        user = piskos;
        ProjectTools.saveInfo(piskos, LoginActivity.this, launcherPresent, 1, null);
    }
}