<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：知识库检查表修改页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>知识库检查表修改</title>
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
            var bodyCheck = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/bodyCheck_editSave.do", {"bodyCheck":bodyCheck}, function(result) {
            	if(result == "-1") {
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"检查已存在",
                        timeout:5000,
                        showType:"slide"
                    });
                    $.hideLoad();
            	} else if(result=="1"){
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
        <input type="hidden" id="id" name="id"  value="${(bodyCheck.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">检查名称</td>
                <td class="td"><input type="text" id="name" name="name" class="input easyui-validatebox" validType="maxLength[100]" value="${(bodyCheck.name)!}" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">检查等级</td>
                <td class="td"><input type="text" id="grade" name="grade" class="input easyui-validatebox" validType="maxLength[64]" value="${(bodyCheck.grade)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">特检特治标志</td>
                <td class="td"><input type="text" id="special" name="special" class="input easyui-numberbox" min="0" max="9999999999" precision="0" value="${(bodyCheck.special)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">一级及以下限价</td>
                <td class="td"><input type="text" id="level1_price" name="level1_price" class="input easyui-numberbox" min="0" max="999999999.99" precision="2" value="${(bodyCheck.level1_price)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">二级限价</td>
                <td class="td"><input type="text" id="level2_price" name="level2_price" class="input easyui-numberbox" min="0" max="999999999.99" precision="2" value="${(bodyCheck.level2_price)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">三乙限价</td>
                <td class="td"><input type="text" id="level3_price" name="level3_price" class="input easyui-numberbox" min="0" max="999999999.99" precision="2" value="${(bodyCheck.level3_price)!}" style="width:300px;"/></td>
            </tr>
          <!-- <tr style="display: none">
                <td class="th">审核状态</td>
                <td class="td"><input type="hidden" id="body_check_status" name="body_check_status" value='0' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>-->
        </table>
    </form>
</div>
</body>
</html>