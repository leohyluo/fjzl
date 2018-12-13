<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：基础知识库疾病表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>基础知识库疾病表详细</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
        var idsarr=$("#ids").val().split(',');
        var icd9arr=$("#icd9").val().split(',');
        var icd9Namearr=$("#icd9Name").val().split(',');
       if($("#ids").val()!="")
        {
        splitNames(idsarr,icd9arr,icd9Namearr);
        }
        });
        function splitNames(idarr,icd9s,names){
			for(var i = 0; i < idarr.length; i++) //遍历当前数组
				{
					 var val = $("#list tbody:eq(0) input[name=sa_class]:last").val();
		             var row = $($("#temp").val());
		             row.insertBefore($("#list tbody:eq(0) tr:last"));
		             row.find("input[name=sa_icd9Name]").val(names[i]);
		             row.find("input[name=sa_icd9]").val(icd9s[i]);
		             row.find("input[name=sa_ids]").val(idarr[i]);
		             row.find("input").validatebox();
				}
		}
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:window.location.reload();" class="easyui-linkbutton" icon="icon-reload" plain="true">刷新</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding:10px;">
    <div style="+zoom:1;">
        <input type="hidden" id="id" name="id" value="${(disease.id)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">icd10</td>
                <td class="td">${(disease.icd_10)!}</td>
            </tr>
            <tr>
                <td class="th">疾病名称</td>
                <td class="td">${(disease.disease_name)!}</td>
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
            </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="td"></td>
                    <td class="td"></td>
                </tr>
            </tbody>
        </table>
         <textarea id="temp" style="display:none">
        <tr>
            <td class="td">
             <input name="sa_ids" id="sa_ids" readonly="true" type="hidden" class="input easyui-validatebox" required="true" validType="maxLength[100]" style='border:0px; width:250px;'/>
            <input name="sa_icd9Name" id="sa_icd9Name" readonly="true" type="text" class="input easyui-validatebox" required="true" validType="maxLength[20]" style='border:0px;width:120px;'/></td>
           <!-- <select id="diseaseicd9" class="easyui-combobox" name="drug_status" panelMaxHeight="200" style="width:142px;">
					       <#list listIcd9s as item>
					       	<option value="${item.id}">${item.icd9}-${item.icd9_name}</option>
			    			</#list>
					</select>-->
					</td>
            <td class="td"><input name="sa_icd9" id="sa_icd9" readonly="true" type="text" class="input easyui-validatebox" required="true" validType="maxLength[100]" style='border:0px;width:150px;'/></td>
        </tr>
    </textarea>
                <!--<input type="text" readonly="readonly" id="icd9Name" name="icd9Name" class="input easyui-validatebox" validType="maxLength[50]" style="width:300px;"/>
                <a href="javascript:toIcdview()" class="easyui-linkbutton" icon="icon-all" plain="true">添加</a>-->
                </td>
            </tr>
            <tr>
                <td class="th">助记符</td>
                <td class="td">${(disease.py_sympol)!}
                <input name="ids" id="ids" type="hidden" value="${(disease.ids)!}"></>
                <input name="icd9" id="icd9" type="hidden" value="${(disease.icd9)!}"></>
             	<input name="icd9Name" id="icd9Name" type="hidden" value="${(disease.icd9Name)!}"></>
                </td>
            </tr>
            <tr>
                <td class="th">所属科室</td>
                <td class="td">${(disease.department)!}</td>
            </tr>
            <tr>
                <td class="th">疾病评估</td>
                <td class="td">${(disease.assessment)!}</td>
            </tr>
            <tr>
                <td class="th">疾病等级</td>
                <td class="td">${(disease.grade)!}</td>
            </tr>
            <tr>
                <td class="th">审核状态</td>
                <td class="td">
                	<#if disease.disease_status==0>
					未审核
					</#if>
					<#if disease.disease_status==1>
					已审核
					</#if>
                </td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut disease.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(disease.creatorName)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut disease.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(disease.last_updatorName)!}</td>
            </tr>
                      <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut disease.review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(disease.revieworName)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
