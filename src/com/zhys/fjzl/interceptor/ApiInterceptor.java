package com.zhys.fjzl.interceptor;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

public class ApiInterceptor extends HandlerInterceptorAdapter {
	
	private String mappingURL;
	private OrgService orgService;
	private static Logger logger = LoggerFactory.getLogger(ApiInterceptor.class);

	@SuppressWarnings("static-access")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if(uri.contains(mappingURL)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			JSONObject input = JSONObject.parseObject(in.readLine());
			
			String orgId = input.getString("orgId");
			String zndz_sub_orgId = input.getString("zndz_sub_orgId");//智能导诊子机构id
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
			JSONArray drugJson = input.getJSONArray("drugInfo");
			JSONObject checkJson = input.getJSONObject("checkInfo");
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
				diseaseReq = new DiseaseReq(orgId, ids, names);
			}
			if(drugJson != null) {
				drugReq = new DrugReq(orgId, drugJson);
			}
			if(checkJson != null) {
				String ids = checkJson.getString("ids");
				checkReq = new CheckReq(orgId, ids);
			}
			request.setAttribute("orgId", orgId);
			request.setAttribute("zndz_sub_orgId", StringUtil.isEmpty(zndz_sub_orgId) ? "" : zndz_sub_orgId);
			request.setAttribute("patientInfo", patientInfoReq);
			request.setAttribute("doctorInfo", doctorInfoReq);
			request.setAttribute("diseaseInfo", diseaseReq);
			request.setAttribute("drugInfo", drugReq);
			request.setAttribute("checkInfo", checkReq);
			
			System.out.println("ApiInterceptor param is :" + input.toJSONString());
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
}
