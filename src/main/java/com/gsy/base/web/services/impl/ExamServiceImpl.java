package com.gsy.base.web.services.impl;

import com.gsy.base.beans.ExamQueue;
import com.gsy.base.beans.RedisBean;
import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.dao.ExamQuestionMapper;
import com.gsy.base.web.dto.RedisItemDTO;
import com.gsy.base.web.entity.Question;
import com.gsy.base.web.services.ExamService;
import com.gsy.base.web.services.UserInfoService;
import com.gsy.base.web.services.payService.PayService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



/**
 * Created by mrzsh on 2018/1/28.
 */
@Service
public class ExamServiceImpl implements ExamService {

    public static Logger logger = Logger.getLogger(ExamService.class);
    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Autowired
    private RedisBean redisBean ;

    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private PayService payService;

    @Autowired
    private UserInfoService userInfoService;


    @Override
    public boolean checkExamExist(long uid,ExamHelper.ExamStar star) {
        String key = redisUid(uid,star);
        RedisItemDTO<List<Question>> qList = (RedisItemDTO<List<Question>>)redisBean.get(key);
        logger.info(qList);
        return qList != null;
    }

    private String redisUid(long uid, ExamHelper.ExamStar star){
        StringBuilder keyName = new StringBuilder();
        keyName.append(uid).append("-").append(star.getCode());
        return keyName.toString();
    }
    @Override
    public List getQuestions(long uid, ExamHelper.ExamStar star) {
        ExamQueue examQueue = (ExamQueue) beanFactory.getBean("examQueue");
        String key = redisUid(uid,star);
        RedisItemDTO<List<Question>> holder = (RedisItemDTO)redisBean.get(key.toString());
        List<Question> questionList = null;
        if(holder != null){
            questionList = holder.getItem();
        }
        if(questionList == null){
            examQueue.putExam(uid,star.getCode());
            // 从数据库随机拿6道题
            questionList = examQuestionMapper.getExamQuestions(star.getCode());
            // 封装为redis类型
            redisBean.set(key.toString(),new RedisItemDTO(questionList));
        }
        questionList.forEach((question)->{
            question.setIsPurchAnalyse(payService.checkAnalysePurched(userInfoService.getOpenId(uid), star.getCode(), (int) question.getId())?1:0);
        });
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
        RedisItemDTO<List<Question>> redisItem = (RedisItemDTO)redisBean.get(key);
        if(redisItem == null) throw new Exception("exam timeout limited");
        redisBean.delete(key);
        return ExamHelper.calculateScore(redisItem.getItem(),chooseList);
    }
}
