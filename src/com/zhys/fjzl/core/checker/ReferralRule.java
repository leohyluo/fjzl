package com.zhys.fjzl.core.checker;

import java.util.List;

import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.ReferralResDetail;

public interface ReferralRule {

	void load(String orgId, String zndzSubOrgId, PatientInfoReq patientInfoReq, DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq);
	
	ReferralRes check() throws Exception;
	
	List<ReferralResDetail> check4Gmws() throws Exception;
	
	List<ReferralResDetail> checkWithoutGmws() throws Exception;
	
	void register();
}
