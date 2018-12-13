package com.zhys.fjzl.service.report;

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
 * 是否转诊报表业务逻辑
 * @author Administrator
 *
 */
@Service("ReferralRateService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class ReferralRateService extends BaseService {
	
	@Resource(name = "sysOrgService")
	private OrgService orgService;

	public JSONObject getData(PageParam mapper, User user) throws Exception {
		String yltId = StringUtil.null2string(mapper.get("yltId"));
		String orgId = StringUtil.null2string(mapper.get("orgid"));
		if(StringUtil.isEmpty(orgId) && !"1".equals(user.getSu_admin())) {
			String so_id = user.getSo_id();
			mapper.put("orgid", so_id);
		}
		if(StringUtil.isNotEmpty(yltId)) {
			Object[] subOrgArr = orgService.getSubOrgIds(yltId);
			mapper.put("org_id_arr", subOrgArr);
		}
		List<Map<String, Integer>> dataList = sqlDao.list("fjzl_api_record.report_4_referralRate", mapper);
		return toJson(dataList);
	}
	
	private JSONObject toJson(List<Map<String, Integer>> dataList) {
		JSONObject json = new JSONObject();
		
		JSONArray series_data = new JSONArray();
		for(int i = 0; i < dataList.size(); i++) {
			Map<String, Integer> map = dataList.get(i);
			for(String key : map.keySet()) {
				System.out.println("key=" +key+",value="+map.get(key));
			}
			Integer referralFlag = map.get("referralflag");
			Long num = Long.parseLong(map.get("num")+"");
			String name = referralFlag == 1 ? "已转诊" : "未转诊";
			
			JSONObject itemJson = new JSONObject();
			itemJson.put("name", name);
			itemJson.put("value", num);
			series_data.add(itemJson);
		}
		json.put("series_data", series_data);
		return json;
	}
}
