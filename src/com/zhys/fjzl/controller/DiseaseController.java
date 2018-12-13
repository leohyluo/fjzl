package com.zhys.fjzl.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.zhys.fjzl.domain.DiseaseIcd9Relation;
import com.zhys.fjzl.domain.EbmDisease;
import com.zhys.fjzl.domain.EbmIcd9;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.service.DiseaseIcd9RelationService;
import com.zhys.fjzl.service.DiseaseMapperService;
import com.zhys.fjzl.service.EbmDiseaseService;
import com.zhys.fjzl.service.EbmIcd9Service;

/**
 * 版权：智慧药师 <br/>
 * 作者：dail <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库疾病表 Controller
 */
@Controller("DiseaseController")
@RequestMapping("/fjzl/disease_*.do")
public class DiseaseController extends BaseController{

    @Autowired
    @Qualifier("DiseaseService")
    private EbmDiseaseService diseaseService;
    
    @Autowired
    private EbmIcd9Service ebmIcd9Service;
    @Autowired
    private DiseaseMapperService diseaseMapperService;
    @Autowired
    @Qualifier("DiseaseIcd9Service")
    private DiseaseIcd9RelationService diseaseIcd9Service;

    @ActionAnnotation(name = "基础知识库疾病表入口",group = "查询")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return getView(request);
    }

    @ActionAnnotation(name = "基础知识库疾病表分页",group = "查询")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageParam ebmDisease = BeanUtil.wrapPageBean(request);
        PageResult pageResult = diseaseService.pageList(ebmDisease);
        return responseText(response, EasyuiUtil.toDataGridData(pageResult));
    }

    @ActionAnnotation(name = "基础知识库疾病表详细",group = "查询")
    public ModelAndView view(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        EbmDisease ebmDisease = diseaseService.query(request.getParameter("id"));
        model.put("disease",ebmDisease);
        return getView(request,model);
    }

    @ActionAnnotation(name = "基础知识库疾病表添加",group = "添加")
    public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {

        EbmIcd9 ebmIcd9 = BeanUtil.wrapBean(EbmIcd9.class, request);
        List<EbmIcd9> listIcd9s = ebmIcd9Service.listQuery(ebmIcd9);
        ModelAndView mav = getView(request);
        mav.addObject("listIcd9s", listIcd9s);
        return mav;
    }

    @ActionAnnotation(name = "基础知识库疾病表添加保存",group = "添加",log = true)
    public ModelAndView addSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EbmDisease ebmDisease = BeanUtil.wrapBean(EbmDisease.class, request.getParameter("disease"));
        PageParam countParam = new PageParam();
        countParam.put("disease_name_eq", ebmDisease.getDisease_name());
        countParam.put("icd_10", ebmDisease.getIcd_10());
        if(diseaseService.getCount(countParam).longValue() > 0) {
        	return responseText(response, String.valueOf(String.valueOf("-1")));
        }
        
        Date now = new Date();
        Long creator=Long.parseLong(getLoginUser(request).getSu_id());
        ebmDisease.setCreate_time(now);
        ebmDisease.setCreator(creator);
        String result = diseaseService.create(ebmDisease);
        String Icd9Namestr[]=ebmDisease.getIcd9Name().split(",");
        String Icd9str[]=ebmDisease.getIcd9().split(",");
        	for (int i = 0; i < Icd9Namestr.length; i++) {
        		EbmIcd9 ebmIcd9=new EbmIcd9();
        		ebmIcd9.setIcd9_name(Icd9Namestr[i]);
        		List<EbmIcd9> ebmIcd9s=ebmIcd9Service.FindEbmIcd9(ebmIcd9);
                if (ebmIcd9s.isEmpty()) {
                	  DiseaseIcd9Relation diseaseIcd9=new DiseaseIcd9Relation();
                	 ebmIcd9.setIcd9(Icd9str[i]);
                	 String ebmIcd9Result = ebmIcd9Service.create(ebmIcd9);
                	   diseaseIcd9.setIcd9_id(ebmIcd9Result);
                       diseaseIcd9.setDisease_id(result);
                       diseaseIcd9.setCreate_time(now);
                       diseaseIcd9.setCreator(creator);
                       String results = diseaseIcd9Service.create(diseaseIcd9);
        		}
                else
                {
                	 DiseaseIcd9Relation diseaseIcd9=new DiseaseIcd9Relation();
                	 EbmIcd9 ebmIcd92=ebmIcd9Service.query(ebmIcd9s.get(0).getId().toString());
                	 diseaseIcd9.setIcd9_id(ebmIcd92.getId().toString());
                     diseaseIcd9.setDisease_id(result);
                     diseaseIcd9.setCreate_time(now);
                     diseaseIcd9.setCreator(creator);
                     String results = diseaseIcd9Service.create(diseaseIcd9);
                }
			}

     /*   DiseaseIcd9Relation diseaseIcd9=new DiseaseIcd9Relation();
        String ids[]=ebmDisease.getIds().split(",");
        for (int i = 0; i < ids.length; i++) {
        	   diseaseIcd9.setIcd9_id(ids[i]);
               diseaseIcd9.setDisease_id(result);
               diseaseIcd9.setCreate_time(now);
               diseaseIcd9.setCreator(creator);
               String results = diseaseIcd9Service.create(diseaseIcd9);
		}*/
        return responseText(response, "1");
    }
    @ActionAnnotation(name = "基础知识库疾病表修改",group = "停用")
    public ModelAndView beforeDisable(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String[] arr = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
    	String result = "0";
    	for(String diseaseId : arr) {
    		pageParam.put("disease_id", diseaseId);
    		result = diseaseMapperService.updateCheck(pageParam);
    		if(!"0".equals(result)) {
    			break;
    		}
    	}
    	return responseText(response, result);
    }
    @ActionAnnotation(name = "判断基础知识库疾病表机构疾病是否有关联",group = "修改")
    public ModelAndView beforeEdit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PageParam pageParam = BeanUtil.wrapPageBean(request);
    	String result = diseaseMapperService.updateCheck(pageParam);
    	return responseText(response, result);
    }
    @ActionAnnotation(name = "基础知识库疾病表修改",group = "修改")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String,Object> model = new HashMap<String,Object>();
        EbmDisease ebmDisease = diseaseService.query(request.getParameter("id"));
        model.put("disease",ebmDisease);
        return getView(request,model);
    }

    @ActionAnnotation(name = "基础知识库疾病表修改保存",group = "修改",log = true)
    public ModelAndView editSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        EbmDisease ebmDisease = BeanUtil.wrapBean(EbmDisease.class, request.getParameter("disease"));
        PageParam countParam = new PageParam();
        countParam.put("id_other", ebmDisease.getId());
        countParam.put("disease_name_eq", ebmDisease.getDisease_name());
        countParam.put("icd_10", ebmDisease.getIcd_10());
        if(diseaseService.getCount(countParam).longValue() > 0) {
        	return responseText(response, "-1");
        }
        
        Date now = new Date();
        Long updator=Long.parseLong(getLoginUser(request).getSu_id());
        ebmDisease.setLast_update_time(now);
        ebmDisease.setLast_updator(updator);
        String disid=ebmDisease.getId().toString();
        String result = diseaseService.update(ebmDisease);
        String Icd9Namestr[]=ebmDisease.getIcd9Name().split(",");
        String Icd9str[]=ebmDisease.getIcd9().split(",");
        diseaseIcd9Service.deleteByDiseaseId(disid);
        	for (int i = 0; i < Icd9Namestr.length; i++) {
        		EbmIcd9 ebmIcd9=new EbmIcd9();
        		ebmIcd9.setIcd9_name(Icd9Namestr[i]);
        		List<EbmIcd9> ebmIcd9s=ebmIcd9Service.FindEbmIcd9(ebmIcd9);
                if (ebmIcd9s.isEmpty()) {
                	  DiseaseIcd9Relation diseaseIcd9=new DiseaseIcd9Relation();
                	 ebmIcd9.setIcd9(Icd9str[i]);
                	 String ebmIcd9Result = ebmIcd9Service.create(ebmIcd9);
                	   diseaseIcd9.setIcd9_id(ebmIcd9Result);
                       diseaseIcd9.setDisease_id(disid);
                       diseaseIcd9.setLast_update_time(now);
                       diseaseIcd9.setLast_updator(updator);
                       String results = diseaseIcd9Service.create(diseaseIcd9);
        		}
                else
                {
                	 DiseaseIcd9Relation diseaseIcd9=new DiseaseIcd9Relation();
                	 EbmIcd9 ebmIcd92=ebmIcd9Service.query(ebmIcd9s.get(0).getId().toString());
                	 diseaseIcd9.setIcd9_id(ebmIcd92.getId().toString());
                     diseaseIcd9.setDisease_id(disid);
                     diseaseIcd9.setLast_update_time(now);
                     diseaseIcd9.setLast_updator(updator);
                     String results = diseaseIcd9Service.create(diseaseIcd9);
                }
			}

/*        DiseaseIcd9Relation diseaseIcd9=new DiseaseIcd9Relation();
        String disid=ebmDisease.getId().toString();
        String ids[]=ebmDisease.getIds().split(",");
        diseaseIcd9Service.deleteByDiseaseId(disid);
        for (int i = 0; i < ids.length; i++) {
        	   diseaseIcd9.setIcd9_id(ids[i]);
               diseaseIcd9.setDisease_id(disid);
               diseaseIcd9.setLast_update_time(now);
               diseaseIcd9.setLast_updator(updator);
               String results = diseaseIcd9Service.create(diseaseIcd9);
		}*/
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库疾病表删除",group = "删除",log = true)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = diseaseService.delete(request.getParameter("id"));
        String results=diseaseIcd9Service.deleteByDiseaseId(request.getParameter("id"));
        return responseText(response, result);
    }

    @ActionAnnotation(name = "基础知识库疾病表批量删除",group = "删除",log = true)
    public ModelAndView deleteBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = diseaseService.delete(ids);
        String results = diseaseIcd9Service.deleteByDiseaseId(ids);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "基础知识库疾病表批量审核",group = "审核",log = true)
    public ModelAndView reviewBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        Date now = new Date();
        Long reviewor=Long.parseLong(getLoginUser(request).getSu_id());
        String result = diseaseService.review(ids,now,reviewor);
        return responseText(response, result);
    }
    
    @ActionAnnotation(name = "基础知识库疾病表批量启用",group = "启用",log = true)
    public ModelAndView EnableYBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = diseaseService.enableOrNot(ids, EnableStatus.ENABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "基础知识库疾病表批量停用",group = "停用",log = true)
    public ModelAndView EnableNBatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = BeanUtil.wrapArray(String.class, request.getParameter("ids"));
        String result = diseaseService.enableOrNot(ids, EnableStatus.DISABLE);
        return responseText(response, result);
    }
    @ActionAnnotation(name = "导入知识库疾病",group = "导入",log = true)
    public ModelAndView upload(HttpServletRequest request) throws Exception {
    	 return getView(request);
    }
}
