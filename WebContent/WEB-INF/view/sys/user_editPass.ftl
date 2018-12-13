<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户密码修改</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">

        $(function(){
            $("body").layout();
            $("#form input:password").val("");
        });

        function save(){
            if (!$("#form").form("validate")) return;
            if($("#su_password_new").val()!=$("#su_password_check").val()){
                $.messager.alert("系统提示","新密码和确认密码不相同","info");
                return;
            }

            $("#form input[name=su_password]").val(hex_md5($("#su_password_new").val()));
            var user = $("#form").serializeJson();
            $.ajaxPost("${path}/sys/user_editPass.do",{"user":user},function(result){
                if(result=="1"){
                    var msg = "用户密码修改成功";
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:msg,
                        timeout:5000,
                        showType:"slide"
                    });
//                    window.parent.reloadPage();
                    window.parent.$.closeDialog("dialog");
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
<form id="form" name="form" style="+zoom:1">
    <input type="hidden" name="su_id" value="${user.su_id}"/>
    <input type="hidden" name="su_password"/>
    <table id="table" class="table-border" width="100%">
        <tbody>
        <tr>
            <td class="th" width="100">用户帐号</td>
            <td class="td">${user.su_code}</td>
        </tr>
        <tr>
            <td class="th">用户名称</td>
            <td class="td">${user.su_name}</td>
        </tr>
        <tr>
            <td class="th">新密码</td>
            <td class="td"><input type="password" id="su_password_new" maxlength="20" style="width:250px"
                       class="input easyui-validatebox" required="true" validType="minLength[6]"/></td>
        </tr>
        <tr>
            <td class="th">确认密码</td>
            <td class="td"><input type="password" id="su_password_check" maxlength="20" style="width:250px"
                       class="input easyui-validatebox" required="true" validType="minLength[6]"></td>
        </tr>
        </tbody>
    </table>
</form>
    </div>
</body>
</html>