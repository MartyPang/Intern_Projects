package com.sinocontact.common;

import org.apache.commons.lang3.StringUtils;

public class StringFilterUtil {
	/**
	 * 过滤限制字符
	 * @param str
	 * @return
	 */
	public static String filterLimitChar(String str){
		if(StringUtils.isNotEmpty(str)){
			str=str.replace("'", "");
			str=str.replace("\"", "");
			str=str.replace("=", "");
		}
		return str;
	}
	
	public static String filterWechatEmojiString (String str) {
		if(StringUtils.isNotEmpty(str)){
			str=str.replaceAll("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", "");
		}
		return str;
	}
}
