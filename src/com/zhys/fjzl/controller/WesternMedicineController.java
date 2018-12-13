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
import com.zhys.fjzl.domain.WesternMedicine;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.service.DrugMapperService;
import com.zhys.fjzl.service.WesternMedicineService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：知识库西药表 Controller
 */
@Controller("WesternMedicineController")
@RequestMapping("/fjzl/westernMedicine_*.do")
public class WesternMedicineController extends BaseController{

    @Autowired
    @Qualifier("WesternMedicineService")
    private WesternMedicineService westernMedicineService;
    @Resource(name = "DrugMapperService")
    private DrugMapperService drugMapperService;

    @ActionAnnotation(name = "知识库西药表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "知识库西药表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam westernMedicine = BeanUtil.wrapPageBean(request);
        PageResult pageResult = westernMedicineService.pageList(westernMedicine);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "知识库西药表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        WesternMedicine westernMedicine = westernMedicineService.query(request.getParameter("id"));
        model.put("westernMedicine",westernMedicine);
        return getView(request,model);
    }

    @ActionAnnotation(name = "知识库西药表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "知识库西药表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WesternMedicine westernMedicine = BeanUtil.wrapBean(WesternMedicine.class, request.getParameter("westernMedicine"));
        PageParam countParam = new PageParam();
        countParam.put("drug_name_eq", westernMedicine.getDrug_name());
        countParam.put("dosage_form_eq", westernMedicine.getDosage_form());
        if(westernMedicineService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        westernMedicine.setCreate_time(now);
        westernMedicine.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = westernMedicineService.create(westernMedicine);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "知识库西药表修改",group = "修改")
    public ModelAndView beforeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String result = drugMapperService.updateCheck(pageParam);
    	return responseText(response, result);
    }
    
    @ActionAnnotation(name = "知识库西药表修改",group = "停用")
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

    @ActionAnnotation(name = "知识库西药表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        WesternMedicine westernMedicine = westernMedicineService.query(request.getParameter("id"));
        model.put("westernMedicine",westernMedicine);
        return getView(request,model);
    }

    @ActionAnnotation(name = "知识库西药表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        WesternMedicine westernMedicine = BeanUtil.wrapBean(WesternMedicine.class, request.getParameter("westernMedicine"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", westernMedicine.getId());
        countParam.put("drug_name_eq", westernMedicine.getDrug_name());
        countParam.put("dosage_form_eq", westernMedicine.getDosage_form());
        if(westernMedicineService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        westernMedicine.setLast_update_time(now);
        westernMedicine.setLast_updator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = westernMedicineService.update(westernMedicine);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "知识库西药表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = westernMedicineService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "知识库西药表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = westernMedicineService.delete(ids);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "知识库西药表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = westernMedicineService.review(ids,now,reviewor);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "知识库西药表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = westernMedicineService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "知识库西药表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = westernMedicineService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入知识库西药",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
    
}
