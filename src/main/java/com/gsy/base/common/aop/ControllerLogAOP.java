package com.gsy.base.common.aop;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.web.entity.UserInfoEntity;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.UserInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by mrzsh on 2018/2/8.
 */
@Aspect
@Component
public class ControllerLogAOP {
    private static Logger logger =  LoggerFactory.getLogger(ControllerLogAOP.class);
    @Pointcut("execution(public * com.gsy.base.web.controller.pay.PayCallBackController.*(..))")
    public void controllerCallbackMethod(){}

    @Pointcut("execution(public * com.gsy.base.web.controller.pay.PayController.*(..))")
    public void controllerMethod(){}

    @Around("controllerCallbackMethod()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        try{
            joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("{} use time: {}ms",joinPoint.getSignature(),elapsedTime);
        }catch (Exception e){
            logger.error("交易回调出错",e);
        }
    }


    @Around("controllerMethod()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable{
        long startTime = System.currentTimeMillis();
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        try {
            Object result ;
            UserInfoEntity userInfoEntity ;
            LoginService service = new LoginService(request,response);
            UserInfo userInfo = service.check();
            userInfoEntity = new UserInfoEntity(userInfo);
            Object[] args = joinPoint.getArgs();
            args[0]=userInfoEntity;
            result = joinPoint.proceed(args);
            long elapsedTime = System.currentTimeMillis() - startTime;
            logger.info("[{}use time: {}]",joinPoint.getSignature(),elapsedTime);
            return result;
        }catch (Throwable e){
            logger.error("交易失败",e);
            return e;
        }
    }



}
