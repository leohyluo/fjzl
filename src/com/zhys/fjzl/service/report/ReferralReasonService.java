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
 * 转诊原因占比统计
 * @author Administrator
 *
 */
@Service("ReferralReasonService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class ReferralReasonService extends BaseService {
	
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
		List<Map<String, Object>> dataList = sqlDao.list("referralResonStatistic.main", mapper);
		return toJson(dataList);
	}
	
	private JSONObject toJson(List<Map<String, Object>> dataList) {
		JSONObject json = new JSONObject();
		
		JSONArray series_data = new JSONArray();
		JSONArray legend_data = new JSONArray();
		for(int i = 0; i < dataList.size(); i++) {
			Map<String, Object> map = dataList.get(i);
			
			String referralType = StringUtil.null2string(map.get("referral_type"));
			String num = StringUtil.null2string(map.get("num"));
			String name = "";
			
			if("1".equals(referralType)) {
				name = "疾病";
			} else if ("2".equals(referralType)) {
				name = "药品";
			} else if ("3".equals(referralType)) {
				name = "检查";
			}
			
			JSONObject itemJson = new JSONObject();
			itemJson.put("name", name);
			itemJson.put("value", num);
			series_data.add(itemJson);
			legend_data.add(name);
		}
		json.put("series_data", series_data);
		json.put("legend_data", legend_data);
		return json;
	}
}
