package com.rebater.cash.well.fun.match;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.CheckBean;
import com.rebater.cash.well.fun.util.LogInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CheckAdapter extends BaseQuickAdapter<CheckBean, BaseViewHolder> {
    Context mContext;

    public CheckAdapter(Context context, int layoutResId, @Nullable List<CheckBean> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CheckBean payInfo) {
        int status=payInfo.status;
        int position=baseViewHolder.getLayoutPosition()+1;
        if(status==0){
            baseViewHolder.setTextColor(R.id.day,mContext.getResources().getColor(R.color.black));
            baseViewHolder.setBackgroundRes(R.id.img_sign,R.drawable.sign_icon);
            baseViewHolder.setTextColor(R.id.points,mContext.getResources().getColor(R.color.sign_bottom));
            baseViewHolder.setBackgroundRes(R.id.item_sign, R.drawable.dra_sign_select);
        }else if(status==1){
            baseViewHolder.setTextColor(R.id.day,mContext.getResources().getColor(R.color.white));
            baseViewHolder.setBackgroundRes(R.id.img_sign,R.drawable.signed_icon);
            baseViewHolder.setTextColor(R.id.points,mContext.getResources().getColor(R.color.white));
            baseViewHolder.setBackgroundRes(R.id.item_sign, R.drawable.dra_signed);
        }else if(status==2){
            baseViewHolder.setTextColor(R.id.day,mContext.getResources().getColor(R.color.black));
            baseViewHolder.setBackgroundRes(R.id.img_sign,R.drawable.sign_icon);
            baseViewHolder.setTextColor(R.id.points,mContext.getResources().getColor(R.color.sign_bottom));
            baseViewHolder.setBackgroundRes(R.id.item_sign, R.drawable.dra_sign_unselect);
        }
        baseViewHolder.setText(R.id.day, mContext.getString(R.string.day)+" " + position);
        baseViewHolder.setText(R.id.points, ""+payInfo.points);
        LogInfo.e(""+payInfo.points);
//        baseViewHolder.addOnClickListener(R.id.img_sign);
    }
}
