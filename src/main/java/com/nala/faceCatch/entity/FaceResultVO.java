package com.nala.faceCatch.entity;

/**
 * create by lizenn
 * create date 2018/8/30
 * description
 */
public class FaceResultVO {

    /**
     * 结果状态码
     */
    private Integer error_code;


    /**
     * 结果状态
     */
    private String error_msg;



    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

}
