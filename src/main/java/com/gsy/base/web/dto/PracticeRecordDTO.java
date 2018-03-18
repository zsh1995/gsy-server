package com.gsy.base.web.dto;

/**
 * Created by Administrator on 2017/6/18.
 */
public class PracticeRecordDTO {

    private Integer userId;

    private Double score;

    private Integer questionGroup;

    private Integer stars;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
