<#include "/WEB-INF/view/macro.ftl"/>
<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>标题</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        var sr_id=null;
        $(function(){
            $("body").layout();
            $("#table").datagrid({
                fit:true,
                border:false,
                url:"${path}/sys/role_list.do",
                onLoadError:showError,
                idField:"sr_id",
                singleSelect:true,
                striped:true,
                columns:[[
                    {field:"sr_id",title:"操作",width:70,sortable:false,align:"center",
                        formatter:function(value, rowData, rowIndex) {
                            var link = "";
                            <#if checkEdit>
                            link += "<a href='javascript:void(0)' onclick=\"edit(event,'"+value+"')\">修改</a>&nbsp;";
                            <#else>
                            link += "<span style='color:#AAAAAA'>修改</span>&nbsp;";
                            </#if>

                            <#if checkDelete>
                            link += "<a href='javascript:void(0)' onclick=\"del(event,'"+value+"')\">删除</a>";
                            <#else>
                            link += "<span style='color:#AAAAAA'>删除</span>";
                            </#if>
                            return link;
                        }
                    },
                    {field:"sr_code",title:"角色编码",width:60,sortable:false},
                    {field:"sr_name",title:"角色名称",width:140,sortable:false}
                ]],
                toolbar:[
                    {
                        text:"刷新",
                        iconCls:"icon-reload",
                        handler:refreshPage
                    },
                    {
                        <#if checkAdd>
                        handler:add,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"添加",
                        iconCls:"icon-add"
                    }
                ],
                onClickRow:function(index,data){
                    sr_id = data.sr_id;
                    $('#menuTable').treegrid("reload");
                },
                onLoadSuccess:function(data){
                    if(data!=null && data.rows!=false){
                        if (sr_id == null || sr_id == "") {
                            sr_id = data.rows[0].sr_id;
                        }
                        $("#table").datagrid("selectRecord", sr_id);
                    }
                    else{
                        sr_id = null;
                    }
                    $('#menuTable').treegrid("reload");
                }
            });

            $('#menuTable').treegrid({
                fit:true,
                border:false,
                nowrap:false,
                collapsible:true,
                url:"${path}/sys/role_view.do",
				idField:'id',
				treeField:'sm_name',
				frozenColumns:[[
	                {title:'菜单名称',field:'sm_name',width:240,
		                formatter:function(value,data){
		                	return "<span class='sysMenu "+data.style +"'></span>"+value;
		                }
	                }
				]],
				columns:[[
					{field:'actionList',title:'菜单功能',width:600,
						formatter:function(value){
                            if(value == null||value.length==0) return "<span style='color:#888888'>无</span>";
                            else{
                                var s = "";
                                for(var i=0;i<value.length;i++){
                                    s+="<span class='sysAction "+ value[i].style +"'></span>" + value[i].sa_group + "&nbsp;&nbsp;";
                                }
                                return s;
                            }
						}
					}
				]],
                onBeforeLoad:function(row, param){
                    if(sr_id!=null) param.sr_id = sr_id;
                }
            });
        });


        /**
         * 刷新页面
         */
        function reloadPage(){
            $("#table").datagrid("reload");
        }

        function refreshPage(){
            window.location = "${path}/sys/role_main.do";
            return false;
        }


        <#if checkAdd>
        /**
         * 显示新建窗口
         */
        function add(){
            $("#dialog").css("display","block").dialog({
            	 title:"角色添加",
                 width:$.autoWidth(),
                 height:$.autoHeight(),
                 onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                 onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/sys/role_add.do");
        }
        </#if>

        <#if checkEdit>
        /**
         * 显示修改窗口
         * @param sr_id
         */
        function edit(event,sr_id){
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
            	title:"角色修改",
                width:$.autoWidth(),
                height:$.autoHeight(),
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/sys/role_edit.do?sr_id="+sr_id);
        }
        </#if>

        <#if checkDelete>
        /**
         * 删除角色
         * @param role_id
         */
        function del(event,p_sr_id){
            $.event.fix(event).stopPropagation();
            $.messager.confirm("提示信息", "你确认要删除该角色吗？",
                function(r) {
                    if (r) {
                        $.ajaxPost("${path}/sys/role_delete.do",{"sr_id":p_sr_id},function(result){
                            $.messager.show({
                                title:"消息提醒",
                                msg:"角色删除成功",
                                timeout:5000,
                                showType:"slide"
                            });
                        });
                        sr_id = null;
                        reloadPage();
                    }
                }
            );
        }
        </#if>

    </script>

    <style type="text/css">
        .sysMenu,.sysAction{
            display: inline-block;
            height: 18px;
            vertical-align: middle;
            width: 16px;
        }
    </style>

</head>
<body>
<div region="west" split="true" title="角色列表" style="width:300px;overflow: hidden;">
    <table id="table"></table>
</div>
<div region="center" title="菜单分配" style="overflow:hidden;">
    <table id="menuTable"></table>
</div>
<div id="dialog" style="width:800px;height:400px; overflow:hidden;display:none;" resizable="true" maximizable="true" modal="true">
    <iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0"></iframe>
</div>
</body>
</html>