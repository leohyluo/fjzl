<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>菜单排序</title>
<#include "/WEB-INF/view/linkScript.ftl"/>
    <style type="text/css">
        .drag-item {
            list-style-type: none;
            display: block;
            padding: 5px 5px 5px 20px;
            border: 1px solid #ccc;
            margin: 2px;
            width: 300px;
            background: #fafafa;
            text-align: left;
        }

        .indicator {
            position: absolute;
            font-size: 12px;
            width: 10px;
            height: 10px;
            display: none;
            color: red;
        }
    </style>
    <script>
        $(function() {
            var indicator = $('div.indicator');
            $('.drag-item').draggable({
                revert:true,
                deltaX:-80,
                deltaY:-50
            }).droppable({
                onDragOver:function(e, source) {
                    indicator.css({
                                display:'block',
                                top:$(this).offset().top + $(this).outerHeight() - 5 ,
                                left:$(this).offset().left - 10
                            });
                },
                onDragLeave:function(e, source) {
                    indicator.hide();
                },
                onDrop:function(e, source) {
                    $(source).insertAfter(this);
                    indicator.hide();
                }
            });

            $("body").layout();
        });

        function orderSave() {
        <#if menuList?size gt 1>
            var menuList = $("#list>li");
            var menuOrder = [];
            $.each(menuList, function(i, n) {
                menuOrder.push({"sm_id":$(n).attr("id"),"sm_order":1 + i});
            });
            $.ajaxPost("${path}/sys/menu_orderSave.do", {"menuList":menuOrder}, function(result) {
                if (result == "1") {
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"菜单排序成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    window.parent.$.closeDialog('dialog');
                    window.parent.reloadPage('${menu.sap_id}','${menu.sm_parentid}');
                }
            });
        </#if>
        }

    </script>

</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:orderSave()" class="easyui-linkbutton" icon="icon-save" plain="true">保存</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel"
           plain="true">关闭</a>
    </div>
</div>
<div region="center" border="false" style="padding: 30px;" align="center">
<#if menuList??>
    <ul style="width:400px; margin:0;padding:0;margin-left:10px;" id="list">
        <#list menuList as item>
            <li id="${item.sm_id}" class="drag-item">
            <#if item.sm_icon??>
                <span class="${item.sm_icon}" style="width:16px;height:16px;display:inline-block;"></span>${item.sm_name}
            <#else>
                <span style="width:16px;height:16px;display:inline-block;"></span>${item.sm_name}
            </#if>
            </li>
        </#list>
    </ul>
</#if>

</div>
<div class="indicator">&gt;&gt;</div>

</body>
</html>