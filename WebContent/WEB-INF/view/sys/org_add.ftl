<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>机构添加</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {
            $("body").layout();
            var tree =  ${tree!"null"};
            $("#so_parentid").combotree({
                panelHeight:180,
                data:tree,
                value:"${org.so_id!"0"}",
                onSelect:function(node){
                    if(node.id==0) {
                        $("#so_parentcode").hide();
                        $("#so_parentcode").text("");
                    }
                    else {
                        $("#so_parentcode").show();
                        $("#so_parentcode").text(node.attributes.so_code2);
                    }
                }
            });


            var selected =  $("#so_parentid").combotree("tree").tree("find", "${org.so_id!"0"}");
            if(selected!=null){
                $("#so_parentid").combotree("tree").tree("select", selected.target);
                $("#so_parentid").combotree("tree").tree("expandTo", selected.target);
            }
            
        });

        /**
         * 提交新建机构
         */
        function save() {
            if (!$("#form").form("validate")) return;
            var org = $("#form").serializeJson();
            org.so_code = $("#so_code").val();
            org.so_code2 = $("#so_parentcode").text()+$("#so_code").val();
            $.ajaxPost("${path}/sys/org_addSave.do",{"org":org}, function(result){
                if(result=="1"){
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"机构添加成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    window.parent.$.closeDialog("dialog");
                    window.parent.reloadPage();
                }
                else if(result=="2"){ //编码重复
                    $.messager.alert("系统提示","机构编码重复","info");
                }
            });
        }
    </script>
    <style type="text/css">
        #so_parentcode{
            background: #EFEFEF;
            font-size: 12px;
            height: 14px;
            line-height: 14px;
            padding:4px 3px 3px 3px;
            vertical-align: middle;
            border: 1px solid #A4BED4;
        }
    </style>
</head>


<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:save()" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding: 5px">
    <form  id="form" name="form" style="+zoom:1;">
<#--        <input id="so_order" name="so_order">-->
        <table class="table-border" id="table" width="100%">
            <tbody>
                <tr>
                    <td class="th">机构编码</td>
                    <td class="td"><span id="so_parentcode" <#if org.so_id==0>style="display: none"</#if>>${org.so_code2}</span><input id="so_code" name="so_code" class="input easyui-validatebox" type="text" required="true" style="width: 300px"/></td>
                </tr>
                <tr>
                    <td class="th" width="100">机构名称</td>
                    <td class="td"><input id="so_name" name="so_name" class="input easyui-validatebox" type="text" required="true" validType="maxLength[100]" style="width: 300px"/></td>
                </tr>
                <tr>
                    <td class="th">上级机构</td>
                    <td class="td"><input id="so_parentid" name="so_parentid" class="input" type="text" style="width:308px"/></td>
                </tr>
                <tr>
	                <td class="th">联系人</td>
	                <td class="td"><input type="text" id="so_contactor" name="so_contactor" class="input easyui-validatebox" validType="maxLength[4]" style="width:300px;"/></td>
	            </tr>
                <tr>
                    <td class="th">联系方式</td>
                    <td class="td"><input id="so_contact" name="so_contact" type="text" class="input easyui-validatebox" validType="maxLength[15]" style="width: 300px" /></td>
                </tr>
                <tr>
                    <td class="th">邮箱地址</td>
                    <td class="td"><input id="so_email" name="so_email" class="input easyui-validatebox" validType="email" type="text" style="width: 300px"/></td>
                </tr>
                <tr>
                    <td class="th">邮政编码</td>
                    <td class="td"><input id="so_post" name="so_post" type="text" class="input easyui-validatebox" validType="maxLength[6]" style="width: 300px"></td>
                </tr>
            <tr>
                <td class="th">机构类型(医联体/医院/社康)</td>
                <td class="td">
                	<input type="radio" id="rdo_type1" value="1" name="so_type">医联体
                	<input type="radio" id="rdo_type2" value="2" name="so_type" checked>医院
                	<input type="radio" id="rdo_type3" value="3" name="so_type">社康
                </td>
            </tr>
            <tr>
                <td class="th">省份</td>
                <td class="td"><input type="text" id="so_province" name="so_province" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">城市</td>
                <td class="td"><input type="text" id="so_city" name="so_city" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">区</td>
                <td class="td"><input type="text" id="so_area" name="so_area" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">街道</td>
                <td class="td"><input type="text" id="so_street" name="so_street" class="input easyui-validatebox" validType="maxLength[255]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">经度</td>
                <td class="td"><input type="text" id="so_longitude" name="so_longitude" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">纬度</td>
                <td class="td"><input type="text" id="so_latitude" name="so_latitude" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">横坐标</td>
                <td class="td"><input type="text" id="so_xcoordinate" name="so_xcoordinate" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">纵坐标</td>
                <td class="td"><input type="text" id="so_ycoordinate" name="so_ycoordinate" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构地址</td>
                <td class="td"><input type="text" id="so_address" name="so_address" class="input easyui-validatebox" validType="maxLength[255]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构等级</td>
                <td class="td"><input type="text" id="so_grade" name="so_grade" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构性质(市属/区属/社会办)</td>
                <td class="td">
                	<input type="radio" id="rdo_nature1" name="so_nature" value="1" checked>市属
                	<input type="radio" id="rdo_nature2" name="so_nature" value="2" checked>区属
                	<input type="radio" id="rdo_nature3" name="so_nature" value="3" checked>社会办
                </td>
            </tr>
            <tr>
                <td class="th">是否是指导单位</td>
                <td class="td">
                	<input type="radio" id="rdo_leader1" name="so_leader_org_flag" value="1">是
                	<input type="radio" id="rdo_leader2" name="so_leader_org_flag" value="0" checked>否
                </td>
            </tr>
            <tr>
                <td class="th">是否为牵头单位</td>
                <td class="td">
                	<input type="radio" id="rdo_guide1" name="so_guide_org_flag" value="1">是
                	<input type="radio" id="rdo_guide2" name="so_guide_org_flag" value="0" checked>否
                </td>
            </tr>
            </tbody>
        </table>
    </form>
 </div>
</body>
</html>

