package com.rebater.cash.well.fun.bean;

import java.util.List;

public class WithdrawalPanel {
    public int conversion;
    public List<PayDetail> list;
    public static class PayDetail{
        public String title,desc,icon;
        public int id;
        public int pay_type;
        public float cash_amount;
    }
}
