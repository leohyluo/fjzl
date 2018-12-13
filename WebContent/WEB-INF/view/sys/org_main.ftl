<#include "/WEB-INF/view/macro.ftl"/>
<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>机构管理</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">

        $(function(){
            $("#tree").tree({
                url:"${path}/sys/org_tree.do",
                onLoadError:showError,
                onClick:function(node) {
                    var org = {"refresh":"1"};
                    if (node.id != 0)  org.so_code_parent = node.attributes.so_code2;
                    $("#table").datagrid("clearSelections").datagrid("load",org);
                }
            });

            $("#table").datagrid({
                url:"${path}/sys/org_page.do",
                sortName:"so_code",     <#--排序字段-->
                sortOrder:"asc",        <#--升降序-->
                pageNumber:1,           <#--当前默认页-->
                pageSize:20,            <#--页尺寸-->
                queryParams:{"refresh":"1"},
                onLoadError:showError,
                onLoadSuccess:function(data){
                    delete $("#table").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    {field:"so_id",title:"操作",width:80,align:"center",
                        formatter:function(value, rowData, rowIndex) {
                            var link = "";
                            <#if checkEdit>
                            link = "<a href='javascript:void(0)' onclick=\"edit('" + value + "')\">修改</a>&nbsp;";
                            <#else>
                            link = "<span style='color:#AAAAAA'>修改</span>&nbsp;";
                            </#if>
                            <#if checkDelete>
                            if(rowData.so_child=="0"&&rowData.so_used=="0"){
                                link +="<a href='javascript:void(0)' onclick=\"del('" + value + "')\">删除</a>";
                            }
                            else{
                                link+="<span style='color:#AAAAAA'>删除</span>";
                            }
                            <#else>
                            link+="<span style='color:#AAAAAA'>删除</span>";
                            </#if>
                            return link;
                        }
                    }
                ]],
                columns:[
                    [                      
                        {field:"so_code",title:"机构编码",width:100,sortable:true,hidden:true},
                        {field:"so_name",title:"机构名称",width:150,sortable:true,formatter:function(value, rowData, rowIndex){
                            return "<a href='javascript:void(0)' onclick=\"view('"+rowData.so_id+"')\">"+value+"</a>";
                        }},
                        {title:'机构性质',field:'so_nature',width:150,sortable:true,formatter:function(value, rowData, rowIndex){
							if(value == '1') {
								return "市属";
							} else if(value == '2') {
								return "区属";
							} else if(value == "3") {
								return "社会办";
							}
							return "";
						}},
						{title:'机构类型',field:'so_type',width:150,sortable:true,formatter:function(value, rowData, rowIndex){
                        	if(value == '1') {
                        		return "医联体";
                        	} else if(value == '2') {
                        		return '医院';
                        	} else if (value == '3') {
                        		return '社康';
                        	}
                        	return '';
                        }},
						{title:'机构等级',field:'so_grade',width:150,sortable:true},
                        {field:"so_parentname",title:"上级机构",width:150,sortable:true, formatter:function(value, rowData, rowIndex) {
                            return !$.isEmpty(value)?value:"无";
                        }},
                        {title:'联系人',field:'so_contactor',width:150,sortable:true},
                        {field:"so_contact",title:"联系方式",width:150,sortable:true},
                        {field:"so_email",title:"邮箱地址",width:200,sortable:true},
                        {field:"so_post",title:"邮编",width:100,sortable:true},
                        
                    	{title:'拼音助记符',field:'so_symbol',width:150,sortable:true,hidden:true},
						{title:'省份',field:'so_province',width:150,sortable:true,hidden:true},
						{title:'城市',field:'so_city',width:150,sortable:true,hidden:true},
						{title:'区',field:'so_area',width:150,sortable:true,hidden:true},
						{title:'街道',field:'so_street',width:150,sortable:true,hidden:true},
						{title:'经度',field:'so_longitude',width:150,sortable:true},
						{title:'纬度',field:'so_latitude',width:150,sortable:true},
						{title:'横坐标',field:'so_xcoordinate',width:150,sortable:true},
						{title:'纵坐标',field:'so_ycoordinate',width:150,sortable:true},
						{title:'机构地址',field:'so_address',width:150,sortable:true},
						
						{title:'机构状态',field:'so_status',width:150,sortable:true,formatter:function(value, rowData, rowIndex){
							if(value == "1") {
								return "已审核";
							} else {
								return "未审核";
							}
						},hidden:true},
						{title:'是否是指导单位',field:'so_leader_org_flag',width:150,sortable:true, formatter:function(value, rowData, rowIndex){
							if(value == "1") {
								return "是";
							} else {
								return "否";
							}
						}},
						{title:'是否为牵头单位',field:'so_guide_org_flag',width:150,sortable:true, formatter:function(value, rowData, rowIndex){
							if(value == "1") {
								return "是";
							} else {
								return "否";
							}
						}},
						{title:'是否启用',field:'so_enable',width:150,sortable:true,formatter:function(value, rowData, rowIndex){
							if(value == "1") {
								return "启用";
							} else {
								return "停用";
							}
						}},
						{title:'创建者',field:'so_creatorName',width:150,sortable:true},
						{title:'创建时间',field:'so_create_time',width:150,sortable:true},
						{title:'最后修改者',field:'so_lastUpdatorName',width:150,sortable:true},
						{title:'最后修改时间',field:'so_last_upd_time',width:150,sortable:true},
						{title:'审核者',field:'so_revieworName',width:150,sortable:true,hidden:true},
						{title:'审核时间',field:'so_review_time',width:150,sortable:true,hidden:true}
                    ]
                ],
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
                ]
            });

        });

        <#if checkAdd>
        function add(){
            var so_id = "0";
            var so_code = "";
            var node = $("#tree").tree("getSelected");
            if(node!=null){
                so_id=node.id;
                so_code= node.attributes.so_code2;
                if(so_code.length>=30){
                    $.messager.alert("系统提示","机构级别过多，不能添加子机构","info");
                    return;
                }
            }
            $("#iframe").attr("src","${path}/sys/org_add.do?so_id="+so_id+"&so_code2="+so_code);
            $("#dialog").css("display","block").dialog({
                title:"部门添加",
				width:700,
                height:window.$.autoHeight(),
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });        
            }
        </#if>
        
        <#if checkEdit>
        function edit(so_id){
            $("#iframe").attr("src","${path}/sys/org_edit.do?so_id="+so_id);
            $("#dialog").css("display","block").dialog({
                title:"机构修改",
                width:700,
                height:window.$.autoHeight(),
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
        }
        </#if>
		
        <#if checkDelete>
        function del(val){
            $.messager.confirm("提示信息", "你确认要删除该机构吗？",
                function(r) {
                    if (r) {
                        var selectedMenu = $("#table").datagrid("getSelected");
                        var org_parentid=selectedMenu.org_parentid;
                        $.ajaxPost("${path}/sys/org_delete.do",{"so_id":val},function(result){
                            var msg="";
                            if(result=="1") msg = "机构删除成功";
                            else if(result=="2") msg = "机构正在使用中，不能删除";
                            $.messager.show({
                                title:"消息提醒",
                                msg:msg,
                                timeout:5000,
                                showType:"slide"
                            });
                            reloadPage();
                        });
                    }
                }
            );

        }
        </#if>

        function view(id){  
            $("#iframe").attr("src","${path}/sys/org_view.do?so_id="+id);
            $("#dialog").css("display","block").dialog({
                title:"机构查看",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
        }

        /**
         * 刷新页面
         */
        function refreshPage(){
            window.location="${path}/sys/org_main.do";
            return false;
        }

        /**
         * 重载表格数据
         */
        function reloadPage(){
            $("#tree").tree("reload");
            $("#table").datagrid("options").queryParams.refresh="1";
            $("#table").datagrid("clearSelections").datagrid("reload");
        }

          /**
         * 查询翻页
         */
        function searchPage(reset) {
            if(reset) $("#form input:text").val("");      //重置查询表单
            var org = $("#form").serializeJson();         //表单序列化
            org.refresh = "1";                             //刷新记录数参数
            $("#table").datagrid("clearSelections").datagrid("load",org);
        }


    </script>

</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;">   
    <div id="accordion" class="easyui-accordion" fit="true" border="false">
        <div title="机构树" style="overflow:auto;">
            <ul id="tree"></ul>
        </div>
        <div title="机构查询"  style="overflow:auto;padding:10px;">
            <form id="form" name="form">
            <table class="table">
                <tr>
                    <td class="th">机构名称</td>
                    <td class="td"><input type="text" name="so_name" id="so_name" class="input"></td>
                </tr>
                <tr>
                    <td class="th">机构编码</td>
                    <td class="td"><input type="text" name="so_code" id="so_code" class="input"></td>
                </tr>
                <tr>
                    <td  class="td" colspan="2" style="text-align:center;">
                        <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-search" id="searchBtn" onclick="searchPage(false)">查询</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-reload" id="resetBtn" onclick="searchPage(true)">重置</a>
                    </td>                 
                </tr>
            </table>
            </form>
        </div>
    </div>
</div>
<div region="center" title="当前位置：系统管理&gt;&gt;机构管理">
    <table id="table"
        border="false"       <#---->
        fit="true"           <#--自动填充宽度高度-->
        pagination="true"    <#--是否显示翻页导航-->
        rownumbers="true"    <#--是否显示行号-->
        striped="true"       <#--奇偶行颜色交错-->
        idField="so_id"      <#--主键字段-->
        nowrap="true"        <#--单元格数据不换行-->
        singleSelect="true">  <#--单选模式-->
    </table>
</div>

<div id="dialog" style="width:640px;height:400px; overflow:hidden;display:none;" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>

</body>
</html>