package com.gsy.base.web.services;

import com.gsy.base.common.exam.ExamHelper;

import java.util.List;

/**
 * Created by mrzsh on 2018/1/28.
 */
public interface ExamService {


    /**
     * @param uid 用户id
     * @param star 考试星级
     * @return 是否存在考试
     */
    boolean checkExamExist(long uid, ExamHelper.ExamStar star);

    /**
     * @param uid 用户id
     * @param star 考试星级
     * @return 获得试题
     */
    List getQuestions(long uid, ExamHelper.ExamStar star);

    void deleteExam(long uid, ExamHelper.ExamStar star);

    double calcScore(long uid, ExamHelper.ExamStar star, List<Integer> chooseList) throws Exception;

}
