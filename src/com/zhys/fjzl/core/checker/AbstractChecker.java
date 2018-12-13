package com.zhys.fjzl.core.checker;

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

import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.constant.DataPool;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.ItemCheckResult;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.ReferralResDetail;
import com.zhys.fjzl.enums.ReferralResult;
import com.zhys.fjzl.enums.ReferralRuleType;
import com.zhys.sys.domain.Org;
import com.zhys.sys.service.OrgService;

@Component
public abstract class AbstractChecker implements ReferralRule {

	private String orgId;
	private String zndzSubOrgId;
	private PatientInfoReq patientInfoReq;
	private DoctorInfoReq doctorInfoReq;
	private DiseaseReq diseaseReq;
	private DrugReq drugReq;
	private CheckReq checkReq;
	@Resource
	private OrgService orgService;
		
	protected static final Integer UP = 1;
	protected static final Integer DOWN = 0;
	
	private Logger logger = LoggerFactory.getLogger(AbstractChecker.class);
	
	
	@Override
	public void load(String orgId, String zndzSubOrgId, PatientInfoReq patientInfoReq, DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) {
		this.orgId = orgId;
		this.zndzSubOrgId = zndzSubOrgId;
		this.patientInfoReq = patientInfoReq;
		this.doctorInfoReq = doctorInfoReq;
		this.diseaseReq = diseaseReq;
		this.drugReq = drugReq;
		this.checkReq = checkReq;
	}
	
	protected ReferralResult compare(ReferralRuleType type, Org org, Long itemGrade) {
		String _grade = org.getSo_grade();
		Long orgGrade = Long.valueOf(_grade);
		
		if(itemGrade.longValue() < orgGrade.longValue()) {
			if(type == ReferralRuleType.DISEASE) {
				return ReferralResult.DISEASE_LT;
			} else if (type == ReferralRuleType.DRUG) {
				//由于药品是向上兼容的，所以药品无需下转
				//return ReferralResult.DRUG_LT;
				return ReferralResult.DRUG_MATCH;
			} else if (type == ReferralRuleType.BODYCHECK) {
				return ReferralResult.CHECK_LT;
			} 
		} else if(itemGrade.longValue() == orgGrade.longValue()) {
			if(type == ReferralRuleType.DISEASE) {
				return ReferralResult.DISEASE_MATCH;
			} else if (type == ReferralRuleType.DRUG) {
				return ReferralResult.DRUG_MATCH;
			} else if (type == ReferralRuleType.BODYCHECK) {
				return ReferralResult.CHECK_MATCH;
			} 
		} else if(itemGrade.longValue() > orgGrade.longValue()) {
			if(type == ReferralRuleType.DISEASE) {
				return ReferralResult.DISEASE_GT;
			} else if (type == ReferralRuleType.DRUG) {
				return ReferralResult.DRUG_GT;
			} else if (type == ReferralRuleType.BODYCHECK) {
				return ReferralResult.CHECK_GT;
			} 
		} 
		return null;
	}
	
	/**
	 * 转诊至医联体内targetGrade级机构（上级机构排第一）
	 * @param flag 1:上转 0:下转
	 * @param targetGrade 接诊的医院等级
	 * @return
	 * @throws Exception
	 */
	protected List<Org> getReferralOrg(Integer flag, Long targetGrade) throws Exception {
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
			List<Org> orgList = getOrgListFromMedicalBody();
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
			List<Org> orgList = getOrgListFromMedicalBody();
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
	
	/**
	 * 只保留需要转诊的检测结果
	 * @param detailList
	 * @return
	 * @throws Exception 
	 */
	protected List<ReferralResDetail> removeNoNeedUpGradeItem(List<ReferralResDetail> detailList) throws Exception {
		logger.info("removeNoNeedUpGradeItem start");

		List<ReferralResDetail> upGradeList = new ArrayList<>();
		
		if(CollectionUtils.isNotEmpty(detailList)) {
			String actualOrgId = StringUtil.isEmpty(zndzSubOrgId) ? orgId : zndzSubOrgId;
			Org org = orgService.query(actualOrgId);
			String _grade = org.getSo_grade();
			Long orgGrade = Long.valueOf(_grade);
			
			for(ReferralResDetail item : detailList) {
				if(item.getGrade().longValue() != orgGrade.longValue()) {
					upGradeList.add(item);
				}
			}
		}
		logger.info("removeNoNeedUpGradeItem end");
		return upGradeList;
	}
	
	/*protected ReferralResult getHighestGrade(Map<ReferralResult,String[]> set) {
		Long max = -1L;
		ReferralResult highest = null;
		for(ReferralResult item : set.keySet()) {
			if(max < item.getType()) {
				max = item.getType();
			}
		}
		for(ReferralResult item : set.keySet()) {
			if(item.getType().longValue() == max.longValue()) {
				highest = item;
				break;
			}
		}
		highest = highest == null ? ReferralResult.NONE : highest;
		return highest;
	}*/
	
	/**
	 * 先取上转,如果上转的级别相同则找(三大目录)最高等级的一条数据,下转也如此
	 * @param list
	 * @return
	 */
	protected ItemCheckResult getHighestGrade(List<ItemCheckResult> list) {
		if(CollectionUtils.isEmpty(list)) {
			return new ItemCheckResult(ReferralResult.NONE, null, 0L);
		}
		
		Collections.sort(list, new Comparator<ItemCheckResult>() {
			@Override
			public int compare(ItemCheckResult o1, ItemCheckResult o2) {
				Long grade1 = o1.getGrade();
				Long grade2 = o2.getGrade();
				
				ReferralResult result1 = o1.getReferralResult();
				ReferralResult result2 = o2.getReferralResult();
				
				if(result1.getType()==result2.getType()){
					if(grade1 == grade2){
						return o2.getGrade().compareTo(o1.getGrade());
					}else{
						return grade2.compareTo(grade1);
					}
					
				} else {
					 return result2.getType().compareTo(result1.getType());
				}
			}
		});
		ItemCheckResult itemCheckResult = list.get(0);
		return itemCheckResult;
	}
	
	protected ReferralRes noResult() {
		ReferralRes res = null;
		try {
			res = new ReferralRes(ReferralResult.NONE, null, null, 1L);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	protected ReferralResDetail noReferralResDetail(ReferralRuleType type) {
		ReferralResDetail res = null;
		try {
			res = new ReferralResDetail(type, null, null, 1L, ReferralResult.NONE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	
	protected Org getZndzSubOrg() {
		if(StringUtil.isNotEmpty(zndzSubOrgId)) {
			return DataPool.getOrg(zndzSubOrgId);
		}
		return null;
	}

	//获取医联体的所有机构
	private List<Org> getOrgListFromMedicalBody() throws Exception {
		Org rootOrg = getRootOrg(this.orgId);
		String so_code2 = rootOrg.getSo_code2();
		Org param = new Org();
		param.setSo_code2(so_code2);
		return orgService.list(param);
	}
	
	//查询所在医联体
	private Org getRootOrg(String orgId) throws Exception {
		Org org = orgService.query(orgId);
		if(org.getSo_parentid().equals(GlobalConstant.XZYX_ORG_ID)) {
			return org;
		} else {
			return getRootOrg(org.getSo_parentid());
		}
	}
	
	public String getOrgId() {
		return orgId;
	}

	public PatientInfoReq getPatientInfoReq() {
		return patientInfoReq;
	}

	public DiseaseReq getDiseaseReq() {
		return diseaseReq;
	}

	public DrugReq getDrugReq() {
		return drugReq;
	}

	public CheckReq getCheckReq() {
		return checkReq;
	}

	public DoctorInfoReq getDoctorInfoReq() {
		return doctorInfoReq;
	}
	
}
