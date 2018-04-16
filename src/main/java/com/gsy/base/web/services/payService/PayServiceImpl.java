package com.gsy.base.web.services.payService;

import com.gsy.base.common.*;
import com.gsy.base.web.dao.WechatPayDAO;
import com.gsy.base.web.dto.*;
import com.gsy.base.web.entity.merge.Prepay;
import com.thoughtworks.xstream.XStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mrzsh on 2017/11/13.
 */
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private WechatPayDAO wechatPayDAO;

    public static Logger L = Logger.getLogger(PayServiceImpl.class);


    @Override
    public OrderReturnDTO orderAnalyse(String openId, int star, int questionId, int group, int question, int channel) throws Exception {
        //检查是否已购买
        //付款
        //
        //
        return purchAnalyse(openId,star,questionId,group,question,channel);
    }

    @Override
    public OrderReturnDTO orderAnalyse(String openId, int star, int questionId, int group, int question, int channel, StringBuilder outNum) throws Exception {
        //检查是否已购买
        //付款
        //
        //
        return purchAnalyse(openId,star,questionId,group,question,channel,outNum);
    }

    @Override
    public OrderReturnDTO orderExam(String openId, int star, int channel) throws Exception {
        //检查是否已购买
        //付款
        //
        //
        return purchExam(openId,star,channel);
    }

    @Override
    public OrderReturnDTO orderExam(String openId, int star, int channel, StringBuilder sb) throws Exception {
        //检查是否已购买
        //付款
        //
        //
        return purchExam(openId,star,channel,sb);
    }

    @Override
    public boolean refund(String orderNumber) {
        //检查是否符合要求
        //退款
        return false;
    }

    @Override
    public boolean checkAnalysePurched(String openId , int star, int questionId) {
        PurchAnalyseDTO purchAnalyseDTO = wechatPayDAO.getAnalyseRecord(openId,star,questionId);
        if(purchAnalyseDTO == null) return false;
        if(purchAnalyseDTO.getQuestionId() == questionId && purchAnalyseDTO.getStar() == star){
            return true;
        }
        return false;
    }

    @Override
    public int checkExamPurched(String openId, int star) {
        PurchExamDTO purchExamDTO = wechatPayDAO.getExamRecord(openId,star);
        if(ApiConst.TEST_SHENHE) return 6;
        if(purchExamDTO == null) return 0;
        if(purchExamDTO.getStar() == star) return purchExamDTO.getAvaliableTimes();
        return 0;
    }

    @Override
    public boolean addPurchRecord(String tradeNo, int star, int questionId, int group, int feQuestion, int channel){
        wechatPayDAO.insertNewPreAnalyseDetail(tradeNo,star,questionId,group,feQuestion,channel);
        return true;
    }
    @Override
    public boolean addPurchRecord(String tradeNo, int star, int channel){
        wechatPayDAO.insertNewExam(tradeNo,star,channel);
        return true;
    }

    private boolean addPurchRecord(String tradeNo){
        wechatPayDAO.insertNewReturnable(tradeNo);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderReturnDTO orderReturnable(String openId) throws Exception {
        try {
            OrderInfoDTO orderInfoDTO = generateOrder(openId);
            OrderReturnDTO orderReturnDTO = prePay(orderInfoDTO);
            int productId = ApiConst.PRODUCT_RETURNABLE;
            Prepay prepay = new Prepay(openId,orderInfoDTO.getOut_trade_no(),productId,0);
            wechatPayDAO.insertNewPrePay(prepay);
            addPurchRecord(orderInfoDTO.getOut_trade_no());
            return orderReturnDTO;
        } catch (IOException e) {
            // todo exception 没有配置文件
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new Exception("支付失败");

    }


    private boolean isPurched(){
        return false;
    }

    private OrderReturnDTO purchAnalyse(String openId,int star,int questionId,int group,int feQuestionId,int channel) throws Exception {
        return purchAnalyse(openId,star,questionId,group,feQuestionId,channel,new StringBuilder());
    }

    private OrderReturnDTO purchAnalyse(String openId,int star,int questionId,int group,int feQuestionId,int channel,StringBuilder outNum) throws Exception {
        try {
            OrderInfoDTO orderInfoDTO = generateOrder(openId,star,questionId);
            OrderReturnDTO orderReturnDTO = prePay(orderInfoDTO);
            Prepay prepay = new Prepay(openId,orderInfoDTO.getOut_trade_no(),ApiConst.PRODUCT_ANALYSE,0);
            wechatPayDAO.insertNewPrePay(prepay);
            //todo insert db
            addPurchRecord(prepay.getOutTradeNo(),star,questionId,group,feQuestionId,channel);
            outNum.append(orderInfoDTO.getOut_trade_no());
            return orderReturnDTO;
        } catch (IOException e) {
            // todo exception 没有配置文件
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new Exception("支付失败");
    }

    private OrderReturnDTO purchExam(String openId,int star,int channel) throws Exception {
        return purchExam(openId,star,channel,new StringBuilder());
    }

    private OrderReturnDTO purchExam(String openId,int star,int channel,StringBuilder sb) throws Exception {
        try {
            OrderInfoDTO orderInfoDTO = generateOrder(openId,star);
            OrderReturnDTO orderReturnDTO = prePay(orderInfoDTO);
            int productId = 0;
            switch (star){
                case 1:productId=ApiConst.PRODUCT_EXAM_1;break;
                case 2:productId=ApiConst.PRODUCT_EXAM_2;break;
                case 3:productId=ApiConst.PRODUCT_EXAM_3;break;
            }
            Prepay prepay = new Prepay(openId,orderInfoDTO.getOut_trade_no(),productId,0);
            wechatPayDAO.insertNewPrePay(prepay);
            sb.append(prepay.getOutTradeNo());
            addPurchRecord(orderInfoDTO.getOut_trade_no(),star,channel);
            return orderReturnDTO;
        } catch (IOException e) {
            // todo exception 没有配置文件
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        } catch (Exception e){
            throw e;
        }
    }






    private OrderInfoDTO generateOrder(String openId,int star) throws IllegalAccessException, IOException {
        StringBuilder bodyBuilder = new StringBuilder("高商联盟-");
        int price = 0;
        try {
            OrderInfoDTO order = PayConstanst.getInstance().loadInitData();
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setOut_trade_no(generateOutTradeNo(ApiConst.PURCH_TYPE_EXAM,star));
            switch (star){
                case 1:bodyBuilder.append("一星级考试");price = (int) wechatPayDAO.getPrice(ApiConst.PRODUCT_EXAM_1);break;
                case 2:bodyBuilder.append("二星级考试");price = (int) wechatPayDAO.getPrice(ApiConst.PRODUCT_EXAM_2);break;
                case 3:bodyBuilder.append("三星级考试");price = (int) wechatPayDAO.getPrice(ApiConst.PRODUCT_EXAM_3);break;
            }
            order.setBody(bodyBuilder.toString());
            order.setTotal_fee(price);
            order.setOpenid(openId);
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            return order;
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        }
    }

    private OrderInfoDTO generateOrder(String openId,int star,int questionId) throws IOException, IllegalAccessException {
        StringBuilder bodyBuilder = new StringBuilder("高商联盟-");
        int price = 0;
        try {
            OrderInfoDTO order = PayConstanst.getInstance().loadInitData();
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setOut_trade_no(generateOutTradeNo(ApiConst.PURCH_TYPE_ANALYSE,star,0,questionId));
            price = (int) wechatPayDAO.getPrice(ApiConst.PRODUCT_ANALYSE);
            bodyBuilder.append("试题解析")
                    .append("No")
                    .append(questionId+",").append(star).append("星");
            order.setBody(bodyBuilder.toString());
            order.setTotal_fee(price);
            order.setOpenid(openId);
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            return order;
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private OrderInfoDTO generateOrder(String openId) throws IOException, IllegalAccessException {
        StringBuilder bodyBuilder = new StringBuilder("高商联盟-");
        int price = 0;
        try {
            OrderInfoDTO order = PayConstanst.getInstance().loadInitData();
            order.setNonce_str(RandomStringGenerator.getRandomStringByLength(32));
            order.setOut_trade_no(generateReturnableOutTradeNo());
            price = (int) wechatPayDAO.getPrice(ApiConst.PRODUCT_RETURNABLE);
            bodyBuilder.append("加急押金");
            order.setBody(bodyBuilder.toString());
            order.setTotal_fee(price);
            order.setOpenid(openId);
            //生成签名
            String sign = Signature.getSign(order);
            order.setSign(sign);
            return order;
        }catch (IOException e){
            e.printStackTrace();
            throw e;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw e;
        }
    }


    private OrderReturnDTO prePay(OrderInfoDTO orderInfoDTO){
        String result = null;
        try {
            result = HttpRequest.sendPost(PayConstanst.getInstance().getPrePayURL(), orderInfoDTO);
            L.info("--------下单返回："+result);
            XStream xStream = new XStream();
            xStream.alias("xml", OrderReturnDTO.class);
            OrderReturnDTO returnInfo = (OrderReturnDTO)xStream.fromXML(result);

            return returnInfo;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }



    private String generateOutTradeNo(int type,int star){
        SimpleDateFormat sdf = new SimpleDateFormat(ApiConst.FORMATE_DATE);
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date()))
                .append("E")
                .append(star)
                .append(RandomStringGenerator.getRandomStringByLength(22));
        return sb.toString();
    }

    private String generateOutTradeNo(int type,int star,int grouId,int questionId){
        SimpleDateFormat sdf = new SimpleDateFormat(ApiConst.FORMATE_DATE);
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date()))
                .append("A")
                .append(star)
                .append(RandomStringGenerator.getRandomStringByLength(22));
        return sb.toString();
    }
    private String generateReturnableOutTradeNo(){
        SimpleDateFormat sdf = new SimpleDateFormat(ApiConst.FORMATE_DATE);
        StringBuffer sb = new StringBuffer();
        sb.append(sdf.format(new Date()))
                .append("R")
                .append(1)
                .append(RandomStringGenerator.getRandomStringByLength(22));
        return sb.toString();
    }

    public SignInfoDTO weChatResignData(String repayId) throws IllegalAccessException {
            String repay_id = repayId;
            SignInfoDTO signInfo = new SignInfoDTO();
            signInfo.setAppId(Configure.getAppID());
            long time = System.currentTimeMillis()/1000;
            signInfo.setTimeStamp(String.valueOf(time));
            signInfo.setNonceStr(RandomStringGenerator.getRandomStringByLength(32));
            signInfo.setRepay_id("prepay_id="+repay_id);

            signInfo.setSignType("MD5");
            signInfo.setCode(0);
            //生成签名
            signInfo.setPaySign(Signature.getSign(signInfo));
            return  signInfo;
    }

}
