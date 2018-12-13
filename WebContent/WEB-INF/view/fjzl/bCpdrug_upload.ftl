<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-05-19 <br/>
描述：导入药品管理添加页面
-->
<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>导入中成药</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <script type="text/javascript">
        $(function(){
            $("body").layout();
            $('#form input.easyui-numberbox').numberbox();
            
            initAjaxUpload('upload');
        });
            
    	function initAjaxUpload(uploadId) {
            var uploader = $("#" + uploadId);
            new AjaxUpload(uploader,{
                action : path+"/file/upload.do?path=/upload/excel/",
                name : 'file_path',
                onSubmit : function(file, ext){
                	if (ext && /^(xlsx|xls)$/.test(ext)) {//过滤上传文件格式
                        ext_str = ext;
                    } else {
                        $.messager.alert('错误信息', '非excel文件格式,请重传！', 'error');
                        return false;
                    }
                    $.showLoad();
                },
                onComplete: function(file, response){
                	$.hideLoad();
                	if(response == "outofsize"){
                        $.messager.alert("系统提示", "文件过大，无法上传！", "info");
                    }
                	else if(response == "error"){
                        $.messager.alert("系统提示", "文件上传失败，请重新上传！", "info");
                    }else{
                    	eval("res=" + response);
                    	var absolutePath = res.absolutePath;
                    	var name = res.name;
                    	$('#fileName').html(name);
                    	$("#filePath").val(absolutePath);
                    }
                }
            });
        }    

        /**
         * 保存
         */
        function save() {
            var fileType = $('#fileType').val();
            var filePath = $('#filePath').val();
             if(filePath==null || filePath==""){
            	$.messager.alert("系统提示","请选择上传文件！","info");
            	return;
            }
            $.showLoad();
            $.ajaxPost("${path}/fjzl/template_uploadSave.do", {"fileType":fileType,"filePath":filePath}, function(result) {
            	if(result=="1"){
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

    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:32px; background-color: #EFEFEF;border-bottom: 1px solid #99bbe8;" border="false">
    <div style="padding: 2px;">
        <a href="javascript:save()" class="easyui-linkbutton" icon="icon-save" plain="true">上传</a>
        <a href="javascript:window.parent.$.closeDialog('dialog');" class="easyui-linkbutton" icon="icon-cancel" plain="true">关闭</a>
    </div>
</div>

<div region="center" border="false" style="padding:10px;">
    <form id="form" name="form" style="+zoom:1;">
    	<input type="hidden" id="fileType" name="fileType" value="EBM_ZCY"/>
        <table class="table-border" width="100%">
            <col width="20%">
            <col width="80%">
	        <tr>
	            <td class="th">文件名称</td>
	            <td class="td"><a id="fileName"></a></td>
	        </tr>
	        <tr>
	            <td class="th">选择文件</td>
	            <td class="td">
	            	<a href="javascript:void(0)" style="pointer: cursor" id="upload" class="easyui-linkbutton" icon="icon-importPic" plain="true"></a>上传excel&nbsp;&nbsp;
	            	<input type="hidden" id="filePath" name="absolutePath" value="">
				</td>
	        </tr>
        </table>
    </form>
</div>
</body>
</html>