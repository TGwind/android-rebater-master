package com.rebater.cash.well.fun.match;//package com.amusing.cash.well.fun.match;
//
//import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.widget.ImageView;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.amusing.cash.well.fun.R;
//import com.amusing.cash.well.fun.bean.SimpleInfo;
//import com.amusing.cash.well.fun.util.Glidetap;
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.Priority;
//import com.bumptech.glide.request.RequestOptions;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//
//import java.util.List;
//
//public class TungoAdapter extends BaseQuickAdapter<SimpleInfo, BaseViewHolder> {
//    Context mContext;
//    public TungoAdapter(Context context, int layoutResId, @Nullable List<SimpleInfo> data){
//        super(layoutResId,data);
//        mContext=context;
//    }
//
//    @Override
//    protected void convert(@NonNull BaseViewHolder baseViewHolder, SimpleInfo offerInfo){
//        baseViewHolder.setText(R.id.pay_text,offerInfo.name);
//        RequestOptions option=new RequestOptions()
//                .centerCrop()
//                .error(R.drawable.locol_icon)
//                .priority(Priority.HIGH);
//        String url=offerInfo.icon;
//        if(!TextUtils.isEmpty(url)&&!url.equals("null")){
//            Glide.with(mContext).load(url)
//                    .apply(option).transition(withCrossFade())
//                    .transform(new Glidetap(8,1))
//                    .into((ImageView)baseViewHolder.getView(R.id.pay_ways));
//        }
//    }
//}
