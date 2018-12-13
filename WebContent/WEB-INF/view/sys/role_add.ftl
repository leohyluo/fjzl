<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>角色新建</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();

            $('#menuTable').treegrid({
				nowrap:false,
				collapsible:true,
                rownumbers: false,
                animate:false,
				idField:'id',
				treeField:'sm_name',
				frozenColumns:[[
	                {title:'菜单名称',field:'sm_name',width:240,
		                formatter:function(value,data){
		                	return "<span class='sysMenu "+data.style +"' sap_id='" + data.sap_id + "' sm_id='"+data.sm_id+"' "
                                    +(data.hasOwnProperty("sm_parentid")? "sm_parentid='"+data.sm_parentid+"'":"")
                                    +" onClick='checkMenu(this)'></span>"+value;
		                }
	                }
				]],
				columns:[[
					{field:'actionList',title:'菜单功能',width:600,
						formatter:function(value,data){
                            if(value == null||value.length==0) return "<span style='color:#888888'>无</span>";
                            else{
                                var s = "";
                                for(var i=0;i<value.length;i++){
                                    s+="<span class='sysAction "+ value[i].style +"' sa_id='"+ value[i].sa_id
                                            + "' sm_id='"+data.sm_id+"' onClick='checkAction(this)'></span>"
                                            + value[i].sa_group + "&nbsp;&nbsp;";
                                }
                                return s;
                            }
						}
					}
				]]
            });

            $('#menuTable').treegrid("loadData",${tree!"[]"});

        });

        //勾选菜单
        function checkMenu(obj){

            if($(obj).hasClass("tree-checkbox0")) {  //未勾选->勾选
                $(obj).removeClass("tree-checkbox0").addClass("tree-checkbox1");
                $("span.sysAction[sm_id="+ $(obj).attr("sm_id") +"]").removeClass("tree-checkbox0").addClass("tree-checkbox1");

                //查找所有子菜单，选中它 和 功能
                updateChild($(obj).attr("sap_id"),$(obj).attr("sm_id"),true);

                //查找所有父菜单，更新勾选状态 全部勾中或部分勾中
                if($(obj).attr("sm_id")!='0'){
                    updateParent($(obj).attr("sap_id"),$(obj).attr("sm_parentid"),true);
                }
            }
            else if($(obj).hasClass("tree-checkbox2")){  //部分勾选->勾选
                $(obj).removeClass("tree-checkbox2").addClass("tree-checkbox1");
                $("span.sysAction[sm_id="+ $(obj).attr("sm_id") +"]").removeClass("tree-checkbox0").addClass("tree-checkbox1");

                //查找所有子菜单，选中它 (未选中的情况下全选功能)
                updateChild($(obj).attr("sap_id"),$(obj).attr("sm_id"),true);

                //查找所有父菜单，更新勾选状态 全部勾中或部分勾中
                if($(obj).attr("sm_id")!='0'){
                    updateParent($(obj).attr("sap_id"),$(obj).attr("sm_parentid"),true);
                }
            }
            else if($(obj).hasClass("tree-checkbox1")){  //勾选->取消勾取
                $(obj).removeClass("tree-checkbox1").addClass("tree-checkbox0");
                $("span.sysAction[sm_id="+ $(obj).attr("sm_id") +"]").removeClass("tree-checkbox1").addClass("tree-checkbox0");

                //取消所有子菜单的选中状态
                updateChild($(obj).attr("sap_id"),$(obj).attr("sm_id"),false);

                //查找所有父菜单，更新勾选状态 部分勾中或取消勾中
                if($(obj).attr("sm_id")!='0'){
                    updateParent($(obj).attr("sap_id"),$(obj).attr("sm_parentid"),false);
                }
            }
        }

        //更新子菜单
        function updateChild(sap_id, sm_id, checked){
            var childMenu = $("span.sysMenu[sap_id="+sap_id+"][sm_parentid="+sm_id+"]");
            if(checked){ //勾选
                for (var i = 0; i < childMenu.length; i++) {
                    var menu = $(childMenu[i]);
                    if(menu.hasClass("tree-checkbox0")){  //未勾选->勾选
                        menu.removeClass("tree-checkbox0").addClass("tree-checkbox1");
                        $("span.sysAction[sm_id="+menu.attr("sm_id")+"]").removeClass("tree-checkbox0").addClass("tree-checkbox1");
                    }
                    else if(menu.hasClass("tree-checkbox2")){ //部分勾选->勾选
                        menu.removeClass("tree-checkbox2").addClass("tree-checkbox1");
                        $("span.sysAction[sm_id="+menu.attr("sm_id")+"]").removeClass("tree-checkbox0").addClass("tree-checkbox1");
                    }
                    updateChild(sap_id, menu.attr("sm_id"), checked);
                }
            }
            else{  //取消勾选
                for (var i = 0; i < childMenu.length; i++) {
                    var menu = $(childMenu[i]);
                    if(menu.hasClass("tree-checkbox1")){  //勾选->取消勾选
                        menu.removeClass("tree-checkbox1").addClass("tree-checkbox0");
                        $("span.sysAction[sm_id="+menu.attr("sm_id")+"]").removeClass("tree-checkbox1").addClass("tree-checkbox0");
                    }
                    else if(menu.hasClass("tree-checkbox2")){   //部分勾选->取消勾选
                        menu.removeClass("tree-checkbox2").addClass("tree-checkbox0");
                        $("span.sysAction[sm_id="+menu.attr("sm_id")+"]").removeClass("tree-checkbox1").addClass("tree-checkbox0");
                    }
                    updateChild(sap_id, menu.attr("sm_id"), checked);
                }
            }
        }

        //更新父菜单
        function updateParent(sap_id, sm_id, checked){
            var menu = $("span.sysMenu[sap_id="+sap_id+"][sm_id="+sm_id+"]");
            if(checked){ //勾选
                menu.removeClass("tree-checkbox0");
                var check0 = $("span.sysMenu.tree-checkbox0[sap_id=" + sap_id + "][sm_parentid=" + sm_id + "]");
                var check2 = $("span.sysMenu.tree-checkbox2[sap_id=" + sap_id + "][sm_parentid=" + sm_id + "]");
                if (check0.length > 0 || check2.length > 0){
                    if (menu.hasClass("tree-checkbox1")) menu.removeClass("tree-checkbox1");
                    if (!menu.hasClass("tree-checkbox2")) menu.addClass("tree-checkbox2");
                }
                else{
                    if (menu.hasClass("tree-checkbox2")) menu.removeClass("tree-checkbox2");
                    if (!menu.hasClass("tree-checkbox1")) menu.addClass("tree-checkbox1");
                }
            }
            else{   //取消勾选
                menu.removeClass("tree-checkbox1");
                var check1 = $("span.sysMenu.tree-checkbox1[sap_id=" + sap_id + "][sm_parentid=" + sm_id + "]");
                var check2 = $("span.sysMenu.tree-checkbox2[sap_id=" + sap_id + "][sm_parentid=" + sm_id + "]");
                if (check1.length > 0 || check2.length > 0){
                    if (menu.hasClass("tree-checkbox0")) menu.removeClass("tree-checkbox0");
                    if (!menu.hasClass("tree-checkbox2")) menu.addClass("tree-checkbox2");
                }
                else{
                    if (menu.hasClass("tree-checkbox2")) menu.removeClass("tree-checkbox2");
                    if (!menu.hasClass("tree-checkbox0")) menu.addClass("tree-checkbox0");
                }
            }
            if(sm_id!='0'){
                updateParent(sap_id,menu.attr("sm_parentid"),checked);
            }
        }

        //勾选动作
        function checkAction(obj){
            var menu = $("span.sysMenu[sm_id=" + $(obj).attr("sm_id") +"]");
            if($(obj).hasClass("tree-checkbox0")){ //未选中->选中
                $(obj).removeClass("tree-checkbox0").addClass("tree-checkbox1");
                if(!menu.hasClass("tree-checkbox1")){
                    menu.removeClass("tree-checkbox0").removeClass("tree-checkbox2").addClass("tree-checkbox1");
                    updateChild(menu.attr("sap_id"),menu.attr("sm_id"),true);
                    if(menu.attr("sm_id")!='0'){
                        updateParent(menu.attr("sap_id"),menu.attr("sm_parentid"),true);
                    }
                }
            }
            else{   //选中->取消
                $(obj).removeClass("tree-checkbox1").addClass("tree-checkbox0");
                /*if ($("span.sysAction.tree-checkbox1[sm_id=" + $(obj).attr("sm_id") + "]").length==0)  {
                    menu.removeClass("tree-checkbox1").removeClass("tree-checkbox2").addClass("tree-checkbox0");
                    updateChild(menu.attr("sap_id"),menu.attr("sm_id"),false);
                    if(menu.attr("sm_id")!='0'){
                        updateParent(menu.attr("sap_id"),menu.attr("sm_parentid"),false);
                    }
                }*/
           }
        }

        //保存
        function addSave(){
            if (!$("#addForm").form("validate")) return;
            var role = $("#addForm").serializeJson();
            var menuAction = new Object();
            var checkMenus = $("span.sysMenu.tree-checkbox1, span.sysMenu.tree-checkbox2");
            for(var i=0;i<checkMenus.length;i++){
                var sm_id = $(checkMenus[i]).attr("sm_id");
                if(sm_id!="0"){
                    var checkActions = $("span.sysAction.tree-checkbox1[sm_id="+sm_id+"]");
                    var actions = new Array();
                    for(var j = 0; j<checkActions.length; j++){
                        actions.push($(checkActions[j]).attr("sa_id"));
                    }
                    menuAction[sm_id] = actions;
                }
            }

            $.ajaxPost("${path}/sys/role_addSave.do",{"role":role, "menuAction":menuAction}, function(response){

                var json = eval("("+response+")");

                if(json.result=="2"){//编码重复
                    $.messager.alert("系统提示","角色编码重复","info");
                }
                else if(json.result=="1"){
                    window.parent.$.messager.show({
                        title:"消息提醒",
                        msg:"角色添加成功",
                        timeout:5000,
                        showType:"slide"
                    });
                    window.parent.$.closeDialog('dialog');
                    window.parent.sr_id = json.sr_id;
                    window.parent.reloadPage();
                }
            });
        }
    </script>

    <style type="text/css">
        .sysMenu,.sysAction{
            display: inline-block;
            height: 18px;
            vertical-align: middle;
            width: 16px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div region="north" border="false" style="height: 36px;overflow: hidden;background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;">
<form id="addForm" name="addForm" style="width: 100%;height: 100%">
<input id="sr_status" name="sr_status" type="hidden" value="1"/>
<table cellpadding="4">
    <tr>
        <td>角色编码:</td>
        <td><input id="sr_code" name="sr_code" type="text" class="input easyui-validatebox" validType="maxLength[30]" required="true"/></td>
        <td>角色名称:</td>
        <td><input id="sr_name" name="sr_name" type="text" class="input easyui-validatebox" validType="maxLength[30]" required="true"/></td>
        <td><a href="javascript:addSave();" class="easyui-linkbutton" icon="icon-ok">保存</a></td>
        <td><a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel">关闭</a></td>
    </tr>
</table>
</form>
</div>
<div region="center" style="padding:1px;" border="false">
    <table id="menuTable" fit="true" border="false"></table>
</div>
</body>
</html>