package com.nala.faceCatch.entity.search;

/**
 * create by lizenn
 * create date 2018/8/22
 * description 人脸对比返回结果
 */
public class SearchRootResultVO {

    /**
     * 匹对结果码
     */
    private Integer error_code;

    /**
     * 匹对状态码
     */
    private String error_msg;

    /**
     * 匹对结果
     */
    private SearchVO result;

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public SearchVO getResult() {
        return result;
    }

    public void setResult(SearchVO result) {
        this.result = result;
    }

}
