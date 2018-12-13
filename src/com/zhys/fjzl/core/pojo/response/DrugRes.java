package com.zhys.fjzl.core.pojo.response;

import com.zhys.fjzl.enums.HospitalGradeView;

public class DrugRes {

	private String id;
	private String name;
	private String grade;
	
	public DrugRes (String id, String name, String grade) {
		this.id = id;
		this.name = name;
		this.grade = HospitalGradeView.getByGradeVal(grade).getGradeName();
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
}
