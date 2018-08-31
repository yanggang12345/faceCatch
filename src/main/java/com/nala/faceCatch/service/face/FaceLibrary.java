package com.nala.faceCatch.service.face;

/**
 * create by lizenn
 * create date 2018/7/30
 * description 人脸库
 */

import com.nala.faceCatch.util.*;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class FaceLibrary {

    private static String accessToken = GetToken.getAuth();

    /**
     * 新增人脸（人脸注册）
     *
     * @return
     */
    public static String add(byte[] image, String groupId, String userId, String userInfo) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            String baseImage = ImageUtil.encode(image);
            Map<String, Object> map = new HashMap<>();
            map.put("image", baseImage);
            map.put("group_id", groupId);
            map.put("user_id", userId);
            map.put("user_info", userInfo);
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 人脸删除
     * @param image
     * @param groupId
     * @param userId
     * @param userInfo
     * @return
     */
    public static String delete(String groupId, String userId, String faceToken) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/delete";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("group_id", groupId);
            map.put("user_id", userId);
            map.put("face_token", faceToken);

            String param = GsonUtils.toJson(map);


            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 人脸库·更新人脸
     *
     * @param image
     * @return
     */
    public static String update(byte[] image, String groupId, String userId, String userInfo) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/update";
        try {
            String baseImage = ImageUtil.encode(image);
            Map<String, Object> map = new HashMap<>();
            map.put("image", baseImage);
            map.put("group_id", groupId);
            map.put("user_id", userId);
            map.put("user_info", userInfo);
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户信息查询·获取人脸库中某个用户的信息（user_info和所属组）
     * @return
     */
    public static String get(String userId, String groupId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/get";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", userId);
            map.put("group_id", groupId);

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户人脸列表
     * @return
     */
    public static String getList(String userId, String groupId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/face/getlist";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("user_id", userId);
            map.put("group_id", groupId);

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取用户列表
     * @return
     */
    public static String getUsers(String groupId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/getusers";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("group_id", groupId);

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建用户组
     * @return
     */
    public static String groupAdd(String groupId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("group_id", groupId);

            String param = GsonUtils.toJson(map);
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除用户组
     * @return
     */
    public static String groupDelete(String groupId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/delete";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("group_id", groupId);

            String param = GsonUtils.toJson(map);

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户组列表查询
     * @return
     */
    public static String GroupGetlist(int startIndex, int groupLength) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/group/getlist";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("start", startIndex);
            map.put("length", groupLength);

            String param = GsonUtils.toJson(map);
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
//        byte[] bytes = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/image_dev/mayun_1.jpg");
//        add(bytes,"group_repeat","mayun","alibaba");
//        getList("lizengqi","group_repeat");
        delete("group_repeat","lizengqi","7aebf33fcfa818b40120418bc4aa4368");
    }
}
