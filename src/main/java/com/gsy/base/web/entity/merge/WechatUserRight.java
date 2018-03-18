package com.gsy.base.web.entity.merge;

public class WechatUserRight {
  private Long id;
  private String openId;
  private String type;
  private Integer questionId;
  private Integer star;
  private Integer passExam;
  private Integer remainTimes;
  private String createTime;

  public Integer getPassExam() {
    return passExam;
  }

  public void setPassExam(Integer passExam) {
    this.passExam = passExam;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
  }

  public Integer getStar() {
    return star;
  }

  public void setStar(Integer star) {
    this.star = star;
  }

  public Integer getRemainTimes() {
    return remainTimes;
  }

  public void setRemainTimes(Integer remainTimes) {
    this.remainTimes = remainTimes;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }
}
