package com.chuenyee.service.api;


import com.alibaba.fastjson.JSONObject;
import com.chuenyee.service.HeadPortraitFallbackServer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="store-fileServer",fallback= HeadPortraitFallbackServer.class)
public interface HeadPortrait {
	@RequestMapping(value="/store/uploadHeadPortrait.do",consumes=MediaType.APPLICATION_JSON_VALUE,method=RequestMethod.POST,produces ="application/json,charset=utf-8" )
	public Map<String, Object> upload(@RequestParam Map<String,Object> request_map) ;
}
