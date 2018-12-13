<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>菜单详细</title>
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
<div region="center" border="false" style="padding: 5px">
    <fieldset class="fieldset" id="fieldset1">
        <legend class="legend">菜单信息</legend>
        <table class="table-border"  id="table" width="100%">
            <col width="150">
            <col>
            <tbody>
            <tr>
                <td class="th">菜单名称</td>
                <td class="td">${menu.sm_name}</td>
            </tr>
        <#--
            <tr>
                <th>菜单图标</th>
                <td><div class="${menu.menu_icon}" style="width:16px;height:16px;"></div></td>
            </tr>
        -->
            <tr>
                <th class="th">父菜单</th>
                <td class="td"><#if menu.sm_parentid=="0">无<#else>${menu.sm_parentname}</#if></td>
            </tr>
            <tr>
                <th class="th">菜单类型</th>
                <td class="td"><#if menu.sm_type=="0"><label style="color:#00F">分割线</label><#elseif menu.sm_type=="1">菜单项</#if></td>
            </tr>
            <tr>
                <th class="th">菜单动作</th>
                <td class="td">${menu.sm_action}</td>
            </tr>
            <tr>
                <th class="th">备注</th>
                <td class="td">${menu.sm_content}</td>
            </tr>
            <tr>
                <th class="th">子菜单</th>
                <td class="td"><#if menu.sm_child=="0"><label style="color:#AAAAAA">无</label><#elseif menu.sm_child=="1">有</#if></td>
            </tr>
            </tbody>
        </table>
    </fieldset>
    <br>
    <#if menu.sm_type=="1"&&menu.sm_child=="0">
        <#if actionList?? && (actionList?size > 0)>
    <fieldset class="fieldset" id="fieldset2" style="<#if menu.sm_type=="0"||menu.sm_child=="1">display:none</#if>">
        <legend class="legend">菜单功能</legend>

        <table class="table-border" id="list" width="100%" >
            <col width="150">
            <col>
            <thead>
            <tr>
                <td class="td">功能组名</td>
                <td class="td">控制器名</td>
            </tr>
            </thead>
            <tbody>
                <#list actionList as item >
                <tr>
                    <td class="td">${item.sa_group}</td>
                    <td class="td">${item.sa_class}</td>
                </tr>
                </#list>
            </tbody>
        </table>
    </fieldset>
        </#if>
    </#if>
</div>


</body>
</html>