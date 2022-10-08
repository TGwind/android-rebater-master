package com.rebater.cash.well.fun.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.essential.OverView;
import com.rebater.cash.well.fun.util.LogInfo;

import butterknife.ButterKnife;

public abstract class OverActivity extends AppCompatActivity implements OverView {
    protected OverPresent overPresent;
    FragmentManager fragmentManager; //管理Fragment 负责　添加　移除　显示　隐藏
    private ProgressDialog mProgressDialog; //圆形加载进度对话框

    protected abstract OverPresent initModel();
    public OverPresent getModel(){
        //当返回值是一个接口的时候，要返回这个接口的实现类，这里相当于初始化得到一个Present实例对象
        return  overPresent;
    }

    protected  abstract  void initContentView();
    protected abstract void initView();

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        try{
            LogInfo.e("Create Activity"+this.toString());
            //设置顶部状态栏的样式
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(0xF1F7DA);
            //初始化View注入
            initContentView();
            ButterKnife.bind(this);
            overPresent = initModel();
            initView();
        }catch (Exception e){
        }
    }

    public  void addFragment(int res,Fragment fragment){
        /*在Android中，对Fragment的操作都是通过FragmentTransaction来执行
         * Fragment的操作大致可以分为两类：
         * 显示：add()，replace()， show() ， attach()
         * 隐藏：remove()， hide() ，detach()  */
        FragmentTransaction fragmentTransaction = getFragmentTransaction();
        fragmentTransaction.add(res,fragment); //fragment放入Id为res的布局容器中
        fragmentTransaction.commit();
    }

    private FragmentTransaction getFragmentTransaction() {
        return getBaseFragmentManager().beginTransaction();
    }

    private FragmentManager getBaseFragmentManager() { //获取fragmentManager实例
        if(fragmentManager == null){
            fragmentManager = getSupportFragmentManager();
        }
        return fragmentManager;
    }
    public void showFragment(Fragment fragment){
        if(fragment.isHidden()){ //当fragment被隐藏时执行一下操作
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.show(fragment); //显示当前fragment
            fragmentTransaction.commit(); //提交操作
        }
    }
    public void hideFragment(Fragment fragment){
        if(!fragment.isHidden()){ //当fragment显示的时候执行以下操作
            FragmentTransaction fragmentTransaction = getFragmentTransaction();
            fragmentTransaction.hide(fragment); //隐藏当前fragment
            fragmentTransaction.commit(); //提交操作
        }
    }

    @Override
    public void showReqProgress(boolean flag, String message) {  //显示进度条
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //样式为圆形
                mProgressDialog.setCancelable(flag);//设置是否可被关闭
                mProgressDialog.setCanceledOnTouchOutside(true); //触及对话框外时被关闭
                mProgressDialog.setMessage(message);//设置文本
            }
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception e) {
//            LogInfo.e(e.toString());
        }
    }


    @Override
    public void hideReqProgress() { //隐藏进度条
        try{
            if(mProgressDialog ==null){
                return;
            }
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }catch (Exception e){
//            LogInfo.e(e.toString());
        }

    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        try {
            if(isFinishing()){//判断当前Activity是活跃状态还是等待状态
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){

        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onError() {

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        hideReqProgress();
    }

}
