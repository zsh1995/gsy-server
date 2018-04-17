package com.gsy.base.web.dto;

import java.io.Serializable;

/**
 * Created by mrzsh on 2018/4/16.
 */
public class RedisItemDTO <T> implements Serializable{

    private T item;
    private long createTime = System.nanoTime();

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public RedisItemDTO(T item){
        this.item = item;
    }

    public RedisItemDTO(){}
}
