<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-17 <br/>
描述：药品对照表修改页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>药品对照表修改</title>
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
            var mapper = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/mapper_editSave.do", {"mapper":mapper}, function(result) {
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
        <input type="hidden" id="id" name="id"  value="${(mapper.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr style="display: none">
                <td class="th" >机构id</td>
                <td class="td"><input type="text" id="org_id" name="org_id" class="input easyui-validatebox"  value="${(mapper.org_id)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">机构药品id</td>
                <td class="td"><input type="text" id="drug_id" name="drug_id" class="input easyui-validatebox" value="${(mapper.drug_id)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构药品编码</td>
                <td class="td"><input type="text" id="org_drug_code" name="org_drug_code" class="input easyui-validatebox" validType="maxLength[64]" value="${(mapper.org_drug_code)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构药品名称</td>
                <td class="td"><input type="text" id="org_drug_name" name="org_drug_name" class="input easyui-validatebox" validType="maxLength[50]" value="${(mapper.org_drug_name)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">本位码</td>
                <td class="td"><input type="text" id="standard_code" name="standard_code" class="input easyui-validatebox" validType="maxLength[50]" value="${(mapper.standard_code)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">批准文号</td>
                <td class="td"><input type="text" id="approval_code" name="approval_code" class="input easyui-validatebox" validType="maxLength[50]" value="${(mapper.approval_code)!}" style="width:300px;"/></td>
            </tr>
              <tr style="display: none">
                <td class="th">药物类型(西药/中成药/中药饮片)</td>
                <td class="td"><input type="hidden" id="type" name="type" class="input easyui-numberbox" min="0" value="${(mapper.type)!}" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">规格</td>
                <td class="td"><input type="text" id="specifications" name="specifications" class="input easyui-validatebox" validType="maxLength[200]" value="${(mapper.specifications)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">剂型</td>
                <td class="td"><input type="text" id="dosage_form" name="dosage_form" class="input easyui-validatebox" validType="maxLength[50]" value="${(mapper.dosage_form)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">单位</td>
                <td class="td"><input type="text" id="unit" name="unit" class="input easyui-validatebox" validType="maxLength[20]" value="${(mapper.unit)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">药物组成</td>
                <td class="td">
                <!--<input type="text" id="drug_composition" name="drug_composition" class="input easyui-validatebox" validType="maxLength[200]" value="${(mapper.drug_composition)!}" style="width:300px;"/>-->
                    <textarea id="drug_composition" name="drug_composition" class="textarea easyui-validatebox" 
                    style="width:302px;height:50px;" validType="maxLength[200]">${(mapper.drug_composition)!}</textarea>
                </td>
            
            </tr>
            <tr style="display: none">
                <td class="th">药物毒性</td>
                <td class="td">
               <!-- <input type="text" id="drug_toxicity" name="drug_toxicity" class="input easyui-validatebox" validType="maxLength[200]" value="${(mapper.drug_toxicity)!}" style="width:300px;"/>-->
                       <textarea id="drug_toxicity" name="drug_toxicity" class="textarea easyui-validatebox" 
                    style="width:302px;height:50px;" validType="maxLength[200]">${(mapper.drug_toxicity)!}</textarea>
                </td>
            </tr>
            <tr style="display: none">
                <td class="th">药性</td>
                <td class="td"><input type="text" id="resistance" name="resistance" class="input easyui-validatebox" validType="maxLength[200]" value="${(mapper.resistance)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">化学成份</td>
                <td class="td"><input type="text" id="chemical_composition" name="chemical_composition" class="input easyui-validatebox" validType="maxLength[200]" value="${(mapper.chemical_composition)!}" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">药品等级</td>
                <td class="td"><input type="text" id="grade" name="grade" class="input easyui-numberbox" min="0" max="9999999999" precision="0" value="${(mapper.grade)!}" style="width:300px;"/></td>
            </tr>
           <!-- <tr style="display: none">
                <td class="th">状态</td>
                <td class="td"><input type="hidden" id="drug_status" name="drug_status" value='0' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>-->
        </table>
    </form>
</div>
</body>
</html>