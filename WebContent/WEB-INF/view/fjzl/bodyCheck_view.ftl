<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：知识库检查表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>知识库检查表详细</title>
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
        <input type="hidden" id="id" name="id" value="${(bodyCheck.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">检查名称</td>
                <td class="td">${(bodyCheck.name)!}</td>
            </tr>
            <tr>
                <td class="th">检查等级</td>
                <td class="td">${(bodyCheck.grade)!}</td>
            </tr>
            <tr>
                <td class="th">特检特治标志</td>
                <td class="td">${(bodyCheck.special)!}</td>
            </tr>
            <tr>
                <td class="th">一级及以下限价</td>
                <td class="td">${(bodyCheck.level1_price)!}</td>
            </tr>
            <tr>
                <td class="th">二级限价</td>
                <td class="td">${(bodyCheck.level2_price)!}</td>
            </tr>
            <tr>
                <td class="th">三乙限价</td>
                <td class="td">${(bodyCheck.level3_price)!}</td>
            </tr>
             <tr>
                <td class="th">状态</td>
                <td class="td">
					<#if bodyCheck.body_check_status==0>
					未审核
					</#if>
					<#if bodyCheck.body_check_status==1>
					已审核
					</#if>
                </td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut bodyCheck.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(bodyCheck.creatorName)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut bodyCheck.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(bodyCheck.last_updatorName)!}</td>
            </tr>
             <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut bodyCheck.review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(bodyCheck.revieworName)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
