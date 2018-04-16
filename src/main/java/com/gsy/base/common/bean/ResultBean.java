package com.gsy.base.common.bean;

import com.gsy.base.web.entity.BaseEntity;

import javax.xml.transform.Result;
import java.io.Serializable;

/**
 * Created by mrzsh on 2018/2/3.
 */
public class ResultBean<T> extends BaseEntity implements Serializable{

    public static final int NO_LOGIN = -1;

    public static final int SUCCESS = 0;

    public static final int CHECK_FAIL = 1;

    public static final int NO_PERMISSION = 2;

    public static final int PARAMENT_ERRO = 3;

    public static final int UNKNOWN_EXCEPTION = -99;

    private String message = "success";

    /**
     * 返回0表示成功
     */
    private int code = SUCCESS;

    /**
     * 返回的数据
     */
    private T data;

    public ResultBean(){
        super();
    }

    public ResultBean(T data){
        super();
        this.data = data;
    }

    public ResultBean(Throwable e){
        super();
        this.message = e.toString();
        this.code=UNKNOWN_EXCEPTION;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

