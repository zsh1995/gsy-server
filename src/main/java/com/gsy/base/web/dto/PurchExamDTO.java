package com.gsy.base.web.dto;

/**
 * Created by mrzsh on 2017/11/15.
 */
public class PurchExamDTO {

    private long id;

    private long productId;

    private int star;

    private int avaliableTimes;

    public int getAvaliableTimes() {
        return avaliableTimes;
    }

    public void setAvaliableTimes(int avaliableTimes) {
        this.avaliableTimes = avaliableTimes;
    }

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
}
