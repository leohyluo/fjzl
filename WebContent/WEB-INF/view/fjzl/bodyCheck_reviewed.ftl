<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-17 <br/>
描述：知识库检查表主页面
-->
<#include "/WEB-INF/view/macro.ftl"/>

<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<#assign checkDec= check("解密")>
<#assign checkUpload= check("导入")>
<#assign checkDownload= check("模板下载")>
<#assign checkEnableY= check("启用")>
<#assign checkEnableN= check("停用")>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>知识库检查表管理</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function() {
            $("body").layout();
            $("#form").css("display","block");

            $("#table").datagrid({
                onLoadError:showError,
                sortName:"id",    
                sortOrder:"asc",        
                pageNumber:1,           
                pageSize:20,            
                queryParams:{"refresh":"1"},
                onLoadSuccess:function(data){
                    delete $("#table").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    {field:'ck',checkbox:true},
                    {title:'操作',field:'id',width:120,sortable:false,align:"center",
                        formatter:function(value, data, index){
                            var link= "<a href=\"javascript:void(0)\" onclick=\"view(event, '" + value + "')\">查看</a>&nbsp;";
                            <#if checkEdit>
							link+="<a href=\"javascript:void(0)\" onclick=\"edit(event, '" + value + "')\">修改</a>&nbsp;";
                            <#else>
                            link+="<span style='color:#808080'>修改</span>&nbsp;";
                            </#if>
                            <#if checkDelete>
							link+="<a href=\"javascript:void(0)\" onclick=\"del(event, '" + value + "')\">删除</a>&nbsp;";
                            <#else>
                            link+="<span style='color:#808080'>删除</span>&nbsp;";
                            </#if>
                            return link;
                        }
                    }
                ]],
                columns:[[
                    {title:'检查名称',field:'name',width:150,sortable:true},
                    {title:'检查等级',field:'grade',width:150,sortable:true},
                    {title:'特检特治标志',field:'special',width:150,sortable:true},
                    {title:'一级及以下限价',field:'level1_price',width:150,sortable:true},
                    {title:'二级限价',field:'level2_price',width:150,sortable:true},
                    {title:'三乙限价',field:'level3_price',width:150,sortable:true},
                    {title:'是否启用',field:'_enable',width:150,sortable:true,formatter:function(value, data, index){
                    		if(value=="0"){
                    			return "已停用";
                    		}
                    		if(value=="1"){
                    			return "已启用";
                    		}
                    		return value;
                    	}
                    },
                    {title:'状态',field:'body_check_status',width:150,sortable:true,formatter:function(value, data, index){
                    		if(value=="0"){
                    			return "未审核";
                    		}
                    		if(value=="1"){
                    			return "已审核";
                    		}
                    		return value;
                    	}
                    },
                    {title:'创建时间',field:'create_time',width:150,sortable:true},
                    {title:'创建者',field:'creatorName',width:150,sortable:true},
                    {title:'最后修改时间',field:'last_update_time',width:150,sortable:true},
                    {title:'最后修改者',field:'last_updatorName',width:150,sortable:true},
                    {title:'审核时间',field:'review_time',width:150,sortable:true},
                    {title:'审核者',field:'revieworName',width:150,sortable:true}
                   
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
                    },
                    {
                        <#if checkDelete>
                        handler:delBatch,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"删除",
                        iconCls:"icon-remove"
                    },
 					{
                        <#if checkUpload>
                        handler:upload,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"导入",
                        iconCls:"icon-upload"
                    },
                    {
						<#if checkDownload>
					    handler:download,
					    <#else>
					    disabled:true,
					    </#if>
					    text:"模板下载",
					    iconCls:"icon-download"
					},
                    {
				        <#if checkDec>
                        handler:deciphering,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"审核",
				        iconCls:"icon-logout"
				    } ,
                    {
				        <#if checkEnableY>
                        handler:EnableY,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"启用",
				        iconCls:"icon-logout"
				    },
                    {
				        <#if checkEnableN>
                        handler:EnableN,
                        <#else>
                        disabled:true,
                        </#if>
                        text:"停用",
				        iconCls:"icon-logout"
				    }
                ]
            });
        });
        
        /**
		 * 审核
		 */
		function deciphering(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要审核的记录", "info");  
            }
            else{
                $.messager.confirm("系统提示", "你确认要审核所选择的记录吗？", function(b){
                    if(b){
                        var ids=new Array();
                        $.each(selections,function(i,o){
                           ids.push(o.id);
                        });

                        $.ajaxPost("${path}/fjzl/bodyCheck_reviewBatch.do", {"ids":ids}, function(result) {
                            $.messager.show({
                                title:"消息提醒",
                                msg:"审核成功",
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
		 * 启用
		 */
		function EnableY(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要启用的记录", "info");  
            }
            else{
                $.messager.confirm("系统提示", "你确认要启用所选择的记录吗？", function(b){
                    if(b){
                        var ids=new Array();
                        $.each(selections,function(i,o){
                           ids.push(o.id);
                        });

                        $.ajaxPost("${path}/fjzl/bodyCheck_EnableYBatch.do", {"ids":ids}, function(result) {
                            $.messager.show({
                                title:"消息提醒",
                                msg:"记录启用成功",
                                timeout:5000,
                                showType:"slide"
                            });
                            reloadPage();
                        });
                    }
                });
            }
        } /**
		 * 停用
		 */
		function EnableN(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要停用的记录", "info");  
            }
            else{
                $.messager.confirm("系统提示", "你确认要停用所选择的记录吗？", function(b){
                    if(b){
                        var ids=new Array();
                        $.each(selections,function(i,o){
                           ids.push(o.id);
                        });

                        $.ajaxPost("${path}/fjzl/bodyCheck_EnableNBatch.do", {"ids":ids}, function(result) {
                            $.messager.show({
                                title:"消息提醒",
                                msg:"记录停用成功",
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
         * 显示知识库检查表添加对话框
         */
        function add() {
            $("#dialog").css("display","block").dialog({
                title: "记录添加",
                width:500,
                height:300,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src", "${path}/fjzl/bodyCheck_add.do");
        }

        /**
         * 显示知识库检查表修改对话框
         */
        function edit(event, value) {
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "记录修改",
                width:500,
                height:300,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/fjzl/bodyCheck_edit.do?id="+value);
        }

        /**
         * 删除知识库检查表
         */
        function del(event,val) {
            $.event.fix(event).stopPropagation();
            $.messager.confirm("提示信息", "你确认要删除该记录吗？", function(b) {
                if (b) {
                    $.ajaxPost("${path}/fjzl/bodyCheck_delete.do",{"id":val},function(result){
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
         * 批量删除知识库检查表
         */
        function delBatch(){
            var selections=$("#table").datagrid("getSelections");
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要删除的记录", "info");  
            }
            else{
                $.messager.confirm("系统提示", "你确认要删除所选择的记录吗？", function(b){
                    if(b){
                        var ids=new Array();
                        $.each(selections,function(i,o){
                           ids.push(o.id);
                        });

                        $.ajaxPost("${path}/fjzl/bodyCheck_deleteBatch.do", {"ids":ids}, function(result) {
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
         * 查看知识库检查表详细
         */
        function view(event,value){
            $.event.fix(event).stopPropagation();
            $("#dialog").css("display","block").dialog({
                title: "详细查看",
                width:600,
                height:600,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/fjzl/bodyCheck_view.do?id="+value);
        }

        /**
         * 刷新页面
         */
        function refreshPage(){
            window.location="${path}/fjzl/bodyCheck_reviewed.do";
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

            var bodyCheck = $("#form").serializeJson();
            bodyCheck.refresh = "1"; //刷新记录数参数
            $("#table").datagrid("clearSelections").datagrid("load",bodyCheck);
        }
        
        /**
		*导入知识库
		*/
		function upload() {
			$("#dialog").css("display","block").dialog({
                title: "导入数据",
                width:600,
                height:150,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
            $("#iframe").attr("src","${path}/fjzl/bodyCheck_upload.do");
		}
		function download() {
		    window.location="${path}/fjzl/template_download.do?fileType=EBM_BODYCHECK";
			return false;
		}
    </script>
</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;padding:10px;">
    <form id="form" name="form" style="display: none">
        <table class="table">
            <tr>
                <td class="th">检查名称</td>
                <td class="td"><input id="name" name="name" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">检查等级</td>
                <td class="td"><input id="grade" name="grade" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">特检特治标志</td>
                <td class="td"><input id="special" name="special" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
             <td class="th">审核状态</td>
		        <td style="text-align:left">
		            <span class="radioSpan">
		                <input id="body_check_status" name="body_check_status" type="radio" name="adminFlag" value="0">未审核</input>
		                <input id="body_check_status" name="body_check_status" type="radio" name="adminFlag" value="1">已审核</input>
		            </span>
		        </td>
            </tr>
            <tr>
                <td class="th">启用状态</td>
		        <td style="text-align:left">
		            <span class="radioSpan">
		                <input id="_enable" name="_enable" type="radio" name="adminFlag" value="0">已停用</input>
		                <input id="_enable" name="_enable" type="radio" name="adminFlag" value="1">已启用</input>
		            </span>
		        </td>
            </tr>
           <!-- <tr>
                <td class="th">特检特治标志</td>
                <td class="td"><input id="special_min" name="special_min" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="special_max" name="special_max" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">一级及以下限价</td>
                <td class="td"><input id="level1_price" name="level1_price" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">一级及以下限价</td>
                <td class="td"><input id="level1_price_min" name="level1_price_min" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="level1_price_max" name="level1_price_max" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">二级限价</td>
                <td class="td"><input id="level2_price" name="level2_price" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">二级限价</td>
                <td class="td"><input id="level2_price_min" name="level2_price_min" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="level2_price_max" name="level2_price_max" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">三乙限价</td>
                <td class="td"><input id="level3_price" name="level3_price" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">三乙限价</td>
                <td class="td"><input id="level3_price_min" name="level3_price_min" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="level3_price_max" name="level3_price_max" type="text" class="input easyui-numberbox" min="0" max="999999999.99" precision="2"/></td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><input id="create_time_begin" name="create_time_begin" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="create_time_end" name="create_time_end" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td"><input id="creator" name="creator" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td"><input id="creator_min" name="creator_min" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="creator_max" name="creator_max" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><input id="last_update_time_begin" name="last_update_time_begin" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="last_update_time_end" name="last_update_time_end" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td"><input id="last_updator" name="last_updator" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td"><input id="last_updator_min" name="last_updator_min" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="last_updator_max" name="last_updator_max" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">状态</td>
                <td class="td"><input id="body_check_status" name="body_check_status" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">状态</td>
                <td class="td"><input id="body_check_status_min" name="body_check_status_min" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>
            <tr>
                <td class="th">至</td>
                <td class="td"><input id="body_check_status_max" name="body_check_status_max" type="text" class="input easyui-numberbox" min="0" max="9999999999" precision="0"/></td>
            </tr>-->
            <tr>
                <td colspan="2" style="text-align:center;" class="td">
                    <a href="javascript:searchPage(false)" class="easyui-linkbutton" icon="icon-search" id="searchBtn">查询</a>
                    <a href="javascript:searchPage(true)" class="easyui-linkbutton" icon="icon-reload" id="resetBtn">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div region="center" title="当前位置：知识库检查表管理">
    <table id="table"
           url="${path}/fjzl/bodyCheck_page.do"
           border="false"         <#--无边框-->
           fit="true"             <#--自动填充宽度高度-->
           singleSelect="false"   <#--单选模式-->
           pagination="true"      <#--是否显示翻页导航-->
           rownumbers="true"      <#--是否显示行号-->
           striped="true"         <#--奇偶行颜色交错-->
           idField="id"  <#--主键字段-->
           nowrap="true">         <#--单元格数据不换行-->
    </table>
</div>
<div id="dialog" style="width:800px;height:400px; overflow:hidden;display: none" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>
</body>
</html>