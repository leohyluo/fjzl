package com.zhys.fjzl.service;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.core.util.StringUtil;
import com.zhys.fjzl.core.pojo.ReferralForm;
import com.zhys.fjzl.domain.ApiRecord;
import com.zhys.fjzl.domain.ReferralDetail;
import com.zhys.fjzl.enums.ReferralFlag;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述： Service
 */
@Service("ReferralDetailService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class ReferralDetailService extends BaseService {
	
	@Resource(name = "ApiRecordService")
	private ApiRecordService apiRecordService; 
	
    /**
     * 查询对象
     * @param id 字符串型主键
     * @return ReferralDetail
     * @throws Exception 异常
     */
    public ReferralDetail query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("fjzl_referral_detail.pageList", param);
    }

    /**
     * 查询对象集合
     * @param param 查询条件
     * @return List<ReferralDetail>
     * @throws Exception 异常
     */
    public List<ReferralDetail> list(PageParam param) throws Exception {
    	return sqlDao.list("fjzl_referral_detail.pageList",param);
    }
    
    /**
     * 翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("fjzl_referral_detail.pageCount");
    	param.setRecordSql("fjzl_referral_detail.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入记录
     * @param referralDetail 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(ReferralDetail referralDetail) throws Exception  {
        sqlDao.create("fjzl_referral_detail.create",referralDetail);
        return "1";
    }

    /**
     * 更新记录
     * @param referralDetail 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(ReferralDetail referralDetail) throws Exception {
        sqlDao.update("fjzl_referral_detail.update", referralDetail);
        return "1";
    }

    /**
     * 删除记录
     * @param referralDetail 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(ReferralDetail referralDetail) throws Exception {
        sqlDao.delete("fjzl_referral_detail.delete", referralDetail);
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
        return delete(new ReferralDetail(id));
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
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(ReferralForm form) throws Exception {
    	String flag = form.getFlag();
    	String recordId = form.getRecord_id();
    	if(StringUtil.isEmpty(recordId)) {
    		throw new Exception("recordId was null, please check");
    	}
    	if("1".equals(flag)) {
    		ApiRecord apiRecord = apiRecordService.query(recordId);
    		if(apiRecord == null) {
    			throw new Exception("invalid recordId "+recordId+"");
    		}
    		ReferralDetail referralDetail = new ReferralDetail(form);
            create(referralDetail);
            apiRecord.setReferralflag(ReferralFlag.YES.getStatus());
            apiRecordService.update(apiRecord);
    	}
    	
    }
}