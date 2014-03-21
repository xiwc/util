/**
 * SqlUtil.java
 */
package com.skycloud.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.skycloud.constant.Constants;

/**
 * SQL处理工具类.
 * 
 * @creation 2013-10-11 上午11:51:59
 * @modification 2013-10-11 上午11:51:59
 * @company Skycloud
 * @author xiweicheng
 * @version 1.0
 * 
 */
public final class SqlUtil {

	/** EMPTY [String] */
	public static final String EMPTY = "";

	private SqlUtil() {
		super();
	}

	/**
	 * 替换sql中的占位key, eg:{?1}
	 * 
	 * @param paramsMap
	 *            参数存放map
	 * @param paramKey
	 *            参数存放map中对应的key
	 * @param replaceKey
	 *            sql中可替换的占位符key
	 * @param replaceValue
	 *            要替换内容 eg1: 包含{?1} eg2: ...
	 * @param emptyValue
	 *            paramsMap中paramKey对应的值为空时,替换用的值
	 * @param placeholderVals
	 *            替换内容参数[replaceValue]中的占位符[{?1} {?2} ...]要替换的值
	 */
	public static void replace(Map<String, String> paramsMap, String paramKey, String replaceKey, String replaceValue,
			String emptyValue, String... placeholderVals) {

		String value = paramsMap.get(paramKey);

		if (value == null || value.length() == 0) {

			emptyValue = emptyValue == null ? EMPTY : emptyValue;

			if (placeholderVals != null && placeholderVals.length > 0) {

				for (int i = 0; i < placeholderVals.length; i++) {
					String val = placeholderVals[i];

					if (val.startsWith("?")) {
						String pVal = paramsMap.get(val.substring(1));
						pVal = pVal == null ? EMPTY : pVal;
						emptyValue = emptyValue.replace("{?" + (i + 1) + "}", pVal);
					} else {
						emptyValue = emptyValue.replace("{?" + (i + 1) + "}", val);
					}
				}
			} else {

				if (emptyValue.indexOf("{?1}") > -1) {
					emptyValue = emptyValue.replace("{?1}", value == null ? EMPTY : value);
				}
			}

			paramsMap.put(replaceKey, emptyValue);
		} else {

			if (placeholderVals != null && placeholderVals.length > 0) {

				for (int i = 0; i < placeholderVals.length; i++) {
					String val = placeholderVals[i];

					if (val.startsWith("?")) {
						String pVal = paramsMap.get(val.substring(1));
						pVal = pVal == null ? EMPTY : pVal;
						replaceValue = replaceValue.replace("{?" + (i + 1) + "}", pVal);
					} else {
						replaceValue = replaceValue.replace("{?" + (i + 1) + "}", val);
					}
				}
			} else {

				if (replaceValue.indexOf("{?1}") > -1) {
					replaceValue = replaceValue.replace("{?1}", value == null ? EMPTY : value);
				}
			}

			paramsMap.put(replaceKey, replaceValue);
		}
	}

	/**
	 * 替换sql中的占位key, eg:{?1}
	 * 
	 * @param paramsMap
	 *            参数存放map
	 * @param replaceKey
	 *            sql中可替换的占位符key
	 * @param replaceValue
	 *            要替换内容 eg1: 包含{?1} eg2: ...
	 * @param placeholderVals
	 *            替换内容参数[replaceValue]中的占位符[{?1} {?2} ...]要替换的值
	 */
	public static void replace2(Map<String, String> paramsMap, String replaceKey, String replaceValue,
			String... placeholderVals) {

		if (placeholderVals != null && placeholderVals.length > 0) {

			for (int i = 0; i < placeholderVals.length; i++) {
				String val = placeholderVals[i];

				if (val.startsWith("?")) {
					String pVal = paramsMap.get(val.substring(1));
					pVal = pVal == null ? EMPTY : pVal;
					replaceValue = replaceValue.replace("{?" + (i + 1) + "}", pVal);
				} else {
					replaceValue = replaceValue.replace("{?" + (i + 1) + "}", val);
				}
			}
		}

		paramsMap.put(replaceKey, replaceValue);
	}

	/**
	 * 用空串替换sql中的占位符.
	 * 
	 * @param paramsMap
	 * @param replaceKey
	 */
	public static void empty(Map<String, String> paramsMap, String replaceKey) {

		paramsMap.put(replaceKey, EMPTY);
	}

	/**
	 * 分页sql处理.
	 * 
	 * @param isPage
	 *            是否分页
	 * @param paramsMap
	 *            参数存放map
	 * @param replaceKey
	 *            sql中可替换的占位符key
	 * @param replaceValue
	 *            要替换内容 eg1: 包含{?1} eg2: ...
	 * @param placeholderVals
	 *            替换内容参数[replaceValue]中的占位符[{?1} {?2} ...]要替换的值
	 */
	public static void page(boolean isPage, Map<String, String> paramsMap, String replaceKey, String replaceValue,
			String... placeholderVals) {

		if (isPage) {
			replace2(paramsMap, replaceKey, replaceValue, placeholderVals);
		} else {
			empty(paramsMap, replaceKey);
		}
	}

	/**
	 * 将查询条件拼接成in查询条件.
	 * 
	 * @author xiweicheng
	 * @creation 2013年11月9日 下午4:07:10
	 * @modification 2013年11月9日 下午4:07:10
	 * @param arr
	 * @return
	 */
	public static String joinAsStrIn(String[] arr) {

		if (arr == null || arr.length == 0) {
			return EMPTY;
		}

		for (int i = 0; i < arr.length; i++) {
			arr[i] = "'" + arr[i] + "'";
		}

		return StringUtil.join(",", arr);
	}

	/**
	 * 将查询条件拼接成in查询条件.
	 * 
	 * @author xiweicheng
	 * @creation 2013年11月9日 下午4:07:10
	 * @modification 2013年11月9日 下午4:07:10
	 * @param list
	 * @return
	 */
	public static String joinAsStrIn(List<String> list) {

		if (list == null || list.size() == 0) {
			return EMPTY;
		}

		for (int i = 0; i < list.size(); i++) {
			list.set(i, "'" + list.get(i) + "'");
		}

		return StringUtil.join(",", list);
	}

	/**
	 * 将查询条件拼接成in查询条件.
	 * 
	 * @author xiweicheng
	 * @creation 2013年11月9日 下午4:07:10
	 * @modification 2013年11月9日 下午4:07:10
	 * @param arr
	 * @return
	 */
	public static String joinAsIntIn(String[] arr) {

		if (arr == null || arr.length == 0) {
			return EMPTY;
		}

		return StringUtil.join(",", arr);
	}

	/**
	 * 将查询条件拼接成in查询条件.
	 * 
	 * @author xiweicheng
	 * @creation 2013年11月9日 下午4:07:10
	 * @modification 2013年11月9日 下午4:07:10
	 * @param list
	 * @return
	 */
	public static String joinAsIntIn(List<String> list) {

		if (list == null || list.size() == 0) {
			return EMPTY;
		}

		return StringUtil.join(",", list);
	}

	/**
	 * 将查询条件拼接成in查询条件.
	 * 
	 * @author xiweicheng
	 * @creation 2013年11月9日 下午4:06:37
	 * @modification 2013年11月9日 下午4:06:37
	 * @param str
	 * @return
	 */
	public static String joinAsIn(Object str) {

		if (str == null || EMPTY.equals(str)) {
			return EMPTY;
		}

		String[] arr = String.valueOf(str).split(Constants.COMMA);

		List<String> list = new ArrayList<>();

		for (String item : arr) {
			if (StringUtil.isNotEmpty(item.trim())) {
				list.add(item);
			}
		}

		return joinAsStrIn(list.toArray(new String[0]));
	}

	/**
	 * 获取一组值.
	 * 
	 * @author xiweicheng
	 * @creation 2013年11月30日 下午5:58:37
	 * @modification 2013年11月30日 下午5:58:37
	 * @param map
	 * @param keyArr
	 * @return
	 */
	public static Object[] getValues(Map<String, Object> map, String... keyArr) {

		Object[] objArr = new Object[keyArr.length];

		if (keyArr.length > 0 && map != null && map.size() > 0) {
			int i = 0;

			for (String key : keyArr) {
				objArr[i++] = map.get(key);
			}
		}

		return objArr;
	}

}
