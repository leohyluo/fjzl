package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-07-31 <br/>
 * 描述：智能导诊疾病检查关系表类
 */
public class GmwsDiseaseDetail extends Base {

	private static final long serialVersionUID = 3879793028725540941L;

	@FieldAnnotation(comment = "智能导诊检查id", exp = true, imp = true, empty = true, len = 19)
    private Long id_;

    @FieldAnnotation(comment = "智能导诊疾病id", exp = true, imp = true, empty = true, len = 19)
    private Long dis_id_;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 10)
    private Long order_;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 500000)
    private String data_;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 10)
    private Long data_type_;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 19)
    private Long main_id;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 19)
    private Long xbid;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 200)
    private String dxid;

    @FieldAnnotation(comment = "", exp = true, imp = true, empty = true, len = 200)
    private String symbol_;


    /**
     * 智能导诊疾病检查关系表对象构造函数
     */
    public GmwsDiseaseDetail() {
        super();
    }

    public GmwsDiseaseDetail(String id_) {
        this("id_",id_);
    }

    public GmwsDiseaseDetail(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得智能导诊检查id
     * @return Long
     */
    public Long getId_(){
        return this.id_;
    }

    /**
     * 设置智能导诊检查id
     * @param id_  智能导诊检查id
     */
    public void setId_(Long id_){
        putField("id_");
        this.id_ = id_;
    }
    /**
     * 获得智能导诊疾病id
     * @return Long
     */
    public Long getDis_id_(){
        return this.dis_id_;
    }

    /**
     * 设置智能导诊疾病id
     * @param dis_id_  智能导诊疾病id
     */
    public void setDis_id_(Long dis_id_){
        putField("dis_id_");
        this.dis_id_ = dis_id_;
    }
    /**
     * 获得
     * @return Long
     */
    public Long getOrder_(){
        return this.order_;
    }

    /**
     * 设置
     * @param order_  
     */
    public void setOrder_(Long order_){
        putField("order_");
        this.order_ = order_;
    }
    /**
     * 获得
     * @return String
     */
    public String getData_(){
        return this.data_;
    }

    /**
     * 设置
     * @param data_  
     */
    public void setData_(String data_){
        putField("data_");
        this.data_ = data_;
    }
    /**
     * 获得
     * @return Long
     */
    public Long getData_type_(){
        return this.data_type_;
    }

    /**
     * 设置
     * @param data_type_  
     */
    public void setData_type_(Long data_type_){
        putField("data_type_");
        this.data_type_ = data_type_;
    }
    /**
     * 获得
     * @return Long
     */
    public Long getMain_id(){
        return this.main_id;
    }

    /**
     * 设置
     * @param main_id  
     */
    public void setMain_id(Long main_id){
        putField("main_id");
        this.main_id = main_id;
    }
    /**
     * 获得
     * @return Long
     */
    public Long getXbid(){
        return this.xbid;
    }

    /**
     * 设置
     * @param xbid  
     */
    public void setXbid(Long xbid){
        putField("xbid");
        this.xbid = xbid;
    }
    /**
     * 获得
     * @return String
     */
    public String getDxid(){
        return this.dxid;
    }

    /**
     * 设置
     * @param dxid  
     */
    public void setDxid(String dxid){
        putField("dxid");
        this.dxid = dxid;
    }
    /**
     * 获得
     * @return String
     */
    public String getSymbol_(){
        return this.symbol_;
    }

    /**
     * 设置
     * @param symbol_  
     */
    public void setSymbol_(String symbol_){
        putField("symbol_");
        this.symbol_ = symbol_;
    }
}