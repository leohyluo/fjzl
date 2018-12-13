package com.zhys.fjzl.handler;

import java.io.File;
import java.util.List;

import com.zhys.fjzl.enums.FileType;

public interface IFileHandler<T> {
	
	FileType getFileType();
	
	boolean validate(List<T> list) throws Exception;

	List<T> read(File file) throws Exception;
	
	void doProcess(List<T> list) throws Exception;
	
}
