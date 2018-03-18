package com.gsy.base.common;


import com.gsy.base.web.dto.OrderInfoDTO;

import java.io.*;
import java.util.Properties;

/**
 * Created by mrzsh on 2017/11/13.
 */
public class PayConstanst {

    public static final String payConfigFile = "/pay-config.properties";

    private static PayConstanst payConstanst;
    private Properties properties;

    private String prePayURL ;

    public String getPrePayURL(){
        return prePayURL;
    }

    public static PayConstanst getInstance() throws IOException {
        if (payConstanst==null){
            payConstanst =  new PayConstanst();
            payConstanst.initial();
        }
        return payConstanst;
    }

    public void initial() throws IOException {
        String prefix = PayConstanst.class.getClassLoader().getResource("").getPath();
        properties = new Properties();
        try {
            InputStream is = new FileInputStream(prefix+File.separator+payConfigFile);
            properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public OrderInfoDTO loadInitData(){
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setAppid(properties.getProperty("app_id"));
        orderInfoDTO.setMch_id(properties.getProperty("mch_id"));
        orderInfoDTO.setNotify_url(properties.getProperty("notify_url"));
        orderInfoDTO.setSpbill_create_ip(properties.getProperty("spbill_create_ip"));
        orderInfoDTO.setTrade_type(properties.getProperty("trade_type"));
        orderInfoDTO.setSign_type(properties.getProperty("sign_type"));
        prePayURL = properties.getProperty("prePayUrl");
        return orderInfoDTO;
    }


}
