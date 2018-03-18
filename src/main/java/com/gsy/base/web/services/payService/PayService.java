package com.gsy.base.web.services.payService;

import com.gsy.base.web.dto.OrderReturnDTO;
import com.gsy.base.web.dto.SignInfoDTO;


/**
 * Created by zsh1995 on 2017/6/26.
 */
public interface PayService {

    OrderReturnDTO orderAnalyse(String openId, int star, int questionId, int group, int question, int channel) throws Exception;

    OrderReturnDTO orderAnalyse(String openId, int star, int questionId, int group, int question, int channel, StringBuilder outNum) throws Exception;

    OrderReturnDTO orderExam(String openId, int star, int channel) throws Exception;

    OrderReturnDTO orderExam(String openId, int star, int channel, StringBuilder sb) throws Exception;

    boolean refund(String orderNumber);

    boolean checkAnalysePurched(String openId, int star, int questionId);

    int checkExamPurched(String openId, int star);



    boolean addPurchRecord(String tradeNo, int star, int questionId, int group, int feQuestion, int channel);

    boolean addPurchRecord(String tradeNo, int star, int channel);

    OrderReturnDTO orderReturnable(String openId) throws Exception;

    SignInfoDTO weChatResignData(String repayId) throws IllegalAccessException;
}
