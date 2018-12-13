<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>标题</title>
    <#include "/WEB-INF/view/linkScript.ftl"/>
    <style type="text/css">
        .fieldset{
            border:1px solid #99BBE8;
            display:none;
            margin:0;
            padding:0;
        }
        .fieldset legend{
            /*border:1px solid;*/
            /*background-color:#afeeee;*/
        }

        #temp{
            display:none;
        }

        .div{
           padding:10px;
        }

    </style>


    <script type="text/javascript">
        var tableList = ${toJsonString(database.tableList)};

        var treeJson=[
            {
                "id":"${database.driverClassName}",
                "text":"${database.username?lower_case}",
                "children":[
                    <#list database.tableList as item>
                        {
                            "id":"${item.name}",
                            "text":"${item.name}"
                        }<#if item_has_next>,</#if>
                    </#list>
                ]
            }
        ];
        $(function(){
            $("#tree").tree({
                "data":treeJson,
                "checkbox":true,
                "onSelect":nodeSelect,
                "onCheck":nodeCheck
            });

            $("#table input[name=annotation]").blur(function(){
                var name = $("#table td.tableName").text();
                var table = getTable(name);
                table["annotation"]=$.trim($(this).val());
            });

            $("#table input[name=subjectModuleName]").blur(function(){
                var name = $("#table td.tableName").text();
                var table = getTable(name);
                table["subjectModuleName"]=$.trimChar($.trim($(this).val()),'.');
                $("#table td.domainClass").text("com.zhys"+ join(table.moduleName,table.subjectModuleName,'.')+".domain."+table.classDomain);
                $("#table td.serviceClass").text("com.zhys"+ join(table.moduleName,table.subjectModuleName,'.')+".service."+table.classDomain+"Service");
                $("#table td.controllerClass").text("com.zhys"+ join(table.moduleName,table.subjectModuleName,'.')+".controller."+table.classDomain+"Controller");
                $("#table td.requestMapping").text(join(table.moduleName,table.subjectModuleName,'/')+"/"+ $.lowerFirstChar(table.classDomain)+"_*.do");
            });

            $("#table input[name=buildCURD]").click(function(){
                var name = $("#table td.tableName").text();
                var table = getTable(name);
                table.buildCURD=$(this).attr("checked");
            });
        });


        function join(module,subjectModule,joinChar){
            var str = "";
            if (!$.isEmpty(module)) str += joinChar + module;
            if (!$.isEmpty(subjectModule)) str += joinChar + subjectModule;
            return str;
        }

        function nodeSelect(node){

            var table=getTable(node.id);

            if(table==null) return;
            $("#fieldset1").show();
            $("#fieldset2").show();
            $("#div input").unbind();
            $("#table td.tableName").text(table.name);
            $("#table td.moduleName").text(table.moduleName);


            $("#table td.domainClass").text("com.zhys"+ join(table.moduleName,table.subjectModuleName,'.')+".domain."+table.classDomain);
            $("#table td.serviceClass").text("com.zhys"+ join(table.moduleName,table.subjectModuleName,'.')+".service."+table.classDomain+"Service");
            $("#table td.controllerClass").text("com.zhys"+ join(table.moduleName,table.subjectModuleName,'.')+".controller."+table.classDomain+"Controller");
            $("#table td.requestMapping").text(join(table.moduleName,table.subjectModuleName,'/')+"/"+ $.lowerFirstChar(table.classDomain)+"_*.do");

            $("#table input[name=annotation]").val(table.annotation);
            $("#table input[name=subjectModuleName]").val(table.subjectModuleName);

            $("#table input[name=buildCURD]").attr("checked",(table.buildCURD != undefined && table.buildCURD));

            $("#columnTable input[name=annotation]").unbind("blur");
            $("#tbody").empty();
            var columnList = table.columnList;
            for(var i=0;i<columnList.length;i++){
                var column=columnList[i];
                var row=$($("#temp").val());
                $("#tbody").append(row);
                row.find("td.col_name").text(column.name);
                row.find("td.col_annotation input[name=annotation]").val(column.annotation);
                row.find("td.col_pk").text(column.pk=='Y'?"是":"否");
                row.find("td.col_null").text((column.nullAble=='YES' || column.nullAble=='Y')?"是":"否");
                row.find("td.col_jdbc").text(column.jdbcType);
                row.find("td.col_ibatis").text(column.ibatisType);
                row.find("td.col_java").text(column.javaType);
                row.find("td.col_length").text(column.length);
                row.find("td.col_scale").text(column.scale);
            }

            $("#columnTable input[name=annotation]").bind("blur",function(){
                var tableName = $("#table").find("td.tableName").text();
                var colunmName = $(this).closest("tr").find("td.col_name").text();
                var table = getTable(tableName);
                var column = getColumn(table.columnList,colunmName);
                column["annotation"]=$.trim($(this).val());
            });
        }

        function getTable(name) {
            for (var i = 0; i < tableList.length; i++) {
                if (tableList[i].name == name) {
                    return tableList[i];
                }
            }
            return null;
        }

        function getColumn(columnList,columnName){
            for (var i = 0; i < columnList.length; i++) {
                if (columnList[i].name == columnName) {
                    return columnList[i];
                }
            }
            return null;
        }

        function nodeCheck(node, checked){
            //$("#tree").tree("select",node.target);
        }

        function save(){
            var json = {};
            var columnList, column;
            for(var i = 0; i<tableList.length; i++){
                var table=tableList[i];
                var name = table.name;
                json[name+".annotation"] = table.hasOwnProperty("annotation")?table["annotation"]:"";
                json[name+".subjectmodulename"]=table.hasOwnProperty("subjectModuleName")?table["subjectModuleName"]:"";
                json[name+".buildcurd"] = table.hasOwnProperty("buildCURD")?table["buildCURD"]:"";
                columnList = table.columnList;
                for(var j = 0; j<columnList.length; j++){
                    column = columnList[j];
                    json[name+".column."+column.name] = column.hasOwnProperty("annotation")?column["annotation"]:"";
                }
            }

            $.ajaxPost("${path}/auto/save.do",{"param":json},function(result){
                window.parent.$.messager.show({
                    title:"消息提醒",
                    msg:"配置保存成功",
                    timeout:5000,
                    showType:'slide'
                });
            });
        }

        function create(){
            var rootNode=$("#tree").tree("getRoot");
            var childNodes=$("#tree").tree("getChildren",rootNode.target);
            var createTables = new Array();
            for(var i=0;i<childNodes.length;i++){
                if(childNodes[i].checked){
                    createTables.push(tableList[i]);
                }
            }
            if(createTables.length==0){
                $.messager.alert("系统提示","请勾选树节点中的表生成代码","info");
                return;
            }

            $.ajaxPost("${path}/auto/create.do",{"tableList":createTables},function(result){
                window.parent.$.messager.show({
                    title:"消息提醒",
                    msg:"代码生成成功<br>生成目录/web/autoBuild",
                    timeout:5000,
                    showType:'slide'
                });
            },function (error){alert(error)});

        }
    </script>

    <style type="text/css">
        .table tr{
            height: 24px;

        }
    </style>

</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;">
    <ul class="easyui-tree" id="tree">
    </ul>
</div>
<div region="center" title="当前位置：代码自动生成">
    <div class="easyui-layout" fit="true">
        <div region="north" split="false" border="false" style="height:31px;padding:2px;border-bottom:1px solid #99BBE8;background-color:#efefef">
            <a href="javascript:save()" class="easyui-linkbutton" plain="true" icon="icon-ok" id="saveBtn">保存配置</a>
            <a href="javascript:create()" class="easyui-linkbutton" plain="true" icon="icon-ok" id="buildBtn">生成代码</a>
        </div>
        <div region="center" border="false" style="padding:5px;">

            <fieldset id="fieldset1" class="fieldset">
                <legend class="legend">生成参数</legend>
                <div class="div" style="+zoom:1">
                <table width="100%" class="table-border" id="table">
                    <tr>
                        <td width="15%" class="th">表名称</td>
                        <td width="35%" class="td tableName">&nbsp;</td>
                        <td width="15%" class="th">domain类名</td>
                        <td width="35%" class="td domainClass"></td>
                    </tr>
                    <tr>
                        <td class="th">controller类名</td>
                        <td class="td controllerClass"></td>
                        <td class="th">service类名</td>
                        <td class="td serviceClass"></td>
                    </tr>
                    <tr>
                        <td class="th">url映射</td>
                        <td class="td requestMapping"></td>
                        <td  class="th">模块名</td>
                        <td class="td moduleName">&nbsp;</td>
                    </tr>
                    <tr>
                        <td class="th">表名注释</td>
                        <td class="td"><input type="text" name="annotation" style="width:200px;" class="input"`></td>
                        <td class="th">子模块名</td>
                        <td class="td"><input type="text" name="subjectModuleName" style="width:200px;" class="input"></td>
                    </tr>
                    <tr>
                        <td class="th">生成CURD</td>
                        <td class="td"><input type="checkbox" name="buildCURD" class="checkbox">是</td>
                        <td class="th">&nbsp;</td>
                        <td class="td">&nbsp;</td>
                    </tr>
                </table>
                    </div>
            </fieldset>
            <br>
            <fieldset  id="fieldset2" class="fieldset">
                <legend class="legend">字段列表</legend>
                <div class="div">
                <table class="table-border" width="100%" id="columnTable">
                    <thead>
                    <tr>
                        <td width="15%" class="th">字段名</td>
                        <td width="15%" class="th">字段注释</td>
                        <td width="8%" class="th">是否主键</td>
                        <td width="8%" class="th">允许为空</td>
                        <td width="12%" class="th">Jdbc类型</td>
                        <td width="12%" class="th">Ibatis类型</td>
                        <td width="12%" class="th">Java类型</td>
                        <td width="12%" class="th">长度</td>
                        <td width="6%" class="th">精度</td>
                    </tr>
                    </thead>
                    <tbody id="tbody"></tbody>
                </table>
                </div>
            </fieldset>
            </div>
        </div>
</div>
<textarea id="temp">
    <tr>
        <td class="td col_name">&nbsp;</td>
        <td class="td col_annotation"><input type="text" name="annotation" style="width:95%" class="input"></td>
        <td class="td col_pk">&nbsp;</td>
        <td class="td col_null">&nbsp;</td>
        <td class="td col_jdbc">&nbsp;</td>
        <td class="td col_ibatis">&nbsp;</td>
        <td class="td col_java">&nbsp;</td>
        <td class="td col_length">&nbsp;</td>
        <td class="td col_scale">&nbsp;</td>
    </tr>
</textarea>

</body>
</html>