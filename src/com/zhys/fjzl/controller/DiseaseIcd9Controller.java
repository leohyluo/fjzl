package com.zhys.fjzl.controller;

import java.util.Date;
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
import com.zhys.fjzl.domain.DiseaseIcd9Relation;
import com.zhys.fjzl.service.DiseaseIcd9RelationService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库疾病与icd9关系表 Controller
 */
@Controller("DiseaseIcd9Controller")
@RequestMapping("/fjzl/diseaseIcd9_*.do")
public class DiseaseIcd9Controller extends BaseController{

    @Autowired
    @Qualifier("DiseaseIcd9Service")
    private DiseaseIcd9RelationService diseaseIcd9Service;

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam diseaseIcd9 = BeanUtil.wrapPageBean(request);
        PageResult pageResult = diseaseIcd9Service.pageList(diseaseIcd9);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        DiseaseIcd9Relation diseaseIcd9 = diseaseIcd9Service.query(request.getParameter("id"));
        model.put("diseaseIcd9",diseaseIcd9);
        return getView(request,model);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiseaseIcd9Relation diseaseIcd9 = BeanUtil.wrapBean(DiseaseIcd9Relation.class, request.getParameter("diseaseIcd9"));
        Date now = new Date();
        diseaseIcd9.setCreate_time(now);
        diseaseIcd9.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = diseaseIcd9Service.create(diseaseIcd9);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        DiseaseIcd9Relation diseaseIcd9 = diseaseIcd9Service.query(request.getParameter("id"));
        Date now = new Date();
        diseaseIcd9.setLast_update_time(now);
        diseaseIcd9.setLast_updator(Long.parseLong(getLoginUser(request).getSu_id()));
        model.put("diseaseIcd9",diseaseIcd9);
        return getView(request,model);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiseaseIcd9Relation diseaseIcd9 = BeanUtil.wrapBean(DiseaseIcd9Relation.class, request.getParameter("diseaseIcd9"));
        String result = diseaseIcd9Service.update(diseaseIcd9);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = diseaseIcd9Service.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库疾病与icd9关系表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = diseaseIcd9Service.delete(ids);
        return responseText(response, result);
    }
}
