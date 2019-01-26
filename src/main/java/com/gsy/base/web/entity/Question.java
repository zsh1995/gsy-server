package com.gsy.base.web.entity;

import com.gsy.base.common.exam.ExamHelper;

/**
 * Created by mrzsh on 2018/1/28.
 */
public class Question extends BaseEntity{

    private long id;

    private String content;

    private ExamHelper.ExamType type;

    private String tip;

    private String analysis;

    private int isPurchAnalyse;

    public int getIsPurchAnalyse() {
        return isPurchAnalyse;
    }

    public void setIsPurchAnalyse(int isPurchAnalyse) {
        this.isPurchAnalyse = isPurchAnalyse;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ExamHelper.ExamType getType() {
        return type;
    }

    public void setType(ExamHelper.ExamType type) {
        this.type = type;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
