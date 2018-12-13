package com.zhys.fjzl.core.pojo;

public class DoctorInfoReq {

	private String doctorCode;
	private String doctorName;
	private String mobile;
	private String department;
	private String doctorGrade;
	
	public DoctorInfoReq(String doctorCode, String doctorName, String mobile, String department, String doctorGrade) {
		this.doctorCode = doctorCode;
		this.doctorName = doctorName;
		this.mobile = mobile;
		this.department = department;
		this.doctorGrade = doctorGrade;
	}
	
	public String getDoctorCode() {
		return doctorCode;
	}
	public void setDoctorCode(String doctorCode) {
		this.doctorCode = doctorCode;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDoctorGrade() {
		return doctorGrade;
	}

	public void setDoctorGrade(String doctorGrade) {
		this.doctorGrade = doctorGrade;
	}
	
}
