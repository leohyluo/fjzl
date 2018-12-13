<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：中成药表添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>中成药表添加</title>
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
            var bCpdrug = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/bCpdrug_addSave.do", {"bCpdrug":bCpdrug}, function(result) {
            	if(result == "-1") {
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"药品已存在",
                        timeout:5000,
                        showType:"slide"
                    });
                    $.hideLoad();
            	} else if(result=="1"){
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
                <td class="th">药品名称</td>
                <td class="td"><input type="text" id="cpdrugname" name="cpdrugname" class="input easyui-validatebox" validType="maxLength[100]" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">剂型规格</td>
                <td class="td"><input type="text" id="standard" name="standard" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">收费类别</td>
                <td class="td"><input type="text" id="charge_type" name="charge_type" value="中成药" class="input easyui-validatebox" validType="maxLength[20]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">收费项目等级</td>
                <td class="td"><input type="text" id="charge_grade" name="charge_grade" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">atc1</td>
                <td class="td"><input type="text" id="atc1" name="atc1" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">atc2</td>
                <td class="td"><input type="text" id="atc2" name="atc2" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">atc3</td>
                <td class="td"><input type="text" id="atc3" name="atc3" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">atc4</td>
                <td class="td"><input type="text" id="atc4" name="atc4" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">药品等级</td>
                <td class="td"><input type="text" id="grade" name="grade" class="input easyui-validatebox" style="width:300px;"/></td>
            </tr>
             <tr style="display: none">
                <td class="th">审核状态</td>
                <td class="td"><input type="hidden" id="cpdrug_status" name="cpdrug_status" value='0' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
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