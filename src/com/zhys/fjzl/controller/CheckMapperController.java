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
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.domain.CheckMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.BodyCheckService;
import com.zhys.fjzl.service.CheckMapperService;
import com.zhys.sys.domain.User;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：检查对照表 Controller
 */
@Controller("CheckMapperController")
@RequestMapping("/fjzl/checkMapper_*.do")
public class CheckMapperController extends BaseController{

    @Autowired
    @Qualifier("CheckMapperService")
    private CheckMapperService checkMapperService;
    @Resource(name = "BodyCheckService")
    private BodyCheckService bodyCheckService;

    @ActionAnnotation(name = "检查对照表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }
    
    @ActionAnnotation(name = "未对照检查表入口",group = "查询")
    public ModelAndView unreviewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getView(request);
    }
    
    @ActionAnnotation(name = "已对照检查表入口",group = "查询")
    public ModelAndView reviewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getView(request);
    }

    @ActionAnnotation(name = "检查对照表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam checkMapper = BeanUtil.wrapPageBean(request);
        User user = getLoginUser(request);
        if(!GlobalConstant.SUPER_ADMIN.equals(user.getSu_admin())) {
        	checkMapper.put("org_id", user.getSo_id());
        }
        PageResult pageResult = checkMapperService.pageList(checkMapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "检查对照表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        CheckMapper checkMapper = checkMapperService.query(request.getParameter("id"));
        model.put("checkMapper",checkMapper);
        return getView(request,model);
    }

    @ActionAnnotation(name = "检查对照表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "检查对照表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckMapper checkMapper = BeanUtil.wrapBean(CheckMapper.class, request.getParameter("checkMapper"));
        PageParam countParam = new PageParam();
        countParam.put("org_id",getLoginUser(request).getSo_id());
        countParam.put("org_check_name_eq", checkMapper.getOrg_check_name());
        countParam.put("org_check_id", checkMapper.getOrg_check_id());
        if(checkMapperService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        Date now = new Date();
        checkMapper.setCreate_time(now);
        checkMapper.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        checkMapper.setOrg_id(getLoginUser(request).getSo_id());
        String result = checkMapperService.create(checkMapper);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "检查对照表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        CheckMapper checkMapper = checkMapperService.query(request.getParameter("id"));
        model.put("checkMapper",checkMapper);
        return getView(request,model);
    }

    @ActionAnnotation(name = "检查对照表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CheckMapper checkMapper = BeanUtil.wrapBean(CheckMapper.class, request.getParameter("checkMapper"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", checkMapper.getId());
        countParam.put("org_id",getLoginUser(request).getSo_id());
        countParam.put("org_check_name_eq", checkMapper.getOrg_check_name());
        countParam.put("org_check_id", checkMapper.getOrg_check_id());
        if(checkMapperService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        Date now = new Date();
        checkMapper.setLast_update_time(now);
        checkMapper.setLast_updateor(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = checkMapperService.update(checkMapper);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "对照医院检查",group = "对照")
    public ModelAndView review(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String checkId = request.getParameter("id");
    	ModelAndView mav = getView(request);
    	mav.addObject("id", checkId);
    	return mav;
    }
    
    @ActionAnnotation(name = "对照医院检查",group = "对照")
    public ModelAndView reviewPage1(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam checkMapper = BeanUtil.wrapPageBean(request);
        PageResult pageResult = checkMapperService.pageList(checkMapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    
    @ActionAnnotation(name = "对照医院检查",group = "对照")
    public ModelAndView reviewPage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam param = BeanUtil.wrapPageBean(request);
    	param.put("body_check_status", EbmStatus.REVIEWED.getStatus());
        PageResult pageResult = bodyCheckService.pageList(param);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    
    @ActionAnnotation(name = "保存手工对照",group = "对照")
    public ModelAndView saveReview(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String orgCheckId = request.getParameter("orgCheckId");
    	String ebmCheckId = request.getParameter("ebmCheckId");
    	Long reviewResult = Long.valueOf(request.getParameter("reviewResult"));
    	CheckMapper checkMapper = checkMapperService.query(orgCheckId);
    	String result = "对照失败";
    	if(checkMapper != null) {
    		HisDataStatus status = HisDataStatus.findByStatus(reviewResult);
    		
    		checkMapper.setBody_check_status(status.getStatus());
    		checkMapper.setMapper_user(getLoginUser(request).getSo_id());
    		checkMapper.setMapper_time(new Date());
    		if(status == HisDataStatus.MAPED) {
	    		checkMapper.setCheck_id(ebmCheckId);
    		}
    		result = checkMapperService.update(checkMapper);
    	}
    	return responseText(response, result);
    }

    @ActionAnnotation(name = "检查对照表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = checkMapperService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "检查对照表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = checkMapperService.delete(ids);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "检查对照表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = checkMapperService.review(ids,now,reviewor);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "检查对照表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = checkMapperService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "检查对照表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = checkMapperService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入知识库疾病",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
}
