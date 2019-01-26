package com.gsy.base.common.aop;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.common.exceptions.NoUserException;
import com.gsy.base.common.exceptions.ParamentErroException;
import com.gsy.base.web.entity.UserInfoEntity;
import com.gsy.base.web.services.SecurityService;
import com.qcloud.weapp.authorization.LoginService;

import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Created by mrzsh on 2018/2/3.
 */
@Aspect
@Component
public class AjaxControllerAOP {
    private static Logger logger =  LoggerFactory.getLogger(AjaxControllerAOP.class);

    @Autowired
    SecurityService securityService;

    @Value("${gsy.aop.test}")
    private boolean isTest;

    @Pointcut("execution(public * com.gsy.base.web.controller.ajaxcontroller.*.*(..))")
    public void controllerMethod(){}

    @Pointcut("execution(public * com.gsy.base.web.controller.commonController.*.*(..))")
    public void commonControllermethod(){}

    @Pointcut("execution(public * com.gsy.base.web.controller.api.*.*(..))")
    public void apiMethod(){}

    @Around("controllerMethod()")
    public void doBefore(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            UserInfoEntity userInfoEntity ;
            if(isTest){
                //注入测试openId
                userInfoEntity = new UserInfoEntity();
                userInfoEntity.setOpenId("o5TL-0FErAiYMqeXGCs0T1N2KlP4");
            }else{
                LoginService service = new LoginService(request,response);
                UserInfo userInfo = service.check();
                userInfoEntity = new UserInfoEntity(userInfo);
            }
            Object[] args = joinPoint.getArgs();
            args[0]=userInfoEntity;
            result = (ResultBean) joinPoint.proceed(args);
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("[{}use time: {}]",joinPoint.getSignature(),elapsedTime);
        }catch (Throwable e){
            result = handleException(joinPoint,e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(new JSONObject(result).toString());
    }

    @Around("commonControllermethod()")
    public void aroundCommonController(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            Object[] args = joinPoint.getArgs();
            result = (ResultBean) joinPoint.proceed(args);
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("[{}use time: {}]",joinPoint.getSignature(),elapsedTime);
        }catch (Throwable e){
            result = handleException(joinPoint,e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(new JSONObject(result).toString());
    }

    @Around("apiMethod()")
    public void aroundApi(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        ResultBean<?> result;
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        String key = request.getParameter("key");
        logger.info("key = {}",key);
        try {
            if(!securityService.isExist(key))
                throw new NoPermissionException("无权限,或key不可用");
            Object[] args = joinPoint.getArgs();
            result = (ResultBean) joinPoint.proceed(args);
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("[{}use time: {}]",joinPoint.getSignature(),elapsedTime);
        }catch (Throwable e){
            result = handleException(joinPoint,e);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(new JSONObject(result).toString());

    }

    private ResultBean handleException(ProceedingJoinPoint pjp,Throwable e){
        ResultBean<?> result = new ResultBean<>();
        boolean test = e instanceof NoPermissionException;
        logger.info("test={}",test);
        if(e instanceof NoPermissionException){
            result.setMessage(e.getLocalizedMessage());
            result.setCode(ResultBean.NO_PERMISSION);
        } else if(e instanceof ParamentErroException) {
            result.setMessage(e.getLocalizedMessage());
            result.setCode(ResultBean.PARAMENT_ERRO);
        } else if(e instanceof NoUserException){
            result.setMessage(e.getLocalizedMessage());
            result.setCode(ResultBean.NO_USER);
        } else if(e instanceof LoginServiceException){
            result.setMessage(e.getLocalizedMessage());
            result.setCode(ResultBean.NO_LOGIN);
        } else{
            logger.error(pjp.getSignature()+" error",e);
            result.setMessage(e.toString());
            result.setCode(ResultBean.UNKNOWN_EXCEPTION);
        }
        return result;
    }
}
