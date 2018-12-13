package com.zhys.core.enums;

/**
 * 版权：智慧药师 <br/>
 * 作者：xuan.zhou@zhys.com <br/>
 * 生成日期：2013-12-12 <br/>
 * 描述：短信类型枚举
 */
public enum SmsSendState implements BaseEnum<Long> {

	VALID(0l, "待发送"), AUDIT(1l, "成功"), CUSTOM(2l, "失败");

	private Long code;
	private String desc;

	private SmsSendState(Long code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Override
	public Long getCode() {
		return this.code;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

}
