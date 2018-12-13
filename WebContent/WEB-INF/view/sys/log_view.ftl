<#--
系统日志表查看
User: Created by auto build
Date: 2011-08-12 17:03:39
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统日志表详细</title>
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
    <div style="+zoom:1">
        <input type="hidden" id="sl_id" name="sl_id" value="${(log.sl_id)!}">
        <table class="table-dotted" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">日志时间</td>
                <td class="td"><@dateOut log.sl_date/></td>
            </tr>
            <tr>
                <td class="th">登录账号</td>
                <td class="td">${(log.sl_user_code)!}</td>
            </tr>
            <tr>
                <td class="th">用户名称</td>
                <td class="td">${(log.sl_user_name)!}</td>
            </tr>
            <tr>
                <td class="th">机构名称</td>
                <td class="td">${(log.sl_org_name)!}</td>
            </tr>
            <tr>
                <td class="th">IP地址</td>
                <td class="td">${(log.sl_ip)!}</td>
            </tr>
            <tr>
                <td class="th">类名称</td>
                <td class="td">${(log.sl_class)!}</td>
            </tr>
            <tr>
                <td class="th">方法名称</td>
                <td class="td">${(log.sl_method)!}</td>
            </tr>
            <tr>
                <td class="th">方法描述</td>
                <td class="td">${(log.sl_description)!}</td>
            </tr>
            <tr>
                <td class="th">日志内容</td>
                <td class="td">${(log.sl_content)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
