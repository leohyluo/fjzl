package com.zhys.fjzl.core.checker;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.zhys.core.domain.PageParam;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.ItemCheckResult;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.ReferralResDetail;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.domain.EbmDisease;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.enums.HospitalGrade;
import com.zhys.fjzl.enums.ReferralResult;
import com.zhys.fjzl.enums.ReferralRuleType;
import com.zhys.fjzl.service.DiseaseMapperService;
import com.zhys.fjzl.service.EbmDiseaseService;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

@Component
public class DiseaseChecker extends AbstractChecker {
	
	@Resource(name = "sysOrgService")
	private OrgService orgService;
	@Resource(name = "DiseaseService")
	private EbmDiseaseService ebmDiseaseService;
	@Resource(name = "DiseaseMapperService")
	private DiseaseMapperService diseaseMapperService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public ReferralRes check() throws Exception {
		DiseaseReq diseaseReq = getDiseaseReq();
		if(diseaseReq == null) {
			return noResult();
		}
		
		String orgId = diseaseReq.getOrgId();
		Org org = orgService.query(orgId);
		
		List<String> diseaseIdList = diseaseReq.getDiseaseIds();
		List<ItemCheckResult> checkList = new ArrayList<>();
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
			
			String ebmDiseaseId = hisDisease.getDisease_id();
			PageParam ebmParam = new PageParam();
			ebmParam.put("id", ebmDiseaseId);
			ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
			ebmParam.put("disease_status", EbmStatus.REVIEWED.getStatus());
			List<EbmDisease> diseaseList = ebmDiseaseService.list(ebmParam);
			EbmDisease disease = CollectionUtils.isNotEmpty(diseaseList) ? diseaseList.get(0) : null;
			if(disease == null) {
				continue;
			}
			//疾病等级名称如一级、二级、三级、一级医院...
			String diseaseGradeName = disease.getGrade();
			HospitalGrade hg = HospitalGrade.getByName(diseaseGradeName);
			if(hg == null) {
				continue;
			}
			//将一级转为数字1
			Long diseaseGrade = hg.getGrade();
			ReferralResult result = compare(ReferralRuleType.DISEASE, org, diseaseGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			//resultMap.put(result, new String[]{hisDisease.getDiseaseName(), String.valueOf(diseaseGrade)});
			String[] args = new String[]{hisDisease.getOrg_disease_name(), String.valueOf(diseaseGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, diseaseGrade);
			checkList.add(itemCheckResult);
		}

		ItemCheckResult checkResult = getHighestGrade(checkList);
		ReferralResult result = checkResult.getReferralResult();
		String[] args = checkResult.getArgs();
		
		List<Org> referralOrgList = new ArrayList<>();
		Long grade = Long.valueOf(org.getSo_grade());
		if(result == ReferralResult.DISEASE_GT) {
			Long maxGrade = Long.valueOf(args[1]);
			grade = maxGrade;
			referralOrgList = getReferralOrg(UP, maxGrade);
		} else if (result == ReferralResult.DISEASE_LT) {
			Long minGrade = Long.valueOf(args[1]);
			grade = minGrade;
			referralOrgList = getReferralOrg(DOWN, minGrade);
		}
		ReferralRes retVal = new ReferralRes(result, args, referralOrgList, grade);
		return retVal;
	}

	@Override
	@PostConstruct
	public void register() {
		ReferralRuleMapping.register(ReferralRuleType.DISEASE, this);
	}

	@Override
	public List<ReferralResDetail> check4Gmws() throws Exception {
		
		List<ReferralResDetail> detailList = new ArrayList<>();
		
		DiseaseReq diseaseReq = getDiseaseReq();
		if(diseaseReq == null) {
//			detailList.add(noReferralResDetail(ReferralRuleType.DISEASE));
			return detailList;
		}
		
		String orgId = diseaseReq.getOrgId();
		Org org = orgService.query(orgId);
		Org zndzSubOrg = getZndzSubOrg(); 
		
		List<String> diseaseIdList = diseaseReq.getDiseaseIds();
		List<ItemCheckResult> checkList = new ArrayList<>();
		Org actualOrg = null;
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
			
			String ebmDiseaseId = hisDisease.getDisease_id();
			if(StringUtil.isEmpty(ebmDiseaseId)) {
				continue;
			}
			PageParam ebmParam = new PageParam();
			ebmParam.put("id", ebmDiseaseId);
			ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
			ebmParam.put("disease_status", EbmStatus.REVIEWED.getStatus());
			List<EbmDisease> diseaseList = ebmDiseaseService.list(ebmParam);
			EbmDisease disease = CollectionUtils.isNotEmpty(diseaseList) ? diseaseList.get(0) : null;
			if(disease == null) {
				continue;
			}
			//疾病等级名称如一级、二级、三级、一级医院...
			String diseaseGradeName = disease.getGrade();
			HospitalGrade hg = HospitalGrade.getByName(diseaseGradeName);
			if(hg == null) {
				continue;
			}
			//将一级转为数字1
			Long diseaseGrade = hg.getGrade();
			actualOrg = zndzSubOrg == null ? org : zndzSubOrg;
			ReferralResult result = compare(ReferralRuleType.DISEASE, actualOrg, diseaseGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			//resultMap.put(result, new String[]{hisDisease.getDiseaseName(), String.valueOf(diseaseGrade)});
			String[] args = new String[]{hisDisease.getOrg_disease_name(), String.valueOf(diseaseGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, diseaseGrade);
			checkList.add(itemCheckResult);
			
			ReferralResDetail itemReferralDetail = new ReferralResDetail(ReferralRuleType.DISEASE, hisDisease.getOrg_disease_id(), hisDisease.getOrg_disease_name(), diseaseGrade, itemCheckResult.getReferralResult().getMessage());
			detailList.add(itemReferralDetail);
		}
		/*
		ItemCheckResult checkResult = getHighestGrade(checkList);
		ReferralResult result = checkResult.getReferralResult();
		String[] args = checkResult.getArgs();
		
		List<Org> referralOrgList = new ArrayList<>();
		Long grade = Long.valueOf(org.getSo_grade());
		if(result == ReferralResult.DISEASE_GT) {
			Long maxGrade = Long.valueOf(args[1]);
			grade = maxGrade;
			referralOrgList = getReferralOrg(UP, maxGrade);
		} else if (result == ReferralResult.DISEASE_LT) {
			Long minGrade = Long.valueOf(args[1]);
			grade = minGrade;
			referralOrgList = getReferralOrg(DOWN, minGrade);
		}
		ReferralRes retVal = new ReferralRes(result, args, referralOrgList, grade);
		return retVal;*/
		return removeNoNeedUpGradeItem(detailList);
	}

	@Override
	public List<ReferralResDetail> checkWithoutGmws() throws Exception {
		logger.info("disease check without gmws start");
		List<ReferralResDetail> detailList = new ArrayList<>();
		
		DiseaseReq diseaseReq = getDiseaseReq();
		if(diseaseReq == null) {
			logger.info("DiseaseReq is null");
			return detailList;
		}
		List<String> diseaseIdList = diseaseReq.getDiseaseIds();
		List<String> diseaseNameList = diseaseReq.getDiseaseNames();
		if(CollectionUtils.isEmpty(diseaseIdList) || CollectionUtils.isEmpty(diseaseNameList)) {
			logger.info("input diseaseIds or diseaseNames is null");
			return detailList;
		}
		if(diseaseIdList.size() != diseaseNameList.size()) {
			logger.info("input diseaseIds diseaseNames quantity is diffient");
			return detailList;
		}
		
		String orgId = diseaseReq.getOrgId();
		Org org = orgService.query(orgId);
		Org zndzSubOrg = getZndzSubOrg(); 
		
		List<ItemCheckResult> checkList = new ArrayList<>();
		Org actualOrg = null;
		for(int i = 0; i < diseaseIdList.size(); i++) {
			String diseaseId = diseaseIdList.get(i);
			String diseaseName = diseaseNameList.get(i);
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_disease_id", diseaseId);
			param.put("_enable", EnableStatus.ENABLE.getStatus());
			param.put("disease_status", HisDataStatus.REVIEWED.getStatus());
			List<DiseaseMapper> mapperList = diseaseMapperService.selfList(param);
			
			DiseaseMapper hisDisease = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			EbmDisease disease = null;
			//如疾病未对照则根据疾病名称去查找知识库的疾病
			if(hisDisease == null) {
				logger.info("disease " + diseaseId + " (" + diseaseName + ") not mapped in fjzl, now query by diseaseName " );
				PageParam ebmDiseaseParam = new PageParam();
				ebmDiseaseParam.put("disease_name_eq", diseaseName);
				ebmDiseaseParam.put("_enable", EnableStatus.ENABLE.getStatus());
				ebmDiseaseParam.put("disease_status", EbmStatus.REVIEWED.getStatus());
				List<EbmDisease> ebmDiseaseList = ebmDiseaseService.selfList(ebmDiseaseParam);
				disease = CollectionUtils.isNotEmpty(ebmDiseaseList) ? ebmDiseaseList.get(0) : null;				
			} else {
				logger.info("disease " + diseaseId + " (" + diseaseName + ") mapped in fjzl" );
				String ebmDiseaseId = hisDisease.getDisease_id();
				if(StringUtil.isEmpty(ebmDiseaseId)) {
					continue;
				}
				PageParam ebmParam = new PageParam();
				ebmParam.put("id", ebmDiseaseId);
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				ebmParam.put("disease_status", EbmStatus.REVIEWED.getStatus());
				List<EbmDisease> diseaseList = ebmDiseaseService.list(ebmParam);
				disease = CollectionUtils.isNotEmpty(diseaseList) ? diseaseList.get(0) : null;
			}
			if(disease == null) {
				logger.info("EbmDisease not found and ignore ");
				continue;
			}
			//疾病等级名称如一级、二级、三级、一级医院...
			String diseaseGradeName = disease.getGrade();
			HospitalGrade hg = HospitalGrade.getByName(diseaseGradeName);
			if(hg == null) {
				continue;
			}
			//将一级转为数字1
			Long diseaseGrade = hg.getGrade();
			actualOrg = org;
			ReferralResult result = compare(ReferralRuleType.DISEASE, actualOrg, diseaseGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = {diseaseName, String.valueOf(diseaseGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, diseaseGrade);
			checkList.add(itemCheckResult);
			
			ReferralResDetail itemReferralDetail = null;
			if(hisDisease != null) {
				itemReferralDetail = new ReferralResDetail(ReferralRuleType.DISEASE, hisDisease.getOrg_disease_id(), hisDisease.getOrg_disease_name(), diseaseGrade, itemCheckResult.getReferralResult().getMessage());
			} else {
				itemReferralDetail = new ReferralResDetail(ReferralRuleType.DISEASE, diseaseId, diseaseName, diseaseGrade, itemCheckResult.getReferralResult().getMessage());
			}
			detailList.add(itemReferralDetail);
		}
		List<ReferralResDetail> referralResDetailList = removeNoNeedUpGradeItem(detailList);
		logger.info("unmatch disease size = " + referralResDetailList.size());
		logger.info("disease check without gmws end");
		return referralResDetailList;
	}

	
}
