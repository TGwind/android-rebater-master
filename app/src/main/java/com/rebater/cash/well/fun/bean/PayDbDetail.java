package com.rebater.cash.well.fun.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class PayDbDetail {
    @Id(autoincrement = true)
    private Long id;
    private String title, desc, icon;
    private int payId;
    private int pay_type;
    private int conversion;
    private float cash_amount;
    private long createTime;
    private String date;

    @Generated(hash = 836477928)
    public PayDbDetail(Long id, String title, String desc, String icon, int payId,
                       int pay_type, int conversion, float cash_amount, long createTime,
                       String date) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.payId = payId;
        this.pay_type = pay_type;
        this.conversion = conversion;
        this.cash_amount = cash_amount;
        this.createTime = createTime;
        this.date = date;
    }

    @Generated(hash = 858600268)
    public PayDbDetail() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getPayId() {
        return this.payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getPay_type() {
        return this.pay_type;
    }

    public void setPay_type(int pay_type) {
        this.pay_type = pay_type;
    }

    public int getConversion() {
        return this.conversion;
    }

    public void setConversion(int conversion) {
        this.conversion = conversion;
    }

    public float getCash_amount() {
        return this.cash_amount;
    }

    public void setCash_amount(float cash_amount) {
        this.cash_amount = cash_amount;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
