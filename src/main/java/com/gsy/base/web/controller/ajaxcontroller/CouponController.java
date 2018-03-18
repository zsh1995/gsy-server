package com.gsy.base.web.controller.ajaxcontroller;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.common.coupon.AnalyseCoupon;
import com.gsy.base.common.coupon.CouponUse;
import com.gsy.base.common.coupon.ExamCoupon;
import com.gsy.base.web.entity.UserInfoEntity;
import com.gsy.base.web.services.coupon.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrzsh on 2018/2/6.
 */
@RestController
@RequestMapping("/ajax/coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUse analyseCoupon;

    @Autowired
    private CouponUse examCoupon;

    /**
     * 使用考试优惠券
     */
    @RequestMapping("/useExam")
    public ResultBean useExamCoupon(UserInfoEntity userInfoEntity,
                                @RequestParam int star) throws Exception {
        Map dataMap = new HashMap<>();
        dataMap.put("star", star);
        return new ResultBean(examCoupon.useCoupon(userInfoEntity.getOpenId(), dataMap));
    }

    /**
     * 使用解析优惠券
     */
    @RequestMapping("/useAnalyse")
    public ResultBean userAnalyseCoupon(UserInfoEntity userInfoEntity,
                                        @RequestParam int star,
                                        @RequestParam int questionId,
                                        @RequestParam int feQuestionId,
                                        @RequestParam int groupId) throws Exception {
        Map dataMap = new HashMap();
        dataMap.put("questionId", questionId);
        dataMap.put("star", star);
        dataMap.put("group", groupId);
        dataMap.put("feQuestion", feQuestionId);
        return new ResultBean(analyseCoupon.useCoupon(userInfoEntity.getOpenId(), dataMap));
    }

    /**
     * 获取用户所有的优惠券信息
     */
    @RequestMapping("/getAllCoupon")
    public ResultBean getAllCoupon(UserInfoEntity userInfoEntity){
        return new ResultBean(couponService.userAllCoupons(userInfoEntity.getOpenId()));
    }
}
