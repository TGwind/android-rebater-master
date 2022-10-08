package com.rebater.cash.well.fun.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class AdvertiBean {
    @Id(autoincrement = true)
    private Long id;
    private String packageName;
    private int adId;
    private boolean isUp;
    private long createTime;
    private long changeTime;
    @Generated(hash = 829649892)
    public AdvertiBean(Long id, String packageName, int adId, boolean isUp,
            long createTime, long changeTime) {
        this.id = id;
        this.packageName = packageName;
        this.adId = adId;
        this.isUp = isUp;
        this.createTime = createTime;
        this.changeTime = changeTime;
    }
    @Generated(hash = 671044273)
    public AdvertiBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPackageName() {
        return this.packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public int getAdId() {
        return this.adId;
    }
    public void setAdId(int adId) {
        this.adId = adId;
    }
    public boolean getIsUp() {
        return this.isUp;
    }
    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getChangeTime() {
        return this.changeTime;
    }
    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }
}
