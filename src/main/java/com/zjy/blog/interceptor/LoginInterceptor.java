package com.zjy.blog.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       if (request.getSession().getAttribute("user") == null){
           request.setAttribute("msg","请先登录！");
           request.getRequestDispatcher("/admin").forward(request,response);
           return false;
       }
        return true;
    }
}
