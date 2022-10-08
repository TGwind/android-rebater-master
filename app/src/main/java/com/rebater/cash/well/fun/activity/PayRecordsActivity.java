package com.rebater.cash.well.fun.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Qisks;
import com.rebater.cash.well.fun.bean.Records;
import com.rebater.cash.well.fun.bean.UserView;
import com.rebater.cash.well.fun.bean.SimpleInfo;
import com.rebater.cash.well.fun.bean.WithdrawalPanel;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.GetedInfoAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.WithdrawPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ViewTools;

import java.util.List;

import butterknife.BindView;

public class PayRecordsActivity extends OverActivity implements IModel.LiftView {
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.record)
    RecyclerView recyclerView_record;
    @BindView(R.id.empty_img)
    ImageView empty_img;
    @BindView(R.id.text_nodata)
    TextView text_nodata;
    GetedInfoAdapter getedInfoAdapter;
    WithdrawPresent withdrawPresent;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_play_records);
    }

    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    private static Intent intentof(Context context) {
        Intent intent = new Intent(context, PayRecordsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
    @Override
    protected void initView() {
        try{
            title.setText(R.string.record);
            backgo.setOnClickListener(v -> {
                if (ViewTools.isFastDoubleClick(R.id.backgo)){
                    return;
                }
                finish();
            });
            if(withdrawPresent ==null){
                withdrawPresent = (WithdrawPresent) getModel();
            }
            withdrawPresent.getRecord(0);
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    @Override
    protected OverPresent initModel() {
        return new WithdrawPresent(this);
    }


    @Override
    public void onHold(WithdrawalPanel withdrawalPanel) {

    }

    @Override
    public void onCash(UserView userView) {

    }

    @Override
    public void onRecord(Records recordsList) {
        try {
            List<Records.RecordDetails> list= recordsList.list;
            if(list!=null&&list.size()>0) {
                if (getedInfoAdapter == null) {
                    getedInfoAdapter = new GetedInfoAdapter(this, R.layout.item_record, list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView_record.setLayoutManager(linearLayoutManager);
                    recyclerView_record.addItemDecoration(new ItemGap(20, 4));
                    recyclerView_record.setAdapter(getedInfoAdapter);
//                    recordAdapter.setOnLoadMoreListener(() -> {
//
//                    });
                } else {
                    getedInfoAdapter.getData();
                    getedInfoAdapter.getData().clear();
                    getedInfoAdapter.addData(list);
                }
            }
            if (getedInfoAdapter != null && getedInfoAdapter.getData() != null && getedInfoAdapter.getData().size() > 0) {
                empty_img.setVisibility(View.GONE);
                text_nodata.setVisibility(View.GONE);
            } else {
                empty_img.setVisibility(View.VISIBLE);
                text_nodata.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    public void onModel(List<SimpleInfo> simpleInfoList) {

    }

    @Override
    public void onDeal(Qisks qisks) {

    }

    @Override
    public void onError() {
//        showToast(R.string.no_record);
//        finish();
        if (getedInfoAdapter != null && getedInfoAdapter.getData() != null && getedInfoAdapter.getData().size() > 0) {
            empty_img.setVisibility(View.GONE);
            text_nodata.setVisibility(View.GONE);
        } else {
            empty_img.setVisibility(View.VISIBLE);
            text_nodata.setVisibility(View.VISIBLE);
        }
    }
}