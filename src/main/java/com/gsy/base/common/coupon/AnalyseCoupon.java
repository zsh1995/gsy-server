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
 * Created by Administrator on 2017/8/24.
 */
@Component
public class AnalyseCoupon implements CouponUse {

    @Autowired
    private CouponDAO couponDAO;

    @Autowired
    private CouponService couponService;

    @Autowired
    private PayService payService;

    @Autowired
    private WechatPayDAO payDAO;

    class HoldString{
        public String str;
    }

    @Override
    public boolean useCoupon(String openId, Map data) throws Exception {

        if(!couponService.checkCouponAvaliable(openId, ApiConst.ANALYSE_FREE_THREE_TIME)){
            throw new Exception("使用券出错--次数已用尽");
        }

        if(!couponService.useCoupon(openId, ApiConst.ANALYSE_FREE_THREE_TIME)){
            throw new Exception("使用券出错--修改次数出错");
        }
        if( !realUse(openId,(int)data.get("questionId"),(int)data.get("star"),(int)data.get("group"),(int)data.get("feQuestion") ) ){
            throw new Exception("使用券出错--插入权限信息错误");
        }
        return true;
    }


    private boolean realUse(String openId, int questionId,int star,int group,int feQuestion){
        StringBuilder sb = new StringBuilder();
        try {
            payService.orderAnalyse(openId,star,questionId,group,feQuestion,ApiConst.PURCH_CHANNEL_COUPON,sb);
            String outNum = sb.toString();
            payDAO.selectedIntoPurRel(outNum);
            Prepay temp = new Prepay();
            temp.setOutTradeNo(outNum);
            payDAO.updatePrePay(temp);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
