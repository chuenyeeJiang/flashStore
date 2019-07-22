package com.chuenyee.controller;
import baidu.speech.restapi.common.ConnUtil;
import baidu.speech.restapi.common.DemoException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class TTS {
    //getToken     https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=lG0djrbMqMZYQh3Tl2W3CDMT&client_secret=0eR4xU2a9D1F2sXDi0vlw5TgDq1eTkh9
    //getMP3       http://tsn.baidu.com/text2audio?lan=zh&ctp=1&cuid=abcdxxx&tok=1.a6b7dbd428f731035f771b8d****.86400.1292922000-2346678-124328&tex=%e7%99%be%e5%ba%a6%e4%bd%a0%e5%a5%bd&vol=9&per=0&spd=5&pit=5&aue=3
    private static final String API_ID = "16206017";
    private static final String API_KEY = "lG0djrbMqMZYQh3Tl2W3CDMT"; //client_id
    private static final String SECRET_KEY = "0eR4xU2a9D1F2sXDi0vlw5TgDq1eTkh9";//client_secret
    private static final String Token_URL= "https://openapi.baidu.com/oauth/2.0/token";
    private static final String MP3_URL= "http://tsn.baidu.com/text2audio";

    private String access_token = "";
    private String lan = "zh";
    private String cuid = "16206017";
    public int vol =1;                  //音量，取值0-15，默认为5中音量
    private int ctp = 1;                 //	客户端类型选择，web端填写固定值1
    public int per = 0;                 // 发音人选择, 0为普通女声，1为普通男生，3为情感合成-度逍遥，4为情感合成-度丫丫，默认为普通女声
    public int spd = 1;                 //语速，取值0-15，默认为5中语速
    public int pit = 1;                 //音调，取值0-15，默认为5中语调
    private int aue =3;                 //3为mp3格式(默认)； 4为pcm-16k；5为pcm-8k；6为wav（内容同pcm-16k）; 注意aue=4或者6是语音识别要求的格式，但是音频内容不是语音识别要求的自然人发音，所以识别效果会受影响。


    public static final String  grant_type="client_credentials";

    public String getToken(){
        String params="grant_type="+grant_type+"&client_id="+API_KEY+"&client_secret="+SECRET_KEY;
        return  httpSend(Token_URL+"?"+params);
    }

    @Test
    public void outputMP3() throws IOException, DemoException {
        String text = "志斌牛逼";
        String path = ".";
        String params="lan="+lan+"&ctp="+ctp+"&cuid="+cuid+"&tok="+getToken()+"&tex="+text+"&vol="+vol+"&per="+per+"&spd="+spd+"&pit="+pit+"&aue="+aue;
        HttpURLConnection httpURLConnectiont = (HttpURLConnection)new URL(MP3_URL).openConnection();
        httpURLConnectiont.setDoInput(true);
        httpURLConnectiont.setDoOutput(true);
        httpURLConnectiont.setConnectTimeout(6000);
        PrintWriter printWriter = new PrintWriter(httpURLConnectiont.getOutputStream());
        printWriter.write(params);
        printWriter.flush();
        printWriter.close();
        String contentType = httpURLConnectiont.getContentType();
        int code = httpURLConnectiont.getResponseCode();
        System.out.println("contentType:" +contentType);
        System.out.println("code:" +code);
        System.out.println("message:"+httpURLConnectiont.getResponseMessage());
        if(contentType.contains("audio")){
            byte[] responseBytes = ConnUtil.getResponseBytes(httpURLConnectiont);
            File file = new File(path+"."+getFormat(aue));
            FileOutputStream fileOutputStream =  new FileOutputStream(file);
            fileOutputStream.write(responseBytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            System.out.println("audio file write to " + file.getAbsolutePath());
        }
    }

    public String httpSend(String url){
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = HttpClients.createDefault().execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(statusCode==200){
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = JSON.parseObject(result);
                access_token= (String)jsonObject.get("access_token");

                return  access_token;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 下载的文件格式, 3：mp3(default) 4： pcm-16k 5： pcm-8k 6. wav
    private String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue-3];
    }



}
