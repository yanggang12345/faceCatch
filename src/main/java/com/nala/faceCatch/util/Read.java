package com.nala.faceCatch.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * create by lizenn
 * create date 2018/7/21
 * description
 */
public class Read {

    /**
     * 解析joson
     */
    public static Map<String, Object> analysisJson(JSONObject jsonObject) {
        //创建json解析器
        Map<String, Object> map = new HashMap<>();
        try {
//            System.out.println(jsonObject);
//            JSONObject result = jsonObject.getJSONObject("result");
            double score = jsonObject.getDouble("score");
            map.put("score", score);
            JSONArray faceList = jsonObject.getJSONArray("face_list");
            JSONObject face1 = (JSONObject) faceList.get(0);
            JSONObject face2 = (JSONObject) faceList.get(1);

            String face1_token = face1.getString("face_token");
            String face2_token = face2.getString("face_token");
            map.put("face1", face1_token);
            map.put("face2", face2_token);

//            System.out.println("faceList--"+faceList);
//            System.out.println("result--"+result);
//            System.out.println("score--"+score);
//            System.out.println("face1_token--"+face1_token);
//            System.out.println("face2_token--"+face2_token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
