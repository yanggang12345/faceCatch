package com.nala.faceCatch.service.face;

/**
 * create by lizenn
 * create date 2018/8/20
 * description 人脸检测
 */

import com.google.gson.Gson;
import com.nala.faceCatch.entity.quality.QualityResultVO;
import com.nala.faceCatch.entity.quality.QualityRootResultVO;
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
        byte[] image = FileUtil.image2byte("/Users/lizengqi/Pictures/face_dev/quality/20181121164527600.jpeg");
        String result = FaceDetection.faceQuality(image);
        Gson gson = new Gson();
        QualityRootResultVO qualityRootResultVO = gson.fromJson(result, QualityRootResultVO.class);

        System.out.println(result);
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