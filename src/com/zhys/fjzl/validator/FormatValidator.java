package com.zhys.fjzl.validator;

import java.lang.reflect.Field;

import com.zhys.fjzl.annotation.Validator;
import com.zhys.fjzl.enums.ValidatorType;

@Validator
public class FormatValidator extends AbstractValidator {
	

	@Override
	public <T> void verify(T t, Field field) throws Exception {
		System.out.println("格式验证器暂时没实现");
	}

	@Override
	public ValidatorType getName() {
		return ValidatorType.FORMAT;
	}

}
