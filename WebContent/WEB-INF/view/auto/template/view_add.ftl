${"<#--"}
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：${now?string("yyyy-MM-dd")} <br/>
描述：${table.annotation}添加页面
${"-->"}
${"<#include \"/WEB-INF/view/macro.ftl\"/>"}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${table.annotation}添加</title>
    ${"<#include \"/WEB-INF/view/linkScript.ftl\"/>"}
    <script type="text/javascript">
        $(function(){
            $("body").layout();
            $('#form input.easyui-numberbox').numberbox();
        });

        /**
         * 保存
         */
        function save() {
            if (!$("#form").form("validate")) return;
            var ${table.classDomain?uncap_first} = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${"$"+"{path}"}${table.mappings?replace("*","addSave")}", {"${table.classDomain?uncap_first}":${table.classDomain?uncap_first}}, function(result) {
            	if(result=="1"){
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"记录添加成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    $.hideLoad();
                    window.parent.$.closeDialog('dialog');
                    window.parent.reloadPage();
            	}else{
            		$.messager.alert("提示",result,"warning");
            		$.hideLoad();
            	}
            });
        }

    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:save()" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>

<div region="center" border="false" style="padding:10px;">
    <form id="form" name="form" style="+zoom:1;">
        <#--<input type="hidden" id="${table.pk}" name="${table.pk}">-->
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <#list table.columnList as item><#if item.name!=table.pk>
            <tr>
                <td class="th">${item.annotation}</td>
                <td class="td"><#compress>
                    <#if item.ibatisType=="VARCHAR">
                        <input type="text" id="${item.name}" name="${item.name}" class="input easyui-validatebox" <#if item.nullAble=='N'>required="true"</#if> validType="maxLength[${item.length?c}]" style="width:300px;"/>
                    <#elseif  item.ibatisType=="NUMERIC">
                        <input type="text" id="${item.name}" name="${item.name}" class="input easyui-numberbox" min="0" max="${item.max}" precision="${item.scale?c}" <#if item.nullAble=='N'>required="true"</#if> style="width:300px;"/>
                    <#elseif  item.ibatisType=="TIMESTAMP">
                        <input type="text" id="${item.name}" name="${item.name}" class="input Wdate<#if item.nullAble=='N'> easyui-validatebox</#if>" onclick="WdatePicker()" <#if item.nullAble=='N'>required="true"</#if> style="width:300px;"/>
                    <#elseif  item.ibatisType=="TEXT">
                        <textarea id="${item.name}" name="${item.name}" class="textarea<#if item.nullAble=='N'> easyui-validatebox</#if>" <#if item.nullAble=='N'>required="true"</#if> style="width:300px;height:100px;"></textarea>
                    <#elseif  item.ibatisType=="CLOB">
                        <textarea id="${item.name}" name="${item.name}" class="textarea<#if item.nullAble=='N'> easyui-validatebox</#if>" <#if item.nullAble=='N'>required="true"</#if>  style="width:300px;height:100px;"></textarea>
                    <#elseif  item.ibatisType=="BLOB">
                        不支持BLOB型
                    </#if>
                </#compress></td>
            </tr>
            </#if></#list>
        </table>
    </form>
</div>
</body>
</html>