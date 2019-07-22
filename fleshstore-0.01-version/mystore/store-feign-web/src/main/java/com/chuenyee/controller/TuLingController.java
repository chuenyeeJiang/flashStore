package com.chuenyee.controller;

import baidu.speech.restapi.sttdemo.STT;
import baidu.speech.restapi.ttsdemo.AI;
import baidu.speech.restapi.ttsdemo.AI2;
import baidu.speech.restapi.ttsdemo.TTS;
import com.chuenyee.service.TuLingUntil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;


@Controller
public class TuLingController {
         static Logger logger = LoggerFactory.getLogger(WeatherController.class);

        @RequestMapping(value="/tuling_getResult.do")
        public void getResult(String message, HttpServletResponse response) throws Exception{
            response.setCharacterEncoding("utf-8");
            String result=TuLingUntil.getResult(message);
            TTS.outputMP3(result,"store-feign-web\\src\\main\\resources\\static\\voice\\output");
            TTS.outputMP3(result,"store-feign-web\\target\\classes\\static\\voice\\output");
            PrintWriter printWriter = response.getWriter();

            printWriter.write(result);
            printWriter.flush();
            printWriter.close();
        }

    @RequestMapping(value="/tuling_stt.do")
    public void STT(MultipartFile file, HttpServletResponse response) throws Exception{
        response.setCharacterEncoding("utf-8");
         byte[] bytes = file.getBytes();
//        //输出检验
        File file1 = new File("d://jar//output.wav");
        OutputStream fileOutputStream = new FileOutputStream(file1);
        DataOutputStream outputStream = new DataOutputStream(fileOutputStream);
        outputStream.write(bytes,0 ,bytes.length );

        //输入检验
        InputStream fileInputStream   = new FileInputStream(file1);
        DataInputStream inputStream = new DataInputStream(fileInputStream);
        byte[] inputBytes = new byte[100000];
        inputStream.read(inputBytes);


        logger.debug("tuling_stt.do==============run");
        logger.debug("bytes："+bytes);
        String result =  STT.getResult(inputBytes);
        logger.debug("result："+result);
        PrintWriter printWriter = response.getWriter();

        printWriter.write(result);
        printWriter.flush();
        printWriter.close();
    }


}
