package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-01-16 <br/>
 * 描述：转诊规则表类
 */
public class Options extends Base {

    @FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "机构id", exp = true, imp = true, empty = true, len = 64)
    private String org_id;

    @FieldAnnotation(comment = "规则名称", exp = true, imp = true, empty = true, len = 200)
    private String referral_id;

    @FieldAnnotation(comment = "无", exp = true, imp = true, empty = true, len = 10)
    private Long _enable;

    @FieldAnnotation(comment = "序号", exp = true, imp = true, empty = true, len = 10)
    private Long _order;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 10)
    private Long creator;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改者", exp = true, imp = true, empty = true, len = 10)
    private Long last_updator;

     private String referralName;
	 private String creatorName;
	 private String last_updatorName;
	 private String ruleCode;	//转诊规则编码
	 
	 
    public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
	     putField("creatorName");
		this.creatorName = creatorName;
	}

	public String getLast_updatorName() {
		return last_updatorName;
	}

	public void setLast_updatorName(String last_updatorName) {
	     putField("last_updatorName");
		this.last_updatorName = last_updatorName;
	}

	/**
     * 转诊规则表对象构造函数
     */
    public Options() {
        super();
    }

    public Options(String id) {
        this("id",id);
    }

    public Options(String property, Object value) {
        this();
        init(property, value);
    }

    public String getReferralName() {
		return referralName;
	}

	public void setReferralName(String referralName) {
		   putField("referralName");
		this.referralName = referralName;
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

    public String getReferral_id() {
		return referral_id;
	}

	public void setReferral_id(String referral_id) {
	    putField("referral_id");
		this.referral_id = referral_id;
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
     * 获得序号
     * @return Long
     */
    public Long get_order(){
        return this._order;
    }

    /**
     * 设置序号
     * @param _order  序号
     */
    public void set_order(Long _order){
        putField("_order");
        this._order = _order;
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

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		putField("ruleCode");
		this.ruleCode = ruleCode;
	}
    
}