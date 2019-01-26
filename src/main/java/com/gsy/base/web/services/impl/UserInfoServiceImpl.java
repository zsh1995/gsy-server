package com.gsy.base.web.services.impl;

import com.gsy.base.common.ApiConst;
import com.gsy.base.common.ApiMethod;
import com.gsy.base.common.RedisLockHelper;
import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.common.exceptions.NoUserException;
import com.gsy.base.common.exceptions.ParamentErroException;
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
@Transactional
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

    @Autowired
    RedisLockHelper redisLockHelper;

    @Override
    public UserInfoDTO getUserInfo(String openId) {
        return userDao.getUserInfo(openId);
    }

    @Override
    public String getOpenId(long id) {
        return userDao.getOpenId(id);
    }

    @Override
    public boolean updateUserInfo(UserInfoDTO userInfoDTO){
        userDao.updateUserInfo(userInfoDTO);
        return true;
    }

    @Override
    public boolean addCompanys(List<String> companyList, String openId){
        long uid = userDao.getUserInfo(openId).getId();
        int validCount = 0;
        for(String company : companyList){
            if(ApiMethod.isEmpty(company)) continue;
            validCount+=1;
            EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
            enterpriseInfo.setEnterpriseName(company);
            companyService.addNewCompany(enterpriseInfo,uid);
        }
        if(validCount > 3) throw new ParamentErroException("内推公司数不能大于3！");
        return true;
    }

    @Override
    public UserInfoDTO getInvitor(Long id) {
        return userDao.selectInvitor(id);
    }

    @Override
    public boolean updateUserInvitor(Long id, String openId) {
        // redisLockHelper.getLock(RedisLockHelper.USERINFO_LOCK,RedisLockHelper.OPENID_LOCK,openId);
        UserInfoDTO userInfoDTO = getInvitor(id);
        UserInfoDTO ownInfo = getUserInfo(openId);
        if(userInfoDTO == null){
            throw new NoPermissionException("不存在用户");
        }
        if(openId.equals(userInfoDTO.getOpenId())){
            throw new NoPermissionException("不能选择自己");
        }
        if(!ApiMethod.isEmpty(ownInfo.getInvitor()) || "0".equals(ownInfo.getInvitor())){
            throw new NoPermissionException("已经设置！");
        }
        if(Long.parseLong(userInfoDTO.getInvitor()) == ownInfo.getId() ) {
            throw new NoPermissionException("不能互相邀请！");
        }
        userDao.updateInvitor(id,openId);
        // redisLockHelper.releaseLock(RedisLockHelper.USERINFO_LOCK,RedisLockHelper.OPENID_LOCK,openId);
        return true;
    }

    @Override
    public List getPurchExamRecord(String openId) {
        List<Map> records = (List<Map>) payDAO.getPurchExamRecord(openId);
        records.forEach(record->{
            int price = Integer.parseInt((String) record.get("price"));
            record.put("price",price / 100);
        });
        return records;
    }

    @Override
    public List<UserInfoDTO> getAllInvite(String openId) {
        return userDao.queryAllInvite(openId);
    }

    @Override
    public boolean checkEnroll(String openid) {
        UserInfoDTO userInfoDTO = getUserInfo(openid);
        if(userInfoDTO.getUserChannel() == 1){
            return true;
        }
        return false;
    }

    @Override
    public int getInviteNums(String openId){
        return userDao.selectInvitedCount(openId);
    }
    @Override
    public boolean invitEnough(int invitNum, int star) throws ParamentErroException {
        int need = ApiMethod.getConstant(ApiConst.NEED_INIVITE_PRE+star);
        if(need == 0) throw new ParamentErroException("获取邀请人数量参数错误！");
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
    public double calcAuthRank(String openId) throws ParamentErroException {
        double rank = 0;
        // 检查考试完成情况
        for(int cnt = 0; cnt < 3; cnt++){
            int star = cnt + 1;
            boolean passExam = userRightService.passExam(userRightService.getExamStatus(openId,star),star);
            boolean invitEnough = invitEnough(getInviteNums(openId),star);
            if(passExam && invitEnough)
                rank += 1;
            else
                break;
        }
        // 检查附加项完成情况
        boolean exercise = getExercisePass(openId);
        boolean practice = getPracticePass(openId);
        rank += exercise && practice ? 0.5:0;
        //
        return rank;
    }

    @Override
    public String getOpenIdByPhone(String phone) throws ParamentErroException {
        String formatePhone = phone.trim().replaceAll("[^\\d]","");
        if(ApiMethod.isEmpty(formatePhone) || formatePhone.length() != 11 ) throw new ParamentErroException("手机号格式不正确");
        String openId = userDao.queryOpenIdByPhone(phone);
        if(ApiMethod.isEmpty(openId)) throw new NoUserException("该用户不存在");
        return openId;
    }
    @Override
    public double getRankByPhone(String phone) throws ParamentErroException{
        return calcAuthRank(getOpenIdByPhone(phone));
    }

    @Override
    public Map getMyInvitor(String openId){
        return userDao.getMyInvitor(openId);
    }

}
