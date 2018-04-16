package com.gsy.base.web.services.coupon;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */
public interface CouponService {


    //绑定一张优惠券

    boolean bindCoupon(long id, String couponName, int source);



    boolean bindAnalyseCoupon(long id, int source);


    boolean bindAnalyseCoupon(String openId, int source);

    boolean bindExamCoupon(long id, int source);

    boolean bindExamCoupon(String openId, int source);

    //获取用户当前可用优惠券
    List userCoupons(String openId, String couponName);

    //
    List userAllCoupons(String openId);

    boolean checkCouponAvaliable(String openId, String couponName);


    boolean useCoupon(String openId, String couponName) throws Exception;
}
