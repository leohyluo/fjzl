package com.zhys.fjzl.controller;

import java.io.File;
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
import com.zhys.fjzl.domain.Table1;
import com.zhys.fjzl.enums.FileType;
import com.zhys.fjzl.handler.PersistentHandler;
import com.zhys.fjzl.service.Table1Service;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-10-10 <br/>
 * 描述：新建表 Controller
 */
@Controller("Table1Controller")
@RequestMapping("/fjzl/table1_*.do")
public class Table1Controller extends BaseController{

    @Autowired
    @Qualifier("Table1Service")
    private Table1Service table1Service;
    @Autowired
    private PersistentHandler persistentHandler;

    @ActionAnnotation(name = "新建表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	test();
        return getView(request);
    }

    @ActionAnnotation(name = "新建表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam table1 = BeanUtil.wrapPageBean(request);
        PageResult pageResult = table1Service.pageList(table1);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "新建表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        Table1 table1 = table1Service.query(request.getParameter("id"));
        model.put("table1",table1);
        return getView(request,model);
    }

    @ActionAnnotation(name = "新建表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "新建表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Table1 table1 = BeanUtil.wrapBean(Table1.class, request.getParameter("table1"));
        String result = table1Service.create(table1);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "新建表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        Table1 table1 = table1Service.query(request.getParameter("id"));
        model.put("table1",table1);
        return getView(request,model);
    }

    @ActionAnnotation(name = "新建表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Table1 table1 = BeanUtil.wrapBean(Table1.class, request.getParameter("table1"));
        String result = table1Service.update(table1);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "新建表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = table1Service.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "新建表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = table1Service.delete(ids);
        return responseText(response, result);
    }
    
    public void test() { 
    	System.out.println("===========================================>test");
    	try {
    		//persistentHandler.load(FileType.EBM_DISEASE, new File("F:/Test/ebm_disease_error.xls"));
			//persistentHandler.persistent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
