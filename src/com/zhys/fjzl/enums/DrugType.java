package com.zhys.fjzl.enums;

public enum DrugType {

	XY(1L),		//西药
	ZCY(2L),	//中成药
	ZYYP(3L);	//中药饮片
	
	private Long type;
	
	private DrugType(Long type){
		this.type = type;
	}

	public Long getType() {
		return type;
	}
	
	public static DrugType findByType(Long type) {
		for(DrugType item : values()) {
			if(item.type == type) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + type);
	}
}
