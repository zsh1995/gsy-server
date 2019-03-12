package com.gsy.base.common;


import com.gsy.base.web.dto.OrderInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Properties;

/**
 * Created by mrzsh on 2017/11/13.
 */
@Deprecated
@Component
public class PayConstanst {

    @Autowired
    private PayConfig payConfig;

    public static final String payConfigFile = "/config/pay-config.properties";

    private static volatile PayConstanst payConstanst;
    private Properties properties;

    private String prePayURL ;

    public String getPrePayURL(){
        return prePayURL;
    }

    public OrderInfoDTO loadInitData(){
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setAppid(payConfig.getAppid());
        orderInfoDTO.setMch_id(payConfig.getMch_id());
        orderInfoDTO.setNotify_url(payConfig.getNotify_url());
        orderInfoDTO.setSpbill_create_ip(payConfig.getSpbill_create_ip());
        orderInfoDTO.setTrade_type(payConfig.getTrade_type());
        orderInfoDTO.setSign_type(payConfig.getSign_type());
        prePayURL = payConfig.getPrePayUrl();
        return orderInfoDTO;
    }


}
