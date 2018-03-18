package com.gsy.base.web.services.uerRight;

/**
 * Created by Administrator on 2017/7/9.
 */
public interface UserRightService {

    //查询用户权限信息
    int checkUserRight(String openId, int type, int star, int questionId) throws Exception;

    //更新用户权限信息
    boolean updateUserRight(String openId, int star, int remainTimes);

    //插入新的考试权限
    boolean insertNewExamRight(String openId, int star) throws Exception;

    //插入新的解析权限
    boolean insertNewAnalyseRight(String openId, int star, int questionId) throws Exception;

    //根据新的购买信息更新用户权限信息
    boolean inserUserRight(String openId, String tradeNo);

    int checkAvaliableTime(String openId, int productId);

    int checkExamAvaliableTime(String openId, int star);

    //


    boolean updateUserExamStatus(String openId, int star, boolean pass) throws Exception;

    boolean passExam(int passTimes, int star);

    int getExamStatus(String openId, int star) throws Exception;

    int updateExamTimes(String openId, int star, int delta) throws Exception;

    boolean checkReturnalbePurched(String openId) throws Exception;

}
