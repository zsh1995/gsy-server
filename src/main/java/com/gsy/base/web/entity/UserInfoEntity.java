package com.gsy.base.web.entity;

import com.qcloud.weapp.authorization.UserInfo;

/**
 * Created by mrzsh on 2018/2/5.
 */
public class UserInfoEntity extends BaseEntity{
    private String openId;
    private String nickName;
    private String avatarUrl;
    private Integer gender;
    private String language;
    private String city;
    private String province;
    private String country;
    public UserInfoEntity(){}

    public UserInfoEntity(UserInfo userInfo){
        this.openId = userInfo.getOpenId();
        this.nickName = userInfo.getNickName();
        this.avatarUrl = userInfo.getNickName();
        this.gender = userInfo.getGender();
        this.language = userInfo.getLanguage();
        this.city = userInfo.getCity();
        this.province = userInfo.getProvince();
        this.country = userInfo.getCountry();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
