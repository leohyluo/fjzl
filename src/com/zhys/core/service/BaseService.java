package com.zhys.core.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.zhys.core.dao.SqlDao;
import com.zhys.fjzl.annotation.FieldCheck;
import com.zhys.fjzl.domain.EbmDisease;

/**
 * 
 * 版权：智慧药师 <br/>
 * 作者：dailing <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：基础对象类
 */
public class BaseService implements ServletContextAware{

    protected ServletContext servletContext;

    @Autowired
    @Qualifier("sqlDao")
    protected SqlDao sqlDao;


    @Autowired
    @Qualifier("pageService")
    protected AbstractPageService pageService;
    protected Log logger = LogFactory.getLog(this.getClass());

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public WebApplicationContext getWebApplicationContext(){
        return WebApplicationContextUtils.getWebApplicationContext(this.servletContext);
    }

    public SqlDao getSqlDao() {
        return sqlDao;
    }

    public void setSqlDao(SqlDao sqlDao){
        this.sqlDao = sqlDao;
    }

    public AbstractPageService getPageService() {
        return pageService;
    }

    public void setPageService(AbstractPageService pageService) {
        this.pageService = pageService;
    }

    @SuppressWarnings("unchecked")
	public <T> T getBean(String id) {
        return (T)getWebApplicationContext().getBean(id);
    }
    
    public <T> boolean hasSpecialFieldUpdate(T t, T t2) throws Exception {
    	boolean specialFieldChanged = false;
    	Field[] fields = t.getClass().getDeclaredFields();
    	for(Field field : fields) {
    		FieldCheck fieldCheck = field.getAnnotation(FieldCheck.class);
    		if(fieldCheck != null) {
    			Class<?> typeClass = field.getType();
    			String fieldName = field.getName();
    			String firstChar = fieldName.substring(0, 1).toUpperCase();
    			String methodName = "get" + firstChar + fieldName.substring(1);
    			Method method1 = t.getClass().getMethod(methodName);
    			Method method2 = t2.getClass().getMethod(methodName);
    			Object v1 = method1.invoke(t);
    			Object v2 = method2.invoke(t2);
    			
    			if(v1 == null || v2 == null) {
    				continue;
    			}
    			
    			if(typeClass == String.class) {
    				if(!v1.toString().equals(v2.toString())) {
    					specialFieldChanged = true;
    				}
    			} else if (typeClass == Long.class) {
    				if(Long.valueOf(v1.toString()).longValue() != Long.valueOf(v2.toString()).longValue()) {
    					specialFieldChanged = true;
    				}
    			}
    			if(specialFieldChanged) {
    				break;
    			}
    		}
    	}
    	return specialFieldChanged;
    }

    /*public static void main(String[] args) {
		EbmDisease d1 = new EbmDisease();
		d1.setDisease_name("疾病1");
		d1.setIcd_10("324");
		d1.setGrade(1280L);
		
		EbmDisease d2 = new EbmDisease();
		d2.setDisease_name("疾病1");
		d2.setIcd_10("324");
		d2.setGrade(1280L);
		
		BaseService thiz = new BaseService();
		try {
			boolean flag = thiz.hasSpecialFieldUpdate(d1, d2);
			System.out.println(flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
