package baidu.speech.restapi.sttdemo;

import baidu.speech.restapi.common.ConnUtil;
import baidu.speech.restapi.common.DemoException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chuenyee.controller.WeatherController;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class STT {
    //日志
    static Logger logger = LoggerFactory.getLogger(STT.class);

    //基础信息
    private static final String API_ID = "16206017";
    private static final String API_KEY = "lG0djrbMqMZYQh3Tl2W3CDMT"; //client_id
    private static final String SECRET_KEY = "0eR4xU2a9D1F2sXDi0vlw5TgDq1eTkh9";//client_secret
    private static final String Token_URL= "https://openapi.baidu.com/oauth/2.0/token";
    private static final String Server_URL= "http://vop.baidu.com/server_api";
    public static final String  grant_type="client_credentials";



    //参数（必填）
    private static final String cuid    = "16206017";
    private static final String format  = "wav";                          //语音文件的格式，pcm 或者 wav 或者 amr。不区分大小写。推荐pcm文件
    private static final int channel    = 1;                              //声道数，仅支持单声道，请填写固定值 1
    private static final int rate       = 16000;                          //采样率，16000，固定值

    //选填
    private static final int  dev_pid = 1537;    //不填写lan参数生效，都不填写，默认1537（普通话 输入法模型），dev_pid参数见本节开头的表格


    public static String getToken(){
        String params="grant_type="+grant_type+"&client_id="+API_KEY+"&client_secret="+SECRET_KEY;
        return  httpSend(Token_URL+"?"+params);
    }

    public static String getResult(byte[] content) throws IOException, DemoException {
        String speech = null;

        speech	= base64Encode(content);      //本地语音文件的的二进制语音数据 ，需要进行base64 编码。与len参数连一起使用。

        HttpURLConnection Conn = (HttpURLConnection) new URL(Server_URL).openConnection();

        Conn.setDoOutput(true);
        Conn.setDoInput(true);
        Conn.setConnectTimeout(6000);
        Conn.setRequestMethod("POST");
        Conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

        JSONObject params = new JSONObject();
        params.put("token",getToken());
        params.put("cuid",cuid);
        params.put("format",format);
        params.put("channel",channel);
        params.put("rate",rate);
        params.put("dev_pid",dev_pid);
        params.put("speech",speech);
        params.put("len",content.length);

        Conn.getOutputStream().write(params.toString().getBytes());
        Conn.getOutputStream().flush();
        Conn.getOutputStream().close();
        String responseString = ConnUtil.getResponseString(Conn);
       return responseString;
    }


    public static String httpSend(String url){
        HttpGet httpGet = new HttpGet(url);
        try {

            HttpResponse httpResponse = HttpClients.createDefault().execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if(statusCode==200){
                String result = EntityUtils.toString(httpResponse.getEntity());
                JSONObject jsonObject = JSON.parseObject(result);
                String access_token= (String)jsonObject.get("access_token");

                return  access_token;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String base64Encode(byte[] content) {
         Base64.Encoder encoder = Base64.getEncoder(); // JDK 1.8  推荐方法
         String str = encoder.encodeToString(content);
         return str;

        /**
        char[] chars = Base64Util.encode(content); // 1.7 及以下，不推荐，请自行跟换相关库
        String str = new String(chars);

        return str;
         */
    }
}
