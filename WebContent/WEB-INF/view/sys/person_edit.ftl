<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>个人信息</title>
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
                    var user = $("#form").serializeJson();
                    $.ajaxPost("${path}/sys/person_editSave.do",{"user":user},function(result){
                        if (result == "1") {
                            window.parent.$.messager.show({
                                title:"消息提醒",
                                msg:"个人修改成功",
                                timeout:5000,
                                showType:"slide"
                            });
                            
                            back();
                        }
                    });
                }
            });
        }
        
        function back() {
            window.parent.$("#iframe_work").attr("src", "${path}/sys/person_view.do");
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:back();" class="easyui-linkbutton" icon="icon-back" plain="true">返回</a>
        <a href="javascript:save();" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding:10px;">
<form id="form" name="form" style="+zoom:1">
    <input type="hidden" name="su_id" value="${user.su_id}"/>
    <table id="table" class="table-border" style="width:100%">
        <tbody>
        <tr>
            <td class="th" width="100">用户帐号</td>
            <td class="td">${user.su_code}</td>
        </tr>
        <tr>
            <td class="th">用户名称</td>
            <td class="td">
                <input id="su_name" name="su_name" type="text" class="input easyui-validatebox" style="width:300px;" 
                    required="true" validType="maxLength[50]" value="${user.su_name}" />
            </td>
        </tr>
        <tr>
            <td class="th">所属机构</td>
            <td class="td">${user.so_name}</td>
        </tr>
        <tr>
            <td class="th">性别</td>
            <td class="td">
                <input id="su_sex1" name="su_sex" type="radio" class="radio" value="男" <#if user.su_sex=="男">checked</#if>/><label for="su_sex1">男</label>
                <input id="su_sex2" name="su_sex" type="radio" class="radio" value="女" <#if user.su_sex=="女">checked</#if>/><label for="su_sex2">女</label>
            </td>
        </tr>
        <tr>
            <td class="th">联系方式</td>
            <td class="td">
                <input id="su_contact" name="su_contact" type="text"  class="input easyui-validatebox" style="width:300px;" 
                    validType="maxLength[100]" value="${user.su_contact}" />
            </td>
        </tr>
        <tr>
            <td class="th">邮箱</td>
            <td class="td">
                <input id="su_email" name="su_email" type="text" class="input easyui-validatebox" style="width:300px;" 
                    validType="email" value="${user.su_email}" />
            </td>
        </tr>
        <tr>
            <td class="th">备注</td>
            <td class="td">
                <textarea id="su_content" name="su_content" class="textarea easyui-validatebox" 
                    style="width:300px;height:50px;" validType="maxLength[500]">${user.su_content}</textarea>
            </td>
        </tr>
        <tr>
            <td class="th">用户上次登录IP</td>
            <td class="td">${user.su_last_ip}</td>
        </tr>
        <tr>
            <td class="th">用户登录次数</td>
            <td class="td">${user.su_login_count}</td>
        </tr>
        <tr>
            <td class="th">用户上次登录时间</td>
            <td class="td"><@datetimeOut user.su_last_time/></td>
        </tr>
        <tr>
            <td class="th">用户创建时间</td>
            <td class="td"><@datetimeOut user.su_add_time/></td>
        </tr>
        <#if user.su_admin=="0">
        <tr>
            <td class="th">角色分配</td>
            <td class="td">
                <#list roleList as role>
                    <#if role.status=="1">${role.sr_name}&nbsp;</#if>
                </#list>
            </td>
        </tr>
        <tr>
            <td class="th">状态</td>
            <td class="td">
                <#if user.su_status=="0"><label style="color:#AAAAAA">未启用</label></#if>
                <#if user.su_status=="1"><label style="color:#000000">启用中</label></#if>
                <#if user.su_status=="2"><label style="color:#FF0000">已禁用</label></#if>
            </td>
        </tr>
        </#if>
        <#if user.su_admin=="1">
        <tr>
            <td class="th">角色分配</td>
            <td class="td">超级管理员</td>
        </tr>
        </#if>
        </tbody>
    </table>
</form>
</div>

</body>
</html>