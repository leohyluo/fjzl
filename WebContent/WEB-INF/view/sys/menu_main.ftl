<#include "/WEB-INF/view/macro.ftl"/>
<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>菜单管理</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        var sap_id = '${(app.sap_id)!0}';
        var sm_parentid = '0';

        $(function(){
            $("#tree").tree({
                url:"${path}/sys/menu_tree.do",
                onLoadError:showError,
                onClick:function(node) {
                    sap_id = node.attributes.sap_id;
                    sm_parentid = node.attributes.sm_parentid;
                    $("#table").datagrid("clearSelections").datagrid("load");
                },
                onLoadSuccess:function(node, data){
                    var selected = $("#tree").tree("find", sm_parentid == 0 ? sap_id : sm_parentid);
                     if (selected!=null){
                        $("#tree").tree("select", selected.target);
                        $("#tree").tree("expandTo", selected.target);
                    }
                }
            });


            $("#table").datagrid({
                onLoadError:showError,
                url:"${path}/sys/menu_list.do",
                onBeforeLoad:function(param) {
                    var node = getNode();
                    if(node!=null){
                        param.sap_id = node.attributes.sap_id;
                        param.sm_parentid = node.attributes.sm_parentid;
                    }
                    else{
                        param.sap_id = sap_id;
                        param.sm_parentid = sm_parentid;
                    }
                },
                frozenColumns:[[
                    {field:"sm_id",title:"操作",width:80,align:"center",
                        formatter:function(value, data) {
                            var link="";
                            <#if checkEdit>
                            link += "<a href='javascript:void(0)' onclick=\"edit('" + value + "')\">修改</a>&nbsp;";
                            <#else>
                            link += "<span style='color:#AAAAAA'>修改</span>&nbsp;";
                            </#if>
                            <#if checkDelete>
                            if (data.sm_child == "0" && data.sm_admin == "0") {
                                link +="<a href='javascript:void(0)' onclick=\"del('" + value + "')\">删除</a>";
                            }
                            else{
                                link += "<span style='color:#AAAAAA'>删除</span>";
                            }
                            <#else>
                            link += "<span style='color:#AAAAAA'>删除</span>";
                            </#if>
                            return link;
                        }
                    }
                ]],
                columns:[
                    [
                        {field:"sm_icon",title:"菜单图标",width:60,sortable:false,align:"center",
                            formatter:function(val, data) {
                                return !$.isEmpty(val) ? "<div style='width:16px;height:16px;margin:auto' class='"+val+"'></div>":"";
                            }
                        },
                        {field:"sm_name",title:"菜单名称",width:200,sortable:false,
                            formatter:function(val, data){
                                return "<a href='javascript:void(0)' onclick=\"view('"+data.sm_id+"')\">"+val+"</a>";
                            }
                        },
                        {field:"sm_parentid",title:"上级菜单",width:200,sortable:false,
                            formatter:function(val, data) {
                                return val==0 ? "无" : data.sm_parentname;
                            }
                        },
                        {field:"sap_id",title:"系统名称",width:200,sortable:false,
                            formatter:function(val, data) {
                                return data.sap_name;
                            }
                        },
                        {field:"sm_admin",title:"管理员菜单",width:80,sortable:false,
                            formatter:function(val, data){
                                return val=="1"?"是":"<label style='color:#AAA'>否</label>";
                            }
                        },
                        {field:"sm_type",title:"菜单类型",width:60,sortable:false,
                            formatter:function(val, data) {
                                return val=="1"?"菜单项":"<label style='color:#00F'>分割线</label>";
                            }
                        },
                        {field:"sm_child",title:"子菜单",width:60,sortable:false,
                            formatter:function(val, data) {
                                return val=="1"?"有子菜单":"<label style='color:#AAA'>无子菜单</label>";
                            }
                        },
                        {field:"sm_action",title:"菜单动作",width:250,sortable:false},
                        {field:"sm_content",title:"菜单备注",width:200,sortable:false}
                    ]
                ],

                toolbar:[
                    {
                        text:"刷新",
                        iconCls:"icon-reload",
                        handler:refreshPage
                    },
                    "-",
                    {
                        <#if checkAdd>
                        handler:add,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"添加",
                        iconCls:"icon-add"
                    },
                    {
                        <#if checkEdit>
                        handler:order,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"排序",
                        iconCls:"icon-sort"
                    }
                ]
            });
        });

        <#if checkAdd>
        function add(){
            var node = getNode();
            if (node == null) return;
            //if (node.attributes.sm_admin == "1") return;
            if (node.attributes.sm_type == "0") {
                $.messager.alert("系统提示", "菜单分割线不能设置子菜单", "info");
                return;
            }

            $("#iframe").attr("src", "${path}/sys/menu_add.do?sap_id=" + node.attributes.sap_id + "&sm_parentid=" + node.attributes.sm_parentid);
            $("#dialog").css("display", "block").dialog({
                title:"菜单添加",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function() { $.restoreDialog("dialog") }
            });
        }
        </#if>

        <#if checkEdit>
        function edit(sm_id){
            $("#iframe").attr("src","${path}/sys/menu_edit.do?sm_id="+sm_id);
            $("#dialog").css("display","block").dialog({
                title:"菜单修改",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function() { $.restoreDialog("dialog") }
            });
        }

        function order(){
            var data = $("#table").datagrid("getData");
            if(!data||!data.rows) return;
            var count = data.rows.length;
            if(count<2) return;

            $("#iframe").attr("src","${path}/sys/menu_order.do?sap_id="+sap_id+"&sm_parentid="+sm_parentid);
            $("#dialog").css("display","block").dialog({
                title:"菜单排序",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function() { $.restoreDialog("dialog") }
            });
        }
        </#if>

        <#if checkDelete>
        function del(val){
            $.messager.confirm("提示信息", "您确认要删除该菜单吗？",
                function(r) {
                    if (r) {
                        var selected = $("#table").datagrid("getSelected");
                        var sap_id=selected.sap_id;
                        var sm_parentid=selected.sm_parentid;
                        $.ajaxPost("${path}/sys/menu_delete.do",{"sm_id":val},function(result){
                            var msg="";
                            if(result=="1"){
                                msg="菜单删除成功";
                            }
                            else if(result=="2"){
                                msg="该菜单已经设置了子菜单，请先删除子菜单";
                            }
                            else if(result == "3"){
                                msg="该菜单是管理员菜单，不能删除";
                            }
                            $.messager.show({
                                title:"消息提醒",
                                msg:msg,
                                timeout:5000,
                                showType:"slide"
                            });
                            reloadPage(sap_id,sm_parentid);
                        });
                    }
                }
            );

        }
        </#if>

        function getNode(){
            var node = $("#tree").tree("getSelected");
            if (node == null) node = $("#tree").tree("getRoot");
            return node;
        }

        function view(id){
            $("#iframe").attr("src","${path}/sys/menu_view.do?sm_id="+id);
            $("#dialog").css("display","block").dialog({
                title:"菜单查看",
                onMove:function(left,top){ $.adjustPosition("dialog",left,top) },
                onBeforeClose:function() { $.restoreDialog("dialog") }
            });
        }

        /**
         * 刷新页面
         */
        function reloadPage(p_sap_id,p_sm_parentid){
        	sap_id = p_sap_id;
        	sm_parentid = p_sm_parentid;
            $("#tree").tree("reload");
            $("#table").datagrid("reload");
        }

        function refreshPage(){
            window.location = "${path}/sys/menu_main.do";
            return false;
        }

    </script>
</head>
<body class="easyui-layout">

<div region="west" split="true" border="true" title="操作面板" style="width:240px;">
    <ul id="tree"></ul>
</div>
<div region="center" title="当前位置：系统管理&gt;&gt;菜单设置">
    <table  id="table"
            border="false"          <#--是否有边框-->
            fit="true"              <#--自动填充宽度高度-->
            pagination="false"      <#--是否显示翻页导航-->
            rownumbers="true"       <#--是否显示行号-->
            striped="true"          <#--奇偶行颜色交错-->
            idField="sm_id"         <#--主键字段-->
            nowrap="true"           <#--单元格数据不换行-->
            singleSelect="true">    <#--单选模式-->
    </table>
</div>

<div id="dialog" style="width:700px;height:400px; overflow:hidden;display:none;" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>

</body>
</html>