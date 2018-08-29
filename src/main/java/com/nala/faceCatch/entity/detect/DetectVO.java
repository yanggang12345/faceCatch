package com.nala.faceCatch.entity.detect;

import java.io.Serializable;
import java.util.List;

/**
 * create by lizenn
 * create date 2018/8/22
 * description 人脸检测结果封装类
 */
public class DetectVO implements Serializable {

    /**
     * 人脸个数
     */
    private Integer face_Num;

    /**
     * 人脸token及检测结果集
     */
    private List<FaceListVO> face_list;



    public Integer getFace_Num() {
        return face_Num;
    }

    public void setFace_Num(Integer face_Num) {
        this.face_Num = face_Num;
    }

    public List<FaceListVO> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceListVO> face_list) {
        this.face_list = face_list;
    }
}
