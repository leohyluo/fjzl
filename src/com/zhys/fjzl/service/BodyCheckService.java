package com.zhys.fjzl.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.domain.BCpdrug;
import com.zhys.fjzl.domain.BodyCheck;
import com.zhys.fjzl.domain.CheckMapper;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：知识库检查表 Service
 */
@Service("BodyCheckService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class BodyCheckService extends BaseService {
	
	
	@Resource(name = "CheckMapperService")
	private CheckMapperService checkMapperService; 
    /**
     * 查询知识库检查表对象
     * @param id 字符串型主键
     * @return BodyCheck
     * @throws Exception 异常
     */
    public BodyCheck query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("ebm_body_check.pageList", param);
    }

    /**
     * 查询知识库检查表对象集合
     * @param param 查询条件
     * @return List<BodyCheck>
     * @throws Exception 异常
     */
    public List<BodyCheck> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_body_check.pageList",param);
    }
    
    /**
     * 知识库检查表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_body_check.pageCount");
    	param.setRecordSql("ebm_body_check.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入知识库检查表记录
     * @param bodyCheck 知识库检查表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(BodyCheck bodyCheck) throws Exception  {
        sqlDao.create("ebm_body_check.create",bodyCheck);
        return "1";
    }

    /**
     * 更新知识库检查表记录
     * @param bodyCheck 知识库检查表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(BodyCheck bodyCheck) throws Exception {
    	BodyCheck originalEntity = query(bodyCheck.getId().toString());
    		if(hasSpecialFieldUpdate(bodyCheck, originalEntity)) {
    			bodyCheck.setBody_check_status(EbmStatus.UNREVIEW.getStatus());
    			bodyCheck.setReview_time(null);
    			bodyCheck.setReviewor(null);
    			Long ebmDiseaseId = bodyCheck.getId();
    			PageParam param = new PageParam();
    			param.put("check_id", ebmDiseaseId);
    			param.put("body_check_status_in", "1,3,4,5");
    			List<CheckMapper> mapperList = checkMapperService.queryAll(param);
    			for(CheckMapper item : mapperList) {
    				item.setBody_check_status(HisDataStatus.UNMAPER.getStatus());
    				item.setMapper_time(null);
    				item.setMapper_user(null);
    				item.setReview_time(null);
    				item.setReviewor(null);
    				item.setMemo(GlobalConstant.UNMAPPER_MESSAGE);
    				checkMapperService.update(item);
    			}
    		}
        sqlDao.update("ebm_body_check.update", bodyCheck);
        return "1";
    }

    /**
     * 删除知识库检查表记录
     * @param bodyCheck 知识库检查表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(BodyCheck bodyCheck) throws Exception {
        sqlDao.delete("ebm_body_check.delete", bodyCheck);
        return "1";
    }

    /**
     * 删除知识库检查表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new BodyCheck(id));
    }

    /**
     * 删除知识库检查表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String[] ids) throws Exception {
        for(String id:ids){
            delete(id);
        }
        return "1";
    }
    
    /**
     * 审核知识库检查表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] ids,Date now,Long reviewor) throws Exception {
        for(String id:ids){
        	BodyCheck bodyCheck=query(id);
        	bodyCheck.setBody_check_status(EbmStatus.REVIEWED.getStatus());
        	bodyCheck.setReview_time(now);
        	bodyCheck.setReviewor(reviewor);
        	update(bodyCheck);
        }
        return "1";
    }
    /**
     * 启用知识库检查表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
    	   for(String id : ids){
           	if(status == EnableStatus.DISABLE) {
           		PageParam param = new PageParam();
       			param.put("check_id", id);
       			param.put("body_check_status", "1,3,4,5");
       			List<CheckMapper> mapperList = checkMapperService.queryAll(param);
       			for(CheckMapper item : mapperList) {
       				item.setBody_check_status(HisDataStatus.UNMAPER.getStatus());
       				item.setMapper_time(null);
       				item.setMapper_user(null);
       				item.setReview_time(null);
       				item.setReviewor(null);
       				item.setMemo(GlobalConstant.UNMAPPER_MESSAGE);
       				checkMapperService.update(item);
       			}
           	}
        	BodyCheck bodyCheck=query(id);
        	bodyCheck.set_enable(status.getStatus());
        	update(bodyCheck);
        }
        return "1";
    }
    
    public Long getCount(PageParam param) {
    	return sqlDao.query("ebm_body_check.pageCount", param);
    }
}