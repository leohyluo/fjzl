package com.zhys.fjzl.core.pojo;


import com.zhys.fjzl.enums.ReferralRuleType;

public class ReferralResDetail {

	private ReferralRuleType type;
	private String id;
	private String name;
	private Long grade;
	private String message;
	
	public ReferralResDetail(ReferralRuleType type,String id,String name,Long grade,String message) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.message = message;
	}
	
	public ReferralRuleType getType() {
		return type;
	}
	public void setType(ReferralRuleType type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getGrade() {
		return grade;
	}
	public void setGrade(Long grade) {
		this.grade = grade;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
