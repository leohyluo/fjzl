<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2017-01-16 <br/>
描述：转诊规则表添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>转诊规则表添加</title>
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
            var options = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/options_addSave.do", {"options":options}, function(result) {
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
            <tr>
                <td class="th">转诊规则名称</td>
                <td class="td">
                      <select id="referral_id" class="easyui-combobox" name="referral_id" editable="editable" panelMaxHeight="100px" style="Height:50px;width:158px;">
					       <#list ruleList as item>
					       	<option value="${item.id}">${item.name}</option>
			    			</#list>
					</select>
                </td>
            </tr>
            <tr style="display: none">
                <td class="th">是否启用</td>
                <td class="td"><input type="text" id="_enable" name="_enable" value='1' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">序号</td>
                <td class="td"><input type="text" id="_order" name="_order" class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:150px;"/></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>