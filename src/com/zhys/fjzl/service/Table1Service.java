package com.zhys.fjzl.service;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import com.zhys.core.domain.PageResult;
import com.zhys.core.domain.PageParam;
import com.zhys.core.service.BaseService;
import com.zhys.fjzl.domain.Table1;

/**
 * 版权：智慧药师  <br/>
 * 作者：dailing <br/>
 * 生成日期：2016-10-10 <br/>
 * 描述：新建表 Service
 */
@Service("Table1Service")
@Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true, rollbackFor = Throwable.class)
public class Table1Service extends BaseService {
    /**
     * 查询新建表对象
     * @param id 字符串型主键
     * @return Table1
     * @throws Exception 异常
     */
    public Table1 query(String id) throws Exception{
    	PageParam param = new PageParam();
    	param.put("id", id);
        return sqlDao.query("table1.pageList", param);
    }

    /**
     * 查询新建表对象集合
     * @param param 查询条件
     * @return List<Table1>
     * @throws Exception 异常
     */
    public List<Table1> list(PageParam param) throws Exception {
    	return sqlDao.list("table1.pageList",param);
    }
    
    /**
     * 新建表翻页查询
     * @param 分页条件对象
     * @return PageResult
     * @throws Exception 异常
     */
    public PageResult pageList(PageParam param) throws Exception {
    	param.setCountSql("table1.pageCount");
    	param.setRecordSql("table1.pageList");
        return pageService.pageQuery(param);
    }

    /**
     * 插入新建表记录
     * @param table1 新建表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String create(Table1 table1) throws Exception  {
        sqlDao.create("table1.create",table1);
        return "1";
    }

    /**
     * 更新新建表记录
     * @param table1 新建表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String update(Table1 table1) throws Exception {
        sqlDao.update("table1.update", table1);
        return "1";
    }

    /**
     * 删除新建表记录
     * @param table1 新建表对象
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(Table1 table1) throws Exception {
        sqlDao.delete("table1.delete", table1);
        return "1";
    }

    /**
     * 删除新建表记录
     * @param id 字符串型主键
     * @return String
     * @throws Exception 异常
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Throwable.class)
    public String delete(String id) throws Exception {
        return delete(new Table1(id));
    }

    /**
     * 删除新建表记录
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