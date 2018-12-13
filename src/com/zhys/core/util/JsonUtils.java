package com.zhys.core.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class JsonUtils {
	
	@SuppressWarnings("static-access")
	public static JSONObject parese(HttpServletRequest request) throws IOException {
		InputStream is = request.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader br = new BufferedReader(isr);
		String line = "";
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		isr.close();
		is.close();
		String str = sb.toString();
		JSONObject json = JSONObject.parseObject(str);
		return json;
	}
	
	public static String getString(JSONObject json, String key) {
		if(json.containsKey(key)) {
			return json.getString(key);
		}
		return "";
	}
	
}
