package com.zhys.fjzl.service;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.CheckMapper;
import com.zhys.fjzl.domain.DiseaseMapper;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-17 <br/>
 * 描述：检查对照表 Service
 */
@Service("CheckMapperService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class CheckMapperService extends BaseService {
    /**
     * 查询检查对照表对象
     * @param id 字符串型主键
     * @return CheckMapper
     * @throws Exception 异常
     */
    public CheckMapper query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("body_check_mapper.pageList", param);
    }

    /**
     * 查询检查对照表对象集合
     * @param param 查询条件
     * @return List<CheckMapper>
     * @throws Exception 异常
     */
    public List<CheckMapper> list(PageParam param) throws Exception {
    	return sqlDao.list("body_check_mapper.pageList",param);
    }
    /**
     * 查询检查对照表对象集合
     * @param param 查询条件
     * @return List<Mapper>
     * @throws Exception 异常
     */
    public List<CheckMapper> queryAll(PageParam param) throws Exception {
    	return sqlDao.list("body_check_mapper.queryAll",param);
    }
    
    /**
     * 检查对照表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("body_check_mapper.pageCount");
    	param.setRecordSql("body_check_mapper.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入检查对照表记录
     * @param checkMapper 检查对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(CheckMapper checkMapper) throws Exception  {
        sqlDao.create("body_check_mapper.create",checkMapper);
        return "1";
    }
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String updateCheck(PageParam pageParam) throws Exception {
    	Long count = getCount(pageParam);
    	return String.valueOf(count);
    }
    /**
     * 更新检查对照表记录
     * @param checkMapper 检查对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(CheckMapper checkMapper) throws Exception {
    	CheckMapper originalEntity = query(checkMapper.getId().toString());
    		if(hasSpecialFieldUpdate(checkMapper, originalEntity)) {
    			checkMapper.setBody_check_status(HisDataStatus.UNMAPER.getStatus());
    			checkMapper.setReview_time(null);
    			checkMapper.setReviewor(null);
    		}
        sqlDao.update("body_check_mapper.update", checkMapper);
        return "1";
    }

    /**
     * 删除检查对照表记录
     * @param checkMapper 检查对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(CheckMapper checkMapper) throws Exception {
        sqlDao.delete("body_check_mapper.delete", checkMapper);
        return "1";
    }

    /**
     * 删除检查对照表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new CheckMapper(id));
    }

    /**
     * 删除检查对照表记录
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
     * 审核检查对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] ids,Date now,Long reviewor) throws Exception {
        for(String id:ids){
         	CheckMapper checkMapper =query(id);
        	checkMapper.setBody_check_status(HisDataStatus.REVIEWED.getStatus());
        	checkMapper.setReview_time(now);
        	checkMapper.setReviewor(reviewor);
        	update(checkMapper);
       
        }
        return "1";
    }
    
    /**
     * 启用检查对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus status) throws Exception {
        for(String id:ids){
         	CheckMapper checkMapper = query(id);
        	checkMapper.set_enable(status.getStatus());
        	update(checkMapper);
        }
        return "1";
    }
    
    public Long getCount(PageParam param) {
    	return sqlDao.query("body_check_mapper.pageCount", param);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void autoMatch(String orgId, String importNo) {
    	try {
    		HashMap<String, Object> param = new HashMap<>();
    		param.put("orgId", orgId);
    		param.put("importNo", importNo);
    		param.put("result", 0);
    		sqlDao.query("body_check_mapper.autoMatch", param);
    		System.out.println("auto match complete...");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}