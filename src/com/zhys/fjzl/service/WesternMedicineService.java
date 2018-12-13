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
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.domain.WesternMedicine;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：知识库西药表 Service
 */
@Service("WesternMedicineService")
@Transactional
public class WesternMedicineService extends BaseService {
    /**
     * 查询知识库西药表对象
     * @param id 字符串型主键
     * @return WesternMedicine
     * @throws Exception 异常
     */
	
	@Resource(name = "DrugMapperService")
	private DrugMapperService drugMapperService; 
	
    public WesternMedicine query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("ebm_western_medicine.pageList", param);
    }
    
    public Long getCount(PageParam param) {
    	return sqlDao.query("ebm_western_medicine.pageCount", param);
    }

    /**
     * 查询知识库西药表对象集合
     * @param param 查询条件
     * @return List<WesternMedicine>
     * @throws Exception 异常
     */
    public List<WesternMedicine> list(PageParam param) throws Exception {
    	return sqlDao.list("ebm_western_medicine.pageList",param);
    }
    
    /**
     * 知识库西药表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("ebm_western_medicine.pageCount");
    	param.setRecordSql("ebm_western_medicine.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入知识库西药表记录
     * @param westernMedicine 知识库西药表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String create(WesternMedicine westernMedicine) throws Exception  {
        sqlDao.create("ebm_western_medicine.create",westernMedicine);
        return "1";
    }

    /**
     * 更新知识库西药表记录
     * @param westernMedicine 知识库西药表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String update(WesternMedicine westernMedicine) throws Exception {
    	WesternMedicine originalEntity = query(westernMedicine.getId().toString());
    		if(hasSpecialFieldUpdate(westernMedicine, originalEntity)) {
    			westernMedicine.setWestern_status(EbmStatus.UNREVIEW.getStatus());
    			westernMedicine.setReview_time(null);
    			westernMedicine.setReviewor(null);
    			
    			Long ebmDrugId = westernMedicine.getId();
    			PageParam param = new PageParam();
    			param.put("drug_id", ebmDrugId);
    			param.put("drug_status_in", "1,3,4,5");
    			param.put("type", "1");
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
        sqlDao.update("ebm_western_medicine.update", westernMedicine);
        return "1";
    }

    /**
     * 删除知识库西药表记录
     * @param westernMedicine 知识库西药表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(WesternMedicine westernMedicine) throws Exception {
        sqlDao.delete("ebm_western_medicine.delete", westernMedicine);
        return "1";
    }

    /**
     * 删除知识库西药表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String delete(String id) throws Exception {
        return delete(new WesternMedicine(id));
    }

    /**
     * 删除知识库西药表记录
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
     * 审核知识库西药表记录
     * @param id_s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] id_s,Date now,Long reviewor) throws Exception {
        for(String id_:id_s){
        	WesternMedicine medicine =query(id_);
        	medicine.setWestern_status(EbmStatus.REVIEWED.getStatus());
        	medicine.setReview_time(now);
        	medicine.setReviewor(reviewor);
        	medicine.set_enable(EnableStatus.ENABLE.getStatus());
        	update(medicine);
        }
        return "1";
    }
    /**
     * 启用知识库西药表记录
     * @param id_s 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
        for(String id_ : ids){
        	if(status == EnableStatus.DISABLE) {
        		PageParam param = new PageParam();
    			param.put("drug_id", id_);
    			param.put("body_check_status_in", "1,3,4,5");
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
        	WesternMedicine medicine = query(id_);
        	medicine.set_enable(status.getStatus());
        	update(medicine);
        }
        return "1";
    }
}