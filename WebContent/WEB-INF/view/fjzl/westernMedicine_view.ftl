<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：知识库西药表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>知识库西药表详细</title>
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
        <input type="hidden" id="id" name="id" value="${(westernMedicine.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">药品名称</td>
                <td class="td">${(westernMedicine.drug_name)!}</td>
            </tr>
            <tr>
                <td class="th">剂型</td>
                <td class="td">${(westernMedicine.dosage_form)!}</td>
            </tr>
            <tr>
                <td class="th">是否启用</td>
                <td class="td">${(westernMedicine._enable)!}</td>
            </tr>
            <tr>
                <td class="th">用法</td>
                <td class="td">${(westernMedicine._usage)!}</td>
            </tr>
            <tr>
                <td class="th">收费类别</td>
                <td class="td">${(westernMedicine.charge_type)!}</td>
            </tr>
            <tr>
                <td class="th">收费项目等级</td>
                <td class="td">${(westernMedicine.charge_grade)!}</td>
            </tr>
            <tr>
                <td class="th">atc1</td>
                <td class="td">${(westernMedicine.atc1)!}</td>
            </tr>
            <tr>
                <td class="th">atc2</td>
                <td class="td">${(westernMedicine.atc2)!}</td>
            </tr>
            <tr>
                <td class="th">atc3</td>
                <td class="td">${(westernMedicine.atc3)!}</td>
            </tr>
            <tr>
                <td class="th">atc4</td>
                <td class="td">${(westernMedicine.atc4)!}</td>
            </tr>
            <tr>
                <td class="th">药品等级</td>
                <td class="td">${(westernMedicine.grade)!}</td>
            </tr>
             <tr>
                <td class="th">审核状态</td>
                <td class="td">
                	<#if westernMedicine.western_status==0>
					未审核
					</#if>
					<#if westernMedicine.western_status==1>
					已审核
					</#if>
                </td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut westernMedicine.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(westernMedicine.creatorName)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut westernMedicine.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(westernMedicine.last_updatorName)!}</td>
            </tr>
            <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut westernMedicine.review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(westernMedicine.revieworName)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
