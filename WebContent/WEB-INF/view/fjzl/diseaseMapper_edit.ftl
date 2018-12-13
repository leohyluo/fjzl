<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-21 <br/>
描述：疾病对照表修改页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>疾病对照表修改</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
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
            var mapper = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/diseaseMapper_editSave.do", {"mapper":mapper}, function(result) {
               if(result == "-1") {
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"疾病已存在",
                        timeout:5000,
                        showType:"slide"
                    });
                    $.hideLoad();
            	}else if(result=="1"){
                	window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"记录修改成功",
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
        <input type="hidden" id="id" name="id"  value="${(mapper.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr style="display: none">
                <td class="th">机构id</td>
                <td class="td"><input type="text" id="org_id" name="org_id" class="input easyui-validatebox" value="${(mapper.org_id)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">疾病id</td>
                <td class="td"><input type="text" id="disease_id" name="disease_id" class="input easyui-validatebox" value="${(mapper.disease_id)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构疾病编码</td>
                <td class="td"><input type="text" id="org_disease_id" name="org_disease_id" class="input easyui-validatebox" value="${(mapper.org_disease_id)!}" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">机构疾病名称</td>
                <td class="td"><input type="text" id="org_disease_name" name="org_disease_name" class="input easyui-validatebox" validType="maxLength[100]" value="${(mapper.org_disease_name)!}" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">机构icd10</td>
                <td class="td"><input type="text" id="org_icd_10" name="org_icd_10" class="input easyui-validatebox" validType="maxLength[20]" value="${(mapper.org_icd_10)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">疾病定义</td>
                <td class="td"><input type="text" id="definition" name="definition" class="input easyui-validatebox" validType="maxLength[500]" value="${(mapper.definition)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">对照方式</td>
                <td class="td"><input type="text" id="type" name="type" class="input easyui-numberbox" min="0" max="9999999999" precision="0" value="${(mapper.type)!}" style="width:300px;"/></td>
            </tr>
           <!--<tr style="display: none">
                <td class="th">状态</td>
                <td class="td"><input type="hidden" id="disease_status" name="disease_status" value='0' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>-->
        </table>
    </form>
</div>
</body>
</html>