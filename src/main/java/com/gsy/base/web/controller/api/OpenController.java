package com.gsy.base.web.controller.api;/**
 * Created by mrzsh on 2018/5/16.
 */

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.web.services.UserInfoService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: gsyboot
 * @description: 提供给第三方的API
 * @author: Mr.zsh
 * @create: 2018-05-16 13:47
 **/
@RestController
@RequestMapping("/api")
public class OpenController {

    @Autowired
    UserInfoService userInfoService;


    @RequestMapping("/user/{phone}/star")
    public ResultBean getUserStarByPhone(@PathVariable("phone") String phone) throws Exception {
        return new ResultBean(userInfoService.getRankByPhone(phone));
    }

    @RequestMapping("/test")
    public ResultBean test() {
        return new ResultBean("123");
    }

}
