package com.gsy.base.web.dao;

import com.gsy.base.web.entity.merge.Coupon;
import com.gsy.base.web.entity.merge.UserCoupon;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by mrzsh on 2018/2/7.
 */
@Mapper
public interface CouponDAO {

    @Insert("INSERT INTO coupon_user_rel (user_openId, couponId, source, create_time, used_flag) SELECT wu.openId, cp.id , #{source} ,NOW(), 0 FROM coupon cp LEFT JOIN wechat_userinfo wu ON wu.id = #{id} WHERE cp.code_name = #{coupon}")
    int inserNewCouponRel(@Param("id") long id,@Param("coupon") String coupon,@Param("source") int source);

    @Select("SELECT cur.id,cp.code_name, count(cp.code_name) AS aval_times FROM coupon_user_rel cur LEFT JOIN coupon cp ON cp.id = cur.couponId WHERE cur.user_openId = #{openId} AND cur.delete_flag = 0 AND cur.used_flag != 1 AND cp.code_name= #{couponName}")
    List<Coupon> queryCouponOfUser(@Param("openId") String openId,@Param("couponName") String couponName);

    @Update("UPDATE coupon_user_rel cur LEFT JOIN coupon cp ON cp.id = cur.couponId SET used_flag = 1 WHERE cur.user_openId = #{openId} AND cp.code_name = #{couponName} AND cur.id=#{couponId}")
    int useCoupon(@Param("openId") String openId,@Param("couponName") String couponName,@Param("couponId") int couponId);

    @Insert("INSERT INTO coupon_record (`user_openId`,`coupon_id`,`create_time`) SELECT #{openId},id,NOW() FROM coupon WHERE code_name = #{couponName} LIMIT 1")
    int logUseCoupon(@Param("openId") String openId,@Param("couponName") String couponName);

    /*
    @Insert("INSERT INTO wechat_user_right ( openId,type,questionId,star,create_time) VALUES (#{openId},'analyse',#{questionId},#{star},NOW())")
    int analyseCouponUse(@Param("openId") String openId,@Param("questionId") int questionId,@Param("star") int star);

    @Insert("INSERT INTO wechat_user_right( openId, type, star, remain_times, create_time) SELECT #{openId},'exam',#{star},dict_value,NOW() FROM dict_table WHERE dict_name = 'TIMES_PER_PURCH'")
    int examCouponUse(@Param("openId") String openId,@Param("star") int star);

    */
    @Select("SELECT cur.couponId,count(*) as remain_times FROM coupon_user_rel cur LEFT JOIN coupon cp ON cp.id = cur.couponId WHERE user_openId = #{openId} AND cp.code_name = #{couponName} AND cur.used_flag!=1 AND cur.delete_flag!=1")
    UserCoupon selectCouponsRemain(@Param("openId") String openId,@Param("couponName") String couponName);

    @Select("SELECT\n" +
            "\tcouponId,\n" +
            "\tcreate_time,\n" +
            "\tsource,\n" +
            "\tused_flag\n"+
            "FROM\n" +
            "\tcoupon_user_rel\n" +
            "WHERE\n" +
            "\tuser_openId = #{openId} AND delete_flag !=1")
    List<UserCoupon> queryAllCoupons(String openId);


}