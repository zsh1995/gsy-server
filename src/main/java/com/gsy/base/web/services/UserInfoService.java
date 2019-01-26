package com.gsy.base.web.services;



import com.gsy.base.common.exceptions.ParamentErroException;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.entity.merge.EnterpriseInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface UserInfoService {

    UserInfoDTO getUserInfo(String openId);

    String getOpenId(long id);

    boolean updateUserInfo(UserInfoDTO userInfoDTO) throws Exception;

    boolean addCompanys(List<String> companyList, String openId);

    UserInfoDTO getInvitor(Long id);

    boolean updateUserInvitor(Long id, String openId);

    List getPurchExamRecord(String openId);

    List<UserInfoDTO> getAllInvite(String openId);

    boolean checkEnroll(String openid);

    int getInviteNums(String openId);

    boolean invitEnough(int invitNum, int star) throws Exception;

    boolean getExercisePass(String openId);

    boolean getPracticePass(String openId);

    double calcAuthRank(String openId) throws Exception;

    String getOpenIdByPhone(String phone) throws ParamentErroException;

    double getRankByPhone(String phone) throws ParamentErroException;

    Map getMyInvitor(String openId);
}
