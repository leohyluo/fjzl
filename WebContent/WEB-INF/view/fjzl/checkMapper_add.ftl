<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-17 <br/>
描述：检查对照表添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>检查对照表添加</title>
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
            var checkMapper = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/checkMapper_addSave.do", {"checkMapper":checkMapper}, function(result) {
            if(result == "-1") {
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"记录已存在",
                        timeout:5000,
                        showType:"slide"
                    });
                    $.hideLoad();
            	}else if(result=="1"){
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
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr style="display: none">
                <td class="th">机构id</td>
                <td class="td"><input type="text" id="org_id" name="org_id" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">知识库检查id</td>
                <td class="td"><input type="text" id="check_id" name="check_id" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构检查编码</td>
                <td class="td"><input type="text" id="org_check_id" name="org_check_id" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">机构检查名称</td>
                <td class="td"><input type="text" id="org_check_name" name="org_check_name" class="input easyui-validatebox" validType="maxLength[50]" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">耗材</td>
                <td class="td"><input type="text" id="invitation" name="invitation" class="input easyui-validatebox" validType="maxLength[200]" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">检查等级</td>
                <td class="td"><input type="text" id="grade" name="grade" class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
                <tr style="display: none">
                <td class="th">状态</td>
                <td class="td"><input type="hidden" id="body_check_status" name="body_check_status" value='0' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
           <tr style="display: none">
                <td class="th">启用状态</td>
                <td class="td"><input type="hidden" id="_enable" name="_enable" class="input easyui-numberbox"  value='1' min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>