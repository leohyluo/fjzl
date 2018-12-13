package ${table.packageDomain};
import com.zhys.core.annotation.FieldAnnotation;
import com.zhys.core.domain.Base;
import java.util.Date;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：${now?string("yyyy-MM-dd")} <br/>
 * 描述：${table.annotation}类
 */
public class ${table.classDomain} extends Base {

    <#list table.columnList as item>
    @FieldAnnotation(comment = "${item.annotation}"<#if item.pk=='Y'>, exp = false, imp = false<#else>, exp = true, imp = true</#if><#if item.nullAble=='N'>, empty = false<#else>, empty = true</#if><#if item.javaType=='String' || item.javaType=='Integer' || item.javaType=='Long' || item.javaType=='Double'>, len = ${item.length}</#if><#if item.ibatisType=='NUMERIC' && item.scale gt 0>, scale = ${item.scale}</#if><#if item.pk=='Y'>, pk = true</#if>)
    private ${item.javaType} ${item.name?lower_case};<#--${item.annotation}-->

    </#list>

    /**
     * ${table.annotation}对象构造函数
     */
    public ${table.classDomain}() {
        super();
        <#--
        this.putComment("tableComment", "${table.annotation}");
        <#list table.columnList as item>
        this.putComment("${item.name}", "${item.annotation}");
        </#list>
        -->
    }

    public ${table.classDomain}(String ${table.pk}) {
        this("${table.pk}",${table.pk});
    }

    public ${table.classDomain}(String property, Object value) {
        this();
        init(property, value);
    }

    <#list table.columnList as item>
    /**
     * 获得${item.annotation}
     * @return ${item.javaType}
     */
    public ${item.javaType} get${item.name?cap_first}(){
        return this.${item.name};
    }

    /**
     * 设置${item.annotation}
     * @param ${item.name}  ${item.annotation}
     */
    public void set${item.name?cap_first}(${item.javaType} ${item.name}){
        putField("${item.name}");
        this.${item.name} = ${item.name};
    }
    </#list>
}