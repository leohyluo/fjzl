package com.zhys.fjzl.core.pojo;


import java.util.List;

import com.zhys.fjzl.enums.ReferralResult;
import com.zhys.fjzl.enums.ReferralRuleType;
import com.zhys.sys.domain.Org;

public class ReferralRes {

	private ReferralRuleType type;
	private ReferralResult result;
	private Long grade;
	private String message;
	private List<Org> referralOrgList;
	private String recordId;	//转诊记录id
	private static final String splitChar = "\\{\\?\\}";
	
	public ReferralRes () {}
	
	public ReferralRes(ReferralResult result, String[] args, List<Org> referralOrgList,  Long grade) throws Exception {
		String message = result.getMessage();
		String[] arr = message.split(splitChar);
		int argLen = args != null ? args.length : 0;
		if(arr.length -1 > argLen) {
			throw new Exception("could not create ReferralRes, for args dismatch");
		}
		for(int i = 0; i < argLen; i++) {
			message = message.replaceFirst(splitChar, args[i]);
		}
		this.result = result;
		this.grade = grade;
		this.message = message;
		this.referralOrgList = referralOrgList;
	}
	
	public List<Org> getReferralOrgList() {
		return referralOrgList;
	}

	public void setReferralOrgList(List<Org> referralOrgList) {
		this.referralOrgList = referralOrgList;
	}

	
	public ReferralResult getResult() {
		return result;
	}

	public void setResult(ReferralResult result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Long getGrade() {
		return grade;
	}

	public void setGrade(Long grade) {
		this.grade = grade;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public ReferralRuleType getType() {
		return type;
	}

	public void setType(ReferralRuleType type) {
		this.type = type;
	}

	public static void main(String[] args) {
		String str = "疾病{?}与机构等级相符,可继续就诊";
		//String str = "疾病{?}等级为{?}级,建议向上转诊";
		String[] arr = str.split(splitChar);
		for(String item : arr) {
			System.out.println(item);
		}
		System.out.println(arr.length);
		
		/*try {
			String[] arg = {"流行性感冒", "梅山苑社康"};
			new ReferralRes(ReferralResult.DISEASE_GT, arg, null, 0L);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
