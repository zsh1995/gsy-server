package com.gsy.base.web.dto;

/**
 * Created by Administrator on 2017/6/18.
 */
public class PracticeRecordDTO {

    private long userId;

    private Double score;

    private Integer questionGroup;

    private Integer stars;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getQuestionGroup() {
        return questionGroup;
    }

    public void setQuestionGroup(Integer questionGroup) {
        this.questionGroup = questionGroup;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
