package com.zhys.fjzl.controller;

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
import com.zhys.fjzl.domain.ApiRecord;
import com.zhys.fjzl.service.ApiRecordService;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述： Controller
 */
@Controller("ApiRecordController")
@RequestMapping("/fjzl/apiRecord_*.do")
public class ApiRecordController extends BaseController{

    @Autowired
    @Qualifier("ApiRecordService")
    private ApiRecordService apiRecordService;

    @ActionAnnotation(name = "入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam apiRecord = BeanUtil.wrapPageBean(request);
        PageResult pageResult = apiRecordService.pageList(apiRecord);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        ApiRecord apiRecord = apiRecordService.query(request.getParameter("id"));
        model.put("apiRecord",apiRecord);
        return getView(request,model);
    }

    @ActionAnnotation(name = "添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApiRecord apiRecord = BeanUtil.wrapBean(ApiRecord.class, request.getParameter("apiRecord"));
        String result = apiRecordService.create(apiRecord);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        ApiRecord apiRecord = apiRecordService.query(request.getParameter("id"));
        model.put("apiRecord",apiRecord);
        return getView(request,model);
    }

    @ActionAnnotation(name = "修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ApiRecord apiRecord = BeanUtil.wrapBean(ApiRecord.class, request.getParameter("apiRecord"));
        String result = apiRecordService.update(apiRecord);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = apiRecordService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = apiRecordService.delete(ids);
        return responseText(response, result);
    }
}
