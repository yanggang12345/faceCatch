package com.nala.faceCatch.entity.quality;

/**
 * create by lizenn
 * create date 2018/11/12
 * description 遮挡范围
 */
public class OcclusionVO {

    /**
     * 左眼被遮挡的阈值 0.6
     */
    private Double left_eye;

    /**
     * 右眼被遮挡的阈值 0.6
     */
    private Double right_eye;

    /**
     * 鼻子被遮挡的阈值 0.7
     */
    private Double nose;

    /**
     * 嘴巴被遮挡的阈值 0.7
     */
    private Double mouth;

    /**
     * 左脸颊被遮挡的阈值 0.8
     */
    private Double left_cheek;

    /**
     * 右脸颊被遮挡的阈值 0.8
     */
    private Double right_cheek;

    /**
     * 下巴被遮挡阈值 0.6
     */
    private Double chin_contour;



    public Double getLeft_eye() {
        return left_eye;
    }

    public void setLeft_eye(Double left_eye) {
        this.left_eye = left_eye;
    }

    public Double getRight_eye() {
        return right_eye;
    }

    public void setRight_eye(Double right_eye) {
        this.right_eye = right_eye;
    }

    public Double getNose() {
        return nose;
    }

    public void setNose(Double nose) {
        this.nose = nose;
    }

    public Double getMouth() {
        return mouth;
    }

    public void setMouth(Double mouth) {
        this.mouth = mouth;
    }

    public Double getLeft_cheek() {
        return left_cheek;
    }

    public void setLeft_cheek(Double left_cheek) {
        this.left_cheek = left_cheek;
    }

    public Double getRight_cheek() {
        return right_cheek;
    }

    public void setRight_cheek(Double right_cheek) {
        this.right_cheek = right_cheek;
    }

    public Double getChin_contour() {
        return chin_contour;
    }

    public void setChin_contour(Double chin_contour) {
        this.chin_contour = chin_contour;
    }
}
