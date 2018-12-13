package com.zhys.fjzl.enums;

public enum HospitalGradeView {

	LEVEL1("1", "一级"),
	LEVEL2("2","二级"),
	LEVEL3("3","三级");
	
	private String gradeVal;
	private String gradeName;
	
	private HospitalGradeView(String gradeVal, String gradeName) {
		this.gradeVal = gradeVal;
		this.gradeName = gradeName;
	}

	public String getGradeVal() {
		return gradeVal;
	}

	public void setGradeVal(String gradeVal) {
		this.gradeVal = gradeVal;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
	public static HospitalGradeView getByGradeVal(String gradeVal) {
		for(HospitalGradeView item : values()) {
			if(item.gradeVal.equals(gradeVal)) {
				return item;
			}
		}
		throw new IllegalArgumentException("can not create menu HospitalGrade by grade : " + gradeVal);
	}
	
	public static HospitalGradeView getByGradeName(String gradeName) {
		for(HospitalGradeView item : values()) {
			if(item.gradeName.equals(gradeName)) {
				return item;
			}
		}
		throw new IllegalArgumentException("can not create menu HospitalGrade by grade : " + gradeName);
	}
}
