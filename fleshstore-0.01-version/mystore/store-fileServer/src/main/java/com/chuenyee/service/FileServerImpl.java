package com.chuenyee.service;

import com.chuenyee.service.api.FileServer;
import com.chuenyee.tool.MyHttphandle;
import org.bouncycastle.util.encoders.Base64;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;

public class FileServerImpl implements FileServer {

    static String  osName = null;
    static String  save_path = null;
    static{
        /*
          根据操作系统 初始化上传路径
         */
        Properties properties = System.getProperties();
        osName= properties.getProperty("os.name");
        if(osName!=null) {
            if (osName.contains("windows") || osName.contains("Windows")) {
                save_path = "d://tempUpload//HeadPortrait//2019//"+ MyHttphandle.getUserName() +".jpg";
            } else if (osName.contains("Linux") || osName.contains("linux")) {
                save_path = "/usr/local/tempUpload//HeadPortrait//2019//"+MyHttphandle.getUserName()+".jpg";
            } else {
                throw new RuntimeException("该应用程序未能兼容此设备操作系统");
            }
        }else{
            throw new RuntimeException("获取操作系统信息错误！");
        }
    }
    public void setSave_path(String path){
        save_path = path;
    }

    @Override
    public boolean upload(String string_file) {
        try {

         /*
         创建临时文件
         */
            File target_file = createFile();

          /*
          base64解码
          */
            byte[] decode = FileServerImpl.decode(string_file);




        /*
            头像截取
         */


        /*
         写出文件
         */
        OutputStream outputStream = new FileOutputStream(target_file);
        outputStream.write(FileServerImpl.decode(string_file));
        outputStream.flush();
        outputStream.close();
        }catch (Exception e){
            return false;
        }
        return true;
    }

    //创建临时文件
    private File createFile(){
        File target_file = new File(save_path);

        //创建目录
        File parentFile = target_file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        //创建文件
        if (!target_file.exists()) {
            try {
                target_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
         授权(待)
         */
        target_file.setWritable(true);
        target_file.setReadable(true);
        return  target_file;
    }

    //base64解码
    public static byte[] decode(String data){
        byte[] bytes = Base64.decode(data);
        for(int i=0;i<bytes.length;i++){
           if( bytes[i] <0)
           {
               bytes[i]+=256;
           }
        }

        return bytes;
    }




}
