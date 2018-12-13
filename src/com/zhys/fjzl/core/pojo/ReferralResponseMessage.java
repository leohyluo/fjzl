package com.zhys.fjzl.core.pojo;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.util.CollectionUtils;
import com.zhys.sys.domain.Org;

public class ReferralResponseMessage implements Serializable {

	public static final long serialVersionUID = -2297332672297066370L;
	protected Long code;
	protected String message;
	protected List<Org> data;
	private String url;

	public ReferralResponseMessage() { 
		
	}

	public ReferralResponseMessage(ReferralRes res, String url) {
		if(res != null) {
			Long code = res.getResult().getType();
			String message = res.getMessage();
			List<Org> data = res.getReferralOrgList();
			
			this.code = code;
			this.message = message;
			this.data = data;			
		}
		this.url = url;
	}	

	public String toJsonString() {
		JSONObject json = new JSONObject();
		json.put("code", this.code);
		json.put("message", this.message);
		if(CollectionUtils.isNotEmpty(data)) {
			JSONArray jarr = new JSONArray();
			for(Org item : data) {
				JSONObject itemJson = new JSONObject();
				itemJson.put("name", item.getSo_name());
				itemJson.put("grade", item.getSo_grade());
				jarr.add(itemJson);
			}
			json.put("data", jarr);
		}
		json.put("url", this.url);
		return json.toJSONString();
	}
}
