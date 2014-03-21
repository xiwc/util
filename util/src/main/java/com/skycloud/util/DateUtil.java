/** WebUtil.java */
package com.skycloud.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author XiWeiCheng
 * 
 */
public final class DateUtil {

	/** EMPTY [String] */
	public static final String EMPTY = "";
	public static final String FORMAT1 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT2 = "yyyy/MM/dd HH:mm:ss";
	public static final String FORMAT3 = "yyyy-MM-dd";
	public static final String FORMAT4 = "yyyy/MM/dd";

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT2);

	private DateUtil() {
		super();
	}

	/**
	 * 解析日期字符串.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date parse(String date, String... formats) {

		if (date == null || date.length() == 0) {
			return null;
		}

		for (String format : formats) {

			try {
				return new SimpleDateFormat(format).parse(date);
			} catch (Exception e) {
				// e.printStackTrace();
				continue;
			}
		}

		return null;
	}

	/**
	 * 返回当前日期.
	 * 
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * 格式化日期.
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date, String format) {

		dateFormat.applyPattern(format);

		return dateFormat.format(date);
	}

	/**
	 * 获取日期时间秒值.
	 * 
	 * @author xiweicheng
	 * @creation 2014年2月28日 下午1:31:34
	 * @modification 2014年2月28日 下午1:31:34
	 * @param dateTime
	 * @return
	 */
	public static Long getTime(String dateTime) {

		if (StringUtil.isNotEmpty(dateTime)) {
			Date date = parse(dateTime, FORMAT1, FORMAT2, FORMAT3, FORMAT4);

			if (date != null) {
				return date.getTime() / 1000;
			}
		}

		return null;
	}
}
