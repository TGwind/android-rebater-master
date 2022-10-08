package com.rebater.cash.well.fun.bean;

import java.util.List;

public class Records {
    public List<RecordDetails> list;
    public int count,limit,offset;
    public class RecordDetails{
        public String pay_images,cash_amount,receipt_email,adtime,unit,status;
    }
}
