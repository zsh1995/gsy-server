package com.gsy.base.web.services.uerRight;


import com.gsy.base.common.ApiConst;
import com.gsy.base.common.ApiMethod;
import com.gsy.base.web.dao.UserDAO;
import com.gsy.base.web.entity.merge.WechatUserRight;
import com.gsy.base.web.services.payService.PayService;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/7/9.
 */
@Service
public class UserRightServiceImpl implements UserRightService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    PayService payService;

    @Override
    public int checkUserRight(String openId, int type, int star, int questionId) throws Exception {

        WechatUserRight userInformation = new WechatUserRight();
        userInformation.setOpenId(openId);
        userInformation.setQuestionId(questionId);
        userInformation.setStar(star);

        if(ApiConst.PURCH_TYPE_EXAM == type){

            userInformation.setType(ApiConst.RIGHT_TYPE_EXAM);
            //查询用户权限信息
            Integer remainTimes = userDAO.selecteUserRightRemainTimes(userInformation);
            if(remainTimes == null){
                remainTimes =0;
            }
            if(ApiConst.TEST_SHENHE){
                remainTimes = 6;
            }
            return remainTimes;
        } else if(ApiConst.PURCH_TYPE_ANALYSE == type){
            //
            userInformation.setType(ApiConst.RIGHT_TYPE_ANALYSE);
            Long returnId = userDAO.selecteUserRightAnalyse(userInformation);
            int resultId = 0;
            if(returnId != null){
                resultId = returnId.intValue();
            }
            return resultId;
        }
        throw new Exception("参数错误");
    }

    @Override
    public boolean updateUserRight(String openId, int star, int remainTimes) {

        WechatUserRight userRight = new WechatUserRight();

        userRight.setOpenId(openId);
        userRight.setStar(star);
        userRight.setRemainTimes(remainTimes);
        userRight.setType(ApiConst.RIGHT_TYPE_EXAM);

        userDAO.updateUserRightRemainTimes(userRight);
        return true;
    }

    @Override
    public boolean insertNewExamRight(String openId, int star) throws Exception {
        WechatUserRight userRight = new WechatUserRight();

        userRight.setType(ApiConst.RIGHT_TYPE_EXAM);
        userRight.setOpenId(openId);
        userRight.setStar(star);
        userDAO.insertNewUserRight(userRight);
        return true;
    }

    @Override
    public boolean insertNewAnalyseRight(String openId, int star,int questionId) throws Exception {
        WechatUserRight userRight = new WechatUserRight();
        userRight.setType(ApiConst.RIGHT_TYPE_ANALYSE);
        userRight.setOpenId(openId);
        userRight.setQuestionId(questionId);
        userRight.setStar(star);
        userDAO.insertNewUserRight(userRight);
        return true;
    }

    @Override
    public boolean inserUserRight(String openId, String tradeNo) {
        userDAO.insertUserRightByPurchedInfo(openId,tradeNo);
        return true;
    }

    @Override
    public int checkAvaliableTime(String openId, int productId) {
        return 0;
    }


    @Override
    public int checkExamAvaliableTime(String openId, int star) {
        return payService.checkExamPurched(openId,star);
    }

    @Override
    public boolean updateUserExamStatus(String openId, int star, boolean pass) throws Exception {
        int passTimes = 0;
        try{
            passTimes = getExamStatus(openId,star);
        } catch (Exception e){
            passTimes = 0;
        }
        int passed = 0;
        if(passTimes >6) throw new Exception("考试次数异常");
        int needTims = ApiMethod.getConstant(ApiConst.PASS_EXAM_PRE+star) - passTimes;
        boolean unableComp = needTims > checkExamAvaliableTime(openId,star);
        //废弃考试机会
        //* 2018.3.8 暂废弃该功能
        /*
        if(unableComp){
            updateExamTimes(openId,star,0);
            userDAO.updateExamPassTimes(openId,star,0,0);
            return true;
        }
        */
        if(!pass) return true;

        userDAO.updateExamPassTimes(openId,star,passTimes+1,passTimes >= 5?1:0);
        return true;
    }

    @Override
    public boolean passExam(int passTimes, int star){
        return passTimes >= ApiMethod.getConstant(ApiConst.PASS_EXAM_PRE+star);
    }

    @Override
    public int getExamStatus(String openId, int star) throws Exception {
        int passExam = 0;
        try{
            passExam =userDAO.findExamPassTimes(openId,star);
        }catch (BindingException e){
            // 说明没有通过过考试,不是异常,后期优化sql
        }
        return passExam;
    }

    @Override
    public int updateExamTimes(String openId, int star, int newData) throws Exception {
        if(newData < 0) throw new Exception("插入数据出错，新次数不能小于0");
        return userDAO.updateExamTimes(openId,star,newData);
    }

    @Override
    public boolean checkReturnalbePurched(String openId) throws Exception {
        long id =  userDAO.findReturnableById(openId);
        return id > 0 ? true:false;
    }




}
