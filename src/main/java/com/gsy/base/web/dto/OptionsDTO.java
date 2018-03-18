package com.gsy.base.web.dto;

/**
 * Created by Administrator on 2017/6/3.
 */
public class OptionsDTO {
    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private String tip;
    private String content;
    private String color;
}
