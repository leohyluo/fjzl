<#--
版权：智慧药师 <br/>
作者：dail <br/>
生成日期：2016-12-19 <br/>
描述：报表主页面
-->
<#include "/WEB-INF/view/macro.ftl"/>

<#assign checkAdd = check("添加")>
<#assign checkEdit = check("修改")>
<#assign checkDelete= check("删除")>
<#assign checkDec= check("解密")>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>药品数量统计报表</title>
    
    <#include "/WEB-INF/view/linkScript.ftl"/>
    
    <script type="text/javascript">
        $(function() {
            $("body").layout();
            $("#form").css("display","block");
            
            var option = {
			    color: ['#3398DB'],
			    title : {
			      text : '药品数量统计报表',
			    },
			    tooltip : {
			        trigger: 'axis',
			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
			        }
			    },
			    grid: {
			        left: '3%',
			        right: '4%',
			        bottom: '3%',
			        containLabel: true
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun'],
			            axisTick: {
			                alignWithLabel: true
			            }
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : [
			        {
			            name:'数量',
			            type:'bar',
			            barWidth: '60%',
			            data:[10, 52, 200, 334, 390, 330, 220]
			        }
			    ]
			};
            
            function load(params) {
            	$.post('${path}/fjzl/report_getIllItemStatisticData.do', params,function(data){
	            	var series_data = data.series_data;
	            	if(series_data && series_data.length > 0) {
	            		$('#div_no_data').hide();
	            		$('#main').show();
	            		option.xAxis[0].data = data.xAxis_data;
		            	option.series[0].data = data.series_data;
		            	draw(option);
	            	} else {
	            		$('#main').hide();
	            		$('#div_no_data').show();
	            	}
            	}, 'json');
            }
            
            var draw = function(_option) {
            	var myChart = echarts.init(document.getElementById('main'));
            	myChart.setOption(_option);
            };
            //draw end
            
            //查询
            $('#searchBtn').live('click', function(){
				var params = $('#form').serializeJson();
            	load(params);            
            })
            
            load({'refresh':'1','illegaType':'2'});
        });
    </script>
</head>
<body class="easyui-layout">
<div region="west" split="true" border="true" title="操作面板" style="width:250px;padding:10px;">
    <form id="form" name="form" style="display: none">
    	<input type="hidden" name="illegaType" value="2">
        <#include "/WEB-INF/view/fjzl/report_search_bar.ftl"/>
    </form>
</div>
<div region="center" title="当前位置：药品数量统计报表">
    <div id="main" align="center" style="width: 100%;height:100%;"></div>
    <div id="div_no_data" align="center" style="width: 900px;height:600px;display:none">暂无数据</div>
</div>

</body>
</html>