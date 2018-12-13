package com.zhys.fjzl.enums;

public enum ReferralRuleType {
	
	DISEASE(1),
	DRUG(2),
	//DRUGMI(3),
	BODYCHECK(3),
	MULTI(4);

	private Integer type;
	
	private ReferralRuleType(Integer type) {
		this.type = type;
	}
	
	public Integer getType() {
		return this.type;
	}
	
	public static ReferralRuleType findByType(Integer type) {
		for(ReferralRuleType item : values()) {
			if(item.type == type) {
				return item;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + type);
	}
}
