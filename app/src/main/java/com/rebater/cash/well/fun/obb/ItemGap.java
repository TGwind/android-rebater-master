package com.rebater.cash.well.fun.obb;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemGap extends RecyclerView.ItemDecoration {
    //定义RecycleView的每个item 的间隔样式
    private int spaceTB;
    private int spaceLR;

    public ItemGap(int t, int r) {
        spaceTB = t;
        spaceLR = r;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = spaceLR;
        outRect.right = spaceLR;
        outRect.bottom = spaceTB;
        outRect.top = spaceTB;
    }
}