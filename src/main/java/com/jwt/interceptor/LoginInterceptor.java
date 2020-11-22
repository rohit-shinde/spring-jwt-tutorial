package com.jwt.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //Logging request coming to customer360 services. 
        logger.info(":: {} :: {} :: {}", request.getMethod(), request.getRequestURI(), request.getProtocol());
        return true;
    }
	
	@Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView model) throws Exception {
    }
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception arg3) throws Exception {
        if (response.getStatus() >= 400) {
            //Logging response error which is getting returned from customer360 services. 
            logger.error(" RESPONSE_STATUS: {} :: {} :: {} :: {}", response.getStatus(), request.getMethod(), request.getRequestURI(), request.getProtocol());
        } else {
            //Logging response success which is getting returned from customer360 services. 
            logger.info(" RESPONSE_STATUS: {} :: {} :: {} :: {}", response.getStatus(), request.getMethod(), request.getRequestURI(), request.getProtocol());
        }
    }
}
