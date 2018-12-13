package com.zhys.fjzl.core.checker;

import java.util.HashMap;
import java.util.Map;

import com.zhys.fjzl.enums.ReferralRuleType;

public class ReferralRuleMapping {

	private static Map<ReferralRuleType, ReferralRule> map = new HashMap<>();
	
	public static ReferralRule get(ReferralRuleType type) {
		return map.get(type);
	}
	
	public static void register(ReferralRuleType type, ReferralRule checker) {
		map.put(type, checker);
	}
}
