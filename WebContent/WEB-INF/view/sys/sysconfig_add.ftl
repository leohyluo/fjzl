<#--
版权：融贯资讯 <br/>
作者：xuan.zhou@rogrand.com <br/>
生成日期：2013-12-17 <br/>
描述：系统配置添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统配置添加</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
            $('#form input.easyui-numberbox').numberbox();
            
            $("#configtype").combobox({
                data:[
                    {"id":"0","text":"用户配置"},
                    {"id":"1","text":"系统配置"}
                ],
                value:"0"
            });
        });

        function save() {
            if (!$("#form").form("validate")) return;
            var sysconfig = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/sys/sysconfig_addSave.do", {"sysconfig":sysconfig}, function(result) {
                window.parent.$.messager.show({
                    title:"消息提醒",
                    msg:"记录添加成功",
                    timeout:5000,
                    showType:"slide"
                });
                $.hideLoad();
                window.parent.$.closeDialog('dialog');
                window.parent.reloadPage();
                // window.parent.searchPage(false);
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
            <tr>
                <td class="th">配置KEY</td>
                <td class="td"><input type="text" id="configkey" name="configkey" class="input easyui-validatebox" required="true" validType="maxLength[30]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">配置值</td>
                <td class="td"><input type="text" id="configvalue" name="configvalue" class="input easyui-validatebox" required="true" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">配置描述</td>
                <td class="td">
                    <textarea id="configdesc" name="configdesc" class="textarea easyui-validatebox" validType="maxLength[200]" style="height:60px; width:300px;">${(sysconfig.configdesc)!}</textarea>
                </td>
            </tr>
            <tr>
                <td class="th">配置类型</td>
                <td class="td">
                    <input id="configtype" name="configtype" editable="false" panelHeight="100" valueField="id" textField="text" class="input easyui-validatebox" />
                </td>
            </tr>
            <#--
            <tr>
                <td class="th">配置时间</td>
                <td class="td"><input type="text" id="configaddtime" name="configaddtime" class="input Wdate" onclick="WdatePicker()" style="width:300px;"/></td>
            </tr>
            -->
        </table>
    </form>
</div>
</body>
</html>