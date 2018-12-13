package com.zhys.fjzl.core;

import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;

public abstract class AbstractReferralHandler implements ReferralHandler {

	protected PatientInfoReq getPatientInfo() {
		return null;
	}
	
	protected DiseaseReq getDiseaseReq() {
		return null;
	}
	
	protected DrugReq getDrugReq() {
		return null;
	}
	
	protected CheckReq getCheckReq() {
		return null;
	}
}
