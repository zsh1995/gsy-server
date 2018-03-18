package com.gsy.base.web.services;

import com.gsy.base.common.exam.ExamHelper;

import java.util.List;

/**
 * Created by mrzsh on 2018/1/28.
 */
public interface ExamService {



    boolean checkExamExist(long uid, ExamHelper.ExamStar star);

    List getQuestions(long uid, ExamHelper.ExamStar star);

    void deleteExam(long uid, ExamHelper.ExamStar star);

    double calcScore(long uid, ExamHelper.ExamStar star, List<Integer> chooseList) throws Exception;

}
