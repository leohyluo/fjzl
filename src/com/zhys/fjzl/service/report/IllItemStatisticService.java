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
 * 违规项数量统计报表
 * @author Administrator
 *
 */
@Service("IllItemStatisticService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class IllItemStatisticService extends BaseService {
	
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
		List<Map<String, Object>> dataList = sqlDao.list("illItemCountStatistic.main", mapper);
		return toJson(dataList);
	}
	
	private JSONObject toJson(List<Map<String, Object>> dataList) {
		JSONObject json = new JSONObject();
		JSONArray series_data = new JSONArray();
		JSONArray diseaseNameArr = new JSONArray();
		for(int i = 0; i < dataList.size(); i++) {
			Map<String, Object> map = dataList.get(i);
			String diseaseName = StringUtil.null2string(map.get("item_name"));
			String num = StringUtil.null2string(map.get("num"));
			
			diseaseNameArr.add(diseaseName);
			series_data.add(num);
		}
		json.put("xAxis_data", diseaseNameArr);
		json.put("series_data", series_data);
		return json;
	}
}
