package com.zhys.sys.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-27 <br/>
 * 描述：机构管理类
 */
public class Org extends Base {

    @FieldAnnotation(comment = "机构id", exp = false, imp = false, empty = true, len = 64, pk = true)
    private String so_id;

    @FieldAnnotation(comment = "机构名称机构部门表", exp = true, imp = true, empty = true, len = 100)
    private String so_name;

    @FieldAnnotation(comment = "上级机构id：0代表根级机构", exp = true, imp = true, empty = true, len = 64)
    private String so_parentid;

    @FieldAnnotation(comment = "机构排序", exp = true, imp = true, empty = true, len = 10)
    private Integer so_order;

    @FieldAnnotation(comment = "机构编码", exp = true, imp = true, empty = true, len = 30)
    private String so_code;

    @FieldAnnotation(comment = "联系电话", exp = true, imp = true, empty = true, len = 100)
    private String so_contact;

    @FieldAnnotation(comment = "电子邮箱", exp = true, imp = true, empty = true, len = 100)
    private String so_email;

    @FieldAnnotation(comment = "邮政编码", exp = true, imp = true, empty = true, len = 20)
    private String so_post;

    @FieldAnnotation(comment = "单位权限", exp = true, imp = true, empty = true, len = 2000)
    private String so_purview;

    @FieldAnnotation(comment = "机构类型(医联体/医院/社康)", exp = true, imp = true, empty = true, len = 20)
    private String so_type;

    @FieldAnnotation(comment = "拼音助记符", exp = true, imp = true, empty = true, len = 255)
    private String so_symbol;

    @FieldAnnotation(comment = "联系人", exp = true, imp = true, empty = true, len = 64)
    private String so_contactor;

    @FieldAnnotation(comment = "省份", exp = true, imp = true, empty = true, len = 50)
    private String so_province;

    @FieldAnnotation(comment = "城市", exp = true, imp = true, empty = true, len = 50)
    private String so_city;

    @FieldAnnotation(comment = "区", exp = true, imp = true, empty = true, len = 50)
    private String so_area;

    @FieldAnnotation(comment = "街道", exp = true, imp = true, empty = true, len = 255)
    private String so_street;

    @FieldAnnotation(comment = "经度", exp = true, imp = true, empty = true, len = 255)
    private String so_longitude;

    @FieldAnnotation(comment = "纬度", exp = true, imp = true, empty = true, len = 255)
    private String so_latitude;

    @FieldAnnotation(comment = "横坐标", exp = true, imp = true, empty = true, len = 50)
    private String so_xcoordinate;

    @FieldAnnotation(comment = "纵坐标", exp = true, imp = true, empty = true, len = 50)
    private String so_ycoordinate;

    @FieldAnnotation(comment = "机构地址", exp = true, imp = true, empty = true, len = 255)
    private String so_address;

    @FieldAnnotation(comment = "机构等级", exp = true, imp = true, empty = true, len = 10)
    private String so_grade;

    @FieldAnnotation(comment = "机构性质(市属/区属/社会办)", exp = true, imp = true, empty = true, len = 10)
    private String so_nature;

    @FieldAnnotation(comment = "机构状态(已审核/未审核)", exp = true, imp = true, empty = true, len = 10)
    private String so_status;

    @FieldAnnotation(comment = "是否是指导单位", exp = true, imp = true, empty = true, len = 10)
    private String so_leader_org_flag;

    @FieldAnnotation(comment = "是否为牵头单位", exp = true, imp = true, empty = true, len = 10)
    private String so_guide_org_flag;

    @FieldAnnotation(comment = "是否启用", exp = true, imp = true, empty = true, len = 10)
    private String so_enable;

    @FieldAnnotation(comment = "创建时间", exp = true, imp = true, empty = true)
    private Date so_create_time;

    @FieldAnnotation(comment = "最后修改时间", exp = true, imp = true, empty = true)
    private Date so_last_upd_time;

    @FieldAnnotation(comment = "创建者", exp = true, imp = true, empty = true, len = 64)
    private String so_creator;

    @FieldAnnotation(comment = "最后修改者", exp = true, imp = true, empty = true, len = 64)
    private String so_last_update_user;

    @FieldAnnotation(comment = "审核时间", exp = true, imp = true, empty = true)
    private Date so_review_time;

    @FieldAnnotation(comment = "审核者", exp = true, imp = true, empty = true, len = 64)
    private String so_reviewor;
    
    @FieldAnnotation(comment = "机构编码", exp = true, imp = true, empty = true, len = 2000)
    private String so_code2;

	private String so_parentname;
    private String so_child;
    private String so_used;
    private String so_creatorName;
    private String so_lastUpdatorName;
    private String so_revieworName;

    /**
     * 机构管理对象构造函数
     */
    public Org() {
        super();
    }
    
    public Org(Long so_id){
        this("so_id",so_id);
    }
    
    public Org(String so_id) {
        this("so_id",so_id);
    }

    public Org(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得机构id
     * @return String
     */
    public String getSo_id(){
        return this.so_id;
    }

    /**
     * 设置机构id
     * @param so_id  机构id
     */
    public void setSo_id(String so_id){
        putField("so_id");
        this.so_id = so_id;
    }
    /**
     * 获得机构名称机构部门表
     * @return String
     */
    public String getSo_name(){
        return this.so_name;
    }

    /**
     * 设置机构名称机构部门表
     * @param so_name  机构名称机构部门表
     */
    public void setSo_name(String so_name){
        putField("so_name");
        this.so_name = so_name;
    }
    /**
     * 获得上级机构id：0代表根级机构
     * @return String
     */
    public String getSo_parentid(){
        return this.so_parentid;
    }

    /**
     * 设置上级机构id：0代表根级机构
     * @param so_parentid  上级机构id：0代表根级机构
     */
    public void setSo_parentid(String so_parentid){
        putField("so_parentid");
        this.so_parentid = so_parentid;
    }
    /**
     * 获得机构排序
     * @return Long
     */
    public Integer getSo_order(){
        return this.so_order;
    }

    /**
     * 设置机构排序
     * @param so_order  机构排序
     */
    public void setSo_order(Integer so_order){
        putField("so_order");
        this.so_order = so_order;
    }
    /**
     * 获得机构编码
     * @return String
     */
    public String getSo_code(){
        return this.so_code;
    }

    /**
     * 设置机构编码
     * @param so_code  机构编码
     */
    public void setSo_code(String so_code){
        putField("so_code");
        this.so_code = so_code;
    }
    /**
     * 获得联系电话
     * @return String
     */
    public String getSo_contact(){
        return this.so_contact;
    }

    /**
     * 设置联系电话
     * @param so_contact  联系电话
     */
    public void setSo_contact(String so_contact){
        putField("so_contact");
        this.so_contact = so_contact;
    }
    /**
     * 获得电子邮箱
     * @return String
     */
    public String getSo_email(){
        return this.so_email;
    }

    /**
     * 设置电子邮箱
     * @param so_email  电子邮箱
     */
    public void setSo_email(String so_email){
        putField("so_email");
        this.so_email = so_email;
    }
    /**
     * 获得邮政编码
     * @return String
     */
    public String getSo_post(){
        return this.so_post;
    }

    /**
     * 设置邮政编码
     * @param so_post  邮政编码
     */
    public void setSo_post(String so_post){
        putField("so_post");
        this.so_post = so_post;
    }
    /**
     * 获得单位权限
     * @return String
     */
    public String getSo_purview(){
        return this.so_purview;
    }

    /**
     * 设置单位权限
     * @param so_purview  单位权限
     */
    public void setSo_purview(String so_purview){
        putField("so_purview");
        this.so_purview = so_purview;
    }
    /**
     * 获得机构类型(医联体/医院/社康)
     * @return String
     */
    public String getSo_type(){
        return this.so_type;
    }

    /**
     * 设置机构类型(医联体/医院/社康)
     * @param so_type  机构类型(医联体/医院/社康)
     */
    public void setSo_type(String so_type){
        putField("so_type");
        this.so_type = so_type;
    }
    /**
     * 获得拼音助记符
     * @return String
     */
    public String getSo_symbol(){
        return this.so_symbol;
    }

    /**
     * 设置拼音助记符
     * @param so_symbol  拼音助记符
     */
    public void setSo_symbol(String so_symbol){
        putField("so_symbol");
        this.so_symbol = so_symbol;
    }
    /**
     * 获得联系人
     * @return String
     */
    public String getSo_contactor(){
        return this.so_contactor;
    }

    /**
     * 设置联系人
     * @param so_contactor  联系人
     */
    public void setSo_contactor(String so_contactor){
        putField("so_contactor");
        this.so_contactor = so_contactor;
    }
    /**
     * 获得省份
     * @return String
     */
    public String getSo_province(){
        return this.so_province;
    }

    /**
     * 设置省份
     * @param so_province  省份
     */
    public void setSo_province(String so_province){
        putField("so_province");
        this.so_province = so_province;
    }
    /**
     * 获得城市
     * @return String
     */
    public String getSo_city(){
        return this.so_city;
    }

    /**
     * 设置城市
     * @param so_city  城市
     */
    public void setSo_city(String so_city){
        putField("so_city");
        this.so_city = so_city;
    }
    /**
     * 获得区
     * @return String
     */
    public String getSo_area(){
        return this.so_area;
    }

    /**
     * 设置区
     * @param so_area  区
     */
    public void setSo_area(String so_area){
        putField("so_area");
        this.so_area = so_area;
    }
    /**
     * 获得街道
     * @return String
     */
    public String getSo_street(){
        return this.so_street;
    }

    /**
     * 设置街道
     * @param so_street  街道
     */
    public void setSo_street(String so_street){
        putField("so_street");
        this.so_street = so_street;
    }
    /**
     * 获得经度
     * @return String
     */
    public String getSo_longitude(){
        return this.so_longitude;
    }

    /**
     * 设置经度
     * @param so_longitude  经度
     */
    public void setSo_longitude(String so_longitude){
        putField("so_longitude");
        this.so_longitude = so_longitude;
    }
    /**
     * 获得纬度
     * @return String
     */
    public String getSo_latitude(){
        return this.so_latitude;
    }

    /**
     * 设置纬度
     * @param so_latitude  纬度
     */
    public void setSo_latitude(String so_latitude){
        putField("so_latitude");
        this.so_latitude = so_latitude;
    }
    /**
     * 获得横坐标
     * @return String
     */
    public String getSo_xcoordinate(){
        return this.so_xcoordinate;
    }

    /**
     * 设置横坐标
     * @param so_xcoordinate  横坐标
     */
    public void setSo_xcoordinate(String so_xcoordinate){
        putField("so_xcoordinate");
        this.so_xcoordinate = so_xcoordinate;
    }
    /**
     * 获得纵坐标
     * @return String
     */
    public String getSo_ycoordinate(){
        return this.so_ycoordinate;
    }

    /**
     * 设置纵坐标
     * @param so_ycoordinate  纵坐标
     */
    public void setSo_ycoordinate(String so_ycoordinate){
        putField("so_ycoordinate");
        this.so_ycoordinate = so_ycoordinate;
    }
    /**
     * 获得机构地址
     * @return String
     */
    public String getSo_address(){
        return this.so_address;
    }

    /**
     * 设置机构地址
     * @param so_address  机构地址
     */
    public void setSo_address(String so_address){
        putField("so_address");
        this.so_address = so_address;
    }
    /**
     * 获得机构等级
     * @return String
     */
    public String getSo_grade(){
        return this.so_grade;
    }

    /**
     * 设置机构等级
     * @param so_grade  机构等级
     */
    public void setSo_grade(String so_grade){
        putField("so_grade");
        this.so_grade = so_grade;
    }
    /**
     * 获得机构性质(市属/区属/社会办)
     * @return String
     */
    public String getSo_nature(){
        return this.so_nature;
    }

    /**
     * 设置机构性质(市属/区属/社会办)
     * @param so_nature  机构性质(市属/区属/社会办)
     */
    public void setSo_nature(String so_nature){
        putField("so_nature");
        this.so_nature = so_nature;
    }
    /**
     * 获得机构状态(已审核/未审核)
     * @return String
     */
    public String getSo_status(){
        return this.so_status;
    }

    /**
     * 设置机构状态(已审核/未审核)
     * @param so_status  机构状态(已审核/未审核)
     */
    public void setSo_status(String so_status){
        putField("so_status");
        this.so_status = so_status;
    }
    /**
     * 获得是否是指导单位
     * @return String
     */
    public String getSo_leader_org_flag(){
        return this.so_leader_org_flag;
    }

    /**
     * 设置是否是指导单位
     * @param so_leader_org_flag  是否是指导单位
     */
    public void setSo_leader_org_flag(String so_leader_org_flag){
        putField("so_leader_org_flag");
        this.so_leader_org_flag = so_leader_org_flag;
    }
    /**
     * 获得是否为牵头单位
     * @return String
     */
    public String getSo_guide_org_flag(){
        return this.so_guide_org_flag;
    }

    /**
     * 设置是否为牵头单位
     * @param so_guide_org_flag  是否为牵头单位
     */
    public void setSo_guide_org_flag(String so_guide_org_flag){
        putField("so_guide_org_flag");
        this.so_guide_org_flag = so_guide_org_flag;
    }
    /**
     * 获得是否启用
     * @return String
     */
    public String getSo_enable(){
        return this.so_enable;
    }

    /**
     * 设置是否启用
     * @param so_enable  是否启用
     */
    public void setSo_enable(String so_enable){
        putField("so_enable");
        this.so_enable = so_enable;
    }
    /**
     * 获得创建时间
     * @return Date
     */
    public Date getSo_create_time(){
        return this.so_create_time;
    }

    /**
     * 设置创建时间
     * @param so_create_time  创建时间
     */
    public void setSo_create_time(Date so_create_time){
        putField("so_create_time");
        this.so_create_time = so_create_time;
    }
    /**
     * 获得最后修改时间
     * @return Date
     */
    public Date getSo_last_upd_time(){
        return this.so_last_upd_time;
    }

    /**
     * 设置最后修改时间
     * @param so_last_upd_time  最后修改时间
     */
    public void setSo_last_upd_time(Date so_last_upd_time){
        putField("so_last_upd_time");
        this.so_last_upd_time = so_last_upd_time;
    }
    /**
     * 获得创建者
     * @return String
     */
    public String getSo_creator(){
        return this.so_creator;
    }

    /**
     * 设置创建者
     * @param so_creator  创建者
     */
    public void setSo_creator(String so_creator){
        putField("so_creator");
        this.so_creator = so_creator;
    }
    /**
     * 获得最后修改者
     * @return String
     */
    public String getSo_last_update_user(){
        return this.so_last_update_user;
    }

    /**
     * 设置最后修改者
     * @param so_last_update_user  最后修改者
     */
    public void setSo_last_update_user(String so_last_update_user){
        putField("so_last_update_user");
        this.so_last_update_user = so_last_update_user;
    }
    /**
     * 获得审核时间
     * @return Date
     */
    public Date getSo_review_time(){
        return this.so_review_time;
    }

    /**
     * 设置审核时间
     * @param so_review_time  审核时间
     */
    public void setSo_review_time(Date so_review_time){
        putField("so_review_time");
        this.so_review_time = so_review_time;
    }
    /**
     * 获得审核者
     * @return String
     */
    public String getSo_reviewor(){
        return this.so_reviewor;
    }

    /**
     * 设置审核者
     * @param so_reviewor  审核者
     */
    public void setSo_reviewor(String so_reviewor){
        putField("so_reviewor");
        this.so_reviewor = so_reviewor;
    }
	
		public String getSo_parentname() {
        return so_parentname;
    }

    public void setSo_parentname(String so_parentname) {
        putField("so_parentname");
        this.so_parentname = so_parentname;
    }
	
	public void setSo_child(String so_child) {
        putField("so_child");
        this.so_child = so_child;
    }
	
	public String getSo_child() {
        return so_child;
    }
	
	 public void setSo_used(String so_used) {
        putField("so_used");
        this.so_used = so_used;
    }

    public String getSo_used() {
        return so_used;
    }

	public String getSo_creatorName() {
		return so_creatorName;
	}

	public void setSo_creatorName(String so_creatorName) {
		putField("so_creatorName");
		this.so_creatorName = so_creatorName;
	}

	public String getSo_lastUpdatorName() {
		return so_lastUpdatorName;
	}

	public void setSo_lastUpdatorName(String so_lastUpdatorName) {
		putField("so_lastUpdatorName");
		this.so_lastUpdatorName = so_lastUpdatorName;
	}

	public String getSo_revieworName() {
		return so_revieworName;
	}

	public void setSo_revieworName(String so_revieworName) {
		putField("so_revieworName");
		this.so_revieworName = so_revieworName;
	}

	public String getSo_code2() {
		return so_code2;
	}

	public void setSo_code2(String so_code2) {
		putField("so_code2");
		this.so_code2 = so_code2;
	}
}