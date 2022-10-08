package com.rebater.cash.well.fun.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Kosa;
import com.rebater.cash.well.fun.bean.Piskos;
import com.rebater.cash.well.fun.bean.UserInfo;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.present.CodePresent;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;

import butterknife.BindView;
//Invitation Code
public class InvitationCodeActivity extends OverActivity implements IModel.HelpView {

    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    CodePresent codePresent;
    @BindView(R.id.confirmation_code)
    EditText editText;
    @BindView(R.id.sure)
    Button sure;
    String code;
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_invitation_code);

    }
    public static void startActivity(Context context ) {
        Intent intent=new Intent(context, InvitationCodeActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void initView() {
        title.setText(R.string.fill_hint);
        backgo.setOnClickListener(v -> {
            if (ViewTools.isFastDoubleClick(R.id.backgo)){
                return;
            }
            finish();
        });
        if(codePresent ==null){
            codePresent = (CodePresent) getModel();
        }
        sure.setOnClickListener(v -> {
            if (ViewTools.isFastDoubleClick(R.id.sure)){
                return;
            }
            gotoMain();
        });
    }

    private void gotoMain() {
        code = editText.getText().toString().trim();
        if (!TextUtils.isEmpty(code)) {
            codePresent.upReson(code);
        } else {
            showToast(R.string.code_null);
            return;
        }
//        codePresent.sendlogs(WorksUtils.getString(0,28,12,""+0));
        WorksUtils.insertData(InvitationCodeActivity.this, 0, 28, 12, "0", "0", "0", 0, "0");

    }
    @Override
    protected OverPresent initModel() {
        return new CodePresent(this);
    }


    @Override
    public void onError() {
        showToast(R.string.contact_code);
    }

    @Override
    public void onPeizhi(UserInfo userInfo) {

    }

    @Override
    public void onLocationError() {

    }

    @Override
    public void onNumber(Kosa poloass) {
        showToast(R.string.finish_contact);
        SelfPrefrence.INSTANCE.setString(SelfValue.USER_CODE,code);
        if(poloass.user!=null) {
            SelfPrefrence.INSTANCE.setString(SelfValue.recommended_code, poloass.user.recommended_code);
        }
        finish();
    }

    @Override
    public void onInfo(Piskos piskos) {

    }
}