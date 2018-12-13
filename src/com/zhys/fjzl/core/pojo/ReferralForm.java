package com.zhys.fjzl.core.pojo;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.zhys.core.util.JsonUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.util.DateUtils;


/**
 * 转诊单信息
 * @author Administrator
 *
 */
public class ReferralForm {
	
	//是否转诊标识 0未转诊 1已转诊
	private String flag;

	//记录id
    private String record_id;

    //机构编码
    private String org_id;

    //目标单位
    private String target_unit;

    //患者姓名
    private String target_name;

    //患者性别
    private String target_gender;

    //患者年龄
    private String target_age;

    //初步诊断
    private String diagnosis;

    //现病史
    private String history_illness;

    //转出原因
    private String reason;

    //既往史
    private String past_history;

    //转诊医生
    private String target_doctor;

    //联系电话
    private String target_mobile;

    //机构名称
    private String target_org_name;

    //转诊日期
    private Date target_date;

    public ReferralForm(JSONObject json) {
    	this.flag = JsonUtils.getString(json, "flag");
    	if("1".equals(flag)) {
	    	this.record_id = JsonUtils.getString(json, "record_id");
	    	this.org_id = JsonUtils.getString(json, "org_id");
	    	this.target_unit = JsonUtils.getString(json, "target_unit");
	    	this.target_name = JsonUtils.getString(json, "target_name");
	    	this.target_gender = JsonUtils.getString(json, "target_gender");
	    	this.diagnosis = JsonUtils.getString(json, "diagnosis");
	    	this.history_illness = JsonUtils.getString(json, "history_illness");
	    	this.reason = JsonUtils.getString(json, "reason");
	    	this.past_history = JsonUtils.getString(json, "past_history");
	    	this.target_doctor = JsonUtils.getString(json, "target_doctor");
	    	this.target_mobile = JsonUtils.getString(json, "target_mobile");
	    	this.target_org_name = JsonUtils.getString(json, "target_org_name");
	    	String targetDateStr = JsonUtils.getString(json, "target_date");
	    	if(StringUtil.isNotEmpty(targetDateStr)) {
	    		try {
					this.target_date = DateUtils.parseDate(targetDateStr, DateUtils.DATE_FORMAT);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
    	}
    }
    
	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public String getTarget_unit() {
		return target_unit;
	}

	public void setTarget_unit(String target_unit) {
		this.target_unit = target_unit;
	}

	public String getTarget_name() {
		return target_name;
	}

	public void setTarget_name(String target_name) {
		this.target_name = target_name;
	}

	public String getTarget_gender() {
		return target_gender;
	}

	public void setTarget_gender(String target_gender) {
		this.target_gender = target_gender;
	}

	public String getTarget_age() {
		return target_age;
	}

	public void setTarget_age(String target_age) {
		this.target_age = target_age;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	public String getHistory_illness() {
		return history_illness;
	}

	public void setHistory_illness(String history_illness) {
		this.history_illness = history_illness;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getPast_history() {
		return past_history;
	}

	public void setPast_history(String past_history) {
		this.past_history = past_history;
	}

	public String getTarget_doctor() {
		return target_doctor;
	}

	public void setTarget_doctor(String target_doctor) {
		this.target_doctor = target_doctor;
	}

	public String getTarget_mobile() {
		return target_mobile;
	}

	public void setTarget_mobile(String target_mobile) {
		this.target_mobile = target_mobile;
	}

	public String getTarget_org_name() {
		return target_org_name;
	}

	public void setTarget_org_name(String target_org_name) {
		this.target_org_name = target_org_name;
	}

	public Date getTarget_date() {
		return target_date;
	}

	public void setTarget_date(Date target_date) {
		this.target_date = target_date;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
