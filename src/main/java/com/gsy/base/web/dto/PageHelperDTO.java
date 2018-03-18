package com.gsy.base.web.dto;

import com.gsy.base.web.entity.BaseEntity;

import java.util.List;

/**
 * Created by mrzsh on 2018/2/6.
 */
public class PageHelperDTO<T> extends BaseEntity{
    private List<T> list;

    private int allCount;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
}
