package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：检查对照表类
 */
public class CheckMapper extends Base {

    @FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "机构id", exp = true, imp = true, empty = true, len = 64)
    private String org_id;

    @FieldAnnotation(comment = "知识库检查id", exp = true, imp = true, empty = true, len = 64)
    private String check_id;

    @FieldAnnotation(comment = "机构检查id", exp = true, imp = true, empty = true, len = 64)
    private String org_check_id;

    @FieldAnnotation(comment = "机构检查名称", exp = true, imp = true, empty = true, len = 50)
    @FieldCheck(nullable = false)
    private String org_check_name;

    @FieldAnnotation(comment = "耗材", exp = true, imp = true, empty = true, len = 200)
    private String invitation;

    @FieldAnnotation(comment = "检查等级", exp = true, imp = true, empty = true, len = 10)
    private String grade;

    @FieldAnnotation(comment = "状态，已对照/未对照/对照失败", exp = true, imp = true, empty = true, len = 10)
    private Long body_check_status;

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

    @FieldAnnotation(comment = "导入批号", exp = true, imp = true, empty = true, len = 64)
    private String import_no;

    @FieldAnnotation(comment = "对照时间", exp = true, imp = true, empty = true)
    private Date mapper_time;

    @FieldAnnotation(comment = "对照者", exp = true, imp = true, empty = true, len = 64)
    private String mapper_user;

    private String creatorName;
    private String last_updatorName;
    private String revieworName;
    private String orgName;
    private String checkName; //知识库检查名称
    private String memo;
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
	
    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
	       putField("memo");
		this.memo = memo;
	}

	/**
     * 检查对照表对象构造函数
     */
    public CheckMapper() {
        super();
    }

    public CheckMapper(String id) {
        this("id",id);
    }

    public CheckMapper(String property, Object value) {
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
     * 获得知识库检查id
     * @return String
     */
    public String getCheck_id(){
        return this.check_id;
    }

    /**
     * 设置知识库检查id
     * @param check_id  知识库检查id
     */
    public void setCheck_id(String check_id){
        putField("check_id");
        this.check_id = check_id;
    }
    /**
     * 获得机构检查id
     * @return String
     */
    public String getOrg_check_id(){
        return this.org_check_id;
    }

    /**
     * 设置机构检查id
     * @param org_check_id  机构检查id
     */
    public void setOrg_check_id(String org_check_id){
        putField("org_check_id");
        this.org_check_id = org_check_id;
    }
    /**
     * 获得机构检查名称
     * @return String
     */
    public String getOrg_check_name(){
        return this.org_check_name;
    }

    /**
     * 设置机构检查名称
     * @param org_check_name  机构检查名称
     */
    public void setOrg_check_name(String org_check_name){
        putField("org_check_name");
        this.org_check_name = org_check_name;
    }
    /**
     * 获得耗材
     * @return String
     */
    public String getInvitation(){
        return this.invitation;
    }

    /**
     * 设置耗材
     * @param invitation  耗材
     */
    public void setInvitation(String invitation){
        putField("invitation");
        this.invitation = invitation;
    }
    /**
     * 获得检查等级
     * @return Long
     */
    public String getGrade(){
        return this.grade;
    }

    /**
     * 设置检查等级
     * @param grade  检查等级
     */
    public void setGrade(String grade){
        putField("grade");
        this.grade = grade;
    }
    /**
     * 获得状态，已对照/未对照/对照失败
     * @return Long
     */
    public Long getBody_check_status(){
        return this.body_check_status;
    }

    /**
     * 设置状态，已对照/未对照/对照失败
     * @param body_check_status  状态，已对照/未对照/对照失败
     */
    public void setBody_check_status(Long body_check_status){
        putField("body_check_status");
        this.body_check_status = body_check_status;
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
}