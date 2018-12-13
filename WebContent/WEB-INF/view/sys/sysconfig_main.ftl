<#--
版权：融贯资讯 <br/>
作者：xuan.zhou@rogrand.com <br/>
生成日期：2013-12-17 <br/>
描述：系统配置主页面
-->
<#include "/WEB-INF/view/macro.ftl"/>

<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#--
<#assign checkDelete= check("删除")>
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>系统配置管理</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {
            $("body").layout();
            $("#form").css("display","block");
            // $('#form input.easyui-numberbox').numberbox();
            // $("#form input.combo-f").combo("setText", "请选择");

            $("#table").datagrid({
                onLoadError:showError,
                sortName:"configid",    
                sortOrder:"asc",        
                pageNumber:1,           
                pageSize:20,            
                queryParams:{"refresh":"1"},
                onLoadSuccess:function(data){
                    delete $("#table").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    {title:'操作',field:'configid',width:80,sortable:false,align:"center",
                        formatter:function(value, data, index){
                            var link= "<a href=\"javascript:void(0)\" onclick=\"view(event, '" + value + "')\">查看</a>&nbsp;";
                            <#if checkEdit>
                            if(data.configtype == 0){
                                link+="<a href=\"javascript:void(0)\" onclick=\"edit(event, '" + value + "')\">修改</a>&nbsp;";
                            }else{
                                link+="<span style='color:#808080'>修改</span>&nbsp;";
                            }
                            <#else>
                            link+="<span style='color:#808080'>修改</span>&nbsp;";
                            </#if>
                            <#--
                            <#if checkDelete>
                            link+="<a href=\"javascript:void(0)\" onclick=\"del(event, '" + value + "')\">删除</a>&nbsp;";
                            <#else>
                            link+="<span style='color:#808080'>删除</span>&nbsp;";
                            </#if>
                            -->
                            return link;
                        }
                    }
                ]],
                columns:[[
                    {title:'配置KEY',field:'configkey',width:200,sortable:true},
                    {title:'配置值',field:'configvalue',width:200,sortable:true},
                    {title:'配置描述',field:'configdesc',width:300,sortable:true},
                    {title:'配置类型',field:'configtype',width:100,sortable:true,
                        formatter:function(value, rowData, rowIndex){
                            if(value == 0){
                                return "用户配置";
                            }else{
                                return "系统配置";
                            }
                        },
                        styler:function(value, rowData, rowIndex){
                            if (value == 1){
                                return 'color:red;';
                            }
                        }
                    },
                    {title:'配置时间',field:'configaddtime',width:150,sortable:true}
                ]],
                toolbar:[
                    {
                        handler:refreshPage,
                        text:"刷新",
                        iconCls:"icon-reload"
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
                    }
                    <#--
                    ,
                    {
                        <#if checkDelete>
                        handler:delBatch,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"删除",
                        iconCls:"icon-remove"
                    }
                    -->
                ]
            });
        });

        /**
         * 显示系统配置添加对话框
         */
        function add() {
            $("#dialog").css("display","block").dialog({
                title: "记录添加",
                width:600,
                height:300,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${path}/sys/sysconfig_add.do");
        }

        /**
         * 显示系统配置修改对话框
         */
        function edit(event, value) {
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "记录修改",
                width:600,
                height:300,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/sys/sysconfig_edit.do?configid="+value);
        }

        /**
         * 删除系统配置
         */
        function del(event,val) {
            $.event.fix(event).stopPropagation();
            $.messager.confirm("提示信息", "你确认要删除该记录吗？", function(b) {
                if (b) {
                    $.ajaxPost("${path}/sys/sysconfig_delete.do",{"configid":val},function(result){
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

        /**
         * 批量删除系统配置
         */
        function delBatch(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要删除的记录", "info");  
            }
            else{
                $.messager.confirm("系统提示", "你确认要删除所选择的记录吗？", function(b){
                    if(b){
                        var configids=new Array();
                        $.each(selections,function(i,o){
                           configids.push(o.configid);
                        });

                        $.ajaxPost("${path}/sys/sysconfig_deleteBatch.do", {"configids":configids}, function(result) {
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
         * 查看系统配置详细
         */
        function view(event,value){
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "详细查看",
                width:600,
                height:300,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/sys/sysconfig_view.do?configid="+value);
        }

        /**
         * 刷新页面
         */
        function refreshPage(){
            window.location="${path}/sys/sysconfig_main.do";
            return false;
        }

        /**
         * 重载表格数据,刷新当前页 (添加、修改、删除后)
         */
        function reloadPage(){
            $("#table").datagrid("options").queryParams.refresh="1";
            $("#table").datagrid("clearSelections").datagrid("reload");
        }

          /**
         * 查询翻页
         */
        function searchPage(reset) {
            if(reset) {
                $("#form input:text").val(""); //重置查询表单
                $("#form input.combobox-f").combobox("clear");
            }

            var sysconfig = $("#form").serializeJson();
            sysconfig.refresh = "1"; //刷新记录数参数
            $("#table").datagrid("clearSelections").datagrid("load",sysconfig);
        }
    </script>
</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;padding:10px;">
    <form id="form" name="form" style="display: none">
        <table class="table">
            <tr>
                <td class="th">配置KEY</td>
                <td class="td"><input id="configkey" name="configkey" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">配置值</td>
                <td class="td"><input id="configvalue" name="configvalue" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">配置描述</td>
                <td class="td"><input id="configdesc" name="configdesc" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">配置时间</td>
                <td class="td"><input id="configaddtime_begin" name="configaddtime_begin" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="configaddtime_end" name="configaddtime_end" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
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
<div region="center" title="当前位置：系统配置管理">
    <table id="table"
           url="${path}/sys/sysconfig_page.do"
           border="false"         <#--无边框-->
           fit="true"             <#--自动填充宽度高度-->
           singleSelect="true"   <#--单选模式-->
           pagination="true"      <#--是否显示翻页导航-->
           rownumbers="true"      <#--是否显示行号-->
           striped="true"         <#--奇偶行颜色交错-->
           idField="configid"  <#--主键字段-->
           nowrap="true">         <#--单元格数据不换行-->
    </table>
</div>
<div id="dialog" style="width:800px;height:400px; overflow:hidden;display: none" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>
</body>
</html>