<#macro pageNav formScript fixedRows=false>
    <#assign key = getPk()>
<script type="text/javascript">
    <#if fixedRows==false>
    function changeRows${key}(r) {
        var rows = parseInt(r);
        if($("#${formScript}").attr("tagName")=="FORM")  {
            $("#${formScript} input[name=page]").val(1);
            $("#${formScript} input[name=rows]").val(rows);
            $("#${formScript}").submit();
        }
        else{
            ${formScript}(1,rows);
        }
    }
    </#if>

    function changePage${key}(p) {
        var rows =<#if fixedRows==false>parseInt($("#rows${key}").val())<#else>${pageParam.rows}</#if>;
        if ($.isInteger(p)) {
           if($("#${formScript}").attr("tagName")=="FORM")  {
               $("#${formScript} input[name=page]").val(parseInt(p));
               $("#${formScript} input[name=rows]").val(rows);
               $("#${formScript}").submit();
            }
            else{
               ${formScript}(parseInt(p),rows);
           }
        }
        else {
           alert("请输入正确的页码");
        }
    }
</script>

<table id="${key}">
    <tr>
        <td nowrap="nowrap">每页</td>
        <td nowrap="nowrap">
            <#if fixedRows==true>
                ${pageParam.rows}
            <#else>
                <select id="rows${key}" onchange="changeRows${key}(this.value)">
                    <option <#if pageParam.rows==20>selected</#if> value="20">20</option>
                    <option <#if pageParam.rows==50>selected</#if> value="50">50</option>
                    <option <#if pageParam.rows==100>selected</#if> value="100">100</option>
                    <option <#if pageParam.rows==200>selected</#if> value="200">200</option>
                    <option <#if pageParam.rows==500>selected</#if> value="500">500</option>
                    <option <#if pageParam.rows==1000>selected</#if> value="1000">1000</option>
                </select>
            </#if>
        </td>
        <td nowrap="nowrap">条/页</td>
        <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">
            <#if pageParam.recordCount gt 0 && pageParam.page gt 1>
                <a href="###" onclick="changePage${key}('1')">首页</a>
            <#else>
                <span>首页</span>
            </#if>
        </td>
            <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">
            <#if pageParam.recordCount gt 0 && pageParam.page gt 1>
                <a href="###" onclick="changePage${key}('${pageParam.page-1}')">上一页</a>
            <#else>
                <span>上一页</span>
            </#if>
        </td>
            <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">
            <#if pageParam.recordCount gt 0 && pageParam.page lt pageParam.totalPage>
                <a href="###" onclick="changePage${key}('${pageParam.page+1}')">下一页</a>
            <#else>
                <span>下一页</span>
            </#if>
        </td>
            <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">
            <#if pageParam.recordCount gt 0 && pageParam.page lt pageParam.totalPage>
                <a href="###" onclick="changePage${key}('${pageParam.totalPage}')">末页</a>
            <#else>
                <span>末页</span>
            </#if>
        </td>
        <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">第</td>
        <td nowrap="nowrap">
            <#if pageParam.recordCount gt 0 && pageParam.totalPage gt 1>
                <input id="page${key}" type="text" style="width:40px;" value="${pageParam.page}" onkeypress="if(event.keyCode==13){ changePage${key}(this.value) }"/>
            <#else>
                <input id="page${key}" class="tbox" type="text" style="width:30px;" value="${pageParam.page}" disabled>
            </#if>
        </td>
        <td nowrap="nowrap">/</td>
        <td nowrap="nowrap">${pageParam.totalPage}</td>
        <td nowrap="nowrap">页</td>
        <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">显示${pageParam.begin}到${pageParam.end}，总数${pageParam.recordCount}条</td>
        <td nowrap="nowrap">&nbsp;</td>
        <td nowrap="nowrap">耗时${pageParam.queryTime}毫秒</td>
    </tr>
</table>
</#macro>

<#macro pageFill colCount>
    <#assign rowCount = pageParam.rows-(pageParam.end-pageParam.begin)>
    <#list 0..rowCount as i>
    <tr>
        <#list 0..colCount as j>
            <td>&nbsp;</td>
        </#list>
    </tr>
    </#list>
</#macro>

<#macro pageHidden>
<input type="hidden" name="page" value="${pageParam.page}">
<input type="hidden" name="rows" value="${pageParam.rows}">
<input type="hidden" name="sort" value="${pageParam.sort}">
<input type="hidden" name="order" value="${pageParam.order}">
<input type="hidden" name="refresh" value="">
</#macro>

<#function getPk>
    <#return statics["com.zhys.core.util.StringUtil"].getPrimaryKey()/>
</#function>

<#function getNow pattern="yyyy-MM-dd">
    <#return statics["com.zhys.core.util.DateUtil"].getNow(pattern)/>
</#function>

<#function getLocalDate>
    <#return statics["com.zhys.core.util.DateUtil"].formatLocal()/>
</#function>

<#function formatDate date="" pattern="yyyy-MM-dd">
    <#if date?is_date>
        <#return statics["com.zhys.core.util.DateUtil"].formatDate(date,pattern)/>
        <#else>
            <#return ""/>
    </#if>
</#function>

<#function formatDateTime date="" pattern="yyyy-MM-dd HH:mm:ss">
   <#if date?is_date>
       <#return statics["com.zhys.core.util.DateUtil"].formatDate(date,pattern)/>
    <#else>
        <#return ""/>
    </#if>
</#function>

<#macro dateOut date=""><#if date?is_date>${date?date}</#if></#macro>

<#macro datetimeOut date=""><#if date?is_date>${date?datetime}</#if></#macro>

<#macro timeOut date=""><#if date?is_date>${date?time}</#if></#macro>

<#function createInstance className>
    <#return statics["com.zhys.core.util.BeanUtil"].createInstance(className)/>
</#function>

<#function getBean serviceName>
    <#return springMacroRequestContext.getWebApplicationContext().getBean(serviceName)/>
</#function>

<#function getParameters>
<#assign map = createInstance("java.util.HashMap")>
${map.putAll(RequestParameters)}
<#return map/>
</#function>

<#function getSysParameter key>
    <#return statics["com.zhys.core.system.SystemParameter"].get(key)/>
</#function>

<#function md5 str="">
    <#return statics["com.zhys.core.security.MD5"].getEncrypt(str)/>
</#function>

<#function toJsonString obj="">
    <#if obj?is_string>
        <#if obj==""><#return "null"/><#else><#return obj/></#if>
    <#else>
        <#return statics["com.zhys.core.util.BeanUtil"].toJsonString(obj)/>
    </#if>
</#function>

<#function trimChar str="" char="">
    <#return statics["com.zhys.core.util.StringUtil"].trimChar(str,char)/>
</#function>

<#function check action>
    <#assign loginUser = Session["loginUser"]>
    <#assign actionList = Session["loginAction"]>
    <#if loginUser.su_admin == "1"><#return true/></#if>
    <#assign controllerAction = (controller!"")+"."+action>
    <#return actionList?seq_contains(controllerAction)/>
</#function>

<#-- 
Title: 根据枚举 code 取 desc 
Author: xuan.zhou@zhys.com
Example: ${getDescByCode(UserType, feedback.bak1)}
-->
<#function getDescByCode enumName enumCode>
	<#list enumName?values as e>
		<#if enumCode == e.code>
			<#return e.desc />
		</#if>
	</#list>
</#function>

<#--
Title: 获取图片路径
Author: xuan.zhou@zhys.com
Example: 
    获取头像：${getPicUrl(account, path, 0)}
    获取资质：${getPicUrl(account, path, 1)}
<#function getPicUrl account path type>
    <#if path! != "">
        <#if type == 0>
            <#return getSysParameter("remoteImageUploadPath") + "pic/" + path />
        <#else>
            <#return getSysParameter("remoteImageUploadPath") + path />
        </#if>
    <#else>
        <#if type == 0>
            <#return getSysParameter("remoteImageUploadPath") + "/pic/" + account + ".jpg" />
        <#else>
            <#return getSysParameter("remoteImageUploadPath") + account + "_a" + type + ".jpg" />
        </#if>
    </#if>
</#function>
-->
<#function getPicUrl account path type>
    <#return getSysParameter("remoteImageUploadPath")?replace("/kkmy/upload/", "") + path />
</#function>

<#function getCoordinate v>
    <#if v! != "">
        <#return v?string("#.########") />
    </#if>
</#function>

<#--
Title: 生成枚举下拉框
Author: xuan.zhou@zhys.com
Example:<@enumSelect "usertype" "usertype" UserType feedback.bak1 "全部" />
-->
<#macro enumSelect id name enumName enumCode emptyValue>
	<select id="${id}" class="easyui-combobox combobox-f" name="${name}" editable="false">
		<#if emptyValue??>
		<option value="">${emptyValue}</option>
		</#if>
		<#list enumName?values as e>
			<option value="${e.code}" <#if e.code == enumCode>selected</#if> >${e.desc}</option>
		</#list>
	</select>
</#macro>
