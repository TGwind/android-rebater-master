package com.rebater.cash.well.fun.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.essential.OverView;

import butterknife.ButterKnife;

public abstract class OverFragment extends Fragment implements OverView {
    private OverActivity mActivity;
    private View mLayoutView;


    private ProgressDialog mProgressDialog;

    protected OverPresent overPresent;

    @Override
    public void hideReqProgress() {
        try {
            if (mProgressDialog == null)
                return;
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
//            LogInfo.e(e.toString());
        }
    }

    public OverPresent getModel() {
        return overPresent;
    }

    @Override
    public void showReqProgress(boolean flag, String message) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(getBaseActivity());
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setCancelable(flag);
                mProgressDialog.setCanceledOnTouchOutside(true);
                mProgressDialog.setMessage(message);
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
//            LogInfo.e(e.toString());
        }
    }

    protected abstract OverPresent initModel();

    public abstract int getLayoutRes();

    public abstract int initView(View view);

    protected abstract void managerArguments();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            managerArguments();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //20160727 修复该方法多次调用 bug
        if (mLayoutView != null) {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null) {
                parent.removeView(mLayoutView);
            }
        } else {
            mLayoutView = getCreateView(inflater, container);
            ButterKnife.bind(this, mLayoutView);
            overPresent = initModel();
            initView(mLayoutView);     //初始化布局
        }

        return mLayoutView;
    }

    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    public OverActivity getBaseActivity() {
        if (mActivity == null) {
            mActivity = (OverActivity) getActivity();
        }
        return mActivity;
    }

    //
    @Override
    public void showToast(int resId) {
        if (getStatus()) {
            OverActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(resId);
            }
        }
    }

    @Override
    public void showToast(String msg) {
        if (getStatus()) {
            OverActivity activity = getBaseActivity();
            if (activity != null) {
                activity.showToast(msg);
            }
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }
}