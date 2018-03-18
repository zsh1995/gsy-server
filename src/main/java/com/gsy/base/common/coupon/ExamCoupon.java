package com.gsy.base.common.coupon;


import com.gsy.base.common.ApiConst;
import com.gsy.base.web.dao.CouponDAO;
import com.gsy.base.web.dao.WechatPayDAO;
import com.gsy.base.web.entity.merge.Prepay;
import com.gsy.base.web.services.coupon.CouponService;
import com.gsy.base.web.services.payService.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Map;

/**
 * Created by Administrator on 2017/8/25.
 */
@Component
public class ExamCoupon implements CouponUse{

    @Autowired
    private CouponService couponService;
    @Autowired
    private PayService payService;

    @Autowired
    private WechatPayDAO payDAO;

    @Override
    public boolean useCoupon(String openId, Map data) throws Exception {
        if(!couponService.checkCouponAvaliable(openId, ApiConst.EXAM_FREE_THREE_TIME)){
            throw new Exception("使用券出错--次数已用尽");
        }
        if(!couponService.useCoupon(openId, ApiConst.EXAM_FREE_THREE_TIME)){
            throw new Exception("使用券出错--修改次数出错");
        }
        if( !realUse( openId,(int)data.get("star") )){
            throw new Exception("使用券出错--插入权限信息错误");
        }

        return true;
    }

    private boolean realUse(String openId,int star){
        StringBuilder sb = new StringBuilder();
        try {
            payService.orderExam(openId,star,ApiConst.PURCH_CHANNEL_COUPON,sb);
            String outNum = sb.toString();
            payDAO.selectedIntoPurRel(outNum);
            Prepay temp = new Prepay();
            temp.setOutTradeNo(outNum);
            payDAO.updatePrePay(temp);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
