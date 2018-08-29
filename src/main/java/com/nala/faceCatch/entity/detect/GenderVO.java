package com.nala.faceCatch.entity.detect;

import java.io.Serializable;

/**
 * create by lizenn
 * create date 2018/8/22
 * description 性别
 */
public class GenderVO implements Serializable {

    /**
     * 类型-male:男性 female:女性
     */
    private String type;

    /**
     * 性别置信度，范围【0~1】，0代表概率最小、1代表最大
     */
    private Double probability;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
