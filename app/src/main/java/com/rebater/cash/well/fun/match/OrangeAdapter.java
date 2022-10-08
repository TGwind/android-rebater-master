package com.rebater.cash.well.fun.match;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Oiswd;
import com.rebater.cash.well.fun.util.Glidetap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class OrangeAdapter extends BaseQuickAdapter<Oiswd, BaseViewHolder> {
    Context mContext;
    public OrangeAdapter(Context context, int layoutResId, @Nullable List<Oiswd> data){
        super(layoutResId,data);
        mContext=context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Oiswd offerInfo){
        baseViewHolder.setText(R.id.title,offerInfo.title);
        RequestOptions option=new RequestOptions()
                .centerCrop()
                .error(R.drawable.locol_icon)
                .placeholder(R.drawable.locol_icon)
                .priority(Priority.HIGH);
        String url=offerInfo.images;
        if(!TextUtils.isEmpty(url)){
            Glide.with(mContext).load(url)
                    .apply(option).transition(withCrossFade())
                    .transform(new Glidetap(24, 1))
                    .into((ImageView)baseViewHolder.getView(R.id.img_wall));
        }
        baseViewHolder.addOnClickListener(R.id.img_wall);
    }

}
