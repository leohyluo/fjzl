<#--
系统日志表管理
User: Created by auto build
Date: 2011-08-12 17:03:39
-->
<#include "/WEB-INF/view/macro.ftl"/>
<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>后台日志</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {
            $("body").layout();
            $("#form").css("display","block");

            $("#table").datagrid({
                url:"${path}/sys/log_page.do",
                onLoadError:showError,
                queryParams:{"refresh":"1"},
                sortName:"sl_date",    
                sortOrder:"desc",        
                pageNumber:1,           
                pageSize:20,
                onLoadSuccess:function(data){
                    delete $("#table").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    <#if checkDelete>
                    {field:'ck',checkbox:true},
                    </#if>
                    {title:'操作',field:'sl_id',width:40,sortable:false,align:"center",
                        formatter:function(value, data, index){
                            return "<a href=\"javascript:void(0)\" onclick=\"view(event, '" + value + "')\">查看</a>";
                        }
                    }
                ]],
                columns:[[
                    {title:'日志时间',field:'sl_date',width:130,sortable:true},
                    {title:'日志内容',field:'sl_description',width:150,sortable:true},
                    {title:'登录账号',field:'sl_user_code',width:100,sortable:true},
                    {title:'用户名称',field:'sl_user_name',width:100,sortable:true},
                    {title:'机构名称',field:'sl_org_name',width:100,sortable:true},
                    {title:'IP地址',field:'sl_ip',width:100,sortable:true},
                    {title:'类名称',field:'sl_class',width:150,sortable:true},
                    {title:'方法名称',field:'sl_method',width:100,sortable:true}
                ]],
                toolbar:[
                    {
                        text:"刷新",
                        iconCls:"icon-reload",
                        handler:refreshPage
                    } ,
                    "-",
                    {
                        <#if checkDelete>
                        handler:delBatch,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"删除选中日志",
                        iconCls:"icon-remove"
                    },
                    {
                        <#if checkDelete>
                        handler:delMonth,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"删除本月以前的日志",
                        iconCls:"icon-remove"
                    },
                    {
                        <#if checkDelete>
                        handler:delAll,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"删除所有日志",
                        iconCls:"icon-remove"
                    }
                ]
            });
        });


        <#if checkDelete>
        /**
         * 批量删除系统日志表
         */
        function delBatch(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要删除的记录", "info");  
            }
            else{
                $.messager.confirm("系统提示", "你确认要删除所选择的记录吗？", function(b){
                    if(b){
                        var sl_ids=new Array();
                        $.each(selections,function(i,o){
                           sl_ids.push(o.sl_id);
                        });

                        $.ajaxPost("${path}/sys/log_deleteBatch.do", {"sl_ids":sl_ids}, function(result) {
                            $.messager.show({
                                title:"消息提醒",
                                msg:"记录删除成功",
                                timeout:5000,
                                showType:"slide"
                            });
                            reloadPage();
                        });
                    }
                });
            }
        }

             /**
         * 删除一个月之前的日志
         */
        function delMonth(){
            $.messager.confirm("系统提示", "你确认要删除本月之前的日志吗？", function(b){
                if(b){
                    $.ajaxPost("${path}/sys/log_deleteMonth.do", null, function(result) {
                        window.parent.$.messager.show({
                            title:"消息提醒",
                            msg:"记录删除成功",
                            timeout:5000,
                            showType:"slide"
                        });
                        reloadPage();
                    })
                }
            });
        }

        function delAll(){
            $.messager.confirm("系统提示", "你确认要删除所有日志吗？", function(b){
                if(b){
                    $.ajaxPost("${path}/sys/log_deleteAll.do", null, function(result) {
                        window.parent.$.messager.show({
                            title:"消息提醒",
                            msg:"记录删除成功",
                            timeout:5000,
                            showType:"slide"
                        });
                        reloadPage();
                    })
                }
            });

        }
        </#if>

        /**
         * 查看系统日志表详细
         */
        function view(event,value){
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "详细查看",
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/sys/log_view.do?sl_id="+value);
        }

        /**
         * 刷新页面
         */
        function refreshPage(){
            window.location="${path}/sys/log_main.do";
            return false;
        }

        /**
         * 重载表格数据
         */
        function reloadPage(){
            $("#table").datagrid("options").queryParams.refresh="1";
            $("#table").datagrid("clearSelections").datagrid("reload");
        }

          /**
         * 查询翻页
         */
        function searchPage(reset) {
            if(reset) $("#form input:text").val("");      //重置查询表单
            var log = $("#form").serializeJson();         //表单序列化
            log.refresh = "1"                             //刷新记录数参数
            $("#table").datagrid("clearSelections").datagrid("load",log);
        }


    </script>
</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;padding:10px;">
    <form id="form" name="form" style="display: none">
        <table class="table">
            <tr>
                <td class="th">日志时间</td>
                <td class="td"><input id="sl_date_begin" name="sl_date_begin" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="sl_date_end" name="sl_date_end" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
	            <td class="th">日志内容</td>
	            <td class="td"><input id="sl_description" name="sl_description" type="text" class="input"></td>
	        </tr>
            <tr>
                <td class="th">登录账号</td>
                <td class="td"><input id="sl_user_code" name="sl_user_code" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">用户名称</td>
                <td class="td"><input id="sl_user_name" name="sl_user_name" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">机构名称</td>
                <td class="td"><input id="sl_org_name" name="sl_org_name" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">IP地址</td>
                <td class="td"><input id="sl_ip" name="sl_ip" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">类名称</td>
                <td class="td"><input id="sl_class" name="sl_class" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">方法名称</td>
                <td class="td"><input id="sl_method" name="sl_method" type="text" class="input"></td>
            </tr>
            <tr>
                <td colspan="2" style="text-align:center;" class="td">
                    <a href="javascript:searchPage(false)" class="easyui-linkbutton" icon="icon-search" id="searchBtn">查询</a>
                    <a href="javascript:searchPage(true)" class="easyui-linkbutton" icon="icon-reload" id="resetBtn">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div region="center" title="当前位置：用户管理&gt;&gt;后台日志">
    <table id="table"
           border="false"       <#--无边框-->
           fit="true"           <#--自动填充宽度高度-->
           singleSelect="false" <#--单选模式-->
           pagination="true"    <#--是否显示翻页导航-->
           rownumbers="true"    <#--是否显示行号-->
           striped="true"       <#--奇偶行颜色交错-->
           idField="sl_id"   <#--主键字段-->
           nowrap="true"        <#--单元格数据不换行-->
           >
    </table>
</div>
<div id="dialog" style="width:800px;height:400px; overflow:hidden;display: none" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>

</body>
</html>