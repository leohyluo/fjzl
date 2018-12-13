<#include "/WEB-INF/view/macro.ftl"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>转诊&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</title>
	<#include "/WEB-INF/view/linkScript.ftl"/>
	<script type="text/javascript" src="${path}/js/jqprint/jquery.jqprint-0.3.js"></script>
	
	
	
	<style type="text/css">
			.page_flag{
				position:fixed;
				right:1em;
				bottom:1em;
				color:#DDDFDE;
				z-index:99999;
				font-size:90%;
			}
			@media screen and (max-width: 600px) {
			    .page_flag {
			    	font-size:50%;
			    }
			}
		</style>
	<script type="text/javascript">
		var phone = ''; 
		var referralUnit = '';
		var department = '';
		var targetDoctor = '';
		
		$(document).ready(function(){
		})
		
		$('#area_pastHistory').live('blur', function(){
			var pastHistory = $('#area_pastHistory').val();
			$('#area_pastHistory').hide();
			$('#span_area_pastHistory').text(pastHistory);
			$('#span_area_pastHistory').show();
		})
		
		$('#td_pastHistory').live('click', function(){
			$('#area_pastHistory').show();
			$('#span_area_pastHistory').hide();
		})
		
		$('#area_cureAdvice').live('blur', function(){
			var cureAdvice = $('#area_cureAdvice').val();
			$('#area_cureAdvice').hide();
			$('#span_area_cureAdvice').text(cureAdvice);
			$('#span_area_cureAdvice').show();
		})
		
		$('#td_cureAdvice').live('click', function(){
			$('#area_cureAdvice').show();
			$('#span_area_cureAdvice').hide();
		})
		
		function c_print() {
			if(validate()) {
				
				$('.tb_searchbar').jqprint();				
			}
		}
		
	
		
		function save() {
			/*if(validate()) {
				$.post('../api_detail/Smartestimate_saveReferral.action',$('#form_referral').serialize(),function(data){
					if(data.code == 20000) {
						$('#form_main_sympton').submit();
					}
				},'json')
			}*/
			$('<iframe src="http://127.0.0.1:8081/gmws_web/jsp/api/fjzl/callback.jsp" style="display:none" id="frame1"></iframe>').appendTo("body");					    
		}
		
		function validate() {
			var name = document.getElementById('app.jsp.referral.form.a12').value;
			if(name == '') {
				alert('请填写患者姓名');
				return false;
			}
			var gender = document.getElementById('app.jsp.referral.form.a13').value;
			if(gender == '') {
				alert('患者性别不允许为空');
				return false;
			}
			referralUnit = document.getElementById('sel_org_list').value;
			if(referralUnit == '') {
				alert('请选择接诊单位');				
				return false;
			}
			var doctor = document.getElementById('app.jsp.referral.form.a19').value;
			if(doctor == '') {
				alert('转诊医生不允许为空');
				return false;
			}
			var reason = document.getElementById('app.jsp.referral.form.reason').value;
			if(reason == '') {
				alert('请选择转诊原因');
				return false;
			}
			var tel = /^1[3|4|5|7|8]\d{9}$/;
			phone = document.getElementById('app.jsp.referral.form.a20').value;
			if(phone == '' || !tel.test(phone)) {
				alert('请填写正确的电话号码');
				document.getElementById('app.jsp.referral.form.a20').focus();
				return false;
			} 
			return true;
		}
		
		function findAndReplace(input, value) {
			var inputVal = input.val();
			var parent = input.parent();
			if(inputVal != '' && parent != null) {
				var parentHtml = parent.html();
				var result = parentHtml.replace('value=""','value="'+value+'"');
				parent.html(result);
			}
		}
	</script>
</head>
<style type="text/css">
<!--
body{ font-size: 14px;}
td span{font-size: 14px;}
td{ padding:2px 10px 2px 10px; line-height:23px;word-wrap:break-word;word-break:break-all;}
h1{ padding:20px 0 0 0;}

.STYLE1 {
	color: #000000;
	font-weight: bold;
	font-size:36px;
}
input{border: none; border-bottom:1px solid #000; width:180px; height: 16px; line-height:16px; vertical-align:middle; border-bottom:1px solid #000;}
.btn{ border:#114b88 1px solid; background:#114b88; width:120px; height:30px; line-height:30px; vertical-align:middle; color:#fff; cursor:pointer;}
.btn:hover{border:#2f6eb1 1px solid; background:#2f6eb1;}
.mainbox{ padding:20px; width:645px;}
.bon_box{ text-align:center; width:100%; padding:30px 0 10px 0;}
.none_border{border-left: none;border-right: none;border-top: none;border-bottom-color: black;}
-->
</style>
<body style="overflow-x: hidden;">
	<form action="../api_detail/Smartestimate_finish.action" id="form_main_sympton" method="post">
		<input type="hidden" name="Account" value=""/>
		<input type="hidden" name="Sign" value=""/>	
	</form>
	<form action="" id="form_referral">
	<input type="hidden" name="companyId" value=""/>
	<input type="hidden" name="sign" value=""/>
	<input type="hidden" name="preDiseaseName" value=""/>
	<input type="hidden" name="sympton" value="" />
	<input type="hidden" name="sourceDoctorCode" value=""/>
	<input type="hidden" id="hidReferralUrl" name="referralUrl" value=""/>
	
	<div class="mainbox">
		
	  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tb_searchbar" id="app.jsp.treatmentPlan.form">
	  
	  <tr>
	    <td height="106" colspan="4" align="center" bgcolor="#FFFFFF" class="td_field" style="border: none;"><h1>双向转诊（转出）单</h1></td>
	  </tr>
	  <tr>
	    <td height="40" colspan="4" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    	<span class="td_field" style="border: none;">单位：</span>
	    	<label id="lbl_targetAgencyName2">
		    	<!-- <span class="td_field" style="border: none;">
		    			      		<input name="text23" type="text" id="app.jsp.referral.form.a11" value="" 
		    			      		style="border-left: none;border-right: none;border-top: none;border-bottom-color: black"   
		    			      		onkeyup="document.getElementById('app.jsp.referral.form.a6').value = this.value;" maxlength="20"/>
		    	</span> -->
		    	<select id="sel_org_list">
			    	<#list orgList as org>
			    		<option value="${org.so_id}">${org.so_name}</option>
			    	</#list>
		    	</select>
	    	</label>
	    </td>
	    </tr>
	  <tr>
	    <td height="40" colspan="2" bgcolor="#FFFFFF">现有患者：
	      <input name="text2" type="text" id="app.jsp.referral.form.a12"  maxlength="10" readonly="readonly"
	      	style="border-left: none;border-right: none;border-top: none;border-bottom-color: black"  
	      	value="${(record.target_name)}"/>
	    </td>
	    <td height="40" colspan="2" bgcolor="#FFFFFF">
	    	<span class="td_field" style="border: none;">
		    	<span style="vertical-align:middle; text-align:right;">性别：</span>
	    	</span>
	    	<span class="td_field" style="border: none;">
	    		
	      		<input name="text29" value="<#if record.target_gender == "0">男</#if><#if record.target_gender == "1">女</#if>" type="text" id="app.jsp.referral.form.a13"
	      			style="border-left: none;border-right: none;border-top: none;border-bottom-color: black" 
	      		 	maxlength="10" readonly="readonly"/>
	    </span></td>
	    </tr>
	  <tr>
	    <td height="40" colspan="4" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    	年龄：<input name="text24" type="text" value="${record.target_age}" id="app.jsp.referral.form.a14" 
	    			style="border-left: none;border-right: none;border-top: none;border-bottom-color: black"
	    	 		onkeyup="this.value=this.value.replace(/[^0-9]/g,'');document.getElementById('txt_age').value = this.value;" 
	    	 		maxlength="3"/>
	   		  &nbsp; 因病情需要，转入贵单位，请予以接诊。
	    </td>
	   </tr>
	  <tr>
	    <td height="36" colspan="4" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    <strong>初步诊断:</strong>
	    <span id="app.jsp.referral.form.a15" style="padding:0 0 0 6px;">${(record.diagnosis)}</span>
	    </td>
	  </tr>
	  <tr>
	    <td colspan="4" valign="top" bgcolor="#FFFFFF" class="td_field" style="border: none; padding: 0px 0px 0px 10px;height: 10px;" >
	    	<strong>现病史:</strong>
	    	<div style="padding:5px 0 0 0;" id="app.jsp.referral.form.a16"></div>
	    </td>
	  </tr>
	  
	  <tr>
	    <td height="36" colspan="4" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    	转出原因：<span style="vertical-align: middle;">
	      	<!-- <input name="Input" id="app.jsp.referral.form.reason" />
	      	<input name="hidden" type="hidden" id="app.jsp.referral.form.reason_val" /> -->
	      	<!-- <label id="lbl_reason">
	      	<select id="app.jsp.referral.form.reason" name="reason">
	      		<option value="">-请选择-</option>
	      		<option value="缺少必要设备" selected="selected">缺少必要设备</option>
	      		<option value="缺少必要药物">缺少必要药物</option>
	      		<option value="技术限制">技术限制</option>
	      		<option value="个人原因">个人原因</option>
	      	</select>
	      	</label> -->
	      	<div style="padding:5px 0 0 0;" id="app.jsp.referral.form.a18">
	    		<textarea rows="4" cols="90" id="app.jsp.referral.form.reason" name="reason">${record.reason}</textarea>
	    	</div>
	    	</span>
	    </td>
	  </tr>
	  <tr>
	    <td id="td_pastHistory" colspan="4" bgcolor="#FFFFFF" class="td_field" style="padding:10px;height: 50px;">
	    	<strong>既往史:</strong>
	    	<div style="padding:5px 0 0 0;" id="app.jsp.referral.form.a17">
	    		<textarea rows="4" cols="90" id="area_pastHistory" name="pastHistory"></textarea>
	    		<span style="display: none" id="span_area_pastHistory"></span>
	    	</div>
	    </td>
	  </tr>
	  <tr>
	    <td id="td_cureAdvice" colspan="4" bgcolor="#FFFFFF" class="td_field" style="padding:10px;height: 50px;">
	    	<strong>治疗意见:</strong>
	    	<div style="padding:5px 0 0 0;" id="app.jsp.referral.form.a18">
	    		<textarea rows="4" cols="90" id="area_cureAdvice" name="advice"></textarea>
	    		<span style="display: none" id="span_area_cureAdvice"></span>
	    	</div>
	    </td>
	  </tr>
	  
	  <tr>
	    <td height="30" colspan="4" align="right" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    	<span>转诊医生：</span>
	      	<input maxLength="10" type="text" readonly="readonly" 
	      		style="border-left: none; border-right: none; border-top: none; border-bottom-color: black;" 
	      		id="app.jsp.referral.form.a19" name="sourceDoctor" value="${record.target_doctor}" /></td>
	  </tr>
	  <tr>
	    <td id="td_phone" height="30" colspan="4" align="right" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    	<span>联系电话：</span>
	    	<label id="lbl_mobile">
		      	<input maxLength="20" type="text" 
		      		style="border-left: none; border-right: none; border-top: none; border-bottom-color: black;" 
		      		id="app.jsp.referral.form.a20" name="mobile" value="${record.target_mobile}" />
	      	</label>	
	     </td>
	      		
	    </tr>
	  <tr>
	    <td height="30" colspan="4" align="right" bgcolor="#FFFFFF" class="td_field" style="border: none;">
	    	<span>机构名称：</span>
	      	<input maxLength="20" type="text" style="border-left: none; border-right: none; border-top: none; 
		      	border-bottom-color: black;" id="app.jsp.referral.form.a21" name="sourceAgencyName" 
		      	value="${record.target_org_name}" readonly="readonly" />
	     </td>
	    </tr>
	  <tr>
	    <td height="30" colspan="4" align="right" bgcolor="#FFFFFF" class="td_field" style="border: none;"><span>日期：</span>
	      <input name="text28" value="" type="text" id="app.jsp.referral.form.a22"
	      style="border-left: none;border-right: none;border-top: none;border-bottom-color: black"  
	      maxlength="10" readonly="readonly" />
	    </td>
	    </tr>
	</table>
	  <object id="zclcsoftPrintFactory" viewastext="" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="<%=request.getContextPath()%>/resource/print/smsx.cab#Version=6,3,434,12">
	  </object>
	        <div id="com.zclcsoft.print" class="bon_box">
	          <input class="btn" name="button" type="button" style="width:80px;font:12px;" onclick="save()" value="提交" />
	          <input class="btn" name="button" type="button" style="width:80px;font:12px;" onclick="c_print()" value="打印" />
	      </div>
		  
		  
	</div>
</form>
<!-- <span class="page_flag">&copy;深圳循证医学信息技术有限公司</span>	 -->
</body>
</html>