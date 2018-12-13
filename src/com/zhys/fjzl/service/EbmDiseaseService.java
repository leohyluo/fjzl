package com.zhys.fjzl.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.domain.PageResult;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.domain.EbmDisease;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-16 <br/>
 * 描述：基础知识库疾病表 Service
 */
@Service("DiseaseService")
@Transactional
public class EbmDiseaseService extends BaseService {
	
	
	@Resource(name = "DiseaseMapperService")
	private DiseaseMapperService diseaseMapperService; 
    /**
     * 查询基础知识库疾病表对象
     * @param id 字符串型主键
     * @return EbmDisease
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public EbmDisease query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("ebm_disease.pageList", param);
    }

    /**
     * 查询基础知识库疾病表对象集合
     * @param param 查询条件
     * @return List<EbmDisease>
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<EbmDisease> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_disease.pageList",param);
    }
	
	/**
     * 查询基础知识库疾病表对象集合
     * @param param 查询条件
     * @return List<EbmDisease>
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<EbmDisease> selfList(PageParam param) throws Exception {
    	return sqlDao.list("ebm_disease.selfList",param);
    }
    
    /**
     * 基础知识库疾病表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_disease.pageCount");
    	param.setRecordSql("ebm_disease.pageList");
        return pageService.pageQuery(param);
    }
	
	public Long getCount(PageParam param) {
		Long count = sqlDao.query("ebm_disease.pageCount", param);
		return count;
	}

    /**
     * 插入基础知识库疾病表记录
     * @param EbmDisease 基础知识库疾病表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String create(EbmDisease EbmDisease) throws Exception  {
        return String.valueOf(sqlDao.create("ebm_disease.create",EbmDisease));
    }

    /**
     * 更新基础知识库疾病表记录
     * @param EbmDisease 基础知识库疾病表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String update(EbmDisease ebmDisease) throws Exception {
		EbmDisease originalEntity = query(ebmDisease.getId().toString());
		if(hasSpecialFieldUpdate(ebmDisease, originalEntity)) {
			ebmDisease.setDisease_status(EbmStatus.UNREVIEW.getStatus());
			ebmDisease.setReview_time(null);
			ebmDisease.setReviewor(null);
			Long ebmDiseaseId = ebmDisease.getId();
			PageParam param = new PageParam();
			param.put("disease_id", ebmDiseaseId);
			param.put("disease_status_in", "1,3,4,5");
			List<DiseaseMapper> mapperList = diseaseMapperService.queryAll(param);
			for(DiseaseMapper item : mapperList) {
				item.setDisease_status(HisDataStatus.UNMAPER.getStatus());
				item.setMapper_time(null);
				item.setMapper_user(null);
				item.setReview_time(null);
				item.setReviewor(null);
				item.setMemo(GlobalConstant.UNMAPPER_MESSAGE);
				diseaseMapperService.update(item);
			}
		}
        return String.valueOf(sqlDao.update("ebm_disease.update", ebmDisease));
    }

    /**
     * 删除基础知识库疾病表记录
     * @param EbmDisease 基础知识库疾病表对象
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(EbmDisease EbmDisease) throws Exception {
        return String.valueOf(sqlDao.delete("ebm_disease.delete", EbmDisease));
    }

    /**
     * 删除基础知识库疾病表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(String id) throws Exception {
        return delete(new EbmDisease(id));
    }

    /**
     * 删除基础知识库疾病表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(String[] ids) throws Exception {
        for(String id:ids){
            delete(id);
        }
        return "1";
    }
	/**
     * 删除基础知识库疾病表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String review(String[] ids,Date now,Long reviewor) throws Exception {
        for(String id:ids){
        	EbmDisease ebmDisease=query(id);
        	ebmDisease.setDisease_status(EbmStatus.REVIEWED.getStatus());
        	ebmDisease.setReview_time(now);
        	ebmDisease.setReviewor(reviewor);
        	update(ebmDisease);
        }
        return "1";
    }
	/**
     * 启用基础知识库疾病表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
 	   for(String id : ids){
          	if(status == EnableStatus.DISABLE) {
          		PageParam param = new PageParam();
      			param.put("check_id", id);
      			param.put("body_check_status", "1,3,4,5");
      			List<DiseaseMapper> mapperList = diseaseMapperService.queryAll(param);
      			for(DiseaseMapper item : mapperList) {
      				item.setDisease_status(HisDataStatus.UNMAPER.getStatus());
      				item.setMapper_time(null);
      				item.setMapper_user(null);
      				item.setReview_time(null);
      				item.setReviewor(null);
      				item.setMemo(GlobalConstant.UNMAPPER_MESSAGE);
      				diseaseMapperService.update(item);
      			}
          	}
        	EbmDisease ebmDisease=query(id);
        	ebmDisease.set_enable(status.getStatus());
        	update(ebmDisease);
        }
        return "1";
    }
	
}