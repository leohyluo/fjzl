package com.zhys.fjzl.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSON;
import com.zhys.core.annotation.ActionAnnotation;
import com.zhys.core.controller.BaseController;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.util.BeanUtil;
import com.zhys.core.util.EasyuiUtil;
import com.zhys.fjzl.domain.EbmIcd9;
import com.zhys.fjzl.service.EbmIcd9Service;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库icd9表 Controller
 */
@Controller("Icd9Controller")
@RequestMapping("/fjzl/icd9_*.do")
public class Icd9Controller extends BaseController{

    @Autowired
    @Qualifier("Icd9Service")
    private EbmIcd9Service icd9Service;

    @ActionAnnotation(name = "基础知识库icd9表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }
    
    @ActionAnnotation(name = "基础知识库icd9表入口",group = "查询")
    public ModelAndView mainlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EbmIcd9 ebmIcd9 = BeanUtil.wrapBean(EbmIcd9.class, request);
        List<EbmIcd9> listIcd9s = icd9Service.listQuery(ebmIcd9);
        ModelAndView mav = getView(request);
        mav.addObject("listIcd9s", listIcd9s);
         return responseText(response,  JSON.toJSONString(mav));
    }
    @ActionAnnotation(name = "基础知识库icd9表入口",group = "查询")
    public ModelAndView mainQuery(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }
    @ActionAnnotation(name = "基础知识库icd9表分页",group = "查询")
    public ModelAndView mainQuerypage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam icd9 = BeanUtil.wrapPageBean(request);
        PageResult pageResult = icd9Service.pageList(icd9);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "基础知识库icd9表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam icd9 = BeanUtil.wrapPageBean(request);
        PageResult pageResult = icd9Service.pageList(icd9);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "基础知识库icd9表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        EbmIcd9 icd9 = icd9Service.query(request.getParameter("id"));
        model.put("icd9",icd9);
        return getView(request,model);
    }

    @ActionAnnotation(name = "基础知识库icd9表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "基础知识库icd9表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EbmIcd9 icd9 = BeanUtil.wrapBean(EbmIcd9.class, request.getParameter("icd9"));
        Date now = new Date();
        icd9.setCreate_time(now);
        icd9.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = icd9Service.create(icd9);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库icd9表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        EbmIcd9 icd9 = icd9Service.query(request.getParameter("id"));
        model.put("icd9",icd9);
        return getView(request,model);
    }

    @ActionAnnotation(name = "基础知识库icd9表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EbmIcd9 icd9 = BeanUtil.wrapBean(EbmIcd9.class, request.getParameter("icd9"));
        Date now = new Date();
        icd9.setLast_update_time(now);
        icd9.setLast_updator(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = icd9Service.update(icd9);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库icd9表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = icd9Service.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库icd9表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = icd9Service.delete(ids);
        return responseText(response, result);
    }
}
