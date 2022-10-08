package com.rebater.cash.well.fun.retorfit;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class AskJson implements Converter<Object, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType
            .parse("application/json; charset=UTF-8");
    static final Charset UTF_8 = Charset.forName("UTF-8");

    final Gson gson;
    final TypeAdapter adapter;

    @Override
    public RequestBody convert(Object value) throws IOException {
        //将传入的实体类先转成json，再进行加密
        String json = gson.toJson(value);
        return RequestBody.create(MEDIA_TYPE, json);
    }
    public AskJson(Gson gson, TypeAdapter adaptert) {
        this.gson = gson;
        this.adapter = adaptert;
//        System.out.println("#IRequestBodyConverter初始化#");
    }
}
