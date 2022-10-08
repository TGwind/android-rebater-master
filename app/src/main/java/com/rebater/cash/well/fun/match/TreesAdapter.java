package com.rebater.cash.well.fun.match;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.AdInfo;
import com.rebater.cash.well.fun.util.Glidetap;
import com.rebater.cash.well.fun.util.LogInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.AdInfo;
import com.rebater.cash.well.fun.util.LogInfo;

import java.util.List;

public class TreesAdapter extends BaseQuickAdapter<AdInfo, BaseViewHolder> {
    Context mContext;
    public TreesAdapter(Context context, int layoutResId, @Nullable List<AdInfo> data){
        super(layoutResId,data);
        mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, AdInfo offerInfo){
        try {
            baseViewHolder.setText(R.id.title, offerInfo.title);
            baseViewHolder.setText(R.id.desc_ad, offerInfo.desc);
            baseViewHolder.setText(R.id.points, "+" + offerInfo.award);
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.default__icon)
                    .timeout(10000)
                    .transform(new Glidetap(24, 1));

            String url = offerInfo.image_url;
            String hotUrl = offerInfo.image_max;
//        if(!TextUtils.isEmpty(hotUrl)&&!hotUrl.equals("null")){
            RequestOptions option = new RequestOptions()
                    .timeout(10000)
                    .error(R.drawable.game_icon)
                    .placeholder(R.drawable.game_icon)
                    .transform(new Glidetap(32, 2))
                    .priority(Priority.HIGH);
            Glide.with(mContext).load(hotUrl)
                    .override(1000, 380)
                    .apply(option).transition(withCrossFade())
                    .into((ImageView) baseViewHolder.getView(R.id.big_pic));
//        }
//        if(!TextUtils.isEmpty(url)&&!url.equals("null")){
            Glide.with(mContext).load(url)
                    .apply(options).transition(withCrossFade())
                    .into((ImageView) baseViewHolder.getView(R.id.icon));
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
//        }

    }
}
