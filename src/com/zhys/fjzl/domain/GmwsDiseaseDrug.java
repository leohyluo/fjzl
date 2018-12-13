package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-07-31 <br/>
 * 描述：智能导诊疾病药品关系表类
 */
public class GmwsDiseaseDrug extends Base {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8440085750622190684L;

	@FieldAnnotation(comment = "智能导诊疾病id", exp = true, imp = true, empty = true, len = 19)
    private Long gmws_disease_id;

    @FieldAnnotation(comment = "智能导诊疾病名称", exp = true, imp = true, empty = true, len = 50)
    private String gmws_disease_name;

    @FieldAnnotation(comment = "治疗原则疾病id", exp = true, imp = true, empty = true, len = 10)
    private Long zlyz_disease_id;

    @FieldAnnotation(comment = "治疗原则疾病名称", exp = true, imp = true, empty = true, len = 200)
    private String zlyz_disease_name;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 50)
    private String zlyz_ypid;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 50)
    private String zlyz_drugcode;

    @FieldAnnotation(comment = "智能导诊药品编码", exp = true, imp = true, empty = true, len = 20)
    private String gmws_drug_code;

    @FieldAnnotation(comment = "智能导诊药品名称", exp = true, imp = true, empty = true, len = 200)
    private String gmws_drug_name;


    /**
     * 智能导诊疾病药品关系表对象构造函数
     */
    public GmwsDiseaseDrug() {
        super();
    }

    public GmwsDiseaseDrug(String gmws_disease_id) {
        this("gmws_disease_id",gmws_disease_id);
    }

    public GmwsDiseaseDrug(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得智能导诊疾病id
     * @return Long
     */
    public Long getGmws_disease_id(){
        return this.gmws_disease_id;
    }

    /**
     * 设置智能导诊疾病id
     * @param gmws_disease_id  智能导诊疾病id
     */
    public void setGmws_disease_id(Long gmws_disease_id){
        putField("gmws_disease_id");
        this.gmws_disease_id = gmws_disease_id;
    }
    /**
     * 获得智能导诊疾病名称
     * @return String
     */
    public String getGmws_disease_name(){
        return this.gmws_disease_name;
    }

    /**
     * 设置智能导诊疾病名称
     * @param gmws_disease_name  智能导诊疾病名称
     */
    public void setGmws_disease_name(String gmws_disease_name){
        putField("gmws_disease_name");
        this.gmws_disease_name = gmws_disease_name;
    }
    /**
     * 获得治疗原则疾病id
     * @return Long
     */
    public Long getZlyz_disease_id(){
        return this.zlyz_disease_id;
    }

    /**
     * 设置治疗原则疾病id
     * @param zlyz_disease_id  治疗原则疾病id
     */
    public void setZlyz_disease_id(Long zlyz_disease_id){
        putField("zlyz_disease_id");
        this.zlyz_disease_id = zlyz_disease_id;
    }
    /**
     * 获得治疗原则疾病名称
     * @return String
     */
    public String getZlyz_disease_name(){
        return this.zlyz_disease_name;
    }

    /**
     * 设置治疗原则疾病名称
     * @param zlyz_disease_name  治疗原则疾病名称
     */
    public void setZlyz_disease_name(String zlyz_disease_name){
        putField("zlyz_disease_name");
        this.zlyz_disease_name = zlyz_disease_name;
    }
    /**
     * 获得
     * @return String
     */
    public String getZlyz_ypid(){
        return this.zlyz_ypid;
    }

    /**
     * 设置
     * @param zlyz_ypid  
     */
    public void setZlyz_ypid(String zlyz_ypid){
        putField("zlyz_ypid");
        this.zlyz_ypid = zlyz_ypid;
    }
    /**
     * 获得
     * @return String
     */
    public String getZlyz_drugcode(){
        return this.zlyz_drugcode;
    }

    /**
     * 设置
     * @param zlyz_drugcode  
     */
    public void setZlyz_drugcode(String zlyz_drugcode){
        putField("zlyz_drugcode");
        this.zlyz_drugcode = zlyz_drugcode;
    }
    /**
     * 获得智能导诊药品编码
     * @return String
     */
    public String getGmws_drug_code(){
        return this.gmws_drug_code;
    }

    /**
     * 设置智能导诊药品编码
     * @param gmws_drug_code  智能导诊药品编码
     */
    public void setGmws_drug_code(String gmws_drug_code){
        putField("gmws_drug_code");
        this.gmws_drug_code = gmws_drug_code;
    }
    /**
     * 获得智能导诊药品名称
     * @return String
     */
    public String getGmws_drug_name(){
        return this.gmws_drug_name;
    }

    /**
     * 设置智能导诊药品名称
     * @param gmws_drug_name  智能导诊药品名称
     */
    public void setGmws_drug_name(String gmws_drug_name){
        putField("gmws_drug_name");
        this.gmws_drug_name = gmws_drug_name;
    }
}