<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-16 <br/>
描述：基础知识库icd9表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>基础知识库icd9表详细</title>
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
        <input type="hidden" id="id" name="id" value="${(icd9.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">icd9编码</td>
                <td class="td">${(icd9.icd9)!}</td>
            </tr>
            <tr>
                <td class="th">icd9名称</td>
                <td class="td">${(icd9.icd9_name)!}</td>
            </tr>
            <tr>
                <td class="th">数据来源(导入/手动添加)</td>
                <td class="td">${(icd9.source)!}</td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut icd9.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(icd9.creator)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut icd9.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(icd9.last_updator)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
