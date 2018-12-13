package com.zhys.fjzl.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zhys.sys.domain.Org;

public class DataPool {

	private static Map<String, Org> orgMap;
	
	static {
		orgMap = new ConcurrentHashMap<>();
	}
	
	public static void putOrg(String orgId, Org org) {
		if(orgMap == null) {
			orgMap = new ConcurrentHashMap<>();
		}
		orgMap.put(orgId, org);
	}
	
	public static Org getOrg(String orgId) {
		return orgMap.get(orgId);
	}
}
