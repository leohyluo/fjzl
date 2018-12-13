<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>标题</title>
<#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        <#--加载-->
        $(function() {
            $("body").click(function(){
                $("#iconSpan").css("display","none");
            });

            var tree = ${tree!"null"};
            var id = '<#if menu.sm_parentid==0>${menu.sap_id}<#else>${menu.sm_parentid}</#if>';

            $("#sm_parentid").combotree({
                data:tree,
                value:id
            });
            var selected = $("#sm_parentid").combotree("tree").tree("find", id);
            if (selected != null) {
                $("#sm_parentid").combotree("tree").tree("select", selected.target);
                $("#sm_parentid").combotree("tree").tree("expandTo", selected.target);
            }

            var comboData = [
                {"id":"1","text":"菜单项"},
                {"id":"0","text":"分割线"}
            ];
            $("#sm_type").combobox({
                    <#if menu.sm_child=="1"||menu.sm_admin=="1"><#--当有子菜单时，菜单类型只能为菜单项-->
                        disabled:true,
                    <#elseif menu.sm_child=="0"><#--无子菜单时，菜单类型可以修改-->
                        onSelect:changeType,
                    </#if>
                        editable:false,
                        data:comboData,
                        value:"${menu.sm_type}"
                    });

            $("#list tbody a.delAction").click(deleteRow);
            $("#list tbody a.addAction").click(addRow);

            $("#sm_icon_span").click(function(event){
                var offset = $(this).offset();
                $("#iconSpan").css("display","block").offset(offset);
                event.stopPropagation();
            });

            $('#iconSpan a').click(function(){
                var css = $(this).attr("class");
                var title = $(this).attr("title");
                $("#sm_icon_span").removeClass().addClass(css);
                $('#iconSpan a').removeClass("choice");
                $(this).addClass("choice");
                $("#sm_icon").val(css);
            });


            $("body").layout();
        });

        <#--改变菜单类型-->
        function changeType(record) {
            if (record.id == "0") { <#--分割线-->
                $("#sm_name").attr({readonly:true, value:"──────"}).validatebox("validate");
                $("#sm_action").attr({readonly:true, value:""});
                $("#fieldset2").hide();
                $("#list tbody:eq(0) input:text").validatebox({required:false}).validatebox("validate");
            }
            else { <#--菜单项-->
                $("#sm_name").attr({readonly:false, value:""}).validatebox("validate");
                $("#sm_action").attr({readonly:false});
                $("#fieldset2").show();
                $("#list tbody:eq(0) tr:visible input:text").validatebox({required:true}).validatebox("validate");
            }
        }

        <#--加行-->
        function addRow() {
            var sa_class = $("#list tbody:eq(0) tr:visible input[name=sa_class]:last").val();
            var row = $($("#temp").val());
            row.insertBefore($("#list tbody:eq(0) tr:last"));
            row.find("input[name=sa_class]").val(sa_class);
            row.find("a.delAction").click(deleteRow).linkbutton();
            row.find("input").validatebox();
        }

        <#--删行-->
        function deleteRow() {
            $(this).unbind();
            var row = $(this).closest("tr");
            var status = $(this).siblings("input[name=status]");
            if (status.val() == "0") {<#--新添加的-->
                row.find("input").validatebox("destroy");
                row.remove();
            }
            else if (status.val() == "1") {<#--已保存的-->
                status.val("2");
                row.find("input:text").validatebox({required:false}).validatebox("validate");
                row.css("display","none");
            }
        }

        <#--修改提交-->
        function editSave() {
            if (!$("#editForm").form("validate")) return;
            var json = $("#table").serializeJson();
            var selected = $("#sm_parentid").combotree("tree").tree("getSelected");
            json.sap_id = selected.attributes.sap_id;
            json.sm_parentid = selected.attributes.sm_parentid;
            var jsonArray = null;
            if (json.sm_type == "1") {
                jsonArray = $("#list tbody:eq(0)").serializeJsonArray("tr");
            }
            $.ajaxPost("${path}/sys/menu_editSave.do", {"menu":json,"actionList":jsonArray}, function(result) {
                if (result == "1") {
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"菜单修改成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    window.parent.$.closeDialog('dialog');
                    window.parent.reloadPage(json.sap_id, json.sm_parentid);
                }
            });

        }

    </script>

    <style type="text/css">
        #iconSpan{
            position: absolute;
            border: 1px solid #D3D3D3;
            width: 200px;
            background-color: #FFFFFF;
            display: none;
            padding: 5px;
        }

        #iconSpan a{
            display: block;
            width: 18px;
            height: 18px;
            border: 1px solid #ffffff;
            background-position: center;
            float: left;
            font-size: 12px;
        }

        #iconSpan a:hover{
            border: 1px solid #000000;
        }

        #iconSpan a.choice{
            border: 1px solid #000000;
            background-color:#00ee00;
        }

        #sm_icon_span{
            width: 18px;
            height: 18px;
            padding: 0;
            margin: 0;
            display: inline-block;
            border: 1px solid #D3D3D3;
            cursor: pointer;
            background-position: center;
        }

    </style>

</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:editSave()" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding: 5px">
    <form id="editForm" name="editForm">
        <fieldset class="fieldset" id="fieldset1">
            <legend class="legend">菜单信息</legend>
            <table id="table" class="table-border" width="100%">
                <tbody>
                <tr>
                    <th class="th" width="80">菜单图标</th>
                    <td class="td"><span id="sm_icon_span" title="请选择" class="<#if (menu.sm_icon)??>${menu.sm_icon}<#else>icon-blank</#if>">
                                   </span><input id="sm_icon" name="sm_icon" type="hidden" value="${menu.sm_icon}"></td>
                </tr>
                <tr>
                    <td class="th">菜单名称<input id="sm_id" name="sm_id" type="hidden" value="${menu.sm_id}"/></td>
                    <td class="td">
                        <input id="sm_name" name="sm_name" class="input easyui-validatebox" required="true" type="text"
                               validType="maxLength[40]" value="${menu.sm_name}" style="width: 300px" <#if menu.sm_type=="0">readonly</#if>/></td>
                </tr>
                <tr>
                    <td class="th" >父菜单</td>
                    <td class="td"><input id="sm_parentid" name="sm_parentid" style="width:308px"/></td>
                </tr>
                <tr>
                    <td class="th" >菜单类型</td>
                    <td class="td"><input id="sm_type" name="sm_type" style="width:308px" type="text" valueField="id"  textField="text"/></td>
                </tr>
                <tr>
                    <td class="th" >菜单动作</td>
                    <td class="td"><input id="sm_action" name="sm_action" class="input easyui-validatebox" validType="maxLength[100]"
                               type="text" value="${menu.sm_action}"  style="width: 300px" <#if menu.sm_type=="0">readonly</#if> /></td>
                </tr>
                <tr>
                    <td class="th" >备注</td>
                    <td class="td"><input id="sm_content" name="sm_content" class="input easyui-validatebox"  style="width: 300px"
                               validType="maxLength[200]" type="text" value="${menu.sm_content}"/></td>
                </tr>
                </tbody>
            </table>
        </fieldset>
        <br>
        <fieldset class="fieldset" id="fieldset2" style="<#if menu.sm_type=="0"||menu.sm_child=="1">display:none</#if>">
            <legend class="legend">菜单功能</legend>
            <table class="table-border" id="list" width="100%" >
                <col width="80">
                <col width="180">
                <col>
                <thead>
                <tr>
                    <td class="td" style="text-align: center;">操作</td>
                    <td class="td">功能组名</td>
                    <td class="td">控制器名</td>
                </tr>
                </thead>
                <tbody>
                <#if actionList??>
                    <#list actionList as item >
                    <tr>
                        <td class="td" style="text-align: center">
                            <a href="javascript:void(0)" plain="true" class="easyui-linkbutton delAction" icon="icon-remove">删除</a>
                            <input name="status" type="hidden" value="1"/>
                            <input name="sa_id" type="hidden" value="${item.sa_id}"/>
                        </td>
                        <td class="td">
                            <input type="text" name="sa_group" class="input easyui-validatebox" validType="maxLength[20]"
                                   <#if menu.sm_type=="1"||menu.sm_child=="0">required="true"</#if> style="width:120px;" value="${item.sa_group}"/>
                        </td>
                        <td class="td">
                            <input type="text" name="sa_class" class="input easyui-validatebox" validType="maxLength[100]"
                                   <#if menu.sm_type=="1"||menu.sm_child=="0">required="true"</#if> style="width:250px;" value="${item.sa_class}"/>
                        </td>
                    </tr>
                    </#list>
                </#if>
                <tr>
                    <td class="td" style="text-align: center;">
                        <a href="javascript:void(0)" id="addBtn"  plain="true" class="easyui-linkbutton addAction" icon="icon-add">添加</a>
                    </td>
                    <td class="td">&nbsp;</td>
                    <td class="td">&nbsp;</td>
                </tr>
                </tbody>
            </table>
        </fieldset>
        <textarea id="temp" style="display:none">
            <tr>
                <td class="td" style="text-align: center">
                    <a href="javascript:void(0)" plain="true" class="easyui-linkbutton delAction" icon="icon-remove">删除</a>
                    <input type="hidden" name="status" value="0"/>
                    <input type="hidden" name="sa_id" value=""/>
                </td>
                <td class="td">
                    <input name="sa_group" type="text" class="input easyui-validatebox" validType="maxLength[20]" required="true" style="width:120px;"/>
                </td>
                <td class="td">
                    <input name="sa_class" type="text" class="input easyui-validatebox" validType="maxLength[100]" required="true" style="width:250px;"/>
                </td>
            </tr>
        </textarea>
    </form>

    <div id="iconSpan">
    <#list icons?keys as key>
        <a href="javascript:void(0)" href="javascript:void(0)" class="${key}<#if key==((menu.sm_icon)!)> choice</#if>" title="${icons[key]}"></a>
    </#list>
    </div>


</div>
</body>
</html>