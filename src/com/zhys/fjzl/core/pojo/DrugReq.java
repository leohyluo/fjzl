package com.zhys.fjzl.core.pojo;

import com.alibaba.fastjson.JSONArray;

public class DrugReq {

	private String orgId;
	private JSONArray drugs;
	
	public DrugReq() {}
	
	public DrugReq(String orgId, JSONArray drugs) {
		this.drugs = drugs;
		this.orgId = orgId;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public JSONArray getDrugs() {
		return drugs;
	}

	public void setDrugs(JSONArray drugs) {
		this.drugs = drugs;
	}
}
