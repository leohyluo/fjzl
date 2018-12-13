<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户添加</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function (){
            $("body").layout();
            var tree =  ${tree!"null"};
            $("#so_id").combotree({
                panelHeight:180,
                data:tree,
                value:"${so_id!"0"}"
            });
        });


        function save(){
            if (!$("#form").form("validate")) return;
            var user = $("#form").serializeJson();

            if (user.so_id==""||user.so_id=="0"){
                $.messager.alert("系统提示", "请选择机构", "info");
                return;
            }

            var s = "";
            var sr_ids = $("input[name=sr_id]:checked");
            for (var i = 0; i < sr_ids.length; i++) {
                s += "," + $(sr_ids[i]).val();
            }

            if(s.length>0) user.sr_id = s.substring(1);
            else delete user.sr_id;

            $.ajaxPost("${path}/sys/user_addSave.do",{"user":user},function(result){
                if (result == "1") {
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"用户添加成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    window.parent.reloadPage();
                    window.parent.$.closeDialog("dialog");
                } else if (result == "2") {
                    $.messager.alert("系统提示", "用户帐号重复", "info");
                }
            });
        }

        function checkStatus(obj){
            $("#su_status").val($(obj).attr("checked") ? "1" : "0");
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
    <input type="hidden" id="su_status" name="su_status" value="0"/>
    <table id="table" class="table-border" style="width:100%">
        <tbody>
        <tr>
            <td class="th" width="100">用户帐号</td>
            <td class="td"><input id="su_code" name="su_code" type="text" class="input easyui-validatebox"
                       required="true" validType="maxLength[50]"  style="width:300px;"/></td>
        </tr>
        <tr>
            <td class="th">用户名称</td>
            <td class="td"><input id="su_name" name="su_name" type="text" class="input easyui-validatebox"
                       required="true" validType="maxLength[50]" style="width:300px;"/></td>
        </tr>
        <tr>
            <td class="th">所属机构</td>
            <td class="td"><input id="so_id" name="so_id" type="text" style="width:308px" required="true" /></td>
        </tr>
        <tr>
            <td class="th">性别</td>
            <td class="td"><input id="su_sex1" name="su_sex" type="radio" value="男" checked/>男
                <input id="su_sex2" name="su_sex" type="radio" value="女"/>女</td>
        </tr>
        <tr>
            <td class="th">密码</td>
            <td class="td">默认:888888</td>
        </tr>
        <tr>
            <td class="th">联系方式</td>
            <td class="td"><input id="su_contact" name="su_contact" type="text" class="input easyui-validatebox"
                       validType="maxLength[100]" style="width:300px;"/></td>
        </tr>
        <tr>
            <td class="th">邮箱</td>
            <td class="td"><input id="su_email" name="su_email" type="text" class="input easyui-validatebox"
                       validType="email" style="width:300px;"/></td>
        </tr>
        <tr>
            <td class="th">备注</td>
            <td class="td"><textarea id="su_content" name="su_content" class="textarea easyui-validatebox"
                          validType="maxLength[500]" style="width:300px;height:50px;"></textarea></td>
        </tr>
        <tr>
            <td class="th">角色分配</td>
            <td class="td">
                <#if roleList??>
                    <#list roleList as role>
                    <div style="float:left;margin-left:5px;" class="nobr">
                        <input name="sr_id" class="checkbox" type="checkbox" value="${role.sr_id}">${role.sr_name}
                    </div>
                    </#list>
                </#if>
            </td>
        </tr>
        <tr>
            <td class="th" style="color:#AAAAAA">未启用</td>
            <td class="td"><input type="checkbox" onclick="checkStatus(this)">启用</td>
        </tr>
        </tbody>
    </table>
</form>
    </div>
</body>
</html>