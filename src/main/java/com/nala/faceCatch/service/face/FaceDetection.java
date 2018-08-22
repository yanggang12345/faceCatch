package com.nala.faceCatch.service.face;

/**
 * create by lizenn
 * create date 2018/8/20
 * description 人脸检测
 */

import com.google.gson.JsonObject;
import com.nala.faceCatch.util.*;
import java.util.*;


public class FaceDetection {

    private static final String DETECT_URL_V2 = "https://aip.baidubce.com/rest/2.0/face/v2/detect";

    private static final String DETECT_URL_V3 = "https://aip.baidubce.com/rest/2.0/face/v3/detect";


    /**
     * 人脸属性分析（年龄性别）
     * @param image
     * @return
     */
    public static String detect(byte[] image) {

        try {
            String baseImage = ImageUtil.encode(image);
            Map<String, Object> map = new HashMap<>();
            map.put("image", baseImage);
            map.put("face_field", "age,gender,");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            String accessToken = GetToken.getAuth();
            String result = HttpUtil.post(DETECT_URL_V3, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        byte[] image = FileUtil.image2byte("/Users/lizengqi/Pictures/image_dev/lizenn1.jpeg");
        String result = FaceDetection.faceQuality(image);
        try{
            Map map = JsonUtil.json2map(result);
            System.out.println(map);
            Object result2 = map.get(result);
            System.out.println("result--->"+result2);



        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * 检测图片的质量
     * @param image
     * @return
     */
    public static String faceQuality(byte[] image) {

        try {
            String baseImage = ImageUtil.encode(image);
            Map<String, Object> map = new HashMap<>();
            map.put("image", baseImage);
            map.put("face_field", "quality");
            map.put("image_type", "BASE64");

            String param = GsonUtils.toJson(map);

            String accessToken = GetToken.getAuth();
            String result = HttpUtil.post(DETECT_URL_V3, accessToken, "application/json", param);
//            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}