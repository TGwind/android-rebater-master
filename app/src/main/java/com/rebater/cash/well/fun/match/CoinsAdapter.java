package com.rebater.cash.well.fun.match;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Currency;
import com.rebater.cash.well.fun.util.Glidetap;
import com.rebater.cash.well.fun.util.LogInfo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CoinsAdapter extends BaseQuickAdapter<Currency.Details, BaseViewHolder> {
    Context mContext;
    public CoinsAdapter(Context context, int layoutResId, @Nullable List<Currency.Details> data) {
        super(layoutResId, data);
        mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Currency.Details payInfo) {
        try {
            baseViewHolder.setText(R.id.userView,""+ payInfo.time);
            String status = payInfo.type;
            String points=payInfo.points.split("\\.")[0];
            if (!TextUtils.isEmpty(status)) {
                if (status.equals("1")) {
                    baseViewHolder.setText(R.id.count_num, "+"+points);
                } else if (status.equals("2")) {
                    baseViewHolder.setText(R.id.count_num, "-"+points);
                }
            }
            RequestOptions option = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.default__icon)
                    .priority(Priority.HIGH);
            String url = payInfo.icon;
            if (!TextUtils.isEmpty(url) && !url.equals("null")) {
                Glide.with(mContext).load(url)
                        .apply(option).transition(withCrossFade())
                        .transform(new Glidetap(24, 1))
                        .into((ImageView) baseViewHolder.getView(R.id.img));
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }
}