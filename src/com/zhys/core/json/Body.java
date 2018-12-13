package com.zhys.core.json;

import java.io.Serializable;

/**
 * 
 * 版权：智慧药师 <br/>
 * 作者：kai.gao@zhys.com <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：返回前端JSON对象Body部分
 */
public class Body implements Serializable {
	
	private static final long serialVersionUID = -8392636493848545165L;
	
	private String code;
	private String message;
	private Object result;
	
	public String toString() {
		return "code:" + code + ", message:" + message + ", result:" + result;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void setResult(Object result) {
		this.result = result;
	}
}
