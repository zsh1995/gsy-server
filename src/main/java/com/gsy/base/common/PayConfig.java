package com.gsy.base.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @program: gsyboot
 * @description:
 * @author: Mr.zsh
 * @create: 2019-03-11 20:04
 **/
@Configuration
@PropertySource("classpath:config/pay-config.properties")
@ConfigurationProperties(prefix="pay")
public class PayConfig {

    private String appid;// 小程序ID

    private String mch_id;// 商户号

    private String sign_type;//签名类型

    private String spbill_create_ip;// 终端IP

    private String notify_url;// 通知地址

    private String trade_type;// 交易类型

    private String prePayUrl;

    public String getPrePayUrl() {
        return prePayUrl;
    }

    public void setPrePayUrl(String prePayUrl) {
        this.prePayUrl = prePayUrl;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
}
