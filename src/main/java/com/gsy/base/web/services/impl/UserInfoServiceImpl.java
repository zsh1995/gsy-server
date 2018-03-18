package com.gsy.base.web.services.impl;

import com.gsy.base.common.ApiConst;
import com.gsy.base.common.ApiMethod;
import com.gsy.base.web.dao.UserDAO;
import com.gsy.base.web.dao.WechatPayDAO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.entity.merge.EnterpriseInfo;
import com.gsy.base.web.services.UserInfoService;
import com.gsy.base.web.services.company.CompanyService;
import com.gsy.base.web.services.uerRight.UserRightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    public Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDAO userDao;
    @Autowired
    WechatPayDAO payDAO;
    @Autowired
    CompanyService companyService;
    @Autowired
    UserRightService userRightService;

    @Override
    public UserInfoDTO getUserInfo(String openId) {
        return userDao.getUserInfo(openId);
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO){
        long id = userDao.getUserInfo(userInfoDTO.getOpenId()).getId();
        EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWantedCompany1());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWantedCompany2());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWantedCompany3());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWantedCompany4());companyService.addNewCompany(enterpriseInfo,id);
        enterpriseInfo.setEnterpriseName(userInfoDTO.getWantedCompany5());companyService.addNewCompany(enterpriseInfo,id);
        userDao.updateUserInfo(userInfoDTO);
        return true;
    }

    @Override
    public UserInfoDTO getInvitor(Long id) {
        return userDao.selectInvitor(id);
    }

    @Override
    public boolean updateUserInvitor(Long id, String openId) {
        userDao.updateInvitor(id,openId);
        return true;
    }

    @Override
    public List getPurchExamRecord(String openId) {
        return (List<Map>) payDAO.getPurchExamRecord(openId);
    }

    @Override
    public List<UserInfoDTO> getAllInvite(String openId) {
        return userDao.queryAllInvite(openId);
    }

    @Override
    public boolean checkEnroll(String openid) {
        UserInfoDTO userInfoDTO =  getUserInfo(openid);
        if(userInfoDTO.getUserChannel() == 1){
            return true;
        }
        return false;
    }

    @Override
    public int getInviteNums(String openId){
        return userDao.selectInvitedCount(openId);
    }
    private boolean invitEnough(int invitNum,int star) throws Exception {
        int need = ApiMethod.getConstant(ApiConst.NEED_INIVITE_PRE+star);
        if(need == 0) throw new Exception("获取邀请人数量参数错误！");
        return invitNum >= need ;
    }
    @Override
    public boolean getExercisePass(String openId){
        if(userDao.findAppend(openId) == null){
            return false;
        }
        return (int)userDao.findAppend(openId).getExercisePass() == 1;
    }
    @Override
    public boolean getPracticePass(String openId){
        if(userDao.findAppend(openId) == null){
            return false;
        }
        return (int)userDao.findAppend(openId).getPracticePass() == 1;
    }

    @Override
    public double calcAuthRank(String openId) throws Exception {
        double rank = 0;
        // 检查考试通过情况
        try {
           int pass1 = userRightService.passExam(userRightService.getExamStatus(openId,1),1)? 1: 0;
           int pass2 = userRightService.passExam(userRightService.getExamStatus(openId,2),2)? 1: 0;
           int pass3 = userRightService.passExam(userRightService.getExamStatus(openId,3),3)? 1: 0;
           rank = pass1+pass2+pass3;
        } catch (Exception e) {
            throw new Exception("计算等级出错，请联系管理员",e);
        }
        // 检查附加项完成情况
        boolean exercise = getExercisePass(openId);
        boolean practice = getPracticePass(openId);
        rank += exercise && practice ? 0.5:0;
        //
        return rank;
    }

    @Override
    public Map getMyInvitor(String openId){
        return userDao.getMyInvitor(openId);
    }

}
