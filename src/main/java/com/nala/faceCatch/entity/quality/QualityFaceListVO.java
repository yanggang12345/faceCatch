package com.nala.faceCatch.entity.quality;

/**
 * create by lizenn
 * create date 2018/11/12
 * description
 */
public class QualityFaceListVO {

    /**
     * face token 人脸标识
     */
    private String face_token;

    /**
     * 人脸置信度：
     * 范围【0~1】，代表这是一张人脸的概率，0最小、1最大
     */
    private Double face_probability;

    /**
     * 图片质量
     */
    private QualityVO quality;


    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public Double getFace_probability() {
        return face_probability;
    }

    public void setFace_probability(Double face_probability) {
        this.face_probability = face_probability;
    }

    public QualityVO getQuality() {
        return quality;
    }

    public void setQuality(QualityVO quality) {
        this.quality = quality;
    }
}
