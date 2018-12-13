package com.zhys.fjzl.enums;

public enum EbmStatus {

	UNREVIEW(0L),		//未审核
	REVIEWED(1L),		//已审核
	REVIEW_FAIL(2L);	//审核不通过
	
	private Long status;
	
	private EbmStatus(Long status){
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}
	
	public static EbmStatus findByStatus(Long status) {
		for(EbmStatus item : values()) {
			if(item.status == status) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + status);
	}
}
