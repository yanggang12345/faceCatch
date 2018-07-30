package com.nala.faceCatch.service;

/**
 * create by lizenn
 * create date 2018/7/30
 * description 人脸注册
 */

import com.nala.faceCatch.util.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 人脸注册
 */
@Service
public class FaceRegister {

    public static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            byte[] bytes = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/face_dev/20180730111449826.jpeg");
            String image = ImageUtil.encode(bytes);
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("group_id", "group_repeat");
            map.put("user_id", "lizengqi");
            map.put("user_info", "vip");
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        FaceRegister.add();
    }
}
