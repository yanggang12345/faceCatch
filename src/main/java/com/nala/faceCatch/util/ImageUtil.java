package com.nala.faceCatch.util;


import com.nala.faceCatch.service.face.FaceSearch;
import org.apache.commons.codec.binary.Base64;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * create by lizenn
 * create date 2018/7/30
 * description 图像工具类
 */
public class ImageUtil {

    /**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }


    public static void main(String[] args) throws Exception {
        byte[] bytes1 = FileUtil.readFileByBytes("/Users/lizengqi/Pictures/2.jpg");
        System.out.println(encode(bytes1));
    }

    public static void faceMacth(byte[] array) {
//        //4 - 7 字节的二进制表示·协议中低位在前，故反向拼接
//        String bitStr = NumberUtil.binaryString(array[7])
//                +NumberUtil.binaryString(array[6])
//                +NumberUtil.binaryString(array[5])
//                +NumberUtil.binaryString(array[4]);
//        //数据包长度
//        int realLength = Integer.valueOf(bitStr,2);
//
//        //人脸数据长度
//        int faceLength = realLength - 64;
//        byte[] faceArray = new byte[faceLength];
//        System.arraycopy(array,76,faceArray,0,faceLength-1);
//        //人脸库·搜索 匹配
        FaceSearch.search(array, "group_repeat,group_celebrity");
        Date date = new Date();
        FileUtil.byte2image(array, "/Users/lizengqi/Pictures/face_dev/"
                + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date) + ".jpeg");
        System.out.print("----------here2--------");
    }

}
