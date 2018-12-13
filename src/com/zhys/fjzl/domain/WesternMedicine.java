package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：知识库西药表类
 */
public class WesternMedicine extends Base {

	private static final long serialVersionUID = 9193024466658770995L;

	@FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;
	
	@FieldAnnotation(comment = "药品编码", exp = true, imp = true, empty = true, len = 50)
	private String drug_code;

    @FieldAnnotation(comment = "药品名称", exp = true, imp = true, empty = true, len = 50)
    @FieldCheck(nullable = false)
    private String drug_name;

    @FieldAnnotation(comment = "剂型", exp = true, imp = true, empty = true, len = 50)
    @FieldCheck
    private String dosage_form;

    @FieldAnnotation(comment = "无", exp = true, imp = true, empty = true, len = 10)
    private Long _enable;

    @FieldAnnotation(comment = "用法", exp = true, imp = true, empty = true, len = 50)
    private String _usage;

    @FieldAnnotation(comment = "收费类别", exp = true, imp = true, empty = true, len = 20)
    private String charge_type;

    @FieldAnnotation(comment = "收费项目等级", exp = true, imp = true, empty = true, len = 20)
    private String charge_grade;

    @FieldAnnotation(comment = "atc1", exp = true, imp = true, empty = true, len = 50)
    private String atc1;

    @FieldAnnotation(comment = "atc2", exp = true, imp = true, empty = true, len = 50)
    private String atc2;

    @FieldAnnotation(comment = "atc3", exp = true, imp = true, empty = true, len = 50)
    private String atc3;

    @FieldAnnotation(comment = "atc4", exp = true, imp = true, empty = true, len = 50)
    private String atc4;

    @FieldAnnotation(comment = "药品等级", exp = true, imp = true, empty = true, len = 20)
    @FieldCheck(nullable = false)
    private String grade;

    @FieldAnnotation(comment = "审核状态", exp = true, imp = true, empty = true, len = 3)
    private Long western_status;

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

    private String creatorName;
    private String last_updatorName;
    private String revieworName;
    
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

	/**
     * 知识库西药表对象构造函数
     */
    public WesternMedicine() {
        super();
    }

    public WesternMedicine(String id) {
        this("id",id);
    }

    public WesternMedicine(String property, Object value) {
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
     * 获得药品名称
     * @return String
     */
    public String getDrug_name(){
        return this.drug_name;
    }

    /**
     * 设置药品名称
     * @param drug_name  药品名称
     */
    public void setDrug_name(String drug_name){
        putField("drug_name");
        this.drug_name = drug_name;
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
     * 获得无
     * @return Long
     */
    public Long get_enable(){
        return this._enable;
    }

    /**
     * 设置无
     * @param _enable  无
     */
    public void set_enable(Long _enable){
        putField("_enable");
        this._enable = _enable;
    }
    /**
     * 获得用法
     * @return String
     */
    public String get_usage(){
        return this._usage;
    }

    /**
     * 设置用法
     * @param _usage  用法
     */
    public void set_usage(String _usage){
        putField("_usage");
        this._usage = _usage;
    }
    /**
     * 获得收费类别
     * @return String
     */
    public String getCharge_type(){
        return this.charge_type;
    }

    /**
     * 设置收费类别
     * @param charge_type  收费类别
     */
    public void setCharge_type(String charge_type){
        putField("charge_type");
        this.charge_type = charge_type;
    }
    /**
     * 获得收费项目等级
     * @return Long
     */
    public String getCharge_grade(){
        return this.charge_grade;
    }

    /**
     * 设置收费项目等级
     * @param charge_grade  收费项目等级
     */
    public void setCharge_grade(String charge_grade){
        putField("charge_grade");
        this.charge_grade = charge_grade;
    }
    /**
     * 获得atc1
     * @return String
     */
    public String getAtc1(){
        return this.atc1;
    }

    /**
     * 设置atc1
     * @param atc1  atc1
     */
    public void setAtc1(String atc1){
        putField("atc1");
        this.atc1 = atc1;
    }
    /**
     * 获得atc2
     * @return String
     */
    public String getAtc2(){
        return this.atc2;
    }

    /**
     * 设置atc2
     * @param atc2  atc2
     */
    public void setAtc2(String atc2){
        putField("atc2");
        this.atc2 = atc2;
    }
    /**
     * 获得atc3
     * @return String
     */
    public String getAtc3(){
        return this.atc3;
    }

    /**
     * 设置atc3
     * @param atc3  atc3
     */
    public void setAtc3(String atc3){
        putField("atc3");
        this.atc3 = atc3;
    }
    /**
     * 获得atc4
     * @return String
     */
    public String getAtc4(){
        return this.atc4;
    }

    /**
     * 设置atc4
     * @param atc4  atc4
     */
    public void setAtc4(String atc4){
        putField("atc4");
        this.atc4 = atc4;
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
     * 获得审核状态
     * @return Integer
     */
    public Long getWestern_status(){
        return this.western_status;
    }

    /**
     * 设置审核状态
     * @param western_status  审核状态
     */
    public void setWestern_status(Long western_status){
        putField("western_status");
        this.western_status = western_status;
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

	public String getDrug_code() {
		return drug_code;
	}

	public void setDrug_code(String drug_code) {
		putField("drug_code");
		this.drug_code = drug_code;
	}
    
    
}