package com.zhys.fjzl.validator;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhys.fjzl.annotation.Validator;
import com.zhys.fjzl.enums.ValidatorType;

@Validator
public class NumberValidator extends AbstractValidator {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String REGEX = "[0-9]*";
	private static Pattern pattern = null;
	static {
		pattern = Pattern.compile(REGEX);
	}

	@Override
	public <T> void verify(T t, Field field) throws Exception {
		String fieldName = field.getName();
		ValidatorMapping.getValidator(ValidatorType.NULLABLE).verify(t, field);
		String result = getFieldValue(t, field).toString();
		if(!pattern.matcher(result).matches()) {
			Long rowno = getRowno(t);
			String comment = getFieldComment(field);
			logger.error(t.getClass().getName()+ "." + fieldName + " is not a number, which rowno is " + rowno);
			throw new NullPointerException("文件第"+rowno+"行字段"+comment+"为非整型");
		}
	}

	@Override
	public ValidatorType getName() {
		return ValidatorType.NUMBER;
	}

}
