package com.zhys.fjzl.core;

import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.core.pojo.ReferralRes;
import com.zhys.fjzl.core.pojo.response.FjzlResult;

public interface ReferralHandler {

	public ReferralRes handle(String orgId, PatientInfoReq patientInfoReq, DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception;
	
	/**
	 * 
	 * @param orgId	机构id
	 * @param zndzSubOrgId	智能诊断子hospitalid
	 * @param patientInfoReq 患者信息
	 * @param doctorInfoReq	医生信息
	 * @param diseaseReq	疾病信息
	 * @param drugReq		药品信息
	 * @param checkReq		检查信息
	 * @return
	 * @throws Exception
	 */
	public FjzlResult handle4Gmws(String orgId, String zndzSubOrgId, PatientInfoReq patientInfoReq, DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception;
	
	public FjzlResult handleWithoutGmws(String orgId, PatientInfoReq patientInfoReq, 
			DoctorInfoReq doctorInfoReq, DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) throws Exception;
}
