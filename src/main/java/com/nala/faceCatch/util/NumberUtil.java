package com.nala.faceCatch.util;

/**
 * create by lizenn
 * create date 2018/7/25
 * description
 */
public class NumberUtil {

    public static void convertBinary(int num){
        int binary[] = new int[50];
        int index = 0;
        if(num > 0){
            while(num > 0){
                binary[index++] = num%2;
                num = num/2;
            }
            for(int i = index-1;i >= 0;i--){
                System.out.print(binary[i]);
//                System.out.println(" ");
            }
        }else if(num < 0){
            int tempNum = -num;
            while (tempNum > 0){
                binary[index++] = tempNum%2;
                tempNum = tempNum/2;
            }

            //取反码
            for(int i = 0;i<index;i++){
                if(binary[i] == 0)
                    binary[i] = 1;
                if(binary[i] == 1)
                    binary[i] = 0;
            }
            //取补码
            for(int i = index-1;i >= 0;i--){
                if(binary[i] == 0){
                    binary[i] = 1;
                }else if(binary[i] == 1){
                    binary[i] = 0;
                    if(binary[i-1] == 0){
                        binary[i-1] = 1;
                    }
                }
                System.out.print(binary[i]);
            }
        }



    }

//    public static void main(String[] args){
////        convertBinary(441);
//
//        Integer.toBinaryString(441);
//        Integer.toBinaryString(441);
//
//        //十进制转二进制
//        System.out.println(Integer.toBinaryString(0));
//        Integer.valueOf("110111001",2).toString();
//        //二进制转十进制
//        System.out.println(Integer.valueOf("110111001",2).toString());
//        //16转10进制
////        Integer.valueOf("bb",16).toString();
////        System.out.println(Integer.valueOf("97",16).toString());
//    }

    /**
     * 十进制转二进制，获取末尾8位二进制码
     * @param num
     */
    public static String binaryString(int num){
        String bitString = null;

        bitString = Integer.toBinaryString(num);
        int flag = 8 - bitString.length();
        if(flag>0){
            for(int i = 1;i<=flag;i++){
                bitString = "0"+bitString;
            }

        }else if(flag < 0){
            bitString = bitString.substring(bitString.length()-8,bitString.length());
        }
        System.out.println("bit===>"+bitString);
        return bitString;
    }



}
