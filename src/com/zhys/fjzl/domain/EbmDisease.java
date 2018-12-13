package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库疾病表类
 */
public class EbmDisease extends Base {

	private static final long serialVersionUID = 6469209054191641125L;

	@FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "icd10", exp = true, imp = true, empty = true, len = 10)
    private String icd_10;

    @FieldAnnotation(comment = "疾病名称", exp = true, imp = true, empty = true, len = 50)
    @FieldCheck(nullable = false, comment = "疾病名称")
    private String disease_name;

    @FieldAnnotation(comment = "助记符", exp = true, imp = true, empty = true, len = 50)
    private String py_sympol;

    @FieldAnnotation(comment = "所属科室", exp = true, imp = true, empty = true, len = 64)
    private String department;

    @FieldAnnotation(comment = "疾病评估", exp = true, imp = true, empty = true, len = 500)
    private String assessment;

    @FieldAnnotation(comment = "疾病等级", exp = true, imp = true, empty = true, len = 10)
    @FieldCheck(nullable = false, comment = "疾病等级")
    private String grade;
    private Long type;
    @FieldAnnotation(comment = "是否启用", exp = true, imp = true, empty = true, len = 10)
    private Long _enable;
    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 10)
    private Long creator;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改者", exp = true, imp = true, empty = true, len = 10)
    private Long last_updator;
    
    @FieldAnnotation(comment = "审核时间", exp = true, imp = true, empty = true)
    private Date review_time;

    @FieldAnnotation(comment = "审核人", exp = true, imp = true, empty = true, len = 10)
    private Long reviewor;
    
	@FieldAnnotation(comment = "状态", exp = true, imp = true, empty = true, len = 10)
    private Long disease_status;
	
	@FieldAnnotation(comment = "icd10附加码", exp = true, imp = true, empty = true)
	private String icd10_attach_code;
	@FieldAnnotation(comment = "对应系统", exp = true, imp = true, empty = true)
	private String dis_system;
	@FieldAnnotation(comment = "一级科室", exp = true, imp = true, empty = true)
	private String dept_lv1;
	@FieldAnnotation(comment = "二级科室", exp = true, imp = true, empty = true)
	private String dept_lv2;
	@FieldAnnotation(comment = "icd10名称", exp = true, imp = true, empty = true)
	private String icd10Name;

	 private String creatorName;
	 private String last_updatorName;
	 private String revieworName;
	 private String sz_grade;
    /**
     * 扩展字段，不与数据库对应
     */
    private String ids;
    private String icd9;
    private String icd9Name;
    

    public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
	    putField("ids");
		this.ids = ids;
	}

	/**
     * 基础知识库疾病表对象构造函数
     */
    public EbmDisease() {
        super();
    }

    public EbmDisease(String id) {
        this("id",id);
    }

    public EbmDisease(String property, Object value) {
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
     * 获得icd10
     * @return String
     */
    public String getIcd_10(){
        return this.icd_10;
    }

    /**
     * 设置icd10
     * @param icd_10  icd10
     */
    public void setIcd_10(String icd_10){
        putField("icd_10");
        this.icd_10 = icd_10;
    }
    /**
     * 获得疾病名称
     * @return String
     */
    public String getDisease_name(){
        return this.disease_name;
    }

    /**
     * 设置疾病名称
     * @param disease_name  疾病名称
     */
    public void setDisease_name(String disease_name){
        putField("disease_name");
        this.disease_name = disease_name;
    }
    /**
     * 获得助记符
     * @return String
     */
    public String getPy_sympol(){
        return this.py_sympol;
    }

    /**
     * 设置助记符
     * @param py_sympol  助记符
     */
    public void setPy_sympol(String py_sympol){
        putField("py_sympol");
        this.py_sympol = py_sympol;
    }
    /**
     * 获得所属科室
     * @return String
     */
    public String getDepartment(){
        return this.department;
    }

    /**
     * 设置所属科室
     * @param department  所属科室
     */
    public void setDepartment(String department){
        putField("department");
        this.department = department;
    }
    /**
     * 获得疾病评估
     * @return String
     */
    public String getAssessment(){
        return this.assessment;
    }

    /**
     * 设置疾病评估
     * @param assessment  疾病评估
     */
    public void setAssessment(String assessment){
        putField("assessment");
        this.assessment = assessment;
    }
    /**
     * 获得疾病等级
     * @return Long
     */
    public String getGrade(){
        return this.grade;
    }

    /**
     * 设置疾病等级
     * @param grade  疾病等级
     */
    public void setGrade(String grade){
        putField("grade");
        this.grade = grade;
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
     * @return Long
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
     * 获得最后修改者
     * @return Long
     */
    public Long getLast_updator(){
        return this.last_updator;
    }

    /**
     * 设置最后修改者
     * @param last_updator  最后修改者
     */
    public void setLast_updator(Long last_updator){
        putField("last_updator");
        this.last_updator = last_updator;
    }
	
	/**
     * 获得状态
     * @return Long
     */
    public Long getDisease_status(){
        return this.disease_status;
    }

    /**
     * 设置状态
     * @param disease_status  状态
     */
    public void setDisease_status(Long disease_status){
        putField("disease_status");
        this.disease_status = disease_status;
    }
	
	public String getIcd9() {
		return icd9;
	}

	public void setIcd9(String icd9) {
		   putField("icd9");
		this.icd9 = icd9;
	}

	public String getIcd9Name() {
		return icd9Name;
	}

	public void setIcd9Name(String icd9Name) {
		   putField("icd9Name");
		this.icd9Name = icd9Name;
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
     * 获得审核人
     * @return Long
     */
    public Long getReviewor(){
        return this.reviewor;
    }

    /**
     * 设置审核人
     * @param reviewor  审核人
     */
    public void setReviewor(Long reviewor){
        putField("reviewor");
        this.reviewor = reviewor;
    }

	public String getIcd10_attach_code() {
		return icd10_attach_code;
	}

	public void setIcd10_attach_code(String icd10_attach_code) {
		putField("icd10_attach_code");
		this.icd10_attach_code = icd10_attach_code;
	}

	public String getDis_system() {
		return dis_system;
	}

	public void setDis_system(String dis_system) {
		putField("dis_system");
		this.dis_system = dis_system;
	}

	public String getDept_lv1() {
		return dept_lv1;
	}

	public void setDept_lv1(String dept_lv1) {
		putField("dept_lv1");
		this.dept_lv1 = dept_lv1;
	}

	public String getDept_lv2() {
		return dept_lv2;
	}

	public void setDept_lv2(String dept_lv2) {
		putField("dept_lv2");
		this.dept_lv2 = dept_lv2;
	}
    
    
    public String getCreatorName() {
    	return creatorName;
    }
    
    public void setCreateorName(String creatorName) {
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public String getIcd10Name() {
		return icd10Name;
	}

	public void setIcd10Name(String icd10Name) {
		putField("icd10Name");
		this.icd10Name = icd10Name;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getSz_grade() {
		return sz_grade;
	}

	public void setSz_grade(String sz_grade) {
		putField("sz_grade");
		this.sz_grade = sz_grade;
	}
	
}