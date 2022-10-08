package com.rebater.cash.well.fun.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ViewTools;

import butterknife.BindView;
//webView
public class MyWebActivity extends OverActivity {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    String url;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_web);
    }

    public static void startActivity(Context context, String url) {
        context.startActivity(intentof(context, url));
    }

    private static Intent intentof(Context context, String url) {
        Intent intent = new Intent(context, MyWebActivity.class);
        intent.putExtra("url", url);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void initView() {
        backgo.setOnClickListener(v -> {
            if (ViewTools.isFastDoubleClick(R.id.backgo)) {
                return;
            }
            finish();
        });
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            LogInfo.e(url);
            if (!TextUtils.isEmpty(url)) {
                initWebView(webview, url);
            } else {
                finish();
            }
        } else {
            finish();
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    public void initWebView(WebView mWebView, String url) {
        try {
            WebSettings localWebSettings = mWebView.getSettings();
            localWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
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
                    LogInfo.e(consoleMessage.message());
                    return super.onConsoleMessage(consoleMessage);
                }
            });

            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    LogInfo.e(url);
                    return true;
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
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
//                    stopColect();
//				view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
//				VideAPP.getInstance(mContext).preferenceUtil
//						.setisTaobao(false);
//				LogTool.e("---3stop");
//				myHandler.sendEmptyMessage(0x12);
                }
            });
            mWebView.loadUrl(url);//getString(R.string.privacy_policy_url)
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

    @Override
    protected OverPresent initModel() {
        return null;
    }
}