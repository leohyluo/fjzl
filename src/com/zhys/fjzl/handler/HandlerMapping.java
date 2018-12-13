package com.zhys.fjzl.handler;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhys.core.util.SpringContextHolder;
import com.zhys.fjzl.annotation.FileHandler;
import com.zhys.fjzl.enums.FileType;

@Component
public class HandlerMapping {
	
	private static Logger logger = LoggerFactory.getLogger(HandlerMapping.class);

	private static String DEFAULT_HANDLER_PATH = "com.zhys.fjzl.handler";
	private static Map<FileType, IFileHandler<?>> map = new ConcurrentHashMap<>();
	private static boolean initialed = false;
	
	private HandlerMapping() {
		
	}
	
	public static void initial() {
		new HandlerMapping().init();
	}
	
//	@PostConstruct
	public void init() {
		try {
			System.out.println("init HandlerMapping start...");
			Set<Class<?>> classes = new HashSet<>();
			scan(DEFAULT_HANDLER_PATH, classes);
			register(classes);
			initialed = true;
			System.out.println("init HandlerMapping end");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error on HandlerMapping.init", e);
		}
	}
	
	public IFileHandler<?> getHandler(FileType fileType) {
		if(!initialed) {
			logger.info("HandlerMapping not initial on system start up");
			HandlerMapping t = new HandlerMapping();
			t.init();
		}
		
		return map.get(fileType);
	}
	
	private void scan(String packPath, Set<Class<?>> classes) throws Exception {
		String originalPackPath = packPath;
		packPath = packPath.replace(".", File.separator);
		URL url = Thread.currentThread().getContextClassLoader().getResource(packPath);
		String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
		 
		File dir = new File(filePath);
		if(!dir.exists() || !dir.isDirectory()) {
			logger.warn("scan dir " + packPath + " not exists");
			return;
		}
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".class");
			}
		});
		
		for(File file : files) {
			String fileName = file.getName();
			fileName = fileName.replace(".class", "");
			String className = originalPackPath + "." + fileName;
			Class<?> clazz = Class.forName(className);
			if(!clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
				FileHandler fileHandler = clazz.getAnnotation(FileHandler.class);
				if(fileHandler != null) {
					classes.add(clazz);
				}
			}
		}
	}
	
	private void register(Set<Class<?>> classes) throws Exception {
		logger.info(classes.size() + " handler will be register.");
		String methodName = "getFileType";
		for(Class<?> clazz : classes) {
			String className = clazz.getName();
			String beanName = getDefaultBeanName(className);
			Object obj = clazz.newInstance();
			Method method = clazz.getDeclaredMethod(methodName);
			Object objFileType = method.invoke(obj);
			if(objFileType != null) {
				Object fileHandler = SpringContextHolder.getBean(beanName);
				FileType fileType = (FileType) objFileType;
				map.put(fileType, (IFileHandler<?>) fileHandler);
			}
		}
	}
	
	private String getDefaultBeanName(String className) {
		String beanName = className;
		int lastIndex = className.lastIndexOf("."); 
		if(lastIndex != -1) {
			String firstChar = String.valueOf(className.charAt(lastIndex + 1)).toLowerCase();
			beanName = firstChar + className.substring(lastIndex + 2);
			
		}
		return beanName;
	}
	
	public static void main(String[] args) {
		HandlerMapping mapping = new HandlerMapping();
		mapping.init();
	}
}
