package com.nala.faceCatch.entity.search;

/**
 * create by lizenn
 * create date 2018/8/22
 * description 匹配的用户信息列表
 */
public class UserListVO {

    /**
     * 用户所属的group_id
     */
    private String group_id;

    /**
     * 用户的user_id
     */
    private String user_id;

    /**
     * 注册用户时携带的user_info
     */
    private String user_info;

    /**
     * 用户的匹配得分，推荐阈值80分
     */
    private float score;


    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
