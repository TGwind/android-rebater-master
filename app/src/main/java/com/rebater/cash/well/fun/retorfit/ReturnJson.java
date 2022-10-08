package com.rebater.cash.well.fun.retorfit;

import android.text.TextUtils;

import com.rebater.cash.well.fun.util.LogInfo;
import com.rebater.cash.well.fun.util.ProjectTools;
import com.rebater.cash.well.fun.util.pre.Constans;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import org.json.JSONObject;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ReturnJson<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    public ReturnJson(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        jsonReader.setLenient(true);
        String string = value.string();
        String normal = string;
//        LogUtils.e("#解密前@#" + string);
        try {
            if (ProjectTools.getJSONType(string)) {
                return adapter.fromJson(new JSONObject(normal).toString());
            }
            String desString = ProjectTools.getCode(string);
            if (Constans.ISDEBUG) {
                if (desString.length() > 4000) {
                    for (int i = 0; i < desString.length(); i += 4000) {
                        if (i + 4000 < desString.length()) {
                            LogInfo.e(desString.substring(i, i + 4000));

                        } else {
                            LogInfo.e(desString.substring(i));

                        }
                    }
                } else {
                    LogInfo.e(desString);
                }
            }

            if (!TextUtils.isEmpty(desString)) {
                JSONObject jsonObject = new JSONObject(desString);
                String datalog = jsonObject.optString("data");//[]
                if (jsonObject.has("status") && jsonObject.optInt("status") == 1) {
                    return adapter.fromJson(desString);
                } else if (jsonObject.has("data") && !TextUtils.isEmpty(datalog) && datalog.equals("[]")) {
                    return adapter.fromJson(desString);
                } else {
                    return adapter.fromJson(jsonObject.optString("data"));

                }
            }
        } catch (Exception e) {
            LogInfo.e("er" + e.getMessage());
            string = "";
        }

        return adapter.fromJson(normal);
    }
}