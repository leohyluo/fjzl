package com.zhys.core.system;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

import com.zhys.core.util.WebUtil;

/**
 * 
 * 版权：智慧药师 <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-03-31 <br/>
 * 描述：上下文过滤器
 */
public class WebFilter extends CharacterEncodingFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        super.doFilterInternal(request, response, filterChain);
//        WebUtil.setRequest(request);
//        WebUtil.setResponse(response);
    }

    @Override
    protected void initFilterBean() throws ServletException {
        WebUtil.setServletContext(super.getServletContext());
        super.initFilterBean();
    }

    @Override
    public void destroy() {
//        WebUtil.removeRequest();
//        WebUtil.removeResponse();
//        WebUtil.removeServletContext();
        super.destroy();
    }




}