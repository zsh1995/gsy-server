package com.gsy.base.web.entity.merge;

public class PurchExamRecord {
  private Long id;
  private String openId;
  private Integer purchStar;
  private Integer remainTimes;
  private Integer transaction;
  private Integer purchQuestionId;
  private String type;
  private Integer deleteFlag;
  private String createDate;
  private String tradeNo;

  public Integer getPurchQuestionId() {
    return purchQuestionId;
  }

  public void setPurchQuestionId(Integer purchQuestionId) {
    this.purchQuestionId = purchQuestionId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public Integer getPurchStar() {
    return purchStar;
  }

  public void setPurchStar(Integer purchStar) {
    this.purchStar = purchStar;
  }

  public Integer getRemainTimes() {
    return remainTimes;
  }

  public void setRemainTimes(Integer remainTimes) {
    this.remainTimes = remainTimes;
  }

  public Integer getTransaction() {
    return transaction;
  }

  public void setTransaction(Integer transaction) {
    this.transaction = transaction;
  }

  public Integer getDeleteFlag() {
    return deleteFlag;
  }

  public void setDeleteFlag(Integer deleteFlag) {
    this.deleteFlag = deleteFlag;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
}
