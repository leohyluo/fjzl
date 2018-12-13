package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.Record;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2017-03-10 <br/>
 * 描述：转诊记录 Service
 */
@Service("RecordService")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class RecordService extends BaseService {
    /**
     * 查询转诊记录对象
     * @param id 字符串型主键
     * @return Record
     * @throws Exception 异常
     */
    public Record query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("referral_record.pageList", param);
    }

    /**
     * 查询转诊记录对象集合
     * @param param 查询条件
     * @return List<Record>
     * @throws Exception 异常
     */
    public List<Record> list(PageParam param) throws Exception {
    	return sqlDao.list("referral_record.pageList",param);
    }
    
    /**
     * 转诊记录翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("referral_record.pageCount");
    	param.setRecordSql("referral_record.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入转诊记录记录
     * @param record 转诊记录对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(Record record) throws Exception  {
    	Integer id = (Integer) sqlDao.create("referral_record.create",record);
        return id.toString();
    }

    /**
     * 更新转诊记录记录
     * @param record 转诊记录对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(Record record) throws Exception {
        sqlDao.update("referral_record.update", record);
        return "1";
    }

    /**
     * 删除转诊记录记录
     * @param record 转诊记录对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(Record record) throws Exception {
        sqlDao.delete("referral_record.delete", record);
        return "1";
    }

    /**
     * 删除转诊记录记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new Record(id));
    }

    /**
     * 删除转诊记录记录
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