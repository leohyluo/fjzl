package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.RecordUnmatchItem;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-06-20 <br/>
 * 描述： Service
 */
@Service("RecordUnmatchItemService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class RecordUnmatchItemService extends BaseService {
    /**
     * 查询对象
     * @param id 字符串型主键
     * @return ResultRecord
     * @throws Exception 异常
     */
    public RecordUnmatchItem query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("fjzl_record_unmatch_items.pageList", param);
    }

    /**
     * 查询对象集合
     * @param param 查询条件
     * @return List<ResultRecord>
     * @throws Exception 异常
     */
    public List<RecordUnmatchItem> list(PageParam param) throws Exception {
    	return sqlDao.list("fjzl_record_unmatch_items.pageList",param);
    }
    
    /**
     * 翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("fjzl_record_unmatch_items.pageCount");
    	param.setRecordSql("fjzl_record_unmatch_items.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入记录
     * @param resultRecord 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String create(RecordUnmatchItem resultRecord) throws Exception  {
        sqlDao.create("fjzl_record_unmatch_items.create",resultRecord);
        return "1";
    }

    /**
     * 更新记录
     * @param resultRecord 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(RecordUnmatchItem resultRecord) throws Exception {
        sqlDao.update("fjzl_record_unmatch_items.update", resultRecord);
        return "1";
    }

    /**
     * 删除记录
     * @param resultRecord 对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(RecordUnmatchItem resultRecord) throws Exception {
        sqlDao.delete("fjzl_record_unmatch_items.delete", resultRecord);
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
        return delete(new RecordUnmatchItem(id));
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
}