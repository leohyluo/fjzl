package com.zhys.fjzl.report.builder;

import com.zhys.fjzl.report.builder.vo.piesimple.PieSimpleVo;

public abstract class PieSimpleBuilder extends AbstractReportBuilder<PieSimpleVo> {

	@Override
	protected Class<PieSimpleVo> getReportClass() {
		return PieSimpleVo.class;
	}

	@Override
	protected String getReportTemplate() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append("		    title : { ");
		sb.append("		        text: '某站点用户访问来源', ");
		sb.append("		        subtext: '纯属虚构', ");
		sb.append("		        x:'center' ");
		sb.append("		    }, ");
		sb.append("		    tooltip : { ");
		sb.append("		        trigger: 'item', ");
		sb.append("		        formatter: \"{a} <br/>{b} : {c} ({d}%)\" ");
		sb.append("		    }, ");
		sb.append("		    legend: { ");
		sb.append("		        orient: 'vertical', ");
		sb.append("		        left: 'left', ");
		sb.append("		        data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎'] ");
		sb.append("		    }, ");
		sb.append("		    series : [ ");
		sb.append("		        { ");
		sb.append("		            name: '访问来源', ");
		sb.append("		            type: 'pie', ");
		sb.append("		            radius : '55%', ");
		sb.append("		            center: ['50%', '60%'], ");
		sb.append("		            data:[ ");
		sb.append("		                {value:335, name:'直接访问'}, ");
		sb.append("		                {value:310, name:'邮件营销'}, ");
		sb.append("		                {value:234, name:'联盟广告'}, ");
		sb.append("		                {value:135, name:'视频广告'}, ");
		sb.append("		                {value:1548, name:'搜索引擎'} ");
		sb.append("		            ], ");
		sb.append("		            itemStyle: { ");
		sb.append("		                emphasis: { ");
		sb.append("		                    shadowBlur: 10, ");
		sb.append("		                    shadowOffsetX: 0, ");
		sb.append("		                    shadowColor: 'rgba(0, 0, 0, 0.5)' ");
		sb.append("		                } ");
		sb.append("		            } ");
		sb.append("		        } ");
		sb.append("		    ] ");
		sb.append("		} ");
		return sb.toString();
	}
}
