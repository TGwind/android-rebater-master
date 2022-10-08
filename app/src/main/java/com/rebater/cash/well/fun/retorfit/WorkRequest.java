package com.rebater.cash.well.fun.retorfit;

import android.os.Environment;
import android.text.TextUtils;

import com.rebater.cash.well.fun.essential.IRetrofit;
import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.rebater.cash.well.fun.util.pre.SelfPrefrence;
import com.rebater.cash.well.fun.util.pre.SelfValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class WorkRequest {
    private static IRetrofit allApI;
    private static OkHttpClient okHttpClient;
    private static RxJava2CallAdapterFactory rxJava2CallAdapterFactory;

    private static final long cacheSize = 1024 * 1024 * 15;// 缓存文件最大限制大小20M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/aop"; // 设置缓存文件路径
    private static Cache cache = new Cache(new File(cacheDirectory),cacheSize);

    //uuid
    public static byte[] args1={0x75, 0x75, 0x69, 0x64};
    private static OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);  //设置连接超时时间
        builder.writeTimeout(30,TimeUnit.SECONDS);  //设置写入超时时间
        builder.readTimeout(30,TimeUnit.SECONDS);  //设置读取超时时间
        builder.retryOnConnectionFailure(true);   //设置进行连接失败重试
        builder.addNetworkInterceptor(getInterceptor()); //拦截器
        if (Constans.ISDEBUG){
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)); //拦截器
        }
        builder.dispatcher(getdispatcher());  //调度器
        builder.cache(cache); //设置缓存
        return builder.build();
    }

    public static IRetrofit getAllface(){
        if(allApI == null){
            if(okHttpClient == null){
                okHttpClient=getOkHttpClient(); //创建OkhttpClient
            }
            if (rxJava2CallAdapterFactory==null){
                rxJava2CallAdapterFactory=RxJava2CallAdapterFactory.create();
            }
            Retrofit retrofit = new Retrofit.Builder() //创建Retrofit实例
                    .client(okHttpClient)
                    .baseUrl(Constans.BASE_URL)
                    .addConverterFactory(CoverJson.create()) //设置数据解析器
                    .addCallAdapterFactory(rxJava2CallAdapterFactory)  //？
                    .build();
            allApI = retrofit.create(IRetrofit.class); //创建网络接口对象实例
        }
        return allApI;
    }

    private  static Dispatcher getdispatcher(){
        Dispatcher dispatcher=new Dispatcher();
        dispatcher.setMaxRequests(3);//设置相同域名最大并发数
        return  dispatcher;
    }

    //gaid
    public static byte[] args2={ 0x67, 0x61, 0x69, 0x64 };
    //channel
    public static byte[] args3={ 0x63, 0x68, 0x61, 0x6E, 0x6E, 0x65, 0x6C };
    //version
    public static byte[] args4={ 0x76, 0x65, 0x72, 0x73, 0x69, 0x6F, 0x6E };
    //pkg
    public static byte[] args5={ 0x70, 0x6B, 0x67 };
    //isnet
    public static byte[] args6={ 0x69, 0x73, 0x6E, 0x65, 0x74 };
    //level_id
    public static byte[] args7={ 0x6C, 0x65, 0x76, 0x65, 0x6C, 0x5F, 0x69, 0x64 };
    //versioncode
    public static byte[] args8={ 0x76, 0x65, 0x72, 0x73, 0x69, 0x6F, 0x6E, 0x63, 0x6F, 0x64, 0x65 };
    //request_time
    public static byte[] args9={ 0x72, 0x65, 0x71, 0x75, 0x65, 0x73, 0x74, 0x5F, 0x74, 0x69, 0x6D, 0x65 };
    //net_type
    public static byte[] args10={ 0x6E, 0x65, 0x74, 0x5F, 0x74, 0x79, 0x70, 0x65 };

    private static Interceptor getInterceptor() {  //拦截器
        Interceptor interceptors = new Interceptor() {
            Charset UTF8 = StandardCharsets.UTF_8;
            @Override
            public Response intercept(Chain chain) throws IOException {
                String gaid = SelfPrefrence.INSTANCE.getString(SelfValue.GAID,"");
                if (TextUtils.isEmpty(gaid)||gaid.contains("00000000")){
                    gaid=SelfPrefrence.INSTANCE.getString(SelfValue.ANDEIS,"")+Constans.packageName;
                }
                String net=SelfPrefrence.INSTANCE.getString(SelfValue.ISVPSNXCS,"0");
                String netTYPE=SelfPrefrence.INSTANCE.getString(SelfValue.NETSTATUS,"0");
                LogInfo.e("--"+net+"--"+gaid+"--"+netTYPE);
                Request request = chain.request();
                Request newrequest = request.newBuilder()
//                    .header("accept", "application/json")"*/*"
                        .header("Content-Type", "ext/plain")
                        .header(new String(args2),gaid )
                        .header(new String(args1), SelfPrefrence.INSTANCE.getString(SelfValue.USER_ID,""))
                        .header(new String(args4), Constans.VERSION)
                        .header(new String(args3),  SelfPrefrence.INSTANCE.getString(SelfValue.CHANNEL,Constans.CHANNEL))
                        .header(new String(args5), Constans.pkg)
                        .header(new String(args6), net)
                        .header(new String(args7), SelfPrefrence.INSTANCE.getString(SelfValue.USER_level,"1"))
                        .header(new String(args8),Constans.VERSIONNAME)
                        .header(new String(args9),""+(System.currentTimeMillis()))
                        .header(new String(args10),netTYPE)
                        .method(request.method(), request.body())
                        .build();
                Response response = chain.proceed(newrequest);
                return  response;
            }
        };
        return interceptors;
    }
}
