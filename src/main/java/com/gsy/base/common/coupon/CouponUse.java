package com.gsy.base.common.coupon;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
public interface CouponUse {

    boolean useCoupon(String openId, Map data) throws Exception;

}
