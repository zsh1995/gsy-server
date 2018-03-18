package com.gsy.base.web.entity.merge;

/**
 * Created by mrzsh on 2017/11/5.
 */
public class LabelStyle {
    private long id;

    private String labelBackgroundcolor;

    private String labelColor;

    private String labelText;

    private String labelBorderColor;

    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLabelBorderColor() {
        return labelBorderColor;
    }

    public void setLabelBorderColor(String labelBorderColor) {
        this.labelBorderColor = labelBorderColor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabelBackgroundcolor() {
        return labelBackgroundcolor;
    }

    public void setLabelBackgroundcolor(String labelBackgroundcolor) {
        this.labelBackgroundcolor = labelBackgroundcolor;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getLabelText() {
        return labelText;
    }

    public void setLabelText(String labelText) {
        this.labelText = labelText;
    }
}
