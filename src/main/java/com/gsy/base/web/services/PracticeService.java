package com.gsy.base.web.services;


import com.gsy.base.web.dto.PracticeRecordDTO;
import com.gsy.base.web.dto.QuestionDTO;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public interface PracticeService {

    /**
     * 获取指定6个题目
     * @returnpublic
     */
    List<PracticeRecordDTO> getRecord(String openId, int stars);

    List<QuestionDTO> getDatas(String openId, int stars, int groupId);

    boolean checkExist(String openId, int stars) throws Exception;

    double calcScore(String openId, int star, List chooseList) throws Exception;

    List getExamQuestion(String openId, int stars) throws Exception;

    String getQuestionAnalyse(String openId, int id, int star) throws Exception ;


    }
