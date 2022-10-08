package com.rebater.cash.well.fun.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.rebater.cash.well.fun.common.OverApplication;
import com.rebater.cash.well.fun.util.LogInfo;


public class SkipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            //设置1像素
            Window window = getWindow();
            window.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams params = window.getAttributes();
            params.x = 0;
            params.y = 0;
            params.height = 1;
            params.width = 1;
            window.setAttributes(params);
            Intent intent=getIntent();
            int type=2;
            if(intent!=null){
                type=intent.getIntExtra("notyfy",2);
            }
            Activity activity= OverApplication.getInstance().getActivityManager().currentActivity();
            if(type==2) {
                if (activity != null) {
                    Intent lastintent = new Intent(this, activity.getClass());
                    lastintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(lastintent);
                } else {
                    Intent mIntent = new Intent(this, MainActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent);
                }
            }else{
                Intent oIntent = new Intent(this, MainActivity.class);
                oIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(oIntent);
            }
            finish();
        }catch (Exception e){
            LogInfo.e(e.toString());
        }

    }
}