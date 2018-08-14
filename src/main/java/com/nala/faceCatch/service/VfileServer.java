package com.nala.faceCatch.service;

import org.apache.catalina.Server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * create by lizenn
 * create date 2018/7/23
 * description 获取设备抓拍的图片服务端
 */

public class VfileServer {

    public static int port = 8102;

    public static void main(String[] args) throws Exception {

        //创建客户端的Socket服务，指定目的主机和端口。
        Socket s = new Socket("192.168.10.10", 8102);
        System.out.println("客户端已建立链接...");

//        //发送数据，应该获取Socket流中的输出流。
//        OutputStream out = s.getOutputStream();
//        out.write("holle,server!".getBytes());
        //获得服务器发过来的数据，先获得输入流
        InputStream in = s.getInputStream();
        byte[] buf = new byte[1024];
        //注意：read会产生阻塞
        int len = in.read(buf);
        //将服务器发过来的额数据打印在控制台上
        System.out.println(new String(buf, 0, len));
        //关闭资源
        s.close();

    }

    /**
     * 读取输入流中指定字节的长度
     *
     * @param in     输入流
     * @param length 指定长度
     * @return 指定长度的字节数组
     */
    public static byte[] readBytes(InputStream in, long length) throws IOException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();// 建立字节流
        byte[] buffer = new byte[1024];//1024长度
        int read = 0;
        while (read < length) {// 循环将需要读取内容写入到bo中
            int cur = in.read(buffer, 0, (int) Math.min(1024, length - read));
            if (cur < 0) {//直到读到的返回标记为-1，表示读到流的结尾
                break;
            }
            read += cur;//每次读取的长度累加
            bo.write(buffer, 0, cur);
        }
        return bo.toByteArray();//返回内容
    }
}





