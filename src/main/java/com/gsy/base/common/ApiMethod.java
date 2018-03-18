package com.gsy.base.common;



import com.gsy.base.web.dao.CommonDAO;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/7/14.
 */
@Component
public class ApiMethod {

    @Autowired
    public ApiMethod(CommonDAO commonDAO) {
        ApiMethod.commonDAO = commonDAO;
    }

    private static CommonDAO commonDAO;

    public static Integer getConstant(String constantName){
        return commonDAO.selectConst(constantName);
    }

    public static void formateResultWithDate(JSONObject result,Object data){
        result.put("data",data);
        result.put("code", 0);
        result.put("message", "OK");
    }

    public static void formateResultWithNothing(JSONObject result,boolean success){
        if(success){
            result.put("code", 0);
            result.put("message", "OK");
        } else {
            result.put("code", 1);
            result.put("message", "erro");
        }
    }

    public static void formateResultWithExcp(JSONObject result, Exception e){
        result.put("code", 1);
        result.put("message", e.toString());
    }
    //String null Or empty
    public static boolean isEmpty(String str){
        if("".equals(str) || str == null) return true;
        return false;
    }


}
