package com.zhys.fjzl.interceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.CollectionUtils;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.domain.GmwsDiseaseDetail;
import com.zhys.fjzl.domain.GmwsDiseaseDrug;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.DiseaseMapperService;
import com.zhys.fjzl.service.GmwsDiseaseDetailService;
import com.zhys.fjzl.service.GmwsDiseaseDrugService;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

public class UnmatchItem4HisInterceptor extends HandlerInterceptorAdapter {
	
	private String mappingURL;
	private OrgService orgService;
	private DiseaseMapperService diseaseMapperService;
	private GmwsDiseaseDetailService gmwsDiseaseDetailService;
	private GmwsDiseaseDrugService gmwsDiseaseDrugService;
	private static Logger logger = LoggerFactory.getLogger(UnmatchItem4HisInterceptor.class);

	@SuppressWarnings("static-access")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if(uri.contains(mappingURL)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			JSONObject input = JSONObject.parseObject(in.readLine());
			
			String orgId = input.getString("orgId");
			Org org = orgService.query(orgId);
			if(org == null) {
				logger.error("org was not found by id " + orgId);
				return false;
			}
			
			PatientInfoReq patientInfoReq = null;
			DoctorInfoReq doctorInfoReq = null;
			DiseaseReq diseaseReq = null;
			DrugReq drugReq = null;
			CheckReq checkReq = null;
			
			JSONObject patientJson = input.getJSONObject("patientInfo");
			JSONObject doctorJson = input.getJSONObject("doctorInfo");
			JSONObject diseaseJson = input.getJSONObject("diseaseInfo");
			if(patientJson != null) {
				String name = patientJson.getString("name");
				//int age = patientJson.getIntValue("age");
				int gender = patientJson.getIntValue("gender");
				String birth = patientJson.getString("birth");
				
				patientInfoReq = new PatientInfoReq(orgId, name, birth, gender);
			}
			if(doctorJson != null) {
				String doctorCode = doctorJson.getString("doctorCode");
				String doctorName = doctorJson.getString("doctorName");
				String mobile = doctorJson.getString("mobile");
				String department = doctorJson.getString("department");
				String doctorGrade = doctorJson.getString("doctorGrade");
				doctorInfoReq = new DoctorInfoReq(doctorCode, doctorName, mobile, department, doctorGrade);
			}
			if(diseaseJson != null) {
				String ids = diseaseJson.getString("ids");
				String names = diseaseJson.getString("names");
				//机构疾病id -> 分级诊疗知识库疾病id -> 智能导诊疾病id
				String[] idArr = ids.split(",");
				String[] nameArr = names.split(",");
				String ord_disease_id = idArr[0];
				String org_disease_name = nameArr[0];
				
				PageParam param = new PageParam();
				param.put("org_id", orgId);
				param.put("org_disease_id", ord_disease_id);
				param.put("_enable", EnableStatus.ENABLE.getStatus());
				param.put("disease_status", HisDataStatus.REVIEWED.getStatus());
				List<DiseaseMapper> mapperList = diseaseMapperService.selfList(param);
				//如疾病id未找到对照关系则根据疾病名称查找
				if(CollectionUtils.isEmpty(mapperList)) {
					param = new PageParam();
					param.put("org_id", GlobalConstant.GMWS_ORG_ID);
					param.put("org_disease_name_eq", org_disease_name);
					param.put("_enable", EnableStatus.ENABLE.getStatus());
					param.put("disease_status", HisDataStatus.REVIEWED.getStatus());
					mapperList = diseaseMapperService.selfList(param);
				}
				if(CollectionUtils.isNotEmpty(mapperList)) {
					DiseaseMapper dm = mapperList.get(0);
					String fjzlDiseaseId = dm.getDisease_id();
					//分级诊疗知识库疾病id -> 智能导诊疾病id
					param = new PageParam();
					param.put("org_id", GlobalConstant.GMWS_ORG_ID);
					param.put("disease_id", fjzlDiseaseId);
					param.put("_enable", EnableStatus.ENABLE.getStatus());
					param.put("disease_status", HisDataStatus.REVIEWED.getStatus());
					List<DiseaseMapper> gmwsMapperList = diseaseMapperService.selfList(param);
					//如果根据机构疾病id未找对照关系，则根据疾病名称查找对照关系
					if(CollectionUtils.isEmpty(gmwsMapperList)) {
						param = new PageParam();
						param.put("org_id", GlobalConstant.GMWS_ORG_ID);
						param.put("org_disease_name_eq", org_disease_name);
						param.put("_enable", EnableStatus.ENABLE.getStatus());
						param.put("disease_status", HisDataStatus.REVIEWED.getStatus());
						gmwsMapperList = diseaseMapperService.selfList(param);
					}
					if(CollectionUtils.isNotEmpty(gmwsMapperList)) {
						dm = gmwsMapperList.get(0);
						//智能导诊疾病id
						String gmwsDiseaseId = dm.getOrg_disease_id();
						PageParam gmwsParam = new PageParam();
						gmwsParam.put("dis_id_", gmwsDiseaseId);
						//根据智能诊断疾病id查找检查
						List<GmwsDiseaseDetail> gmwsDiseaseDetailList = gmwsDiseaseDetailService.list(gmwsParam);
						
						if(CollectionUtils.isNotEmpty(gmwsDiseaseDetailList)) {
							checkReq = buildCheckReq(orgId, gmwsDiseaseDetailList);
						}
						//根据智能诊断疾病id查找药品
						gmwsParam = new PageParam();
						gmwsParam.put("gmws_disease_id", gmwsDiseaseId);
						List<GmwsDiseaseDrug> gmwsDrugList = gmwsDiseaseDrugService.list(gmwsParam);
						if(CollectionUtils.isNotEmpty(gmwsDrugList)) {
							drugReq = buildDrugReq(orgId, gmwsDrugList);
						}
					}
				}
				
				diseaseReq = new DiseaseReq(orgId, ids, names);
			}
			request.setAttribute("orgId", orgId);
			//request.setAttribute("zndz_sub_orgId", StringUtil.isEmpty(zndz_sub_orgId) ? "" : zndz_sub_orgId);
			request.setAttribute("patientInfo", patientInfoReq);
			request.setAttribute("doctorInfo", doctorInfoReq);
			request.setAttribute("diseaseInfo", diseaseReq);
			request.setAttribute("drugInfo", drugReq);
			request.setAttribute("checkInfo", checkReq);
			
			System.out.println("UnmatchItem4HisInterceptor param is :" + input.toJSONString());
			return true;
		}
		
		return true;
	}

	public void setMappingURL(String mappingURL) {
		this.mappingURL = mappingURL;
	}
	
	public void setOrgService(OrgService orgService) {
		this.orgService = orgService;
	}

	public void setDiseaseMapperService(DiseaseMapperService diseaseMapperService) {
		this.diseaseMapperService = diseaseMapperService;
	}

	public void setGmwsDiseaseDetailService(GmwsDiseaseDetailService gmwsDiseaseDetailService) {
		this.gmwsDiseaseDetailService = gmwsDiseaseDetailService;
	}

	public void setGmwsDiseaseDrugService(GmwsDiseaseDrugService gmwsDiseaseDrugService) {
		this.gmwsDiseaseDrugService = gmwsDiseaseDrugService;
	}
	
	private CheckReq buildCheckReq(String orgId, List<GmwsDiseaseDetail> gmwsDiseaseDetailList) {
		String checkIds = "";
		for(GmwsDiseaseDetail item : gmwsDiseaseDetailList) {
			checkIds += item.getId_() + ",";
		}
		checkIds = checkIds.length() > 0 ? checkIds.substring(0, checkIds.length() - 1) : checkIds;
		CheckReq checkReq = new CheckReq(orgId, checkIds);
		return checkReq;
	}
	
	private DrugReq buildDrugReq(String orgId, List<GmwsDiseaseDrug> gmwsDiseaseDrugList) {
		JSONArray drugArr = new JSONArray();
		for(GmwsDiseaseDrug item : gmwsDiseaseDrugList) {
			JSONObject itemJson = new JSONObject();
			itemJson.put("id", item.getGmws_drug_code());
			itemJson.put("type", "1");
			drugArr.add(itemJson);
		}
		DrugReq drugReq = new DrugReq(orgId, drugArr);
		return drugReq;
	}
}
