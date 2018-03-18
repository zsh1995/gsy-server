package com.gsy.base.web.services.coupon;


import com.gsy.base.common.ApiConst;
import com.gsy.base.web.dao.CouponDAO;
import com.gsy.base.web.entity.merge.Coupon;
import com.gsy.base.web.entity.merge.UserCoupon;
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

    @Override
    public boolean bindCoupon(String openId, String couponName) {
        //数据库操作
        couponDAO.inserNewCouponRel(openId,couponName);
        return true;
    }

    @Override
    public boolean bindAnalyseCoupon(String openId) {
        return bindCoupon(openId, ApiConst.ANALYSE_FREE_THREE_TIME);
    }

    @Override
    public boolean bindExamCoupon(String openId) {
        return bindCoupon(openId, ApiConst.EXAM_FREE_THREE_TIME);
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
