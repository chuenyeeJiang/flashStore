package com.chuenyee.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WeatherUntil {
    //日志
    static Logger logger = LoggerFactory.getLogger(WeatherUntil.class);
    //存储apikey
    private  static final String API_KEY = "d2afbac20dafe75efcaf8d0ae3f791af";
    //存储apiurl
    private  static final String API_URL="http://v.juhe.cn/weather/index";

    //定义一个全局访问点
    public static String getWeather(String cityname){
        logger.info("getWeather===================run");
        //城市名或城市ID，如："苏州",需要utf8 urlencode
        try {
            URLEncoder.encode(cityname, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //拼接url
        String url = API_URL+"?key="+API_KEY+"&cityname="+cityname;

        //使用apach的httpClient模块开发
        HttpGet request = new HttpGet(url);

        String weather ="";
        try {
            HttpResponse response =  HttpClients.createDefault().execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
        if(statusCode==200){
            weather = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSON.parseObject(weather);
            Object result = jsonObject.get("result").toString();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weather;

    }
}
