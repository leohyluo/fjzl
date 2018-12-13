package com.zhys.fjzl.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.domain.PageResult;
import com.zhys.core.service.BaseService;
import com.zhys.core.util.CollectionUtils;
import com.zhys.fjzl.core.pojo.response.CheckRes;
import com.zhys.fjzl.core.pojo.response.DiseaseRes;
import com.zhys.fjzl.core.pojo.response.DrugRes;
import com.zhys.fjzl.core.pojo.response.FjzlResult;
import com.zhys.fjzl.domain.ApiRecord;
import com.zhys.fjzl.domain.RecordUnmatchItem;
import com.zhys.fjzl.enums.ExamResult;
import com.zhys.fjzl.enums.ReferralFlag;
import com.zhys.fjzl.enums.ReferralRuleType;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述： Service
 */
@Service("ApiRecordService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class ApiRecordService extends BaseService {
	
	@Resource(name = "RecordUnmatchItemService")
	private RecordUnmatchItemService recordUnmatchItemService;
    /**
     * 查询对象
     * @param id 字符串型主键
     * @return ApiRecord
     * @throws Exception 异常
     */
    public ApiRecord query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("fjzl_api_record.pageList", param);
    }

    /**
     * 查询对象集合
     * @param param 查询条件
     * @return List<ApiRecord>
     * @throws Exception 异常
     */
    public List<ApiRecord> list(PageParam param) throws Exception {
    	return sqlDao.list("fjzl_api_record.pageList",param);
    }
    
    /**
     * 翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("fjzl_api_record.pageCount");
    	param.setRecordSql("fjzl_api_record.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入记录
     * @param apiRecord 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(ApiRecord apiRecord) throws Exception  {
        sqlDao.create("fjzl_api_record.create",apiRecord);
        return "1";
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String saveFjzlRecord(ApiRecord apiRecord, FjzlResult fjzlResult) throws Exception {
    	
    	String orgId = apiRecord.getOrgid();
    	List<RecordUnmatchItem> unmatchRecordList = new ArrayList<>();
    	List<DiseaseRes> diseaseReport = fjzlResult.getDiseaseList();
    	List<DrugRes> drugReport = fjzlResult.getDrugList();
    	List<CheckRes> checkReport = fjzlResult.getCheckList();
    	
    	if(CollectionUtils.isNotEmpty(diseaseReport)) {
    		for(DiseaseRes itemDisRes : diseaseReport) {
    			RecordUnmatchItem unmatchItem = new RecordUnmatchItem();
    			unmatchItem.setOrgid(orgId);
    			unmatchItem.setIllegalid(itemDisRes.getId());
    			unmatchItem.setIllegalName(itemDisRes.getName());
    			unmatchItem.setExpectGrade(itemDisRes.getGrade());
    			unmatchItem.setIllegatype(ReferralRuleType.DISEASE.getType());
    			unmatchRecordList.add(unmatchItem);
    		}
    	}
    	
    	if(CollectionUtils.isNotEmpty(drugReport)) {
    		for(DrugRes itemRes : drugReport) {
    			RecordUnmatchItem unmatchItem = new RecordUnmatchItem();
    			unmatchItem.setOrgid(orgId);
    			unmatchItem.setIllegalid(itemRes.getId());
    			unmatchItem.setIllegalName(itemRes.getName());
    			unmatchItem.setExpectGrade(itemRes.getGrade());
    			unmatchItem.setIllegatype(ReferralRuleType.DRUG.getType());
    			unmatchRecordList.add(unmatchItem);
    		}
    	}
    	
    	if(CollectionUtils.isNotEmpty(checkReport)) {
    		for(CheckRes itemRes : checkReport) {
    			RecordUnmatchItem unmatchItem = new RecordUnmatchItem();
    			unmatchItem.setOrgid(orgId);
    			unmatchItem.setIllegalid(itemRes.getId());
    			unmatchItem.setIllegalName(itemRes.getName());
    			unmatchItem.setExpectGrade(itemRes.getGrade());
    			unmatchItem.setIllegatype(ReferralRuleType.BODYCHECK.getType());
    			unmatchRecordList.add(unmatchItem);
    		}
    	}
    	
    	if(CollectionUtils.isNotEmpty(unmatchRecordList)) {
    		apiRecord.setExamresult(ExamResult.NOTPASS.getStatus());
    	}
    	Integer referralType = getReferralType(unmatchRecordList);
    	apiRecord.setOrglist(fjzlResult.getOrgList());
    	apiRecord.setReferralType(referralType);
    	Object recordId = sqlDao.create("fjzl_api_record.create",apiRecord);
    	
    	
    	for(RecordUnmatchItem item : unmatchRecordList) {
    		item.setRecordid(Long.valueOf(recordId.toString()));
    		recordUnmatchItemService.create(item);
    	}
    	return String.valueOf(recordId);    			
    }

    /**
     * 更新记录
     * @param apiRecord 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String update(ApiRecord apiRecord) throws Exception {
        sqlDao.update("fjzl_api_record.update", apiRecord);
        return "1";
    }

    /**
     * 删除记录
     * @param apiRecord 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(ApiRecord apiRecord) throws Exception {
        sqlDao.delete("fjzl_api_record.delete", apiRecord);
        return "1";
    }

    /**
     * 删除记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new ApiRecord(id));
    }

    /**
     * 删除记录
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
    
    public void updateRecordStatus(String recordId) throws Exception {
    	ApiRecord apiRecord = query(recordId);
    	apiRecord.setReferralflag(ReferralFlag.YES.getStatus());
        this.update(apiRecord);
    }
    
    /**
     * 获取导致转诊的项目（疾病、药品、检查）
     * @param unmatchList
     * @return
     */
    private Integer getReferralType(List<RecordUnmatchItem> unmatchList) {
    	
    	if(CollectionUtils.isNotEmpty(unmatchList)) {
    		Collections.sort(unmatchList, new Comparator<RecordUnmatchItem>() {
				@Override
				public int compare(RecordUnmatchItem o1, RecordUnmatchItem o2) {
					return o2.getExpectGrade().compareTo(o1.getExpectGrade());
				}
			});
    		String maxGrade = unmatchList.get(0).getExpectGrade();
    		List<RecordUnmatchItem> maxGradeList = new ArrayList<>();
    		for(RecordUnmatchItem item : unmatchList) {
    			if(item.getExpectGrade().equals(maxGrade)) {
    				maxGradeList.add(item);
    			}
    		}
    		
    		for(RecordUnmatchItem item : maxGradeList) {
    			int illegaType = item.getIllegatype();
    			if(ReferralRuleType.DISEASE.getType() == illegaType) {
    				return ReferralRuleType.DISEASE.getType();
    			}
    		}
    		
    		for(RecordUnmatchItem item : maxGradeList) {
    			int illegaType = item.getIllegatype();
    			if(ReferralRuleType.DRUG.getType() == illegaType) {
    				return ReferralRuleType.DRUG.getType();
    			}
    		}
    		
    		for(RecordUnmatchItem item : maxGradeList) {
    			int illegaType = item.getIllegatype();
    			if(ReferralRuleType.BODYCHECK.getType() == illegaType) {
    				return ReferralRuleType.BODYCHECK.getType();
    			}
    		}
    		
    	}
    	return 0;
    }
}