<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-05-19 <br/>
描述：导入药品管理添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>导入药品管理添加</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
            $('#form input.easyui-numberbox').numberbox();
            
            //药品导入数据
            $("#drugImport").datagrid({
            	url:"${path}/fjzl/diseaseMapper_reviewPage1.do",
            	onLoadError:showError,
                sortName:"id",    
                sortOrder:"asc",    
                height:100, 
                queryParams:{"refresh":"1","id":'${id}'},
                onLoadSuccess:function(data){
                    delete $("#drugImport").datagrid("options").queryParams.refresh;
                },
                columns:[[
                    {title:'对比状态',field:'disease_status',width:120,sortable:true,
                    	formatter:function(value, data, index){
                    		if(value=="0"){
                    			return "未对照";
                    		}
                    		if(value=="1"){
                    			return "对照成功";
                    		}
                    		if(value=="2"){
                    			return "对照失败";
                    		}
	                    }
                    },
                    {title:'疾病编码',field:'org_disease_id',width:120,sortable:true},
                    {title:'疾病名称',field:'org_disease_name',width:180,sortable:true},
                    {title:'icd10',field:'org_icd_10',width:120,sortable:true},
                    {title:'所属医院',field:'orgName',width:212,sortable:true}
                ]]
            });
            
            //对照数据         
           $("#drugContrastCfda").datagrid({
                onLoadError:showError,
                sortName:"disease_name",    
                sortOrder:"asc",        
                pageNumber:1,           
                pageSize:20,       
                height:500, 
                queryParams:{"refresh":"1"},
                onLoadSuccess:function(data){
                    delete $("#drugContrastCfda").datagrid("options").queryParams.refresh;
                },
                frozenColumns:[[
                    {field:'id',checkbox:true}
                ]],
                columns:[[                
                	{title:'疾病名称',field:'icd10Name',width:220,sortable:true},
                    {title:'icd10',field:'icd_10',width:200,sortable:true},
                    {title:'临床诊断名称',field:'disease_name',width:220,sortable:true},
                    {title:'疾病等级',field:'grade',width:140,sortable:true},
                    {title:'状态',field:'disease_status',width:140,sortable:true,formatter:function(value, data, index){
                    	if(value == "0") {
                    		return "未审核";
                    	} else if(value == "1") {
                    		return "已审核";
                    	} else if(value == "2") {
                    		return "审核不通过";
                    	} 
                    }}             
                ]],
                toolbar:[
                ]
            });
        });

        /**
         * 对照
         */
        function contrast(status) {
        	var selections=$("#drugContrastCfda").datagrid("getSelections");
        	var org_disease_id = $('#org_disease_id').val();
            if(selections.length==0){
                $.messager.alert("系统提示", "请选择你要对照的记录", "info");  
                return;
            }
            else if(selections.length>1){
            	$.messager.alert("系统提示", "只能选择一条记录进行对照！", "info");  
                return;
            }
            else{
                $.messager.confirm("系统提示", "你确认要对照所选择的记录吗？", function(b){
                    if(b){
                        var ebmDiseaseId = selections[0].id;
                        $.ajaxPost("${path}/fjzl/diseaseMapper_saveReview.do", {"orgDiseaseId":org_disease_id,"ebmDiseaseId":ebmDiseaseId,"reviewResult":status}, function(result) {
                        	if(result=="1"){
                        		window.parent.$.messager.show({
                                    title:"消息提醒",
                                    msg:"对照成功",
                                    timeout:5000,
                                    showType:"slide"
                                });
                                $.hideLoad();
                                window.parent.$.closeDialog('dialog');
                                window.parent.reloadPage();
                        	}else{
                        		$.messager.alert("提示",result,"warning");
                        		$.hideLoad();
                        	}
                        });
                    }
                });
            }
        }
            
        /**
         * 无法对照
         */    
        function nocontrast(){
        	$.messager.confirm("系统提示", "你确认此疾病无法对照吗？", function(b){
        		if(b){
        			var org_disease_id = $('#org_disease_id').val();
                	$.ajaxPost("${path}/fjzl/diseaseMapper_saveReview.do", {"orgDiseaseId":org_disease_id,"ebmDiseaseId":"","reviewResult":"2"}, function(result) {
                    	if(result=="1"){
                    		window.parent.$.messager.show({
                                title:"消息提醒",
                                msg:"操作成功",
                                timeout:5000,
                                showType:"slide"
                            });
                            $.hideLoad();
                            window.parent.$.closeDialog('dialog');
                            window.parent.reloadPage();
                    	}else{
                    		$.messager.alert("提示",result,"warning");
                    		$.hideLoad();
                    	}
                    });
        		}
        	})
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

            var drugContrastData = $("#form").serializeJson();
            drugContrastData.refresh = "1"; //刷新记录数参数
            $("#drugContrastCfda").datagrid("clearSelections").datagrid("load",drugContrastData);
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:contrast(1)" class="easyui-linkbutton" icon="icon-save" plain="true">对照</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
        <a href="javascript:nocontrast()" class="easyui-linkbutton" icon="icon-no" plain="true">无法对照</a>
    </div>
</div>

<div region="center" border="false" style="padding:10px;">
    <form id="form" name="form" style="+zoom:1;">
    	<input type="hidden" id="org_disease_id" name="org_disease_id" value="${id}">
        <table class="table-border" width="100%">
            <tr>
                <td class="td">
                	<div id="drugImport"></div>
                </td>
            </tr>
            <tr>
	            <td class="td">
		            <div style="margin:5px">
	            		疾病名称:<input id="name" name="disease_name" type="text" class="input">
	            		icd10:<input id="name" name="icd_10" type="text" class="input">
		            	<a href="javascript:searchPage(false)" class="easyui-linkbutton" icon="icon-search" id="searchBtn">查询</a>
		            	<a href="javascript:searchPage(true)" class="easyui-linkbutton" icon="icon-reload" id="resetBtn">重置</a>
		            </div>
	            	
	            	<table id="drugContrastCfda"
	                    url="${path}/fjzl/diseaseMapper_reviewPage2.do"
	                    border="true"         <#--无边框-->
	                    fit="false"             <#--自动填充宽度高度-->
	                    singleSelect="true"   <#--单选模式-->
	                    pagination="true"      <#--是否显示翻页导航-->
	                    rownumbers="true"      <#--是否显示行号-->
	                    striped="true"         <#--奇偶行颜色交错-->
	                    idField="common_name"  <#--主键字段-->
	                    nowrap="true">         <#--单元格数据不换行-->
	               </table>
	            </td>
	        </tr>
           
        </table>
    </form>
</div>
</body>
</html>