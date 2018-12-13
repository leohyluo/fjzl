package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.fjzl.annotation.FieldCheck;

import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：中药饮片类
 */
public class BChdrug extends Base {

	private static final long serialVersionUID = 6394722025450564113L;

	@FieldAnnotation(comment = "表id", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id_;
	
	@FieldAnnotation(comment = "药品编码", exp = true, imp = true, empty = true, len = 50)
	private String drug_code;

    @FieldAnnotation(comment = "药品名称", exp = true, imp = true, empty = true, len = 200)
    @FieldCheck(nullable = false)
    private String name_;

    @FieldAnnotation(comment = "药性", exp = true, imp = true, empty = true, len = 2000)
    private String phenotypictrait;

    @FieldAnnotation(comment = "功效。作用", exp = true, imp = true, empty = true, len = 2000)
    private String effect;

    @FieldAnnotation(comment = "应用", exp = true, imp = true, empty = true, len = 1000)
    private String precautions;

    @FieldAnnotation(comment = "用法用量", exp = true, imp = true, empty = true, len = 1000)
    private String reposit;

    @FieldAnnotation(comment = "注意事项", exp = true, imp = true, empty = true, len = 1000)
    private String standardcontrains;

    @FieldAnnotation(comment = "化学成分", exp = true, imp = true, empty = true, len = 1000)
    private String chemicalcomposition;

    @FieldAnnotation(comment = "药理作用", exp = true, imp = true, empty = true, len = 1000)
    private String pharmacologicalactions;

    @FieldAnnotation(comment = "不良反应", exp = true, imp = true, empty = true, len = 2000)
    private String adversereaction;

    @FieldAnnotation(comment = "助记符", exp = true, imp = true, empty = true, len = 300)
    private String symbol;

    @FieldAnnotation(comment = "收费类别", exp = true, imp = true, empty = true, len = 20)
    private String charge_type;

    @FieldAnnotation(comment = "收费项目等级", exp = true, imp = true, empty = true, len = 10)
    private String charge_grade;

    @FieldAnnotation(comment = "药品等级", exp = true, imp = true, empty = true, len = 10)
    @FieldCheck(nullable = false)
    private String grade;

    @FieldAnnotation(comment = "数据来源", exp = true, imp = true, empty = true, len = 500)
    private String data_sources;

    @FieldAnnotation(comment = "审核状态", exp = true, imp = true, empty = true, len = 10)
    private Long chdrug_status;
    @FieldAnnotation(comment = "是否启用", exp = true, imp = true, empty = true, len = 10)
    private Long _enable;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建人", exp = true, imp = true, empty = true, len = 10)
    private Long creator;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改人", exp = true, imp = true, empty = true, len = 10)
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
     * 中药饮片对象构造函数
     */
    public BChdrug() {
        super();
    }

    public BChdrug(String id_) {
        this("id_",id_);
    }

    public BChdrug(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得表id
     * @return Long
     */
    public Long getId_(){
        return this.id_;
    }

    /**
     * 设置表id
     * @param id_  表id
     */
    public void setId_(Long id_){
        putField("id_");
        this.id_ = id_;
    }
    /**
     * 获得药品名称
     * @return String
     */
    public String getName_(){
        return this.name_;
    }

    /**
     * 设置药品名称
     * @param name_  药品名称
     */
    public void setName_(String name_){
        putField("name_");
        this.name_ = name_;
    }
    /**
     * 获得药性
     * @return String
     */
    public String getPhenotypictrait(){
        return this.phenotypictrait;
    }

    /**
     * 设置药性
     * @param phenotypictrait  药性
     */
    public void setPhenotypictrait(String phenotypictrait){
        putField("phenotypictrait");
        this.phenotypictrait = phenotypictrait;
    }
    /**
     * 获得功效。作用
     * @return String
     */
    public String getEffect(){
        return this.effect;
    }

    /**
     * 设置功效。作用
     * @param effect  功效。作用
     */
    public void setEffect(String effect){
        putField("effect");
        this.effect = effect;
    }
    /**
     * 获得应用
     * @return String
     */
    public String getPrecautions(){
        return this.precautions;
    }

    /**
     * 设置应用
     * @param precautions  应用
     */
    public void setPrecautions(String precautions){
        putField("precautions");
        this.precautions = precautions;
    }
    /**
     * 获得用法用量
     * @return String
     */
    public String getReposit(){
        return this.reposit;
    }

    /**
     * 设置用法用量
     * @param reposit  用法用量
     */
    public void setReposit(String reposit){
        putField("reposit");
        this.reposit = reposit;
    }
    /**
     * 获得注意事项
     * @return String
     */
    public String getStandardcontrains(){
        return this.standardcontrains;
    }

    /**
     * 设置注意事项
     * @param standardcontrains  注意事项
     */
    public void setStandardcontrains(String standardcontrains){
        putField("standardcontrains");
        this.standardcontrains = standardcontrains;
    }
    /**
     * 获得化学成分
     * @return String
     */
    public String getChemicalcomposition(){
        return this.chemicalcomposition;
    }

    /**
     * 设置化学成分
     * @param chemicalcomposition  化学成分
     */
    public void setChemicalcomposition(String chemicalcomposition){
        putField("chemicalcomposition");
        this.chemicalcomposition = chemicalcomposition;
    }
    /**
     * 获得药理作用
     * @return String
     */
    public String getPharmacologicalactions(){
        return this.pharmacologicalactions;
    }

    /**
     * 设置药理作用
     * @param pharmacologicalactions  药理作用
     */
    public void setPharmacologicalactions(String pharmacologicalactions){
        putField("pharmacologicalactions");
        this.pharmacologicalactions = pharmacologicalactions;
    }
    /**
     * 获得不良反应
     * @return String
     */
    public String getAdversereaction(){
        return this.adversereaction;
    }

    /**
     * 设置不良反应
     * @param adversereaction  不良反应
     */
    public void setAdversereaction(String adversereaction){
        putField("adversereaction");
        this.adversereaction = adversereaction;
    }
    /**
     * 获得助记符
     * @return String
     */
    public String getSymbol(){
        return this.symbol;
    }

    /**
     * 设置助记符
     * @param symbol  助记符
     */
    public void setSymbol(String symbol){
        putField("symbol");
        this.symbol = symbol;
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
    
    @FieldAnnotation(comment = "atc1", exp = true, imp = true, empty = true, len = 50)
    private String atc1;

    @FieldAnnotation(comment = "atc2", exp = true, imp = true, empty = true, len = 50)
    private String atc2;

    @FieldAnnotation(comment = "atc3", exp = true, imp = true, empty = true, len = 50)
    private String atc3;

    @FieldAnnotation(comment = "atc4", exp = true, imp = true, empty = true, len = 50)
    private String atc4;
    
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
     * 获得数据来源
     * @return String
     */
    public String getData_sources(){
        return this.data_sources;
    }

    /**
     * 设置数据来源
     * @param data_sources  数据来源
     */
    public void setData_sources(String data_sources){
        putField("data_sources");
        this.data_sources = data_sources;
    }
    /**
     * 获得审核状态
     * @return Long
     */
    public Long getChdrug_status(){
        return this.chdrug_status;
    }

    /**
     * 设置审核状态
     * @param chdrug_status  审核状态
     */
    public void setChdrug_status(Long chdrug_status){
        putField("chdrug_status");
        this.chdrug_status = chdrug_status;
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
     * 获得创建人
     * @return Long
     */
    public Long getCreator(){
        return this.creator;
    }

    /**
     * 设置创建人
     * @param createor  创建人
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
    public Long getLast_updator(){
        return this.last_updator;
    }

    /**
     * 设置最后修改人
     * @param last_updator  最后修改人
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
}