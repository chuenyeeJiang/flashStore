package com.chuenyee.service;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.chuenyee.service.api.HeadPortrait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;


@Component
public class HeadPortraitFallbackServer implements HeadPortrait {

    Logger logger = LoggerFactory.getLogger(HeadPortraitFallbackServer.class);


    @Override
    public Map<String, Object> upload(@RequestParam Map<String,Object> request_map) {
        logger.info("HeadPortraitServer Fallback!");
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> response_param = new HashMap<String, Object>();
        response_param.put("error", "系统正在维护中...");
        map.put("response_param", response_param);
        map.put("view", "personalUserInformation.html");
        return map;
    }
}
