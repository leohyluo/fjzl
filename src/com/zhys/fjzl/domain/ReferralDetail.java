package com.zhys.fjzl.domain;
import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.core.pojo.ReferralForm;
import com.zhys.util.DateUtils;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述：类
 */
public class ReferralDetail extends Base {

    @FieldAnnotation(comment = "主键", exp = false, imp = false, empty = true, len = 19, pk = true)
    private Long id;

    @FieldAnnotation(comment = "记录id", exp = true, imp = true, empty = true, len = 19)
    private String record_id;

    @FieldAnnotation(comment = "机构编码", exp = true, imp = true, empty = true, len = 50)
    private String org_id;

    @FieldAnnotation(comment = "目标单位", exp = true, imp = true, empty = true, len = 255)
    private String target_unit;

    @FieldAnnotation(comment = "患者姓名", exp = true, imp = true, empty = true, len = 10)
    private String target_name;

    @FieldAnnotation(comment = "患者性别", exp = true, imp = true, empty = true, len = 10)
    private String target_gender;

    @FieldAnnotation(comment = "患者年龄", exp = true, imp = true, empty = true, len = 255)
    private String target_age;

    @FieldAnnotation(comment = "初步诊断", exp = true, imp = true, empty = true, len = 255)
    private String diagnosis;

    @FieldAnnotation(comment = "现病史", exp = true, imp = true, empty = true, len = 255)
    private String history_illness;

    @FieldAnnotation(comment = "转出原因", exp = true, imp = true, empty = true, len = 255)
    private String reason;

    @FieldAnnotation(comment = "既往史", exp = true, imp = true, empty = true, len = 255)
    private String past_history;

    @FieldAnnotation(comment = "转诊医生", exp = true, imp = true, empty = true, len = 20)
    private String target_doctor;

    @FieldAnnotation(comment = "联系电话", exp = true, imp = true, empty = true, len = 20)
    private String target_mobile;

    @FieldAnnotation(comment = "机构名称", exp = true, imp = true, empty = true, len = 255)
    private String target_org_name;

    @FieldAnnotation(comment = "转诊日期", exp = true, imp = true, empty = true)
    private Date target_date;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    public ReferralDetail(ReferralForm form) {
    	BeanUtils.copyProperties(form, this);
    	try {
        	create_time = new Date();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }

    /**
     * 对象构造函数
     */
    public ReferralDetail() {
        super();
    }

    public ReferralDetail(String id) {
        this("id",id);
    }

    public ReferralDetail(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得主键
     * @return Long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * 设置主键
     * @param id  主键
     */
    public void setId(Long id){
        putField("id");
        this.id = id;
    }
    /**
     * 获得记录id
     * @return Long
     */
    public String getRecord_id(){
        return this.record_id;
    }

    /**
     * 设置记录id
     * @param record_id  记录id
     */
    public void setRecord_id(String record_id){
        putField("record_id");
        this.record_id = record_id;
    }
    /**
     * 获得机构编码
     * @return String
     */
    public String getOrg_id(){
        return this.org_id;
    }

    /**
     * 设置机构编码
     * @param org_id  机构编码
     */
    public void setOrg_id(String org_id){
        putField("org_id");
        this.org_id = org_id;
    }
    /**
     * 获得目标单位
     * @return String
     */
    public String getTarget_unit(){
        return this.target_unit;
    }

    /**
     * 设置目标单位
     * @param target_unit  目标单位
     */
    public void setTarget_unit(String target_unit){
        putField("target_unit");
        this.target_unit = target_unit;
    }
    /**
     * 获得患者姓名
     * @return String
     */
    public String getTarget_name(){
        return this.target_name;
    }

    /**
     * 设置患者姓名
     * @param target_name  患者姓名
     */
    public void setTarget_name(String target_name){
        putField("target_name");
        this.target_name = target_name;
    }
    /**
     * 获得患者性别
     * @return String
     */
    public String getTarget_gender(){
        return this.target_gender;
    }

    /**
     * 设置患者性别
     * @param target_gender  患者性别
     */
    public void setTarget_gender(String target_gender){
        putField("target_gender");
        this.target_gender = target_gender;
    }
    /**
     * 获得患者年龄
     * @return String
     */
    public String getTarget_age(){
        return this.target_age;
    }

    /**
     * 设置患者年龄
     * @param target_age  患者年龄
     */
    public void setTarget_age(String target_age){
        putField("target_age");
        this.target_age = target_age;
    }
    /**
     * 获得初步诊断
     * @return String
     */
    public String getDiagnosis(){
        return this.diagnosis;
    }

    /**
     * 设置初步诊断
     * @param diagnosis  初步诊断
     */
    public void setDiagnosis(String diagnosis){
        putField("diagnosis");
        this.diagnosis = diagnosis;
    }
    /**
     * 获得现病史
     * @return String
     */
    public String getHistory_illness(){
        return this.history_illness;
    }

    /**
     * 设置现病史
     * @param history_illness  现病史
     */
    public void setHistory_illness(String history_illness){
        putField("history_illness");
        this.history_illness = history_illness;
    }
    /**
     * 获得转出原因
     * @return String
     */
    public String getReason(){
        return this.reason;
    }

    /**
     * 设置转出原因
     * @param reason  转出原因
     */
    public void setReason(String reason){
        putField("reason");
        this.reason = reason;
    }
    /**
     * 获得既往史
     * @return String
     */
    public String getPast_history(){
        return this.past_history;
    }

    /**
     * 设置既往史
     * @param past_history  既往史
     */
    public void setPast_history(String past_history){
        putField("past_history");
        this.past_history = past_history;
    }
    /**
     * 获得转诊医生
     * @return String
     */
    public String getTarget_doctor(){
        return this.target_doctor;
    }

    /**
     * 设置转诊医生
     * @param target_doctor  转诊医生
     */
    public void setTarget_doctor(String target_doctor){
        putField("target_doctor");
        this.target_doctor = target_doctor;
    }
    /**
     * 获得联系电话
     * @return String
     */
    public String getTarget_mobile(){
        return this.target_mobile;
    }

    /**
     * 设置联系电话
     * @param target_mobile  联系电话
     */
    public void setTarget_mobile(String target_mobile){
        putField("target_mobile");
        this.target_mobile = target_mobile;
    }
    /**
     * 获得机构名称
     * @return String
     */
    public String getTarget_org_name(){
        return this.target_org_name;
    }

    /**
     * 设置机构名称
     * @param target_org_name  机构名称
     */
    public void setTarget_org_name(String target_org_name){
        putField("target_org_name");
        this.target_org_name = target_org_name;
    }
    /**
     * 获得转诊日期
     * @return Date
     */
    public Date getTarget_date(){
        return this.target_date;
    }

    /**
     * 设置转诊日期
     * @param target_date  转诊日期
     */
    public void setTarget_date(Date target_date){
        putField("target_date");
        this.target_date = target_date;
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
}