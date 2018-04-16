package com.gsy.base.web.entity.merge;


import java.sql.Date;

/**
 * Created by Administrator on 2017/8/25.
 */
public class UserCoupon {

    private int couponId;

    private int usedFlag;

    private int remainTimes;

    private int source;

    private Date createTime;

    public int getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(int usedFlag) {
        this.usedFlag = usedFlag;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getRemainTimes() {
        return remainTimes;
    }

    public void setRemainTimes(int remainTimes) {
        this.remainTimes = remainTimes;
    }
}
