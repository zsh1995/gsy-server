package com.gsy.base.web.services;



import com.gsy.base.web.dto.UserInfoDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/2.
 */
public interface UserInfoService {

    UserInfoDTO getUserInfo(String openId);

    boolean updateUserInfo(UserInfoDTO userInfoDTO) throws Exception;

    UserInfoDTO getInvitor(Long id);

    boolean updateUserInvitor(Long id, String openId);

    List getPurchExamRecord(String openId);

    List<UserInfoDTO> getAllInvite(String openId);

    boolean checkEnroll(String openid);

    int getInviteNums(String openId);

    boolean getExercisePass(String openId);

    boolean getPracticePass(String openId);

    double calcAuthRank(String openId) throws Exception;

    Map getMyInvitor(String openId);
}
