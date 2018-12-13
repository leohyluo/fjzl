package com.zhys.core.enums;

/**
 * 版权：智慧药师 <br/>
 * 作者：xuan.zhou@zhys.com <br/>
 * 生成日期：2013-11-21 <br/>
 * 描述：基础业务枚举类,K为泛型,为Code字段的类型定义
 */
public interface BaseEnum<K> {
	
	/**
	 * 得到存入Db/或者代表的值
	 * 
	 * @return
	 */
	K getCode();
	
	/**
	 * 描述信息
	 * 
	 * @return
	 */
	String getDesc();
    
}
