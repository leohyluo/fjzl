package com.zhys.fjzl.enums;

public enum FileType {

	EBM_DISEASE("EBM_DISEASE"),
	EBM_XY("EBM_XY"),
	EBM_ZCY("EBM_ZCY"),
	EBM_ZYYP("EBM_ZYYP"),
	EBM_BODYCHECK("EBM_BODYCHECK"),
	MID_DISEASE("MID_DISEASE"),
	MID_XY("MID_XY"),
	MID_ZCY("MID_ZCY"),
	MID_ZYYP("MID_ZYYP"),
	MID_BODYCHECK("MID_BODYCHECK"),
	HIS_DISEASE("HIS_DISEASE"),
	HIS_XY("HIS_XY"),
	HIS_ZCY("HIS_ZCY"),
	HIS_ZYYP("HIS_ZYYP"),
	HIS_BODYCHECK("HIS_BODYCHECK");
	
	private String type;
	
	private FileType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	public static FileType findByType(String type) {
		for(FileType item : values()) {
			if(item.type.equals(type)) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + type + " type!");
	}
}
