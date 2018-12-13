package com.zhys.fjzl.core.pojo;

public class PatientInfoReq {

	private String orgId;
	private String name;
	//private Integer age;
	private Integer gender;
	private String birth;
	
	public PatientInfoReq() {}
	
	/*public PatientInfoReq(String orgId, String name, Integer age, Integer gender) {
		this.orgId = orgId;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}*/
	
	public PatientInfoReq(String orgId, String name, String birth, Integer gender) {
		this.orgId = orgId;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/*public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}*/
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}	
}
