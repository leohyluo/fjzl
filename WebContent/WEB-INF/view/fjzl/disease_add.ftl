<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：基础知识库疾病表添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>基础知识库疾病表添加</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
            $('#form input.easyui-numberbox').numberbox();
              $("#list tbody a.addAction").click(addRow);
        });
 /**
         * 加行
         */
        function addRow() {
            var val = $("#list tbody:eq(0) input[name=sa_class]:last").val();
            var row = $($("#temp").val());
            row.insertBefore($("#list tbody:eq(0) tr:last"));
           	row.find("input[name=sa_icd9Name]").val(val);
		    row.find("input[name=sa_icd9]").val(val);
		    row.find("input[name=sa_ids]").val(val);
            row.find("a.delAction").click(deleteRow).linkbutton();
            row.find("input").validatebox();
        }

        /**
         * 删行
         */
        function deleteRow() {
            $(this).unbind();
            var row = $(this).closest("tr");
            row.find("input").validatebox("destroy");
            row.remove();
        }
        /**
         * 保存
         */
        function save() {
           var idsArray = [];// 创建id数组
        	var icd9sArray = [];// 创建icd9数组
        	var namesArray = [];// 创建icdname数组
			 $("input[name='sa_ids']").each(  
  				function(){ 
					idsArray.push($(this).val()); // 添加到最后;  
				});
        	$("input[name='sa_icd9']").each(  
  				function(){ 
					icd9sArray.push($(this).val()); // 添加到最后;  
				});
			$("input[name='sa_icd9Name']").each(  
  				function(){
					namesArray.push($(this).val()); // 添加到最后;  
				});
				 $("#ids").val(idsArray.join(","));
				 $("#icd9").val(icd9sArray.join(","));
				 $("#icd9Name").val(namesArray.join(","));
            if (!$("#form").form("validate")) return;
            var disease = $("#form").serializeJson();
            $.showLoad();
            $.ajaxPost("${path}/fjzl/disease_addSave.do", {"disease":disease}, function(result) {
            	if(result == "-1") {
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"疾病已存在",
                        timeout:5000,
                        showType:"slide"
                    });
                    $.hideLoad();
            	}else if(result=="1"){
            		window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"记录添加成功",
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
        function toIcdview()
        {
        	$("#dialog").css("display","block").dialog({
                title: "Icd9",
                width:630,
                height:500,
                onMove:function(left,top){$.adjustPosition("dialog",left,top)},
                onBeforeClose:function(){$.restoreDialog("dialog")}
            });
          $("#iframe").attr("src","${path}/fjzl/icd9_mainQuery.do?callback=selectBrandBack");
        }
        function selectBrandBack(idarr,icd9s,names){
        	var idsArray = [];// 创建id数组
        	var icd9sArray = [];// 创建icd9数组
        	var namesArray = [];// 创建icdname数组
        	$("input[name='sa_ids']").each(  
  				function(){ 
					idsArray.push($(this).val()); // 添加到最后;  
				});
        	$("input[name='sa_icd9']").each(  
  				function(){ 
					icd9sArray.push($(this).val()); // 添加到最后;  
				});
			$("input[name='sa_icd9Name']").each(  
  				function(){
					namesArray.push($(this).val()); // 添加到最后;  
				});
					var idss=tab(idarr,idsArray);
					var icd9ss=tab(icd9s,icd9sArray);
					var namess=tab(names,namesArray);
					if(idss.length!=0){
						splitNames(idss,icd9ss,namess);
					}
	  	}
		function tab(arr1,arr2){
		    var temp = []; //临时数组1  
		    var temparray = []; //临时数组2  
		    for (var i = 0; i < arr2.length; i++) {
		        temp[arr2[i]] = true; //巧妙地方：把数组B的值当成临时数组1的键并赋值为真
		    };
		    for (var i = 0; i < arr1.length; i++) {
		        if (!temp[arr1[i]]) {
		            temparray.push(arr1[i]); //巧妙地方：同时把数组A的值当成临时数组1的键并判断是否为真，如果不为真说明没重复，就合并到一个新数组里，这样就可以得到一个全新并无重复的数组  
		        };
		    };
		    return temparray;
		}
		function splitNames(idarr,icd9s,names){
			for(var i = 0; i < idarr.length; i++) //遍历当前数组
				{
					 var val = $("#list tbody:eq(0) input[name=sa_class]:last").val();
		             var row = $($("#temp").val());
		             row.insertBefore($("#list tbody:eq(0) tr:last"));
		             row.find("input[name=sa_icd9Name]").val(names[i]);
		             row.find("input[name=sa_icd9]").val(icd9s[i]);
		             row.find("input[name=sa_ids]").val(idarr[i]);
		             row.find("a.delAction").click(deleteRow).linkbutton();
		             row.find("input").validatebox();
				}
		}
    </script>
    </script>
      <style type="text/css">
        #iconSpan{
            position: absolute;
            border: 1px solid #D3D3D3;
            width: 200px;
            background-color: #FFFFFF;
            display: none;
            padding: 5px;
        }

        #iconSpan a{
            display: block;
            width: 18px;
            height: 18px;
            border: 1px solid #ffffff;
            background-position: center;
            float: left;
            font-size: 12px;
        }

        #iconSpan a:hover{
            border: 1px solid #000000;
        }

        #iconSpan a.choice{
            border: 1px solid #000000;
            background-color:#00ee00;
        }

        #sm_icon_span{
            width: 18px;
            height: 18px;
            padding: 0;
            margin: 0;
            display: inline-block;
            border: 1px solid #D3D3D3;
            cursor: pointer;
            background-position: center;
        }

    </style>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:save()" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>

<div region="center" border="false" style="padding:10px;">
    <form id="form" name="form" style="+zoom:1;">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">icd10编码</td>
                <td class="td"><input type="text" id="icd_10" name="icd_10" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;" /></td>
            </tr>
              <tr>
                <td class="th">icd10名称</td>
                <td class="td"><input type="text" id="icd10Name" name="icd10Name" class="input easyui-validatebox" validType="maxLength[10]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">临床诊断名称</td>
                <td class="td"><input type="text" id="disease_name" name="disease_name" class="input easyui-validatebox" validType="maxLength[50]" style="width:300px;" required="true"/></td>
            </tr>
            <tr>
                <td class="th">icd9疾病名称</td>
                <td class="td">
               <table id="list" width="600px" class="table-border">
            <col width="180">
            <col width="180">
            <col width="100">
            <thead>
            <tr>
                <td class="td">手术名称</td>
                <td class="td">ICD9</td>
                <td class="td" style="text-align: center;">操作</td>
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="td"></td>
                    <td class="td"></td>
                   <td class="td" style="text-align: center;">
                        <a href="javascript:void(0)" id="addBtn"  plain="true" class="easyui-linkbutton addAction" icon="icon-add">添加</a>
                    </td>
                </tr>
            </tbody>
        </table>
         <textarea id="temp" style="display:none">
        <tr>
            <td class="td">
            <input name="sa_ids" id="sa_ids" readonly="true" type="hidden" class="input easyui-validatebox" validType="maxLength[100]" style='width:180px;'/>
            <input name="sa_icd9Name" id="sa_icd9Name" type="text" class="input easyui-validatebox" required="true"  validType="maxLength[20]" style='width:180px;'/></td>
            <!--<input name="sa_ids" id="sa_ids" readonly="true" type="hidden" class="input easyui-validatebox" required="true" validType="maxLength[100]" style='border:0px; width:180px;'/>
            <input name="sa_icd9Name" id="sa_icd9Name" readonly="true" type="text" class="input easyui-validatebox" required="true" validType="maxLength[20]" style='border:0px;width:120px;'/></td>-->
           <!-- <select id="diseaseicd9" class="easyui-combobox" name="drug_status" panelMaxHeight="200" style="width:142px;">
					       <#list listIcd9s as item>
					       	<option value="${item.id}">${item.icd9}-${item.icd9_name}</option>
			    			</#list>
					</select>-->
					</td>
            <td class="td"><input name="sa_icd9" id="sa_icd9" type="text" class="input easyui-validatebox" required="true"  validType="maxLength[100]" style='width:150px;'/></td>
            <td class="td" style="text-align: center;"><a href="javascript:void(0)" plain="true" class="easyui-linkbutton delAction" icon="icon-remove">删除</a></td>
        </tr>
    </textarea>
                <!--<input type="text" readonly="readonly" id="icd9Name" name="icd9Name" class="input easyui-validatebox" validType="maxLength[50]" style="width:300px;"/>
                <a href="javascript:toIcdview()" class="easyui-linkbutton" icon="icon-all" plain="true">添加</a>-->
                </td>
            </tr>
            <tr>
                <td class="th">助记符</td>
                <td class="td"><input type="text" id="py_sympol" name="py_sympol" class="input easyui-validatebox" validType="maxLength[50]" style="width:300px;"/>
                 <input name="ids" id="ids" type="hidden" value=""></>
                <input name="icd9" id="icd9" type="hidden" value=""></>
             	<input name="icd9Name" id="icd9Name" type="hidden" value=""></>
             	</td>
            </tr>
           <tr>
               <td class="th">对应系统</td>
                <td class="td"><input type="text" id="dis_system" name="dis_system" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;"/></td>
            </tr>
              <tr>
               <td class="th">一级科室</td>
                <td class="td"><input type="text" id="dept_lv1" name="dept_lv1" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;"/></td>
            </tr>
               <tr>
                <td class="th">二级科室</td>
                <td class="td"><input type="text" id="dept_lv2" name="dept_lv2" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">所属科室</td>
                <td class="td"><input type="text" id="department" name="department" class="input easyui-validatebox" validType="maxLength[64]" style="width:300px;"/></td>
            </tr>
            <tr >
                <td class="th">疾病评估</td>
                <td class="td"><input type="text" id="assessment" name="assessment" class="input easyui-validatebox" validType="maxLength[500]" style="width:300px;"/></td>
            </tr>
            <tr>
                <td class="th">医院等级</td>
                <td class="td"><input type="text" id="grade" name="grade" class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
            <tr style="display: none">
                <td class="th">审核状态</td>
                <td class="td"><input type="hidden" id="disease_status" name="disease_status" value='0' class="input easyui-numberbox" min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
           <tr style="display: none">
                <td class="th">启用状态</td>
                <td class="td"><input type="hidden" id="_enable" name="_enable" class="input easyui-numberbox"  value='1' min="0" max="9999999999" precision="0" style="width:300px;"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog" style="width:800px;height:400px; overflow:hidden;display: none" resizable="true" maximizable="true" modal="true">
<iframe id="iframe" name="iframe" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</body>
</html>