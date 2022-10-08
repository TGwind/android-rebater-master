package com.rebater.cash.well.fun.obb;

import com.rebater.cash.well.fun.R;
import com.chad.library.adapter.base.loadmore.LoadMoreView;

public class MyLoadMoreView extends LoadMoreView {
    //定义RecycleView的下拉刷新样式
    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

}
