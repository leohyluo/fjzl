<#include "/WEB-INF/view/macro.ftl"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>标题</title>
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
    <table class="table-border" id="table" width="100%">
        <tbody>
        <tr>
            <td class="th" width="150">机构名称</td>
            <td class="td">${org.so_name}</td>
        </tr>
        <tr>
            <td class="th">机构编码</td>
            <td class="td">${org.so_code}</td>
        </tr>
        <tr>
            <td class="th">上级机构</td>
            <td class="td">${(org.so_parentname)!"无"}</td>
        </tr>
        <tr>
            <td class="th">联系方式</td>
            <td class="td">${org.so_contact}</td>
        </tr>
        <tr>
            <td class="th">邮箱地址</td>
            <td class="td">${org.so_email}</td>
        </tr>
        <tr>
            <td class="th">邮政编码</td>
            <td class="td">${org.so_post}</td>
        </tr>
        <tr>
                <td class="th">机构类型</td>
                <td class="td">
                	<#if org.so_type==1>医联体</#if>
                	<#if org.so_type==2>医院</#if>
                	<#if org.so_type==3>社康</#if>
                </td>
            </tr>
            <tr>
                <td class="th">拼音助记符</td>
                <td class="td">${(org.so_symbol)!}</td>
            </tr>
            <tr>
                <td class="th">联系人</td>
                <td class="td">${(org.so_contactor)!}</td>
            </tr>
            <tr>
                <td class="th">省份</td>
                <td class="td">${(org.so_province)!}</td>
            </tr>
            <tr>
                <td class="th">城市</td>
                <td class="td">${(org.so_city)!}</td>
            </tr>
            <tr>
                <td class="th">区</td>
                <td class="td">${(org.so_area)!}</td>
            </tr>
            <tr>
                <td class="th">街道</td>
                <td class="td">${(org.so_street)!}</td>
            </tr>
            <tr>
                <td class="th">经度</td>
                <td class="td">${(org.so_longitude)!}</td>
            </tr>
            <tr>
                <td class="th">纬度</td>
                <td class="td">${(org.so_latitude)!}</td>
            </tr>
            <tr>
                <td class="th">横坐标</td>
                <td class="td">${(org.so_xcoordinate)!}</td>
            </tr>
            <tr>
                <td class="th">纵坐标</td>
                <td class="td">${(org.so_ycoordinate)!}</td>
            </tr>
            <tr>
                <td class="th">机构地址</td>
                <td class="td">${(org.so_address)!}</td>
            </tr>
            <tr>
                <td class="th">机构等级</td>
                <td class="td">${(org.so_grade)!}</td>
            </tr>
            <tr>
                <td class="th">机构性质</td>
                <td class="td">
                	<#if org.so_nature==1>市属</#if>
                	<#if org.so_nature==2>区属</#if>
                	<#if org.so_nature==3>社会办</#if>
                </td>
            </tr>
            <tr>
                <td class="th">机构状态</td>
                <td class="td">
                	<#if org.so_status==0>未审核</#if>
                	<#if org.so_status==1>已审核</#if>
                </td>
            </tr>
            <tr>
                <td class="th">是否是指导单位</td>
                <td class="td">
                	<#if org.so_leader_org_flag==1>是</#if>
                	<#if org.so_leader_org_flag==0>否</#if>
                </td>
            </tr>
            <tr>
                <td class="th">是否为牵头单位</td>
                <td class="td">
                	<#if org.so_guide_org_flag==1>是</#if>
                	<#if org.so_guide_org_flag==0>否</#if>
                </td>
            </tr>
            <tr>
                <td class="th">是否启用</td>
                <td class="td">
                	<#if org.so_enable==1>已启用</#if>
                	<#if org.so_enable==0>已停用</#if>
                </td>
            </tr>
            <tr>
                <td class="th">创建时间</td>
                <td class="td"><@dateOut org.so_create_time/></td>
            </tr>
            <tr>
                <td class="th">最后修改时间</td>
                <td class="td"><@dateOut org.so_last_upd_time/></td>
            </tr>
            <tr>
                <td class="th">创建者</td>
                <td class="td">${(org.so_creatorName)!}</td>
            </tr>
            <tr>
                <td class="th">最后修改者</td>
                <td class="td">${(org.so_lastUpdatorName)!}</td>
            </tr>
            <tr>
                <td class="th">审核时间</td>
                <td class="td"><@dateOut org.so_review_time/></td>
            </tr>
            <tr>
                <td class="th">审核者</td>
                <td class="td">${(org.so_revieworName)!}</td>
            </tr>
        </tbody>
    </table>
    </div>

</div>
</body>
</html>