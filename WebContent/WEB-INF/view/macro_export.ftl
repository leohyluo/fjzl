<#--
Title: 列表页面导出
Author: xuan.zhou@zhys.com

@Param action: 数据导出动作路径
@Param searchFormID: 条件查询表单ID
-->
<#macro exportExcel action searchFormID="form">
<script type="text/javascript">
$(function(){
    $(".icon-download").click(function () {
        var offset = $(this).offset();
        $("#layoutExport div.menu-item").css("height", 22);
        $("#layoutExport").menu("show", {
            left:offset.left - $('layoutExport').width(),
            top:offset.top + $(this).height()
        });
    });
});

function exportExcel(type) {
    var idField = $("#table").datagrid("options").idField;
    var sortName = $("#table").datagrid("options").sortName;
    var sortOrder = $("#table").datagrid("options").sortOrder;
    
    $("#exportType").val(type);
    $("#exportIds").val("");
    $("#exportSortName").val(sortName);
    $("#exportSortOrder").val(sortOrder);
    
    if(type == 1) {// 导出选中记录
        var selections = $("#table").datagrid("getSelections");
        if (selections.length == 0) {
            $.messager.alert("系统提示", "请选择需要导出的记录", "info");
            return false;
        } else {
            var exportIds = [];
            $.each(selections, function(index, e){
                exportIds.push(e[idField]);
            });
            $("#exportIds").val(exportIds.join(","));
            $("#exportForm").submit();
        }
    }else if(type == 2) {// 导出当前页
        var rows = $("#table").datagrid("getRows");
        if (rows == false) {
            $.messager.alert("系统提示", "暂无可导出的数据", "info");
            return false;
        } else {
            var exportIds = [];
            $.each(rows, function(index, e){
                exportIds.push(e[idField]);
            });
            $("#exportIds").val(exportIds.join(","));
            $("#exportForm").submit();
        }
    }else if(type == 3) {// 导出所有
        $.messager.confirm("提示信息", "数据量过大时，只提供${getSysParameter("exportMaxRows")}条数据导出，您确定执行吗？", function(flag) {
            if(flag){
                var searchContent = $("#${searchFormID}").html();
                var exportContent = $("#exportForm").html();
                
                $("#exportAllForm").html(exportContent + searchContent);
                $("#exportAllForm").submit();
            }
            return false;
        });
    }
}
</script>

<div id="layoutExport" class="easyui-menu" style="width:120px;display: none;">
    <div icon="icon-excel" plain="true">
        <span>Excel导出</span>
        <div style="width:120px;">
            <div onclick="exportExcel(1);">导出选中记录</div>
            <div onclick="exportExcel(2);">导出当前页</div>
            <div onclick="exportExcel(3);">导出所有</div>
        </div>
    </div>
</div>

<form name="exportForm" id="exportForm" action="${action}" method="post" style="display: none">
    <input id="exportType" type="hidden" name="exportType" value="" />
    <input id="exportIds" type="hidden" name="exportIds" value="" />
    <input id="exportSortName" type="hidden" name="sort" value="" />
    <input id="exportSortOrder" type="hidden" name="order" value="" />
</form>

<form name="exportAllForm" id="exportAllForm" action="${action}" method="post" style="display: none">
</form>
</#macro>