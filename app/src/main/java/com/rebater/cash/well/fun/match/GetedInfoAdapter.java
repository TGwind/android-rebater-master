package com.rebater.cash.well.fun.match;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Records;
import com.rebater.cash.well.fun.util.Glidetap;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class GetedInfoAdapter extends BaseQuickAdapter<Records.RecordDetails, BaseViewHolder> {
    Context mContext;

    public GetedInfoAdapter(Context context, int layoutResId, @Nullable List<Records.RecordDetails> data) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Records.RecordDetails payInfo) {
        try {
            float rates = SelfPrefrence.INSTANCE.getFloat(SelfValue.RATEMONEY, 1);
            float returnData = Float.valueOf(payInfo.cash_amount);
            int money = (int) (returnData * rates);
            String kind = SelfPrefrence.INSTANCE.getString(SelfValue.CRRENCY, "USD");
            if (kind.equalsIgnoreCase("RP")) {
                baseViewHolder.setText(R.id.count, "Rp" + money);//RP USD
            } else {
                baseViewHolder.setText(R.id.count, "$" + money);//RP USD
            }
            baseViewHolder.setVisible(R.id.count, true);
            baseViewHolder.setText(R.id.userView, "" + payInfo.adtime);
            LogInfo.e(payInfo.pay_images);
            String status = payInfo.status;
            if (!TextUtils.isEmpty(status)) {
                if (status.equals("1")) {
                    baseViewHolder.setText(R.id.count_num, mContext.getString(R.string.status1));
                } else if (status.equals("2")) {
                    baseViewHolder.setText(R.id.count_num, mContext.getString(R.string.status2));
                } else if (status.equals("3")) {
                    baseViewHolder.setText(R.id.count_num, mContext.getString(R.string.status3));
                } else if (status.equals("4")) {
                    baseViewHolder.setText(R.id.count_num, mContext.getString(R.string.status4));
                } else if (status.equals("5")) {
                    baseViewHolder.setText(R.id.count_num, mContext.getString(R.string.status2));
                } else if (status.equals("6")) {
                    baseViewHolder.setText(R.id.count_num, mContext.getString(R.string.status2));
                }
            }
            RequestOptions option = new RequestOptions()
//                    .centerCrop()
                    .error(R.drawable.default__icon)
//                    .placeholder(R.drawable.default__icon)
                    .priority(Priority.HIGH);
            String url = payInfo.pay_images;
            if (!TextUtils.isEmpty(url) && !url.equals("null")) {
                Glide.with(mContext).load(url)
                        .apply(option).transition(withCrossFade())
                        .transform(new Glidetap(24, 1))
                        .into((ImageView) baseViewHolder.getView(R.id.img));
            }
//        baseViewHolder.addOnClickListener(R.id.withdrawal);
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }
}
