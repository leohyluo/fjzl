package com.zhys.fjzl.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldCheck {
	
	boolean nullable() default true;
	String format() default "";
	boolean isNumber() default false;
	String verifyFunction() default "";
	boolean enable() default true;
	String comment() default "";
}
