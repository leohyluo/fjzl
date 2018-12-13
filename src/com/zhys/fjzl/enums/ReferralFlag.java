package com.zhys.fjzl.enums;

public enum ReferralFlag {
	NO(0L),		 //未转诊
	YES(1L);     //已转诊

	private Long status;
	
	private ReferralFlag(Long status){
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}
	
	public static ReferralFlag findByStatus(Long status) {
		for(ReferralFlag item : values()) {
			if(item.status == status) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + status);
	}
}
