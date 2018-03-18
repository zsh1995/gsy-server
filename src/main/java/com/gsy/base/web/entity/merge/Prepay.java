package com.gsy.base.web.entity.merge;

/**
 * Created by mrzsh on 2017/11/15.
 */
public class Prepay {
    private long id;
    private String openId;
    private String outTradeNo;
    private long productId;
    private int transactionFlag;

    public Prepay(){

    }
    public Prepay(String openId, String outTradeNo, long productId, int transactionFlag) {
        this.openId = openId;
        this.outTradeNo = outTradeNo;
        this.productId = productId;
        this.transactionFlag = transactionFlag;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getTransactionFlag() {
        return transactionFlag;
    }

    public void setTransactionFlag(int transactionFlag) {
        this.transactionFlag = transactionFlag;
    }
}
