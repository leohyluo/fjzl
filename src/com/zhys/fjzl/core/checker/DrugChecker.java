package com.zhys.fjzl.core.checker;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.ItemCheckResult;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.ReferralResDetail;
import com.zhys.fjzl.domain.BChdrug;
import com.zhys.fjzl.domain.BCpdrug;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.domain.WesternMedicine;
import com.zhys.fjzl.enums.DrugType;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.enums.HospitalGrade;
import com.zhys.fjzl.enums.ReferralResult;
import com.zhys.fjzl.enums.ReferralRuleType;
import com.zhys.fjzl.service.BChdrugService;
import com.zhys.fjzl.service.BCpdrugService;
import com.zhys.fjzl.service.DrugMapperService;
import com.zhys.fjzl.service.WesternMedicineService;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

@Component
public class DrugChecker extends AbstractChecker {
	@Resource(name = "sysOrgService")
	private OrgService orgService;
	@Resource(name = "WesternMedicineService")
	private WesternMedicineService xyService;
	@Resource(name = "BCpdrugService")
	private BCpdrugService zcyService;
	@Resource(name = "BChdrugService")
	private BChdrugService zyypService;
	@Resource(name = "DrugMapperService")
	private DrugMapperService mapperService;

	@Override
	public ReferralRes check() throws Exception {
		DrugReq drugReq = getDrugReq();
		if(drugReq == null) {
			return noResult();
		}
		
		String orgId = drugReq.getOrgId();
		Org org = orgService.query(orgId);
		
		JSONArray drugArr = drugReq.getDrugs();
		List<ItemCheckResult> checkList = new ArrayList<>();
		for(int i=0; i<drugArr.size(); i++) {
			JSONObject item = drugArr.getJSONObject(i);
			String hisDrugId = item.getString("id");
			Long type = item.getLong("type");
			
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_drug_code", hisDrugId);
			param.put("type", type);
			param.put("_enable", EnableStatus.ENABLE.getStatus());
			param.put("drug_status", HisDataStatus.REVIEWED.getStatus());
			
			List<DrugMapper> mapperList = mapperService.queryAll(param);
			DrugMapper hisDrug = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			if(hisDrug == null) {
				continue;
			}
			String ebmDrugCode = hisDrug.getDrug_id();	//此处对应知识库药品编码
			if(StringUtil.isEmpty(ebmDrugCode)) {
				continue;
			}
			Long drugGrade = 0L;
			
			PageParam ebmParam = null;
			if(type.longValue() == DrugType.XY.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("western_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<WesternMedicine> ebmDrugList = xyService.list(ebmParam);
				
				WesternMedicine ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} else if(type.longValue() == DrugType.ZCY.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("cpdrug_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<BCpdrug> ebmDrugList = zcyService.list(ebmParam);
				
				BCpdrug ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} else if(type.longValue() == DrugType.ZYYP.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("chdrug_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<BChdrug> ebmDrugList = zyypService.list(ebmParam);
				
				BChdrug ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} 
			ReferralResult result = compare(ReferralRuleType.DRUG, org, drugGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = {hisDrug.getOrg_drug_name(), String.valueOf(drugGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, drugGrade);
			checkList.add(itemCheckResult);
		}

		ItemCheckResult checkResult = getHighestGrade(checkList);
		ReferralResult result = checkResult.getReferralResult();
		String[] args = checkResult.getArgs();
		
		List<Org> referralOrgList = new ArrayList<>();
		Long grade = Long.valueOf(org.getSo_grade());
		if(result == ReferralResult.DRUG_GT) {
			Long maxGrade = Long.valueOf(args[1]);
			grade = maxGrade;
			referralOrgList = getReferralOrg(UP, maxGrade);
		} else if (result == ReferralResult.DRUG_LT) {
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
		ReferralRuleMapping.register(ReferralRuleType.DRUG, this);
	}

	@Override
	public List<ReferralResDetail> check4Gmws() throws Exception{
		
		List<ReferralResDetail> detailList = new ArrayList<>();
				
		DrugReq drugReq = getDrugReq();
		if(drugReq == null) {
//			detailList.add(noReferralResDetail(ReferralRuleType.DRUG));
			return detailList;
		}
		
		String orgId = drugReq.getOrgId();
		Org org = orgService.query(orgId);
		Org zndzSubOrg = getZndzSubOrg();
		
		JSONArray drugArr = drugReq.getDrugs();
		List<ItemCheckResult> checkList = new ArrayList<>();
		for(int i=0; i<drugArr.size(); i++) {
			JSONObject item = drugArr.getJSONObject(i);
			String hisDrugId = item.getString("id");
			Long type = item.getLong("type");
			
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_drug_code", hisDrugId);
			param.put("type", type);
			param.put("api_enable", EnableStatus.ENABLE.getStatus());
			param.put("drug_status", HisDataStatus.REVIEWED.getStatus());
			
			List<DrugMapper> mapperList = mapperService.queryAll(param);
			DrugMapper hisDrug = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			if(hisDrug == null) {
				continue;
			}
			String ebmDrugCode = hisDrug.getDrug_id();	//此处对应知识库药品编码
			if(StringUtil.isEmpty(ebmDrugCode)) {
				continue;
			}
			Long drugGrade = 0L;
			
			PageParam ebmParam = null;
			if(type.longValue() == DrugType.XY.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("western_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<WesternMedicine> ebmDrugList = xyService.list(ebmParam);
				
				WesternMedicine ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} else if(type.longValue() == DrugType.ZCY.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("cpdrug_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<BCpdrug> ebmDrugList = zcyService.list(ebmParam);
				
				BCpdrug ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} else if(type.longValue() == DrugType.ZYYP.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("chdrug_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<BChdrug> ebmDrugList = zyypService.list(ebmParam);
				
				BChdrug ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} 
			Org actualOrg = zndzSubOrg == null ? org : zndzSubOrg;
			ReferralResult result = compare(ReferralRuleType.DRUG, actualOrg, drugGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = {hisDrug.getOrg_drug_name(), String.valueOf(drugGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, drugGrade);
			checkList.add(itemCheckResult);
			
			ReferralResDetail referralDetail = new ReferralResDetail(ReferralRuleType.DRUG, hisDrug.getOrg_drug_code(), hisDrug.getOrg_drug_name(), drugGrade, itemCheckResult.getReferralResult().getMessage());
			detailList.add(referralDetail);
		}
		return removeNoNeedUpGradeItem(detailList);
	}

	@Override
	public List<ReferralResDetail> checkWithoutGmws() throws Exception {
		List<ReferralResDetail> detailList = new ArrayList<>();
		
		DrugReq drugReq = getDrugReq();
		if(drugReq == null) {
			return detailList;
		}
		
		String orgId = drugReq.getOrgId();
		Org org = orgService.query(orgId);
		
		JSONArray drugArr = drugReq.getDrugs();
		List<ItemCheckResult> checkList = new ArrayList<>();
		for(int i=0; i<drugArr.size(); i++) {
			JSONObject item = drugArr.getJSONObject(i);
			String hisDrugId = item.getString("id");
			Long type = item.getLong("type");
			
			PageParam param = new PageParam();
			param.put("org_id", GlobalConstant.GMWS_ORG_ID);
			param.put("org_drug_code", hisDrugId);
			param.put("type", type);
			param.put("api_enable", EnableStatus.ENABLE.getStatus());
			param.put("drug_status", HisDataStatus.REVIEWED.getStatus());
			
			List<DrugMapper> mapperList = mapperService.queryAll(param);
			DrugMapper hisDrug = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			if(hisDrug == null) {
				continue;
			}
			String ebmDrugCode = hisDrug.getDrug_id();	//此处对应知识库药品编码
			if(StringUtil.isEmpty(ebmDrugCode)) {
				continue;
			}
			Long drugGrade = 0L;
			
			PageParam ebmParam = null;
			if(type.longValue() == DrugType.XY.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("western_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<WesternMedicine> ebmDrugList = xyService.list(ebmParam);
				
				WesternMedicine ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} else if(type.longValue() == DrugType.ZCY.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("cpdrug_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<BCpdrug> ebmDrugList = zcyService.list(ebmParam);
				
				BCpdrug ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} else if(type.longValue() == DrugType.ZYYP.getType().longValue()) {
				ebmParam = new PageParam();
				ebmParam.put("drug_code", ebmDrugCode);
				ebmParam.put("chdrug_status", EbmStatus.REVIEWED.getStatus());
				ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
				List<BChdrug> ebmDrugList = zyypService.list(ebmParam);
				
				BChdrug ebmDrug = CollectionUtils.isNotEmpty(ebmDrugList) ? ebmDrugList.get(0) : null;
				if(ebmDrug == null) {
					continue;
				}
				String drugGradeName = ebmDrug.getGrade();
				HospitalGrade hg = HospitalGrade.getByName(drugGradeName);
				drugGrade = hg.getGrade();
			} 
			Org actualOrg = org;
			ReferralResult result = compare(ReferralRuleType.DRUG, actualOrg, drugGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = {hisDrug.getOrg_drug_name(), String.valueOf(drugGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, drugGrade);
			checkList.add(itemCheckResult);
			
			ReferralResDetail referralDetail = new ReferralResDetail(ReferralRuleType.DRUG, hisDrug.getOrg_drug_code(), hisDrug.getOrg_drug_name(), drugGrade, itemCheckResult.getReferralResult().getMessage());
			detailList.add(referralDetail);
		}
		return removeNoNeedUpGradeItem(detailList);
	}

}
