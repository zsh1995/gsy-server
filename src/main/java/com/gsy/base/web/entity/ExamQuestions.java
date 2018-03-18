package com.gsy.base.web.entity;


import java.util.List;

/**
 * Created by mrzsh on 2018/1/28.
 */
public class ExamQuestions extends BaseEntity {

    private List questionList;

    private long createTime;

    private long expireTime;

    public List getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List questionList) {
        this.questionList = questionList;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
