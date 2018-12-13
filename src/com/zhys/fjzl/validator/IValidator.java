package com.zhys.fjzl.validator;

import java.lang.reflect.Field;

import com.zhys.fjzl.enums.ValidatorType;

public interface IValidator {

	<T> void verify(T t, Field field) throws Exception;
	
	ValidatorType getName();
}
