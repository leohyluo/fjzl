package com.zhys.fjzl.service.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.core.util.StringUtil;
import com.zhys.sys.domain.User;
import com.zhys.sys.service.OrgService;

/**
 * 医生转诊数量统计逻辑
 * @author Administrator
 *
 */
@Service("DoctorReferralStatisticService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class DoctorReferralStatisticService extends BaseService {
	
	@Resource(name = "sysOrgService")
	private OrgService orgService;

	public JSONObject getData(PageParam mapper, User user) throws Exception {
		String yltId = StringUtil.null2string(mapper.get("yltId"));
		String orgId = StringUtil.null2string(mapper.get("orgid"));
		if(StringUtil.isNotEmpty(yltId)) {
			Object[] subOrgArr = orgService.getSubOrgIds(yltId);
			mapper.put("org_id_arr", subOrgArr);
		}
		if(StringUtil.isEmpty(orgId) && !"1".equals(user.getSu_admin())) {
			String so_id = user.getSo_id();
			mapper.put("orgid", so_id);
		}
		List<Map<Object, Object>> dataList = sqlDao.list("doctorReferralStatistic.main", mapper);
		return toJson(dataList);
	}
	
	private JSONObject toJson(List<Map<Object, Object>> dataList) {
		JSONObject json = new JSONObject();
		
		Map<String, Map<String, String>> orgReferralMap = new HashMap<>();
		
		for(int i = 0; i < dataList.size(); i++) {
			Map<Object, Object> map = dataList.get(i);
			String doctorName = map.get("doctor_name").toString();
			String referralFlag = map.get("referral_flag").toString();
			String num = map.get("num").toString();
			String key = "1".equals(referralFlag) ? "referralNum" : "unreferralNum";
			
			if(orgReferralMap.containsKey(doctorName)) {
				Map<String, String> subMap = orgReferralMap.get(doctorName);
				subMap.put(key, num);
			} else {
				Map<String, String> subMap = new HashMap<>();
				subMap.put(key, num);
				orgReferralMap.put(doctorName, subMap);
			}
		}
		JSONArray orgNameArr = new JSONArray();
		JSONArray referralNumArr = new JSONArray();
		JSONArray unreferralNumArr = new JSONArray();
		for(String orgName : orgReferralMap.keySet()) {
			Map<String, String> subMap = orgReferralMap.get(orgName);
			orgNameArr.add(orgName);
			String referralNum = subMap.containsKey("referralNum") ? subMap.get("referralNum") : "0";
			referralNumArr.add(referralNum);
			String unreferralNum = subMap.containsKey("unreferralNum") ? subMap.get("unreferralNum") : "0";
			unreferralNumArr.add(unreferralNum);
		}
		json.put("doctorNameArr", orgNameArr);
		json.put("referralNumArr", referralNumArr);
		json.put("unreferralNumArr", unreferralNumArr);
		return json;
	}
}
