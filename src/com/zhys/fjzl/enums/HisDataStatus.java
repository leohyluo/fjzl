package com.zhys.fjzl.enums;

public enum HisDataStatus {

	UNMAPER(0L),		//未对照
	MAPED(1L),			//已对照
	MAPER_FAIL(2L),		//对照失败
	REVIEWED(3L),		//已审核
	UNREVIEWED(4L),		//未审核	
	REVIEW_FAIL(5L);	//审核不通过
	
	private Long status;
	
	private HisDataStatus(Long status){
		this.status = status;
	}

	public Long getStatus() {
		return status;
	}
	
	public static HisDataStatus findByStatus(Long status) {
		for(HisDataStatus item : values()) {
			if(item.status == status) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + status);
	}
}
