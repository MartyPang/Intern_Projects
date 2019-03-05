package com.sinocontact.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;



public class Base64Code {
	private static final Logger logger=Logger.getLogger(Base64Code.class);
	/**
	 * 编码
	 * @param bstr
	 * @return String
	 */
	public static String encode(String str) {
		String encodeString="";
		Base64 code = new Base64();
		try {
			byte[] bstr=code.encode(str.getBytes());
			encodeString=new String(bstr,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("",e);
		}
		return encodeString;
	}

	/**
	 * 解码
	 * @param str
	 * @return string
	 */
	public static String  decode(String str) {
		String resultString="";
		byte[] bt = null;
		try {
			Base64 code = new Base64();
			bt = code.decode(str);
			resultString=new String(bt,"UTF-8");
		} catch (Exception e) {
			logger.error("",e);
		}
		return resultString;
	}
	
	
	
	
	
	
}
