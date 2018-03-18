package com.gsy.base.web.services.impl;

import com.gsy.base.web.dao.UserDAO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.services.LoginService;
import com.gsy.base.web.services.coupon.CouponService;
import com.qcloud.weapp.authorization.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Scanner;

/**
 * Created by Administrator on 2017/6/18.
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private CouponService couponService;

    @Override
    public boolean doLogin(UserInfo userInfo) {
        if(!isExist(userInfo)) {
            if (!couponService.bindAnalyseCoupon(userInfo.getOpenId())) {
                //doing - nothing ....
            }
        }
        UserInfoDTO dto = new UserInfoDTO();
        dto.setOpenId(userInfo.getOpenId());
        dto.setNickName(userInfo.getNickName());
        dto.setAvatarUrl(userInfo.getAvatarUrl());
        userDAO.insertNewLoginUser(dto);
        return true;
    }

    @Override
    public boolean isExist(UserInfo userInfo) {
        if(userDAO.getUserInfo(userInfo.getOpenId()) == null){
            return false;
        }
        return true;
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
