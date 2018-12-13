package com.zhys.fjzl.domain;
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-10-10 <br/>
 * 描述：新建表类
 */
public class Table1 extends Base {

    @FieldAnnotation(comment = "编码", exp = false, imp = false, empty = true, len = 20, pk = true)
    private Long id;

    @FieldAnnotation(comment = "名称", exp = true, imp = true, empty = true, len = 200)
    private String name;


    /**
     * 新建表对象构造函数
     */
    public Table1() {
        super();
    }

    public Table1(String id) {
        this("id",id);
    }

    public Table1(String property, Object value) {
        this();
        init(property, value);
    }

    /**
     * 获得编码
     * @return Long
     */
    public Long getId(){
        return this.id;
    }

    /**
     * 设置编码
     * @param id  编码
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
}