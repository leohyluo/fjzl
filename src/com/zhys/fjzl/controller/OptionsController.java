package com.zhys.fjzl.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.BeanUtil;
import com.zhys.core.util.EasyuiUtil;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.domain.EbmIcd9;
import com.zhys.fjzl.domain.Options;
import com.zhys.fjzl.domain.ReferralRule;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.service.OptionsService;
import com.zhys.fjzl.service.ReferralRuleService;
import com.zhys.sys.domain.User;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-01-16 <br/>
 * 描述：转诊规则表 Controller
 */
@Controller("OptionsController")
@RequestMapping("/fjzl/options_*.do")
public class OptionsController extends BaseController{

    @Autowired
    @Qualifier("OptionsService")
    private OptionsService optionsService;
    @Autowired
    @Qualifier("ReferralRuleService")
    private ReferralRuleService referralRuleService;

    @ActionAnnotation(name = "转诊规则表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "转诊规则表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam mapper = BeanUtil.wrapPageBean(request);
        User user = getLoginUser(request);
        if(!GlobalConstant.SUPER_ADMIN.equals(user.getSu_admin())) {
        	mapper.put("org_id", user.getSo_id());
        }
        PageResult pageResult = optionsService.pageList(mapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "转诊规则表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        Options options = optionsService.query(request.getParameter("id"));
        model.put("options",options);
        return getView(request,model);
    }
    @ActionAnnotation(name = "转诊规则表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ReferralRule referralRule = BeanUtil.wrapBean(ReferralRule.class, request);
        List<ReferralRule> ruleList = referralRuleService.listQuery(referralRule);
       ModelAndView mav = getView(request);
       mav.addObject("ruleList", ruleList);
        return mav;
    }
    
    
    @ActionAnnotation(name = "转诊规则表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Options options = BeanUtil.wrapBean(Options.class, request.getParameter("options"));
        PageParam countParam = new PageParam();
        countParam.put("org_id",getLoginUser(request).getSo_id());
        countParam.put("referral_id", options.getReferral_id());
        if(optionsService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        
        Date now = new Date();
        options.setOrg_id(getLoginUser(request).getSo_id());
        options.setCreate_time(now);
        options.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = optionsService.create(options);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "转诊规则表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        Options options = optionsService.query(request.getParameter("id"));
        model.put("options",options);
        return getView(request,model);
    }

    @ActionAnnotation(name = "转诊规则表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Options options = BeanUtil.wrapBean(Options.class, request.getParameter("options"));
        Date now = new Date();
        options.setLast_update_time(now);
        options.setLast_updator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = optionsService.update(options);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "转诊规则表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = optionsService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "转诊规则表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = optionsService.delete(ids);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "转诊规则表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = optionsService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "转诊规则表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = optionsService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
}
