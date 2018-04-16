package com.gsy.base.web.services.impl;

import com.gsy.base.web.dao.SchoolDAO;
import com.gsy.base.web.entity.SchoolEntity;
import com.gsy.base.web.services.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mrzsh on 2018/4/12.
 */
@Service
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    private SchoolDAO schoolDAO;

    @Override
    public List<SchoolEntity> getAllListPage(int page, int count) {
        return schoolDAO.selectAllSchool(page,count);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public List<SchoolEntity> searchPage(String name, int page, int count) {
        return schoolDAO.selectAllSchoolByName(name,page,count);
    }

    @Override
    public int hitCount(String name) {
        return 0;
    }
}
