package com.rebater.cash.well.fun.bean;

public class BusSendEvent<T,Integer> {
    private T data;
    private Integer msg;
    public BusSendEvent(Integer msgs, T datas){
        msg = msgs;
        data = datas;
    }

    public T getData() {
        return data;
    }

    public Integer getMsg() {
        return msg;
    }
}