package com.gsy.base.beans;

import com.gsy.base.common.exam.ExamHelper;
import com.gsy.base.web.services.ExamService;
import com.gsy.base.web.services.uerRight.UserRightService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Created by mrzsh on 2018/4/16.
 */
@Scope("prototype")
@Component
public class ExamTask implements Runnable {

    public static Logger logger = Logger.getLogger(ExamTask.class);

    @Autowired
    ExamService examService;
    @Autowired
    UserRightService userRightService;
    //test
    private long uid;
    private int examStar;
    //end test

    public ExamTask(long uid,int examStar){
        this.uid = uid;
        this.examStar = examStar;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getExamStar() {
        return examStar;
    }

    public void setExamStar(int examStar) {
        this.examStar = examStar;
    }

    @Override
    public void run() {
        try {
            logger.info(String.format("cleaning,uid={},examStar={}",uid,examStar));
            examService.deleteExam(uid, ExamHelper.ExamStar.build(examStar));
            userRightService.updateUserExamStatus(uid,examStar,0.0F);
        } catch (Exception e) {
            logger.error("examtask run erro",e);
        }
    }
}
