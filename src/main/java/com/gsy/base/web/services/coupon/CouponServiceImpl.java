package com.gsy.base.web.services.coupon;


import com.gsy.base.common.ApiConst;
import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.web.dao.CouponDAO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.entity.merge.Coupon;
import com.gsy.base.web.entity.merge.UserCoupon;
import com.gsy.base.web.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDAO couponDAO;
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public boolean bindCoupon(long id, String couponName ,int source) {
        //数据库操作
        couponDAO.inserNewCouponRel(id,couponName,source);
        return true;
    }

    @Override
    public boolean bindAnalyseCoupon(long id,int source) {
        return bindCoupon(id, ApiConst.ANALYSE_FREE_THREE_TIME,source);
    }
    @Override
    public boolean bindAnalyseCoupon(String openId, int source) {
        UserInfoDTO userInfoDTO = userInfoService.getUserInfo(openId);
        if(userInfoDTO == null){
            throw new NoPermissionException("库中无用户");
        }
        return bindCoupon(userInfoDTO.getId(), ApiConst.ANALYSE_FREE_THREE_TIME,source);
    }

    @Override
    public boolean bindExamCoupon(long id, int source) {
        return bindCoupon(id, ApiConst.EXAM_FREE_THREE_TIME,source);
    }
    @Override
    public boolean bindExamCoupon(String openId, int source) {
        UserInfoDTO userInfoDTO = userInfoService.getUserInfo(openId);
        if(userInfoDTO == null){
            throw new NoPermissionException("库中无用户");
        }
        return bindCoupon(userInfoDTO.getId(), ApiConst.EXAM_FREE_THREE_TIME,source);
    }


    @Override
    public List userCoupons(String openId,String couponName) {
        return couponDAO.queryCouponOfUser(openId,couponName);
    }

    @Override
    public List userAllCoupons(String openId) {
        return couponDAO.queryAllCoupons(openId);
    }

    @Override
    public boolean checkCouponAvaliable(String openId, String couponName) {
        UserCoupon userCoupon = couponDAO.selectCouponsRemain(openId,couponName);
        return userCoupon.getRemainTimes()>0;
    }

    @Override
    public boolean useCoupon(String openId, String couponName) throws Exception {
        List<Coupon> coupons = userCoupons(openId, couponName);
        //获取可用券
        if( coupons.isEmpty() ){
            throw new Exception("无可用券");
        }
        //使用券
        couponDAO.useCoupon(openId,couponName,coupons.get(0).getId());
        //记录
        couponDAO.logUseCoupon(openId,couponName);
        return true;
    }


}
