package com.gsy.base.web.services.impl;

import com.gsy.base.common.RedisLockHelper;
import com.gsy.base.web.dao.UserDAO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.services.LoginService;
import com.gsy.base.web.services.coupon.CouponService;
import com.qcloud.weapp.authorization.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Scanner;

/**
 * Created by Administrator on 2017/6/18.
 */
@Transactional
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CouponService couponService;
    @Autowired
    RedisLockHelper redisLockHelper;

    @Override
    public boolean doLogin(UserInfo userInfo) {
        if( !redisLockHelper.getLock(RedisLockHelper.USERINFO_LOCK,RedisLockHelper.OPENID_LOCK,userInfo.getOpenId()) ) return false;
        UserInfoDTO dto = new UserInfoDTO();
        dto.setOpenId(userInfo.getOpenId());
        dto.setNickName(userInfo.getNickName());
        dto.setAvatarUrl(userInfo.getAvatarUrl());
        long id = isExist(userInfo);
        userDAO.insertNewLoginUser(dto);
        if(id == 0) {
            id = isExist(userInfo);
            if(id == 0) return false;
            if (!couponService.bindAnalyseCoupon(id,1)) {
                //doing - nothing ....
            }
        }
        redisLockHelper.releaseLock(RedisLockHelper.USERINFO_LOCK,RedisLockHelper.OPENID_LOCK,userInfo.getOpenId());
        return true;
    }

    @Override
    public long isExist(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = userDAO.getUserInfo(userInfo.getOpenId());
        if( userInfoDTO== null){
            return 0;
        }
        return userInfoDTO.getId();
    }

    public static int maxIn(int a,int b){
        return a > b ? a : b;
    }



    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        while(t-- > 0 ){
            int tBone = scanner.nextInt();
            int tVolume = scanner.nextInt();
            int[] valueArray = new int[tBone];
            int[] volumeArray = new int[tVolume];
            int[] imData = new int[tVolume+1];
            for(int cnt = 0;cnt < tBone;cnt++){
                valueArray[cnt] = scanner.nextInt();
            }
            for(int cnt = 0;cnt < tBone;cnt++){
                volumeArray[cnt] = scanner.nextInt();
            }

            for(int cnt = 1;cnt <= tBone;cnt++){
                int currentVolumn = volumeArray[cnt - 1];
                int currentValue = valueArray[cnt - 1];
                for(int bagVolum = tVolume ; bagVolum >= 0 ; bagVolum --){
                    if(currentVolumn > bagVolum){
                        imData[bagVolum] = imData[bagVolum];
                    }else {
                        imData[bagVolum] = LoginServiceImpl.maxIn( imData[bagVolum],imData[bagVolum - currentVolumn] + currentValue);
                    }
                }

            }
            System.out.println(imData[tVolume]);
        }


    }

}
