package com.zhys.fjzl.domain;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.core.util.CollectionUtils;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.core.pojo.CheckReq;
import com.zhys.fjzl.core.pojo.DiseaseReq;
import com.zhys.fjzl.core.pojo.DoctorInfoReq;
import com.zhys.fjzl.core.pojo.DrugReq;
import com.zhys.fjzl.core.pojo.PatientInfoReq;
import com.zhys.fjzl.enums.ReferralFlag;
import com.zhys.sys.domain.Org;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述：类
 */
public class ApiRecord extends Base {

	private static final long serialVersionUID = 3204735020035408322L;

	@FieldAnnotation(comment = "", exp = false, imp = false, empty = true, len = 19, pk = true)
    private Long id;

    @FieldAnnotation(comment = "机构id", exp = true, imp = true, empty = true, len = 20)
    private String orgid;

    @FieldAnnotation(comment = "医生编号", exp = true, imp = true, empty = true, len = 20)
    private String doctorcode;

    @FieldAnnotation(comment = "医生姓名", exp = true, imp = true, empty = true, len = 20)
    private String doctorname;
    
    @FieldAnnotation(comment = "科室", exp = true, imp = true, empty = true, len = 50)
    private String department;
    
    @FieldAnnotation(comment = "医生级别", exp = true, imp = true, empty = true, len = 50)
    private String doctorGrade;

    @FieldAnnotation(comment = "医生联系方式", exp = true, imp = true, empty = true, len = 20)
    private String doctormobile;

    @FieldAnnotation(comment = "患者姓名", exp = true, imp = true, empty = true, len = 20)
    private String patientname;

    @FieldAnnotation(comment = "患者出生日期", exp = true, imp = true, empty = true, len = 20)
    private String patientbirth;

    @FieldAnnotation(comment = "患者性别", exp = true, imp = true, empty = true, len = 20)
    private String patientgener;

    @FieldAnnotation(comment = "机构端疾病id", exp = true, imp = true, empty = true, len = 50)
    private String hisdiseaseids;
    
    @FieldAnnotation(comment = "机构端疾病名称", exp = true, imp = true, empty = true, len = 250)
    private String hisDiseaseNames;

    @FieldAnnotation(comment = "机构端药品id", exp = true, imp = true, empty = true, len = 255)
    private String hisdrugids;

    @FieldAnnotation(comment = "机构端药品类型", exp = true, imp = true, empty = true, len = 255)
    private String hisdrugtypes;

    @FieldAnnotation(comment = "机构检查id", exp = true, imp = true, empty = true, len = 255)
    private String hischeckids;

    @FieldAnnotation(comment = "检测是否通过", exp = true, imp = true, empty = true, len = 10)
    private Long examresult;
    
    @FieldAnnotation(comment = "转诊类型", exp = true, imp = true, empty = true, len = 10)
    private Integer referralType;

    @FieldAnnotation(comment = "可转诊机构列表", exp = true, imp = true, empty = true, len = 2000)
    private String orglist;

    @FieldAnnotation(comment = "是否已转诊", exp = true, imp = true, empty = true, len = 10)
    private Long referralflag;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date createtime;


    /**
     * 对象构造函数
     */
    public ApiRecord() {
        super();
    }
    
    public ApiRecord(String orgId, PatientInfoReq patientInfoReq, DoctorInfoReq doctorInfoReq,
    		DiseaseReq diseaseReq, DrugReq drugReq, CheckReq checkReq) {
    	try {
    		this.orgid = orgId;
    		if(patientInfoReq != null) {
    			this.patientname = patientInfoReq.getName();
    			this.patientbirth = patientInfoReq.getBirth();
    			if(patientInfoReq.getGender() != null) {
    				String genderStr = patientInfoReq.getGender() == 0 ? "男" : "女";
    				this.patientgener = genderStr;
    			}
    		}
    		
    		if(doctorInfoReq != null) {
    			this.doctorcode = doctorInfoReq.getDoctorCode();
    			this.doctorname = doctorInfoReq.getDoctorName();
    			this.department = doctorInfoReq.getDepartment();
    			this.doctorGrade = doctorInfoReq.getDoctorGrade();
    			this.doctormobile = doctorInfoReq.getMobile();
    		}
    		
    		if(diseaseReq != null) {
    			List<String> diseaseIdList = diseaseReq.getDiseaseIds();
    			String diseaseIds = StringUtil.List2String(diseaseIdList, ",");
    			this.hisdiseaseids = diseaseIds;
    			
    			List<String> diseaseNameList = diseaseReq.getDiseaseNames();
    			String diseaseNames = StringUtil.List2String(diseaseNameList, ",");
    			this.hisDiseaseNames = diseaseNames;
    		}
    		
    		if(drugReq != null) {
    			JSONArray drugArr = drugReq.getDrugs();
    			List<String> drugIdList = new ArrayList<>();
    			List<String> drugTypeList = new ArrayList<>();
    			
    			for(int i=0; i<drugArr.size(); i++) {
    				JSONObject item = drugArr.getJSONObject(i);
    				String hisDrugId = item.getString("id");
    				String type = String.valueOf(item.getLong("type"));
    				drugIdList.add(hisDrugId);
    				drugTypeList.add(type);
    			}
    			String drugIds = StringUtil.List2String(drugIdList, ",");
    			String drugTypes = StringUtil.List2String(drugTypeList, ",");
    			this.hisdrugids = drugIds;
    			this.hisdrugtypes = drugTypes;
    		}
    		
    		if(checkReq != null) {
    			List<String> checkIdList = checkReq.getCheckIds();
    			String checkIds = StringUtil.List2String(checkIdList, ",");
    			this.hischeckids = checkIds;
    		}
    		this.referralflag = ReferralFlag.NO.getStatus();
    		this.createtime = new Date();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public ApiRecord(String id) {
        this("id",id);
    }

    public ApiRecord(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得
     * @return Long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * 设置
     * @param id  
     */
    public void setId(Long id){
        putField("id");
        this.id = id;
    }
    /**
     * 获得机构id
     * @return String
     */
    public String getOrgid(){
        return this.orgid;
    }

    /**
     * 设置机构id
     * @param orgid  机构id
     */
    public void setOrgid(String orgid){
        putField("orgid");
        this.orgid = orgid;
    }
    /**
     * 获得医生编号
     * @return String
     */
    public String getDoctorcode(){
        return this.doctorcode;
    }

    /**
     * 设置医生编号
     * @param doctorcode  医生编号
     */
    public void setDoctorcode(String doctorcode){
        putField("doctorcode");
        this.doctorcode = doctorcode;
    }
    /**
     * 获得医生姓名
     * @return String
     */
    public String getDoctorname(){
        return this.doctorname;
    }

    /**
     * 设置医生姓名
     * @param doctorname  医生姓名
     */
    public void setDoctorname(String doctorname){
        putField("doctorname");
        this.doctorname = doctorname;
    }
    
    public String getHisDiseaseNames() {
		return hisDiseaseNames;
	}

	public void setHisDiseaseNames(String hisDiseaseNames) {
		putField("hisDiseaseNames");
		this.hisDiseaseNames = hisDiseaseNames;
	}

	/**
     * 获得医生联系方式
     * @return String
     */
    public String getDoctormobile(){
        return this.doctormobile;
    }

    /**
     * 设置医生联系方式
     * @param doctormobile  医生联系方式
     */
    public void setDoctormobile(String doctormobile){
        putField("doctormobile");
        this.doctormobile = doctormobile;
    }
    /**
     * 获得患者姓名
     * @return String
     */
    public String getPatientname(){
        return this.patientname;
    }

    /**
     * 设置患者姓名
     * @param patientname  患者姓名
     */
    public void setPatientname(String patientname){
        putField("patientname");
        this.patientname = patientname;
    }
    /**
     * 获得患者出生日期
     * @return String
     */
    public String getPatientbirth(){
        return this.patientbirth;
    }

    /**
     * 设置患者出生日期
     * @param patientbirth  患者出生日期
     */
    public void setPatientbirth(String patientbirth){
        putField("patientbirth");
        this.patientbirth = patientbirth;
    }
    /**
     * 获得患者性别
     * @return String
     */
    public String getPatientgener(){
        return this.patientgener;
    }

    /**
     * 设置患者性别
     * @param patientgener  患者性别
     */
    public void setPatientgener(String patientgener){
        putField("patientgener");
        this.patientgener = patientgener;
    }
    /**
     * 获得机构端疾病id
     * @return String
     */
    public String getHisdiseaseids(){
        return this.hisdiseaseids;
    }

    /**
     * 设置机构端疾病id
     * @param hisdiseaseids  机构端疾病id
     */
    public void setHisdiseaseids(String hisdiseaseids){
        putField("hisdiseaseids");
        this.hisdiseaseids = hisdiseaseids;
    }
    /**
     * 获得机构端药品id
     * @return String
     */
    public String getHisdrugids(){
        return this.hisdrugids;
    }

    /**
     * 设置机构端药品id
     * @param hisdrugids  机构端药品id
     */
    public void setHisdrugids(String hisdrugids){
        putField("hisdrugids");
        this.hisdrugids = hisdrugids;
    }
    /**
     * 获得机构端药品类型
     * @return String
     */
    public String getHisdrugtypes(){
        return this.hisdrugtypes;
    }

    /**
     * 设置机构端药品类型
     * @param hisdrugtypes  机构端药品类型
     */
    public void setHisdrugtypes(String hisdrugtypes){
        putField("hisdrugtypes");
        this.hisdrugtypes = hisdrugtypes;
    }
    /**
     * 获得机构检查id
     * @return String
     */
    public String getHischeckids(){
        return this.hischeckids;
    }

    /**
     * 设置机构检查id
     * @param hischeckids  机构检查id
     */
    public void setHischeckids(String hischeckids){
        putField("hischeckids");
        this.hischeckids = hischeckids;
    }
    /**
     * 获得
     * @return Long
     */
    public Long getExamresult(){
        return this.examresult;
    }

    /**
     * 设置
     * @param examresult  
     */
    public void setExamresult(Long examresult){
        putField("examresult");
        this.examresult = examresult;
    }
    /**
     * 获得可转诊机构列表
     * @return String
     */
    public String getOrglist(){
        return this.orglist;
    }

    /**
     * 设置可转诊机构列表
     * @param orglist  可转诊机构列表
     */
    public void setOrglist(String orglist){
        putField("orglist");
        this.orglist = orglist;
    }
    
    public void setOrglist(List<Org> orgList){
       if(CollectionUtils.isNotEmpty(orgList)) {
    	   JSONArray jarr = new JSONArray();
    	   for(Org org : orgList) {
    		   JSONObject json = new JSONObject();
    		   json.put("orgId", org.getSo_id());
    		   json.put("orgName", org.getSo_name());
    		   jarr.add(json);
    	   }
    	   String str = jarr.toString();
    	   setOrglist(str);
       }
    }
    /**
     * 获得
     * @return Long
     */
    public Long getReferralflag(){
        return this.referralflag;
    }

    /**
     * 设置
     * @param referralflag  
     */
    public void setReferralflag(Long referralflag){
        putField("referralflag");
        this.referralflag = referralflag;
    }
    /**
     * 获得创建时间
     * @return Date
     */
    public Date getCreatetime(){
        return this.createtime;
    }

    /**
     * 设置创建时间
     * @param createtime  创建时间
     */
    public void setCreatetime(Date createtime){
        putField("createtime");
        this.createtime = createtime;
    }

	public Integer getReferralType() {
		return referralType;
	}

	public void setReferralType(Integer referralType) {
		putField("referralType");
		this.referralType = referralType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		putField("department");
		this.department = department;
	}

	public String getDoctorGrade() {
		return doctorGrade;
	}

	public void setDoctorGrade(String doctorGrade) {
		putField("doctorGrade");
		this.doctorGrade = doctorGrade;
	}
    
	
}