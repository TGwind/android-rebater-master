package com.rebater.cash.well.fun.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.present.LovePresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.WorksUtils;
import com.rebater.cash.well.fun.util.pre.Constans;

public class OpenTool {
    public static String a = "com.android.vending";

    public static void a(Context context, String str, int id, int step, String pack, String tableId) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
                WorksUtils.insertData(context, id, 39, 8, "0", tableId, "0", step, pack);
                return;
            }
            WorksUtils.insertData(context, id, 40, 8, "0", tableId, "0", step, pack);
        } catch (Error | Exception e) {
            WorksUtils.insertData(context, id, 41, 8, "0", tableId, "0", step, pack);
        }
    }
    public static int getNetworkType(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return 404;
            }
            if (!activeNetworkInfo.isAvailable()) {
                return 404;
            }

            int type = activeNetworkInfo.getType();
            if (type == 0) {
                int status=activeNetworkInfo.getSubtype();
                return  status;
            }else{
                return  type;
            }
        } catch (Throwable unused) {
            return 101;
        }
    }

    public static void gotoGoogle(Context context, String str, int id, int step, String pack, int type, String tableId) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(str));
            intent.setPackage(a);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            if (type == 1) {
                WorksUtils.insertData(context, id, 37, 8, "0", tableId, "0", step, pack);
            } else {
                WorksUtils.insertData(context, id, 42, 8, "0", tableId, "0", step, pack);

            }

            ProjectTools.upHomeView(1, Constans.offer_open_ok, FirebaseAnalytics.getInstance(context), tableId, "" + id, "" + step);

        } catch (Error | Exception e) {
            if(type==1) {
                WorksUtils.insertData(context, id, 38, 8, "0", tableId, "0", step, pack);
            }else{
                WorksUtils.insertData(context, id, 43, 8, "0", tableId, "0", step, pack);
            }
            a(context, str, id, step, pack, tableId);
            LogInfo.e(e.toString());
        }
    }

}
