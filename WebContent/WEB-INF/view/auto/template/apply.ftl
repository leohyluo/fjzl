<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<title>百孝行——寻找100位孝爱大使</title>
<link href="style/style.css" rel="stylesheet" type="text/css">
</head>

<body>

<div id="wrapper" class="fullScreen">

	<div id="scroller" style="overflow: visible;">
        <a id="openApp" href='' style="display:none;">
            <img width="100%" src="images/header_download.jpg">
        </a>
        <div id="banner">
            <#if active_summary_type==1>
            <img src="${active_summary}" alt="" width="100%" />
            <#else>
                ${active_summary}
            </#if>
        </div>
        <div id="active_intro">
            
        </div>
        <button id="applybtn">
            <a class="enabled">${buttonTitle}<span class="participantNum">（已有${initPeople}人参加）</span></a>
            <a class="disabled">已报名<!--<span class="participantNum">（已有13098人参加）</span>--></a>
        </button>

        <div id="active_rule" class="textArea" >
            ${active_content}
            
            <!--<div style="height:1em;"> </div>
            <h3>活动逻辑：</h3>
            <ul>
                <li><span>1</span>非注册用户可浏览，并参与本次活动</li>
                <li><span>2</span>活动需填写姓名，手机号，点击提交则提示参与成功；</li>
                <li><span>3</span>关注微信与报名按钮并列，非活动参与必选项（关注微信成功，提示可享受此优惠）。</li>
            </ul>-->
        </div>
        <div class="textArea" >
            <h3>活动咨询：</h3>
            <ul>
                <li>客服QQ：4000603351</li>
                <li>客服电话：400-060-3351</li>
                <li>服务邮箱：info@kkmaiyao.com</li>
                <li>新浪/腾讯微博：@康康买药</li>
                <li>微信公众平台：kkmaiyao</li>
            </ul>
           <!-- <div class="bottomMargin">
            </div>-->
        </div>
	</div>
    
</div>
<div id="applyForm">
    <div class="applybg"></div>
    <form>
        <div id="applyList" class="input">
            <#list collectList as type>
                <#if type == 0>
                    <p><label>性别：</label>
                        <span><input id="sexMan" class="radio" type="radio" value="male" checked="checked" name="sex"/>男</span>
                        <span><input class="radio" type="radio" value="female" name="sex"/>女</span>
                    </p>
                <#elseif type == 1>
                    <p><label>年龄：</label>
                        <select id="userAge">
                            <option value="0">16岁以下</option>
                            <option value="1">16-24岁</option>
                            <option value="2">25-30岁</option>
                            <option value="3">31-40岁</option>
                            <option value="4">41-50岁</option>
                            <option value="5">51-60岁</option>
                            <option value="6">61-70岁</option>
                            <option value="7">71-80岁</option>
                        </select>
                    </p>
                <#elseif type == 2>
                    <!--<p><label>所在地：</label></p>-->
                <#elseif type == 3>
                    <p><label>QQ：</label><input id="userQQ" class="textInput" name="" type ="tel"></p>
                <#elseif type == 4>
                    <p><label>手机号：</label><input id="userTel" class="textInput" name="" type ="tel"></p>
                <#elseif type == 5>
                    <p><label>姓名：</label><input id="userName" class="textInput" name="" type="text"></p>
                </#if>
            </#list>
        </div>
        <div class="btn">
            <input id="submit" name="" type="button" value="报名参加">
        </div>
    </form>
</div>
<!--<div id="follow">
    <p>关注微信，有惊喜</p>
    <a class="closebtn"></a>
</div>
-->
<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/iscroll.js"></script>
<script>
	var collectList = [];
	<#list collectList as type>
        collectList.push(${type});
    </#list>
//	collectList = [0,1,2, 3, 4, 5];
	if (collectList.length == 0 || (collectList.length == 0 && collectList[0] == 2)) {
		// 不现实报名内容
		$('#applyList').hide();
	}
	var emptyHint = {
		0 : "",
		1 : "",
		2 : "",
		3 : "请输入QQ号",
		4 : "请输入手机号",
		5 : "请输入姓名",
	};
	var elementIds = {
		0 : "sexMan",
		1 : "userAge",
		2 : "",
		3 : "userQQ",
		4 : "userTel",
		5 : "userName",
	};
	var collectKeys = {
		0 : "sex",
		1 : "age",
		2 : "regionCode",
		3 : "qq",
		4 : "tel",
		5 : "userName",
	};
</script>
<script src="js/webMutual.js"></script>
<script src="js/active.js?09988734hduf"></script>

</body>
</html>
