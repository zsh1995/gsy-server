package com.gsy.base.web.entity;

import java.io.Serializable;

/**
 * Created by mrzsh on 2018/1/28.
 */
public class User implements Serializable{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString(){
        return this.name;
    }

}
