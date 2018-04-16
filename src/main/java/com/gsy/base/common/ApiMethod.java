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

    //String null Or empty
    public static boolean isEmpty(String str){
        if("".equals(str) || str == null) return true;
        return false;
    }


}
