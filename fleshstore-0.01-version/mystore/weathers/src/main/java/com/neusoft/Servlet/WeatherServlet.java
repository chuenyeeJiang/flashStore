package com.neusoft.Servlet;


import com.neusoft.weathertet.WeatherUntil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@WebServlet("/WeatherServlet")
public class WeatherServlet extends HttpServlet {
 private static  final long serialversionUID= 1L;

 @Override
 protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
     request.setCharacterEncoding("utf-8");
     response.setCharacterEncoding("utf-8");
    String city = request.getParameter("cityname");
     String weather = WeatherUntil.getWeather(city);
     PrintWriter printWriter = response.getWriter();
     printWriter.write(weather);
     printWriter.flush();
     printWriter.close();
 }
}
