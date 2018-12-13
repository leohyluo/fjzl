<#--
版权：融贯资讯 <br/>
作者：xuan.zhou@rogrand.com <br/>
生成日期：2013-12-17 <br/>
描述：系统配置查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统配置详细</title>
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
        <input type="hidden" id="configid" name="configid" value="${(sysconfig.configid)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">配置KEY</td>
                <td class="td">${(sysconfig.configkey)!}</td>
            </tr>
            <tr>
                <td class="th">配置值</td>
                <td class="td">${(sysconfig.configvalue)!}</td>
            </tr>
            <tr>
                <td class="th">配置描述</td>
                <td class="td">${(sysconfig.configdesc)!}</td>
            </tr>
            <tr>
                <td class="th">配置时间</td>
                <td class="td"><@datetimeOut sysconfig.configaddtime/></td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
