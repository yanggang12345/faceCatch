package com.nala.faceCatch.service.face;

/**
 * create by lizenn
 * create date 2018/7/30
 * description 人脸搜索
 */

import com.google.gson.Gson;
import com.nala.faceCatch.entity.search.SearchRootResultVO;
import com.nala.faceCatch.util.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 人脸搜索
 */
@Service
public class FaceSearch {

    public static SearchRootResultVO search(byte[] image, String groupId) {
        SearchRootResultVO searchRootResultVO = new SearchRootResultVO();
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            String baseImage = ImageUtil.encode(image);
            Map<String, Object> map = new HashMap<>();
            map.put("image", baseImage);
            map.put("liveness_control", "LOW");
            map.put("group_id_list", groupId);
            map.put("image_type", "BASE64");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，
            // 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = GetToken.getAuth();

            String result = HttpUtil.post(url, accessToken, "application/json", param);

            Gson gson = new Gson();
            searchRootResultVO = gson.fromJson(result, SearchRootResultVO.class);

            System.out.println("searchRootResultVO.getError_code()--->"+searchRootResultVO.getError_code());
            System.out.println(result);
            return searchRootResultVO;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return searchRootResultVO;
    }

//    public static void main(String[] args) throws Exception {
//        byte[] bytes = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/mayun_0.jpg");
////        FaceSearch.search(bytes,"group_celebrity");
//    }
}