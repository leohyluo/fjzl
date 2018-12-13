package com.zhys.fjzl.validator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhys.fjzl.annotation.FieldCheck;

public abstract class AbstractValidator implements IValidator {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractValidator.class);

	protected <T> Object getFieldValue(T t, Field field) {
		Object result = null;
		try {
			String fieldName = field.getName();
			String firstChar = fieldName.substring(0, 1).toUpperCase();
			String methodName = "get" + firstChar + fieldName.substring(1);
			Method method = t.getClass().getMethod(methodName);
			result = method.invoke(t);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error on getFieldValue", e);
		}
		return result;
	}
	
	protected <T> Long getRowno(T t) throws NoSuchMethodException, SecurityException, IllegalAccessException, 
			IllegalArgumentException, InvocationTargetException {
		Long rowno = 0L;
		String methodName = "getRowNo";
		Method method = t.getClass().getMethod(methodName);
		if(method != null) {
			Object result = method.invoke(t);
			if(result != null)
				rowno = Long.valueOf(result.toString());
		}
		return rowno;
	}
	
	public <T> String getFieldComment(Field field) {
		String comment = "";
		FieldCheck anno = field.getAnnotation(FieldCheck.class);
		if(anno != null)
			comment = anno.comment();
		return comment;
	}
}
