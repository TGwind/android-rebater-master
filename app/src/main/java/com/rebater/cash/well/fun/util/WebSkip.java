package com.rebater.cash.well.fun.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rebater.cash.well.fun.bean.Wibumer;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Timer;
import java.util.TimerTask;

public class WebSkip {
    private static WebSkip webSkip;
    private Context mContext;
    private WebView mWebView;
    Timer timer;
    //    UrlCatchListen urlCatchListen;
    Wibumer wibumer;
    String tracklink;
    public WebSkip(Context context) {
        mContext=context;
    }

    public static  WebSkip getInstance(Context context){
        if(webSkip==null){
            synchronized (WebSkip.class){
                if(webSkip==null){
                    webSkip=new WebSkip(context);
                }
            }
        }
        return  webSkip;
    }


    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(String url , Handler handler, Wibumer info) {
        try {

            wibumer = info;
            if (mWebView == null) {
                mWebView = new WebView(mContext);
            }
            if (timer == null) {
                timer = new Timer();
            }
            tracklink = url;
            mWebView.clearCache(true);
            WebSettings localWebSettings = mWebView.getSettings();
            localWebSettings.setAllowFileAccess(true);
            localWebSettings.setAllowContentAccess(false);
            localWebSettings.setGeolocationEnabled(true);
            localWebSettings.setUseWideViewPort(true);
            localWebSettings.setLoadWithOverviewMode(true);
            localWebSettings.setDomStorageEnabled(true);
            localWebSettings.setDatabaseEnabled(true);
            localWebSettings.setMixedContentMode(2);
            localWebSettings.setAllowFileAccessFromFileURLs(true);
            localWebSettings.setJavaScriptEnabled(true);
            localWebSettings.setAppCacheEnabled(true);
            mWebView.setSaveEnabled(true);
            mWebView.setScrollContainer(true);
            mWebView.setSaveFromParentEnabled(true);
            mWebView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            mWebView.setWebChromeClient(new WebChromeClient() {
                @Override
                public boolean onJsAlert(WebView view, String url, String message,
                                         JsResult result) {
                    return super.onJsAlert(view, url, message, result);
                }

                @Override
                public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
//                Log.e("onConso",consoleMessage.message());
                    return super.onConsoleMessage(consoleMessage);

                }

            });
            mWebView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    LogInfo.e("-121-" + url);
                    handleUrl(url, handler);
                    return super.shouldOverrideUrlLoading(view, url);
                }
                @Override
                public void onReceivedSslError(WebView view,
                                               SslErrorHandler handler, SslError error) {
                    LogInfo.e("onReceived----"+error.toString());
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    LogInfo.e("finished--" + url);
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    LogInfo.e("Error----" + description);
                }
            });
            mWebView.loadUrl(url);//getString(R.string.privacy_policy_url)
            int time = SelfPrefrence.INSTANCE.getInt(SelfValue.LOADTIME, 6);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    LogInfo.e("runed");
                    handler.sendEmptyMessage(0x13);
                    if (wibumer != null) {
                        OpenTool.a(mContext, tracklink, wibumer.id, wibumer.step, wibumer.pkg, wibumer.tableId);
                        WorksUtils.insertData(mContext, wibumer.id, 45, 8, "0", wibumer.tableId, Constans.offer_open_err, wibumer.step, wibumer.pkg);
                        ProjectTools.upOpenError(Constans.offer_open_err, FirebaseAnalytics.getInstance(mContext), wibumer.tableId, "" + wibumer.id);
                    }
                    handler.postDelayed(runnable, 2000);
                    timer.cancel();
                    timer = null;
                }
            }, time * 1000);

        } catch (Error | Exception unused) {
            LogInfo.e(unused.toString());
            handler.sendEmptyMessage(0x13);

        }
    }

    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            try{
                clearWebview(mWebView);

            }catch (Exception |Error e){
                LogInfo.e(e.toString());
            }
        }
    };
    private void handleUrl(String url, Handler handler) {
        if (!TextUtils.isEmpty(url) && wibumer != null) {
            try {
                if (url.toLowerCase().startsWith("market://")) {
                    OpenTool.gotoGoogle(mContext, url, wibumer.id, wibumer.step, wibumer.pkg, 1, wibumer.tableId);
//                    OpenTool.gotoGoogle(mContext, url, wibumer.lovePresent, wibumer.id, wibumer.position, wibumer.pkg, 1);
                    handler.sendEmptyMessage(0x13);
                    timer.cancel();
                    timer = null;
                    handler.postDelayed(runnable, 2000);
                    LogInfo.e("1--" + url);
//                    cVar.a(true, "Success: Market Url", str);

                } else if (url.startsWith("https://play.google.com/store/apps/")) {
                    OpenTool.gotoGoogle(mContext, url, wibumer.id, wibumer.step, wibumer.pkg, 0, wibumer.tableId);
                    handler.sendEmptyMessage(0x13);
                    timer.cancel();
                    timer=null;
                    handler.postDelayed(runnable,2000);
                    LogInfo.e("2--"+url);
                } else if (url.startsWith("http://") || url.startsWith("https://")) {
                    LogInfo.e("3--"+url);
                    tracklink=url;
                }else {
                    LogInfo.e("4--"+url);
                    tracklink=url;
                }
            } catch (Error | Exception e) {
                LogInfo.e(e.toString());
                if (wibumer != null) {
                    WorksUtils.insertData(mContext, wibumer.id, 46, 8, "0", wibumer.tableId, "0", wibumer.step, wibumer.pkg);
                }
            }
        }
    }

    public static byte UTF8[]= {85,84,70,45,56};
    public  void clearWebview(WebView webView) {
        String UTF_8=new String(UTF8);
        try {
            if (webView != null) {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    //加载HTML数据
                    webView.loadDataWithBaseURL(null, "", "text/html", UTF_8,
                            null);
                }
            }
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
}
