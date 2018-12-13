package com.zhys.util;

import java.util.Date;

public class TimeUtil {
	/**
	 * 将时间类型转换为long类型的值 判断时间类型是否为空,如果为空将其设置为指定的默认值
	 * @author cychen
	 * @param date
	 * @return long
	 */
	public static long changeDateToLong(Date date, long def) {
		if (date != null) {
			return date.getTime() / 1000;
		} else {
			return def;
		}
	}
}
