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
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.domain.EbmDisease;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.DiseaseMapperService;
import com.zhys.fjzl.service.EbmDiseaseService;
import com.zhys.sys.domain.User;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-21 <br/>
 * 描述：疾病对照表 Controller
 */
@Controller("DiseaseMapperController")
@RequestMapping("/fjzl/diseaseMapper_*.do")
public class DiseaseMapperController extends BaseController{

    @Autowired
    @Qualifier("DiseaseMapperService")
    private DiseaseMapperService mapperService;
    @Resource(name = "DiseaseService")
    private EbmDiseaseService ebmDiseaseService; 

    @ActionAnnotation(name = "疾病对照表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }
    
    @ActionAnnotation(name = "未对照疾病表入口",group = "查询")
    public ModelAndView unreviewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getView(request);
    }
    
    @ActionAnnotation(name = "已对照疾病表入口",group = "查询")
    public ModelAndView reviewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	return getView(request);
    }

    @ActionAnnotation(name = "疾病对照表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam mapper = BeanUtil.wrapPageBean(request);
        User user = getLoginUser(request);
        if(!GlobalConstant.SUPER_ADMIN.equals(user.getSu_admin())) {
        	mapper.put("org_id", user.getSo_id());
        }
        PageResult pageResult = mapperService.pageList(mapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "疾病对照表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        DiseaseMapper mapper = mapperService.query(request.getParameter("id"));
        model.put("mapper",mapper);
        return getView(request,model);
    }

    @ActionAnnotation(name = "疾病对照表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "疾病对照表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiseaseMapper mapper = BeanUtil.wrapBean(DiseaseMapper.class, request.getParameter("mapper"));
        PageParam countParam = new PageParam();
        countParam.put("org_id",getLoginUser(request).getSo_id());
        countParam.put("org_disease_name_eq", mapper.getOrg_disease_name());
        countParam.put("org_icd_10", mapper.getOrg_icd_10());
        if(mapperService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        Date now = new Date();
        mapper.setCreate_time(now);
        mapper.setCreator(Long.parseLong(getLoginUser(request).getSu_id()));
        mapper.setOrg_id(getLoginUser(request).getSo_id());
        String result = mapperService.create(mapper);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "疾病对照表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        DiseaseMapper mapper = mapperService.query(request.getParameter("id"));
        model.put("mapper",mapper);
        return getView(request,model);
    }

    @ActionAnnotation(name = "疾病对照表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DiseaseMapper mapper = BeanUtil.wrapBean(DiseaseMapper.class, request.getParameter("mapper"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", mapper.getId());
        countParam.put("org_id",getLoginUser(request).getSo_id());
        countParam.put("org_disease_name_eq", mapper.getOrg_disease_name());
        countParam.put("org_icd_10", mapper.getOrg_icd_10());
        if(mapperService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        Date now = new Date();
        mapper.setLast_update_time(now);
        mapper.setLast_updateor(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = mapperService.update(mapper);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "疾病对照表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = mapperService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "疾病对照表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = mapperService.delete(ids);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "疾病对照表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = mapperService.review(ids,now,reviewor);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "疾病对照表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = mapperService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "疾病对照表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = mapperService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入His检查",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
    
    @ActionAnnotation(name = "对照医院检查",group = "对照")
    public ModelAndView review(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String checkId = request.getParameter("id");
    	ModelAndView mav = getView(request);
    	mav.addObject("id", checkId);
    	return mav;
    }
    
    @ActionAnnotation(name = "对照医院疾病",group = "对照")
    public ModelAndView reviewPage1(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam checkMapper = BeanUtil.wrapPageBean(request);
        PageResult pageResult = mapperService.pageList(checkMapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    
    @ActionAnnotation(name = "对照医院疾病",group = "对照")
    public ModelAndView reviewPage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam param = BeanUtil.wrapPageBean(request);
    	param.put("disease_status", EbmStatus.REVIEWED.getStatus());
        PageResult pageResult = ebmDiseaseService.pageList(param);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    
    @ActionAnnotation(name = "保存手工对照",group = "对照")
    public ModelAndView saveReview(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String orgDiseaseId = request.getParameter("orgDiseaseId");
    	String ebmDiseaseId = request.getParameter("ebmDiseaseId");
    	Long reviewResult = Long.valueOf(request.getParameter("reviewResult"));
    	DiseaseMapper diseaseMapper = mapperService.query(orgDiseaseId);
    	String result = "对照失败";
    	if(diseaseMapper != null) {
    		HisDataStatus status = HisDataStatus.findByStatus(reviewResult);
    		
    		diseaseMapper.setDisease_status(status.getStatus());
    		diseaseMapper.setMapper_user(getLoginUser(request).getSo_id());
    		diseaseMapper.setMapper_time(new Date());
    		if(status == HisDataStatus.MAPED) {
    			diseaseMapper.setDisease_id(ebmDiseaseId);
    		}
    		result = mapperService.update(diseaseMapper);
    	}
    	return responseText(response, result);
    }
}
