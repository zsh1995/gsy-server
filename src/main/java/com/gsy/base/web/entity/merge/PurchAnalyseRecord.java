package com.gsy.base.web.entity.merge;

public class PurchAnalyseRecord {
  private Long id;
  private String openId;
  private Integer purchStar;
  private Integer purchGroup;
  private Integer questionId;
  private Integer transaction;
  private Integer deleteFlag;
  private String createTime;
  private String tradeNo;

  public Integer getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
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

  public Integer getPurchGroup() {
    return purchGroup;
  }

  public void setPurchGroup(Integer purchGroup) {
    this.purchGroup = purchGroup;
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

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
