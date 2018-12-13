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
import com.zhys.fjzl.domain.BCpdrug;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.service.BCpdrugService;
import com.zhys.fjzl.service.DrugMapperService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：中成药表 Controller
 */
@Controller("BCpdrugController")
@RequestMapping("/fjzl/bCpdrug_*.do")
public class BCpdrugController extends BaseController{
	
	@Resource(name = "DrugMapperService")
	private DrugMapperService drugMapperService; 

    @Autowired
    @Qualifier("BCpdrugService")
    private BCpdrugService bCpdrugService;

    @ActionAnnotation(name = "中成药表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "中成药表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam bCpdrug = BeanUtil.wrapPageBean(request);
        PageResult pageResult = bCpdrugService.pageList(bCpdrug);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "中成药表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        BCpdrug bCpdrug = bCpdrugService.query(request.getParameter("id_"));
        model.put("bCpdrug",bCpdrug);
        return getView(request,model);
    }

    @ActionAnnotation(name = "中成药表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "中成药表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BCpdrug bCpdrug = BeanUtil.wrapBean(BCpdrug.class, request.getParameter("bCpdrug"));
        PageParam countParam = new PageParam();
        countParam.put("cpdrugname_eq", bCpdrug.getCpdrugname());
        countParam.put("standard_eq", bCpdrug.getStandard());
        if(bCpdrugService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        bCpdrug.setCreate_time(now);
        bCpdrug.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = bCpdrugService.create(bCpdrug);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "知识库中成药表修改",group = "修改")
    public ModelAndView beforeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String result = drugMapperService.updateCheck(pageParam);
    	return responseText(response, result);
    }
    
    @ActionAnnotation(name = "中成药表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        BCpdrug bCpdrug = bCpdrugService.query(request.getParameter("id_"));
        model.put("bCpdrug",bCpdrug);
        return getView(request,model);
    }

    @ActionAnnotation(name = "中成药表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BCpdrug bCpdrug = BeanUtil.wrapBean(BCpdrug.class, request.getParameter("bCpdrug"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", bCpdrug.getId_());
        countParam.put("cpdrugname_eq", bCpdrug.getCpdrugname());
        countParam.put("standard_eq", bCpdrug.getStandard());
        if(bCpdrugService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        bCpdrug.setLast_update_time(now);
        bCpdrug.setLast_updateor(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = bCpdrugService.update(bCpdrug);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "中成药表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = bCpdrugService.delete(request.getParameter("id_"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "中成药表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        String result = bCpdrugService.delete(id_s);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "中成药表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = bCpdrugService.review(id_s,now,reviewor);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "中成药表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        String result = bCpdrugService.enableOrNot(id_s, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "知识库中成药表修改",group = "停用")
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
    
    @ActionAnnotation(name = "中成药表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] id_s = BeanUtil.wrapArray(String.class, request.getParameter("id_s"));
        String result = bCpdrugService.enableOrNot(id_s, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入知识库中成药",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
  
}
