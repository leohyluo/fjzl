package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-21 <br/>
 * 描述：疾病对照表类
 */
public class DiseaseMapper extends Base {

	private static final long serialVersionUID = 1L;

	@FieldAnnotation(comment = "id", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "机构id", exp = true, imp = true, empty = true, len = 20)
    private String org_id;

    @FieldAnnotation(comment = "疾病id", exp = true, imp = true, empty = true, len = 20)
    private String disease_id;

    @FieldAnnotation(comment = "机构疾病id", exp = true, imp = true, empty = true, len = 20)
    private String org_disease_id;

    @FieldAnnotation(comment = "机构疾病名称", exp = true, imp = true, empty = true, len = 100)
    @FieldCheck(nullable = false)
    private String org_disease_name;

    @FieldAnnotation(comment = "机构icd10", exp = true, imp = true, empty = true, len = 20)
    private String org_icd_10;

    @FieldAnnotation(comment = "疾病定义", exp = true, imp = true, empty = true, len = 500)
    private String definition;

    @FieldAnnotation(comment = "状态", exp = true, imp = true, empty = true, len = 10)
    private Long disease_status;

    @FieldAnnotation(comment = "对照方式", exp = true, imp = true, empty = true, len = 10)
    private Long type;
    
    @FieldAnnotation(comment = "是否启用", exp = true, imp = true, empty = true, len = 10)
    private Long _enable;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 10)
    private Long creator;
    
    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改人", exp = true, imp = true, empty = true, len = 10)
    private Long last_updateor;
    @FieldAnnotation(comment = "审核时间", exp = true, imp = true, empty = true)
    private Date review_time;

    @FieldAnnotation(comment = "审核者", exp = true, imp = true, empty = true, len = 10)
    private Long reviewor;
    
    @FieldAnnotation(comment = "疾病等级", exp = true, imp = true, empty = true, len = 10)
    private String grade;

    @FieldAnnotation(comment = "导入批号", exp = true, imp = true, empty = true, len = 64)
    private String import_no;

    @FieldAnnotation(comment = "对照者", exp = true, imp = true, empty = true, len = 64)
    private String mapper_user;

    @FieldAnnotation(comment = "对照时间", exp = true, imp = true, empty = true)
    private Date mapper_time;

    private String creatorName;//创建者名称 
    private String last_updatorName;//最后修改者名称
    private String revieworName; //审核者名称
    private String orgName; //机构名称
    private String diseaseName; //知识库疾病名称
    private String ebmIcd10; //知识库icd10
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
	
	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		 putField("memo");
		this.memo = memo;
	}
	/**
     * 疾病对照表对象构造函数
     */
    public DiseaseMapper() {
        super();
    }

    public DiseaseMapper(String id) {
        this("id",id);
    }

    public DiseaseMapper(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得id
     * @return Long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * 设置id
     * @param id  id
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
     * 获得疾病id
     * @return String
     */
    public String getDisease_id(){
        return this.disease_id;
    }

    /**
     * 设置疾病id
     * @param disease_id  疾病id
     */
    public void setDisease_id(String disease_id){
        putField("disease_id");
        this.disease_id = disease_id;
    }
    /**
     * 获得机构疾病id
     * @return String
     */
    public String getOrg_disease_id(){
        return this.org_disease_id;
    }

    /**
     * 设置机构疾病id
     * @param org_disease_id  机构疾病id
     */
    public void setOrg_disease_id(String org_disease_id){
        putField("org_disease_id");
        this.org_disease_id = org_disease_id;
    }
    /**
     * 获得机构疾病名称
     * @return String
     */
    public String getOrg_disease_name(){
        return this.org_disease_name;
    }

    /**
     * 设置机构疾病名称
     * @param org_disease_name  机构疾病名称
     */
    public void setOrg_disease_name(String org_disease_name){
        putField("org_disease_name");
        this.org_disease_name = org_disease_name;
    }
    /**
     * 获得机构icd10
     * @return String
     */
    public String getOrg_icd_10(){
        return this.org_icd_10;
    }

    /**
     * 设置机构icd10
     * @param org_icd_10  机构icd10
     */
    public void setOrg_icd_10(String org_icd_10){
        putField("org_icd_10");
        this.org_icd_10 = org_icd_10;
    }
    /**
     * 获得疾病定义
     * @return String
     */
    public String getDefinition(){
        return this.definition;
    }

    /**
     * 设置疾病定义
     * @param definition  疾病定义
     */
    public void setDefinition(String definition){
        putField("definition");
        this.definition = definition;
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
    /**
     * 获得对照方式
     * @return Long
     */
    public Long getType(){
        return this.type;
    }

    /**
     * 设置对照方式
     * @param type  对照方式
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
     * @return Long
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

	public String getEbmIcd10() {
		return ebmIcd10;
	}

	public void setEbmIcd10(String ebmIcd10) {
		putField("ebmIcd10");
		this.ebmIcd10 = ebmIcd10;
	}
    
}