package com.rebater.cash.well.fun.match;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.WithdrawalPanel;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HappyAdapter extends BaseQuickAdapter<WithdrawalPanel.PayDetail, BaseViewHolder> {
    Context mContext;
    int myRates;

    public HappyAdapter(Context context, int layoutResId, @Nullable List<WithdrawalPanel.PayDetail> data, int rate) {
        super(layoutResId, data);
        mContext = context;
        myRates = rate;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, WithdrawalPanel.PayDetail payInfo) {
        try {
            int account = (int) payInfo.cash_amount;
            int wrath = account * myRates;
            float rates = SelfPrefrence.INSTANCE.getFloat(SelfValue.RATEMONEY, 1);
            String kind = SelfPrefrence.INSTANCE.getString(SelfValue.CRRENCY, "USD");
            int money = (int) (account * rates);
            LogInfo.e(wrath + "--" + rates + kind);
            if (kind.equalsIgnoreCase("RP")) {
                baseViewHolder.setText(R.id.number, "Rp" + money);//RP USD
            } else {
                baseViewHolder.setText(R.id.number, "$" + money);//RP USD
            }
            baseViewHolder.setText(R.id.points, "" + wrath);
            String payNames = SelfPrefrence.INSTANCE.getString(SelfValue.PAYNAME, "Paypal");
            baseViewHolder.setBackgroundRes(R.id.img_ways, R.drawable.paypal);
            int points = Integer.valueOf(SelfPrefrence.INSTANCE.getString(SelfValue.WALLET_ACCOUNT, "1000"));
            ProgressBar progressBar = baseViewHolder.getView(R.id.progress_way);
            progressBar.setMax(wrath);
            progressBar.setProgress(points);

        } catch (Exception e) {
            LogInfo.e(e.toString());
        }
    }

}