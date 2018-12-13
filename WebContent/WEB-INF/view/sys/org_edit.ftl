<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>机构修改</title>
<#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {
            $("body").layout();
            var tree =  ${tree!"null"};
            $("#so_parentid").combotree({
                panelHeight:180,
                data:tree,
                value:"${(org.so_parentid)!"0"}",
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

            var selected =  $("#so_parentid").combotree("tree").tree("find", "${(org.so_parentid)!"0"}");
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
            $.ajaxPost("${path}/sys/org_editSave.do",{"org":org}, function(result) {
                if (result == "1") {
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"机构修改成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    window.parent.$.closeDialog("dialog");
                    window.parent.reloadPage();
                }
                else if (result == "2") { //编码重复
                    $.messager.alert("系统提示", "机构编码重复", "info");
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
<#assign so_parentcode="">
<#assign so_code=org.so_code>
<#if org.so_parentid!=0>
    <#assign len = so_code?length>
    <#assign so_parentcode= so_code?substring(0,len-3)>
    <#assign so_code= so_code?substring(len-3)>
</#if>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:save()" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding: 5px">
    <form  id="form" name="form" style="+zoom:1;">
    <input type="hidden" id="so_id" name="so_id" value="${org.so_id}">
    <table class="table-border"  id="table" width="100%">
        <tbody>
        <tr>
            <td class="th">机构编码</td>
            <td class="td"><span id="so_parentcode" <#if org.so_parentid==0>style="display: none"</#if>>${so_parentcode}</span><input id="so_code" name="so_code" type="text" value="${org.so_code}"  style="width:300px;" class="input easyui-validatebox" required="true"/></td>
        </tr>
        <tr>
            <td class="th" width="100">机构名称</td>
            <td class="td"><input id="so_name" name="so_name" type="text" value="${org.so_name}"  style="width:300px;"
                                  class="input easyui-validatebox" validType="maxLength[100]" required="true" /></td>
        </tr>
        <tr>
            <td class="th">上级机构</td>
            <td class="td"><input id="so_parentid" name="so_parentid" style="width:308px" type="text"/></td>
        </tr>
        <tr>
                <td class="th">联系人</td>
                <td class="td"><input type="text" id="so_contactor" name="so_contactor" class="input easyui-validatebox" validType="maxLength[4]" value="${(org.so_contactor)!}" style="width:300px;"/></td>
            </tr>
        <tr>
            <td class="th">联系方式</td>
            <td class="td"><input id="so_contact" name="so_contact" type="text" value="${org.so_contact}" style="width:300px;"
                                  class="input easyui-validatebox" validType="maxLength[15]"/></td>
        </tr>
        <tr>
            <td class="th">邮箱地址</td>
            <td class="td"><input id="so_email" name="so_email"  type="text" value="${org.so_email}" style="width:300px;"
                                  class="input easyui-validatebox" validType="email"/></td>
        </tr>
        <tr>
            <td class="th">邮政编码</td>
            <td class="td"><input id="so_post" name="so_post" type="text" value="${org.so_post}" style="width:300px;"
                                  class="input easyui-validatebox" validType="maxLength[6]"/></td>
        </tr>
        <tr>
                <td class="th">机构类型</td>
                <td class="td">
                	<input type="radio" id="rdo_type1" value="1" name="so_type" <#if org.so_type==1>checked</#if>>医联体
                	<input type="radio" id="rdo_type2" value="2" name="so_type" <#if org.so_type==2>checked</#if>>医院
                	<input type="radio" id="rdo_type3" value="3" name="so_type" <#if org.so_type==3>checked</#if>>社康
                </td>
            </tr>
            <tr>
                <td class="th">拼音助记符</td>
                <td class="td"><input type="text" id="so_symbol" name="so_symbol" class="input easyui-validatebox" validType="maxLength[255]" value="${(org.so_symbol)!}" style="width:300px;"/></td>
            </tr>
            
            <tr>
                <td class="th">省份</td>
                <td class="td"><input type="text" id="so_province" name="so_province" class="input easyui-validatebox" validType="maxLength[50]" value="${(org.so_province)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">城市</td>
                <td class="td"><input type="text" id="so_city" name="so_city" class="input easyui-validatebox" validType="maxLength[50]" value="${(org.so_city)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">区</td>
                <td class="td"><input type="text" id="so_area" name="so_area" class="input easyui-validatebox" validType="maxLength[50]" value="${(org.so_area)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">街道</td>
                <td class="td"><input type="text" id="so_street" name="so_street" class="input easyui-validatebox" validType="maxLength[255]" value="${(org.so_street)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">经度</td>
                <td class="td"><input type="text" id="so_longitude" name="so_longitude" class="input easyui-validatebox" validType="maxLength[255]" value="${(org.so_longitude)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">纬度</td>
                <td class="td"><input type="text" id="so_latitude" name="so_latitude" class="input easyui-validatebox" validType="maxLength[255]" value="${(org.so_latitude)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">横坐标</td>
                <td class="td"><input type="text" id="so_xcoordinate" name="so_xcoordinate" class="input easyui-validatebox" validType="maxLength[50]" value="${(org.so_xcoordinate)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">纵坐标</td>
                <td class="td"><input type="text" id="so_ycoordinate" name="so_ycoordinate" class="input easyui-validatebox" validType="maxLength[50]" value="${(org.so_ycoordinate)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构地址</td>
                <td class="td"><input type="text" id="so_address" name="so_address" class="input easyui-validatebox" validType="maxLength[255]" value="${(org.so_address)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构等级</td>
                <td class="td"><input type="text" id="so_grade" name="so_grade" class="input easyui-validatebox" validType="maxLength[10]" value="${(org.so_grade)!}" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">机构性质</td>
                <td class="td">
                	<input type="radio" id="rdo_nature1" name="so_nature" value="1" <#if org.so_nature==1>checked</#if>>市属
                	<input type="radio" id="rdo_nature2" name="so_nature" value="2" <#if org.so_nature==2>checked</#if>>区属
                	<input type="radio" id="rdo_nature3" name="so_nature" value="3" <#if org.so_nature==3>checked</#if>>社会办
                </td>
            </tr>
            <tr>
                <td class="th">是否是指导单位</td>
                <td class="td">
                	<input type="radio" id="rdo_leader1" name="so_leader_org_flag" value="1" <#if org.so_leader_org_flag==1>checked</#if>>是
                	<input type="radio" id="rdo_leader2" name="so_leader_org_flag" value="0" <#if org.so_leader_org_flag==0>checked</#if>>否
                </td>
            </tr>
            <tr>
                <td class="th">是否为牵头单位</td>
                <td class="td">
                	<input type="radio" id="rdo_guide1" name="so_guide_org_flag" value="1" <#if org.so_guide_org_flag==1>checked</#if>>是
                	<input type="radio" id="rdo_guide2" name="so_guide_org_flag" value="0" <#if org.so_guide_org_flag==0>checked</#if>>否
                </td>
            </tr>
            <tr>
                <td class="th">是否启用</td>
                <td class="td">
                	<input type="radio" id="rdo_enable1" name="so_enable" value="1" <#if org.so_enable==1>checked</#if>>是
                	<input type="radio" id="rdo_enable1" name="so_enable" value="0" <#if org.so_enable==0>checked</#if>>否
                </td>
            </tr>
            
        </tbody>
    </table>
</form>
 </div>
</body>
</html>