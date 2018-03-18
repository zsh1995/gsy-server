package com.gsy.base.web.entity.merge;

/**
 * Created by Administrator on 2017/8/23.
 */
public class Coupon {
    private int id;

    private String codeName;

    private String describe;

    private String createTime;

    private String validTime;

    private String unvalidTime;

    private String avalTimes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public String getUnvalidTime() {
        return unvalidTime;
    }

    public void setUnvalidTime(String unvalidTime) {
        this.unvalidTime = unvalidTime;
    }

    public String getAvalTimes() {
        return avalTimes;
    }

    public void setAvalTimes(String avalTimes) {
        this.avalTimes = avalTimes;
    }
}
