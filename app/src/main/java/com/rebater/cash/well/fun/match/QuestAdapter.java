package com.rebater.cash.well.fun.match;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Okdko;
import com.rebater.cash.well.fun.util.Glidetap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class QuestAdapter extends BaseQuickAdapter<Okdko, BaseViewHolder> {
    Context mContext;

    public QuestAdapter(Context context, int layoutResId, @Nullable List<Okdko> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Okdko okdko) {
        RequestOptions options = new RequestOptions()
                .error(R.drawable.default__icon)
                .timeout(10000)
                .transform(new Glidetap(24, 1))
                .priority(Priority.HIGH);
        Glide.with(mContext).load(okdko.icom)
                .apply(options).transition(withCrossFade())
                .into((ImageView) baseViewHolder.getView(R.id.img_check));
        baseViewHolder.setText(R.id.check_text, okdko.connect);
        baseViewHolder.addOnClickListener(R.id.sign_in);
    }
}