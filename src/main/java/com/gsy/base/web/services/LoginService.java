package com.gsy.base.web.services;

import com.qcloud.weapp.authorization.UserInfo;

/**
 * Created by Administrator on 2017/6/18.
 */
public interface LoginService {

    boolean doLogin(UserInfo userInfo);

    long isExist(UserInfo userInfo);

}
