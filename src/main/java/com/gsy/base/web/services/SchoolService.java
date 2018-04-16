package com.gsy.base.web.services;

import com.gsy.base.web.entity.SchoolEntity;

import java.util.List;

/**
 * Created by mrzsh on 2018/4/12.
 */
public interface SchoolService {

    public List<SchoolEntity> getAllListPage(int page,int count);

    public int getCount();

    public List<SchoolEntity> searchPage(String name,int page,int count);

    public int hitCount(String name);
}
