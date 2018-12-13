<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2017-01-16 <br/>
描述：转诊规则表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>转诊规则表详细</title>
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
        <input type="hidden" id="id" name="id" value="${(options.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr style="display: none">
                <td class="th">机构id</td>
                <td class="td">${(options.org_id)!}</td>
            </tr>
            <tr>
                <td class="th">机构名称</td>
                <td class="td">${(options.name)!}</td>
            </tr>
            <tr>
                <td class="th">无</td>
                <td class="td">${(options._enable)!}</td>
            </tr>
            <tr>
                <td class="th">序号</td>
                <td class="td">${(options._order)!}</td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut options.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(options.creator)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut options.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(options.last_updator)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
