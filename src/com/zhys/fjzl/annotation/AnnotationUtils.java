package com.zhys.fjzl.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.StringUtils;

import com.zhys.fjzl.enums.ValidatorType;
import com.zhys.fjzl.validator.ValidatorMapping;

public class AnnotationUtils {
	
	@SuppressWarnings("unchecked")
	public static <T> void verify(T t) throws Exception {
		Class<T> clazz = (Class<T>) t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields) {
			FieldCheck fieldCheck = field.getAnnotation(FieldCheck.class);
			if(fieldCheck == null) {
				continue;
			}
			if(fieldCheck.enable()) {
				String verifyFunction = fieldCheck.verifyFunction();
				if(StringUtils.isEmpty(verifyFunction)) {
					if(fieldCheck.nullable() == false) {
						ValidatorMapping.getValidator(ValidatorType.NULLABLE).verify(t, field);
					}
					if(fieldCheck.isNumber()) {
						ValidatorMapping.getValidator(ValidatorType.NUMBER).verify(t, field);
					}
					String format = fieldCheck.format();
					if(!StringUtils.isEmpty(format)) {
						ValidatorMapping.getValidator(ValidatorType.FORMAT).verify(t, field);
					}
				} else {
					Method method = clazz.getMethod(verifyFunction);
					method.invoke(t);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		/*EbmDisease d = new EbmDisease();
		d.setDiseaseName("流行性感冒");
		try {
			verify(d);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
