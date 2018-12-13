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
import com.zhys.fjzl.domain.BChdrug;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-19 <br/>
 * 描述：中药饮片 Service
 */
@Service("BChdrugService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class BChdrugService extends BaseService {
	
	@Resource(name = "DrugMapperService")
	private DrugMapperService drugMapperService; 
	
    /**
     * 查询中药饮片对象
     * @param id_ 字符串型主键
     * @return BChdrug
     * @throws Exception 异常
     */
    public BChdrug query(String id_) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id_", id_);
        return sqlDao.query("ebm_b_chdrug.pageList", param);
    }

    /**
     * 查询中药饮片对象集合
     * @param param 查询条件
     * @return List<BChdrug>
     * @throws Exception 异常
     */
    public List<BChdrug> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_b_chdrug.pageList",param);
    }
    
    /**
     * 中药饮片翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_b_chdrug.pageCount");
    	param.setRecordSql("ebm_b_chdrug.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入中药饮片记录
     * @param bChdrug 中药饮片对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(BChdrug bChdrug) throws Exception  {
        sqlDao.create("ebm_b_chdrug.create",bChdrug);
        return "1";
    }

    /**
     * 更新中药饮片记录
     * @param bChdrug 中药饮片对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(BChdrug bChdrug) throws Exception {
    	BChdrug originalEntity = query(bChdrug.getId_().toString());
		if(hasSpecialFieldUpdate(bChdrug, originalEntity)) {
			bChdrug.setChdrug_status(EbmStatus.UNREVIEW.getStatus());
			bChdrug.setReview_time(null);
			bChdrug.setReviewor(null);
			
			Long ebmDrugId = bChdrug.getId_();
			PageParam param = new PageParam();
			param.put("drug_id", ebmDrugId);
			param.put("drug_status_in", "1,3,4,5");
			param.put("type", "3");
			//查询所有已对照的中药饮片
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
        sqlDao.update("ebm_b_chdrug.update", bChdrug);
        return "1";
    }

    /**
     * 删除中药饮片记录
     * @param bChdrug 中药饮片对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(BChdrug bChdrug) throws Exception {
        sqlDao.delete("ebm_b_chdrug.delete", bChdrug);
        return "1";
    }

    /**
     * 删除中药饮片记录
     * @param id_ 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id_) throws Exception {
        return delete(new BChdrug(id_));
    }

    /**
     * 删除中药饮片记录
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
     * 审核中药饮片表记录
     * @param id_s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] id_s,Date now,Long reviewor) throws Exception {
        for(String id_:id_s){
        BChdrug chdrug = query(id_);
        chdrug.setChdrug_status(EbmStatus.REVIEWED.getStatus());
        chdrug.setReview_time(now);
        chdrug.setReviewor(reviewor);
        update(chdrug);
        }
        return "1";
    }
    
    
    /**
     * 启用或停用
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
    			param.put("type", "3");
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
        	BChdrug chdrug = query(id_);
        	chdrug.set_enable(status.getStatus());
        	update(chdrug);
        }
        return "1";
    }
    
    public Long getCount(PageParam param) {
    	return sqlDao.query("ebm_b_chdrug.pageCount", param);
    }
}