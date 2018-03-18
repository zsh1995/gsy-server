package com.gsy.base.web.controller.ajaxcontroller;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.web.entity.UserInfoEntity;
import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by mrzsh on 2018/2/3.
 */
@RestController
@RequestMapping(value = "/ajax/exam")
public class TestController {

    public static Logger logger = LoggerFactory.getLogger(TestController.class);


    @RequestMapping(value = "/test")
    public ResultBean test(UserInfoEntity userInfo, @RequestParam int id){
        return new ResultBean<String>("hello world!" + userInfo);
    }
}
