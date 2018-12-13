package com.zhys.fjzl.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zhys.core.domain.PageParam;
import com.zhys.core.domain.PageResult;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.BodyCheck;
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.util.DateUtils;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：药品对照表 Service
 */
@Service("DrugMapperService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class DrugMapperService extends BaseService {
    /**
     * 查询药品对照表对象
     * @param id 字符串型主键
     * @return Mapper
     * @throws Exception 异常
     */
    public DrugMapper query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("drug_mapper.pageList2", param);
    }

    /**
     * 查询药品对照表对象集合
     * @param param 查询条件
     * @return List<Mapper>
     * @throws Exception 异常
     */
    public List<DrugMapper> list(PageParam param) throws Exception {
    	return sqlDao.list("drug_mapper.pageList2",param);
    }
    
    /**
     * 查询药品对照表对象集合
     * @param param 查询条件
     * @return List<Mapper>
     * @throws Exception 异常
     */
    public List<DrugMapper> queryAll(PageParam param) throws Exception {
    	return sqlDao.list("drug_mapper.queryAll",param);
    }
    
    /**
     * 药品对照表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("drug_mapper.pageCount");
    	param.setRecordSql("drug_mapper.pageList2");
        return pageService.pageQuery(param);
    }

    /**
     * 插入药品对照表记录
     * @param mapper 药品对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(DrugMapper mapper) throws Exception  {
        sqlDao.create("drug_mapper.create",mapper);
        return "1";
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String updateCheck(PageParam pageParam) throws Exception {
    	Long count = getCount(pageParam);
    	return String.valueOf(count);
    }
    
    /**
     * 更新药品对照表记录
     * @param mapper 药品对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(DrugMapper mapper) throws Exception {
    	DrugMapper originalEntity = query(mapper.getId().toString());
		if(hasSpecialFieldUpdate(mapper, originalEntity)) {
			mapper.setDrug_status(HisDataStatus.UNMAPER.getStatus());
			mapper.setReview_time(null);
			mapper.setReviewor(null);
		}
        sqlDao.update("drug_mapper.update", mapper);
        return "1";
    }

    /**
     * 删除药品对照表记录
     * @param mapper 药品对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(DrugMapper mapper) throws Exception {
        sqlDao.delete("drug_mapper.delete", mapper);
        return "1";
    }

    /**
     * 删除药品对照表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new DrugMapper(id));
    }

    /**
     * 删除药品对照表记录
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
     * 审核药品对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] ids,Date now,Long reviewor) throws Exception {
        for(String id:ids){
        	DrugMapper drugMapper =query(id);
        	drugMapper.setDrug_status(HisDataStatus.REVIEWED.getStatus());
        	drugMapper.setReview_time(now);
        	drugMapper.setReviewor(reviewor);
        	update(drugMapper);
       
        }
        return "1";
    }
    
    /**
     * 启用药品对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
        for(String id:ids){
        	DrugMapper drugMapper = query(id);
         	drugMapper.set_enable(status.getStatus());
        	update(drugMapper);
        }
        return "1";
    }
    
    public Long getCount(PageParam pageParam) {
    	return sqlDao.query("drug_mapper.pageCount", pageParam);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void xyAutoMatch(String orgId, String importNo) {
    	try {
    		HashMap<String, Object> param = new HashMap<>();
    		param.put("orgId", orgId);
    		param.put("importNo", importNo);
    		param.put("result", 0);
    		sqlDao.query("drug_mapper.xyAutoMatch", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void zcyAutoMatch(String orgId, String importNo) {
    	try {
    		HashMap<String, Object> param = new HashMap<>();
    		param.put("orgId", orgId);
    		param.put("importNo", importNo);
    		param.put("result", 0);
    		System.out.println("orgId="+orgId+",importNo="+importNo);
    		sqlDao.query("drug_mapper.zcyAutoMatch", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void zyypAutoMatch(String orgId, String importNo) {
    	try {
    		HashMap<String, Object> param = new HashMap<>();
    		param.put("orgId", orgId);
    		param.put("importNo", importNo);
    		param.put("result", 0);
    		sqlDao.query("drug_mapper.zyypAutoMatch", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}