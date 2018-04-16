package com.gsy.base.common;

import com.gsy.base.beans.RedisBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by mrzsh on 2018/3/29.
 */
@Component
public class RedisLockHelper {
    @Autowired
    RedisBean redisBean;

    public static final String USERINFO_LOCK ="wechat_userinfo";

    public static final String OPENID_LOCK ="openId";

    private static final String SPLIT_FLAG ="_";

    public boolean getLock(String table,String keyColumn,String item){
        String keyName = table + SPLIT_FLAG + keyColumn + SPLIT_FLAG + item;
        if(redisBean.get(keyName) != null && redisBean.get(keyName).toString().equals("true")) {
            return false;
        }
        redisBean.set(keyName,true,800);
        return true;
    }
    public void releaseLock(String table,String keyColumn,String item){
        String keyName = table + SPLIT_FLAG + keyColumn + SPLIT_FLAG + item;
        redisBean.set(keyName,false);
        redisBean.delete(keyName);
    }

}
