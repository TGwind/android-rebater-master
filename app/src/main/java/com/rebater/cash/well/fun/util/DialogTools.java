package com.rebater.cash.well.fun.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.rebater.cash.well.fun.R;

public class DialogTools {
    public static void showPushDialog(Activity context, int type) {
        try {
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_error, null);
            Dialog dialog = new AlertDialog.Builder(context)
                    .setView(view)
                    .setCancelable(true)
                    .create();
            TextView textView=view.findViewById(R.id.content);
            if(type==1){
                textView.setText(context.getString(R.string.error_info));
            }else{
                textView.setText(context.getString(R.string.infos));

            }
            Button intall_button = view.findViewById(R.id.exit);
            intall_button.setOnClickListener(v -> {
                GoHome(context);
                dialog.dismiss();
            });
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();

        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
    public static void GoHome(Activity context) {
        try{
            Intent intent= new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(intent);
            context.finish();
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
}
