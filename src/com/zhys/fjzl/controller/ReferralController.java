package com.zhys.fjzl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.fjzl.domain.Record;
import com.zhys.fjzl.service.RecordService;
import com.zhys.sys.domain.Org;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-02-06 <br/>
 * 描述：规则表 Controller
 */
@Controller("ReferralController")
@RequestMapping("/fjzl/referral_*.do")
public class ReferralController extends BaseController{

	@Resource(name = "RecordService")
    private RecordService recordService;

    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	String inputOrgId = request.getParameter("orgId");
    	String id = request.getParameter("id");
    	Record record = recordService.query(id);
    	String unitContent = record.getTarget_unit();
    	JSONArray arr = JSONArray.parseArray(unitContent);
    	List<Org> orgList = new ArrayList<>();
    	for(int i=0; i<arr.size(); i++) {
    		Org org = new Org();
    		JSONObject json = arr.getJSONObject(i);
    		String orgId = json.getString("orgId");
    		String orgName = json.getString("orgName");
    		org.setSo_id(orgId);
    		org.setSo_name(orgName);
    		orgList.add(org);
    	}
    	map.put("record", record);
    	map.put("orgList", orgList);
        return getView(request, map);
    }
    
    /*@RequestMapping(value = "/fjzl/referralRecord/{inputOrgId}/{id}/search.do")
    public String page(@PathVariable String inputOrgId, @PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, Object> map = new HashMap<>();
    	//String inputOrgId = request.getParameter("orgId");
    	//String id = request.getParameter("id");
    	Record record = recordService.query(id);
    	String unitContent = record.getTarget_unit();
    	JSONArray arr = JSONArray.parseArray(unitContent);
    	List<Org> orgList = new ArrayList<>();
    	for(int i=0; i<arr.size(); i++) {
    		Org org = new Org();
    		JSONObject json = arr.getJSONObject(i);
    		String orgId = json.getString("orgId");
    		String orgName = json.getString("orgName");
    		org.setSo_id(orgId);
    		org.setSo_name(orgName);
    		orgList.add(org);
    	}
    	map.put("record", record);
    	map.put("orgList", orgList);
    	ModelAndView mav = new ModelAndView("forward:/fjzl/referral_main.ftl");
    	mav.addAllObjects(map);
        return "forward:/fjzl/referral_main.ftl";
    }*/
}
