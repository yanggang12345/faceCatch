package com.nala.faceCatch.entity.search;

import java.util.List;

/**
 * create by lizenn
 * create date 2018/8/22
 * description 人脸搜索匹对结果
 */
public class SearchVO {

    /**
     * 人脸图片token·人脸标志
     */
    private String face_token;

    /**
     * 匹配的用户信息列表
     */
    private List<UserListVO> user_list;

    public String getFace_token() {
        return face_token;
    }

    public void setFace_token(String face_token) {
        this.face_token = face_token;
    }

    public List<UserListVO> getUser_list() {
        return user_list;
    }

    public void setUser_list(List<UserListVO> user_list) {
        this.user_list = user_list;
    }
}
