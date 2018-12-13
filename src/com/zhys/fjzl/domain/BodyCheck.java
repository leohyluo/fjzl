package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：知识库检查表类
 */
public class BodyCheck extends Base {

	private static final long serialVersionUID = 3893326466217880934L;

	@FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "检查名称", exp = true, imp = true, empty = true, len = 100)
    @FieldCheck(nullable = false, comment = "检查名称")
    private String name;

    @FieldAnnotation(comment = "检查等级", exp = true, imp = true, empty = true, len = 64)
    @FieldCheck(nullable = false, comment = "检查等级")
    private String grade;
    
    private String sz_grade;

    @FieldAnnotation(comment = "特检特治标志", exp = true, imp = true, empty = true, len = 10)
    private Long special;

    @FieldAnnotation(comment = "一级及以下限价", exp = true, imp = true, empty = true, len = 11, scale = 2)
    private Double level1_price;

    @FieldAnnotation(comment = "二级限价", exp = true, imp = true, empty = true, len = 11, scale = 2)
    private Double level2_price;

    @FieldAnnotation(comment = "三乙限价", exp = true, imp = true, empty = true, len = 11, scale = 2)
    private Double level3_price;
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

    @FieldAnnotation(comment = "状态", exp = true, imp = true, empty = true, len = 10)
    private Long body_check_status;
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
    /**
     * 知识库检查表对象构造函数
     */
    public BodyCheck() {
        super();
    }

    public BodyCheck(String id) {
        this("id",id);
    }

    public BodyCheck(String property, Object value) {
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
     * 获得检查名称
     * @return String
     */
    public String getName(){
        return this.name;
    }

    /**
     * 设置检查名称
     * @param name  检查名称
     */
    public void setName(String name){
        putField("name");
        this.name = name;
    }
    /**
     * 获得检查等级
     * @return String
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
     * 获得特检特治标志
     * @return Long
     */
    public Long getSpecial(){
        return this.special;
    }

    /**
     * 设置特检特治标志
     * @param special  特检特治标志
     */
    public void setSpecial(Long special){
        putField("special");
        this.special = special;
    }
    /**
     * 获得一级及以下限价
     * @return Double
     */
    public Double getLevel1_price(){
        return this.level1_price;
    }

    /**
     * 设置一级及以下限价
     * @param level1_price  一级及以下限价
     */
    public void setLevel1_price(Double level1_price){
        putField("level1_price");
        this.level1_price = level1_price;
    }
    /**
     * 获得二级限价
     * @return Double
     */
    public Double getLevel2_price(){
        return this.level2_price;
    }

    /**
     * 设置二级限价
     * @param level2_price  二级限价
     */
    public void setLevel2_price(Double level2_price){
        putField("level2_price");
        this.level2_price = level2_price;
    }
    /**
     * 获得三乙限价
     * @return Double
     */
    public Double getLevel3_price(){
        return this.level3_price;
    }

    /**
     * 设置三乙限价
     * @param level3_price  三乙限价
     */
    public void setLevel3_price(Double level3_price){
        putField("level3_price");
        this.level3_price = level3_price;
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
    public Long getBody_check_status(){
        return this.body_check_status;
    }

    /**
     * 设置状态
     * @param body_check_status  状态
     */
    public void setBody_check_status(Long body_check_status){
        putField("body_check_status");
        this.body_check_status = body_check_status;
    }

	public Date getReview_time() {
		return review_time;
	}

	public void setReview_time(Date review_time) {
		  putField("review_time");
		this.review_time = review_time;
	}

	public Long getReviewor() {
		return reviewor;
	}

	public void setReviewor(Long reviewor) {
	 putField("reviewor");
		this.reviewor = reviewor;
	}

	public String getSz_grade() {
		return sz_grade;
	}

	public void setSz_grade(String sz_grade) {
		 putField("sz_grade");
		this.sz_grade = sz_grade;
	}
	
	
}