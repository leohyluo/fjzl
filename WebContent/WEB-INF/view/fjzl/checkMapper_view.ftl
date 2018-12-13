<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-17 <br/>
描述：检查对照表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>检查对照表详细</title>
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
        <input type="hidden" id="id" name="id" value="${(checkMapper.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">机构名称</td>
                <td class="td">${(checkMapper.orgName)!}</td>
            </tr>
            <tr style="display: none">
                <td class="th">知识库检查id</td>
                <td class="td">${(checkMapper.check_id)!}</td>
            </tr>
            <tr style="display: none">
                <td class="th">机构检查id</td>
                <td class="td">${(checkMapper.org_check_id)!}</td>
            </tr>
            <tr>
                <td class="th">机构检查名称</td>
                <td class="td">${(checkMapper.org_check_name)!}</td>
            </tr>
            <tr>
                <td class="th">耗材</td>
                <td class="td">${(checkMapper.invitation)!}</td>
            </tr>
            <tr>
                <td class="th">检查等级</td>
                <td class="td">${(checkMapper.grade)!}</td>
            </tr>
             <tr>
                <td class="th">状态</td>
                <td class="td">
					<#if checkMapper.body_check_status==0>
					未对照
					</#if>
					<#if checkMapper.body_check_status==1>
					已对照
					</#if>
					<#if checkMapper.body_check_status==2>
					对照失败
					</#if>
					<#if checkMapper.body_check_status==3>
					已审核
					</#if>
					<#if checkMapper.body_check_status==4>
					未审核
					</#if>
					<#if checkMapper.body_check_status==5>
					审核不通过
					</#if>
                </td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut checkMapper.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(checkMapper.creatorName)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut checkMapper.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(checkMapper.last_updatorName)!}</td>
            </tr>
            <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut checkMapper.review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(checkMapper.revieworName)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
