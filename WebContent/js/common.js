
/**
 * 校验是否为空
 */
function verifyNull(id){
	var value = $("#"+id).attr("value");
    if(value==null || value==""){
    	$("#"+id+"_tips").empty();
    	$("#"+id).after("<a id='"+id+"_tips' style='color:red'>带星号的为必须录项目!</a>");
    	return false;
    }else{
    	$("#"+id+"_tips").empty();
    	return true;
    }
}

/*
 * 校验金额
 */
function verifyNum(id){
	var value = $("#"+id).attr("value");
    if(value==null || value=="" || parseFloat(value)<=0){
    	$("#"+id+"_tips").empty();
    	$("#"+id).after("<a id='"+id+"_tips' style='color:red'>金额必须大于0!</a>");
    	return false;
    }else{
    	$("#"+id+"_tips").empty();
    	return true;
    }
}

/**
* 空格输入去除
* @param e
* @returns {Boolean}
*/
function inputSapceTrim(e, this_temp) {
	this_temp.value = Trim(this_temp.value, "g");
	var keynum;
	if (window.event) // IE
	{
		keynum = e.keyCode
	} else if (e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which
	}
	if (keynum == 32) {
		return false;
	}
	return true;
} 

/**
* 禁止空格输入
* @param e
* @returns {Boolean}
*/
function banInputSapce(e) {
	var keynum;
	if (window.event) // IE
	{
		keynum = e.keyCode
	} else if (e.which) // Netscape/Firefox/Opera
	{
		keynum = e.which
	}
	if (keynum == 32) {
		return false;
	}
	return true;
} 

function keyPress(ob) {
	if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;
	if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
		ob.o_value = ob.value;
}
function keyUp(ob) {
	if (!ob.value.match(/^[\+\-]?\d*?\.?\d*?$/))
		ob.value = ob.t_value;
	else
		ob.t_value = ob.value;
	if (ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))
		ob.o_value = ob.value;
}
function onBlur(ob) {
	if (!ob.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))
		ob.value = ob.o_value;
	else {
		if (ob.value.match(/^\.\d+$/))
			ob.value = 0 + ob.value;
		if (ob.value.match(/^\.$/))
			ob.value = 0;
		ob.o_value = ob.value
	}
	;
}


$.fn.mfycOnBlue = function(){
	var id = $(this).attr("id");
	$("#"+id).blur(function(event){
		verifyNull(id);
	});
};

$.fn.mfycNumOnBlue = function(){
	var id = $(this).attr("id");
	$("#"+id).blur(function(event){
		verifyNum(id);
	});
};
var GetUrlParam = function (name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return (r[2]); return null;
}
