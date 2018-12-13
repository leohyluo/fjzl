package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库疾病与icd9关系表类
 */
public class DiseaseIcd9Relation extends Base {

	private static final long serialVersionUID = -8097300347840092269L;

	@FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "疾病id", exp = true, imp = true, empty = true, len = 64)
    private String disease_id;

    @FieldAnnotation(comment = "icd9id", exp = true, imp = true, empty = true, len = 64)
    private String icd9_id;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 10)
    private Long creator;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改者", exp = true, imp = true, empty = true, len = 10)
    private Long last_updator;


    /**
     * 基础知识库疾病与icd9关系表对象构造函数
     */
    public DiseaseIcd9Relation() {
        super();
    }

    public DiseaseIcd9Relation(String id) {
        this("id",id);
    }

    public DiseaseIcd9Relation(String property, Object value) {
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
     * 获得icd9id
     * @return String
     */
    public String getIcd9_id(){
        return this.icd9_id;
    }

    /**
     * 设置icd9id
     * @param icd9_id  icd9id
     */
    public void setIcd9_id(String icd9_id){
        putField("icd9_id");
        this.icd9_id = icd9_id;
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
}