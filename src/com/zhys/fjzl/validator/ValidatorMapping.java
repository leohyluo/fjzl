package com.zhys.fjzl.validator;

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

import com.zhys.fjzl.annotation.Validator;
import com.zhys.fjzl.enums.ValidatorType;
import com.zhys.fjzl.handler.HandlerMapping;

@Component
public class ValidatorMapping {

	private static Logger logger = LoggerFactory.getLogger(HandlerMapping.class);

	private static String DEFAULT_HANDLER_PATH = "com.zhys.fjzl.validator";
	private static Map<ValidatorType, IValidator> map = new ConcurrentHashMap<>();
	private static boolean initialed = false;
	
	private ValidatorMapping() {
		
	}
	
	public static void initial() {
		new ValidatorMapping().init();
	}
	
//	@PostConstruct
	public void init() {
		try {
			System.out.println("init ValidatorMapping start...");
			Set<Class<?>> classes = new HashSet<>();
			scan(DEFAULT_HANDLER_PATH, classes);
			register(classes);
			initialed = true;
			System.out.println("init ValidatorMapping end");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error on HandlerMapping.init", e);
		}
	}
	
	public static IValidator getValidator(ValidatorType type) {
		if(!initialed) {
			logger.info("ValidatorMapping not initial on system start up");
			ValidatorMapping t = new ValidatorMapping();
			t.init();
		}
		return map.get(type);
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
				Validator validator = clazz.getAnnotation(Validator.class);
				if(validator != null) {
					classes.add(clazz);
				}
			}
		}
	}
	
	private void register(Set<Class<?>> classes) throws Exception {
		logger.info(classes.size() + " handler will be register.");
		String methodName = "getName";
		for(Class<?> clazz : classes) {
			Object obj = clazz.newInstance();
			Method method = clazz.getDeclaredMethod(methodName);
			Object objType = method.invoke(obj);
			if(objType != null) {
				ValidatorType type = (ValidatorType) objType;
				map.put(type, (IValidator) obj);
			}
		}
	}
	
	public static void main(String[] args) {
		ValidatorMapping mapping = new ValidatorMapping();
		mapping.init();
	}
}
