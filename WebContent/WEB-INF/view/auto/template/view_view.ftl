${"<#--"}
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：${now?string("yyyy-MM-dd")} <br/>
描述：${table.annotation}查看页面
${"-->"}
${"<#include \"/WEB-INF/view/macro.ftl\"/>"}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${table.annotation}详细</title>
    ${"<#include \"/WEB-INF/view/linkScript.ftl\"/>"}
    <script type="text/javascript">
        $(function(){
            $("body").layout();
        });
    </script>
</head>
<#assign domain=table.classDomain?uncap_first>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:window.location.reload();" class="easyui-linkbutton" icon="icon-reload" plain="true">刷新</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding:10px;">
    <div style="+zoom:1;">
        <input type="hidden" id="${table.pk}" name="${table.pk}" value="${"$"+"{("}${domain}.${table.pk}${")!}"}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <#list table.columnList as item>
            <#if item.pk!='Y'>
            <tr>
                <td class="th">${item.annotation}</td>
                <td class="td"><#compress>
                    <#if item.ibatisType=="VARCHAR">${"$"+"{("}${domain}.${item.name}${")!}"}
                    <#elseif  item.ibatisType=="NUMERIC">${"$"+"{("}${domain}.${item.name}${")!}"}
                    <#elseif  item.ibatisType=="TIMESTAMP">${"<@"+"dateOut "}${domain}.${item.name}${"/>"}
                    <#elseif  item.ibatisType=="TEXT">${"$"+"{("}${domain}.${item.name}${")!}"}
                    <#elseif  item.ibatisType=="CLOB">${"$"+"{("}${domain}.${item.name}${")!}"}
                    <#elseif  item.ibatisType=="BLOB">
                        不支持BLOB型
                    </#if>
                </#compress></td>
            </tr>
            </#if>
            </#list>
        </table>
    </div>
</div>
</body>
</html>
