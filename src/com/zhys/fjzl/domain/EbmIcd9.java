package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库icd9表类
 */
public class EbmIcd9 extends Base {

	private static final long serialVersionUID = 3808492684596253710L;

	@FieldAnnotation(comment = "主键，自增长", exp = false, imp = false, empty = true, len = 10, pk = true)
    private Long id;

    @FieldAnnotation(comment = "icd9编码", exp = true, imp = true, empty = true, len = 20)
    private String icd9;

    @FieldAnnotation(comment = "icd9名称", exp = true, imp = true, empty = true, len = 50)
    private String icd9_name;

    @FieldAnnotation(comment = "数据来源(导入/手动添加)", exp = true, imp = true, empty = true, len = 10)
    private Long source;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date create_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 10)
    private Long creator;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date last_update_time;

    @FieldAnnotation(comment = "最后修改者", exp = true, imp = true, empty = true, len = 10)
    private Long last_updator;


    /**
     * 基础知识库icd9表对象构造函数
     */
    public EbmIcd9() {
        super();
    }

    public EbmIcd9(String id) {
        this("id",id);
    }

    public EbmIcd9(String property, Object value) {
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
     * 获得icd9编码
     * @return String
     */
    public String getIcd9(){
        return this.icd9;
    }

    /**
     * 设置icd9编码
     * @param icd9  icd9编码
     */
    public void setIcd9(String icd9){
        putField("icd9");
        this.icd9 = icd9;
    }
    /**
     * 获得icd9名称
     * @return String
     */
    public String getIcd9_name(){
        return this.icd9_name;
    }

    /**
     * 设置icd9名称
     * @param icd9_name  icd9名称
     */
    public void setIcd9_name(String icd9_name){
        putField("icd9_name");
        this.icd9_name = icd9_name;
    }
    /**
     * 获得数据来源(导入/手动添加)
     * @return Long
     */
    public Long getSource(){
        return this.source;
    }

    /**
     * 设置数据来源(导入/手动添加)
     * @param source  数据来源(导入/手动添加)
     */
    public void setSource(Long source){
        putField("source");
        this.source = source;
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