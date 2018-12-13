package com.zhys.fjzl.enums;

public enum ValidatorType {

	NULLABLE("nullable"),
	FORMAT("format"),
	NUMBER("isNumber");
	
	private String type;
	
	private ValidatorType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	
}
