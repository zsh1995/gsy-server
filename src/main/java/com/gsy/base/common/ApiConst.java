package com.gsy.base.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/7.
 */
@Component
public class ApiConst {

    private Logger LOG = LoggerFactory.getLogger(ApiConst.class);

    @Value("${gsy.shenhe.test}")
    public void setShenHe(boolean test) {
        ApiConst.TEST_SHENHE = test;
        LOG.info("shenhe is:{}",test);
    }

    public static final String FORMATE_DATE = "yyyyMMdd";

    public static final int PURCH_TYPE_EXAM = 0;

    public static final String RIGHT_TYPE_EXAM = "exam";//在user-right表中考试类型值

    public static final int PRODUCT_ANALYSE = 1;
    public static final int PRODUCT_EXAM_1 = 2;
    public static final int PRODUCT_EXAM_2 =3;
    public static final int PRODUCT_EXAM_3 =4;
    public static final int PRODUCT_RETURNABLE = 5;

    public static final int PURCH_CHANNEL_NORMAL = 1;
    public static final int PURCH_CHANNEL_COUPON = 2;


    public static final String RIGHT_TYPE_ANALYSE = "analyse";

    public static final int PURCH_TYPE_ANALYSE = 1;

    public static final int PASS_EXAM = 0;

    public static final int FAIL_EXAM = 1;

    public static final String PRICE_EXAM_STAR1="PRICE_EXAM_STAR1";

    public static final String PRICE_EXAM_STAR2="PRICE_EXAM_STAR2";

    public static final String PRICE_EXAM_STAR3="PRICE_EXAM_STAR3";

    public static final String PRICE_ANALYSE="PRICE_ANALYSE";

    public static final String ANALYSE_FREE_THREE_TIME = "ANALYSE_FREE";

    public static final String EXAM_FREE_THREE_TIME = "EXAM_FREE";

    public static boolean TEST_SHENHE=false;

    public static final String NEED_INIVITE_PRE = "NUM_INIVOT_STAR";

    public static final String NEED_INVITE_1 = "NUM_INIVOT_STAR1";

    public static final String NEED_INVITE_2 = "NUM_INIVOT_STAR2";

    public static final String NEED_INVITE_3 = "NUM_INIVOT_STAR3";

    public static final int[] NEEDED_PASSTIMES = {3,4,5};

    public static final String PASS_EXAM_PRE="NUM_EXAM_PASS_STAR";

    public static final String USER_CHANNEL_NORMAL ="0";

    public static final String USER_CHANNEL_ENROLL ="1";

}
