package com.rebater.cash.well.fun.match;

import android.content.Context;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Qiest;
import com.rebater.cash.well.fun.util.LogInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ProgressAdapter extends BaseQuickAdapter<Qiest, BaseViewHolder> {
    Context mContext;

    public int getmSteps() {
        return mSteps;
    }

    public void setmSteps(int mSteps) {
        this.mSteps = mSteps;
    }

    int mSteps;

    //    long time;
    public ProgressAdapter(Context context, int layoutResId, @Nullable List<Qiest> data, int step) {
        super(layoutResId, data);
        mContext = context;
        mSteps = step;
//        time=times;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Qiest offerInfo) {
        try {
            int crrentPosition = baseViewHolder.getLayoutPosition();
            int position = baseViewHolder.getLayoutPosition() + 1;
            baseViewHolder.setText(R.id.title, position + "." + offerInfo.title);
            baseViewHolder.setText(R.id.content, offerInfo.desc);
            int status = offerInfo.status;
            int gapday = offerInfo.step_day;
            int play_time = offerInfo.time * 60;
            long lastTime = offerInfo.lastTimes;
            int doingtime = offerInfo.doingtime;
            int finishTime = 0;
            if (doingtime != 0) {
                finishTime = doingtime / 60;
            }
            LogInfo.e(mSteps+"time==" + finishTime + "--do--" + doingtime + "--play-" + play_time);
            if (status == 1) {
                baseViewHolder.setText(R.id.status, mContext.getString(R.string.complete));
                baseViewHolder.setVisible(R.id.status, true);
                baseViewHolder.setVisible(R.id.status_img, false);
                baseViewHolder.setGone(R.id.progress_layout, false);
                baseViewHolder.setTextColor(R.id.status, mContext.getResources().getColor(R.color.task_grep));
                baseViewHolder.setBackgroundRes(R.id.item_steps, R.drawable.dra_points);

            } else {
                if (mSteps == 99) {
                    if (crrentPosition == 0) {
                        baseViewHolder.setVisible(R.id.status, true);
                        baseViewHolder.setVisible(R.id.status_img, false);
                        baseViewHolder.setBackgroundRes(R.id.item_steps, R.drawable.dra_select);
                        baseViewHolder.setText(R.id.status, mContext.getString(R.string.progress));
                        baseViewHolder.setTextColor(R.id.status, mContext.getResources().getColor(R.color.text_yellow));
                        if (play_time > 0) {
                            baseViewHolder.setVisible(R.id.progress_layout, true);
                            baseViewHolder.setText(R.id.minuets_used, finishTime + " " + mContext.getString(R.string.minutes));
                            ProgressBar progressBar = baseViewHolder.getView(R.id.progress_cr);
                            progressBar.setMax(play_time);
                            progressBar.setProgress(doingtime);
                        } else {
//                            baseViewHolder.setVisible(R.id.progress_layout, false);
                            baseViewHolder.setGone(R.id.progress_layout, false);

                        }
                    } else {
                        baseViewHolder.setText(R.id.status, mContext.getString(R.string.locked));
                        baseViewHolder.setVisible(R.id.status, false);
                        baseViewHolder.setVisible(R.id.status_img, true);
//                        baseViewHolder.setVisible(R.id.progress_layout, false);
//                        baseViewHolder.setGone(R.id.progress_layout, true);
                        baseViewHolder.setTextColor(R.id.status, mContext.getResources().getColor(R.color.white));
//                        baseViewHolder.setVisible(R.id.progress_layout, false);
                        baseViewHolder.setGone(R.id.progress_layout, false);

                    }

                } else {
                    if (mSteps == crrentPosition) {
                        if (System.currentTimeMillis() / 1000 - lastTime >= gapday * 24 * 60 * 60) {
                            baseViewHolder.setVisible(R.id.status, true);
                            baseViewHolder.setVisible(R.id.status_img, false);
                            baseViewHolder.setBackgroundRes(R.id.item_steps, R.drawable.dra_select);
                            baseViewHolder.setText(R.id.status, mContext.getString(R.string.progress));
                            baseViewHolder.setTextColor(R.id.status, mContext.getResources().getColor(R.color.text_yellow));
                            LogInfo.e("s-"+mSteps);
                            if (play_time > 0) {
                                baseViewHolder.setVisible(R.id.progress_layout, true);
                                baseViewHolder.setText(R.id.minuets_used, finishTime + " " + mContext.getString(R.string.minutes));
                                ProgressBar progressBar = baseViewHolder.getView(R.id.progress_cr);
                                progressBar.setMax(play_time);
                                progressBar.setProgress(doingtime);
                            } else {
//                            baseViewHolder.setVisible(R.id.progress_layout, false);
                                baseViewHolder.setGone(R.id.progress_layout, false);

                            }
                        } else {
                            baseViewHolder.setText(R.id.status, mContext.getString(R.string.locked));
                            baseViewHolder.setVisible(R.id.status, false);
                            baseViewHolder.setVisible(R.id.status_img, true);
//                        baseViewHolder.setVisible(R.id.progress_layout, false);
                            baseViewHolder.setTextColor(R.id.status, mContext.getResources().getColor(R.color.white));
//                        baseViewHolder.setVisible(R.id.progress_layout, false);
                            baseViewHolder.setGone(R.id.progress_layout, false);
                            LogInfo.e("s-"+mSteps);

                        }
                    }else{
                        baseViewHolder.setText(R.id.status, mContext.getString(R.string.locked));
                        baseViewHolder.setVisible(R.id.status, false);
                        baseViewHolder.setVisible(R.id.status_img, true);
//                        baseViewHolder.setVisible(R.id.progress_layout, false);
                        baseViewHolder.setTextColor(R.id.status, mContext.getResources().getColor(R.color.white));
//                        baseViewHolder.setVisible(R.id.progress_layout, false);
                        baseViewHolder.setGone(R.id.progress_layout, false);
                        LogInfo.e("s-"+mSteps);

                    }
                }

            }
            baseViewHolder.setText(R.id.points, "+" + offerInfo.award);
        } catch (Exception e) {
            LogInfo.e(e.toString());
        }

    }
}
