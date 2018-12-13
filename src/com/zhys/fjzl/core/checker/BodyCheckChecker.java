package com.zhys.fjzl.core.checker;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zhys.core.domain.PageParam;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.ItemCheckResult;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.ReferralResDetail;
import com.zhys.fjzl.domain.BodyCheck;
import com.zhys.fjzl.domain.CheckMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.enums.HospitalGrade;
import com.zhys.fjzl.enums.ReferralResult;
import com.zhys.fjzl.enums.ReferralRuleType;
import com.zhys.fjzl.service.BodyCheckService;
import com.zhys.fjzl.service.CheckMapperService;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

@Component
public class BodyCheckChecker extends AbstractChecker  {

	@Resource(name = "sysOrgService")
	private OrgService orgService;
	@Resource(name = "CheckMapperService")
	private CheckMapperService mapperService;
	@Resource(name = "BodyCheckService")
	private BodyCheckService checkService;
	
	@Override
	public ReferralRes check() throws Exception {
		CheckReq checkReq = getCheckReq();
		if(checkReq == null) {
			return noResult();
		}
		
		String orgId = checkReq.getOrgId();
		Org org = orgService.query(orgId);
		//Map<ReferralResult, String[]> resultMap = new HashMap<>();
		
		List<String> checkIdList = checkReq.getCheckIds();
		List<ItemCheckResult> checkList = new ArrayList<>();
		for(String checkId : checkIdList) {
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_check_id", checkId);
			param.put("_enable", EnableStatus.ENABLE.getStatus());
			param.put("body_check_status", HisDataStatus.REVIEWED.getStatus());
			List<CheckMapper> mapperList = mapperService.list(param);
			CheckMapper hisCheck = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			if(hisCheck == null) {
				continue;
			}
			String ebmCheckId = hisCheck.getCheck_id();
			if(StringUtil.isEmpty(ebmCheckId)) {
				continue;
			}
			PageParam ebmParam = new PageParam();
			ebmParam.put("id", ebmCheckId);
			ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
			ebmParam.put("body_check_status", EbmStatus.REVIEWED.getStatus());
			
			List<BodyCheck> ebmCheckList = checkService.list(ebmParam);
			BodyCheck ebmCheck = CollectionUtils.isNotEmpty(ebmCheckList) ? ebmCheckList.get(0) : null;
			if(ebmCheck == null) {
				continue;
			}
			//疾病等级名称如一级、二级、三级、一级医院...
			String checkGradeName = ebmCheck.getGrade();
			HospitalGrade hg = HospitalGrade.getByName(checkGradeName);
			if(hg == null) {
				continue;
			}
			//将一级转为数字1
			Long checkGrade = hg.getGrade();
			//Long checkGrade = Long.valueOf(ebmCheck.getGrade());
			ReferralResult result = compare(ReferralRuleType.BODYCHECK, org, checkGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = new String[]{hisCheck.getCheckName(), String.valueOf(checkGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, checkGrade);
			checkList.add(itemCheckResult);
		}

		ItemCheckResult checkResult = getHighestGrade(checkList);
		ReferralResult result = checkResult.getReferralResult();
		String[] args = checkResult.getArgs();
		
		List<Org> referralOrgList = new ArrayList<>();
		Long grade = Long.valueOf(org.getSo_grade());
		if(result == ReferralResult.CHECK_GT) {
			Long maxGrade = Long.valueOf(args[1]);
			grade = maxGrade;
			referralOrgList = getReferralOrg(UP, maxGrade);
		} else if (result == ReferralResult.CHECK_LT) {
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
		// TODO Auto-generated method stub
		ReferralRuleMapping.register(ReferralRuleType.BODYCHECK, this);
	}

	@Override
	public List<ReferralResDetail> check4Gmws() throws Exception {
		
		List<ReferralResDetail> detailList = new ArrayList<>();
		CheckReq checkReq = getCheckReq();
		if(checkReq == null) {
//			detailList.add(noReferralResDetail(ReferralRuleType.BODYCHECK));
			return detailList;
		}
		
		String orgId = checkReq.getOrgId();
		Org org = orgService.query(orgId);
		Org zndzSubOrg = getZndzSubOrg();
		//Map<ReferralResult, String[]> resultMap = new HashMap<>();
		
		List<String> checkIdList = checkReq.getCheckIds();
		List<ItemCheckResult> checkList = new ArrayList<>();
		for(String checkId : checkIdList) {
			PageParam param = new PageParam();
			param.put("org_id", orgId);
			param.put("org_check_id", checkId);
			param.put("_enable", EnableStatus.ENABLE.getStatus());
			param.put("body_check_status", HisDataStatus.REVIEWED.getStatus());
			List<CheckMapper> mapperList = mapperService.list(param);
			CheckMapper hisCheck = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			if(hisCheck == null) {
				continue;
			}
			String ebmCheckId = hisCheck.getCheck_id();
			if(StringUtil.isEmpty(ebmCheckId)) {
				continue;
			}
			PageParam ebmParam = new PageParam();
			ebmParam.put("id", ebmCheckId);
			ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
			ebmParam.put("body_check_status", EbmStatus.REVIEWED.getStatus());
			
			List<BodyCheck> ebmCheckList = checkService.list(ebmParam);
			BodyCheck ebmCheck = CollectionUtils.isNotEmpty(ebmCheckList) ? ebmCheckList.get(0) : null;
			if(ebmCheck == null) {
				continue;
			}
			String diseaseGradeName = ebmCheck.getGrade();
			HospitalGrade hg = HospitalGrade.getByName(diseaseGradeName);
			if(hg == null) {
				continue;
			}
			//将一级转为数字1
			Long checkGrade = hg.getGrade();
			Org actualOrg = zndzSubOrg == null ? org : zndzSubOrg;
			ReferralResult result = compare(ReferralRuleType.BODYCHECK, actualOrg, checkGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = new String[]{hisCheck.getCheckName(), String.valueOf(checkGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, checkGrade);
			
			checkList.add(itemCheckResult);
			
			ReferralResDetail itemReferralDetail = new ReferralResDetail(ReferralRuleType.BODYCHECK, hisCheck.getOrg_check_id(), hisCheck.getOrg_check_name(), checkGrade, itemCheckResult.getReferralResult().getMessage());
			detailList.add(itemReferralDetail);
		}

		return removeNoNeedUpGradeItem(detailList);
	}

	@Override
	public List<ReferralResDetail> checkWithoutGmws() throws Exception {
		List<ReferralResDetail> detailList = new ArrayList<>();
		CheckReq checkReq = getCheckReq();
		if(checkReq == null) {
			return detailList;
		}
		
		String orgId = checkReq.getOrgId();
		Org org = orgService.query(orgId);
		
		List<String> checkIdList = checkReq.getCheckIds();
		List<ItemCheckResult> checkList = new ArrayList<>();
		for(String checkId : checkIdList) {
			PageParam param = new PageParam();
			param.put("org_id", GlobalConstant.GMWS_ORG_ID);
			param.put("org_check_id", checkId);
			param.put("_enable", EnableStatus.ENABLE.getStatus());
			param.put("body_check_status", HisDataStatus.REVIEWED.getStatus());
			List<CheckMapper> mapperList = mapperService.list(param);
			CheckMapper hisCheck = CollectionUtils.isNotEmpty(mapperList) ? mapperList.get(0) : null;
			if(hisCheck == null) {
				continue;
			}
			String ebmCheckId = hisCheck.getCheck_id();
			if(StringUtil.isEmpty(ebmCheckId)) {
				continue;
			}
			PageParam ebmParam = new PageParam();
			ebmParam.put("id", ebmCheckId);
			ebmParam.put("_enable", EnableStatus.ENABLE.getStatus());
			ebmParam.put("body_check_status", EbmStatus.REVIEWED.getStatus());
			
			List<BodyCheck> ebmCheckList = checkService.list(ebmParam);
			BodyCheck ebmCheck = CollectionUtils.isNotEmpty(ebmCheckList) ? ebmCheckList.get(0) : null;
			if(ebmCheck == null) {
				continue;
			}
			String diseaseGradeName = ebmCheck.getGrade();
			HospitalGrade hg = HospitalGrade.getByName(diseaseGradeName);
			if(hg == null) {
				continue;
			}
			//将一级转为数字1
			Long checkGrade = hg.getGrade();
			Org actualOrg = org;
			ReferralResult result = compare(ReferralRuleType.BODYCHECK, actualOrg, checkGrade);
			//需转诊的统一收集起来,如果有一项等级比机构等级高则向上转，如果全部项的等级都比机构的等级低才向下转
			String[] args = new String[]{hisCheck.getCheckName(), String.valueOf(checkGrade)};
			ItemCheckResult itemCheckResult = new ItemCheckResult(result, args, checkGrade);
			
			checkList.add(itemCheckResult);
			
			ReferralResDetail itemReferralDetail = new ReferralResDetail(ReferralRuleType.BODYCHECK, hisCheck.getOrg_check_id(), hisCheck.getOrg_check_name(), checkGrade, itemCheckResult.getReferralResult().getMessage());
			detailList.add(itemReferralDetail);
		}

		return removeNoNeedUpGradeItem(detailList);
	}
}
