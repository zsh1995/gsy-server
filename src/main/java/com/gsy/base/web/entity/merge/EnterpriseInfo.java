package com.gsy.base.web.entity.merge;

/**
 * Created by mrzsh on 2017/11/3.
 */
public class EnterpriseInfo  {
    private long id;
    private String enterpriseName;
    private int state;
    private int firstIconFlag;
    private int secondIconFlag;
    private int thirdIconFlag;
    private String imgUrl;

    private int[] iconArray;

    public int[] getIconArray() {
        return iconArray;
    }

    public void setIconArray(int[] iconArray) {
        this.iconArray = iconArray;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getFirstIconFlag() {
        return firstIconFlag;
    }

    public void setFirstIconFlag(int firstIconFlag) {
        this.firstIconFlag = firstIconFlag;
    }

    public int getSecondIconFlag() {
        return secondIconFlag;
    }

    public void setSecondIconFlag(int secondIconFlag) {
        this.secondIconFlag = secondIconFlag;
    }

    public int getThirdIconFlag() {
        return thirdIconFlag;
    }

    public void setThirdIconFlag(int thirdIconFlag) {
        this.thirdIconFlag = thirdIconFlag;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
