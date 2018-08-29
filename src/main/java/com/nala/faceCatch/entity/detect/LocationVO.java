package com.nala.faceCatch.entity.detect;

import java.io.Serializable;

/**
 * create by lizenn
 * create date 2018/8/22
 * description
 */
public class LocationVO implements Serializable {

    /**
     * 人脸区域离左边界的距离
     */
    private Double left;

    /**
     * 人脸区域离上边界的距离
     */
    private Double top;

    /**
     * 人脸区域的宽度
     */
    private Double width;

    /**
     * 人脸区域的高度
     */
    private Double height;

    public Double getLeft() {
        return left;
    }

    public void setLeft(Double left) {
        this.left = left;
    }

    public Double getTop() {
        return top;
    }

    public void setTop(Double top) {
        this.top = top;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }
}
