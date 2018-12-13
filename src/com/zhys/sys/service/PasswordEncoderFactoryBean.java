package com.zhys.sys.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 加密者工厂bean
 * @author Lineshow    Lineshow7@gmail.com
 * @since 2014-5-16
 */
public class PasswordEncoderFactoryBean implements FactoryBean<PasswordEncoder>,BeanNameAware{

	private String beanName;
	
	public static final String REVERSIBLE_ENCODER = "standardEncoder";
	
	public static final String IRREVERSIBLE_ENCODER = "bcryptEncoder";
	
	@Override
	public PasswordEncoder getObject() throws Exception {
		PasswordEncoder encoder = null;
		if(REVERSIBLE_ENCODER.equals(beanName)){
			encoder = new StandardPasswordEncoder("ebmsz2014");
		}else{
			int i = 'i', e = 'e'*10, b = 'b'*100, m = 'm'*1000;
			SecureRandom random = new SecureRandom();
			random.setSeed(i+e+b+m);
			encoder = new BCryptPasswordEncoder(-1, random);
		}
		return encoder;
	}

	@Override
	public Class<? extends PasswordEncoder> getObjectType() {
		return  REVERSIBLE_ENCODER.equals(beanName)?StandardPasswordEncoder.class:BCryptPasswordEncoder.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

}
