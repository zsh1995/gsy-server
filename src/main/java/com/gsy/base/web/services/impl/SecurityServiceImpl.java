package com.gsy.base.web.services.impl;/**
 * Created by mrzsh on 2018/5/16.
 */

import com.gsy.base.common.ApiMethod;
import com.gsy.base.web.dao.CommonDAO;
import com.gsy.base.web.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: gsyboot
 * @description:
 * @author: Mr.zsh
 * @create: 2018-05-16 15:10
 **/
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    CommonDAO commonDAO;

    @Override
    public boolean isExist(String secrectkey) {
        if(ApiMethod.isEmpty(secrectkey)) return false;
        return commonDAO.countPKI(secrectkey)>0;
    }
}
