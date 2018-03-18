package com.gsy.base.web.dto;

/**
 * Created by mrzsh on 2018/2/8.
 */
public class InvitorCountDTO {
    private int count;

    private int needInvitor;

    public InvitorCountDTO(int count, int needInvitor) {
        this.count = count;
        this.needInvitor = needInvitor;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNeedInvitor() {
        return needInvitor;
    }

    public void setNeedInvitor(int needInvitor) {
        this.needInvitor = needInvitor;
    }
}
