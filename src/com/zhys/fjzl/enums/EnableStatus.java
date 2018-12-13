package com.zhys.fjzl.enums;

public enum EnableStatus {
	DISABLE(0L),		//停用
	ENABLE(1L);      //启用

	private Long status;
	
	private EnableStatus(Long status){
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}
	
	public static EnableStatus findByStatus(Long status) {
		for(EnableStatus item : values()) {
			if(item.status == status) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + status);
	}
}
