<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>exception</title>
    <link rel="stylesheet" type="text/css" href="${path}/css/general.css"/>
</head>
<body>
<#assign path = springMacroRequestContext.getContextPath()/>
<div style="padding:20px;font-size:14px;color:red;">
<a href="###" onclick="window.top.location.href='${path}/index.jsp'">
${exception.message}
</a>
</div>
</body>
</html>