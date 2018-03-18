package com.gsy.base.web.dto;

/**
 * Created by mrzsh on 2017/11/14.
 */
public class PurchAnalyseDTO {

    private long id;

    private long productId;

    private int star;

    private int questionId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
