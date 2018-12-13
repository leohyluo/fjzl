<#assign path = springMacroRequestContext.getContextPath()/>
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>report search bar</title>
    <style type="text/css">
        #so_parentcode{
            background: #EFEFEF;
            font-size: 12px;
            height: 14px;
            line-height: 14px;
            padding:4px 3px 3px 3px;
            vertical-align: middle;
            border: 1px solid #A4BED4;
        }
    </style>
    <script type="text/javascript">
	    $(function(){
	    	var tree =  ${orgTree!""};
	    	$("#orgName").combotree({
				panelHeight:180,
	            data:tree,
	            onSelect:function(node){
	            	var orgId = node.id;
	            	$('#orgid').val(orgId);
	            }    
	        });
	        //默认选中树的第一个节点
	        /*if(tree && tree.length > 0) {
	        	var currentOrgId = tree[0].id;
		        $('#orgName').combotree('setValue', currentOrgId);
	        }*/
	        
	        $('#hisParabiose').combobox({onChange:function(){	            
	      		var orgId = $('#hisParabiose').combobox('getValue');
	      		$('#yltId').val(orgId);
	        }})
	    })
	</script>
</head>
<body>
	<table class="table">
			<input type="hidden" name="refresh" value="1">
            <tr>
                <td class="th">开始时间</td>
                <td class="td"><input id="createtime_begin" name="createtime_begin" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
           <tr>
                <td class="th">结束时间</td>
				<td class="td"><input id="createtime_end" name="createtime_end" type="text" class="input Wdate" onclick="WdatePicker()"/></td>
            </tr>
            <tr>
                <td class="th">医联体</td>
                <td class="td">
						<#if (parabioseList?size>1) >
		                	<select id="hisParabiose" class="easyui-combobox" name="disease_status" panelMaxHeight="200"
		                		 style="width:130px;">
									<option value="">-请选择-</option>
						<#else>
							<select id="hisParabiose" class="easyui-combobox" name="disease_status" panelMaxHeight="200"
		                		 style="width:130px;background-color: #EEEEEE;"  disabled="disabled">
						</#if>
						<#list parabioseList as org>					
							<option value="${org.so_id}">${org.so_name}</option>
						</#list>
					</select>
					<input type="hidden" name="yltId" id="yltId">
                </td>
            </tr>
            <tr>
                <td class="th">医疗机构</td>
                <td class="td">
                	<input id="orgName" name="orgName" type="text" class="input" >
                	<input type="hidden" id="orgid" name="orgid">
                </td>
            </tr>
            <tr>
                <td class="th">科室</td>
                <td class="td"><input id="department" name="department" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">医生</td>
                <td class="td"><input id="doctorName" name="doctorName" type="text" class="input"></td>
            </tr>
            <tr>
                <td class="th">医生级别</td>
                <td class="td"><input id="doctorGrade" name="doctorGrade" type="text" value="" class="input"></td>
            </tr>
            
            <tr>
                <td colspan="2" style="text-align:center;" class="td">
                    <a href="#" class="easyui-linkbutton" icon="icon-search" id="searchBtn">查询</a>
                    <a href="" class="easyui-linkbutton" icon="icon-reload" id="resetBtn">重置</a>
                </td>
            </tr>
        </table>
</body>