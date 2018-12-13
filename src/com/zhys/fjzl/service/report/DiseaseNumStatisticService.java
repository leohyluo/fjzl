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
 * 已转诊疾病数量统计
 * @author Administrator
 *
 */
@Service("DiseaseNumStatisticService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class DiseaseNumStatisticService extends BaseService {
	
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
		List<Map<Object, Object>> dataList = sqlDao.list("diseaseNumStatistic.main", mapper);
		return toJson(dataList);
	}
	
	private JSONObject toJson(List<Map<Object, Object>> dataList) {
		JSONObject json = new JSONObject();
		
		JSONArray targetUnitArr = new JSONArray();
		JSONArray numArr = new JSONArray();
		for(int i = 0; i < dataList.size(); i++) {
			Map<Object, Object> map = dataList.get(i);
			String diseaseName = map.get("org_disease_name").toString();
			String num = map.get("num").toString();
			
			targetUnitArr.add(diseaseName);
			numArr.add(num);
		}
		json.put("xAxis_data", targetUnitArr);
		json.put("series_data", numArr);
		return json;
	}
}
