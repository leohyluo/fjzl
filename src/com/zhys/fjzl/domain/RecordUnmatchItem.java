package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述：类
 */
public class RecordUnmatchItem extends Base {

	private static final long serialVersionUID = 738150368352381625L;

	@FieldAnnotation(comment = "主键", exp = false, imp = false, empty = true, len = 19, pk = true)
    private Long id;

    @FieldAnnotation(comment = "机构编码", exp = true, imp = true, empty = true, len = 20)
    private String orgid;

    @FieldAnnotation(comment = "记录id", exp = true, imp = true, empty = true, len = 19)
    private Long recordid;

    @FieldAnnotation(comment = "检测不通过项id", exp = true, imp = true, empty = true, len = 50)
    private String illegalid;
    
    @FieldAnnotation(comment = "检测不通过项名称", exp = true, imp = true, empty = true, len = 50)
    private String illegalName;
    
    @FieldAnnotation(comment = "所属医院等级", exp = true, imp = true, empty = true, len = 50)
    private String expectGrade;

    @FieldAnnotation(comment = "1疾病 2药品 3检查", exp = true, imp = true, empty = true, len = 19)
    private Integer illegatype;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date createtime;


    /**
     * 对象构造函数
     */
    public RecordUnmatchItem() {
        super();
    }

    public RecordUnmatchItem(String id) {
        this("id",id);
    }

    public RecordUnmatchItem(String property, Object value) {
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
     * 获得机构编码
     * @return String
     */
    public String getOrgid(){
        return this.orgid;
    }

    /**
     * 设置机构编码
     * @param orgid  机构编码
     */
    public void setOrgid(String orgid){
        putField("orgid");
        this.orgid = orgid;
    }
    /**
     * 获得记录id
     * @return Long
     */
    public Long getRecordid(){
        return this.recordid;
    }

    /**
     * 设置记录id
     * @param recordid  记录id
     */
    public void setRecordid(Long recordid){
        putField("recordid");
        this.recordid = recordid;
    }
    /**
     * 获得检测不通过项id
     * @return String
     */
    public String getIllegalid(){
        return this.illegalid;
    }

    /**
     * 设置检测不通过项id
     * @param illegalid  检测不通过项id
     */
    public void setIllegalid(String illegalid){
        putField("illegalid");
        this.illegalid = illegalid;
    }
    /**
     * 获得1疾病 2药品 3检查
     * @return Long
     */
    public Integer getIllegatype(){
        return this.illegatype;
    }

    /**
     * 设置1疾病 2药品 3检查
     * @param illegatype  1疾病 2药品 3检查
     */
    public void setIllegatype(Integer illegatype){
        putField("illegatype");
        this.illegatype = illegatype;
    }
    /**
     * 获得创建时间
     * @return Date
     */
    public Date getCreatetime(){
        return this.createtime;
    }

    /**
     * 设置创建时间
     * @param createtime  创建时间
     */
    public void setCreatetime(Date createtime){
        putField("createtime");
        this.createtime = createtime;
    }

	public String getIllegalName() {
		return illegalName;
	}

	public void setIllegalName(String illegalName) {
		putField("illegalName");
		this.illegalName = illegalName;
	}

	public String getExpectGrade() {
		return expectGrade;
	}

	public void setExpectGrade(String expectGrade) {
		putField("expectGrade");
		this.expectGrade = expectGrade;
	}
    
    
}