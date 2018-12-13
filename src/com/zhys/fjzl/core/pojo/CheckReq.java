package com.zhys.fjzl.core.pojo;

import java.util.Arrays;
import java.util.List;

public class CheckReq {

	private String orgId;
	private List<String> checkIds;
	
	public CheckReq() {}
	
	public CheckReq(String orgId, String ids) {
		String[] arr = ids.split(",");
		List<String> list = Arrays.asList(arr);
		this.checkIds = list;
		this.orgId = orgId;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<String> getCheckIds() {
		return checkIds;
	}
	public void setCheckIds(List<String> checkIds) {
		this.checkIds = checkIds;
	}
}
