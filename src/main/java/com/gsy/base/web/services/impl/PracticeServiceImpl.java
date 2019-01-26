package com.gsy.base.web.services.impl;


import com.gsy.base.common.ApiConst;
import com.gsy.base.common.ApiMethod;
import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.dao.QuestionDAO;
import com.gsy.base.web.dao.UploadScoreDAO;
import com.gsy.base.web.dao.UserDAO;
import com.gsy.base.web.dto.PracticeRecordDTO;
import com.gsy.base.web.dto.QuestionDTO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.services.ExamService;
import com.gsy.base.web.services.PracticeService;
import com.gsy.base.web.services.payService.PayService;
import com.gsy.base.web.services.uerRight.UserRightService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/17.
 */
@Service
public class PracticeServiceImpl implements PracticeService {
    @Autowired
    UserRightService userRightService;
    @Autowired
    ExamService examService;

    @Autowired
    QuestionDAO questionDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UploadScoreDAO uploadScoreDAO;
;

    @Autowired
    PayService payService;
    private static Logger log = Logger.getLogger(PracticeService.class);

    @Override
    public List<PracticeRecordDTO> getRecord(String openId , int stars) {

        PracticeRecordDTO practiceRecordDTO= new PracticeRecordDTO();
        UserInfoDTO dto = uploadScoreDAO.findIdByOpenId(openId);
        return uploadScoreDAO.getScore(dto.getId(),stars);
    }

    @Override
    public List<QuestionDTO> getDatas(String openId, int stars, int groupId) {

        List<QuestionDTO> questions = new ArrayList<QuestionDTO>();
        if(1 == stars){
            questions = questionDAO.getQuestionLove(openId,groupId);
        }
        if(2 == stars){
            questions = questionDAO.getQuestionSocial(openId,groupId);
        }
        if(3 == stars){
            questions = questionDAO.getQuestionWork(openId,groupId);
        }
        for(QuestionDTO temp : questions){
            if( !ApiMethod.isEmpty(temp.getAnalysis())){
                temp.setAnalysis("存在解析");
            }
        }
        if(ApiConst.TEST_SHENHE){
            for(QuestionDTO temp : questions){
                temp.setIsPurchAnalyse(100);
            }
        }
        return questions;
    }

    @Override
    public boolean checkExist(String openId, int stars) throws Exception {
        long uid = userDAO.getUserInfo(openId).getId();
        return examService.checkExamExist(uid, ExamHelper.ExamStar.build(stars));
    }

    @Override
    public double calcScore(String openId, int star, List chooseList) throws Exception {

        long uid = userDAO.getUserInfo(openId).getId();
        return examService.calcScore(uid, ExamHelper.ExamStar.build(star),chooseList);

    }

    @Override
    public List getExamQuestion(String openId, int stars) throws Exception {
        long uid = userDAO.getUserInfo(openId).getId();
        return examService.getQuestions(uid, ExamHelper.ExamStar.build(stars));
    }

    @Override
    public String getQuestionAnalyse(String openId, int id,int star) throws Exception {
        //校验是否有该题权限
        //如果没有跑出异常
        String analyseContent = "";
        if(!ApiConst.TEST_SHENHE) {
            if(!payService.checkAnalysePurched(openId,star,id)){
                throw new Exception("没有权限！");
            }
        }
        switch (star) {
            case 1:
                analyseContent = questionDAO.getAnalyseLove(id);break;
            case 2:
                analyseContent = questionDAO.getAnalyseWork(id);break;
            case 3:
                analyseContent = questionDAO.getAnalyseSocial(id);break;
        }
        return analyseContent;

    }



    public static void main(String[] args){
        PracticeService practiceService = new PracticeServiceImpl();
        try {
            practiceService.getQuestionAnalyse("o5TL-0FErAiYMqeXGCs0T1N2KlP4",2,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
