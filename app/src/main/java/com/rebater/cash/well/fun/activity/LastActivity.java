package com.rebater.cash.well.fun.activity;/*
package com.amusing.cash.well.fun.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amusing.cash.well.fun.R;
import com.amusing.cash.well.fun.match.HappyAdapter;
import com.amusing.cash.well.fun.common.OverActivity;
import com.amusing.cash.well.fun.essential.IModel;
import com.amusing.cash.well.fun.essential.OverPresent;
import com.amusing.cash.well.fun.obb.ItemGap;
import com.amusing.cash.well.fun.util.LogInfo;
import com.amusing.cash.well.fun.util.ViewTools;
import com.amusing.cash.well.fun.util.WorksUtils;
import com.amusing.cash.well.fun.util.pre.Constans;
import com.amusing.cash.well.fun.util.pre.SelfPrefrence;
import com.amusing.cash.well.fun.util.pre.SelfValue;
import com.amusing.cash.well.fun.bean.UserView;
import com.amusing.cash.well.fun.bean.Records;
import com.amusing.cash.well.fun.bean.WithdrawalPanel;
import com.amusing.cash.well.fun.present.WithdrawPresent;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kochava.base.Tracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LastActivity extends OverActivity implements IModel.LiftView {

    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.wisldo)
    RecyclerView recyclerView_payInfo;
    WithdrawalPanel mWisldo;
    HappyAdapter happyAdapter;
    int id;
    String type,name;
    WithdrawPresent withdrawPresent;


    public static void startActivity(Context context, int id, String type, String name) {
        context.startActivity(intentof(context, id,type,name));
    }

    private static Intent intentof(Context context, int id, String type, String name) {
        Intent intent = new Intent(context, LastActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("type",type);
        intent.putExtra("name",name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    public void onError() {

    }
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_reward);
    }

    @Override
    protected void initView() {
        initData();
    }
    private void initData() {
        try{
            title.setText(R.string.withdrawal);
            backgo.setOnClickListener(v -> {
                if (ViewTools.isFastDoubleClick(R.id.backgo)){
                    return;
                }
                finish();
            });
//        List<PayInfo> list= Test.getPayData();
            Intent intent=getIntent();
            if(intent!=null){
                id=intent.getIntExtra("id",0);
                type=intent.getStringExtra("type");
                name=intent.getStringExtra("name");
                LogInfo.e("--"+id+"--"+type+"--"+name);
            }
            if(withdrawPresent ==null){
                withdrawPresent = (WithdrawPresent) getModel();
            }
            withdrawPresent.getHold(id);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
    @Override
    protected OverPresent initModel() {
        return new WithdrawPresent(this);
    }
    private void showInfo(WithdrawalPanel.PayDetail payInfo, int position) {
        try{
            String string= SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT,"0");
            int point=0;
            if(string.contains(".")){
                point=(int)(Float.parseFloat( string));
            }else{
                point=Integer.parseInt(string);
            }
            int money= (int) (mWisldo.conversion* mWisldo.list.get(position).cash_amount);
            if(point< money){
                showToast(R.string.enough);
            }else{
                showUrlDialog( position);
            }
            if(SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_Log,false)) {
                Tracker.sendEvent(new Tracker.Event(Constans.withdraw_btn_click)
                        .addCustom("item_quantity", money)
                        .addCustom("item_name", name));
                Map<String, Object> eventValue = new HashMap<String, Object>();

                Bundle bundleEvent = new Bundle();
                bundleEvent.putString("event_name",Constans.withdraw_btn_click);
                FirebaseAnalytics.getInstance(LastActivity.this).logEvent(Constans.withdraw_btn_click,bundleEvent);
            }

        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
    private void showUrlDialog(int position  ) {
        try {
            View view = LayoutInflater.from(LastActivity.this).inflate(R.layout.item_with, null);
            Dialog dialog = new AlertDialog.Builder(LastActivity.this)
                    .setView(view)
                    .setCancelable(true)
                    .create();
            Button intall_button = view.findViewById(R.id.extra);
            EditText editText1=view.findViewById(R.id.confirmation_code1);
            EditText editText2=view.findViewById(R.id.confirmation_code2);
            intall_button.setOnClickListener(v -> {
                String string1=editText1.getText().toString().trim();
                String string2=editText2.getText().toString().trim();
                if(TextUtils.isEmpty(string1)||TextUtils.isEmpty(string2)){
                    showToast(R.string.pay_empty);
                    return;
                }
                if(string1.equals(string2)){
                    showReqProgress(true,getString(R.string.loading));
                    WorksUtils.getcash(withdrawPresent, mWisldo.list.get(position).id,string1,type);
                }else{
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
    public void onHold(WithdrawalPanel wisldo) {
        try{
//            hideReqProgress();
            mWisldo = wisldo;
            List<WithdrawalPanel.PayDetail> list= wisldo.list;
            if(list!=null&&list.size()>0) {
                if (happyAdapter == null) {
                    happyAdapter = new HappyAdapter(LastActivity.this, R.layout.item_list, list, wisldo.conversion);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView_payInfo.setLayoutManager(linearLayoutManager);
                    recyclerView_payInfo.addItemDecoration(new ItemGap(20, 4));
                    recyclerView_payInfo.setAdapter(happyAdapter);
                    happyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            WithdrawalPanel.PayDetail payDetail = list.get(position);
                            if (payDetail != null) {
                                if (ViewTools.isFastDoubleClick(view.getId())) {
                                    return;
                                }
                                showInfo(payDetail,position);
                                withdrawPresent.sendlogs(WorksUtils.getString(payDetail.id,17,3,""+position));

                            }
                        }


                    });
                    happyAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                        WithdrawalPanel.PayDetail payDetail = list.get(position);
                        if (payDetail != null) {
                            if (ViewTools.isFastDoubleClick(view.getId())) {
                                return;
                            }
                            showInfo(payDetail,position);
                        }
                    });
                } else {
                    happyAdapter.getData();
                    happyAdapter.getData().clear();
                    happyAdapter.addData(list);
                }
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onCash(UserView sidmog) {
        try {
            hideReqProgress();
            String credits = "" + sidmog.points;
            SelfPrefrence.INSTANCE.setString(SelfValue.WALLET_ACCOUNT, credits);
            showSuccess();
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    private void showSuccess() {
        try {
            View view = LayoutInflater.from(LastActivity.this).inflate(R.layout.dialog_ok, null);
            Dialog dialog = new AlertDialog.Builder(LastActivity.this)
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
    public void onRecord( Records recordsList) {

    }
}*/
