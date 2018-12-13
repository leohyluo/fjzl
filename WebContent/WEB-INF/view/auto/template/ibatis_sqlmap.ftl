<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">
<!--
${table.annotation}
User: Created by auto build
Date: ${now?string("yyyy-MM-dd HH:mm:ss")}
-->
<sqlMap namespace="${table.name}">

    <typeAlias alias="${table.name}" type="${table.packageDomain}.${table.classDomain}" />

    <#if hasBlob?? && hasBlob==true>
    <!--
    <resultMap id="resultMap" class="${table.name}">
        <result property="${table.pk}" column="${table.pk}" javaType="Long" jdbcType="NUMERIC"/>
        <#list table.columnList as item>
        <#if item.ibatisType="BLOB">
        <result property="${item.name}" column="${item.name}" typeHandler="org.springframework.orm.ibatis.support.BlobByteArrayTypeHandler"/>
        </#if>
        </#list>
    </resultMap>
    -->
    </#if>
    <!--${table.annotation}分页条件-->
    <sql id="pageWhere">
        <dynamic prepend="where">
    <#list table.columnList as item>
        <#if item.ibatisType!="BLOB" && item.ibatisType!="CLOB" && item.ibatisType!="TEXT">
            <#if item.ibatisType=="NUMERIC">
            <!--${item.annotation}最小值-->
            <isNotEmpty prepend="and" property="${item.name}_min">
                <![CDATA[ ${item.name} >= #${item.name}_min# ]]>
            </isNotEmpty>
            <!--${item.annotation}等于-->
            <isNotEmpty prepend="and" property="${item.name}">
                <![CDATA[ ${item.name} = #${item.name}# ]]>
            </isNotEmpty>
            <!--${item.annotation}最大值-->
            <isNotEmpty prepend="and" property="${item.name}_max">
                <![CDATA[ ${item.name} <= #${item.name}_max# ]]>
            </isNotEmpty>
            <#elseif item.ibatisType=="VARCHAR" || item.ibatisType=="TEXT" || item.ibatisType=="CLOB">
            <!--${item.annotation}-->
            <isNotEmpty prepend="and" property="${item.name}">
                <![CDATA[ instr(${item.name}, #${item.name}#) > 0 ]]>
            </isNotEmpty>
            <#elseif item.ibatisType=="TIMESTAMP">
            <!--${item.annotation}开始-->
            <isNotEmpty prepend="and" property="${item.name}_begin">
                <![CDATA[ ${item.name} >= str_to_date(#${item.name}_begin#,'%Y-%m-%d %T') ]]>
            </isNotEmpty>
            <!--${item.annotation}结束-->
            <isNotEmpty prepend="and" property="${item.name}_end">
                <![CDATA[ ${item.name} <= str_to_date('$${item.name}_end$ 23:59:59','%Y-%m-%d %T') ]]>
            </isNotEmpty>
            </#if>
        </#if>
    </#list>
            <!-- 用于数据导出 -->
            <isNotEmpty prepend="and" property="exportIds">
                <![CDATA[ ${table.pk} in ($exportIds$) ]]>
            </isNotEmpty>
        </dynamic>
    </sql>

    <!--${table.annotation}分页记录数-->
    <select id="pageCount" parameterClass="PageParam" resultClass="Long">
        <![CDATA[
            select count(*) total from ${table.name}
        ]]>
        <include refid="pageWhere"/>
    </select>

<#assign list = statics["com.zhys.core.util.BeanUtil"].createInstance("java.util.ArrayList")>
<#list table.columnList as item>
    <#if item.ibatisType!="BLOB" && item.ibatisType!="CLOB">
        <#assign b = list.add(item.name)/>
    </#if>
</#list>
    <!--${table.annotation}分页记录集-->
    <select id="pageList" parameterClass="PageParam" resultClass="${table.name}">
        <![CDATA[ $head$ ]]>
        <![CDATA[
            select
            <#list list as field>
                ${field}<#if field_has_next>,</#if>
            </#list>
            from ${table.name}
        ]]>
        <include refid="pageWhere"/>
        <![CDATA[ $sortOrder$ ]]>
        <![CDATA[ $foot$ ]]>
    </select>

    <!--创建${table.annotation}-->
    <insert id="create" parameterClass="${table.name}">
        <![CDATA[
            insert into ${table.name}(
            <#list table.columnList as item>
                <#if item.pk!="Y">
                ${item.name}<#if item_has_next>,</#if>
                </#if>
            </#list>
            ) values (
            <#list table.columnList as item>
                <#if item.pk!="Y">
                #${item.name}:${item.ibatisType}#<#if item_has_next>,</#if>
                </#if>
            </#list>
            )
        ]]>
        <!--
        <selectKey keyProperty="${table.pk}" resultClass="String" type="pre">
            <include refid="common.pkSql"/>
        </selectKey>
        -->
        <selectKey resultClass="int" >
            select last_insert_id() as ${table.pk}
        </selectKey>
    </insert>

    <!--更新${table.annotation}-->
    <update id="update" parameterClass="${table.name}">
        <![CDATA[ update ${table.name} ]]>
        <dynamic prepend="set">
        <#list table.columnList as item>
            <#if item.pk!="Y">
            <isPropertyAvailable prepend="," property="fields.${item.name}">
                <![CDATA[ ${item.name} = #${item.name}:${item.ibatisType}# ]]>
            </isPropertyAvailable>
            </#if>
        </#list>
        </dynamic>
        <![CDATA[ where ${table.pk} = #${table.pk}#  ]]>
    </update>

    <!--删除${table.annotation}-->
    <delete id="delete" parameterClass="${table.name}">
        <![CDATA[
            delete from ${table.name} where ${table.pk} = #${table.pk}#
        ]]>
    </delete>

</sqlMap>