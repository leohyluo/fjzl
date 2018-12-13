package com.zhys.fjzl.enums;

public enum HospitalGrade {

	LEVEL1("一级",1L),
	LEVEL2("二级",2L),
	LEVEL3("三级",3L),
	LEVEL1_1("一级医院",1L),
	LEVEL2_1("二级医院",2L),
	LEVEL3_1("三级医院",3L);
	
	private String name;
	private Long grade;
	
	private HospitalGrade(String name, Long grade) {
		this.name = name;
		this.grade = grade;
	}

	public Long getGrade() {
		return grade;
	}
	
	public String getName() {
		return name;
	}
	
	public static HospitalGrade getByName(String name) {
		for(HospitalGrade item : values()) {
			if(item.name.trim().equals(name)) {
				return item;
			}
		}
		throw new IllegalArgumentException("can not create menu HospitalGrade by name : " + name);
	}
	
	public static HospitalGrade getByGrade(Long grade) {
		for(HospitalGrade item : values()) {
			if(item.grade.longValue() == grade.longValue()) {
				return item;
			}
		}
		throw new IllegalArgumentException("can not create menu HospitalGrade by grade : " + grade);
	}
}
