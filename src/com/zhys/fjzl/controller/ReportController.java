package com.zhys.fjzl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.zhys.core.controller.BaseController;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.BeanUtil;
import com.zhys.fjzl.service.report.DiseaseNumStatisticService;
import com.zhys.fjzl.service.report.DoctorReferralStatisticService;
import com.zhys.fjzl.service.report.IllItemStatisticService;
import com.zhys.fjzl.service.report.ReferralOrNotStatisticService;
import com.zhys.fjzl.service.report.ReferralRateService;
import com.zhys.fjzl.service.report.ReferralReasonService;
import com.zhys.fjzl.service.report.UnReferralDiseaseStatisticService;
import com.zhys.sys.domain.User;
import com.zhys.sys.service.OrgService;

@Controller("ReportController")
@RequestMapping("/fjzl/report_*.do")
public class ReportController extends BaseController {
	
	@Resource
	private ReferralRateService referralRateService;
	@Resource
	private DiseaseNumStatisticService diseaseNumStatisticService;
	@Resource
	private ReferralOrNotStatisticService referralOrNotStatisticService;
	@Resource
	private DoctorReferralStatisticService doctorReferralStatisticService;
	@Resource
	private UnReferralDiseaseStatisticService unReferralDiseaseStatisticService;
	@Resource
	private ReferralReasonService referralReasonService;
	@Resource
	private IllItemStatisticService illItemStatisticService;
	
	@Resource(name = "sysOrgService")
	private OrgService orgService;

	/**
	 * 转诊率统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView referralRateMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getReferralRateData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = referralRateService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 * 未转诊的疾病统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView unreferralDiseaseMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getUnreferralDiseaseMainData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = unReferralDiseaseStatisticService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 *  已转诊疾病数量统计
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView diseaseStatisticMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getdiseaseStatisticData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = diseaseNumStatisticService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 * 机构已转诊、未转诊疾病数量统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView referralOrNotStatisticMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getReferralOrNotStatisticData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = referralOrNotStatisticService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 * 医生转诊数量统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doctorReferralStatisticMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getdoctorReferralStatisticData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = doctorReferralStatisticService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 * 三目占比统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView referralReasonStatisticMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getReferralReasonStatisticData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = referralReasonService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 * 违规项(药品)数量统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView illItemStatisticMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getIllItemStatisticData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = illItemStatisticService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
	
	/**
	 * 违规项(检查)数量统计报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView illCheckStatisticMain(HttpServletRequest request, HttpServletResponse response) {
		return getView(request);
	}
	
	public ModelAndView getillCheckStatisticData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PageParam mapper = BeanUtil.wrapPageBean(request);
		User user = getLoginUser(request);
		JSONObject json = illItemStatisticService.getData(mapper, user);
		String data = json.toJSONString();
		return responseText(response, data);
	}
}
