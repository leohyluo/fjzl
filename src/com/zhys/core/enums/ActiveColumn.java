package com.zhys.core.enums;


/**
 * 版权：智慧药师 <br/>
 * 作者：yong.chen@zhys.com <br/>
 * 生成日期：2014-3-7 <br/>
 * 描述：〈描述〉
 */

public enum ActiveColumn implements BaseEnum<String>{
	FAVOUR("1","优惠活动");
	private String code;
	private String desc;

	private ActiveColumn(String code, String desc){
		this.code = code;
		this.desc = desc;
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
	
}
