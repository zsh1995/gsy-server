package com.gsy.base.web.controller.pay;

import com.gsy.base.common.MapperTools;
import com.gsy.base.common.StreamUtil;
import com.gsy.base.web.dao.WechatPayDAO;
import com.gsy.base.web.dto.WechatResultDTO;
import com.gsy.base.web.entity.merge.Prepay;
import com.gsy.base.web.entity.merge.WeixinReturnStatements;
import com.gsy.base.web.services.payService.PayService;
import com.thoughtworks.xstream.XStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mrzsh on 2018/2/6.
 */
@RestController
@RequestMapping("/pay")
public class PayCallBackController {

    public static Logger L = LoggerFactory.getLogger(PayController.class);


    @Autowired
    PayService payService;
    @Autowired
    WechatPayDAO payDAO;

    /**
     *  支付后，供微信回调
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/PayResult")
    public void  payResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String reqParams = StreamUtil.read(request.getInputStream());
        L.info("-------支付结果:"+reqParams);
        XStream xStream = new XStream();
        xStream.alias("xml", WechatResultDTO.class);
        WechatResultDTO returnInfo = (WechatResultDTO)xStream.fromXML(reqParams);
        //写库
        WeixinReturnStatements statements = MapperTools.entityToDTO(WeixinReturnStatements.class,returnInfo);
        L.info("mapper result:"+statements.getAppid()+","+statements.getBankType());
        payDAO.insertNewPayReturn(statements);
        //
        payDAO.selectedIntoPurRel(returnInfo.getOut_trade_no());
        Prepay tempDTO = new Prepay();
        tempDTO.setOutTradeNo(returnInfo.getOut_trade_no());
        payDAO.updatePrePay(tempDTO);
        StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
        response.getWriter().append(sb.toString());
    }
}
