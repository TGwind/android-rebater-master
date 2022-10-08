package com.rebater.cash.well.fun.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.activity.ContactActivity;
import com.rebater.cash.well.fun.activity.PointsDetailActivity;
import com.rebater.cash.well.fun.activity.SuggestionActivity;
import com.rebater.cash.well.fun.activity.InvitationCodeActivity;
import com.rebater.cash.well.fun.activity.MainActivity;
import com.rebater.cash.well.fun.activity.PayRecordsActivity;
import com.rebater.cash.well.fun.bean.Qisks;
import com.rebater.cash.well.fun.bean.Records;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.bean.WithdrawalPanel;
import com.rebater.cash.well.fun.common.OverFragment;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.HappyAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.WithdrawPresent;
import com.rebater.cash.well.fun.util.DbUtils;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Builder;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.RelativeGuide;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

import butterknife.BindView;

public class VersionFragment extends OverFragment implements View.OnClickListener, IModel.LiftView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    @BindView(R.id.way_contact)
    LinearLayout way_contact;
    @BindView(R.id.sign_in)
    LinearLayout sign_in;
    @BindView(R.id.way_code)
    LinearLayout way_code;
    @BindView(R.id.way_privacy)
    LinearLayout way_privacy;
    @BindView(R.id.way_suggestion)
    LinearLayout way_suggestion;
    @BindView(R.id.setting_top)
    ImageView setting_top;
    @BindView(R.id.way_record)
    LinearLayout way_record;
    @BindView(R.id.withdrawal_tab)
    LinearLayout withdrawal_tab;
    @BindView(R.id.list_pay)
    RecyclerView recyclerView_payInfo;
    @BindView(R.id.points_record)
    LinearLayout points_record;
    WithdrawPresent withdrawPresent;
    WithdrawalPanel mWithdrawalPanel;
    HappyAdapter exchangeAdpater;
    int mType;

    public VersionFragment() {
        // Required empty public constructor
    }

    public static VersionFragment newInstance(String param1, String param2) {
        VersionFragment fragment = new VersionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        try {
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                if (exchangeAdpater != null) {
                    exchangeAdpater.notifyDataSetChanged();
                } else {
                    withdrawPresent.getModel();
                }
            } else {
                if (ProjectTools.isMore()) {
                    if (exchangeAdpater != null) {
                        exchangeAdpater.notifyDataSetChanged();
                    } else {
                        withdrawPresent.getModel();
                    }
                }
            }
            LogInfo.e("onHiddenChanged");
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_set;
    }

    @Override
    public int initView(View view) {
        try {
            withdrawPresent = (WithdrawPresent) getModel();
            sign_in.setOnClickListener(this);
            way_code.setOnClickListener(this);
            way_contact.setOnClickListener(this);
            way_privacy.setOnClickListener(this);
            way_suggestion.setOnClickListener(this);
            points_record.setOnClickListener(this);
            way_record.setOnClickListener(this);
            withdrawPresent.getDeal();
            if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                showPayList();
            } else {
                if (ProjectTools.isMore()) {
                    showPayList();
                }
            }
            ProjectTools.upAFF(Constans.withdraw_page_view, 0, FirebaseAnalytics.getInstance(getBaseActivity()));
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
        return 0;
    }

    private void showPayList() {
        try {
            if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.ISPAYSHOW, true)) {
                showReqProgress(true, getString(R.string.load));
            }
            withdrawPresent.getModel();
            List<WithdrawalPanel.PayDetail> list = DbUtils.getTable(getBaseActivity());
            if (list != null && list.size() > 0) {
                int conversion = DbUtils.getConversion(getBaseActivity());
                showAdapter(list, conversion);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void showAdapter(List<WithdrawalPanel.PayDetail> list, int conversion) {
        withdrawal_tab.setVisibility(View.VISIBLE);
        LogInfo.e("type--" + mType);
        if (exchangeAdpater == null) {
            exchangeAdpater = new HappyAdapter(getBaseActivity(), R.layout.with_iten, list, conversion);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getBaseActivity(), 2);
            recyclerView_payInfo.addItemDecoration(new ItemGap(10, 8));
            recyclerView_payInfo.setLayoutManager(gridLayoutManager);
            recyclerView_payInfo.setAdapter(exchangeAdpater);
            exchangeAdpater.setOnItemClickListener((adapter, view, position) -> {
                WithdrawalPanel.PayDetail payDetail = list.get(position);
                LogInfo.e("ddd---");
                if (payDetail != null) {
                    if (ViewTools.isFastDoubleClick(view.getId())) {
                        return;
                    }
                    LogInfo.e("ddd---");
                    showInfo(position);
                    WorksUtils.insertData(getBaseActivity(), payDetail.id, 17, 4, "" + position, "" + 0, Constans.withdraw_btn_click, 0, "0");
                }
            });
//            exchangeAdpater.setOnItemChildClickListener((adapter, view, position) -> {
//                WithdrawalPanel.PayDetail payDetail = list.get(position);
//                if (payDetail != null) {
//                    if (ViewTools.isFastDoubleClick(view.getId())) {
//                        return;
//                    }
//                    showInfo(position);
//                    WorksUtils.insertData(getBaseActivity(), payDetail.id, 17, 4, "" + position, "" + 0, Constans.withdraw_btn_click, 0, "0");
//
//                }
//            });
        } else {
            exchangeAdpater.getData();
            exchangeAdpater.getData().clear();
            exchangeAdpater.addData(list);
        }
        if (exchangeAdpater != null && exchangeAdpater.getData() != null && exchangeAdpater.getData().size() > 0) {
            showGuide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(SelfPrefrence.INSTANCE.getString(SelfValue.recommended_code, ""))) {
            way_code.setVisibility(View.VISIBLE);
        } else {
            way_code.setVisibility(View.GONE);
        }
        if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
            way_record.setVisibility(View.VISIBLE);
        } else {
            way_record.setVisibility(View.GONE);
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    protected OverPresent initModel() {
        return new WithdrawPresent(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (ViewTools.isFastDoubleClick(id)){
            return;
        }
        switch (id) {
            case R.id.way_contact:
                ContactActivity.startActivity(getBaseActivity());
                WorksUtils.insertData(getBaseActivity(), 0, 19, 4, "0", "" + 0, "0", 0, "0");
                break;
            case R.id.way_privacy:
                ProjectTools.startIntent(getBaseActivity(), Constans.urlPrivacy);
                WorksUtils.insertData(getBaseActivity(), 0, 21, 4, "0", "" + 0, "0", 0, "0");

                break;
            case R.id.way_suggestion:
                SuggestionActivity.startActivity(getBaseActivity());
                WorksUtils.insertData(getBaseActivity(), 0, 20, 4, "0", "" + 0, "0", 0, "0");
//                LogTools.e("position--" + ll_contact);
//                SakolActivity.startActivity(getBaseActivity());
                break;
            case R.id.way_code:
            case R.id.sign_in:
                InvitationCodeActivity.startActivity(getBaseActivity());
                WorksUtils.insertData(getBaseActivity(), 0, 29, 4, "0", "" + 0, "0", 0, "0");

                break;

            case R.id.way_record:
                PayRecordsActivity.startActivity(getBaseActivity());
                WorksUtils.insertData(getBaseActivity(), 0, 63, 4, "0", "" + 0, "0", 0, "0");
                break;
            case R.id.points_record:
                PointsDetailActivity.startActivity(getBaseActivity());
                WorksUtils.insertData(getBaseActivity(), 0, 64, 4, "0", "" + 0, "0", 0, "0");

                break;
        }
    }

    @Override
    public void onError() {
        hideReqProgress();

    }

    @Override
    public void onHold(WithdrawalPanel withdrawalPanel) {
        try {
            hideReqProgress();
            mWithdrawalPanel = withdrawalPanel;
            List<WithdrawalPanel.PayDetail> list = withdrawalPanel.list;
            if (list != null && list.size() > 0) {
                int conversion = withdrawalPanel.conversion;
                SelfPrefrence.INSTANCE.setBoolean(SelfValue.ISPAYSHOW, false);
                showAdapter(list, conversion);
                DbUtils.deletePayInfo(getBaseActivity());
                DbUtils.insertPayInfo(getBaseActivity(), list, conversion);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void showGuide() {
        Animation enterAnimation = ProjectTools.getAnimotion(1);
        Animation exitAnimation = ProjectTools.getAnimotion(0);
        Builder builder = NewbieGuide.with(getBaseActivity())
                .setLabel("with_view_guide")
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        LogInfo.e("showed");

                    }
                    @Override
                    public void onRemoved(Controller controller) {
                        LogInfo.e("onRemoved");
                    }
                })
                .setShowCounts(1)
                .alwaysShow(false);
        GuidePage guidePage1 = GuidePage.newInstance()
                //getChildAt获取的是屏幕中可见的第一个，并不是数据中的position
                .addHighLight(recyclerView_payInfo, new RelativeGuide(R.layout.view_guide_with,
                        Gravity.BOTTOM, 16))
                .setBackgroundColor(getResources().getColor(R.color.translate))
                .setEnterAnimation(enterAnimation)
                .setExitAnimation(exitAnimation);
        if (((MainActivity) getBaseActivity()).getLastSelectedPosition() == 2) {
            builder.addGuidePage(guidePage1).show();
        }
    }

    private void showInfo(int position) {
        try {
            String payNames = SelfPrefrence.INSTANCE.getString(SelfValue.PAYNAME, "Paypal");
            String string = SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "0");
            int point = 0;
            if (string.contains(".")) { //转化浮点数
                point = (int) (Float.parseFloat(string));
            } else {
                point = Integer.parseInt(string);
            }

            int money = (int) (mWithdrawalPanel.conversion * mWithdrawalPanel.list.get(position).cash_amount); //？
            if (point < money) {
                showToast(R.string.enough);
                ProjectTools.upHomeView(1, Constans.withdraw_btn_click, FirebaseAnalytics.getInstance(getBaseActivity()), "" + money, payNames, "" + 0);
            } else {
                showUrlDialog(position);
                ProjectTools.upHomeView(1, Constans.withdraw_btn_click_ok, FirebaseAnalytics.getInstance(getBaseActivity()), "" + money, payNames, "" + 0);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void showUrlDialog(int position) {
        try {
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.item_with, null);
            Dialog dialog = new AlertDialog.Builder(getBaseActivity())
                    .setView(view)
                    .setCancelable(true)
                    .create();
            Button intall_button = view.findViewById(R.id.extra);
            EditText editText1 = view.findViewById(R.id.confirmation_code1);
            EditText editText2 = view.findViewById(R.id.confirmation_code2);
            intall_button.setOnClickListener(v -> {
                String string1 = editText1.getText().toString().trim();
                String string2 = editText2.getText().toString().trim();
                if (TextUtils.isEmpty(string1) || TextUtils.isEmpty(string2)) {
                    showToast(R.string.pay_empty);
                    return;
                }
                if (string1.equals(string2)) {
                    showReqProgress(true, getString(R.string.loading));
                    String type = SelfPrefrence.INSTANCE.getString(SelfValue.CRRENCY, "USD");
                    WorksUtils.getcash(withdrawPresent, mWithdrawalPanel.list.get(position).id, string1, type);
                } else {
                    showToast(R.string.two_same);
                    return;
                }
                dialog.dismiss();
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onCash(UserView userView) {
        try {
            hideReqProgress();
            String credits = "" + userView.points;
            if (credits.contains(".")) {
                credits = credits.split("\\.")[0];
            }
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            LogInfo.e(credits);
            ((MainActivity) getBaseActivity()).getmoneyTextView().setText(credits);
            showSuccess();
            if (exchangeAdpater != null) {
                exchangeAdpater.notifyDataSetChanged();
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    private void showSuccess() {
        try {
            View view = LayoutInflater.from(getBaseActivity()).inflate(R.layout.dialog_ok, null);
            Dialog dialog = new AlertDialog.Builder(getBaseActivity())
                    .setView(view)
                    .setCancelable(true)
                    .create();
            ImageView close = view.findViewById(R.id.close);
            Button exit = view.findViewById(R.id.extra);
            close.setOnClickListener(v -> {
                dialog.dismiss();
            });
            exit.setOnClickListener(v -> {
                dialog.dismiss();
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onRecord(Records recordsList) {

    }

    @Override
    public void onModel(List<SimpleInfo> simpleInfoList) {
        try {
            if (simpleInfoList != null && simpleInfoList.size() > 0) {
                SimpleInfo owksa = ProjectTools.getPayWay(simpleInfoList);
                if (owksa != null) {
                    int id = owksa.id;
                    String payNames = owksa.name;
                    SelfPrefrence.INSTANCE.setString(SelfValue.PAYNAME, payNames);
                    withdrawPresent.getHold(id);
                }
            }
        } catch (Exception | Error e) {
            LogInfo.e(e.toString());
        }
    }


    @Override
    public void onDeal(Qisks qisks) {
        try {
            if (qisks.deal.size() > 0) {
                Qisks.Deal deal = ProjectTools.getDeal(qisks.deal);
                if (deal != null) {
                    Qisks.Level level = qisks.level;
                    if (level != null) {
                        float rate = deal.amount;
                        LogInfo.e("--" + rate);
                        SelfPrefrence.INSTANCE.setFloat(SelfValue.RATEMONEY, rate);
                        String coinType = deal.currency;
                        SelfPrefrence.INSTANCE.setString(SelfValue.CRRENCY, coinType);
                    }
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
}