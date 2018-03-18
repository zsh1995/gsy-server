package com.gsy.base.web.dto;



import com.gsy.base.web.entity.BaseEntity;
import com.gsy.base.web.entity.merge.LabelStyle;

import java.util.List;

/**
 * Created by mrzsh on 2017/11/5.
 */
public class EnterpriseInfoDTO extends BaseEntity{

    private long id;
    private String enterpriseName;
    private int state;
    private int firstIconFlag;
    private String labelBackgroundcolor1;
    private String labelBackgroundcolor2;
    private String labelBackgroundcolor3;
    private String labelColor1;
    private String labelColor2;
    private String labelColor3;
    private String labelText1;
    private String labelText2;
    private String labelText3;
    private String labelBorderColor1;
    private String labelBorderColor2;
    private String labelBorderColor3;

    private int secondIconFlag;
    private int thirdIconFlag;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;



    private List<LabelStyle> iconArray;

    public String getLabelBorderColor1() {
        return labelBorderColor1;
    }

    public void setLabelBorderColor1(String labelBorderColor1) {
        this.labelBorderColor1 = labelBorderColor1;
    }

    public String getLabelBorderColor2() {
        return labelBorderColor2;
    }

    public void setLabelBorderColor2(String labelBorderColor2) {
        this.labelBorderColor2 = labelBorderColor2;
    }

    public String getLabelBorderColor3() {
        return labelBorderColor3;
    }

    public void setLabelBorderColor3(String labelBorderColor3) {
        this.labelBorderColor3 = labelBorderColor3;
    }

    public String getLabelColor1() {
        return labelColor1;
    }

    public void setLabelColor1(String labelColor1) {
        this.labelColor1 = labelColor1;
    }

    public String getLabelColor2() {
        return labelColor2;
    }

    public void setLabelColor2(String labelColor2) {
        this.labelColor2 = labelColor2;
    }

    public String getLabelColor3() {
        return labelColor3;
    }

    public void setLabelColor3(String labelColor3) {
        this.labelColor3 = labelColor3;
    }

    public List<LabelStyle> getIconArray() {
        return iconArray;
    }

    public void setIconArray(List<LabelStyle> iconArray) {
        this.iconArray = iconArray;
    }

    public String getLabelBackgroundcolor1() {
        return labelBackgroundcolor1;
    }

    public void setLabelBackgroundcolor1(String labelBackgroundcolor1) {
        this.labelBackgroundcolor1 = labelBackgroundcolor1;
    }

    public String getLabelBackgroundcolor2() {
        return labelBackgroundcolor2;
    }

    public void setLabelBackgroundcolor2(String labelBackgroundcolor2) {
        this.labelBackgroundcolor2 = labelBackgroundcolor2;
    }

    public String getLabelBackgroundcolor3() {
        return labelBackgroundcolor3;
    }

    public void setLabelBackgroundcolor3(String labelBackgroundcolor3) {
        this.labelBackgroundcolor3 = labelBackgroundcolor3;
    }

    public String getLabelText1() {
        return labelText1;
    }

    public void setLabelText1(String labelText1) {
        this.labelText1 = labelText1;
    }

    public String getLabelText2() {
        return labelText2;
    }

    public void setLabelText2(String labelText2) {
        this.labelText2 = labelText2;
    }

    public String getLabelText3() {
        return labelText3;
    }

    public void setLabelText3(String labelText3) {
        this.labelText3 = labelText3;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getFirstIconFlag() {
        return firstIconFlag;
    }

    public void setFirstIconFlag(int firstIconFlag) {
        this.firstIconFlag = firstIconFlag;
    }

    public int getSecondIconFlag() {
        return secondIconFlag;
    }

    public void setSecondIconFlag(int secondIconFlag) {
        this.secondIconFlag = secondIconFlag;
    }

    public int getThirdIconFlag() {
        return thirdIconFlag;
    }

    public void setThirdIconFlag(int thirdIconFlag) {
        this.thirdIconFlag = thirdIconFlag;
    }

    public String getImgUrl1() {
        return imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {
        return imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }
}
