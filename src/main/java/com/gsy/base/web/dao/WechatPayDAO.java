package com.gsy.base.web.dao;

import com.gsy.base.web.dto.PurchAnalyseDTO;
import com.gsy.base.web.dto.PurchExamDTO;
import com.gsy.base.web.entity.merge.Prepay;
import com.gsy.base.web.entity.merge.PurchExamRecord;
import com.gsy.base.web.entity.merge.WeixinReturnStatements;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by mrzsh on 2018/2/7.
 */
@Mapper
public interface WechatPayDAO {

    @Insert("INSERT INTO weixin_return_statements (\n" +
            "\t`appid`,\n" +
            "\t`bank_type`,\n" +
            "\t`fee_type`,\n" +
            "\t`mch_id`,\n" +
            "\t`nonce_str`,\n" +
            "\t`openid`,\n" +
            "\t`out_trade_no`,\n" +
            "\t`result_code`,\n" +
            "\t`return_code`,\n" +
            "\t`sign`,\n" +
            "\t`time_end`,\n" +
            "\t`total_fee`,\n" +
            "\t`trade_type`,\n" +
            "\t`transaction_id`\n" +
            ")\n" +
            "VALUE\n" +
            "\t(\n" +
            "\t\t#{appid},#{bankType},#{feeType},#{mchId},#{nonceStr},#{openid},#{outTradeNo},#{resultCode},#{returnCode},#{sign},#{timeEnd},#{totalFee},#{tradeType},#{transactionId}\n" +
            "\t)")
    int insertNewPayReturn(WeixinReturnStatements returnData);

    @Select("SELECT" +
            " pp.id, pp.product_id, max( CASE pd.prop_name WHEN 'star' THEN pd.prop_value ELSE 0 END) AS star, max( CASE pd.prop_name WHEN 'questionId' THEN pd.prop_value ELSE 0 END ) AS questionId, max( CASE pd.prop_name WHEN 'group' THEN pd.prop_value ELSE 0 END ) AS `group`, max( CASE pd.prop_name WHEN 'fe_question_id' THEN pd.prop_value ELSE 0 END ) AS fe_question_id, max( CASE pd.prop_name WHEN 'channel' THEN pd.prop_value ELSE 0 END ) AS channel, prod.prop_value as price , UNIX_TIMESTAMP(pp.cmp_date) AS date " +
            "FROM pre_pay pp LEFT JOIN prepay_detail pd ON pp.id = pd.prepay_id LEFT JOIN product prod ON prod.product_id = pp.product_id AND prod.prop_name='price' " +
            "WHERE pp.openId = #{openId} AND transaction_flag = 1 AND pp.delete_flag = 0 GROUP BY pd.prepay_id")
    List<Map> getPurchExamRecord(String openId);

    @Select("SELECT * FROM ( SELECT pr.product_id AS product_id, MAX(CASE pr.prop_name WHEN 'star' THEN pr.prop_value ELSE 0 END) AS star, MAX(CASE pr.prop_name WHEN 'questionId' THEN pr.prop_value ELSE 0 END) AS question_id FROM purch_rel pr WHERE pr.product_id = 1 AND pr.user_openId = #{openId} AND delete_flag != 1 GROUP BY pr.group_id ) temp WHERE temp.star = #{star} AND temp.question_id = #{questionId}")
    PurchAnalyseDTO getAnalyseRecord(@Param("openId") String openId,@Param("star") int star,@Param("questionId") int questionId);

    @Select("SELECT product_id,star,MAX(avaliable_times) as avaliable_times FROM( SELECT pr.product_id AS product_id, max( CASE pr.prop_name WHEN 'star' THEN pr.prop_value ELSE 0 END) AS star, max( CASE pr.prop_name WHEN 'avaliable_times' THEN pr.prop_value ELSE 0 END ) AS avaliable_times FROM purch_rel pr WHERE pr.delete_flag != 1 AND pr.user_openId = #{openId} GROUP BY pr.group_id ) temp WHERE temp.star = #{star} AND temp.product_id != 1")
    PurchExamDTO getExamRecord(@Param("openId") String openId,@Param("star") int star);

    @Insert("INSERT INTO prepay_detail( prepay_id, product_id, prop_name, prop_value) VALUES " +
            "( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 )," +
            " ( SELECT product_id FROM product WHERE product.product_name = 'analyse' LIMIT 1 )," +
            " 'star'," +
            " #{star} )," +
            " ( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 )," +
            " ( SELECT product_id FROM product WHERE product.product_name = 'analyse' LIMIT 1 )," +
            " 'questionId'" +
            ", " +
            "#{questionId} ), " +
            "( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 )," +
            " ( SELECT product_id FROM product WHERE product.product_name = 'analyse' LIMIT 1 )," +
            " 'group'," +
            " #{group} ), " +
            "( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 )," +
            " ( SELECT product_id FROM product WHERE product.product_name = 'analyse' LIMIT 1 )," +
            " 'fe_question_id'," +
            " #{feQuestion} )," +
            " ( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 )," +
            " ( SELECT product_id FROM product WHERE product.product_name = 'analyse' LIMIT 1 )," +
            " 'channel'," +
            " #{channel} )")
    int insertNewPreAnalyseDetail(@Param("tradeNo") String tradeNo,@Param("star") int star,@Param("questionId") int questionId,@Param("group") int group ,@Param("feQuestion") int feQuestion ,@Param("channel") int channel);



    @Insert("INSERT INTO prepay_detail( prepay_id, product_id, prop_name, prop_value) VALUES " +
            "( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 )," +
            "( SELECT product_id FROM product WHERE product.product_name = 'exam' AND product.prop_name = 'star' AND product.prop_value = #{star} LIMIT 1 ), 'star', #{star} )," +
            "( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 ), " +
            "( SELECT product_id FROM product WHERE product.product_name = 'exam' AND product.prop_name = 'star' AND product.prop_value = #{star} LIMIT 1 ), 'avaliable_times', ( SELECT prd2.prop_value FROM product prd1 LEFT JOIN product prd2 ON prd2.product_id = prd1.product_id WHERE prd1.product_name = 'exam' AND prd1.prop_name = 'star' AND prd1.prop_value = '1' AND prd2.prop_name = 'avaliable_times' ) ), ( IFNULL( ( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} ), 0 ), ( SELECT product_id FROM product WHERE product.product_name = 'exam' AND product.prop_name = 'star' AND product.prop_value = #{star} LIMIT 1 ), 'channel', #{channel} )")
    int insertNewExam(@Param("tradeNo") String tradeNo,@Param("star") int star,@Param("channel") int channel);

    @Insert("INSERT INTO prepay_detail( prepay_id, product_id, prop_name, prop_value) VALUES " +
            "( " +
            "( SELECT id FROM pre_pay WHERE pre_pay.out_trade_no = #{tradeNo} )," +
            " ( SELECT product_id FROM product WHERE product.product_name = 'returnable' LIMIT 1 )," +
            " ''," +
            " '' )")
    int insertNewReturnable(String tradeNo);

    @Insert("INSERT INTO pre_pay (out_trade_no,openId,product_id,transaction_flag,create_date)VALUES (#{outTradeNo},#{openId},#{productId},#{transactionFlag},NOW())")
    int insertNewPrePay(Prepay prepay);

    @Insert("INSERT INTO purch_rel( product_id,group_id , prop_name, prop_value, user_openId, create_date) ( SELECT prd.product_id, prepay_id ,prd.prop_name,prd.prop_value, prp.openId,NOW() FROM pre_pay prp LEFT JOIN prepay_detail prd ON prd.prepay_id = prp.id WHERE prp.out_trade_no = #{tradeNo}  AND prp.transaction_flag = 0)")
    int selectedIntoPurRel(String tradeNo);


    @Update("UPDATE pre_pay SET transaction_flag = 1 WHERE out_trade_no = #{outTradeNo}")
    @Options(useGeneratedKeys = true)
    long updatePrePay(Prepay prepay);

    @Select("SELECT prop_value as price FROM product WHERE product_id = #{productId} AND prop_name='price'")
    long getPrice(int productId);

}
