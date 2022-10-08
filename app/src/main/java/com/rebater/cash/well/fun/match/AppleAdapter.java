package com.rebater.cash.well.fun.match;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Woask;
import com.rebater.cash.well.fun.util.Glidetap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AppleAdapter extends BaseQuickAdapter<Woask, BaseViewHolder> {
    Context mContext;

    public AppleAdapter(Context context, int layoutResId, @Nullable List<Woask> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Woask woask) {
        baseViewHolder.setText(R.id.title, woask.offerInfo.title);
        baseViewHolder.setText(R.id.reward, "+" + woask.offerInfo.award);
        RequestOptions options = new RequestOptions()
//                .centerCrop()
                .timeout(10000)
                .transform(new Glidetap(24, 1))
                .error(R.drawable.default__icon)
                .priority(Priority.HIGH);
        int type = woask.type;
        if (type == 1) {
            Glide.with(mContext).load(woask.offerInfo.image_url)
                    .apply(options).transition(withCrossFade())
                    .into((ImageView) baseViewHolder.getView(R.id.img));
        } else if (type == 2) {
            Glide.with(mContext).load(woask.offerInfo.images)
                    .apply(options).transition(withCrossFade())
                    .into((ImageView) baseViewHolder.getView(R.id.img));
        } else if (type == 3) {
            Glide.with(mContext).load(woask.offerInfo.icon)
                    .apply(options).transition(withCrossFade())
                    .into((ImageView) baseViewHolder.getView(R.id.img));
        }
        int status = woask.status;
        if (status != 3) {
            baseViewHolder.setText(R.id.task_status, mContext.getString(R.string.progress));
            baseViewHolder.setTextColor(R.id.task_status, mContext.getResources().getColor(R.color.button_yellow));
        } else {
            baseViewHolder.setText(R.id.task_status, mContext.getString(R.string.complete));
            baseViewHolder.setTextColor(R.id.task_status, mContext.getResources().getColor(R.color.task_grep));
        }
    }
}