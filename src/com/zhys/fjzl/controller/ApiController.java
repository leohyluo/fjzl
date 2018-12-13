package com.zhys.fjzl.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.JsonUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.core.ReferralHandler;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.core.pojo.ReferralForm;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.response.CheckRes;
import com.zhys.fjzl.core.pojo.response.DiseaseRes;
import com.zhys.fjzl.core.pojo.response.DrugRes;
import com.zhys.fjzl.core.pojo.response.FjzlResult;
import com.zhys.fjzl.service.ApiRecordService;
import com.zhys.fjzl.service.ReferralDetailService;
import com.zhys.sys.domain.Org;

@Controller("ApiController")
@RequestMapping("/fjzl/api")
public class ApiController {
	
	@Resource
	private ReferralHandler referralHandler;
	private Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Resource(name = "ReferralDetailService")
	private ReferralDetailService referralDetailService;
	
	@Resource(name = "ApiRecordService")
	private ApiRecordService apiRecordService;
	
	@Value("http://192.168.29.54:8080/fjzl-manager/fjzl/referral_main.do")
	private String recordUrl;

	@ActionAnnotation(name = "转诊",group = "转诊")
	@RequestMapping(value="/referral", method = RequestMethod.POST)
	public ModelAndView referral(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		
		String orgId = request.getAttribute("orgId").toString();
		Object patientObj = request.getAttribute("patientInfo");
		Object doctorObj = request.getAttribute("doctorInfo");
		Object diseaseObj = request.getAttribute("diseaseInfo");
		Object drugObj = request.getAttribute("drugInfo");
		Object checkObj = request.getAttribute("checkInfo");
		
		PatientInfoReq patientInfo = null;
		DoctorInfoReq doctorInfo = null;
		DiseaseReq diseaseReq = null;
		DrugReq drugInfo = null;
		CheckReq checkInfo = null;
		
		if(patientObj != null) {
			patientInfo = (PatientInfoReq) patientObj;
		}
		if(doctorObj != null) {
			doctorInfo = (DoctorInfoReq) doctorObj;
		}
		if(diseaseObj != null) {
			diseaseReq = (DiseaseReq) diseaseObj;
		}
		if(drugObj != null) {
			drugInfo = (DrugReq) drugObj;
		}
		if(checkObj != null) {
			checkInfo = (CheckReq) checkObj;
		}
		try {
			ReferralRes res = referralHandler.handle(orgId, patientInfo, doctorInfo, diseaseReq, drugInfo, checkInfo);
			if(res != null) {
				String recordId = res.getRecordId();
				String url = "";
				if(StringUtil.isNotEmpty(recordId)) {
					url = this.recordUrl + "?id="+recordId;
				}			
				//String result = new ReferralResponseMessage(res, url).toJsonString();
				response.getWriter().write(url);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ActionAnnotation(name = "查询违规的疾病、药品、检查",group = "转诊")
	@RequestMapping(value="/getUnmatchItems", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getUnmatchItems(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		
		String orgId = request.getAttribute("orgId").toString();
		String zndz_sub_orgId = request.getAttribute("zndz_sub_orgId").toString(); //智能导诊子机构id
		Object patientObj = request.getAttribute("patientInfo");
		Object doctorObj = request.getAttribute("doctorInfo");
		Object diseaseObj = request.getAttribute("diseaseInfo");
		Object drugObj = request.getAttribute("drugInfo");
		Object checkObj = request.getAttribute("checkInfo");
		
		PatientInfoReq patientInfo = null;
		DoctorInfoReq doctorInfo = null;
		DiseaseReq diseaseReq = null;
		DrugReq drugInfo = null;
		CheckReq checkInfo = null;
		
		if(patientObj != null) {
			patientInfo = (PatientInfoReq) patientObj;
		}
		if(doctorObj != null) {
			doctorInfo = (DoctorInfoReq) doctorObj;
		}
		if(diseaseObj != null) {
			diseaseReq = (DiseaseReq) diseaseObj;
		}
		if(drugObj != null) {
			drugInfo = (DrugReq) drugObj;
		}
		if(checkObj != null) {
			checkInfo = (CheckReq) checkObj;
		}
		FjzlResult fr = null;
		try {
			fr = referralHandler.handle4Gmws(orgId, zndz_sub_orgId, patientInfo, doctorInfo, diseaseReq, drugInfo, checkInfo);
			List<DiseaseRes> list1 = fr.getDiseaseList();
			List<DrugRes> list2 = fr.getDrugList();
			List<CheckRes> list3 = fr.getCheckList();
			List<Org> orgList = fr.getOrgList();
			Org currentOrg = fr.getCurrentOrg();
			JSONObject json = new JSONObject();
			
			json.put("recordId", fr.getRecordId());
			if(CollectionUtils.isNotEmpty(list1)) {
				JSONArray jarr = new JSONArray();				
				for(DiseaseRes item : list1) {
					JSONObject itemJson = new JSONObject();
					itemJson.put("id", item.getId());
					itemJson.put("name", item.getName());
					itemJson.put("grade", item.getGrade());
					jarr.add(itemJson);
				}
				json.put("diseaseList", jarr);
			}
			if(CollectionUtils.isNotEmpty(list2)) {
				JSONArray jarr = new JSONArray();
				for(DrugRes item : list2) {
					JSONObject itemJson = new JSONObject();
					itemJson.put("id", item.getId());
					itemJson.put("name", item.getName());
					itemJson.put("grade", item.getGrade());
					jarr.add(itemJson);
				}
				json.put("drugList", jarr);
			}
			if(CollectionUtils.isNotEmpty(list3)) {
				JSONArray jarr = new JSONArray();
				for(CheckRes item : list3) {
					JSONObject itemJson = new JSONObject();
					itemJson.put("id", item.getId());
					itemJson.put("name", item.getName());
					itemJson.put("grade", item.getGrade());
					jarr.add(itemJson);
				}
				json.put("checkList", jarr);
			}
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
			if(currentOrg != null) {
				json.put("orgId", currentOrg.getSo_name());
				json.put("orgName", currentOrg.getSo_name());
			}
			logger.info("getUnmatchItems result is " + json.toString());
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(json.toString());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ActionAnnotation(name = "查询违规的疾病、药品、检查",group = "转诊")
	@RequestMapping(value="/getIllegalItemsForHis", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView getUnmatchItems4His(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		
		String orgId = request.getAttribute("orgId").toString();
		//String zndz_sub_orgId = request.getAttribute("zndz_sub_orgId").toString(); //智能导诊子机构id
		Object patientObj = request.getAttribute("patientInfo");
		Object doctorObj = request.getAttribute("doctorInfo");
		Object diseaseObj = request.getAttribute("diseaseInfo");
		Object drugObj = request.getAttribute("drugInfo");
		Object checkObj = request.getAttribute("checkInfo");
		
		PatientInfoReq patientInfo = null;
		DoctorInfoReq doctorInfo = null;
		DiseaseReq diseaseReq = null;
		DrugReq drugInfo = null;
		CheckReq checkInfo = null;
		
		if(patientObj != null) {
			patientInfo = (PatientInfoReq) patientObj;
		}
		if(doctorObj != null) {
			doctorInfo = (DoctorInfoReq) doctorObj;
		}
		if(diseaseObj != null) {
			diseaseReq = (DiseaseReq) diseaseObj;
		}
		if(drugObj != null) {
			drugInfo = (DrugReq) drugObj;
		}
		if(checkObj != null) {
			checkInfo = (CheckReq) checkObj;
		}
		FjzlResult fr = null;
		try {
			fr = referralHandler.handleWithoutGmws(orgId, patientInfo, doctorInfo, diseaseReq, drugInfo, checkInfo);
			JSONObject json = fr.toCommonExplain();
			logger.info("getUnmatchItems4His result is " + json.toString());
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(json.toString());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ActionAnnotation(name = "保存转诊结果",group = "保存转诊结果")
	@RequestMapping(value="/saveReferralResult", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveReferralResult(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject json = JsonUtils.parese(request);
			ReferralForm form = new ReferralForm(json);
			referralDetailService.save(form);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@ActionAnnotation(name = "将转诊记录标记为已转诊",group = "将转诊记录标记为已转诊")
	@RequestMapping(value="/updateReferralFlag", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateReferralFlag(HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject json = JsonUtils.parese(request);
			String recordId = json.getString("recordId");
			apiRecordService.updateRecordStatus(recordId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
