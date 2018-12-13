package com.zhys.core.enums;


/**
 * 版权：智慧药师 <br/>
 * 作者：yong.chen@zhys.com <br/>
 * 生成日期：2014-3-7 <br/>
 * 描述：〈描述〉
 */

public enum ActiveType implements BaseEnum<String> {
	APPLY("APPLY","报名活动","/template/apply_active.html");
	private String code;
	private String desc;
	private String template;

	private ActiveType(String code,String desc,String template){
		this.code = code;
		this.desc = desc;
		this.template = template;
	}
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	@Override
	public String getDesc() {
		// TODO Auto-generated method stub
		return desc;
	}
	
	public String getTemplate() {
		return template;
	}
	
	
}
