package com.chuenyee.controller;

import baidu.speech.restapi.ttsdemo.AI;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chuenyee.service.WeatherUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class WeatherController {
    //日志
    static Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @RequestMapping("/getWeather")
    protected void getWeather(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("getWeather controller============run");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String city = request.getParameter("cityname");
        String weather = WeatherUntil.getWeather(city);
        JSONObject jsonObject = JSON.parseObject(weather);

        JSONObject result = (JSONObject) jsonObject.get("result");
        logger.debug("result:"+result);
        JSONObject today =  (JSONObject)result.get("today");
        logger.debug("today:"+today);
        String cityname =  (String)today.get("city");
        logger.debug("城市:"+cityname);

        PrintWriter printWriter = response.getWriter();
        printWriter.write(weather);
        printWriter.flush();
        printWriter.close();
        logger.debug("getWeather controller============over");
    }
}
