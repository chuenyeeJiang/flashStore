package com.nesoft.tulingtest;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Scanner;

//接收控制台输入，将输入信息作为参数给图灵api传参，获悉响应结果
public class TingLingTest {
    //存储apikey
    private static final String API_KEY="40a0ffa5e13547519b32314ad1bac813";
    //存储api url
    private static final String API_URL="http://www.tuling123.com/openapi/api";

    public static void main(String[] arg){
        while (true) {
            //接收控制台输入
            Scanner scanner = new Scanner(System.in);
            String message = scanner.nextLine();
            //调用图灵API的各个环节的编码方式均为UTF-8
            try {
                message = URLEncoder.encode(message, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //拼接URL
            String url = API_URL + "?key=" + API_KEY + "&info=" + message;
            //使用Apache的HttpClient模块开发
            /*
             *  第一步: 创建HttpGet 或者 HttpPost对象
             *  第二步: 使用execute发送请求
             *  第三步获取返回结果(HttpRespouse)
             */
            //封装请求头
            HttpGet request = new HttpGet(url);
            try {
                //发送请求
                HttpResponse response = HttpClients.createDefault().execute(request);
                //获取响应码
                int statusCode = response.getStatusLine().getStatusCode();
                //判断状态码
                if (statusCode == 200) {
                    //获取响应内容
                    HttpEntity entity = response.getEntity();
                    String result = EntityUtils.toString(entity);

                    JSONObject parse = JSONObject.parseObject(result);
                    String text = parse.get("text").toString();
                    System.out.println(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
