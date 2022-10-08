package com.rebater.cash.well.fun.util.pre;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public enum SelfPrefrence {
    INSTANCE;

    private static final String PREF_FILE_NAME = "fun_prefs";
    private SharedPreferences sharedPreferences;

    public void init(Context context) {
        this.sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, 0);  //mode为0 配置文件只能被自己的应用程序访问
    }

    public void clear() {
        this.sharedPreferences.edit().clear().apply();
    }

    public void setString(String key, String value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void removeString(String key) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
        /*SharedPreference 相关修改使用 apply 方法进行提交会先写入内存，然后异步写入磁盘，commit
方法是直接写入磁盘。如果频繁操作的话 apply 的性能会优于 commit，apply会将最后修改内容写入磁盘。
但是如果希望立刻获取存储操作的结果，并据此做相应的其他操作，应当使用 commit*/
    }

    public void setStringSet(String key, Set<String> stringSet) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putStringSet(key, stringSet);
        editor.apply();
    }

    public void setInt(String key, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void setLong(String key, long value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public void setFloat(String key, float value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public float getFloat(String key, float defaultValue) {
        return this.sharedPreferences.getFloat(key, defaultValue);
    }

    public String getString(String key, String defaultValue) {
        return this.sharedPreferences.getString(key, defaultValue);
    }

    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return this.sharedPreferences.getStringSet(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return this.sharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return this.sharedPreferences.getLong(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

}
