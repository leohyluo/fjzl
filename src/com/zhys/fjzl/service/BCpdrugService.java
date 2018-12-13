package com.zhys.fjzl.service;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.domain.PageResult;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.constant.GlobalConstant;
import com.zhys.fjzl.domain.BCpdrug;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：中成药表 Service
 */
@Service("BCpdrugService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class BCpdrugService extends BaseService {
	
	@Resource(name = "DrugMapperService")
	private DrugMapperService drugMapperService; 
	
    /**
     * 查询中成药表对象
     * @param id_ 字符串型主键
     * @return BCpdrug
     * @throws Exception 异常
     */
    public BCpdrug query(String id_) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id_", id_);
        return sqlDao.query("ebm_b_cpdrug.pageList", param);
    }

    /**
     * 查询中成药表对象集合
     * @param param 查询条件
     * @return List<BCpdrug>
     * @throws Exception 异常
     */
    public List<BCpdrug> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_b_cpdrug.pageList",param);
    }
    
    /**
     * 中成药表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_b_cpdrug.pageCount");
    	param.setRecordSql("ebm_b_cpdrug.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入中成药表记录
     * @param bCpdrug 中成药表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(BCpdrug bCpdrug) throws Exception  {
        sqlDao.create("ebm_b_cpdrug.create",bCpdrug);
        return "1";
    }

    /**
     * 更新中成药表记录
     * @param bCpdrug 中成药表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(BCpdrug bCpdrug) throws Exception {
    	BCpdrug originalEntity = query(bCpdrug.getId_().toString());
		if(hasSpecialFieldUpdate(bCpdrug, originalEntity)) {
			bCpdrug.setCpdrug_status(EbmStatus.UNREVIEW.getStatus());
			bCpdrug.setReview_time(null);
			bCpdrug.setReviewor(null);
			
			Long ebmDrugId = bCpdrug.getId_();
			PageParam param = new PageParam();
			param.put("drug_id", ebmDrugId);
			param.put("drug_status_in", "1,3,4,5");
			param.put("type", "2");
			//查询所有已对照的中成药
			List<DrugMapper> mapperList = drugMapperService.queryAll(param);
			for(DrugMapper item : mapperList) {
				item.setDrug_status(HisDataStatus.UNMAPER.getStatus());
				item.setMapper_time(null);
				item.setMapper_user(null);
				item.setReview_time(null);
				item.setReviewor(null);
				item.setMemo(GlobalConstant.UNMAPPER_MESSAGE);
				drugMapperService.update(item);
			}
		}
        sqlDao.update("ebm_b_cpdrug.update", bCpdrug);
        return "1";
    }

    /**
     * 删除中成药表记录
     * @param bCpdrug 中成药表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(BCpdrug bCpdrug) throws Exception {
        sqlDao.delete("ebm_b_cpdrug.delete", bCpdrug);
        return "1";
    }

    /**
     * 删除中成药表记录
     * @param id_ 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id_) throws Exception {
        return delete(new BCpdrug(id_));
    }

    /**
     * 删除中成药表记录
     * @param id_s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String[] id_s) throws Exception {
        for(String id_:id_s){
            delete(id_);
        }
        return "1";
    }
    /**
     * 审核中成药表记录
     * @param id_s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] id_s,Date now,Long reviewor) throws Exception {
        for(String id_:id_s){
        BCpdrug bCpdrug = query(id_);
        	bCpdrug.setCpdrug_status(EbmStatus.REVIEWED.getStatus());
        	bCpdrug.setReview_time(now);
        	bCpdrug.setReviewor(reviewor);
        	update(bCpdrug);
        }
        return "1";
    }
    
   
   /**
    * 启用、停用 
    * @param ids
    * @param status
    * @return
    * @throws Exception
    */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
        for(String id_ : ids){
        	if(status == EnableStatus.DISABLE) {
        		PageParam param = new PageParam();
    			param.put("drug_id", id_);
    			param.put("drug_status_in", "1,3,4,5");
				param.put("type", "2");
    			List<DrugMapper> mapperList = drugMapperService.queryAll(param);
    			for(DrugMapper item : mapperList) {
    				item.setDrug_status(HisDataStatus.UNMAPER.getStatus());
    				item.setMapper_time(null);
    				item.setMapper_user(null);
    				item.setReview_time(null);
    				item.setReviewor(null);
    				item.setMemo(GlobalConstant.UNMAPPER_MESSAGE);
    				drugMapperService.update(item);
    			}
        	}
        	BCpdrug medicine = query(id_);
        	medicine.set_enable(status.getStatus());
        	update(medicine);
        }
        return "1";
    }
    
    @Transactional(readOnly = true)
    public Long getCount(PageParam param) throws Exception {
    	return sqlDao.query("ebm_b_cpdrug.pageCount", param);
    }
}