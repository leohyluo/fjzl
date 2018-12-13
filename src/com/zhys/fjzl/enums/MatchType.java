package com.zhys.fjzl.enums;

public enum MatchType {

	NONE(0L),
	AUTO(1L),		//程序自动对照
	MAUAL(2L);		//人工对照
	
	private Long type;
	
	private MatchType(Long type){
		this.type = type;
	}

	public Long getType() {
		return type;
	}
	
	public static MatchType findByStatus(Long type) {
		for(MatchType item : values()) {
			if(item.type == type) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + type);
	}
}
