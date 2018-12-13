<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>密码修改</title>
    <#include "/WEB-INF/view/macro.ftl"/>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {
            $("body").layout();
        });
        
        function save(){
            if (!$("#form").form("validate")) return;
            
            $.messager.confirm("系统提示", "你确认要保存以上信息吗？", function(flag){
                if(flag){
                    var su_password0 = $("#su_password0").val();
                    var su_password = $("#su_password").val();
        
                    $.ajaxPost("${path}/sys/person_editPassSave.do", {"su_password0":su_password0, "su_password":su_password}, function(result){
                        if (result == "1") {
                            window.top.$.messager.show({
                                title:"消息提醒",
                                msg:"密码修改成功",
                                timeout:5000,
                                showType:"slide"
                            });
                            
                            window.parent.$.closeDialog("dialog");
                        } else {
                            window.top.parent.$.messager.show({
                                title:"消息提醒",
                                msg: result,
                                timeout:5000,
                                showType:"slide"
                            });
                        }
                    });
                }
            });
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:save();" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding:10px;">
<form id="form" name="form" style="+zoom:1">
    <table id="table" class="table-border" style="width:100%">
        <tbody>
        <tr>
            <td class="th">原始密码</td>
            <td class="td">
                <input id="su_password0" name="su_password0" type="password" class="input easyui-validatebox" style="width:200px;" 
                    required="true" validType="maxLength[11]" />
            </td>
        </tr>
        <tr>
            <td class="th">新密码</td>
            <td class="td">
                <input id="su_password" name="su_password" type="password" class="input easyui-validatebox" style="width:200px;" 
                    required="true" validType="safepass" maxlength="11" />
            </td>
        </tr>
        <tr>
            <td class="th">确认密码</td>
            <td class="td">
                <input id="su_password2" name="su_password2" type="password" class="input easyui-validatebox" style="width:200px;" 
                    required="true" validType="equalTo['#su_password']" />
            </td>
        </tr>
        </tbody>
    </table>
</form>
</div>
</body>
</html>