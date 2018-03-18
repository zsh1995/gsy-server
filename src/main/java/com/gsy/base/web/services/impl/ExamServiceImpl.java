package com.gsy.base.web.services.impl;

import com.gsy.base.beans.RedisBean;
import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.dao.ExamQuestionMapper;
import com.gsy.base.web.entity.Question;
import com.gsy.base.web.services.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by mrzsh on 2018/1/28.
 */
@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Autowired
    private RedisBean redisBean ;

    @Override
    public boolean checkExamExist(long uid,ExamHelper.ExamStar star) {
        String key = redisUid(uid,star);
        List qList = (List)redisBean.get(key);
        return qList != null;
    }

    private String redisUid(long uid, ExamHelper.ExamStar star){
        StringBuilder keyName = new StringBuilder();
        keyName.append(uid).append("-").append(star.getCode());
        return keyName.toString();
    }
    @Override
    public List getQuestions(long uid, ExamHelper.ExamStar star) {
        String key = redisUid(uid,star);
        List<Question> questionList = (List)redisBean.get(key.toString());
        if(questionList != null){
            return questionList;
        }
        // 从数据库随机拿6道题
        questionList = examQuestionMapper.getExamQuestions(star.getCode());
        // 封装为redis类型
        redisBean.set(key.toString(),questionList);
        return questionList;
    }
    @Override
    public void deleteExam(long uid, ExamHelper.ExamStar star) {
        String key = redisUid(uid,star);
        redisBean.delete(key);
    }

    @Override
    public double calcScore(long uid, ExamHelper.ExamStar star, List<Integer> chooseList) throws Exception {
        String key = redisUid(uid,star);
        List qList = (List)redisBean.get(key);
        if(qList == null) throw new Exception("exam timeout limited");
        redisBean.delete(key);
        return ExamHelper.calculateScore(qList,chooseList);
    }
}
