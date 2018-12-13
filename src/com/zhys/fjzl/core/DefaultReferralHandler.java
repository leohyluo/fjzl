package com.zhys.fjzl.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.constant.DataPool;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.core.checker.ReferralRule;
import com.zhys.fjzl.core.checker.ReferralRuleMapping;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.ReferralResDetail;
import com.zhys.fjzl.core.pojo.response.CheckRes;
import com.zhys.fjzl.core.pojo.response.DiseaseRes;
import com.zhys.fjzl.core.pojo.response.DrugRes;
import com.zhys.fjzl.core.pojo.response.FjzlResult;
import com.zhys.fjzl.domain.ApiRecord;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.domain.Options;
import com.zhys.fjzl.domain.Record;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.enums.HospitalGradeView;
import com.zhys.fjzl.enums.ReferralResult;
import com.zhys.fjzl.enums.ReferralRuleType;
import com.zhys.fjzl.service.ApiRecordService;
import com.zhys.fjzl.service.DiseaseMapperService;
import com.zhys.fjzl.service.OptionsService;
import com.zhys.fjzl.service.RecordService;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

@Component
public class DefaultReferralHandler implements ReferralHandler {
	
	@Resource(name = "OptionsService")
	private OptionsService optionsService;
	@Resource(name = "RecordService")
	private RecordService recordService;
	@Resource(name = "DiseaseMapperService")
	private DiseaseMapperService diseaseMapperService;
	@Resource(name = "sysOrgService")
	private OrgService orgService;
	@Resource(name = "ApiRecordService")
	private ApiRecordService apiRecordService;
	
	private Logger logger = LoggerFactory.getLogger(DefaultReferralHandler.class);
	
	private static final Integer UP = 1;
	private static final Integer DOWN = 0;
	
	@Override
	public ReferralRes handle(String orgId, PatientInfoReq patientInfoReq, DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception {
		
		PageParam param = new PageParam();
		param.put("org_id", orgId);
		List<Options> optionList = optionsService.list(param);
		if(CollectionUtils.isEmpty(optionList)) {
			logger.warn("no referral rules for org ", orgId);
			return null;
		}
		
		List<ReferralRes> resultList = new ArrayList<>();
		for(Options option : optionList) {
			String code = option.getRuleCode();
			if(StringUtil.isEmpty(code)) {
				continue;
			}
			Integer ruleCode = Integer.valueOf(code);
			ReferralRuleType type = ReferralRuleType.findByType(ruleCode);
			ReferralRule checker = ReferralRuleMapping.get(type);
			checker.load(orgId, null, patientInfoReq, doctorInfoReq, diseaseReq, drugReq, checkReq);
			ReferralRes res = checker.check();
			res.setType(type);
			/*if(res.getGrade().longValue() == GlobalConstant.GRADE_3TH.longValue()) {
				return res;
			}*/
			resultList.add(res);
		}
		Org org = orgService.query(orgId);
		ReferralRes highestResult = getHighestReferralResult(resultList);
		if(Long.valueOf(org.getSo_grade()).longValue() != highestResult.getGrade().longValue()) {
			String recordId = saveReferralRecord(highestResult, orgId, patientInfoReq, doctorInfoReq, diseaseReq, drugReq, checkReq);
			highestResult.setRecordId(recordId);
		}
		
		return highestResult;
	}

	@Override
	public FjzlResult handle4Gmws(String orgId, String zndzSubOrgId, PatientInfoReq patientInfoReq, 
			DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception {
		
		String hisId = StringUtil.isEmpty(zndzSubOrgId) ? orgId : zndzSubOrgId;
		ApiRecord apiRecord = new ApiRecord(hisId, patientInfoReq, doctorInfoReq, diseaseReq, drugReq, checkReq);
		
		
		//查出智能诊断子机构(如天方达)的信息
		if(StringUtil.isNotEmpty(zndzSubOrgId)) {
			Org zndzSubOrg = orgService.query(zndzSubOrgId);
			DataPool.putOrg(zndzSubOrgId, zndzSubOrg);
		}
		
		PageParam param = new PageParam();
		param.put("org_id", orgId);
		List<Options> optionList = optionsService.list(param);
		if(CollectionUtils.isEmpty(optionList)) {
			logger.warn("no referral rules for org ", orgId);
			return null;
		}
		Org currentOrg = orgService.query(orgId);
		
		List<ReferralResDetail> allList = new ArrayList<>();
		for(Options option : optionList) {
			String code = option.getRuleCode();
			if(StringUtil.isEmpty(code)) {
				continue;
			}
			Integer ruleCode = Integer.valueOf(code);
			ReferralRuleType type = ReferralRuleType.findByType(ruleCode);
			ReferralRule checker = ReferralRuleMapping.get(type);
			checker.load(orgId, zndzSubOrgId, patientInfoReq, doctorInfoReq, diseaseReq, drugReq, checkReq);
			List<ReferralResDetail> itemDetailList = checker.check4Gmws();
			allList.addAll(itemDetailList);
			//当疾病不需要转诊时药品与检查则不进行检测
			/*if(type == ReferralRuleType.DISEASE && CollectionUtils.isEmpty(itemDetailList)) {
				break;
			}*/
		}
		
		List<DiseaseRes> diseaseResList = new ArrayList<>();
		List<DrugRes> drugResList = new ArrayList<>();
		List<CheckRes> checkResList = new ArrayList<>();
		for(ReferralResDetail item : allList) {
			if(item.getType() == ReferralRuleType.DISEASE) {
				DiseaseRes diseaseRes = new DiseaseRes(item.getId(), item.getName(), item.getGrade().toString());
				diseaseResList.add(diseaseRes);
			} else if (item.getType() == ReferralRuleType.DRUG) {
				DrugRes drugRes = new DrugRes(item.getId(), item.getName(), item.getGrade().toString());
				drugResList.add(drugRes);
			} else if (item.getType() == ReferralRuleType.BODYCHECK) {
				CheckRes checkRes = new CheckRes(item.getId(), item.getName(), item.getGrade().toString());
				checkResList.add(checkRes);
			}
		}
		
		String actualOrgId = StringUtil.isEmpty(zndzSubOrgId) ? orgId : zndzSubOrgId;
		FjzlResult fr = new FjzlResult();
		fr.setDiseaseList(diseaseResList);
		fr.setDrugList(drugResList);
		fr.setCheckList(checkResList);
		fr.setCurrentOrg(currentOrg);
		List<Org> orgList = getReferralOrg(actualOrgId, fr);
		fr.setOrgList(orgList);

		String recordId = apiRecordService.saveFjzlRecord(apiRecord, fr);
		fr.setRecordId(recordId);
		return fr;
	}
	
	@Override
	public FjzlResult handleWithoutGmws(String orgId, PatientInfoReq patientInfoReq, 
			DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception {
		
		logger.info("defaultReferralHandler handleWithoutGmws start");
		
		String hisId = orgId;
		ApiRecord apiRecord = new ApiRecord(hisId, patientInfoReq, doctorInfoReq, diseaseReq, drugReq, checkReq);
		
		PageParam param = new PageParam();
		param.put("org_id", orgId);
		List<Options> optionList = optionsService.list(param);
		logger.info("org " + orgId + " has " + optionList.size() + " referral options");
		if(CollectionUtils.isEmpty(optionList)) {
			logger.warn("no referral rules for org ", orgId);
			return null;
		}
		Org currentOrg = orgService.query(orgId);
		
		List<ReferralResDetail> allList = new ArrayList<>();
		String zndzSubOrgId = null;
		for(Options option : optionList) {
			String code = option.getRuleCode();
			if(StringUtil.isEmpty(code)) {
				continue;
			}
			Integer ruleCode = Integer.valueOf(code);
			ReferralRuleType type = ReferralRuleType.findByType(ruleCode);
			ReferralRule checker = ReferralRuleMapping.get(type);
			
			checker.load(orgId, zndzSubOrgId, patientInfoReq, doctorInfoReq, diseaseReq, drugReq, checkReq);
			List<ReferralResDetail> itemDetailList = checker.checkWithoutGmws();
			allList.addAll(itemDetailList);
			//当疾病不需要转诊时药品与检查则不进行检测
			/*if(type == ReferralRuleType.DISEASE && CollectionUtils.isEmpty(itemDetailList)) {
					break;
			}*/
		}
		
		List<DiseaseRes> diseaseResList = new ArrayList<>();
		List<DrugRes> drugResList = new ArrayList<>();
		List<CheckRes> checkResList = new ArrayList<>();
		for(ReferralResDetail item : allList) {
			if(item.getType() == ReferralRuleType.DISEASE) {
				DiseaseRes diseaseRes = new DiseaseRes(item.getId(), item.getName(), item.getGrade().toString());
				diseaseResList.add(diseaseRes);
			} else if (item.getType() == ReferralRuleType.DRUG) {
				DrugRes drugRes = new DrugRes(item.getId(), item.getName(), item.getGrade().toString());
				drugResList.add(drugRes);
			} else if (item.getType() == ReferralRuleType.BODYCHECK) {
				CheckRes checkRes = new CheckRes(item.getId(), item.getName(), item.getGrade().toString());
				checkResList.add(checkRes);
			}
		}
		
		String actualOrgId = StringUtil.isEmpty(zndzSubOrgId) ? orgId : zndzSubOrgId;
		FjzlResult fr = new FjzlResult();
		fr.setDiseaseList(diseaseResList);
		fr.setDrugList(drugResList);
		fr.setCheckList(checkResList);
		fr.setCurrentOrg(currentOrg);
		List<Org> orgList = getReferralOrg(actualOrgId, fr);
		fr.setOrgList(orgList);
		if(CollectionUtils.isNotEmpty(orgList)) {
			logger.info("orgList size = " + orgList.size());
		}

		String recordId = apiRecordService.saveFjzlRecord(apiRecord, fr);
		fr.setRecordId(recordId);
		logger.info("defaultReferralHandler handleWithoutGmws end");
		return fr;
	}
	
	private List<Org> getReferralOrg(String orgId, FjzlResult fr) {
		try {
			logger.info("getReferralOrg by orgId " + orgId + " start");
			List<DiseaseRes> diseaseResList = fr.getDiseaseList();
			List<DrugRes> drugResList = fr.getDrugList();
			List<CheckRes> checkResList = fr.getCheckList();
			if(CollectionUtils.isEmpty(diseaseResList) && CollectionUtils.isEmpty(drugResList) && CollectionUtils.isEmpty(checkResList)) {
				return null;
			}
			Long maxGrade = 1L;
			for(DiseaseRes item : diseaseResList) {
				String itemGrade = HospitalGradeView.getByGradeName(item.getGrade()).getGradeVal();
				if(Long.valueOf(itemGrade).longValue() > maxGrade.longValue()) {
					maxGrade = Long.valueOf(itemGrade);
					if(maxGrade.longValue() == 3) {
						break;
					}
				}
			}
			if(maxGrade.longValue() != 3) {
				for(DrugRes item : drugResList) {
					String itemGrade = HospitalGradeView.getByGradeName(item.getGrade()).getGradeVal();
					if(Long.valueOf(itemGrade).longValue() > maxGrade.longValue()) {
						maxGrade = Long.valueOf(itemGrade);
						if(maxGrade.longValue() == 3) {
							break;
						}
					}
				}
			}
			if(maxGrade.longValue() != 3) {
				for(CheckRes item : checkResList) {
					String itemGrade = HospitalGradeView.getByGradeName(item.getGrade()).getGradeVal();
					if(Long.valueOf(itemGrade).longValue() > maxGrade.longValue()) {
						maxGrade = Long.valueOf(itemGrade);
						if(maxGrade.longValue() == 3) {
							break;
						}
					}
				}
			}
			logger.info("getReferralOrg by orgId " + orgId + " end");
			return getReferralOrg(orgId, UP, maxGrade);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 转诊至医联体内targetGrade级机构（上级机构排第一）
	 * @param flag 1:上转 0:下转
	 * @param targetGrade 接诊的医院等级
	 * @return
	 * @throws Exception
	 */
	private List<Org> getReferralOrg(String orgId, Integer flag, Long targetGrade) throws Exception {
		Org org = orgService.query(orgId);
		String patentId = org.getSo_parentid();
		List<Org> retVal = new ArrayList<>();
		
		if(flag == UP) {
			Org parentOrg = orgService.query(patentId);
			//如果上级机构不是医联体
			if(!"1".equals(parentOrg.getSo_type())) {
				//如父机构等级与targetGrade相同,则将上级机构排在转诊列表的第一位
				Long parentGrade = Long.valueOf(parentOrg.getSo_grade());
				if(parentGrade == targetGrade) {
					retVal.add(parentOrg);
				} 
			}
			//找出医联体内所有机构等级与targetGrade相同的机构
			List<Org> orgList = getOrgListFromMedicalBody(orgId);
			for (Org item : orgList) {
				//医联体不处理
				if(item.getSo_type().equals("1")) {
					continue;
				}
				if(parentOrg != null && item.getSo_id().equals(parentOrg.getSo_id())) {
					continue;
				}
				if(StringUtil.isEmpty(item.getSo_grade())) {
					logger.error("org " + item.getSo_name() + "'s grade was null");
					continue;
				}
				if (Long.valueOf(item.getSo_grade()).longValue() == targetGrade) {
					retVal.add(item);
				}
			}
		} else if (flag == DOWN){
			String so_code2 = org.getSo_code2();
			Org param = new Org();
			param.setSo_code2(so_code2);
			List<Org> childList = orgService.list(param);
			//先找子节点下的所有机构
			Set<String> childSet = new HashSet<>();
			if(CollectionUtils.isNotEmpty(childList)) {
				for(Org item : childList) {
					if(Long.valueOf(item.getSo_grade()).longValue() == targetGrade) {
						retVal.add(item);
						childSet.add(item.getSo_id());
					}
				}
			}
			//找出医联体内所有机构等级与targetGrade相同的机构
			List<Org> orgList = getOrgListFromMedicalBody(orgId);
			for(Org item : orgList) {
				//医联体不处理
				if(item.getSo_type().equals("1")) {
					continue;
				}
				if(childSet.contains(item.getSo_id())) {
					continue;
				}
				if(Long.valueOf(item.getSo_grade()).longValue() == targetGrade) {
					retVal.add(item);
				}
			}
		}
		return retVal;
	}
	
	// 获取医联体的所有机构
	private List<Org> getOrgListFromMedicalBody(String orgId) throws Exception {
		Org rootOrg = getRootOrg(orgId);
		String so_code2 = rootOrg.getSo_code2();
		Org param = new Org();
		param.setSo_code2(so_code2);
		return orgService.list(param);
	}
	
	// 查询所在医联体
	private Org getRootOrg(String orgId) throws Exception {
		Org org = orgService.query(orgId);
		if (org.getSo_parentid().equals(GlobalConstant.XZYX_ORG_ID)) {
			return org;
		} else {
			return getRootOrg(org.getSo_parentid());
		}
	}
	
	private String saveReferralRecord(ReferralRes res, String orgId, PatientInfoReq patientInfo, DoctorInfoReq doctorInfo, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception {
		List<Org> orgList = res.getReferralOrgList();
		JSONArray jarr = new JSONArray();
		
		Org curOrg = orgService.query(orgId);
		if(curOrg == null) {
			logger.warn("orginazation not found by orgId " + orgId);
			return null;
		}
		for(Org org : orgList) {
			JSONObject item = new JSONObject();
			item.put("orgId", org.getSo_id());
			item.put("orgName", org.getSo_name());
			jarr.add(item);
		}
		String diseaseName = "";
		if(diseaseReq != null) {
			List<String> diseaseIdList = diseaseReq.getDiseaseIds();
			for(String diseaseId : diseaseIdList) {
				PageParam param = new PageParam();
				param.put("org_id", orgId);
				param.put("org_disease_id", diseaseId);
				param.put("_enable", EnableStatus.ENABLE.getStatus());
				param.put("disease_status", HisDataStatus.REVIEWED.getStatus());
				List<DiseaseMapper> mapperList = diseaseMapperService.selfList(param);
				DiseaseMapper hisDisease = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
				if(hisDisease == null) {
					continue;
				}
				diseaseName += hisDisease.getOrg_disease_name() + ",";
			}
			diseaseName = StringUtil.removeLastChar(diseaseName);
		}
		
		Record record = new Record();
		record.setOrg_id(orgId);
		record.setTarget_unit(jarr.toJSONString());
		record.setTarget_name(patientInfo.getName());
		record.setTarget_gender(patientInfo.getGender().toString());
		record.setTarget_age(patientInfo.getBirth());
		record.setDiagnosis(diseaseName);
		record.setReason(res.getMessage());
		record.setTarget_doctor(doctorInfo.getDoctorName());
		record.setTarget_org_name(curOrg.getSo_name());
		record.setTarget_mobile(doctorInfo.getMobile());
		String id = recordService.create(record);
		return id;
	}
		
	private ReferralRes getHighestReferralResult(List<ReferralRes> resultList) {
		Collections.sort(resultList, new Comparator<ReferralRes>() {
			@Override
			public int compare(ReferralRes o1, ReferralRes o2) {
				return o2.getGrade().compareTo(o1.getGrade());
			}
		});
		return resultList.get(0);
	}
	
	public static void main(String[] args) throws Exception {
		DefaultReferralHandler thiz = new DefaultReferralHandler();
		List<ReferralRes> resultList = new ArrayList<>();
		resultList.add(new ReferralRes(ReferralResult.CHECK_GT, new String[]{"a","1"}, null, 3L));
		resultList.add(new ReferralRes(ReferralResult.CHECK_GT, new String[]{"a","1"}, null, 1L));
		resultList.add(new ReferralRes(ReferralResult.CHECK_GT, new String[]{"a","1"}, null, 2L));
		ReferralRes res = thiz.getHighestReferralResult(resultList);
		System.out.println(res.getGrade());
	}

	
}
