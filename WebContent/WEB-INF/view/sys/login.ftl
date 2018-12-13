<#include "/WEB-INF/view/macro.ftl"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${getSysParameter("systemName")}</title>
    <link href="${path}/css/login.css" rel="stylesheet" type="text/css"/>
    <#include "/WEB-INF/view/linkScript.ftl" />
    <script type="text/javascript">
        $(function() {
            <#if status??>
                <#if status=="0">$.messager.alert("系统提示", "验证码错误", "warn");
                <#elseif status=="2">$.messager.alert("系统提示", "用户名和密码错误", "warn");
                <#elseif status=="3">$.messager.alert("系统提示", "用户没有访问权限", "warn");
                <#elseif status=="4">$.messager.alert("系统提示", "用户被限制访问，请联系管理员", "warn");
                </#if>
            </#if>
            $("#loginForm input:visible").keypress(enterSwitch);
            
            var showRandomCode = "${getSysParameter('checkRandomCode')}";
            if(showRandomCode.toLowerCase() == "true"){
                $("#tr_randomCode").show();
            }
        });
        
        $(function(){
    		$(":input").focus(function(){
    			  $(this).addClass("focus");
    			  if($(this).val() ==this.defaultValue){  
                      $(this).val("");           
    			  } 
    		}).blur(function(){
    			 $(this).removeClass("focus");
    			 if ($(this).val() == '') {
                    $(this).val(this.defaultValue);
                 }
    		});
        })

        //回车切换
        function enterSwitch(event) {
            if (event.keyCode == 13) {
                event.preventDefault();
                if ($(this).attr("id") == "<#if checkRandomCode=="true">randomCode<#else>su_password</#if>") {
                    login();
                }
                else {
                    var inputs = $("#loginForm input:visible");
                    var index = 1 + inputs.index($(this));
                    $(inputs.get(index)).focus();
                }
            }
        }

        function login() {
            if (!$("#loginForm").form("validate")) return;
            $("#loginForm input:hidden[name=su_password]").val(hex_md5($("#su_password").val()));
            $("#loginForm").submit();
        }
        
        function refreshRandom() {
            $("#randomImg").attr("src", "${path}/sys/loginRandom.do?serial=" + (new Date()).getTime());
        }

    </script>
</head>
<body>
<div class="main">
<form id="loginForm" name="loginForm" action="${path}/sys/login.do" method="post" >
  <input type="hidden" name="su_password" />
  <div class="ico_database"></div>
  <table width="280" border="0" cellspacing="0" cellpadding="0" class="login_box">
    <tr>
      <td style="padding-top:0px;"><img src="${path}/css/images/yhdl.png" /></td>
    </tr>
    <tr>
      <td style="padding-top:15px;"><input type="text" id="su_code" class="input_user" value="" name="su_code" /></td>
    </tr>
    <tr>
      <td><input type="password" id="su_password"  class="input_pass" value=""  /></td>
    </tr>
    <tr id="tr_randomCode" style="display: none">
      <td>
        <span style="font-size:14px;color:#fff">验证码：</span>
        <img src="${path}/sys/loginRandom.do" onclick="refreshRandom();" style="cursor:pointer;align:absmiddle;width:80px;height:28px;vertical-align:middle" title="点击刷新" id="randomImg" >
        <input id="randomCode" name="randomCode" maxlength="4" class="input easyui-validatebox" style="width:100px;height:20px;font-size:20px;vertical-align:middle;line-height:20px;">
      </td>
    </tr>
    <tr>
      <td><a href="javascript:login();"  class="dm_bluebtn "><span class="itembody">&nbsp;登录&nbsp;</span></a></td>
    </tr>
  </table>
</form>
</div>
<!--<div class="footer">&copy; 2013 融贯资讯 1024*768</div>-->
</body>
</html>
