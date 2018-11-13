package com.nala.faceCatch.util.fastdfs;

import com.nala.faceCatch.util.FileUtil;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.FileOutputStream;
import java.net.InetSocketAddress;

/**
 * create by lizenn
 * create date 2018/10/30
 * description 库的测试类
 *
 * 使用方法：
 * 1、 把FastDFS提供的jar包添加到工程中
 * 2、 初始化全局配置。加载一个配置文件。
 * 3、 创建一个TrackerClient对象。
 * 4、 创建一个TrackerServer对象。
 * 5、 声明一个StorageServer对象，null。
 * 6、 获得StorageClient对象。
 * 7、 直接调用StorageClient对象方法上传文件即可。
 */
public class StorageTest {

    public static FastDFSClient fastDFSClient = new FastDFSClient();



    public static void main(String args[]) throws Exception {
        for(int i = 0;i<=100;i++){
            byte[] srcImage = FileUtil.image2byte("/Users/lizengqi/Pictures/image_dev/timg.jpeg");
            String fileid = fastDFSClient.uploadFile(srcImage,"jpg");
            System.out.println("call successf the fileid is:"+fileid);
        }


//        byte[] fileByte = fastDFSClient.download_bytes("group1/M00/00/00/wKgKH1vanT2AQU8KAAAXBYlIjxk016.jpg");
////        FileUtil.byte2image(fileByte,"/data/image.jpg");
//
        fastDFSClient.download_file("group1/M00/00/00/wKgKH1vanT2AQU8KAAAXBYlIjxk016.jpg","/data/image1.jpg");

//        int result = fastDFSClient.delete_file("group1/M00/00/00/wKgKH1vanT2AQU8KAAAXBYlIjxk016.jpg");

    }

    public static StorageClient1 getStorageClient1(){

        return null;
    }


    /**
     * 上传文件
     * @return
     * @throws Exception
     */
    public static String uploadFile()throws Exception {

        //远程文件系统返回的文件ID
        String fileid = null;

        try {
            //初始化全剧配置，加载配置文件
            ClientGlobal.init("fdfs_client.conf");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            //创建一个trackerClient对象
            TrackerGroup trackerGroup = new TrackerGroup(new InetSocketAddress[]{new InetSocketAddress("192.168.10.31", 22122)});
            TrackerClient trackerClient = new TrackerClient(trackerGroup);

            //创建一个TrackerServer对象
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                System.out.println("getConnection return null");
                return "getConnection return null";
            }

            //声明一个StorageServer对象
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                System.out.println("getStoreStorage return null");
            }

            //获得StorageClient对象
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] meta_list = null;  //new NameValuePair[0];
            String item;
            if (System.getProperty("os.name").equalsIgnoreCase("windows")) {
                item = "c:/windows/system32/notepad.exe";
                fileid = storageClient1.upload_file1(item, "exe", meta_list);
            } else {
                item = "/etc/hosts";
                fileid = storageClient1.upload_file1(item, "", meta_list);
            }

            System.out.println("Upload local file " + item + " ok, fileid=" + fileid);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return fileid;
    }

    /**
     * 下载文件
     */
    public static void downloadFile(String groupName,String filepath) throws Exception{
        System.out.println("下载文件=======================");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;

        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        byte[] b = storageClient.download_file(groupName, filepath);
        System.out.println("文件大小:"+b.length);
        String fileName = "src/main/resources/test1.jpg";
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(b);
        out.flush();
        out.close();
    }




}
