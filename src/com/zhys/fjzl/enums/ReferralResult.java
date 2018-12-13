package com.zhys.fjzl.enums;

public enum ReferralResult {

	DISEASE_LT(100L, "疾病{?}等级为{?}级,建议向下转诊"),
	DISEASE_MATCH(101L, "疾病{?}与机构等级相符,可继续就诊"),
	DISEASE_GT(102L, "疾病{?}等级为{?}级,建议向上转诊"),
	DRUG_LT(200L, "药品{?}等级为{?}级,建议向下转诊"),	
	DRUG_MATCH(201L, "药品{?}与机构等级相符,可继续就诊"),	
	DRUG_GT(202L, "药品{?}等级为{?}级,建议向上转诊"),	
	CHECK_LT(300L, "检查{?}等级为{?}级,建议向下转诊"),
	CHECK_MATCH(301L, "检查{?}与机构等级相符,可继续就诊"),
	CHECK_GT(302L, "检查{?}等级为{?}级,建议向上转诊"),
	MULTI_MATCH(400L, "疾病、检查、药品的等级与机构相符,可继续就诊"),
	NONE(0L, "无检测结果");
	
	private final Long type;
	private final String message;
	
	private ReferralResult(Long type, String message){
		this.type = type;
		this.message = message;
	}

	public Long getType() {
		return type;
	}
	
	public String getMessage() {
		return message;
	}

	public static ReferralResult findByStatus(Long type) {
		for(ReferralResult item : values()) {
			if(item.type == type) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + type);
	}
	
	public static void main(String[] args) {
		Long name = ReferralResult.CHECK_MATCH.getType();
		System.out.println(name);
	}
}
