package com.chuenyee.controller;

import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.chuenyee.service.TuLingUntil;
import com.chuenyee.tool.MyJsonHandle;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;

import java.io.IOException;

public class test {
    @Value("${server.context-path}")
    String contextPath;

    @Test
    public void test(){
        String result= TuLingUntil.getResult("你好");
        System.out.println(result);
        //AI.TTS(result,"output.mp3");
    }

    @Test
    public void okhttp(){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://127.0.0.1:9000/eureka/apps/store-fileServer")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String str = response.body().string();
                str=str.replace("[", "");
                str=str.replace("]", "");
                System.out.println(str);
                JSONObject root = JSON.parseObject(str);
             //   MyJsonHandle.debug(root,false);
             //   System.out.println("hostName:"+MyJsonHandle.getResult(root,"application.instance.hostName"));
             //   System.out.println("port:"+MyJsonHandle.getResult(root,"application.instance.port.$"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
