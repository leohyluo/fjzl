<#include "/WEB-INF/view/macro.ftl"/>
<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户管理入口页面</title>
<#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {

            $("#tree").tree({
                url:"${path}/sys/user_tree.do",
                onLoadError:showError,
                onClick:function(node) {
                    var user = {"refresh":"1"};
                    if (node.id != 0)  user.so_code_parent = node.attributes.so_code2;
                    $("#table").datagrid("clearSelections").datagrid("load",user);
                }
            });

            $("#su_sex").combobox({
                data:[
                    {"id":"男","text":"男"},
                    {"id":"女","text":"女"}
                ]
            });

            $("#su_status").combobox({
                data:[
                    {"id":"0","text":"未启用"},
                    {"id":"1","text":"启用中"},
                    {"id":"2","text":"已禁用"}
                ]
            });


            //用户列表初始化
            $("#table").datagrid({
                sortName:"su_id",       <#--排序字段-->
                sortOrder:"asc",        <#--升降序-->
                pageNumber:1,           <#--当前默认页-->
                pageSize:20,   <#--页尺寸-->
                url:"${path}/sys/user_page.do",
                onLoadError:showError,
                rownumbers:true,
                singleSelect:true,
                queryParams:{"refresh":"1"},
                onLoadSuccess:function(data){
                    delete $("#table").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    {field:"su_id",title:"操作",width:130,align:"center",
                        formatter:function(value, data, index) {
                            var link="";
                            <#if checkEdit>
                            link += "<a href='javascript:void(0)' onclick=\"edit(event,'" + value + "')\">修改</a>&nbsp;" +
                                    "<a href='javascript:void(0)' onclick=\"editPass(event,'" + value + "')\">密码修改</a>&nbsp;";
                            <#else>
                            link += "<span style='color:#AAAAAA'>修改</span>&nbsp;" +
                                    "<span style='color:#AAAAAA'>密码修改</span>&nbsp;";
                            </#if>
                            <#if checkDelete>
                            if (data.su_admin == "0" && data.su_status == "0") {
                                link += "<a href='javascript:void(0)' onclick=\"del(event,'" + value + "')\">删除</a>";
                            }
                            else {
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
                        {field:"su_code",title:"用户帐号",width:100,sortable:true},
                        {field:"su_name",title:"用户名称",width:100,sortable:true,
                            formatter:function(value, data, index) {
                                return "<a href='javascript:void(0)' onclick=\"view('" + data.su_id + "')\">" + value + "</a>";
                            }
                        },
                        {field:"so_name",title:"所在机构",width:100,sortable:true},
                        {field:"su_status",title:"状态",width:60,sortable:true,
                            formatter:function(value, data, index) {
                                if (value == "0") return "<label style='color:#AAAAAA'>未启用</label>";
                                else if (value == "1") return "<label style='color:#000000'>启用中</label>";
                                else if (value == "2") return "<label style='color:#FF0000'>已禁用</label>";
                                else return value;
                            }
                        },
                        {field:"su_sex",title:"性别",width:40,sortable:true},
                       <#-- {field:"su_contact",title:"联系方式",width:120,sortable:true},-->
                        
                        {field:"su_last_ip",title:"用户上次登录IP",width:120,sortable:true},
                        {field:"su_last_time",title:"用户上次登录时间",width:120,sortable:true},
                        {field:"su_login_count",title:"用户登录次数",width:120,sortable:true},
                        
                        <#-- {field:"su_email",title:"邮箱",width:200,sortable:true},-->
                        {field:"su_admin",title:"身份",width:80,sortable:true,
                            formatter:function(value, data, index) {
                                return value == "1" ? "<label style='color:#0000FF'>超级管理员</label>" : "普通用户";
                            }
                        }
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

            $("#form input.combo-f").combo("setText", "请选择");

        });

        <#if checkAdd>
        function add() {
            var so_id = "0";
            var node = $("#tree").tree("getSelected");
            if(node!=null) so_id=node.id;

            $("#dialog").css("display","block").dialog({
                title:"用户添加",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${path}/sys/user_add.do?so_id=" + so_id);
        }
        </#if>

        <#if checkEdit>
        function edit(event,su_id) {
            $("#dialog").css("display","block").dialog({
            	title:"用户修改",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${path}/sys/user_edit.do?su_id=" + su_id);
        }

        function editPass(event,su_id) {
            $("#dialog").css("display","block").dialog({
                title:"用户密码修改",
                width:640,
                height:300,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${path}/sys/user_editPass.do?su_id=" + su_id);
        }
        </#if>

        <#if checkDelete>
        function del(event,value) {
            $.event.fix(event).stopPropagation();
            $.messager.confirm("提示信息", "你确认要删除该用户吗？",
                function(r) {
                    if (r) {
                        $.ajaxPost("${path}/sys/user_delete.do",{"su_id":value},function(result){
                            var msg = "";
                            if (result == "1") msg = "用户删除成功";
                            else if (result == "2") msg = "该用户是系统内置用户，不能被删除";
                            else if (result == "3") msg = "用户已经启用，不能删除";
                            $.messager.show({
                                title:"消息提醒",
                                msg:msg,
                                timeout:5000,
                                showType:"slide"
                            });
                            refreshPage();
                        });
                    }
                }
            );
        }
        </#if>

        //查看页面
        function view(su_id) {
        	$("#dialog").css("display","block").dialog({
            	title:"用户查看",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${path}/sys/user_view.do?su_id=" + su_id);

        }


        //刷新页面
        function refreshPage() {
            window.location = "${path}/sys/user_main.do";
            return false;
        }

        function reloadPage(){
            $("#tree").tree("reload");
            $("#table").datagrid("options").queryParams.refresh="1";
            $("#table").datagrid("clearSelections").datagrid("reload");
        }

        //查询表单
        function searchPage(reset) {
            if(reset){
                $("#form input:text").val("");      //重置查询表单
                $("#form input.combobox-f").combobox("clear").combobox("setText", "请选择");
            }
            var user = $("#form").serializeJson();
            user.refresh = "1";                             //刷新记录数参数
            $("#table").datagrid("clearSelections").datagrid("load",user);
        }

    </script>
</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;">
    <div id="accordion" class="easyui-accordion" fit="true" border="false">
        <div title="机构树" selected="true" style="overflow:auto;">
            <ul id="tree"></ul>
        </div>
        <div title="查询" style="padding:10px">
            <form id="form" name="form">
                <table class="table">
                    <tr>
                        <td class="th">用户帐号</td>
                        <td class="td"><input type="text" id="su_code" name="su_code" class="input"/></td>
                    </tr>
                    <tr>
                        <td class="th">用户名称</td>
                        <td class="td"><input type="text" id="su_name" name="su_name" class="input"/></td>
                    </tr>
                    <tr>
                        <td class="th">性别</td>
                        <td class="td">
                            <input id="su_sex" name="su_sex" editable="false" panelHeight="100" valueField="id" textField="text"  class="input">
                        </td>
                    </tr>
                    <tr>
                        <td class="th">状态</td>
                        <td class="td">
                            <input id="su_status" name="su_status" editable="false" panelHeight="100" valueField="id" textField="text" class="input">
                        </td>
                    </tr>
                    <tr>
                        <td class="th">联系方式</td>
                        <td class="td"><input id="su_contact" name="su_contact" class="input"></td>
                    </tr>
                    <tr>
                        <td class="th">邮箱</td>
                        <td class="td"><input id="su_email" name="su_email" class="input"></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align:center;"  class="td">
                            <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-search" id="searchBtn" onclick="searchPage(false)">查询</a>
                            <a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-reload" id="reloadBtn" onclick="searchPage(true)">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div region="center" title="当前位置：系统管理&gt;&gt;用户管理">
    <table id="table"
           border="false"       <#---->
           fit="true"           <#--自动填充宽度高度-->
           pagination="true"    <#--是否显示翻页导航-->
           rownumbers="true"    <#--是否显示行号-->
           striped="true"       <#--奇偶行颜色交错-->
           idField="su_id"      <#--主键字段-->
           nowrap="true"        <#--单元格数据不换行-->
            >
    </table>
</div>

<div id="dialog" style="width:800px;height:400px; overflow:hidden;display:none;" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>

</body>
</html>