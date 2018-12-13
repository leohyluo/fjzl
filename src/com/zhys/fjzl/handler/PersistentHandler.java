package com.zhys.fjzl.handler;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhys.fjzl.enums.FileType;
import com.zhys.sys.domain.User;

@Component
public class PersistentHandler {
	
	private FileType fileType;
	private File file;
	private IFileHandler<?> fileHandler;
	private Logger logger = LoggerFactory.getLogger(PersistentHandler.class);
	@Resource
	private HandlerMapping handlerMapping;

	private void load(FileType fileType, File file) {
		this.fileType = fileType;
		this.file = file;
		this.fileHandler = handlerMapping.getHandler(fileType);
		if(this.fileHandler == null) {
			logger.error("FileHandler not found by FileType " + fileType.getType());
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void persistent() throws Exception {
		List list = fileHandler.read(file);
		if(!fileHandler.validate(list)) {
			logger.warn("invalid file " + file.getName() + " with " + fileType);
			return;
		}
		fileHandler.doProcess(list);
	}
	
	public void handle(String fileType, String filePath) throws Exception {
		FileType type = FileType.findByType(fileType);
		File file = new File(filePath);
		this.load(type, file);
		this.persistent();
		file.delete();
	}
	
	public void setHandlerMapping(HandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	public static void main(String[] args) {
		try {
			PersistentHandler t = new PersistentHandler();
			t.load(FileType.EBM_DISEASE, null);
			t.persistent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
