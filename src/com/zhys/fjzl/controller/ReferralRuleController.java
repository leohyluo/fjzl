package com.zhys.fjzl.controller;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.BeanUtil;
import com.zhys.core.util.EasyuiUtil;
import com.zhys.fjzl.domain.ReferralRule;
import com.zhys.fjzl.service.ReferralRuleService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-02-06 <br/>
 * 描述：规则表 Controller
 */
@Controller("ReferralRuleController")
@RequestMapping("/fjzl/referralRule_*.do")
public class ReferralRuleController extends BaseController{

    @Autowired
    @Qualifier("ReferralRuleService")
    private ReferralRuleService referralRuleService;

    @ActionAnnotation(name = "规则表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "规则表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam referralRule = BeanUtil.wrapPageBean(request);
        PageResult pageResult = referralRuleService.pageList(referralRule);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "规则表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        ReferralRule referralRule = referralRuleService.query(request.getParameter("id"));
        model.put("referralRule",referralRule);
        return getView(request,model);
    }

    @ActionAnnotation(name = "规则表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "规则表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReferralRule referralRule = BeanUtil.wrapBean(ReferralRule.class, request.getParameter("referralRule"));
        String result = referralRuleService.create(referralRule);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "规则表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        ReferralRule referralRule = referralRuleService.query(request.getParameter("id"));
        model.put("referralRule",referralRule);
        return getView(request,model);
    }

    @ActionAnnotation(name = "规则表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ReferralRule referralRule = BeanUtil.wrapBean(ReferralRule.class, request.getParameter("referralRule"));
        String result = referralRuleService.update(referralRule);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "规则表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = referralRuleService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "规则表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = referralRuleService.delete(ids);
        return responseText(response, result);
    }
    
	@ActionAnnotation(name = "转诊",group = "转诊")
	public ModelAndView referral(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("showReferralPage start.");
		return getView(request);
	}
}
