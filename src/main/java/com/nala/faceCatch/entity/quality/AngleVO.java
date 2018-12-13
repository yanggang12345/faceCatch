package com.nala.faceCatch.entity.quality;

import java.io.Serializable;

/**
 * create by lizenn
 * create date 2018/11/20
 * description 图片中的人脸角度
 */
public class AngleVO {

    /**
     * 三维旋转之俯仰角度[-90(上), 90(下)]
     */
    private Double yaw;

    /**
     * 平面内旋转角[-180(逆时针), 180(顺时针)]
     */
    private Double pitch;

    /**
     * 三维旋转之左右旋转角[-90(左), 90(右)]
     */
    private Double roll;

    public Double getYaw() {
        return yaw;
    }

    public void setYaw(Double yaw) {
        this.yaw = yaw;
    }

    public Double getPitch() {
        return pitch;
    }

    public void setPitch(Double pitch) {
        this.pitch = pitch;
    }

    public Double getRoll() {
        return roll;
    }

    public void setRoll(Double roll) {
        this.roll = roll;
    }

}
