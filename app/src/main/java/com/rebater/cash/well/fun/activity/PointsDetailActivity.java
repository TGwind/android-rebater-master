package com.rebater.cash.well.fun.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rebater.cash.well.fun.R;
import com.rebater.cash.well.fun.bean.Currency;
import com.rebater.cash.well.fun.common.OverActivity;
import com.rebater.cash.well.fun.essential.IModel;
import com.rebater.cash.well.fun.essential.OverPresent;
import com.rebater.cash.well.fun.match.CoinsAdapter;
import com.rebater.cash.well.fun.obb.ItemGap;
import com.rebater.cash.well.fun.obb.MyLoadMoreView;
import com.rebater.cash.well.fun.present.QuestionPresent;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ViewTools;

import java.util.List;

import butterknife.BindView;
//points Detail
public class PointsDetailActivity extends OverActivity implements IModel.BackView {

    @BindView(R.id.backgo)
    ImageView backgo;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.details_record)
    RecyclerView recyclerView;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swipeRefreshLayout;
    QuestionPresent questionPresent;
    CoinsAdapter coinsAdapter;
    int allPage;
    int page=0;
    int count=0;
    int offset;

    @Override
    protected void initContentView() {
        setContentView(R.layout.activity_points_detail);
    }

    public static void startActivity(Context context) {
        context.startActivity(intentof(context));
    }

    private static Intent intentof(Context context) {
        Intent intent = new Intent(context, PointsDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }
    Handler handler=new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==0x12){
                if(coinsAdapter !=null){
                    coinsAdapter.loadMoreEnd();
                }
                if(swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }
    };
    @Override
    protected void initView() {
        page=0;
        title.setText(R.string.point_details);
        questionPresent = (QuestionPresent) getModel();
        swipeRefreshLayout.setColorSchemeResources(R.color.circle);
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.white));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page++;
            offset=page*allPage;
            if(offset<=count){
                questionPresent.getDetails(offset);
            }else{
//                showToast(R.string.no_more);
                handler.sendEmptyMessage(0x12);
            }
        });
        backgo.setOnClickListener(v -> {
            if (ViewTools.isFastDoubleClick(R.id.backgo)){
                return;
            }
            finish();
        });
        questionPresent.getDetails(0);
    }

    @Override
    protected OverPresent initModel() {
        return new QuestionPresent(this);
    }


    @Override
    public void onFinish(String msg) {

    }

    @Override
    public void onDetails(Currency currency) {
        try{
            stopRefresh();
            List<Currency.Details> list= currency.list;
            allPage= currency.limit;
            count= currency.count;
            if(list!=null&&list.size()>0){
                if(coinsAdapter ==null){
                    coinsAdapter =new CoinsAdapter(this,R.layout.item_record,list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    coinsAdapter.setLoadMoreView(new MyLoadMoreView());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.addItemDecoration(new ItemGap(20, 4));
                    recyclerView.setAdapter(coinsAdapter);
                    coinsAdapter.setOnLoadMoreListener(() -> {
                        page++;
                        offset=page*allPage;
                        if(offset<=count){
                            questionPresent.getDetails(offset);
                        }else{
                            showToast(R.string.no_more);
                            handler.sendEmptyMessage(0x12);
                        }
                    });
                }else{
                    coinsAdapter.addData(list);
//                    recyclerView.notifyAll();
                }
            }
        }catch (Exception e){
            LogInfo.e(e.toString());
        }
    }

    private  void stopRefresh(){
        if(swipeRefreshLayout.isRefreshing()){
            swipeRefreshLayout.setRefreshing(false);
        }
        if(coinsAdapter !=null){
            coinsAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onError() {
        showToast(R.string.no_more);
        stopRefresh();
    }
}