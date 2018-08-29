package com.nala.faceCatch.entity.detect;

/**
 * create by lizenn
 * create date 2018/8/22
 * description
 */
public class DetectResultVO {

    private String error_code;

    private DetectVO result;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public DetectVO getResult() {
        return result;
    }

    public void setResult(DetectVO result) {
        this.result = result;
    }
}
