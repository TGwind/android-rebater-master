package com.rebater.cash.well.fun.retorfit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.rebater.cash.well.fun.match.DoubleAdapter;
import com.rebater.cash.well.fun.match.IntegerAdapter;
import com.rebater.cash.well.fun.match.LongAdapter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class CoverJson extends Converter.Factory {  //json数据解析器
    private static boolean isEncrypt = true; //？

    public static CoverJson create() {
        return create(new GsonBuilder().setLenient()
                .registerTypeAdapter(Integer.class, new IntegerAdapter())
                .registerTypeAdapter(int.class, new IntegerAdapter())
                .registerTypeAdapter(Double.class, new DoubleAdapter())
                .registerTypeAdapter(double.class, new DoubleAdapter())
                .registerTypeAdapter(Long.class, new LongAdapter())
                .disableHtmlEscaping().create()
        );
    }

    public static CoverJson create(boolean encrypt){ //加密
        isEncrypt = encrypt;
        return create(new GsonBuilder().setLenient().disableHtmlEscaping().create());
    }

    public static CoverJson create(Gson gson){
        if (gson == null)
            throw new NullPointerException("gson == null");
            return new CoverJson(gson);
    }
    private final Gson gson;
    private CoverJson(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ReturnJson<>(gson, adapter); //响应
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new AskJson(gson,adapter);
    }
}
