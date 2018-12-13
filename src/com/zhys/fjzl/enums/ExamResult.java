package com.zhys.fjzl.enums;

public enum ExamResult {
	NOTPASS(0L),	//分级诊疗三大目录检查不通过
	PASS(1L);      //分级诊疗三大目录检查通过

	private Long status;
	
	private ExamResult(Long status){
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}
	
	public static ExamResult findByStatus(Long status) {
		for(ExamResult item : values()) {
			if(item.status == status) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + status);
	}
}
