package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-02-06 <br/>
 * 描述：规则表类
 */
public class ReferralRule extends Base {

    @FieldAnnotation(comment = "主键", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "名称", exp = true, imp = true, empty = true, len = 500)
    private String name;

    @FieldAnnotation(comment = "规则编号", exp = true, imp = true, empty = true, len = 10)
    private Long code;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 64)
    private String creator;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "最后修改者", exp = true, imp = true, empty = true, len = 64)
    private String last_update_user;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;


    /**
     * 规则表对象构造函数
     */
    public ReferralRule() {
        super();
    }

    public ReferralRule(String id) {
        this("id",id);
    }

    public ReferralRule(String property, Object value) {
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
     * 获得名称
     * @return String
     */
    public String getName(){
        return this.name;
    }

    /**
     * 设置名称
     * @param name  名称
     */
    public void setName(String name){
        putField("name");
        this.name = name;
    }
    /**
     * 获得规则编号
     * @return Long
     */
    public Long getCode(){
        return this.code;
    }

    /**
     * 设置规则编号
     * @param code  规则编号
     */
    public void setCode(Long code){
        putField("code");
        this.code = code;
    }
    /**
     * 获得创建者
     * @return String
     */
    public String getCreator(){
        return this.creator;
    }

    /**
     * 设置创建者
     * @param creator  创建者
     */
    public void setCreator(String creator){
        putField("creator");
        this.creator = creator;
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
     * 获得最后修改者
     * @return String
     */
    public String getLast_update_user(){
        return this.last_update_user;
    }

    /**
     * 设置最后修改者
     * @param last_update_user  最后修改者
     */
    public void setLast_update_user(String last_update_user){
        putField("last_update_user");
        this.last_update_user = last_update_user;
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
}