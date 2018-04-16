package com.gsy.base.web.controller.ajaxcontroller;

import com.gsy.base.common.ApiConst;
import com.gsy.base.common.ApiMethod;
import com.gsy.base.common.aop.AjaxControllerAOP;
import com.gsy.base.common.bean.ResultBean;
import com.gsy.base.common.exceptions.NoPermissionException;
import com.gsy.base.web.dto.InvitorCountDTO;
import com.gsy.base.web.dto.UserInfoDTO;
import com.gsy.base.web.entity.User;
import com.gsy.base.web.entity.UserInfoEntity;
import com.gsy.base.web.services.UploadScoreService;
import com.gsy.base.web.services.UserInfoService;
import com.gsy.base.web.services.coupon.CouponService;
import com.gsy.base.web.services.payService.PayService;
import com.gsy.base.web.services.uerRight.UserRightService;
import com.qcloud.weapp.authorization.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mrzsh on 2018/2/5.
 */
@RestController
@RequestMapping("/ajax/user")
public class UserController {
    private static Logger logger =  LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    CouponService couponService;
    @Autowired
    PayService payService;
    @Autowired
    UserRightService userRightService;


    /**
     * 根据Id获取用户详情（应用在填写邀请人Id时的搜索功能）
     */
    @RequestMapping("/invitor/getUserInfo")
    public ResultBean getUserInfoById(UserInfoEntity userInfo,
                                     @RequestParam long userId){
        return new ResultBean(userInfoService.getInvitor(userId));
    }

    /**
     *  获取邀请者
     * @param userInfo
     * @return
     */
    @RequestMapping("/invitor/getInvitor")
    public ResultBean getUserInfoById(UserInfoEntity userInfo){
        return new ResultBean(userInfoService.getMyInvitor(userInfo.getOpenId()));
    }

    /**
     * 获取被用户邀请的用户列表
     *
     */
    @RequestMapping("/invitor/getInvitedList")
    public ResultBean getInvitorListByOpenId(UserInfoEntity userInfo){
        return new ResultBean(userInfoService.getAllInvite(userInfo.getOpenId()));
    }

    /**
     * 获取被用户邀请的人总数
     */
    @RequestMapping("/invitor/getCount")
    public ResultBean getIvitedCountByOpenId(UserInfoEntity userInfo,
                                             @RequestParam int star){
        int count = userInfoService.getInviteNums(userInfo.getOpenId());
        int needIvitor = ApiMethod.getConstant(ApiConst.NEED_INIVITE_PRE+star);
        return new ResultBean(new InvitorCountDTO(count,needIvitor));
    }

    /**
     * 设置邀请人
     */
    @RequestMapping("/invitor/setInvitor")
    public ResultBean uploadInvitorId(UserInfoEntity userInfo,
                                      @RequestParam long invitorId){
        userInfoService.updateUserInvitor(invitorId,userInfo.getOpenId());
        couponService.bindExamCoupon(userInfo.getOpenId(),2);
        return new ResultBean();
    }

    /**
     * 获取用户的星级
     */
    @RequestMapping("/getRank")
    public ResultBean getStarRank(UserInfoEntity userInfo) throws Exception {
        return new ResultBean(userInfoService.calcAuthRank(userInfo.getOpenId()));
    }

    /**
     * 获取用户锻炼情况
     */
    @RequestMapping("/exerciseStatus")
    public ResultBean getExerciseStatus(UserInfoEntity userInfo){
        return new ResultBean(userInfoService.getExercisePass(userInfo.getOpenId()));
    }

    /**
     * 获取用户实践情况
     */
    @RequestMapping("/practiceStatus")
    public ResultBean getPracticeStatus(UserInfoEntity userInfo){
        return new ResultBean(userInfoService.getPracticePass(userInfo.getOpenId()));
    }

    /**
     * 获取用户身份信息
     */
    @RequestMapping("/detailInfo")
    public ResultBean getUserInfo(UserInfoEntity userInfo){
        return new ResultBean(userInfoService.getUserInfo(userInfo.getOpenId()));
    }
    /**
     * 上传用户身份信息
     */
    @RequestMapping("/uploadUserInfo")
    public ResultBean uploadUserInfo(UserInfoEntity userInfo,
                                     @RequestBody UserInfoDTO userInfoDTO) throws Exception {
        if(ApiConst.USER_CHANNEL_ENROLL.equals(String.valueOf(userInfoDTO.getUserChannel()))){
            //赠送优惠券
            userRightService.sendGiftCoupon(userInfo.getOpenId());
        }
        userInfoDTO.setOpenId(userInfo.getOpenId());
        userInfoService.updateUserInfo(userInfoDTO);
        return new ResultBean();
    }

    /**
     * 获取购买记录列表
     */
    @RequestMapping("/purchRecord")
    public ResultBean getPurchRecord(UserInfoEntity userInfo){
        return new ResultBean(userInfoService.getPurchExamRecord(userInfo.getOpenId()));
    }
    /**
     * 校验用户是否购买某解析
     */
    @RequestMapping("/checkPurchedAnalyse")
    public ResultBean checkPurchedAnalyse(UserInfoEntity userInfo,
                                          @RequestParam int star,
                                          @RequestParam int questionId){
        boolean isPurched = payService.checkAnalysePurched(userInfo.getOpenId(),star,questionId);
        if(ApiConst.TEST_SHENHE){
            isPurched = true;
        }
        // todo 下一版删除
        Map temp = new HashMap();
        temp.put("isPurched",isPurched);
        return new ResultBean(temp);
    }
    /**
     * 校验用户是否购买保证金
     */
    @RequestMapping("/checkPurchedReturnable")
    public ResultBean checkPurchedRetrunable(UserInfoEntity userInfo) throws Exception {
        boolean isPurched = userRightService.checkReturnalbePurched(userInfo.getOpenId());
        if(ApiConst.TEST_SHENHE){
            isPurched = true;
        }
        return new ResultBean(isPurched);
    }
    /**
     * 获取用户考试剩余次数
     */
    @RequestMapping("/getExamAvaliableTime")
    public ResultBean getExamAvaliableTime(UserInfoEntity userInfo,
                                           @RequestParam int star){
        int remainTimes = userRightService.checkExamAvaliableTime(userInfo.getOpenId(),star);
        return new ResultBean(remainTimes);
    }
    /**
     *  上传用户想要内推的公司
     */
    @RequestMapping("/uploadCompanys")
    public ResultBean uploadCompanys(UserInfoEntity userInfo,
                                     @RequestParam List<String> companys){
        return new ResultBean(userInfoService.addCompanys(companys,userInfo.getOpenId()));
    }

}
