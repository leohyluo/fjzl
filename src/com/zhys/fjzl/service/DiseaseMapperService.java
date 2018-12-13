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
import com.zhys.fjzl.domain.DrugMapper;
import com.zhys.fjzl.enums.EbmStatus;
import com.zhys.fjzl.enums.EnableStatus;
import com.zhys.fjzl.enums.HisDataStatus;
import com.zhys.util.DateUtils;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-12-21 <br/>
 * 描述：疾病对照表 Service
 */
@Service("DiseaseMapperService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class DiseaseMapperService extends BaseService {
    /**
     * 查询疾病对照表对象
     * @param id 字符串型主键
     * @return DiseaseMapper
     * @throws Exception 异常
     */
    public DiseaseMapper query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("disease_mapper.pageList", param);
    }

    /**
     * 查询疾病对照表对象集合
     * @param param 查询条件
     * @return List<DiseaseMapper>
     * @throws Exception 异常
     */
    public List<DiseaseMapper> list(PageParam param) throws Exception {
    	return sqlDao.list("disease_mapper.pageList",param);
    }
    
    /**
     * 查询疾病对照表对象集合
     * @param param 查询条件
     * @return List<DiseaseMapper>
     * @throws Exception 异常
     */
    public List<DiseaseMapper> selfList(PageParam param) throws Exception {
    	return sqlDao.list("disease_mapper.apiPageList",param);
    }
    
    /**
     * 查询疾病对照表对象集合
     * @param param 查询条件
     * @return List<Mapper>
     * @throws Exception 异常
     */
    public List<DiseaseMapper> queryAll(PageParam param) throws Exception {
    	return sqlDao.list("disease_mapper.queryAll",param);
    }
    
    /**
     * 疾病对照表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("disease_mapper.pageCount");
    	param.setRecordSql("disease_mapper.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入疾病对照表记录
     * @param mapper 疾病对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(DiseaseMapper mapper) throws Exception  {
        sqlDao.create("disease_mapper.create",mapper);
        return "1";
    }
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String updateCheck(PageParam pageParam) throws Exception {
    	Long count = getCount(pageParam);
    	return String.valueOf(count);
    }
    /**
     * 更新疾病对照表记录
     * @param mapper 疾病对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(DiseaseMapper mapper) throws Exception {
    	DiseaseMapper originalEntity = query(mapper.getId().toString());
		if(hasSpecialFieldUpdate(mapper, originalEntity)) {
			mapper.setDisease_status(HisDataStatus.UNMAPER.getStatus());
			mapper.setReview_time(null);
			mapper.setReviewor(null);
		}
        sqlDao.update("disease_mapper.update", mapper);
        return "1";
    }

    /**
     * 删除疾病对照表记录
     * @param mapper 疾病对照表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(DiseaseMapper mapper) throws Exception {
        sqlDao.delete("disease_mapper.delete", mapper);
        return "1";
    }

    /**
     * 删除疾病对照表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new DiseaseMapper(id));
    }

    /**
     * 删除疾病对照表记录
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
     * 审核疾病对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String review(String[] ids,Date now,Long reviewor) throws Exception {
        for(String id:ids){
        	DiseaseMapper diseaseMapper=query(id);
        	diseaseMapper.setDisease_status(HisDataStatus.REVIEWED.getStatus());
        	diseaseMapper.setReview_time(now);
        	diseaseMapper.setReviewor(reviewor);
            update(diseaseMapper);
        }
        return "1";
    }
    /**
     * 启用疾病对照表记录
     * @param ids 字符串型主键数组
     * @return String
     * @throws Exception 异常
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String enableOrNot(String[] ids, EnableStatus enable) throws Exception {
        for(String id:ids){
        	DiseaseMapper diseaseMapper = query(id);
        	diseaseMapper.set_enable(enable.getStatus());
            update(diseaseMapper);
        }
        return "1";
    }
    
    public Long getCount(PageParam pageParam) {
    	return sqlDao.query("disease_mapper.pageCount", pageParam);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
    public void autoMatch(String orgId, String importNo) {
    	try {
    		HashMap<String, Object> param = new HashMap<>();
    		param.put("orgId", orgId);
    		param.put("importNo", importNo);
    		param.put("result", 0);
    		sqlDao.query("disease_mapper.autoMatch", param);
    		System.out.println("auto match complete...");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}