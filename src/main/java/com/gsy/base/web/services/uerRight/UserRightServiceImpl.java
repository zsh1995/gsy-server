package com.gsy.base.web.services.uerRight;


import com.gsy.base.common.ApiConst;
import com.gsy.base.common.ApiMethod;
import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.web.dao.UserDAO;
import com.gsy.base.web.dto.PracticeRecordDTO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.entity.merge.WechatUserRight;
import com.gsy.base.web.services.UploadScoreService;
import com.gsy.base.web.services.UserInfoService;
import com.gsy.base.web.services.coupon.CouponService;
import com.gsy.base.web.services.payService.PayService;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2017/7/9.
 */
@Transactional
@Service
public class UserRightServiceImpl implements UserRightService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    PayService payService;
    @Autowired
    CouponService couponService;
    @Autowired
    UserRightService userRightService;
    @Autowired
    UploadScoreService uploadScoreService;

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
    public boolean sendGiftCoupon(String openId) {
        UserInfoDTO userInfoDTO = userDAO.getUserInfo(openId);

        if( userInfoDTO.getUserChannel() == 1) {
            throw new NoPermissionException("您已报名！");
        }
        couponService.bindAnalyseCoupon(userInfoDTO.getId(),0);
        couponService.bindAnalyseCoupon(userInfoDTO.getId(),0);
        couponService.bindAnalyseCoupon(userInfoDTO.getId(),0);
        couponService.bindExamCoupon(userInfoDTO.getId(),0);
        return true;
    }

    @Override
    public boolean updateUserExamStatus(long id, int star, double score) throws Exception {
        String openId = userDAO.selectOpenIdById(id);
        int passTimes = 0;
        boolean pass = score >= 54;
        try{
            passTimes = getExamStatus(openId,star);
        } catch (Exception e){
            passTimes = 0;
        }
        boolean isLastExam = checkExamAvaliableTime(openId,star) == 0;
        boolean passFlag = checkIsPassed(openId,star);

        if(isLastExam){
            userRightService.updateExamTimes(openId,star,0,0);
            if(!passFlag){
                userDAO.updateExamPassTimes(openId,star,0,0);
            }
        }
        if(pass){
            userDAO.updateExamPassTimes(openId,star,passTimes+1,checkIsPass(passTimes+1,star)?1:0);
        }
        // 记录分数
        PracticeRecordDTO dto = new PracticeRecordDTO();
        dto.setQuestionGroup(100);
        dto.setStars(star);
        dto.setScore(score);
        uploadScoreService.uploadScore(openId,dto);

        return true;
    }


    @Override
    public boolean updateUserExamStatus(String openId, int star, double score) throws Exception {
        int passTimes = 0;
        boolean pass = score >= 54;
        try{
            passTimes = getExamStatus(openId,star);
        } catch (Exception e){
            passTimes = 0;
        }
        boolean isLastExam = checkExamAvaliableTime(openId,star) == 0;
        boolean passFlag = checkIsPassed(openId,star);

        if(isLastExam){
            userRightService.updateExamTimes(openId,star,0,0);
            if(!passFlag){
                userDAO.updateExamPassTimes(openId,star,0,0);
            }
        }
        if(pass){
            userDAO.updateExamPassTimes(openId,star,passTimes+1,checkIsPass(passTimes+1,star)?1:0);
        }
        // 记录分数
        PracticeRecordDTO dto = new PracticeRecordDTO();
        dto.setQuestionGroup(100);
        dto.setStars(star);
        dto.setScore(score);
        uploadScoreService.uploadScore(openId,dto);

        return true;
    }

    private boolean checkIsPass(int times,int star){
        return times >= ApiConst.NEEDED_PASSTIMES[star - 1];
    }

    @Override
    public boolean passExam(int passTimes, int star){
        return passTimes >= ApiMethod.getConstant(ApiConst.PASS_EXAM_PRE+star);
    }

    @Override
    public int getExamStatus(String openId, int star) {
        int passExam = 0;
        try{
            passExam =userDAO.findExamPassTimes(openId,star);
        } catch (BindingException e){
            //
        }
        return passExam;
    }

    @Override
    public boolean checkIsPassed(String openId,int star){
        int passFlag = 0;
        try{
            passFlag = userDAO.findPassFlag(openId,star);
        }catch (Exception e){
            // 无数据
        }
        return passFlag == 1;
    }

    @Override
    public int updateExamTimes(String openId, int star, int newData,int validFlag) throws Exception {
        if(newData < 0) throw new Exception("插入数据出错，新次数不能小于0");
        return userDAO.updateExamTimes(openId,star,newData,validFlag);
    }

    @Override
    public boolean checkReturnalbePurched(String openId) throws Exception {
        long id =  userDAO.findReturnableById(openId);
        return id > 0 ? true:false;
    }




}
