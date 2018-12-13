package com.zhys.fjzl.core.pojo.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.sys.domain.Org;

public class FjzlResult {

	private String recordId; //ApiRecord表主键
	private Org currentOrg;
	private List<DiseaseRes> diseaseList;
	private List<DrugRes> drugList;
	private List<CheckRes> checkList;
	private List<Org> orgList;
	
	public List<DiseaseRes> getDiseaseList() {
		return diseaseList;
	}
	public void setDiseaseList(List<DiseaseRes> diseaseList) {
		this.diseaseList = diseaseList;
	}
	public List<DrugRes> getDrugList() {
		return drugList;
	}
	public void setDrugList(List<DrugRes> drugList) {
		this.drugList = drugList;
	}
	public List<CheckRes> getCheckList() {
		return checkList;
	}
	public void setCheckList(List<CheckRes> checkList) {
		this.checkList = checkList;
	}
	public List<Org> getOrgList() {
		return orgList;
	}
	public void setOrgList(List<Org> orgList) {
		this.orgList = orgList;
	}
	public Org getCurrentOrg() {
		return currentOrg;
	}
	public void setCurrentOrg(Org currentOrg) {
		this.currentOrg = currentOrg;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public JSONObject toCommonExplain() {
		JSONObject json = new JSONObject();
		List<DiseaseRes> list1 = this.getDiseaseList();
		List<DrugRes> list2 = this.getDrugList();
		List<CheckRes> list3 = this.getCheckList();
		List<Org> orgList = this.getOrgList();
		
		//将检查结果进行同级分组
		Map<String, List<DiseaseRes>> diseaseMap = new HashMap<>();
		Map<String, List<DrugRes>> drugMap = new HashMap<>();
		Map<String, List<CheckRes>> checkMap = new HashMap<>();
		if(CollectionUtils.isNotEmpty(list1)) {
			for(DiseaseRes item : list1) {
				String igrade = item.getGrade();
				if(diseaseMap.containsKey(igrade)) {
					diseaseMap.get(igrade).add(item);
				} else {
					List<DiseaseRes> itemList = new ArrayList<>();
					itemList.add(item);
					diseaseMap.put(igrade, itemList);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(list2)) {
			for(DrugRes item : list2) {
				String igrade = item.getGrade();
				if(drugMap.containsKey(igrade)) {
					drugMap.get(igrade).add(item);
				} else {
					List<DrugRes> itemList = new ArrayList<>();
					itemList.add(item);
					drugMap.put(igrade, itemList);
				}
			}
		}
		if(CollectionUtils.isNotEmpty(list3)) {
			for(CheckRes item : list3) {
				String igrade = item.getGrade();
				if(checkMap.containsKey(igrade)) {
					checkMap.get(igrade).add(item);
				} else {
					List<CheckRes> itemList = new ArrayList<>();
					itemList.add(item);
					checkMap.put(igrade, itemList);
				}
			}
		}
		//拼装疾病检测不通过项
		StringBuffer diseaseSb = new StringBuffer();
		Set<String> diseaseKeys = diseaseMap.keySet();
		if(CollectionUtils.isNotEmpty(diseaseKeys)) {
			diseaseSb.append("疾病:");
		}
		for(String itemGrade : diseaseMap.keySet()) {
			List<DiseaseRes> itemDiseaseList = diseaseMap.get(itemGrade);
			StringBuffer sb = new StringBuffer();
			for(DiseaseRes itemDiseaseRes : itemDiseaseList) {
				sb.append(itemDiseaseRes.getName()).append(",");				
			}
			String diseaseNames = StringUtil.removeLastChar(sb.toString());
			diseaseSb.append(diseaseNames).append("为").append(itemGrade).append("医院治疗范围");
		}
		json.put("diseaseInfo", diseaseSb.toString());
		//拼装药品检测不通过项
		StringBuffer drugSb = new StringBuffer();
		Set<String> drugKeys = drugMap.keySet();
		if (CollectionUtils.isNotEmpty(drugKeys)) {
			drugSb.append("药品:");
		}
		for (String itemGrade : drugMap.keySet()) {
			List<DrugRes> itemDrugList = drugMap.get(itemGrade);
			StringBuffer sb = new StringBuffer();
			for (DrugRes itemDrugRes : itemDrugList) {
				sb.append(itemDrugRes.getName()).append(",");
			}
			String drugNames = StringUtil.removeLastChar(sb.toString());
			drugSb.append(drugNames).append("为").append(itemGrade).append("医院治疗范围");
		}
		json.put("drugInfo", drugSb.toString());
		//拼装检查检测不通过项
		StringBuffer checkSb = new StringBuffer();
		Set<String> checkKeys = checkMap.keySet();
		if (CollectionUtils.isNotEmpty(checkKeys)) {
			checkSb.append("检查:");
		}
		for (String itemGrade : checkKeys) {
			List<CheckRes> itemCheckList = checkMap.get(itemGrade);
			StringBuffer sb = new StringBuffer();
			for (CheckRes itemCheckRes : itemCheckList) {
				sb.append(itemCheckRes.getName()).append(",");
			}
			String checkNames = StringUtil.removeLastChar(sb.toString());
			checkSb.append(checkNames).append("为").append(itemGrade).append("医院治疗范围,");
		}
		json.put("checkInfo", checkSb.toString());
		if(CollectionUtils.isNotEmpty(orgList)) {
			JSONArray jarr = new JSONArray();
			for(Org item : orgList) {
				JSONObject itemJson = new JSONObject();
				itemJson.put("id", item.getSo_id());
				itemJson.put("name", item.getSo_name());
				jarr.add(itemJson);
			}
			json.put("orgList", jarr);
		}
		json.put("recordId", this.getRecordId());
		return json;
	}
}
