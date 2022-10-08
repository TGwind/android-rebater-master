package com.rebater.cash.well.fun.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class LogEvent {
    @Id(autoincrement = true)
    private Long id;
    private String packageName;
    private int OfferId;
    private boolean isUp;
    private long createTime;
    private long changeTime;
    private String eventName;
    private String country;
    private String  templateId;
    private String  position;
    private  int type;
    private int page ;
    private int step;
    @Generated(hash = 578752859)
    public LogEvent(Long id, String packageName, int OfferId, boolean isUp,
            long createTime, long changeTime, String eventName, String country,
            String templateId, String position, int type, int page, int step) {
        this.id = id;
        this.packageName = packageName;
        this.OfferId = OfferId;
        this.isUp = isUp;
        this.createTime = createTime;
        this.changeTime = changeTime;
        this.eventName = eventName;
        this.country = country;
        this.templateId = templateId;
        this.position = position;
        this.type = type;
        this.page = page;
        this.step = step;
    }
    @Generated(hash = 2029889469)
    public LogEvent() {
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
    public int getOfferId() {
        return this.OfferId;
    }
    public void setOfferId(int OfferId) {
        this.OfferId = OfferId;
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
    public String getEventName() {
        return this.eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public String getCountry() {
        return this.country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getTemplateId() {
        return this.templateId;
    }
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    public String getPosition() {
        return this.position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getPage() {
        return this.page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getStep() {
        return this.step;
    }
    public void setStep(int step) {
        this.step = step;
    }
}
