<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：中成药表查看页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>中成药表详细</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
        });
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
        <input type="hidden" id="id_" name="id_" value="${(bCpdrug.id_)!}">
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
            <tr>
                <td class="th">药品名称</td>
                <td class="td">${(bCpdrug.cpdrugname)!}</td>
            </tr>
            <!--<tr>
                <td class="th">药物组成</td>
                <td class="td">${(bCpdrug.ofdrug)!}</td>
            </tr>
            <tr>
                <td class="th">功能主治</td>
                <td class="td">${(bCpdrug.cfunction)!}</td>
            </tr>
            <tr>
                <td class="th">方解</td>
                <td class="td">${(bCpdrug.prescriptionanalysis)!}</td>
            </tr>
            <tr>
                <td class="th">临床应用</td>
                <td class="td">${(bCpdrug.clinicalapplication)!}</td>
            </tr>
            <tr>
                <td class="th">药物毒性</td>
                <td class="td">${(bCpdrug.pharmacology)!}</td>
            </tr>
            <tr>
                <td class="th">不良反应</td>
                <td class="td">${(bCpdrug.adyersereaction)!}</td>
            </tr>
            <tr>
                <td class="th">禁忌</td>
                <td class="td">${(bCpdrug.taboo)!}</td>
            </tr>
            <tr>
                <td class="th">注意事项</td>
                <td class="td">${(bCpdrug.precautions)!}</td>
            </tr>
            <tr>
                <td class="th">用法用量</td>
                <td class="td">${(bCpdrug.cusage)!}</td>
            </tr>
            <tr>
                <td class="th">剂型规格</td>
                <td class="td">${(bCpdrug.standard)!}</td>
            </tr>
            <tr>
                <td class="th"></td>
                <td class="td">${(bCpdrug.typeid)!}</td>
            </tr>
            <tr>
                <td class="th">助记符</td>
                <td class="td">${(bCpdrug.symbol)!}</td>
            </tr>-->
            <tr>
                <td class="th">收费类别</td>
                <td class="td">${(bCpdrug.charge_type)!}</td>
            </tr>
            <tr>
                <td class="th">收费项目等级</td>
                <td class="td">${(bCpdrug.charge_grade)!}</td>
            </tr>
            <tr>
                <td class="th">药品等级</td>
                <td class="td">${(bCpdrug.grade)!}</td>
            </tr>
            <!--<tr>
                <td class="th">数据来源</td>
                <td class="td">${(bCpdrug.data_sources)!}</td>
            </tr>-->
             <tr>
                <td class="th">审核状态</td>
                <td class="td">
                	<#if bCpdrug.cpdrug_status==0>
					未审核
					</#if>
					<#if bCpdrug.cpdrug_status==1>
					已审核
					</#if>
                </td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut bCpdrug.create_time/></td>
            </tr>
            <tr>
                <td class="th">创建人</td>
                <td class="td">${(bCpdrug.creatorName)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut bCpdrug.last_update_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改人</td>
                <td class="td">${(bCpdrug.last_updateorName)!}</td>
            </tr>
            <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut bCpdrug.review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(bCpdrug.revieworName)!}</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
