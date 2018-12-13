package com.zhys.fjzl.enums;

public enum FileStatus {

	UPLOADED(0),
	SAVE_TO_TMP(1),
	SAVE_TO_REAL(2),
	FINISH(3);
	
	private int status;
	
	private FileStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
