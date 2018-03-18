package com.gsy.base.web.controller.pay;

import com.gsy.base.common.ApiConst;
import com.gsy.base.common.MapperTools;
import com.gsy.base.common.StreamUtil;
import com.gsy.base.web.dto.OrderReturnDTO;
import com.gsy.base.web.dto.SignInfoDTO;
import com.gsy.base.web.dto.WechatResultDTO;
import com.gsy.base.web.dto.WeixinReturnStatements;
import com.gsy.base.web.entity.UserInfoEntity;
import com.gsy.base.web.services.payService.PayService;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mrzsh on 2018/2/6.
 */
@RestController
@RequestMapping("/weixinpay")
public class PayController {

    @Autowired
    PayService payService;


    @RequestMapping("/payAnalyse")
    @ResponseBody
    public SignInfoDTO payAnalyse(UserInfoEntity userInfoEntity,
                                  @RequestParam int star,
                                  @RequestParam int questionId,
                                  @RequestParam int feQuestionId,
                                  @RequestParam int groupId) throws Exception {
        OrderReturnDTO orderReturnDTO =  payService.orderAnalyse(userInfoEntity.getOpenId(),star,questionId,groupId,feQuestionId, ApiConst.PURCH_CHANNEL_NORMAL);
        return payService.weChatResignData(orderReturnDTO.getPrepay_id());
    }
    @RequestMapping("/payExam")
    @ResponseBody
    public SignInfoDTO payExam(UserInfoEntity userInfoEntity,
                               @RequestParam int star) throws Exception {

        OrderReturnDTO orderReturnDTO = payService.orderExam(userInfoEntity.getOpenId(),star, ApiConst.PURCH_CHANNEL_NORMAL);
        return payService.weChatResignData(orderReturnDTO.getPrepay_id());
    }

    @RequestMapping("/payProduct")
    @ResponseBody
    public SignInfoDTO payProduct(UserInfoEntity userInfoEntity,
                                  @RequestParam int productId) throws Exception {
        OrderReturnDTO orderReturnDTO = payService.orderReturnable(userInfoEntity.getOpenId());
        return payService.weChatResignData(orderReturnDTO.getPrepay_id());
    }
}
