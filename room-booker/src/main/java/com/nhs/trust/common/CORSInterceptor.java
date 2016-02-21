package com.nhs.trust.common;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class CORSInterceptor extends HandlerInterceptorAdapter { 
 
 
     @Override 
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
         InputStream in = getClass().getResourceAsStream("/META-INF/app.properties"); 
         Properties prop = new Properties(); 
         prop.load(in); 
         in.close(); 
  
         Set<String> allowedOrigins = new HashSet<String>(Arrays.asList(prop.getProperty("allowed.origins").split(","))); 
 
         String origin = request.getHeader("Origin"); 
         if(allowedOrigins.contains(origin)) { 
             response.addHeader("Access-Control-Allow-Origin", origin); 
             return true; 
         } else { 
             return false; 
         } 
     } 
 } 

