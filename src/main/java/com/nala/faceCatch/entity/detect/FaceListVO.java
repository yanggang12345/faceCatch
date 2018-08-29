package com.nala.faceCatch.entity.detect;

import java.io.Serializable;

/**
 * create by lizenn
 * create date 2018/8/22
 * description
 */
public class FaceListVO implements Serializable {

    /**
     * 人脸在图片中的位置
     */
    private LocationVO location;

    /**
     * 人脸置信度，范围【0~1】，代表这是一张人脸的概率，0最小、1最大
     */
    private Double face_probability;

    /**
     * 识别年龄
     */
    private Double age;

    /**
     * 美丑打分，范围0-100，越大表示越美
     */
    private Long beauty;

    /**
     * 性别
     */
    private GenderVO gender;

    /**
     * 人脸图片质量
     */
    private QualityVO quality;


    public LocationVO getLocation() {
        return location;
    }

    public void setLocation(LocationVO location) {
        this.location = location;
    }

    public Double getFace_probability() {
        return face_probability;
    }

    public void setFace_probability(Double face_probability) {
        this.face_probability = face_probability;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Long getBeauty() {
        return beauty;
    }

    public void setBeauty(Long beauty) {
        this.beauty = beauty;
    }

    public GenderVO getGender() {
        return gender;
    }

    public void setGender(GenderVO gender) {
        this.gender = gender;
    }

    public QualityVO getQuality() {
        return quality;
    }

    public void setQuality(QualityVO quality) {
        this.quality = quality;
    }
}
