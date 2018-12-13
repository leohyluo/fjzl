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
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.fjzl.service.BChdrugService;
import com.zhys.fjzl.service.BCpdrugService;
import com.zhys.fjzl.service.DrugMapperService;
import com.zhys.fjzl.service.WesternMedicineService;
import com.zhys.sys.domain.User;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：药品对照表 Controller
 */
@Controller("DrugMapperController")
@RequestMapping("/fjzl/mapper_*.do")
public class DrugMapperController extends BaseController{

    @Autowired
    @Qualifier("DrugMapperService")
    private DrugMapperService mapperService;
    @Autowired
    @Qualifier("BChdrugService")
    private BChdrugService bChdrugService;
    @Autowired
    @Qualifier("BCpdrugService")
    private BCpdrugService bCpdrugService;
    @Autowired
    @Qualifier("WesternMedicineService")
    private WesternMedicineService westernMedicineService;
    

    @ActionAnnotation(name = "西药药品对照表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }
    @ActionAnnotation(name = "未对照西药药品入口",group = "查询")
    public ModelAndView unreviewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String type = request.getParameter("type");
    	ModelAndView mav = getView(request);
    	mav.addObject("type", type);
    	return mav;
    }
    
    @ActionAnnotation(name = "已对照西药药品入口",group = "查询")
    public ModelAndView reviewMain(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String type = request.getParameter("type");
    	ModelAndView mav = getView(request);
    	mav.addObject("type", type);
    	return mav;
    }
    
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView review(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String checkId = request.getParameter("id");
    	ModelAndView mav = getView(request);
    	mav.addObject("id", checkId);
    	return mav;
    }
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView zcyReview(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String checkId = request.getParameter("id");
    	ModelAndView mav = getView(request);
    	mav.addObject("id", checkId);
    	return mav;
    }
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView zyypReview(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String checkId = request.getParameter("id");
    	ModelAndView mav = getView(request);
    	mav.addObject("id", checkId);
    	return mav;
    }

    @ActionAnnotation(name = "西药药品对照表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String type = request.getParameter("type");
        PageParam mapper = BeanUtil.wrapPageBean(request);
        User user = getLoginUser(request);
        if(!GlobalConstant.SUPER_ADMIN.equals(user.getSu_admin())) {
        	mapper.put("org_id", user.getSo_id());
        }
        mapper.put("type", type);
        PageResult pageResult = mapperService.pageList(mapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView reviewPage1(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam checkMapper = BeanUtil.wrapPageBean(request);
        PageResult pageResult = mapperService.pageList(checkMapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView reviewPage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam param = BeanUtil.wrapPageBean(request);
    	param.put("drug_status", EbmStatus.REVIEWED.getStatus());
        PageResult pageResult = westernMedicineService.pageList(param);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView zcyReviewPage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam param = BeanUtil.wrapPageBean(request);
    	param.put("drug_status", EbmStatus.REVIEWED.getStatus());
        PageResult pageResult = bCpdrugService.pageList(param);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "对照西药药品",group = "对照")
    public ModelAndView zyypReviewPage2(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam param = BeanUtil.wrapPageBean(request);
    	param.put("drug_status", EbmStatus.REVIEWED.getStatus());
        PageResult pageResult = bChdrugService.pageList(param);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "保存手工对照",group = "对照")
    public ModelAndView saveReview(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String orgDrugCode = request.getParameter("orgDrugCode");
    	String ebmDrugId = request.getParameter("ebmDrugId");
    	Long reviewResult = Long.valueOf(request.getParameter("reviewResult"));
    	DrugMapper drugMapper = mapperService.query(orgDrugCode);
    	String result = "对照失败";
    	if(drugMapper != null) {
    		HisDataStatus status = HisDataStatus.findByStatus(reviewResult);
    		
    		drugMapper.setDrug_status(status.getStatus());
    		drugMapper.setMapper_user(getLoginUser(request).getSo_id());
    		drugMapper.setMapper_time(new Date());
    		if(status == HisDataStatus.MAPED) {
    			drugMapper.setDrug_id(ebmDrugId);
    		}
    		result = mapperService.update(drugMapper);
    	}
    	return responseText(response, result);
    }
    
    
    
    
    
    @ActionAnnotation(name = "中成药药品对照表入口",group = "查询")
    public ModelAndView zcymain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "中成药药品对照表分页",group = "查询")
    public ModelAndView zcypage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam mapper = BeanUtil.wrapPageBean(request);
        User user = getLoginUser(request);
        if(!GlobalConstant.SUPER_ADMIN.equals(user.getSu_admin())) {
        	mapper.put("org_id", user.getSo_id());
        }
        PageResult pageResult = mapperService.pageList(mapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "中药饮片药品对照表入口",group = "查询")
    public ModelAndView zyypmain(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "中药饮片药品对照表分页",group = "查询")
    public ModelAndView zyyppage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam mapper = BeanUtil.wrapPageBean(request);
        User user = getLoginUser(request);
        if(!GlobalConstant.SUPER_ADMIN.equals(user.getSu_admin())) {
        	mapper.put("org_id", user.getSo_id());
        }
        PageResult pageResult = mapperService.pageList(mapper);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }
    @ActionAnnotation(name = "药品对照表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        DrugMapper mapper = mapperService.query(request.getParameter("id"));
        model.put("mapper",mapper);
        return getView(request,model);
    }

    @ActionAnnotation(name = "药品对照表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	   Map<String,Object> model = new HashMap<String,Object>();
    	String drugtype=request.getParameter("type");
        model.put("drugtype",drugtype);
        return getView(request,model);
    }

    @ActionAnnotation(name = "药品对照表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DrugMapper mapper = BeanUtil.wrapBean(DrugMapper.class, request.getParameter("mapper"));
        PageParam countParam = new PageParam();
        long drugType=mapper.getType();
	   	 countParam.put("org_id",getLoginUser(request).getSo_id());
	     countParam.put("org_drug_name_eq", mapper.getOrg_drug_name());
	     countParam.put("org_drug_code", mapper.getOrg_drug_code());
        if(drugType==1)
        {
             countParam.put("specifications", mapper.getSpecifications());
             countParam.put("dosage_form", mapper.getDosage_form());
        }
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

    @ActionAnnotation(name = "药品对照表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        DrugMapper mapper = mapperService.query(request.getParameter("id"));
        model.put("mapper",mapper);
        return getView(request,model);
    }

    @ActionAnnotation(name = "药品对照表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        DrugMapper mapper = BeanUtil.wrapBean(DrugMapper.class, request.getParameter("mapper"));
        PageParam countParam = new PageParam();
        long drugType=mapper.getType();
        countParam.put("id_other", mapper.getId());
        countParam.put("org_id",getLoginUser(request).getSo_id());
        countParam.put("org_drug_name_eq", mapper.getOrg_drug_name());
        countParam.put("org_drug_code", mapper.getOrg_drug_code());
        if(drugType==1)
        {
             countParam.put("specifications", mapper.getSpecifications());
             countParam.put("dosage_form", mapper.getDosage_form());
        }
        if(mapperService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        Date now = new Date();
        mapper.setLast_update_time(now);
        mapper.setLast_updateor(Long.parseLong(getLoginUser(request).getSu_id()));
        String result = mapperService.update(mapper);
        return responseText(response, result);
    }

    @ActionAnnotation(name = "药品对照表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = mapperService.delete(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "药品对照表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = mapperService.delete(ids);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "药品对照表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = mapperService.review(ids,now,reviewor);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "药品对照表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = mapperService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "药品对照表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = mapperService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入医院西药",group = "导入",log = true)
    public ModelAndView xyUpload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
    
    @ActionAnnotation(name = "导入医院中成药",group = "导入",log = true)
    public ModelAndView zcyUpload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
    
    @ActionAnnotation(name = "导入医院中药饮片",group = "导入",log = true)
    public ModelAndView zyypUpload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
}
