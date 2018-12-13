package com.zhys.fjzl.validator;

import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.annotation.Validator;
import com.zhys.fjzl.enums.ValidatorType;

@Validator
public class NullPointerValidator extends AbstractValidator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public <T> void verify(T t, Field field) throws Exception {
		String fieldName = field.getName();
		Object result = getFieldValue(t, field);
		if(StringUtil.isEmpty(StringUtil.null2string(result))) {
			Long rowno = getRowno(t);
			String comment = getFieldComment(field);
			logger.error(t.getClass().getName()+ "." + fieldName + " is null, which rowno is " + rowno);
			throw new NullPointerException("文件第"+rowno+"行字段"+comment+"为空");
		}
	}

	@Override
	public ValidatorType getName() {
		return ValidatorType.NULLABLE;
	}

}
