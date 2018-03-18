package com.gsy.base.web.controller.login;

import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mrzsh on 2018/2/8.
 */
@RestController
public class LoginController {

    @Autowired
    com.gsy.base.web.services.LoginService loginService;

    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws LoginServiceException, ConfigurationException {
        LoginService service = new LoginService(request, response);
        UserInfo userInfo = service.login();
        loginService.doLogin(userInfo);
    }

}
