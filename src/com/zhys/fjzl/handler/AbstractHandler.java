package com.zhys.fjzl.handler;

import java.util.List;

import com.zhys.fjzl.annotation.AnnotationUtils;


public abstract class AbstractHandler<T> implements IFileHandler<T> {

	@Override
	public boolean validate(List<T> list) throws Exception {
		for(T t : list) {
			AnnotationUtils.verify(t);
		}
		return true;
	}
}
