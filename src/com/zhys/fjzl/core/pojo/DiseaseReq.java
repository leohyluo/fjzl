package com.zhys.fjzl.core.pojo;

import java.util.Arrays;
import java.util.List;

import com.zhys.core.util.StringUtil;

public class DiseaseReq {

	private String orgId;
	private List<String> diseaseIds;
	private List<String> diseaseNames;
	
	public DiseaseReq(){}
	
	public DiseaseReq(String orgId, String ids) {
		String[] arr = ids.split(",");
		List<String> list = Arrays.asList(arr);
		this.diseaseIds = list;
		this.orgId = orgId;
	}
	
	public DiseaseReq(String orgId, String ids, String names) {
		this.orgId = orgId;
		if(StringUtil.isNotEmpty(ids)) {
			String[] arr = ids.split(",");
			List<String> list = Arrays.asList(arr);
			this.diseaseIds = list;
		}
		if(StringUtil.isNotEmpty(names)) {
			String[] arr = names.split(",");
			List<String> nameList = Arrays.asList(arr);
			this.diseaseNames = nameList;
		}
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<String> getDiseaseIds() {
		return diseaseIds;
	}
	public void setDiseaseIds(List<String> diseaseIds) {
		this.diseaseIds = diseaseIds;
	}

	public List<String> getDiseaseNames() {
		return diseaseNames;
	}

	public void setDiseaseNames(List<String> diseaseNames) {
		this.diseaseNames = diseaseNames;
	}
	
}
