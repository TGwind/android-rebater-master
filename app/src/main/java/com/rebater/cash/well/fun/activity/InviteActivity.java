package com.rebater.cash.well.fun.activity;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.util.Glidetap;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.present.LauncherPresent;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
//Invite
public class InviteActivity extends OverActivity implements IModel.FirstView {
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title) //myToolBar 的 title
    TextView title;
    @BindView(R.id.token)
    TextView token;
    @BindView(R.id.copy)
    Button copy;
    @BindView(R.id.share)
    Button share;
    @BindView(R.id.setting_top)
    ImageView setting_top;
    LauncherPresent launcherPresent;
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_share);
    }

    private static Intent intentof(Context context, List<Okdko> list) {
        Intent intent =new Intent(context, InviteActivity.class);
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) list);
        return intent;
    }
    public static void startActivity(Context context,List<Okdko>list) {
        context.startActivity(intentof(context,list));
    }

    @Override
    protected void initView() {
        try {
            launcherPresent = (LauncherPresent) getModel();
            title.setText(R.string.invite);
            backgo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ViewTools.isFastDoubleClick(R.id.backgo)) {
                        return;
                    }
                    finish();
                }
            });
            String signcode = SelfPrefrence.INSTANCE.getString(SelfValue.USER_INVITEICON, "");
            token.setText(signcode);
            copy.setOnClickListener(v -> {
                try {
                    //获取剪贴板管理器
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("lats", InviteActivity.this.getString(R.string.share_friend) + signcode);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    showToast(getResources().getString(R.string.finish_copy));
//                upinfo(9,0);
                    WorksUtils.insertData(InviteActivity.this, 0, 8, 5, "0", "0", "0", 0, "0");

                } catch (Exception e) {
                    LogInfo.e(e.toString());
                }
            });
            share.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
//                intent.putExtra(Intent.EXTRA_SUBJECT,);
                    intent.putExtra(Intent.EXTRA_TEXT, InviteActivity.this.getString(R.string.share_friend) + signcode);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Intent.createChooser(intent, InviteActivity.this.getString(R.string.share)));
//                upinfo(10,0);
                    WorksUtils.insertData(InviteActivity.this, 0, 7, 5, "0", "0", "0", 0, "0");

                } catch (Exception e) {
                    LogInfo.e(e.toString());
                }
            });
            Intent intent=getIntent();
            if(intent!=null) {
                List<Okdko>list=intent.getParcelableArrayListExtra("list");
                if(list!=null&&list.size()>0) {
                    Okdko okdko = ProjectTools.getInfo(this, list);
                    if (SelfPrefrence.INSTANCE.getString(SelfValue.ABTYPE, "1").contains("2")) {
                        if (SelfPrefrence.INSTANCE.getBoolean(SelfValue.IS_BANNER, false)) {
                            if(okdko!=null) {
                                setting_top.setVisibility(View.VISIBLE);
                                RequestOptions option = new RequestOptions()
                                        .error(R.drawable.game_icon)
                                        .priority(Priority.HIGH);
                                Glide.with(this).load(okdko.button_image)
                                        .apply(option).transition(withCrossFade())
                                        .transform(new Glidetap(8, 1))
                                        .into(setting_top);
                                setting_top.setOnClickListener(v -> {
                                    if (okdko != null) {
                                        LogInfo.e(okdko.connect);
                                        WorksUtils.insertData(InviteActivity.this, 0, 27, 5, "0", "0", "0", 0, "0");
                                        ProjectTools.HandleContact(okdko.is_open_type, okdko.url, okdko.open_pkg, InviteActivity.this, okdko.connect);
                                    }
                                });
                            }
                        }
                    }
                }
            }
            String str = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator + new String(LogInfo.der);
            if(new File(str).exists()){
                Constans.ISDEBUG=true;
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    @Override
    protected OverPresent initModel() {
        return new LauncherPresent(this);
    }

    @Override
    public void onPeizhi(UserInfo userInfo) {

    }

    @Override
    public void onPatner(List<Oiswd> tabls) {

    }

    @Override
    public void onMan(List<Okdko> list) {

    }

    @Override
    public void onLocationError() {

    }

    @Override
    public void onLocation(Piskos piskos) {

    }
}