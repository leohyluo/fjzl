package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：药品对照表类
 */
public class DrugMapper extends Base {

	private static final long serialVersionUID = 6517782571107125631L;

	@FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "机构id", exp = true, imp = true, empty = true, len = 64)
    private String org_id;

    @FieldAnnotation(comment = "知识库药品id", exp = true, imp = true, empty = true, len = 64)
    private String drug_id;

    @FieldAnnotation(comment = "机构药品编码", exp = true, imp = true, empty = true, len = 64)
    private String org_drug_code;

    @FieldAnnotation(comment = "机构药品名称", exp = true, imp = true, empty = true, len = 50)
    @FieldCheck(nullable = false)
    private String org_drug_name;

    @FieldAnnotation(comment = "本位码", exp = true, imp = true, empty = true, len = 50)
    private String standard_code;

    @FieldAnnotation(comment = "批准文号", exp = true, imp = true, empty = true, len = 50)
    private String approval_code;

    @FieldAnnotation(comment = "规格", exp = true, imp = true, empty = true, len = 200)
    private String specifications;

    @FieldAnnotation(comment = "剂型", exp = true, imp = true, empty = true, len = 50)
    private String dosage_form;

    @FieldAnnotation(comment = "用法用量", exp = true, imp = true, empty = true, len = 255)
    private String drug_usage;

    @FieldAnnotation(comment = "单位", exp = true, imp = true, empty = true, len = 20)
    private String unit;

    @FieldAnnotation(comment = "药物组成", exp = true, imp = true, empty = true, len = 200)
    private String drug_composition;

    @FieldAnnotation(comment = "药物毒性", exp = true, imp = true, empty = true, len = 200)
    private String drug_toxicity;

    @FieldAnnotation(comment = "药性", exp = true, imp = true, empty = true, len = 200)
    private String resistance;

    @FieldAnnotation(comment = "化学成份", exp = true, imp = true, empty = true, len = 200)
    private String chemical_composition;

    @FieldAnnotation(comment = "状态，已对照/未对照/对照失败", exp = true, imp = true, empty = true, len = 10)
    private Long drug_status;

    @FieldAnnotation(comment = "药品等级", exp = true, imp = true, empty = true, len = 10)
    private String grade;

    @FieldAnnotation(comment = "药物类型(西药/中成药/中药饮片)", exp = true, imp = true, empty = true, len = 10)
    private Long type;
    @FieldAnnotation(comment = "是否启用", exp = true, imp = true, empty = true, len = 10)
    private Long _enable;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 64)
    private Long creator;
    
    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改人", exp = true, imp = true, empty = true, len = 10)
    private Long last_updateor;
    @FieldAnnotation(comment = "审核时间", exp = true, imp = true, empty = true)
    private Date review_time;

    @FieldAnnotation(comment = "审核者", exp = true, imp = true, empty = true, len = 64)
    private Long reviewor;

    @FieldAnnotation(comment = "对照时间", exp = true, imp = true, empty = true)
    private Date mapper_time;

    @FieldAnnotation(comment = "对照者", exp = true, imp = true, empty = true, len = 64)
    private String mapper_user;

    @FieldAnnotation(comment = "导入批号", exp = true, imp = true, empty = true, len = 64)
    private String import_no;

    private String creatorName;
    private String last_updatorName;
    private String revieworName;
    private String orgName;
    private String ebmDrugName;
    private String ebmDosageForm;
    private String memo;
    
    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getLast_updatorName() {
		return last_updatorName;
	}

	public void setLast_updatorName(String last_updatorName) {
		this.last_updatorName = last_updatorName;
	}

	public String getRevieworName() {
		return revieworName;
	}

	public void setRevieworName(String revieworName) {
		this.revieworName = revieworName;
	}
	
    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
     * 药品对照表对象构造函数
     */
    public DrugMapper() {
        super();
    }

    public DrugMapper(String id) {
        this("id",id);
    }

    public DrugMapper(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得主键，自增长
     * @return Long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * 设置主键，自增长
     * @param id  主键，自增长
     */
    public void setId(Long id){
        putField("id");
        this.id = id;
    }
    /**
     * 获得机构id
     * @return String
     */
    public String getOrg_id(){
        return this.org_id;
    }

    /**
     * 设置机构id
     * @param org_id  机构id
     */
    public void setOrg_id(String org_id){
        putField("org_id");
        this.org_id = org_id;
    }
    /**
     * 获得机构药品id
     * @return String
     */
    public String getDrug_id(){
        return this.drug_id;
    }

    /**
     * 设置机构药品id
     * @param drug_id  机构药品id
     */
    public void setDrug_id(String drug_id){
        putField("drug_id");
        this.drug_id = drug_id;
    }
    /**
     * 获得机构药品编码
     * @return String
     */
    public String getOrg_drug_code(){
        return this.org_drug_code;
    }

    /**
     * 设置机构药品编码
     * @param org_drug_code  机构药品编码
     */
    public void setOrg_drug_code(String org_drug_code){
        putField("org_drug_code");
        this.org_drug_code = org_drug_code;
    }
    /**
     * 获得机构药品名称
     * @return String
     */
    public String getOrg_drug_name(){
        return this.org_drug_name;
    }

    /**
     * 设置机构药品名称
     * @param org_drug_name  机构药品名称
     */
    public void setOrg_drug_name(String org_drug_name){
        putField("org_drug_name");
        this.org_drug_name = org_drug_name;
    }
    /**
     * 获得本位码
     * @return String
     */
    public String getStandard_code(){
        return this.standard_code;
    }

    /**
     * 设置本位码
     * @param standard_code  本位码
     */
    public void setStandard_code(String standard_code){
        putField("standard_code");
        this.standard_code = standard_code;
    }
    /**
     * 获得批准文号
     * @return String
     */
    public String getApproval_code(){
        return this.approval_code;
    }

    /**
     * 设置批准文号
     * @param approval_code  批准文号
     */
    public void setApproval_code(String approval_code){
        putField("approval_code");
        this.approval_code = approval_code;
    }
    /**
     * 获得规格
     * @return String
     */
    public String getSpecifications(){
        return this.specifications;
    }

    /**
     * 设置规格
     * @param specifications  规格
     */
    public void setSpecifications(String specifications){
        putField("specifications");
        this.specifications = specifications;
    }
    /**
     * 获得剂型
     * @return String
     */
    public String getDosage_form(){
        return this.dosage_form;
    }

    /**
     * 设置剂型
     * @param dosage_form  剂型
     */
    public void setDosage_form(String dosage_form){
        putField("dosage_form");
        this.dosage_form = dosage_form;
    }
    /**
     * 获得用法用量
     * @return String
     */
    public String getDrug_usage(){
        return this.drug_usage;
    }

    /**
     * 设置用法用量
     * @param drug_usage  用法用量
     */
    public void setDrug_usage(String drug_usage){
        putField("drug_usage");
        this.drug_usage = drug_usage;
    }
    /**
     * 获得单位
     * @return String
     */
    public String getUnit(){
        return this.unit;
    }

    /**
     * 设置单位
     * @param unit  单位
     */
    public void setUnit(String unit){
        putField("unit");
        this.unit = unit;
    }
    /**
     * 获得药物组成
     * @return String
     */
    public String getDrug_composition(){
        return this.drug_composition;
    }

    /**
     * 设置药物组成
     * @param drug_composition  药物组成
     */
    public void setDrug_composition(String drug_composition){
        putField("drug_composition");
        this.drug_composition = drug_composition;
    }
    /**
     * 获得药物毒性
     * @return String
     */
    public String getDrug_toxicity(){
        return this.drug_toxicity;
    }

    /**
     * 设置药物毒性
     * @param drug_toxicity  药物毒性
     */
    public void setDrug_toxicity(String drug_toxicity){
        putField("drug_toxicity");
        this.drug_toxicity = drug_toxicity;
    }
    /**
     * 获得药性
     * @return String
     */
    public String getResistance(){
        return this.resistance;
    }

    /**
     * 设置药性
     * @param resistance  药性
     */
    public void setResistance(String resistance){
        putField("resistance");
        this.resistance = resistance;
    }
    /**
     * 获得化学成份
     * @return String
     */
    public String getChemical_composition(){
        return this.chemical_composition;
    }

    /**
     * 设置化学成份
     * @param chemical_composition  化学成份
     */
    public void setChemical_composition(String chemical_composition){
        putField("chemical_composition");
        this.chemical_composition = chemical_composition;
    }
    /**
     * 获得状态，已对照/未对照/对照失败
     * @return Long
     */
    public Long getDrug_status(){
        return this.drug_status;
    }

    /**
     * 设置状态，已对照/未对照/对照失败
     * @param drug_status  状态，已对照/未对照/对照失败
     */
    public void setDrug_status(Long drug_status){
        putField("drug_status");
        this.drug_status = drug_status;
    }
    /**
     * 获得药品等级
     * @return Long
     */
    public String getGrade(){
        return this.grade;
    }

    /**
     * 设置药品等级
     * @param grade  药品等级
     */
    public void setGrade(String grade){
        putField("grade");
        this.grade = grade;
    }
    /**
     * 获得药物类型(西药/中成药/中药饮片)
     * @return Long
     */
    public Long getType(){
        return this.type;
    }

    /**
     * 设置药物类型(西药/中成药/中药饮片)
     * @param type  药物类型(西药/中成药/中药饮片)
     */
    public void setType(Long type){
        putField("type");
        this.type = type;
    }
    /**
     * 获得是否启用
     * @return Long
     */
    public Long get_enable(){
        return this._enable;
    }

    /**
     * 设置是否启用
     * @param _enable  是否启用
     */
    public void set_enable(Long _enable){
        putField("_enable");
        this._enable = _enable;
    }
    /**
     * 获得创建时间
     * @return Date
     */
    public Date getCreate_time(){
        return this.create_time;
    }
   
    /**
     * 设置创建时间
     * @param create_time  创建时间
     */
    public void setCreate_time(Date create_time){
        putField("create_time");
        this.create_time = create_time;
    }
    /**
     * 获得创建者
     * @return String
     */
    public Long getCreator(){
        return this.creator;
    }

    /**
     * 设置创建者
     * @param creator  创建者
     */
    public void setCreator(Long creator){
        putField("creator");
        this.creator = creator;
    }
    
    /**
     * 获得最后修改时间
     * @return Date
     */
    public Date getLast_update_time(){
        return this.last_update_time;
    }

    /**
     * 设置最后修改时间
     * @param last_update_time  最后修改时间
     */
    public void setLast_update_time(Date last_update_time){
        putField("last_update_time");
        this.last_update_time = last_update_time;
    }
    /**
     * 获得最后修改人
     * @return Long
     */
    public Long getLast_updateor(){
        return this.last_updateor;
    }

    /**
     * 设置最后修改人
     * @param last_updateor  最后修改人
     */
    public void setLast_updateor(Long last_updateor){
        putField("last_updateor");
        this.last_updateor = last_updateor;
    }
    /**
     * 获得审核时间
     * @return Date
     */
    public Date getReview_time(){
        return this.review_time;
    }

    /**
     * 设置审核时间
     * @param review_time  审核时间
     */
    public void setReview_time(Date review_time){
        putField("review_time");
        this.review_time = review_time;
    }
    /**
     * 获得审核者
     * @return String
     */
    public Long getReviewor(){
        return this.reviewor;
    }

    /**
     * 设置审核者
     * @param reviewor  审核者
     */
    public void setReviewor(Long reviewor){
        putField("reviewor");
        this.reviewor = reviewor;
    }
    /**
     * 获得对照时间
     * @return Date
     */
    public Date getMapper_time(){
        return this.mapper_time;
    }

    /**
     * 设置对照时间
     * @param mapper_time  对照时间
     */
    public void setMapper_time(Date mapper_time){
        putField("mapper_time");
        this.mapper_time = mapper_time;
    }
    /**
     * 获得对照者
     * @return String
     */
    public String getMapper_user(){
        return this.mapper_user;
    }

    /**
     * 设置对照者
     * @param mapper_user  对照者
     */
    public void setMapper_user(String mapper_user){
        putField("mapper_user");
        this.mapper_user = mapper_user;
    }
    /**
     * 获得导入批号
     * @return String
     */
    public String getImport_no(){
        return this.import_no;
    }

    /**
     * 设置导入批号
     * @param import_no  导入批号
     */
    public void setImport_no(String import_no){
        putField("import_no");
        this.import_no = import_no;
    }

	public String getEbmDrugName() {
		return ebmDrugName;
	}

	public void setEbmDrugName(String ebmDrugName) {
		this.ebmDrugName = ebmDrugName;
	}

	public String getEbmDosageForm() {
		return ebmDosageForm;
	}

	public void setEbmDosageForm(String ebmDosageForm) {
		this.ebmDosageForm = ebmDosageForm;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		 putField("memo");
		this.memo = memo;
	}
    
}