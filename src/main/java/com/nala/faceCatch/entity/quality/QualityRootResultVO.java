package com.nala.faceCatch.entity.quality;

import com.nala.faceCatch.entity.FaceResultVO;

/**
 * create by lizenn
 * create date 2018/11/12
 * description
 */
public class QualityRootResultVO extends FaceResultVO {

//    /**
//     * 状态码
//     */
//    private Integer error_code;
//
//    /**
//     * 状态信息
//     */
//    private String error_msg;

    /**
     * 质量检测结果
     */
    private QualityResultVO result;


    public QualityResultVO getResult() {
        return result;
    }

    public void setResult(QualityResultVO result) {
        this.result = result;
    }
}
