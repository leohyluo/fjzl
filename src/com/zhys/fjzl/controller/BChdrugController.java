package com.zhys.fjzl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
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
import com.zhys.fjzl.domain.BChdrug;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.service.BChdrugService;
import com.zhys.fjzl.service.DrugMapperService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：中药饮片 Controller
 */
@Controller("BChdrugController")
@RequestMapping("/fjzl/bChdrug_*.do")
public class BChdrugController extends BaseController{

    @Autowired
    @Qualifier("BChdrugService")
    private BChdrugService bChdrugService;
    
    @Resource(name = "DrugMapperService")
    private DrugMapperService drugMapperService;

    @ActionAnnotation(name = "中药饮片入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "中药饮片分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam bChdrug = BeanUtil.wrapPageBean(request);
        PageResult pageResult = bChdrugService.pageList(bChdrug);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "中药饮片详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        BChdrug bChdrug = bChdrugService.query(request.getParameter("id_"));
        model.put("bChdrug",bChdrug);
        return getView(request,model);
    }

    @ActionAnnotation(name = "中药饮片添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "中药饮片添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BChdrug bChdrug = BeanUtil.wrapBean(BChdrug.class, request.getParameter("bChdrug"));
        PageParam countParam = new PageParam();
        countParam.put("name_eq", bChdrug.getName_());
        if(bChdrugService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        bChdrug.setCreate_time(now);
        bChdrug.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = bChdrugService.create(bChdrug);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "知识库中药饮片表修改",group = "修改")
    public ModelAndView beforeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String result = drugMapperService.updateCheck(pageParam);
    	return responseText(response, result);
    }
	
	@ActionAnnotation(name = "知识库中药饮片表修改",group = "停用")
    public ModelAndView beforeDisable(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String[] arr = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
    	String result = "0";
    	for(String ebmDrugId : arr) {
    		pageParam.put("drug_id", ebmDrugId);
    		result = drugMapperService.updateCheck(pageParam);
    		if(!"0".equals(result)) {
    			break;
    		}
    	}
    	return responseText(response, result);
    }
	
    @ActionAnnotation(name = "中药饮片修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        BChdrug bChdrug = bChdrugService.query(request.getParameter("id_"));
        model.put("bChdrug",bChdrug);
        return getView(request,model);
    }

    @ActionAnnotation(name = "中药饮片修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BChdrug bChdrug = BeanUtil.wrapBean(BChdrug.class, request.getParameter("bChdrug"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", bChdrug.getId_());
        countParam.put("name_eq", bChdrug.getName_());
        if(bChdrugService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        bChdrug.setLast_update_time(now);
        bChdrug.setLast_updator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = bChdrugService.update(bChdrug);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "中药饮片删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = bChdrugService.delete(request.getParameter("id_"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "中药饮片批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        String result = bChdrugService.delete(id_s);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "中药饮片表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        Date now = new Date();
       Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = bChdrugService.review(id_s,now,reviewor);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "中药饮片表批量启用",group = "启用",log = true)
    public ModelAndView enableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        String result = bChdrugService.enableOrNot(id_s, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "中药饮片表批量停用",group = "停用",log = true)
    public ModelAndView enableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        String result = bChdrugService.enableOrNot(id_s, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入知识库中药饮片",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
}
