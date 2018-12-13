package com.zhys.fjzl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.core.domain.PageParam;
import com.zhys.core.domain.PageResult;
import com.zhys.core.util.BeanUtil;
import com.zhys.core.util.EasyuiUtil;
import com.zhys.fjzl.domain.BodyCheck;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.service.BodyCheckService;
import com.zhys.fjzl.service.CheckMapperService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：知识库检查表 Controller
 */
@Controller("BodyCheckController")
@RequestMapping("/fjzl/bodyCheck_*.do")
public class BodyCheckController extends BaseController{

    @Autowired
    @Qualifier("BodyCheckService")
    private BodyCheckService bodyCheckService;
    
    @Autowired
    @Qualifier("CheckMapperService")
    private CheckMapperService checkMapperService;
    

    @ActionAnnotation(name = "知识库检查表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }
    
    @ActionAnnotation(name = "知识库检查表入口",group = "查询")
    public ModelAndView reviewed(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getView(request);
    }

    @ActionAnnotation(name = "知识库检查表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam bodyCheck = BeanUtil.wrapPageBean(request);
        PageResult pageResult = bodyCheckService.pageList(bodyCheck);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "知识库检查表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        BodyCheck bodyCheck = bodyCheckService.query(request.getParameter("id"));
        model.put("bodyCheck",bodyCheck);
        return getView(request,model);
    }

    @ActionAnnotation(name = "知识库检查表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "知识库检查表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BodyCheck bodyCheck = BeanUtil.wrapBean(BodyCheck.class, request.getParameter("bodyCheck"));
        PageParam countParam = new PageParam();
        countParam.put("name_eq", bodyCheck.getName());
        if(bodyCheckService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        bodyCheck.setCreate_time(now);
        bodyCheck.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = bodyCheckService.create(bodyCheck);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "基础知识库检查表修改",group = "停用")
    public ModelAndView beforeDisable(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String[] arr = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
    	String result = "0";
    	for(String checkId : arr) {
    		pageParam.put("check_id", checkId);
    		result = checkMapperService.updateCheck(pageParam);
    		if(!"0".equals(result)) {
    			break;
    		}
    	}
    	return responseText(response, result);
    }
    @ActionAnnotation(name = "判断基础检查表与机构检查是否有关联",group = "修改")
    public ModelAndView beforeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String result = checkMapperService.updateCheck(pageParam);
    	return responseText(response, result);
    }
    @ActionAnnotation(name = "知识库检查表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        BodyCheck bodyCheck = bodyCheckService.query(request.getParameter("id"));
        model.put("bodyCheck",bodyCheck);
        return getView(request,model);
    }

    @ActionAnnotation(name = "知识库检查表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BodyCheck bodyCheck = BeanUtil.wrapBean(BodyCheck.class, request.getParameter("bodyCheck"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", bodyCheck.getId());
        countParam.put("name_eq", bodyCheck.getName());
        if(bodyCheckService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        bodyCheck.setLast_update_time(now);
        bodyCheck.setLast_updator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = bodyCheckService.update(bodyCheck);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "知识库检查表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = bodyCheckService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "知识库检查表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = bodyCheckService.delete(ids);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "知识库检查表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = bodyCheckService.review(ids,now,reviewor);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "知识库检查表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = bodyCheckService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "知识库检查表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = bodyCheckService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入知识库检查数据",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
}
