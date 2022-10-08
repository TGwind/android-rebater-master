package com.rebater.cash.well.fun.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Currency;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.present.QuestionPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ViewTools;
import com.rebater.cash.well.fun.util.WorksUtils;

import butterknife.BindView;
//Suggestion
public class SuggestionActivity extends OverActivity implements IModel.BackView {
    @BindView(R.id.edit_suggestion)
    EditText edit_suggestion;
    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.edit_email)
    EditText edit_email;
    @BindView(R.id.submit)
    Button button;
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    QuestionPresent questionPresent;
    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_suggestion);
    }

    @Override
    protected void initView() {
        title.setText(R.string.feedback);
        if(questionPresent ==null){
            questionPresent = (QuestionPresent) getModel();
        }
        backgo.setOnClickListener(v -> {
            if (ViewTools.isFastDoubleClick(R.id.backgo)){
                return;
            }
            finish();
        });
        init();
    }
    private void init(){
        button.setOnClickListener(v -> {
            String name=edit_name.getText().toString();
            String email=edit_email.getText().toString();
            String content=edit_suggestion.getText().toString();
            if(!TextUtils.isEmpty(content)&&!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(name)){
                WorksUtils.feedback(questionPresent, name, content, email);
                Toast.makeText(SuggestionActivity.this, getString(R.string.feedback_ok), Toast.LENGTH_SHORT).show();
//                questionPresent.sendlogs(WorksUtils.getString(0,22,7,""+0));
                WorksUtils.insertData(SuggestionActivity.this, 0, 22, 7, "0", "0", "0", 0, "0");
                finish();
            }else{
                Toast.makeText(SuggestionActivity.this,getString(R.string.feedback_error),Toast.LENGTH_SHORT).show();
            }


        });
    }
    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    private static Intent intentof(Context context) {
        return new Intent(context, SuggestionActivity.class);
    }
    @Override
    protected OverPresent initModel() {
        return new QuestionPresent(this);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish(String msg) {
        LogInfo.e("--"+msg);
    }

    @Override
    public void onDetails(Currency currency) {

    }
}