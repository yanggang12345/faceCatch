package com.nala.faceCatch.entity.quality;

import com.nala.faceCatch.entity.FaceResultVO;

import java.util.List;

/**
 * create by lizenn
 * create date 2018/11/12
 * description
 */
public class QualityResultVO{

    /**
     * 检测的人脸图片张数
     */
    private Integer face_num;

    /**
     * 检测的人脸列表
     */
    private List<QualityFaceListVO> face_list;


    public Integer getFace_num() {
        return face_num;
    }

    public void setFace_num(Integer face_num) {
        this.face_num = face_num;
    }

    public List<QualityFaceListVO> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<QualityFaceListVO> face_list) {
        this.face_list = face_list;
    }

    @Override
    public String toString() {
        return "QualityResultVO{" +
                "face_num=" + face_num +
                ", face_list=" + face_list +
                '}';
    }
}
