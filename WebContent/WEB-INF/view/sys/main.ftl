<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${getSysParameter("systemName")}</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <link rel="stylesheet" type="text/css" href="${path}/css/main.css">

    <script type="text/javascript">

        function menuClick(id, name, action, icon) {
            switch (action) {
                case "/exitLogin":
                    setTimeout(function() {
                        $.ajaxPost("${path}/sys/loginExit.do", null, function(result) {
                            window.location.href = "${path}/index.jsp";
                        });
                    }, 500);
                    break;
                case "/exitSystem":
                    $.messager.confirm("系统提示", "你确认退出系统吗!", function(b) {
                        if (b) {
                            $.ajaxPost("${path}/sys/loginExit.do", null, function(result) {
                                window.location.href = "${path}/index.jsp";
                            });
                        }
                    });
                    break;
               case "/personal":
                   $("#dialog").css("display", "block").dialog({
                       title:"个人信息",
                       width:700,
                       height:400,
                       onMove:function(left,top){ $.adjustPosition("dialog",left,top)},
                       onBeforeClose:function() { $.restoreDialog("dialog") }
                   });
                   $("#iframe_work").attr("src", "${path}/sys/person_view.do");
                   break;
                case "/resize1":
                    window.moveTo(1, 1);
                    window.resizeTo(800, 600);
                    break;
                case "/resize2":
                    window.moveTo(1, 1);
                    window.resizeTo(1024, 768);
                    break;
                case "/about":
                    $.messager.alert("系统提示", "<center>魔法衣橱后台管理平台<br/>&copy;2013-2016</center>");
                    break;
                case "/help":
                    break;
                case "/build":
                    $.messager.alert("系统提示", "功能实现中");
                    break;
                case "/guide":
                    break;
                default:
                    selectOrCreateTab(id, name, action, icon);
            }
        }

        var tabIcons={};
        function selectOrCreateTab(id, name, action, icon) {
            var title = $('<div></div>').append($("<span id=\"s"+id+"\">"+name+"</span>") ).html();
            if(!$.isEmpty(icon)) tabIcons[title]=icon;
            if($("#tabBar").tabs("exists",title)){
                $("#tabBar").tabs("select",title);
            }
            else{
                var option = {
                    title:title,
                    closable:true,
                    content:"<iframe frameborder='0' scrolling='auto' style='width:100%; height:100%; overflow: auto;'"+
                            "id='iframe_" + id + "' name='iframe_" + id + "' src='${path}"+ action + "'></iframe>"
                };
                if(!$.isEmpty(icon)) option.iconCls = icon;
                $('#tabBar').tabs('add',option);
            }
        }


        $(function() {
            $("body").layout();
            $("#div.bannerbg").width("100%");
            $("#menuBar").width("100%");
            $("#statusBar").width("100%");
            $("#tabBar").tabs({
                onClose:function(title){
                    delete tabIcons[title];
                },
                tools:[
                    {
                        iconCls:'icon-cancel',
                        handler: function() {
                            var tabs = $("#tabBar ul.tabs span.tabs-title.tabs-closable");
                            for (var i = tabs.length - 1; i >= 0; i--) {
                                 $('#tabBar').tabs('close',$(tabs[i]).html());
                            }
                        }
                    },
                    {
                        iconCls:'icon-drop',
                        handler: function() {
                            var menus = $("#openMenus div.menu-item");
                            for (var i = menus.length - 1; i > 0; i--) {
                                $("#openMenus").menu("removeItem", menus[i]);
                            }
                            var tabs = $("#tabBar ul.tabs span.tabs-title.tabs-closable");

                            for(var j = 0; j<tabs.length ; j++){
                                var option = {"text":$(tabs[j]).html()};
                                if(tabIcons.hasOwnProperty($(tabs[j]).html()))
                                    option["iconCls"] = tabIcons[$(tabs[j]).html()];
                                $("#openMenus").menu("appendItem", option);
                            }
                            var offset = $(this).offset();
                            $("#openMenus").menu('show', {
                                left:offset.left-$('#openMenus').width()+$(this).width(),
                                top:offset.top+$(this).height()
                            });
                        }
                    }
                ]
            });
            $("#openMenus").menu({
                onClick:function(item){
                    $("#tabBar").tabs("select",item["text"]);
                }
            });
            
            // 先创建 iframe，再赋值才不会报错
            $("#iframe_100").attr("src", "${path}/sys/welcome.do");
            
            // 初始化Pushlet消息推送
            //initPushlet();
        });
        
        // 初始化Pushlet消息推送
        function initPushlet() {
            PL.webRoot = "${path}/";
            //PL.userId = "${Session["loginUser"].su_id}";// 点对点方式
            PL.userId = "${sessionId}";// 事件监听方式
            PL._init();
            PL.joinListen('all');
            // 订阅其他事件
            initListen();
        }
        
        // 初始化Pushlet事件监听
        function initListen() {
            <#assign loginUser = Session["loginUser"]>
            <#assign actionList = Session["loginAction"]>
            <#assign isAdmin = (Session["loginUser"].su_admin == 1)>
            
            // 管理员或服务处理人员订阅服务处理事件
            <#if isAdmin || actionList?seq_contains(getSysParameter('kkmyAskServiceProcessPurviewTag')) || actionList?seq_contains(getSysParameter('kkmyBuyServiceProcessPurviewTag'))>
            PL.listen("listen_service_process_notice");
            </#if>
        }
        
        // 接收Pushlet推送数据
        function onData(event) {
            var subject = event.get("p_subject");
            var sessionId = event.get("p_id");
            var pushType = event.get("pushType");
            var pushContent = event.get("pushContent");
            var data = $.parseJSON(pushContent);
            
            //alert("subject:" + subject + ", sessionId:" + sessionId + ", pushType:" + pushType + ", pushContent:" + pushContent);
            
            // 交给Pushlet数据处理器统一处理
            pushletHandler(subject, sessionId, pushType, data);
        }
    </script>

</head>

<body class="easyui-layout">

<div region="north" border="false" split="false" style="height:72px; padding:0;overflow:hidden;">
     <div class="bannerbg">
    	<div class="banner_left">
    		<#-- <img src="${path}/css/images/banner.jpg" /> -->
            <div class="input_box">
                <a href="javascript:void(0)" class="a_btn" title="个人信息" onclick="menuClick('个人信息','','/personal','')">个人信息</a>
                <a href="javascript:void(0)" class="a_btn" title="重新登录" onclick="menuClick('重新登录','','/exitLogin','')">重新登录</a>
                <a href="javascript:void(0)" class="a_btn" title="退出系统" onclick="menuClick('退出系统','','/exitSystem','')">退出系统</a>
	    		<#--
	    		<a href="javascript:void(0)" onclick="menuClick('','','/resize2','')">1024*800</a>
	    		<a href="javascript:void(0)" onclick="menuClick('','','/resize1','')">800*600</a>
	    		-->
            </div>
    	</div>
    </div>
    <div id="menuBar">${menu!}</div>
</div>
<div region="center" border="false" split="false" style="overflow:hidden;padding:1px 0;width: 800px; height: 600px">
    <div id="tabBar" fit="true" style="width:100%;height:100%;">
        <div title="首页" iconCls="icon-home">
            <iframe frameborder="0" scrolling="auto" style="width:100%; height:100%; overflow: auto" id="iframe_100" name="iframe_100"></iframe>
        </div>
    </div>
</div>
<div region="south" border="false" split="false" style="height:28px;padding:0;overflow:hidden;">
    <div id="statusBar"><span style="font-family:Arial">&copy;copyright 2013</span>&nbsp; </div>
</div>
<div id="openMenus" class="easyui-menu menuDrop">
    <div iconCls="icon-home">首页</div>
</div>

<div id="dialog" style="width:800px;height:550px; overflow:hidden;" resizable="true" maximizable="true" modal="true">
<iframe id="iframe_work" name="iframe_work" scrolling="auto" src="about:blank" style="width:100%;height:100%;" frameborder="0" ></iframe>
</div>

</body>
</html>
