package com.zhys.fjzl.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.zhys.fjzl.handler.HandlerMapping;
import com.zhys.fjzl.validator.ValidatorMapping;

public class FjzlServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("====================================>");
		HandlerMapping.initial();
		ValidatorMapping.initial();
	}
}
