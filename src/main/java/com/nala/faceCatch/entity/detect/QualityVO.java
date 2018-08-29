package com.nala.faceCatch.entity.detect;

import java.io.Serializable;

/**
 * create by lizenn
 * create date 2018/8/22
 * description
 */
public class QualityVO implements Serializable {

    /**
     * 人脸模糊程度 ，范围[0~1]，0表示清晰，1表示模糊
     */
    private Double blur;

    /**
     * 取值范围在[0~255], 表示脸部区域的光照程度 越大表示光照越好
     */
    private Double illumination;

    /**
     * 人脸完整度，0或1, 0为人脸溢出图像边界，1为人脸都在图像边界内
     */
    private Long completeness;

    public Double getBlur() {
        return blur;
    }

    public void setBlur(Double blur) {
        this.blur = blur;
    }

    public Double getIllumination() {
        return illumination;
    }

    public void setIllumination(Double illumination) {
        this.illumination = illumination;
    }

    public Long getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Long completeness) {
        this.completeness = completeness;
    }
}
