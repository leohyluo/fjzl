<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-17 <br/>
描述：药品对照表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>药品对照表详细</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
        });
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:window.location.reload();" class="easyui-linkbutton" icon="icon-reload" plain="true">刷新</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding:10px;">
    <div style="+zoom:1;">
        <input type="hidden" id="id" name="id" value="${(mapper.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">机构名称</td>
                <td class="td">${(mapper.orgName)!}</td>
            </tr>
            <tr style="display: none">
                <td class="th">机构药品id</td>
                <td class="td">${(mapper.drug_id)!}</td>
            </tr>
            <tr>
                <td class="th">机构药品编码</td>
                <td class="td">${(mapper.org_drug_code)!}</td>
            </tr>
            <tr>
                <td class="th">机构药品名称</td>
                <td class="td">${(mapper.org_drug_name)!}</td>
            </tr>
            <tr>
                <td class="th">本位码</td>
                <td class="td">${(mapper.standard_code)!}</td>
            </tr>
            <tr>
                <td class="th">批准文号</td>
                <td class="td">${(mapper.approval_code)!}</td>
            </tr>
            <tr>
                <td class="th">规格</td>
                <td class="td">${(mapper.specifications)!}</td>
            </tr>
            <tr>
                <td class="th">剂型</td>
                <td class="td">${(mapper.dosage_form)!}</td>
            </tr>
            <tr>
                <td class="th">单位</td>
                <td class="td">${(mapper.unit)!}</td>
            </tr>
            <tr>
                <td class="th">药物组成</td>
                <td class="td">${(mapper.drug_composition)!}</td>
            </tr>
            <tr>
                <td class="th">药物毒性</td>
                <td class="td">${(mapper.drug_toxicity)!}</td>
            </tr>
            <tr>
                <td class="th">药性</td>
                <td class="td">${(mapper.resistance)!}</td>
            </tr>
            <tr>
                <td class="th">化学成份</td>
                <td class="td">${(mapper.chemical_composition)!}</td>
            </tr>
            <tr>
                <td class="th">状态</td>
                <td class="td">
                	<#if mapper.disease_status==0>
					未对照
					</#if>
					<#if mapper.disease_status==1>
					已对照
					</#if>
					<#if mapper.disease_status==2>
					对照失败
					</#if>
					<#if mapper.disease_status==3>
					已审核
					</#if>
					<#if mapper.disease_status==4>
					未审核
					</#if>
					<#if mapper.disease_status==5>
					审核不通过
					</#if></td>
            </tr>
            <tr>
                <td class="th">药品等级</td>
                <td class="td">${(mapper.grade)!}</td>
            </tr>
           <!-- <tr>
                <td class="th">药物类型(西药/中成药/中药饮片)</td>
                <td class="td">${(mapper.type)!}</td>
            </tr>-->
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut mapper.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(mapper.creatorName)!}</td>
            </tr>
             <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut mapper.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(mapper.last_updatorName)!}</td>
            </tr>
            <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut mapper.review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(mapper.revieworName)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
