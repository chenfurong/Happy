package com.ibm.smartcloud.openstack.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.misc.BASE64Encoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Title:StringUtil<br>
 * Company: IBM<br>
 * Copyright @ 2012 .All rights reserved. <br>
 * @author Liuhl<br>
 * @version 2006-8-24 1.0
 */
public class StringUtil {
	/**
	 * convertGBK3Iso
	 * @param value
	 * @return
	 */
	public static String convertGBK3Iso(String value){
		try {
			return new String(value.getBytes("GBK"),"ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 产生不带连接符（-）的32位Guid值
	 * @return
	 */
	public static String randomGUID(){
		UUID uuid = UUID.randomUUID();
		String guid = uuid.toString().replace("-", "");
		return guid;
	}
	/**
	 * 返回五位随机数
	 * @return
	 */
	public static String radomNumberLength5() {
		Random random = new Random();
		// 返回指定范围的随机数，通过取余数控制
		int result = 0;
		do {
			result = Math.abs(random.nextInt() % 100000);
		} while (result < 10000);

		return String.valueOf(result);
	}
	
	/**
	 * 返回六位随机数
	 * @return
	 */
	public static String radomNumberLength6() {
		Random random = new Random();
		// 返回指定范围的随机数，通过取余数控制
		int result = 0;
		do {
			result = Math.abs(random.nextInt() % 1000000);
		} while (result < 100000);

		return String.valueOf(result);
	}
	
	public static String getDomainFromUrl(String url){
		//String pattern = "[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)" ;
		String pattern = "(?<=http://|\\.)[\\w\\.]+[^/|:]" ;
		Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        
		Matcher matcher = p.matcher(url);  
		matcher.find();  
		return matcher.group();
	}

	
	//格式化错误json信息
	public static String jsonErrorToErrorMessage(String error) {
		String message = "";
		if(error.indexOf("403 Forbidden")>-1){
			message = "403 Forbidden. "+error.substring(error.indexOf("\n\n")+2, error.lastIndexOf("\n\n"));
		}else{
			message = "\"message\": \"";
			
			int start = error.indexOf(message);
			message = error.substring(start+message.length());
			String[] arr = message.split("\"");
			message = arr[0];
		}
		return message;
	}
	public static String unicodeEscape(String json){
		ObjectMapper om = new ObjectMapper();
		try {
			json = om.writer(new HtmlCharacterEscapes()).writeValueAsString(om.readTree(json));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	public static class HtmlCharacterEscapes extends CharacterEscapes {
		
		private static final long serialVersionUID = 1L;
		private final int[] asciiEscapes;

		public HtmlCharacterEscapes() {
			// start with set of characters known to require escaping
			// (double-quote, backslash etc)
			int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
			// and force escaping of a few others:
			esc['"'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['<'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['>'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['&'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['\''] = CharacterEscapes.ESCAPE_STANDARD;
			asciiEscapes = esc;
		}

		// this method gets called for character codes 0 - 127
		@Override
		public int[] getEscapeCodesForAscii() {
			return Arrays.copyOf(asciiEscapes, asciiEscapes.length);
		}

		@Override
		public SerializableString getEscapeSequence(int ch) {
			return new SerializedString(String.format("\\u%04x", ch));
		}
	}
	public static String b64encode(String raw, String charset) {
		try {
			return new BASE64Encoder().encode(raw.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	public static String encode(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return new String(encoder.encodeBuffer(str.getBytes())).trim();
	}
	
	public static String decode(String str) {
		try {
			sun.misc.BASE64Decoder decode = new sun.misc.BASE64Decoder();
			return new String(decode.decodeBuffer(str));
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e.getCause());
		}
	}

}
