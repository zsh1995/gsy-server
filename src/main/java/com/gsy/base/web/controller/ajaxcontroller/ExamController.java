package com.gsy.base.web.controller.ajaxcontroller;

import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.web.dto.PracticeRecordDTO;
import com.gsy.base.web.entity.UserInfoEntity;
import com.gsy.base.web.services.PracticeService;
import com.gsy.base.web.services.UploadScoreService;
import com.gsy.base.web.services.payService.PayService;
import com.gsy.base.web.services.uerRight.UserRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by mrzsh on 2018/2/5.
 */
@RestController
@RequestMapping("/ajax/exam")
public class ExamController {

    @Autowired
    PayService payService;
    @Autowired
    PracticeService practiceService;
    @Autowired
    UserRightService userRightService;
    @Autowired
    UploadScoreService uploadScoreService;

    /**
     *  获取考试是否处于进行状态
     */
    @RequestMapping("/examProcess")
    public ResultBean checkExamProcess(UserInfoEntity userInfo,
                                       @RequestParam int star) throws Exception{
        return new ResultBean(practiceService.checkExist(userInfo.getOpenId(),star));
    }

    /**
     * 获取考试题目
     */
    @RequestMapping("/getExamQuestions")
    public ResultBean getExamQuestions(UserInfoEntity userInfo,
                                      @RequestParam int star) throws Exception {
        int remainTimes = payService.checkExamPurched(userInfo.getOpenId(),star);
        if(!practiceService.checkExist(userInfo.getOpenId(),star)){
            if(remainTimes > 0){
                userRightService.updateExamTimes(userInfo.getOpenId(),star,remainTimes - 1,1);
            }else {
                throw new NoPermissionException("user right erro");
            }
        }
        return new ResultBean(practiceService.getExamQuestion(userInfo.getOpenId(),star));
    }

    /**
     * 交卷，提交选项
     */
    @RequestMapping("/finishedExam")
    public ResultBean finishedExam(UserInfoEntity userInfo,
                                   @RequestParam int star,
                                   @RequestParam List<Integer>chooseList) throws Exception {
        double score = practiceService.calcScore(userInfo.getOpenId(),star,chooseList);
        userRightService.updateUserExamStatus(userInfo.getOpenId(),star,score);
        return new ResultBean(score);
    }


    /**
     * 获取练习题目
     */
    @RequestMapping("/getQuestions")
    public ResultBean getQuestions(UserInfoEntity userInfo,
                                   @RequestParam int star,
                                   @RequestParam int groupId){
        return new ResultBean(practiceService.getDatas(userInfo.getOpenId(),star,groupId));
    }

    /**
     * 上传练习分数
     */
    @RequestMapping("/uploadScore")
    public ResultBean uploadPracticeScore(UserInfoEntity userInfo,
                                          @RequestParam int star,
                                          @RequestParam int groupId,
                                          @RequestParam double score){
        PracticeRecordDTO dto = new PracticeRecordDTO();
        dto.setQuestionGroup(groupId);
        dto.setStars(star);
        dto.setScore(score);
        uploadScoreService.uploadScore(userInfo.getOpenId(),dto);
        return new ResultBean();
    }
    /**
     * 获取练习成绩
     */
    @RequestMapping("/getPracticeScore")
    public ResultBean getPracticeScore(UserInfoEntity userInfo,
                                       @RequestParam int star){
        return new ResultBean(practiceService.getRecord(userInfo.getOpenId(),star));
    }

    /**
     * 获取解析内容
     */
    @RequestMapping("/getAnalyse")
    public ResultBean getAnalyse(UserInfoEntity userInfo,
                                 @RequestParam int star,
                                 @RequestParam int questionId) throws Exception {
        return new ResultBean(practiceService.getQuestionAnalyse(userInfo.getOpenId(),questionId,star));
    }
    /**
     * 获取考试可用次数
     */
    @RequestMapping(value = "/getExamAvaliableTime")
    public ResultBean getExamAvaliableTime(UserInfoEntity userInfo,
                                           @RequestParam int star){
        return new ResultBean(userRightService.checkExamAvaliableTime(userInfo.getOpenId(),star));
    }
    /**
     * 获取考试通过次数
     */
    @RequestMapping(value = "/getExamPassTime")
    public ResultBean getExamPassTime(UserInfoEntity userInfo,
                                      @RequestParam int star) throws Exception {
        return new ResultBean(userRightService.getExamStatus(userInfo.getOpenId(),star));
    }


}
