package com.nala.faceCatch.util.fastdfs;

import org.csource.common.NameValuePair;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import org.csource.common.MyException;
import org.csource.fastdfs.*;

/**
 * create by lizenn
 * create date 2018/10/30
 * description 操作fastdfs的使用类
 */
public class FastDFSClient {

    /**
     * 配置文件
     */
    private  static final String CONFIG_FILENAME = "fdfs_client.conf";

    /**
     * 两种不同版本的存储服务器客户端
     */
    private  static StorageClient storageClient = null;

    private  static StorageClient1 storageClient1 = null;

    // 初始化FastDFS Client
    static {

        try {
            ClientGlobal.init(CONFIG_FILENAME);
            TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();

            if (trackerServer == null) {
                throw new IllegalStateException("getConnection return null");
            }

            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }

            storageClient1 = new StorageClient1(trackerServer,storageServer);
            storageClient = new StorageClient1(trackerServer,storageServer);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    /**
     * 上传文件方法
     * @param fileName 文件全路径
     * @param extName 文件扩展名，不包含（.）
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(String fileName, String extName, NameValuePair[] metas) {

        String result=null;

        try {
            result = storageClient1.upload_file1(fileName, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 上传文件,传fileName
     * @param fileName 文件的磁盘路径名称 如：D:/image/image.jpg
     * @return null为失败
     */
    public String uploadFile(String fileName) {
        return uploadFile(fileName, null, null);
    }


    /**
     * @param fileName 文件的磁盘路径名称 如：D:/image/image.jpg
     * @param extName 文件的扩展名 如 txt jpg等
     * @return null为失败
     */
    public  String uploadFile(String fileName, String extName) {
        return uploadFile(fileName, extName, null);
    }


    /**
     * 上传文件
     * @param fileContent 文件的内容，字节数组
     * @param extName 文件扩展名
     * @param metas 文件扩展信息
     * @return
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) {

        String result=null;

        try {
            result = storageClient1.upload_file1(fileContent, extName, metas);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 上传文件
     * @param fileContent 文件的字节数组
     * @return null为失败
     * @throws Exception
     */
    public String uploadFile(byte[] fileContent) throws Exception {
        return uploadFile(fileContent, null, null);
    }


    /**
     * 上传文件
     * @param fileContent  文件的字节数组
     * @param extName  文件的扩展名 如 txt  jpg png 等
     * @return null为失败
     */
    public String uploadFile(byte[] fileContent, String extName) {
        return uploadFile(fileContent, extName, null);
    }


    /**
     * 文件下载
     * @param path 图片路径
     * @param bufferedOutputStream 输出流 中包含要输出到磁盘的路径
     * @return -1失败,0成功
     */
    public int download_file(String path,BufferedOutputStream bufferedOutputStream) {

        int result=-1;

        try {
            byte[] b = storageClient1.download_file1(path);
            try{
                if(b != null){
                    bufferedOutputStream.write(b);
                    result=0;
                }
            }catch (Exception e){} //用户可能取消了下载
            finally {
                if (bufferedOutputStream != null)
                    try {
                        bufferedOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 下载文件（数组）
     * @param path 文件的路径 如:group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return
     */
    public byte[] download_bytes(String path) {

        byte[] b=null;

        try {
            b = storageClient1.download_file1(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
        return b;
    }


    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @param filePath 文件下载保存位置
     * @return
     */
    public int download_file(String fileId, String filePath) {

        File file = new File(filePath);
        FileOutputStream fileOutputStream = null;

        if(filePath == "" || filePath == null)
            return -1;

        try {
            byte[] content = storageClient1.download_file1(fileId);
            if(content != null){
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(content);
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return -1;
    }


    /**
     * 删除文件
     * @param group 组名 如：group1
     * @param storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     */
    public Integer delete_file(String group ,String storagePath){

        int result=-1;

        try {
            result = storageClient.delete_file(group, storagePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return  result;
    }

    /**
     *
     * @param storagePath  文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg
     * @return -1失败,0成功
     * @throws IOException
     * @throws Exception
     */
    public Integer delete_file(String storagePath){

        int result=-1;

        try {
            result = storageClient1.delete_file1(storagePath);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }

        return  result;
    }


    /**
     * 获取文件元数据
     * @param fileId 文件ID
     * @return
     */
    public static Map<String,String> getFileMetadata(String fileId) {

        try {
            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}














































