<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-16 <br/>
描述：基础知识库icd9表修改页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>基础知识库icd9表修改</title>
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
            var icd9 = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/icd9_editSave.do", {"icd9":icd9}, function(result) {
                if(result=="1"){
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
        <input type="hidden" id="id" name="id"  value="${(icd9.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">icd9编码</td>
                <td class="td"><input type="text" id="icd9" name="icd9" class="input easyui-validatebox" validType="maxLength[20]" value="${(icd9.icd9)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">icd9名称</td>
                <td class="td"><input type="text" id="icd9_name" name="icd9_name" class="input easyui-validatebox" validType="maxLength[50]" value="${(icd9.icd9_name)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">数据来源(导入/手动添加)</td>
                <td class="td"><input type="text" id="source" name="source" class="input easyui-numberbox" min="0" max="9999999999" precision="0" value="${(icd9.source)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><input type="text" id="create_time" name="create_time" class="input Wdate" onclick="WdatePicker()" value="<@dateOut icd9.create_time/>" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td"><input type="text" id="creator" name="creator" class="input easyui-numberbox" min="0" max="9999999999" precision="0" value="${(icd9.creator)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><input type="text" id="last_update_time" name="last_update_time" class="input Wdate" onclick="WdatePicker()" value="<@dateOut icd9.last_update_time/>" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td"><input type="text" id="last_updator" name="last_updator" class="input easyui-numberbox" min="0" max="9999999999" precision="0" value="${(icd9.last_updator)!}" style="width:300px;"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>