<#assign path = springMacroRequestContext.getContextPath()/>
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>exception</title>
<#--
    <link rel="stylesheet" type="text/css" href="${path}/css/general.css"/>
-->
</head>
<body>
系统出现错误，请联系管理员！
${exception.message}
<br>
<a href="javascript:void(0)" onclick="document.getElementById('s').style.display='block'">显示错误</a>
<pre id="s" style="display: none">${exception.message}</pre>

</body>
</html>


