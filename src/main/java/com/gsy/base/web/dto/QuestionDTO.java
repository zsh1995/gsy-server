package com.gsy.base.web.dto;

import java.util.List;

/**
 * Created by Administrator on 2017/6/3.
 */
public class QuestionDTO {
    private Integer questionId;

    private Integer isPurchAnalyse;

    private String content;

    private List<OptionsDTO> options;

    private String type;

    private String tips;

    private String analysis;

    public Integer getIsPurchAnalyse() {
        return isPurchAnalyse;
    }

    public void setIsPurchAnalyse(Integer isPurchAnalyse) {
        this.isPurchAnalyse = isPurchAnalyse;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<OptionsDTO> getOptions() {
        return options;
    }

    public void setOptions(List<OptionsDTO> options) {
        this.options = options;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

}
