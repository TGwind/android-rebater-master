package com.rebater.cash.well.fun.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.QuestAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
//Contact us
public class ContactActivity extends OverActivity implements IModel.FirstView {

    @BindView(R.id.recyclerview_help)
    RecyclerView recyclerview_help;
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    QuestAdapter questAdapter;
    LauncherPresent loginTroll;
    @BindView(R.id.empty_img)
    ImageView empty_img;
    @BindView(R.id.text_nodata)
    TextView text_nodata;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_contact);
    }

    private static Intent intentof(Context context) {
        return new Intent(context, ContactActivity.class);
    }

    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    @Override
    protected void initView() {
        if (loginTroll == null) {
            loginTroll = (LauncherPresent) getModel();
        }
        loginTroll.getMan();
        title.setText(R.string.contact);
        backgo.setOnClickListener(v -> {
            if (ViewTools.isFastDoubleClick(R.id.backgo)) {
                return;
            }
            finish();
        });

    }

    private void handleDta(Okdko okdko) {
        ProjectTools.HandleContact(okdko.is_open_type, okdko.url, okdko.open_pkg, ContactActivity.this, okdko.connect);
    }

    @Override
    protected OverPresent initModel() {
        return new LauncherPresent(this);
    }

    @Override
    public void onError() {
//        showToast(R.string.no_more);
//        finish();
        if (questAdapter != null && questAdapter.getData() != null && questAdapter.getData().size() > 0) {
            empty_img.setVisibility(View.GONE);
            text_nodata.setVisibility(View.GONE);
            LogInfo.e("vivi");
        } else {
            LogInfo.e("none");
            empty_img.setVisibility(View.VISIBLE);
            text_nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPeizhi(UserInfo userInfo) {

    }

    @Override
    public void onPatner(List<Oiswd> tabls) {

    }

    @Override
    public void onMan(List<Okdko> list) {
        try {
            if (questAdapter == null) {
                questAdapter = new QuestAdapter(this, R.layout.item_email, list);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerview_help.setLayoutManager(linearLayoutManager);
                recyclerview_help.addItemDecoration(new ItemGap(20, 4));
                recyclerview_help.setAdapter(questAdapter);
                questAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Okdko okdko = list.get(position);
                        if (okdko != null) {
                            handleDta(okdko);
//                        loginTroll.sendlogs(WorksUtils.getString(okdko.id, 23, 6, "" + position));
                            WorksUtils.insertData(ContactActivity.this, 0, 23, 6, "0", "0", "0", 0, "0");

                        }
                    }
                });
                questAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                    Okdko okdko = list.get(position);
                    if (okdko != null) {
                        handleDta(okdko);
                    }
                });
            } else {
                questAdapter.getData();
                questAdapter.getData().clear();
                questAdapter.addData(list);
            }
            if (questAdapter != null && questAdapter.getData() != null && questAdapter.getData().size() > 0) {
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
    public void onLocationError() {

    }

    @Override
    public void onLocation(Piskos piskos) {

    }
}