package com.nala.faceCatch.entity.quality;

/**
 * create by lizenn
 * create date 2018/11/12
 * description
 */
public class QualityVO {

    /**
     * 遮挡范围:
     * 取值范围[0~1]，0为无遮挡，1是完全遮挡
     * 含有多个具体子字段，表示脸部多个部位
     * 通常用作判断头发、墨镜、口罩等遮挡
     */
    private OcclusionVO occlusion;

    /**
     * 模糊度范围:
     * 取值范围[0~1]，0是最清晰，1是最模糊
     */
    private Double blur;

    /**
     * 光照范围:
     * 取值范围[0~255]
     * 脸部光照的灰度值，0表示光照不好
     */
    private Double illumination;

    /**
     * 人脸完整度:
     * （0或1），0为人脸溢出图像边界，
     * 1为人脸都在图像边界内
     */
    private Integer completeness;


    public OcclusionVO getOcclusion() {
        return occlusion;
    }

    public void setOcclusion(OcclusionVO occlusion) {
        this.occlusion = occlusion;
    }

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

    public Integer getCompleteness() {
        return completeness;
    }

    public void setCompleteness(Integer completeness) {
        this.completeness = completeness;
    }
}
