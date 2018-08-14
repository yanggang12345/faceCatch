package com.nala.faceCatch.service;

import com.nala.faceCatch.util.*;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 人脸对比
 */

@Service
public class Contrast {


    public static Map<String, Object> match() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/match";
        Map<String, Object> resultMap = new HashMap<>();
        try {

            byte[] bytes1 = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/2.jpg");
            byte[] bytes2 = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/3.jpg");
            String image1 = Base64Util.encode(bytes1);
            String image2 = Base64Util.encode(bytes2);

            List<Map<String, Object>> images = new ArrayList<>();

            Map<String, Object> map1 = new HashMap<>();
            map1.put("image", image1);
            map1.put("image_type", "BASE64");
            map1.put("face_type", "LIVE");
            map1.put("quality_control", "LOW");
            map1.put("liveness_control", "LOW");

            Map<String, Object> map2 = new HashMap<>();
            map2.put("image", image2);
            map2.put("image_type", "BASE64");
            map2.put("face_type", "LIVE");
            map2.put("quality_control", "LOW");
            map2.put("liveness_control", "LOW");

            images.add(map1);
            images.add(map2);

            String param = GsonUtils.toJson(images);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间，
            // 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println("result---" + result);
            JSONObject jsonObjectResult = new JSONObject(result);
            System.out.println("jsonObjectResult---" + jsonObjectResult);

            JSONObject resultROI = jsonObjectResult.getJSONObject("result");
            if (resultROI != null) {
                resultMap = Read.analysisJson(resultROI);
            } else {
                return null;
            }
            System.out.println("resultROI---" + resultROI);

            System.out.println(resultMap);

            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}