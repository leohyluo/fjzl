package com.zhys.fjzl.enums;

public enum Source {

	IMPORT(0),
	PAGE_ADD(1);
	
	private long status;
	
	private Source(long status) {
		this.status = status;
	}

	public long getStatus() {
		return status;
	}
}
