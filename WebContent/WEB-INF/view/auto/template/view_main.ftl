${"<#--"}
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：${now?string("yyyy-MM-dd")} <br/>
描述：${table.annotation}主页面
${"-->"}
${"<#include \"/WEB-INF/view/macro.ftl\"/>"}
${"
<#assign checkAdd = check(\"添加\")>
<#assign checkEdit = check(\"修改\")>
<#assign checkDelete= check(\"删除\")>
<#assign checkDec= check(\"解密\")>
"}
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#assign list = statics["com.zhys.core.util.BeanUtil"].createInstance("java.util.ArrayList")>
    <#list table.columnList as item>
        <#if item.pk!="Y" && item.ibatisType!="CLOB" && item.ibatisType!="BLOB" && item.ibatisType!="TEXT">
            <#assign b = list.add(item)/>
        </#if>
    </#list>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${table.annotation}管理</title>
    ${"<#include \"/WEB-INF/view/linkScript.ftl\"/>"}
    <script type="text/javascript">
        <#-- var refresh = "1"; -->
        $(function() {
            $("body").layout();
            $("#form").css("display","block");

            $("#table").datagrid({
                onLoadError:showError,
                sortName:"${table.pk}",    <#--排序字段-->
                sortOrder:"asc",        <#--升降序-->
                pageNumber:1,           <#--当前默认页-->
                pageSize:20,            <#--页尺寸-->
                queryParams:{"refresh":"1"},
                onLoadSuccess:function(data){
                    delete $("#table").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    {field:'ck',checkbox:true},
                    {title:'操作',field:'${table.pk}',width:120,sortable:false,align:"center",
                        formatter:function(value, data, index){
                            var link= "<a href=\"javascript:void(0)\" onclick=\"view(event, '" + value + "')\">查看</a>&nbsp;";
                            ${"<#if checkEdit>"}
                            link+="<a href=\"javascript:void(0)\" onclick=\"edit(event, '" + value + "')\">修改</a>&nbsp;";
                            ${"<#else>"}
                            link+="<span style='color:#808080'>修改</span>&nbsp;";
                            ${"</#if>"}
                            ${"<#if checkDelete>"}
                            link+="<a href=\"javascript:void(0)\" onclick=\"del(event, '" + value + "')\">删除</a>&nbsp;";
                            ${"<#else>"}
                            link+="<span style='color:#808080'>删除</span>&nbsp;";
                            ${"</#if>"}
                            return link;
                        }
                    }
                ]],
                columns:[[
                <#list list as item>
                <#if table.pk != item.name>
                    {title:'${item.annotation}',field:'${item.name}',width:150,sortable:true}<#if item_has_next>,</#if>
                </#if>
                    </#list>
                ]],
                toolbar:[
                    {
                        handler:refreshPage,
                        text:"刷新",
                        iconCls:"icon-reload"
                    },
                    "-",
                    {
                        ${"<#if checkAdd>"}
                        handler:add,
                        ${"<#else>"}
                        disabled:true,
                        ${"</#if>"}
                        text:"添加",
                        iconCls:"icon-add"
                    },
                    {
                        ${"<#if checkDelete>"}
                        handler:delBatch,
                        ${"<#else>"}
                        disabled:true,
                        ${"</#if>"}
                        text:"删除",
                        iconCls:"icon-remove"
                    },
                    {
				        ${"<#if checkDec>"}
                        handler:deciphering,
                        ${"<#else>"}
                        disabled:true,
                        ${"</#if>"}
                        text:"解密",
				        iconCls:"icon-logout"
				    }
                ]
            });
        });
        
        /**
		 * 解密
		 */
		function deciphering(){
		    $("#table").datagrid("options").queryParams.refresh="1";
		    //$("#table").datagrid("options").queryParams.colums="c002,c003";
		    $("#table").datagrid("clearSelections").datagrid("reload");
		}

        /**
         * 显示${table.annotation}添加对话框
         */
        function add() {
            $("#dialog").css("display","block").dialog({
                title: "记录添加",
                width:window.$.autoWidth(),
                height:window.$.autoHeight(),
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${"$"+"{path}"}${table.mappings?replace("*","add")}");
        }

        /**
         * 显示${table.annotation}修改对话框
         */
        function edit(event, value) {
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "记录修改",
                width:window.$.autoWidth(),
                height:window.$.autoHeight(),
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${"$"+"{path}"}${table.mappings?replace("*","edit")}?${table.pk}="+value);
        }

        /**
         * 删除${table.annotation}
         */
        function del(event,val) {
            $.event.fix(event).stopPropagation();
            $.messager.confirm("提示信息", "你确认要删除该记录吗？", function(b) {
                if (b) {
                    $.ajaxPost("${"$"+"{path}"}${table.mappings?replace("*","delete")}",{"${table.pk}":val},function(result){
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
         * 批量删除${table.annotation}
         */
        function delBatch(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要删除的记录", "info");  <#--error,question,info,warning-->
            }
            else{
                $.messager.confirm("系统提示", "你确认要删除所选择的记录吗？", function(b){
                    if(b){
                        var ${table.pk}s=new Array();
                        $.each(selections,function(i,o){
                           ${table.pk}s.push(o.${table.pk});
                        });

                        $.ajaxPost("${"$"+"{path}"}${table.mappings?replace("*","deleteBatch")}", {"${table.pk}s":${table.pk}s}, function(result) {
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
         * 查看${table.annotation}详细
         */
        function view(event,value){
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "详细查看",
                width:window.$.autoWidth(),
                height:window.$.autoHeight(),
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${"$"+"{path}"}${table.mappings?replace("*","view")}?${table.pk}="+value);
        }

        /**
         * 刷新页面
         */
        function refreshPage(){
            window.location="${"$"+"{path}"}${table.mappings?replace("*","main")}";
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
                $("#form select").val("");
            }

            var ${table.classDomain?uncap_first} = $("#form").serializeJson();
            ${table.classDomain?uncap_first}.refresh = "1"; //刷新记录数参数
            $("#table").datagrid("clearSelections").datagrid("load",${table.classDomain?uncap_first});
        }
    </script>
</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;padding:10px;">
    <form id="form" name="form" style="display: none">
        <table class="table">
    <#list table.columnList as item>
        <#if item.pk!='Y' && item.ibatisType!="CLOB" && item.ibatisType!="BLOB"  && item.ibatisType!="TEXT">
            <#if item.ibatisType == "VARCHAR">
            <tr>
                <td class="th">${item.annotation}</td>
                <td class="td"><input id="${item.name}" name="${item.name}" type="text" class="input"></td>
            </tr>
            <#elseif item.ibatisType=="TIMESTAMP">
            <tr>
                <td class="th">${item.annotation}</td>
                <td class="td"><input id="${item.name}_begin" name="${item.name}_begin" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="${item.name}_end" name="${item.name}_end" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <#elseif item.ibatisType=="NUMERIC">
            <tr>
                <td class="th">${item.annotation}</td>
                <td class="td"><input id="${item.name}" name="${item.name}" type="text" class="input easyui-numberbox" min="0" max="${item.max}" precision="${item.scale}"/></td>
            </tr>
            <tr>
                <td class="th">${item.annotation}</td>
                <td class="td"><input id="${item.name}_min" name="${item.name}_min" type="text" class="input easyui-numberbox" min="0" max="${item.max}" precision="${item.scale}"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="${item.name}_max" name="${item.name}_max" type="text" class="input easyui-numberbox" min="0" max="${item.max}" precision="${item.scale}"/></td>
            </tr>
            </#if>
        </#if>
    </#list>
            <tr>
                <td colspan="2" style="text-align:center;" class="td">
                    <a href="javascript:searchPage(false)" class="easyui-linkbutton" icon="icon-search" id="searchBtn">查询</a>
                    <a href="javascript:searchPage(true)" class="easyui-linkbutton" icon="icon-reload" id="resetBtn">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div region="center" title="当前位置：${table.annotation}管理">
    <table id="table"
           url="${"$"+"{path}"}${table.mappings?replace("*","page")}"
           border="false"         ${"<#--无边框-->"}
           fit="true"             ${"<#--自动填充宽度高度-->"}
           singleSelect="false"   ${"<#--单选模式-->"}
           pagination="true"      ${"<#--是否显示翻页导航-->"}
           rownumbers="true"      ${"<#--是否显示行号-->"}
           striped="true"         ${"<#--奇偶行颜色交错-->"}
           idField="${table.pk}"  ${"<#--主键字段-->"}
           nowrap="true">         ${"<#--单元格数据不换行-->"}
    </table>
</div>
<div id="dialog" style="width:800px;height:400px; overflow:hidden;display: none" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>
</body>
</html>