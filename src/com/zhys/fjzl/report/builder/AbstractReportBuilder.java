package com.zhys.fjzl.report.builder;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public abstract class AbstractReportBuilder <T> {

	protected abstract String getReportTemplate();
	
	protected abstract Class<T> getReportClass();
	
	protected abstract Map<String, Object> getReportParameters();
	
	protected abstract Object loadData(Map<String, Object> param);
	
	protected abstract void fillData(T t, Object data);
	
	public String build() {
		//将报表模版字符串转为对应的实体类
		T t = stringToEntity();
		//获取生成报表的参数
		Map<String, Object> param = getReportParameters();
		//根据请求参数从数据库查询报表需要的数据
		Object data = loadData(param);
		//将数据填充到报表实体中
		fillData(t, data);
		//报表实体转json字符串
		String option = JSON.toJSONString(t);
		return option;
	}
		
	@SuppressWarnings("static-access")
	private T stringToEntity() {
		String templateStr = getReportTemplate();
		JSONObject json = JSONObject.parseObject(templateStr);
		Class<T> reportClass = getReportClass();
		T t = (T) json.toJavaObject(json, reportClass);
		//T t = JSON.parseObject(templateStr, reportClass);
		return t;
	}
	
}
